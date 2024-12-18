package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Statistics {


/**
 * Model for statistics functions
 * 
 * @author Alex Benitez
 */

    @JsonProperty("totalRevenue") private double totalRevenue;
    @JsonProperty("totalItemsSold") private Integer totalItemsSold;
    

    private static final Logger LOG = Logger.getLogger(Statistics.class.getName());

  

    public Statistics(@JsonProperty("totalRevenue") double totalRevenue, @JsonProperty("totalItemsSold") Integer totalItemsSold) {
        this.totalItemsSold = totalItemsSold;
        this.totalRevenue = totalRevenue;
      
    }
    


    public int getTotalItemsSold(){
        return this.totalItemsSold;

    }
    public double  getTotalRevenue(){
        return this.totalRevenue;
        
    }
    public void addTotalItemsSold(int num){
        this.totalItemsSold += num;

    }
    public void addTotalRevenue(double num){
        this.totalRevenue += num;
        
    }
}
