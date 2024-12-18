package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import com.ufund.api.ufundapi.persistence.StatisticsDAO;
import com.ufund.api.ufundapi.persistence.StatisticsFileDAO;

@RestController
@RequestMapping("basket")
public class FundingBasketController {
    
private static final Logger LOG = Logger.getLogger(FundingBasketController.class.getName());
    private FundingBasketDAO FundingBasketDAO;
    
    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param FundingBasketDao The {@link FundingBasketDAO cuboard Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public FundingBasketController(FundingBasketDAO FundingBasketDao) {
        this.FundingBasketDAO = FundingBasketDao;
     
    }

    /**
     *Checkouts the cart
     * @return ResponseEntity with created {@link Need need} object and HTTP status of OK<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Need need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *
     */
    @PutMapping("checkout")
    public ResponseEntity<Object> checkout(@RequestBody String username) {
        LOG.info("/checkout " + username);
        FundingBasketDAO.checkout(username); 
       
        return new ResponseEntity<>(HttpStatus.CREATED);
        // try{
        //     FundingBasketDAO.checkout(username);            
        //     return new ResponseEntity<>(HttpStatus.CREATED);
        // }catch (IOException e){
        //     LOG.log(Level.SEVERE, e.getLocalizedMessage());
        // }

        // return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
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
    public ResponseEntity<Need> addNeed(@RequestBody Need[] body) {
        LOG.info("POST /Need " + body[1]);
        try{
            String username = body[0].getName();
            Need need = body[1];
            int quantity = body[0].getQuantity(); // quantity to add to basket
            Need createdNeed = FundingBasketDAO.addNeed(username, need, quantity);
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
    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Need> removeNeed(@PathVariable String username, @PathVariable Integer id) {
        LOG.info("DELETE /id/" + id);
        try{
            boolean deleted = FundingBasketDAO.removeNeed(username, id);
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


    //  /**
    //  * Responds to the GET request for all {@linkplain Need Need}
    //  * 
    //  * @return ResponseEntity with array of {@link Need Needo} objects (may be empty) and
    //  * HTTP status of OK<br>
    //  * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    //  */
    // @SuppressWarnings({ "unchecked", "rawtypes" })
    // @GetMapping("")
    // public ResponseEntity<Need[]> getCart(@RequestBody String username) {
    //     LOG.info("GET /Need");
    //     try {
    //         Need[] NeedArray = FundingBasketDAO.getCart(username);
    //         List<Need> Need = new ArrayList<>();
    //         for(Need need: NeedArray){
    //             Need.add(need);
    //         }
    //         return new ResponseEntity(Need,HttpStatus.OK);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }

    //     return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    // }


    
     /**
     * Updates the {@linkplain Need need} with the provided {@linkplain Need need} object, if it exists
     * 
     * @param Need The {@link Hero hero} to update
     * @return 
     * 
     * @return ResponseEntity with updated {@link Need need} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Object> removeAll(@RequestBody String username) {
        LOG.info("REMOVE");
        
        try {
            FundingBasketDAO.removeAll(username);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
        
    }

    /**
     * Responds to the GER request for the cost of the fundingbasket
     * @param null
     * @return ResponseEntity with integet for the cost
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * 
     */
    /*
    @GetMapping("/cost")
    public ResponseEntity<Integer> getCost() throws IOException {
        LOG.info("GET /FundingBasket/Cost" );
        int cost = FundingBasketDAO.getCost();
    
        return new ResponseEntity<>(cost, HttpStatus.OK);  
    }
    */

    /**
     * Responds to the GER request for the cost of the fundingbasket
     * @param null
     * @return ResponseEntity with integet for the cost
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     */
    /*
    @GetMapping("/quantity")
    public ResponseEntity<Integer> getQuanEntity() throws IOException {
        LOG.info("GET /FundingBasket/quantity" );
        int quantity = FundingBasketDAO.getQuantity();
    
        return new ResponseEntity<>(quantity, HttpStatus.OK);  
    }
    */
}
