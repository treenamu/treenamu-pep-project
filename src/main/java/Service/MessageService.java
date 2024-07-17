package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

/*
 * The purpose of the service class to to contain "business logic" betweent the web layer (controller) and persistence layer (DAO).
 */
public class MessageService {
    private MessageDAO messageDAO = new MessageDAO();

    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     */
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Use DAO to create a message
     * 
     * @param message a message object
     * @return the newly created message if made
     */
    public Message createMessage(Message message) {
        /**
         * Implement constraints here:
         * - message_text must not be blank
        */
        if (message.getMessage_text() == null || message.getMessage_text() == "") return null;

        return messageDAO.insertMessage(message);
    }

    /**
     * Use DAO to retreive all messages
     * 
     * @return list of all messages
     */
    public List<Message> retrieveMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Use DAO to retreive a message
     * 
     * @return message with message id passed
     */
    public Message getMessage(String message_id_string) {
        /**
         * Implement constraints here:
         * - message_id_string must not be blank
        */
        if (message_id_string == null || message_id_string == "") return null;
        
        int message_id = Integer.parseInt(message_id_string);
        return messageDAO.getMessageById(message_id);
    }

    /**
     * Use DAO to delete a message
     * 
     * @return deleted message
     */
    public Message deleteMessage(String message_id_string) {
        /**
         * Implement constraints here:
         * - message_text must not be blank
        */
        if (message_id_string == null || message_id_string == "") return null;

        int message_id = Integer.parseInt(message_id_string);
        return messageDAO.deleteMessageById(message_id);
    }

    /**
     * Use DAO to update a message
     * 
     * @return updated message
     */
    public Message updateMessage(String message_id_string, String message_text) {
        /**
         * Implement constraints here:
         * - message_id_string must not be blank
         * - message_text must not be blank or over 255 characters
        */
        if (message_id_string == null || message_id_string == "") return null;
        if (message_text == null || message_text == "" || message_text.length() > 255) return null;

        int message_id = Integer.parseInt(message_id_string);
        return messageDAO.updateMessageById(message_id, message_text);
    }

    /**
     * Use DAO to retrieve all messages from specific account
     * 
     * @return list of messages from specific account
     */
    public List<Message> getAllMessagesFromAccount(String account_id_string) {
        if (account_id_string == null || account_id_string == "") return null;

        int account_id = Integer.parseInt(account_id_string);
        return messageDAO.getAllMessagesFromAccountId(account_id);
    }

}
