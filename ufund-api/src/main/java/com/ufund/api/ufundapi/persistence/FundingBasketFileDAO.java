package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;





@Component
public class FundingBasketFileDAO implements FundingBasketDAO {
    private static final Logger LOG = Logger.getLogger(FundingBasketFileDAO.class.getName());
    ArrayList<FundingBasket> fundingBaskets;   // Provides a local cache of the FundingBasket so that we don't need to read from the file each time    
    private ObjectMapper objectMapper;  // Provides conversion between FundingBasket objects and JSON text format written to the file
    private String filename;    // Filename to read from and write to
    private String accountsFileName; // Filename of the accounts to read from
    private Object cupboardDAO;
   
    /**
     * Creates a Cupboard File Data Access Object
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * @throws IOException when file cannot be accessed or read from
     */
    public FundingBasketFileDAO(@Value("${FundingBaskets.file}") String filename, @Value("${Accounts.file}") String accountsFilename, ObjectMapper objectMapper, CupboardFileDAO cupboardFileDAO) throws IOException {
        this.filename = filename;
        this.accountsFileName = accountsFilename;
        this.objectMapper = objectMapper;
        this.cupboardDAO = cupboardFileDAO;
        load();  // load the Need from the file
    }


    // /**
    //  * Generates an array of {@linkplain Need Need} from the tree map
    //  * using the FundingBasket that matches the username provided
    //  * @param username the username of the owner of the funding basket
    //  * @return  The array of {@link Need Need}, may be empty
    //  */
    // private Need[] getNeedArray(String username) {
    //     return getNeedArray(username, null);
    // }

    // /**
    //  * Generates an array of {@linkplain Need Need} from the tree map for the
    //  * {@linkplain FundingBasket FundingBasket} that matches the username provided
    //  * that contains the text specified by containsText
    //  * If containsText is null, the array contains all of the {@linkplain Need Need}
    //  * in the tree map corresponding to the username
    //  * @param username the username of the owner of the funding basket
    //  * @param containsText the text to filter the needs by
    //  * @return  The array of {@link Need Need}, may be empty. Null if username is not found in the map.
    //  */
    // private Need[] getNeedArray(String username, String containsText) { // if containsText == null, no filter
    //     ArrayList<Need> needArrayList = new ArrayList<>();
    //     if (fundingBaskets.containsKey(username)) {
    //         for (Need need : fundingBaskets.get(username).getCart()) {
    //             if (containsText == null || need.getName().contains(containsText)) {
    //                 needArrayList.add(need);
    //             }
    //         }
    //         Need[] needArray = new Need[needArrayList.size()];
    //         needArrayList.toArray(needArray);
    //         return needArray;
    //     } else return null;
    // }

    /**
     * Saves the {@linkplain FundingBasket FundingBasket} from the map into the file as an array of JSON objects
     * @return true if they were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        FundingBasket[] fundingBasketArray = new FundingBasket[fundingBaskets.size()];
        int i = 0;
        for (FundingBasket fundingBasket : this.fundingBaskets) {
            fundingBasketArray[i++] = fundingBasket;
        }

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), fundingBasketArray);
        return true;
    }

    /**
     * Loads {@linkplain FundingBasket FundingBasket} from the JSON file into the map.
     * Also matches the username found from the JSON with an existing account using accountDAO.
     * @return true if the file was read successfully. false otherwise.
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        fundingBaskets = new ArrayList<>();
        try {
            // AccountFileDAO accountDAO = new AccountFileDAO(accountsFileName, new ObjectMapper());

            // Deserializes the JSON objects from the file into an array of Need
            // readValue will throw an IOException if there's an issue with the file
            // or reading from the file
            FundingBasket[] fundingBasketArray = objectMapper.readValue(new File(filename),FundingBasket[].class);

            // Match each fundingBasket's username with an account
            for (FundingBasket fundingBasket : fundingBasketArray) {
                // fundingBasket.setAccount(accountDAO.getAccount(fundingBasket.getUsername()));
                fundingBaskets.add(fundingBasket);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns a list of needs in the cart of a helper
     * @param username of the owner of the cart
     * @return list of needs. null if username not found
     * @throws IOException if an issue with underlying storage
     */
    @Override
    public Need[] getCart(String username) {
        synchronized(fundingBaskets) {
            for(FundingBasket fundingBasket : this.fundingBaskets) {
                if(fundingBasket.getUsername().equals("username")) {
                    return fundingBasket.getCart();
                }
            }
            return null;
        }
    }

    /**
     * Adds a need to a user's cart
     * @param username of the owner of the cart
     * @param need the need to add to the cart
     * @return The need added
     * @throws IOException if an issue with underlying storage
    */
    @Override
    public Need addNeed(String username, Need need, int quantity) throws IOException {
        synchronized(fundingBaskets) {
            Need newNeed = new Need(need.getId(), need.getName(), need.getCost(), quantity, need.getType());
            for(int i = 0; i < fundingBaskets.size(); i++) {
                if(fundingBaskets.get(i).getUsername().equals(username)) {
                    fundingBaskets.get(i).addNeed(newNeed);
                    save(); // may throw an IOException
                    return newNeed;
                }
            }
        }
        return null;
    }

    /**
     * deletes a need from a user's cart
     * @param username of the owner of the cart
     * @param id of the need
     * @return true if succeeded, false if failed or if username not found
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public boolean removeNeed(String username, int id) throws IOException {
        synchronized(fundingBaskets) {
            for(int i = 0; i < fundingBaskets.size(); i++) {
                if(fundingBaskets.get(i).getUsername().equals(username)) {
                    fundingBaskets.get(i).removeNeed(id);
                    save(); // may throw an IOException
                    return true;
                }
            }
        }
        return false;
    }

   
    /**
     * Removes all needs from a user's cart
     * @param username of the owner of the cart
     * @return void
     * @throws IOException if underlying storage cannot be accessed
     */ 
    @Override
    public void removeAll(String username) throws IOException {
        synchronized(fundingBaskets) {
            for(int i = 0; i < fundingBaskets.size(); i++) {
                if(fundingBaskets.get(i).getUsername().equals("username")) {
                    fundingBaskets.get(i).removeMost();
                    break;
                }
            }            
            save(); // may throw an IOException
        }        throw new UnsupportedOperationException("Unimplemented method 'addNeed'");
    }

    /**
     * Checkouts a user's cart, removing all the items and updating the cupboard to reflect the changes
     * @param username of the owner of the cart
     * @return void
     * @throws IOExeption if underlying storage cannot be accessed
     */
    /**
     * Checkouts a user's cart, removing all the items and updating the cupboard to reflect the changes
     * @param username of the owner of the cart
     * @return void
     * @throws IOExeption if underlying storage cannot be accessed
     */
    @Override
    public void checkout(String username) {
        try {
            for(int i = 0; i < this.fundingBaskets.size(); i++) {
                if(fundingBaskets.get(i).getUsername().equals(username)) {
                    ArrayList<Need> needsToRemove = fundingBaskets.get(i).getNeeds();
                    needsToRemove.removeIf(n -> (n.getId() == -1));
                    for(int j = 0; j < needsToRemove.size(); j++){
                        // if(((CupboardFileDAO) this.cupboardDAO).getNeedById(need.getId())!=null){
                        int id = needsToRemove.get(j).getId();
                        int quantity = needsToRemove.get(j).getQuantity();
                        ((CupboardFileDAO) this.cupboardDAO).reduceQuantity(id, quantity);
                        // }
                    }
                    fundingBaskets.get(i).removeMost();
                    break;
                }
            }   
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("Unimplemented method 'checkout'");
   }
}