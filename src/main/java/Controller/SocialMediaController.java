package Controller;

import java.util.ArrayList;
import java.util.List;

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
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountCreationHandler);
        app.post("/login", this::postAccountLoginHandler);
        app.post("/messages", this::postMessageCreationHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromAccountHandler);

        return app;
    }

    /**
     * Handler to post a new author
     * 
     * @param ctx the The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postAccountCreationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler to login to account
     * 
     * @param ctx the The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postAccountLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedIntoAccount = accountService.loginToAccount(account);
        if (loggedIntoAccount != null) {
            ctx.json(mapper.writeValueAsString(loggedIntoAccount));
        } else {
            ctx.status(401);
        }
    }

    /**
     * Handler to post a new message
     * 
     * @param ctx the The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postMessageCreationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler to retrieve all messages
     * 
     * @param ctx the The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.retrieveMessages();
        ctx.json(messages);
    }

    /**
     * Handler to retrieve a message by message id
     * 
     * @param ctx the The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByIdHandler(Context ctx) {
        String message_id = ctx.pathParam("message_id");
        Message messageWithId = messageService.getMessage(message_id);
        if (messageWithId != null) {
            ctx.json(messageWithId);
        } else {
            ctx.json("");
        }
    }

    /**
     * Handler to delete a message with message id
     * 
     * @param ctx the The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageByIdHandler(Context ctx) {
        String message_id = ctx.pathParam("message_id");
        Message deletedMessage = messageService.deleteMessage(message_id);
        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        } else {
            ctx.json("");
        }
    }

    /**
     * Handler to update a message by message id
     * 
     * @param ctx the The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String message_id = ctx.pathParam("message_id");
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(message_id, message.getMessage_text());
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler to retrieve all messages with account id
     * 
     * @param ctx the The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesFromAccountHandler(Context ctx) {
        String account_id = ctx.pathParam("account_id");
        List<Message> messagesFromAccountId = messageService.getAllMessagesFromAccount(account_id);
        if (messagesFromAccountId != null) {
            ctx.json(messagesFromAccountId);
        } else {
            ctx.json("");
        }
    }

}