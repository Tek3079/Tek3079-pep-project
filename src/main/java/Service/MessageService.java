package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    
MessageDAO messageDAO = new MessageDAO();
AccountDAO accountDAO = new AccountDAO();
public Message creatMessage(Message message){
    Account as = accountDAO.findById(message.getPosted_by());
    if (message.getMessage_text() == null || message.getMessage_text() == "" || as == null || message.getMessage_text().length()>255){
        return null;
}
    return messageDAO.creatMessage(message);   
}
public List<Message> getAllMessage(){
    
    return messageDAO.getAllMessage();
    
}
public Message getMessageById(int id){

    return messageDAO.getMessageById(id);
}
public Message deleteMessage(int id){
    return messageDAO.deleteMessage(id);
}
public List<Message> getAllMessageByUser(int id){
    return messageDAO.getAllMessageByUser(id);
}
public Message updateMessage(int id , String messageText){
    Message ms =messageDAO.getMessageById(id);
    if (ms == null || messageText.length()>255 || messageText == ""){
        return null;
    }
    return messageDAO.updateMessage(id, messageText);

}
}
