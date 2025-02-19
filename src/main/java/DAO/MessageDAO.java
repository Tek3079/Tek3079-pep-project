package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    

    public Message creatMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "insert into Message (posted_by, message_text, time_posted_epoch) Values(? , ? , ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()){
                int message_id = (int)rs.getLong(1);
                return new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getAllMessage(){
        Connection connection = ConnectionUtil.getConnection();
        List <Message> messageList = new ArrayList<>();
        try{
            String sql = "select * from Message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            Message ms = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")

            );       
            messageList.add(ms);        
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messageList;
    }
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
     
        try{
            String sql ="select * from Message where message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
           while( rs.next()){
            return  new Message( rs.getInt("message_id"),
            rs.getInt("posted_by"),
            rs.getString("message_text"),
            rs.getLong("time_posted_epoch"));
           }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return null;
    }
    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = getMessageById(id);
      

        if (message != null){
            try{
                String sql ="Delete from Message where message_id =?;";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                return message;
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    public List<Message> getAllMessageByUser(int posted_by){
        Connection connection =ConnectionUtil.getConnection();
        List <Message> userMessages = new ArrayList<>();
        try{
        String sql = "select * from Message where posted_by =?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, posted_by);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Message ms = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")

            );  
            userMessages.add(ms);     
        }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return userMessages;
    }
    public Message updateMessage(int id, String text){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";

        try {
            PreparedStatement psUpdate = connection.prepareStatement(sql);
            psUpdate.setString(1, text);
            psUpdate.setInt(2, id);
            
            int rowsAffected = psUpdate.executeUpdate();
    
            
            if (rowsAffected > 0) {
                
                return getMessageById(id); 
            } else {
                return null; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }
}
