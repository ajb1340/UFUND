package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Account;

/**
 * Defines the interface for (all) Account persistence
 * 
 * @author Aron Lee
 */
public interface AccountDAO {

    /**
     * Adds an account to the saved list of accounts.
     * @param account Account to add
     * @return True if successful. False otherwise
     * @throws IOException if an issue with underlying storage
     */
    boolean addAccount(Account account) throws IOException;

    /**
     * Creates and adds an account to the saved list of accounts
     * @param username The text to match against
     * @param password The password to the new account
     * @param isAdmin true if the account is an admin, false if it is a helper
     * @return true if successful, false otherwise
     * @throws IOException if an issue with underlying storage
     */
    boolean addAccount(String username, String password, boolean isAdmin) throws IOException;

    /**
     * Returns an account that matches the username
     * @param username of account 
     * @return the account with that username, Null if account does not exist 
     * @throws IOException if an issue with underlying storage
     */
    Account getAccount(String username) throws IOException;

    /**
     * Deletes an account from the saved list of accounts
     * @param username of the account
     * @return true if account was succesfully deleted, False otherwise
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteAccount(String username) throws IOException;

    /**
     * Updates a account based on username
     * @return updated account
     * @throws IOException if underlying storage cannot be accessed
     */
    Account updateAccount (Account account) throws IOException;

}
