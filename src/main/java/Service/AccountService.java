package Service;
import DAO.AccountDAO;
import Model.Account;
public class AccountService {
    
AccountDAO accountDAO = new AccountDAO();
public Account createAccount(Account account) {
    if (account.getUsername() == null || account.getUsername() == "" ) {
        return null; // Username cannot be blank
    }
    if (account.getPassword() == null || account.getPassword().length() < 4) {
        return null; // Password too short
    }
    return accountDAO.createAccount(account);
}

public Account verifyAccount(Account account){

    Account verifiedAccount = accountDAO.findByUsername(account.getUsername());
    if(verifiedAccount == null){
        return null;
    }
    if (verifiedAccount.getPassword().equals(account.getPassword())){
        return verifiedAccount;
    }else {
        return null;
    }
}
}