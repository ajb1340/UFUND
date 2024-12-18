package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Cupboard entity
 */
public class Cupboard {
    @JsonProperty("Need") private ArrayList<Need> Need;
    private static final Logger LOG = Logger.getLogger(Cupboard.class.getName());
    static final String STRING_FORMAT = "Cupboard [id=%d, name=%s]";

  /**
   * Creates a cupboard that contains a list of needs
   */
    public Cupboard() {
       
        this.Need = new ArrayList<>();
    }
 /**
  * Method used to add a need to the list of needs
  * @param need the need being added to the list
  */
    public void addNeed(Need need) {
        this.Need.add(need);
    }
      /**
       * method used to create a need
       * @param id the id of the need
       * @param name the name of the need
       * @param cost the cost of the need
       * @param quantity the quantity of the need
       * @param type the type of teh need
       */  
    public void addNeed(int id, String name, double cost, int quantity, String type) {
        Need n = new Need(id, name, cost, quantity, type);
        addNeed(n);
    }
/**
 * Method used to get a need by name
 * @param name the name of the need
 * @return the need if it exists
 */
    public Need getNeed(String name) {
        for (Need n : this.Need) {
            if (n.getType().equals(name)) return n;
        }
        return null;
    }
/**
 * Method used to get a need by their ID
 * @param id the id of the need
 * @return the need if it exists
 */
    public Need getNeedById(int id) {
        for (Need n : this.Need) {
            if (n.getId() == id) return n;
        }
        return null;
    }
/**
 * Searches the list of needs for needs containing a keyword
 * @param keyword used to get certain needs
 * @return a list of needs that have the keyword
 */
    public Need[] searchNeed(String keyword) {
        ArrayList<Need> results = new ArrayList<>();
        for (Need n : this.Need) {
            if (n.getType().contains(keyword)) results.add(n);
        }
    
    
    // Convert the List<Need> to Need[]
    return results.toArray(new Need[0]);
    }
/**
 * Method used eto delete a need
 * @param name name of the need
 * @return true if deleted, false otherwise
 */
    public boolean deleteNeed(String name) {
        for (Need n : this.Need) {
            if (n.getType().equals(name)) Need.remove(n);
            return true;
        }
        return false;
    }

/**
 * FIlter needs by type
 * @param type the type of need the user is looking for
 * @return list of needs
 */
    public Need[] filterByType(String type){
        ArrayList<Need> results = new ArrayList<>();
        for(Need n: this.Need){
            if(n.getType().equals(type)){
                results.add(n);
            }

        }
        return results.toArray(new Need[0]);

    }
    /**
     * Returns all needs with a cost less than or equal to  a given cost
     * @param cost the cost to check
     * @return list of needs
     */
    public Need[] filterByCost(double cost){
        ArrayList<Need> results = new ArrayList<>();
        for(Need n: this.Need){
            if(n.getCost() <= cost){
                results.add(n);
            }

        }
        return results.toArray(new Need[0]);
    }

// /**
//  * Gets al needs with a rating higher than or equalt a given rating
//  * @param rating the wanted rating
//  * @return array of needs
//  */
//     public Need[] filterByRating(double rating){
//         ArrayList<Need> results = new ArrayList<>();
//         for(Need n: this.Need){
//             if(n.getRating() >= rating){
//                 results.add(n);
//             }

//         }
//         return results.toArray(new Need[0]);
//     }

}