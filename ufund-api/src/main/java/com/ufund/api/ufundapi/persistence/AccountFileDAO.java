package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Account;

/**
 * Persistence for storing all accounts
 * 
 * @author Aron Lee
 */
@Component
public class AccountFileDAO implements AccountDAO {

    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());
    ArrayList<Account> accounts;   // Local cache for loading accounts from the Json file
    private ObjectMapper objectMapper;  // Conversion between account objects and Json text
    private String filename;    // Filename to read from and write to

    public AccountFileDAO(@Value("${Accounts.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates an array of {@linkplain Account Account} from the tree map
     * @return  The array of {@link Account Account}, may be empty
     */
    private Account[] getAccountArray() {
        return getAccountArray(null);
    }

    /**
     * Generates an array of {@linkplain Account Account} from the tree map for any
     * {@linkplain Account Account} that contains the text specified by containsText.
     * If containsText is null, the array contains all of the {@linkplain Account Account}
     * in the tree map
     * @return  The array of {@link Account Account}, may be empty
     */
    private Account[] getAccountArray(String containsText) { // if containsText == null, no filter
        ArrayList<Account> accountArrayList = new ArrayList<>();

        for (Account account : accounts) {
            if (containsText == null || account.getUsername().contains(containsText)) {
                accountArrayList.add(account);
            }
        }

        Account[] accountArray = new Account[accountArrayList.size()];
        accountArrayList.toArray(accountArray);
        return accountArray;
    }


    /**
     * Saves the {@linkplain Account Account} from the map into the file as an array of JSON objects
     * @return true if the {@link Account Account} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Account[] accountArray = getAccountArray();
        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),accountArray);
        return true;
    }

    /**
     * Loads {@linkplain Account account} from the JSON file into the account arraylist
     * @return true if the file was read successfully
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        accounts = new ArrayList<>();

        // Deserializes the JSON objects from the file into an array of Account
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Account[] accountArray = objectMapper.readValue(new File(filename),Account[].class);

        // Add each account to the account arraylist
        for (Account account : accountArray) {
            accounts.add(account);
        }
        return true;
    }

    /**
     * Adds an account to the saved list of accounts.
     * @param account Account to add
     * @return True if successful. False otherwise
     * @throws IOException if an issue with underlying storage
     */
    @Override
    public boolean addAccount(Account account) throws IOException {
        if (getAccount(account.getUsername()) == null) {
            synchronized(accounts) {
                accounts.add(account);
                save(); // may throw an IOException
                return true;
            }
        }
        return false;
    }

    /**
     * Creates and adds an account to the saved list of accounts
     * @param username The text to match against
     * @param password The password to the new account
     * @param isAdmin true if the account is an admin, false if it is a helper
     * @return True if successful. False otherwise
     * @throws IOException if an issue with underlying storage
     */
    @Override
    public boolean addAccount(String username, String password, boolean isAdmin) throws IOException {
        if (getAccount(username) == null) {
            synchronized(accounts) {
                accounts.add(new Account(username, password, isAdmin));
                save(); // may throw an IOException
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an account that matches the username
     * @param username of account 
     * @return the account with that username, Null if account does not exist 
     * @throws IOException if an issue with underlying storage
     */
    @Override
    public Account getAccount(String username) throws IOException {
        synchronized(accounts) {
            for (Account account : accounts) {
                if (account.getUsername().equals(username)) return account;
            }
            return null;
        }
    }

    /**
     * Deletes an account from the saved list of accounts
     * @param username of the account
     * @return true if account was succesfully deleted, False otherwise
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public boolean deleteAccount(String username) throws IOException {
        synchronized(accounts) {
            for (Account account : accounts) {
                if (account.getUsername().equals(username)) {
                    accounts.remove(account);
                    return save();
                }
            }
            return false;
        }
    }

    /**
     * Updates a account based on username
     * @return updated account
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public Account updateAccount(Account account) throws IOException {
        synchronized(accounts) {
            for (Account acc : accounts) {
                if (acc.getUsername().equals(account.getUsername())) {
                    accounts.remove(acc);
                    accounts.add(account);
                    save();
                    return account;
                }
            }
            return null;
        }
    }

    


}