package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

/**
 * Defines the interface for Cubuard object persistence
 * 
 * @author Alex Benitez
 */
public interface FundingBasketDAO {

    /**
     * Adds a need to a user's cart
     * @param username of the owner of the cart
     * @param need the need to add to the cart
     * @return The need added
     * @throws IOException if an issue with underlying storage
    */
    Need addNeed(String username, Need need, int quantity) throws IOException;

    /**
     * Returns a list of needs in the cart of a helper
     * @param username of the owner of the cart
     * @return list of needs. null if username not found
     * @throws IOException if an issue with underlying storage
     */
    Need[] getCart(String username) throws IOException;
   
    /**
     * deletes a need from a user's cart
     * @param username of the owner of the cart
     * @param id of the need
     * @return true if succeeded, false if failed or if username not found
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean removeNeed(String username, int id) throws IOException;

    /**
     * Removes all needs from a user's cart
     * @param username of the owner of the cart
     * @return void
     * @throws IOException if underlying storage cannot be accessed
     */ 
    void removeAll(String username) throws IOException;

    /**
     * Checkouts a user's cart, removing all the items and updating the cupboard to reflect the changes
     * @param username of the owner of the cart
     * @return void
     * @throws IOExeption if underlying storage cannot be accessed
     */
    public void checkout(String username);
    
}
