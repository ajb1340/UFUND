package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Rating;
import com.ufund.api.ufundapi.model.Ratings;

@Component
public class RatingsFileDAO implements RatingsDAO {

    private static final Logger LOG = Logger.getLogger(RatingsFileDAO.class.getName());
    ArrayList<Ratings> ratingsList;   // Provides a local cache of the FundingBasket so that we don't need to read from the file each time    
    private ObjectMapper objectMapper;  // Provides conversion between FundingBasket objects and JSON text format written to the file
    private String filename;    // Filename to read from and write to
    private String accountsFileName; // Filename of the accounts to read from

    public RatingsFileDAO(@Value("${Ratings.file}") String filename, @Value("${Accounts.file}") String accountsFilename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.accountsFileName = accountsFilename;
        this.objectMapper = objectMapper;
        load();  // load the Ratings objects from the file
    }

    /**
     * Saves the ratings from the map into the file as a map of arrays of JSON objects to strings
     * @return true if they were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Ratings[] ratings = (Ratings[]) ratingsList.toArray();
        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), ratings);
        return true;
    }

    /**
     * Loads Ratings objects from the JSON file into the map.
     * @return true if the file was read successfully. false otherwise.
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        Ratings[] ratings = objectMapper.readValue(new File(filename), Ratings[].class);
        ratingsList = new ArrayList<>();
        ratingsList.addAll(Arrays.asList(ratings));
        return false;
    }

    private Ratings getRatingsObject(int id) {
        for (Ratings r : ratingsList) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    @Override
    public Rating addRating(int id, short stars, String username, String description) throws IOException {
        Rating rating = new Rating(stars, username, description);
        Ratings ratingsGroup = getRatingsObject(id);
        if (ratingsGroup != null) {
            ratingsGroup.addRating(rating);
            return rating;
        }
        return null;
    }

    @Override
    public boolean removeRating(String username, int id) throws IOException {
        Ratings ratingsGroup = getRatingsObject(id);
        if (ratingsGroup != null) {
            for (Rating r : ratingsGroup.getRatings()) {
                if (r.getUsername().equals(username)) {
                    ratingsGroup.removeRating(r);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Rating[] getRatings(int id) {
        Ratings ratingsGroup = getRatingsObject(id);
        if (ratingsGroup != null) {
            return ratingsGroup.getRatings();
        }
        return null;
    }
    
}
