package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
//import com.ufund.api.ufundapi.model.Cupboard;
import com.ufund.api.ufundapi.model.Need;

/**
 * Defines the interface for Cubuard object persistence
 * 
 * @author Alex Benitez
 */
public interface CupboardDAO {
    /**
     *
     * 
     * @return An {@link Need Need} object
     * 
     * @throws IOException if an issue with underlying storage
     */
    Need addNeed(Need need) throws IOException;

    /**
     * Adds to a list of Need whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return Null
     * 
     * @throws IOException if an issue with underlying storage
     */
    void addNeed(String name, double cost, int quantity, String type) throws IOException;

    /**
     * Adds need with the givin info
     * 
     * @param Info of need to be added
     * 
     * @return null
     * 
     * @throws IOException if an issue with underlying storage
     */
    Need getNeed(String name) throws IOException;

    /**
     * Returns a need that matches a name
     * 
     * @param Name of need 
     * 
     * @return the need that is searched for, Null if need does not exist 
     * 
     * @throws IOException if an issue with underlying storage
     */
    boolean deleteNeed(int id) throws IOException;

    /**
     * deletes a need form the list of Need
     * 
     * @param id of the need
     * 
     * @return true if need was succesfully deleted, False otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */

    Need[] getNeed() throws IOException;

 /**
     * Returns a array for Need
     * 
     * @param Null
     * 
     * @return tarray of all Need in the cuboard
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Need updateNeed (Need need) throws IOException;

    /**
     * Updates a need based on ID
     * @return updated need
     * @throws IOException if underlying storage cannot be accessed
     */

    public Need[] searchNeeds(String Keyword);
    /**
     * Searches the list of Need for Need that contain a keyword
     * @return  Array of Need
     * @throws  IOExeption if underlying storage cannot be accessed
     */

    public Need getNeedById(int id);
    /**
     * Gets a need by their ID
     * @return need
     * @throws IOExeption if under
     */


    public Need[] filterByCost(double cost);
    /**
     * Gets list of needs that are less than or equal to a desired cost
     * @return Array of Needs
     * @throws IOExeption if underlying storafe cannort be accessed
     */

     public Need[] filterByType(String type);
     /**
      * Gets list of needs have the same type
      * @return Array of Needs
      * @throws IOExeption if underlying storafe cannort be accessed
      */
    
      
    //   public Need[] filterByRating(double rating);
      /**
       * Gets list of needs that are greather than or equal to a desired rating
       * @return Array of Needs
       * @throws IOExeption if underlying storafe cannort be accessed
       */
      
    
}
