package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Statistics;

public interface StatisticsDAO {

/**
 * Defines the interface for (all) Statistics persistence
 * 
 * @author Alex Benitez
 */

    /**
     * Updates revanue value
     * @param num number to add to current revanue
     * @return updated revanue
     * @throws IOException if an issue with underlying storage
     */
    void updateRevenue(double num) throws IOException;

    /**
     * Updates number of items sold
     * @param num number to add to current number sold
     * @return updated num sold
     * @throws IOException if an issue with underlying storage
     */
    void updateNumSold(int num) throws IOException;
    
 /**
     * gets revanue value
     *
     * @return revanue
     * @throws IOException if an issue with underlying storage
     */
    double getRevenue() throws IOException;

    /**
     * gets the number of items sold
     * 
     * @return updated num sold
     * @throws IOException if an issue with underlying storage
     */
    int getNumSold() throws IOException;


    
}
