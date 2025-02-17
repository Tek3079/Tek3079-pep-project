package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
   
     AccountService accountService = new AccountService() ;
    MessageService messageService = new MessageService();
     public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessage);
       // app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context ctx) throws JsonProcessingException {
        try {
            ObjectMapper om = new ObjectMapper();
            Account account = om.readValue(ctx.body(), Account.class);
            Account addedAccount = accountService.createAccount(account);

            if (addedAccount != null) {
                ctx.status(200).json(addedAccount);
            } else {
                
                ctx.status(400).json("");
            }
        } catch (JsonProcessingException e) {
            ctx.status(400).json("");
        }
    
    }
    private void loginHandler (Context ctx ) throws JsonProcessingException{
        try {
            ObjectMapper om = new ObjectMapper();
            Account account = om.readValue(ctx.body(),Account.class);
            Account verifiedAccount = accountService.verifyAccount(account);
            if (verifiedAccount != null){
                ctx.status(200).json(verifiedAccount);
            }else{
                ctx.status(401);
            }
        }catch (JsonProcessingException e){
            ctx.status(401);
        }
    }
private void createMessage(Context ctx) throws JsonProcessingException{
    try{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.creatMessage(message);
        if (createdMessage != null){
            ctx.status(200).json(createdMessage);
        }else{
            ctx.status(400);
        }
    }catch (JsonProcessingException e){
        ctx.status(400);
    }
}

}