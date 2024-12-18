// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class FundingBasketTest {
   public FundingBasketTest() {
   }

   @Test
   public void testCtor() {
      FundingBasket basket = new FundingBasket();
      Assertions.assertEquals(0, basket.getfundingBasketSize());
      Assertions.assertEquals(0.0, basket.getCost());
      Assertions.assertEquals(0, basket.getQuantity());
   }

   @Test
   public void testAddNeed() {
      FundingBasket basket = new FundingBasket();
      Need need = new Need(1, "Ball", 20.0, 2, "toy");
      basket.addNeed(need);
      Assertions.assertEquals(1, basket.getfundingBasketSize());
      Assertions.assertEquals(20.0, basket.getCost());
      Assertions.assertEquals(2, basket.getQuantity());
      Assertions.assertTrue(basket.getCart().contains(need));
   }

   @Test
   public void testRemoveNeed() {
      FundingBasket basket = new FundingBasket();
      Need need1 = new Need(1, "Ball", 20.0, 2, "toy");
      Need need2 = new Need(2, "Bike", 100.0, 1, "vehicle");
      basket.addNeed(need1);
      basket.addNeed(need2);
      basket.removeNeed(1);
      Assertions.assertEquals(1, basket.getfundingBasketSize());
      Assertions.assertEquals(100.0, basket.getCost());
      Assertions.assertEquals(1, basket.getQuantity());
      Assertions.assertTrue(!basket.getCart().contains(need1));
      Assertions.assertTrue(basket.getCart().contains(need2));
   }

   @Test
   public void testRemoveAll() {
      FundingBasket basket = new FundingBasket();
      Need need1 = new Need(1, "Ball", 20.0, 2, "toy");
      Need need2 = new Need(2, "Bike", 100.0, 1, "vehicle");
      basket.addNeed(need1);
      basket.addNeed(need2);
      basket.removeAll();
      Assertions.assertEquals(0.0, basket.getCost());
      Assertions.assertEquals(0, basket.getQuantity());
   }

   @Test
   public void testCheckout() {
      Account account = new Account("username", "password", false);
      FundingBasket basket = new FundingBasket(account);
      Need need1 = new Need(1, "Ball", 20.0, 2, "toy");
      basket.addNeed(need1);
      basket.checkout();
      Assertions.assertEquals(0.0, basket.getCost());
      Assertions.assertEquals(0, basket.getQuantity());
   }

   @Test
   public void testGetCost() {
      FundingBasket basket = new FundingBasket();
      Need need1 = new Need(1, "Ball", 20.0, 2, "toy");
      basket.addNeed(need1);
      double totalCost = basket.getCost();
      Assertions.assertEquals(20.0, totalCost);
   }

   @Test
   public void testGetQuantity() {
      FundingBasket basket = new FundingBasket();
      Need need1 = new Need(1, "Ball", 20.0, 2, "toy");
      basket.addNeed(need1);
      int quantity = basket.getQuantity();
      Assertions.assertEquals(2, quantity);
   }
}
