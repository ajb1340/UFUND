package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;





@Component
public class CupboardFileDAO implements CupboardDAO {
    private static final Logger LOG = Logger.getLogger(CupboardFileDAO.class.getName());
    Map<Integer,Need> cupboard;   // Provides a local cache of the Need 
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Need
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new ID
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Cupboard File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CupboardFileDAO(@Value("${Cupboard.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.cupboard = new TreeMap<>();
        load();  // load the Need from the file
    }

    // // 0 parameter constructor for bean
    // public CupboardFileDAO() {
    //     System.out.println("testing cupboardfiledao");
    // }

    /**
     * Generates the next id for a new {@linkplain Need need}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Need Need} from the tree map
     * 
     * @return  The array of {@link Need Need}, may be empty
     */
    private Need[] getNeedArray() {
        return getNeedArray(null);
    }

    /**
     * Generates an array of {@linkplain Need Need} from the tree map for any
     * {@linkplain Need Need} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Need Need}
     * in the tree map
     * 
     * @return  The array of {@link Need Need}, may be empty
     */
    private Need[] getNeedArray(String containsText) { // if containsText == null, no filter
        ArrayList<Need> needArrayList = new ArrayList<>();

        for (Need need : cupboard.values()) {
            if (containsText == null || need.getName().contains(containsText)) {
                needArrayList.add(need);
            }
        }

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    /**
     * Saves the {@linkplain Need Need} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Need need} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Need[] needArray = getNeedArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),needArray);
        return true;
    }

    /**
     * Loads {@linkplain Need need} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        cupboard = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of Need
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Need[] NeedArray = objectMapper.readValue(new File(filename),Need[].class);

        // Add each need to the tree map and keep track of the greatest id
        for (Need need : NeedArray) {
            cupboard.put(need.getId(),need);
            if (need.getId() > nextId)
                nextId = need.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
     * Gets a need
     */
    @Override
    public Need[] getNeed() {
        synchronized(cupboard) {
            return getNeedArray();
        }
    }

    /**
    ** {@inheritDoc}
    Searches for a need based on a keyword
     */
    @Override
    public Need[] searchNeeds(String keyword) {
        synchronized(cupboard) {
            return getNeedArray(keyword);
        }
    }

    /** 
    ** {@inheritDoc}
    Gets an need by ID
     */
    @Override
    public Need getNeedById(int id) {
        synchronized(cupboard) {
            if (cupboard.containsKey(id)) // gets Need based on id 
                return cupboard.get(id);
            else
                return null; // null if ID isn't find 
        }
    }

    /**
    ** {@inheritDoc}
    Adds a need
     */
    @Override
    public Need addNeed(Need need) throws IOException {
        synchronized(cupboard) {
            // We create a new cupboard object because the id field is immutable
            // and we need to assign the next unique id
            Need newNeed = new Need(nextId(),need.getName(),need.getCost(),need.getQuantity(),need.getType());
            cupboard.put(newNeed.getId(),newNeed);
            save(); // may throw an IOException
            return newNeed;
        }
    }

    /**
    ** {@inheritDoc}
    Updates a need
     */
    @Override
    public Need updateNeed(Need need) throws IOException {
        synchronized(cupboard) {
            if (cupboard.containsKey(need.getId()) == false)
                return null;  // Need does not exist

            cupboard.put(need.getId(), need);
            save(); // may throw an IOException
            return need;
        }
    }

    /*
    public Need addRatingToNeed(Need need, int stars) throws IOException {
        synchronized (cupboard) {
            if(cupboard.containsKey(need.getId())) {
                Rating rating = new Rating(stars);
                need.addRating(rating);
                cupboard.put(need.getId(), need);
                save();
                return need;
            }

            return null;
        }
    }

    
    public boolean deleteRatingFromNeed(Need need, int stars) throws IOException {
        synchronized (cupboard) {
            if(cupboard.containsKey(need.getId())) {
                need.removeRating(stars);
                cupboard.put(need.getId(), need);
                save();
                return true;
            }

            return false;
        }
    }
    */

    /**
    ** {@inheritDoc}
    Deletes a need by ID
     */

    @Override
    public boolean deleteNeed(int id) throws IOException {
        synchronized(cupboard) {
            if (cupboard.containsKey(id)) {
                cupboard.remove(id);
                return save();
            }
            else
                return false;
        }
    }


    public boolean reduceQuantity(int id, int quantity) throws IOException {
        synchronized(cupboard) {
            if (cupboard.containsKey(id)) {
                int newQuantity = cupboard.get(id).getQuantity() - quantity;
                cupboard.get(id).setQuantity(newQuantity);
                return save();
            }
            else
                return false;
        }
    }

   
/**
 * Adds a need need with all parameters given
 */
    @Override
    public void addNeed(String name, double cost, int quantity, String type) throws IOException {
        synchronized(cupboard) {
            Need newNeed = new Need(nextId(),name,cost,quantity,type);
            cupboard.put(newNeed.getId(),newNeed);
            save(); // may throw an IOException
        }        throw new UnsupportedOperationException("Unimplemented method 'addNeed'");
    }
/**
 * Gets a need by string name
 */
    @Override
    public Need getNeed(String name) throws IOException {
        synchronized(cupboard) {
            if (cupboard.containsKey(name))
                return cupboard.get(name);
            else
                return null;
        }
    }




    @Override
    public Need[] filterByCost(double cost) {
        synchronized (cupboard) {
            ArrayList<Need> result = new ArrayList<>();
            for (Need need : cupboard.values()) {
                if (need.getCost() <= cost) {
                    result.add(need);
                }
            }
            return result.toArray(new Need[0]);
        }
    }

    @Override
    public Need[] filterByType(String type) {
        synchronized (cupboard) {
            ArrayList<Need> result = new ArrayList<>();
            for (Need need : cupboard.values()) {
                if (need.getType().equals(type)) {
                    result.add(need);
                }
            }
            return result.toArray(new Need[0]);
        }
    }

    // @Override
    // public Need[] filterByRating(double rating) {
    //     synchronized (cupboard) {
    //         ArrayList<Need> result = new ArrayList<>();
    //         for (Need need : cupboard.values()) {
    //             if (need.getRating() >= rating) { // assuming Need class has a getRating() method
    //                 result.add(need);
    //             }
    //         }
    //         return result.toArray(new Need[0]);
    //     }
    // }
}
