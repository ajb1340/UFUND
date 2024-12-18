package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Rating;

/**
 * Defines the interface for Rating object persistence
 * 
 * @author Aron Lee
 */
public interface RatingsDAO {

    /**
     * Adds a rating to a need.
     * @param id the id of the need being added to
     * @param stars number of stars of the rating
     * @param username the username of the helper that left the rating
     * @param description the text description of the rating
     * @return The rating added
     * @throws IOException if an issue with underlying storage
    */
    Rating addRating(int id, short stars, String username, String description) throws IOException;

    /**
     * deletes a rating from a need.
     * @param id the id of the need
     * @param username the username of the helper that left the rating
     * @return true if succeeded, false if failed or if username not found
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean removeRating(String username, int id) throws IOException;

    /**
     * Gets all ratings of a need.
     * @param id the id of the need
     */
    Rating[] getRatings(int id);
}
