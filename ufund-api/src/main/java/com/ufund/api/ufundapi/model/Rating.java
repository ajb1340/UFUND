package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {
    /**
     * Class used to represent a Need entity
     */
    private static final Logger LOG = Logger.getLogger(Rating.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Rating [stars = %d, description = %s]";
    /**
     * Parameters for a need below
     */
    @JsonProperty("stars") private short stars;
    @JsonProperty("username") private String username;
    @JsonProperty("description") private String description;

    public Rating(@JsonProperty("stars") short stars, @JsonProperty("username") String username, @JsonProperty("description") String description) {
        this.stars = stars;
        this.username = username;
        this.description = description;

    }

    public void setStars(short stars) {
        this.stars = stars;
    }

    public int getStars() {
        return this.stars;
    }

    public String getUsername() {
        return this.username;
    }
}
