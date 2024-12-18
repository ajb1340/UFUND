package com.ufund.api.ufundapi.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ufund.api.ufundapi.persistence.CupboardDAO;
import com.ufund.api.ufundapi.model.Need;


/**
 * Handles the REST API requests for the Hero resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Kyle, 
 */

@RestController
@RequestMapping("cupboard")
public class CupboardController {
    private static final Logger LOG = Logger.getLogger(CupboardController.class.getName());
    private CupboardDAO CupboardDAO;
   
    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param CuboardDao The {@link CuboardDAO cuboard Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public CupboardController(CupboardDAO CupboardDao) {
        this.CupboardDAO = CupboardDao;
    }


 

    /**
     * Responds to the GET request for a Need for the given name
     * 
     * @param id  ID of the need
     * 
     * @return ResponseEntity with created {@link Need need} object and HTTP status of OK<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Need need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<Need> getNeed(@PathVariable Integer id) {
        LOG.info("GET /Cupboard/" + id);
        Need need = CupboardDAO.getNeedById(id);
        if (need != null)
            return new ResponseEntity<Need>(need,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  /**
     * Creates a {@linkplain Need need} with the provided Need object
     * 
     * @param Need - The {@link Need need} to create
     * 
     * @return ResponseEntity with created {@link Need need} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Need need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Need> addNeed(@RequestBody Need need) {
        LOG.info("POST /Need " + need);
        try{
            Need createdNeed = CupboardDAO.addNeed(need);
            return new ResponseEntity<>(createdNeed,HttpStatus.CREATED);
        }catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());

        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    
    /**
     * Deletes a {@linkplain Need Need} with the given ID
     * 
     * @param ID The ID of the {@link Need need} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable Integer id) {
        LOG.info("DELETE /id/" + id);
        try{
            boolean deleted = CupboardDAO.deleteNeed(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
                
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }
     /**
     * Responds to the GET request for all {@linkplain Need Need}
     * 
     * @return ResponseEntity with array of {@link Need Needo} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping("")
    public ResponseEntity<Need[]> getNeed() {
        LOG.info("GET /Need");
        try {
            Need[] NeedArray = CupboardDAO.getNeed();
            List<Need> Need = new ArrayList<>();
            for(Need need: NeedArray){
                Need.add(need);
            }
            return new ResponseEntity(Need,HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

     /**
     * Updates the {@linkplain Need need} with the provided {@linkplain Need need} object, if it exists
     * 
     * @param Need The {@link Hero hero} to update
     * 
     * @return ResponseEntity with updated {@link Need need} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /need " + need);
        try{
            Need updatedNeed = CupboardDAO.updateNeed(need);
            if (updatedNeed!= null){
                return new ResponseEntity<>(updatedNeed, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage(),e);
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }





    /**
     * Responds to the GET request for all {@linkplain Need Need} whose name contains
     * the text in name
     * 
     * @param Cupboard The Cupboard parameter which contains the text used to find the {@link Need Need}
     * 
     * @return ResponseEntity with array of {@link Need Need} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * 
     */
    @GetMapping("/search")
    public ResponseEntity<Need[]> searchNeeds(@RequestParam String keyword) throws IOException {
        LOG.info("GET /cupboard/?keyword=" + keyword);
        Need[] needsArray = CupboardDAO.searchNeeds(keyword);
        List<Need> needs = new ArrayList<>();
        for (Need need : needsArray) {
            needs.add(need);
        }
        return new ResponseEntity<>(needsArray, HttpStatus.OK);  
    }
    

/**
 * Responds to the GET request to filter {@linkplain Need Need} by cost
 * 
 * @param cost The maximum cost of the {@link Need needs} to retrieve
 * @return ResponseEntity with an array of {@link Need Need} objects filtered by cost and HTTP status of OK<br>
 *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
 */
@GetMapping("/filterByCost")
public ResponseEntity<Need[]> filterByCost(@RequestParam double cost) {
    LOG.info("GET /cupboard/filterByCost?cost=" + cost);
    try {
        Need[] filteredNeeds = CupboardDAO.filterByCost(cost);
        return new ResponseEntity<>(filteredNeeds, HttpStatus.OK);
    } catch (Exception e) {
        LOG.log(Level.SEVERE, "Error filtering by cost", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

/**
 * Responds to the GET request to filter {@linkplain Need Need} by type
 * 
 * @param type The type of the {@link Need needs} to retrieve
 * @return ResponseEntity with an array of {@link Need Need} objects filtered by type and HTTP status of OK<br>
 *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
 */
@GetMapping("/filterByType")
public ResponseEntity<Need[]> filterByType(@RequestParam String type) {
    LOG.info("GET /cupboard/filterByType?type=" + type);
    try {
        Need[] filteredNeeds = CupboardDAO.filterByType(type);
        return new ResponseEntity<>(filteredNeeds, HttpStatus.OK);
    } catch (Exception e) {
        LOG.log(Level.SEVERE, "Error filtering by type", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

/**
 * Responds to the GET request to filter {@linkplain Need Need} by rating
 * 
 * @param rating The minimum rating of the {@link Need needs} to retrieve
 * @return ResponseEntity with an array of {@link Need Need} objects filtered by rating and HTTP status of OK<br>
 *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
 */
// @GetMapping("/filterByRating")
// public ResponseEntity<Need[]> filterByRating(@RequestParam double rating) {
//     LOG.info("GET /cupboard/filterByRating?rating=" + rating);
//     try {
//         Need[] filteredNeeds = CupboardDAO.filterByRating(rating);
//         return new ResponseEntity<>(filteredNeeds, HttpStatus.OK);
//     } catch (Exception e) {
//         LOG.log(Level.SEVERE, "Error filtering by rating", e);
//         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//     }
// }
}