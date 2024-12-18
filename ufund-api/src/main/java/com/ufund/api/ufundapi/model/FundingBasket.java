package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FundingBasket {
    
    @JsonProperty("needs") private ArrayList<Need> needs;
    @JsonProperty("username") private String username;
    // private Account account;
    private static final Logger LOG = Logger.getLogger(FundingBasket.class.getName());
    static final String STRING_FORMAT = "Funding Basket [id=%d, name=%s]";

    // public FundingBasket(Account account) {
    //     this.needs = new ArrayList<>();
    //     this.username = account.getUsername();
    //     this.account = account;
    // }

    public FundingBasket(@JsonProperty ArrayList<Need> needs, @JsonProperty String username) {
        this.needs = needs;
        this.username = username;
        // this.account = new Account("username", "password", false);
    }

    public int getCartSize(){
        return this.needs.size();
    }

    public ArrayList<Need> getNeeds() {
        return this.needs;
    }

    public void removeNeed(int id){
        // if(!(account.isAdmin())){
        // this.needs.removeIf(n -> (n.getId() == id));
        
        int index = -1;
        for(int i = 0; i < this.needs.size(); i++) {
            if (this.needs.get(i).getId() == id){
                index = i;
                break;
            }
        }

        if(index != -1) {
            this.needs.remove(index);
        }
        // }
    }

    public void addNeed(Need need){
        // if(!(account.isAdmin())){
        this.needs.add(need);
        // }
    }

    public Need[] getCart(){
        // if(!(account.isAdmin())){
            return needs.toArray(Need[]::new);
        // }
        // else{
            // return null;
        // }
    }

    public String getUsername() { return username; }

    // public Account getAccount() { return account; }

    // public void setAccount(Account account) { this.account = account; }

    public void removeMost(){
        this.needs.removeAll(this.needs);
        this.needs.add(new Need(-1, "empty", 0, 0, "empty"));
    }

    public void checkout(){
        // if(!(account.isAdmin())){
        removeMost();
        // }
    }
}
