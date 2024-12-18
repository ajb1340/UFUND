package com.ufund.api.ufundapi.model; 

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Need {
    /**
     * Class used to represent a Need entity
     */
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Need [id=%d, name=%s]";
/**
 * Parameters for a need below
 */
    @JsonProperty("id") private int id;
    @JsonProperty("name") String name;
    @JsonProperty("cost") private double cost;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("type") private String type;
    @JsonProperty("isFulfilled") private boolean isFulfilled;

    public Need(@JsonProperty("id") int id, 
                @JsonProperty("name") String name, 
                @JsonProperty("cost") double cost, 
                @JsonProperty("quantity") int quantity, 
                @JsonProperty("type") String type) {
        /**
         * Creates a New Need taking in a ID,name,cost,quantity,tyoe and fulfilment status
         */
        
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
        this.isFulfilled = false;
    }
    
    /**
     * Getter for the ID of a need
     * @return the needs id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter for a needs ID
     * @param id the ned ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the name of an need
     * @return name of need
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for an needs new name
     * @param name the new name of the need
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the cost of an need
     * @return cost of need
     */
    public double getCost() {
        return this.cost;
    }

   /**
    * Setter for the cost of a need
    * @param cost new cost of the need
    */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Getter for the quantity of a need
     * @return the quantity of the need
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Setter for the quantity of a need
     * @param quantity new quantity of the need
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter for the type of the need
     * @return the type of Need
     */
    public String getType() {
        return this.type;
    }

    /**
     * Setter for the type of a need
     * @param type new type for the ID
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for the fulfilment status of a need
     * @return current fulfilment status
     */
    public boolean getFulfilledStatus() {
        return this.isFulfilled;
    }

    /**
     * Setter for the fulfilment status of a need
     * @param status new status of the need
     */
    public void setFulfilledStatus(boolean status) {
        this.isFulfilled = status;
    }

    /**
     * Converts a need to its string representation
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name);
    }
}