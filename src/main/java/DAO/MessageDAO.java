package DAO;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Message;
import Util.ConnectionUtil;

// DAO is the class that mediates the transformation of data between the format objects to rows in a database.
public class MessageDAO {
    /**
     * insert new message into the message table
     * 
     * @return newly created message(message_id, posted_by, message_text, time_posted_epoch)
     */
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL Logic
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Write preparedStatement setInt, setString, setLong
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            // Execute preparedStatement
            preparedStatement.executeUpdate();

            // Get the ResultSet
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // If try fails, return null
        return null;
    }

    /**
     * get all messages from the message table
     * 
     * @return all messages
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            // SQL Logic
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute preparedStatement and get the ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Get all attributes
                int message_id = resultSet.getInt("message_id");
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                Long time_posted_epoch = resultSet.getLong("time_posted_epoch");

                // Create message object and add to list
                Message message = new Message(message_id, posted_by, message_text, time_posted_epoch);
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // If try fails, return empty list
        return messages;
    }

    /**
     * get message from the message table with message id
     * 
     * @return message with message id
     */
    public Message getMessageById(int input_message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL Logic
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Write preparedStatement setInt
            preparedStatement.setInt(1, input_message_id);

            // Execute preparedStatement and get the ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Get all attributes
                int message_id = resultSet.getInt("message_id");
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                Long time_posted_epoch = resultSet.getLong("time_posted_epoch");

                // Create message object and return it
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
           }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // If try fails, return null
        return null;
    }

    /**
     * delete message from the message table with message id
     * 
     * @return deleted message
     */
    public Message deleteMessageById(int input_message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Save message to return
            Message savedMessage = getMessageById(input_message_id);

            // SQL Logic
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Write preparedStatement setInt
            preparedStatement.setInt(1, input_message_id);

            // Execute preparedStatement and get the the number of rows that were deleted
            int numOfRowsDeleted = preparedStatement.executeUpdate();
            if (numOfRowsDeleted > 0) return savedMessage;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // If try fails, return null
        return null;
    }

    /**
     * update message in the message table with message id
     * 
     * @return updated message
     */
    public Message updateMessageById(int input_message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL Logic
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Write preparedStatement setString, setInt
            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, input_message_id);

            // Execute preparedStatement and get the number of rows that were deleted
            int numOfRowsUpdated = preparedStatement.executeUpdate();

            if (numOfRowsUpdated > 0) return getMessageById(input_message_id);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // If try fails, return null
        return null;
    }

    /**
     * retrieve all messages in the message table with written by a particular user
     * 
     * @return list of messages
     */
    public List<Message> getAllMessagesFromAccountId(int input_account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            // SQL Logic
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Write preparedStatement setInt
            preparedStatement.setInt(1, input_account_id);

            // Execute preparedStatement and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Get all attributes
                int message_id = resultSet.getInt("message_id");
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                Long time_posted_epoch = resultSet.getLong("time_posted_epoch");

                // Create message object and add to list
                Message message = new Message(message_id, posted_by, message_text, time_posted_epoch);
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // If try fails, return empty messages
        return messages;
    }

}
