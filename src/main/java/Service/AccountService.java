package Service;

import DAO.AccountDAO;
import Model.Account;

/**
 * The purpose of the service class to to contain "business logic" betweent the web layer (controller) and persistence layer (DAO).
 */
public class AccountService {
    private AccountDAO accountDAO;

    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Use DAO to add account
     * 
     * @param account an account object
     * @return the newly created account if made. Null if does not pass username/password requirements
     */
    public Account addAccount(Account account) {
        /* *
         * Implement constraints here:
         * - username must not be blank
         * - password is at least 4 characters long
         * - username must not already exist in database (this will be handled in the DAO)
        */
        if (account.getUsername() == "" || account.getUsername() == null) return null;
        if (account.getPassword().length() <= 4) return null;

        return accountDAO.insertAccount(account);
    }

    /**
     * Use DAO to login
     * 
     * @param account an account object
     * @return the account if there is one with matching username and password. Null if does not pass username/password
     * requirements
     */
    public Account loginToAccount(Account account) {
        return accountDAO.login(account);
    }
}
