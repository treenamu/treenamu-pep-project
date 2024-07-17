package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

// DAO is the class that mediates the transformation of data between the format objects to rows in a database.
public class AccountDAO {
    /**
     * insert new account into the account table
     * 
     * @return newly created account(id, username, password)
     */
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL Logic
            String sql = "INSERT INTO account(username, password) VALUES(?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Write preparedStatement setString
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // Execute preparedStatement
            preparedStatement.executeUpdate();

            // Get the ResultSet
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // If try fails, return null
        return null;
    }

    /*
    As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. The request body will contain a JSON representation of an Account, not containing an account_id. In the future, this action may generate a Session token to allow the user to securely use the site. We will not worry about this for now.

    - The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. If successful, the response body should contain a JSON of the account in the response body, including its account_id. The response status should be 200 OK, which is the default.
    - If the login is not successful, the response status should be 401. (Unauthorized)
    */
    public Account login(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL Logic
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Write preparedStatement setString
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
                 
            // Execute preparedStatement and get results
            ResultSet pkeyResultSet = preparedStatement.executeQuery();
            if (pkeyResultSet.next()) {
                // Get all attributes
                int account_id = pkeyResultSet.getInt("account_id");
                String username = pkeyResultSet.getString("username");
                String password = pkeyResultSet.getString("password");

                // Create account object and return it
                return new Account(account_id, username, password);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        // If try fails, return null
        return null;
    }
}
