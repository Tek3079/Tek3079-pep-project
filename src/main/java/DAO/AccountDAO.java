package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    public Account createAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        if (findByUsername(account.getUsername()) != null) {
            return null; // Username already exists
        }

        try{
            String sql = "Insert into Account (username, password) Values (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
         
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            if(key.next()){
                int generatedAccount_id = (int)key.getLong(1);
                return new Account(generatedAccount_id,account.getUsername(),account.getPassword() );
            }

        }catch(SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
    public Account findByUsername(String username) {
        String sql = "SELECT * FROM Account WHERE username = ?";
        
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
