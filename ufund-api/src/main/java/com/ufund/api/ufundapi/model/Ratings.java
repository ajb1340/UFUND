package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ratings {
    /**
     * Class used to represent a Need entity
     */
    private static final Logger LOG = Logger.getLogger(Rating.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Ratings [id = %s, ratings = %s]";
    /**
     * Parameters for a need below
     */
    @JsonProperty("id") private int id;
    @JsonProperty("ratings") private ArrayList<Rating> ratings;

    public Ratings(@JsonProperty("id") int id, @JsonProperty("ratings") ArrayList<Rating> ratings) {
        this.id = id;
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public Rating[] getRatings() {
        return (Rating[]) ratings.toArray();
    }
    
    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
    }
}
