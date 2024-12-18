// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.controller.FundingBasketController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class FundingBasketControllerTest {
   private FundingBasketController basketController;
   private FundingBasketDAO mockBasketDAO;

   public FundingBasketControllerTest() {
   }

   @BeforeEach
   public void setupCupboardController() {
      this.mockBasketDAO = (FundingBasketDAO)Mockito.mock(FundingBasketDAO.class);
      this.basketController = new FundingBasketController(this.mockBasketDAO);
   }

   @Test
   public void testCheckout() throws IOException {
      Need need = new Need(1, "Food", 10.0, 2, "Food");
      this.basketController.addNeed(need);
      ResponseEntity<Object> response = this.basketController.checkout();
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
   }

   @Test
   public void testAddNeed() throws IOException {
      Need need = new Need(1, "Food", 10.0, 2, "Food");
      Mockito.when(this.mockBasketDAO.addNeed(need)).thenReturn(need);
      ResponseEntity<Need> response = this.basketController.addNeed(need);
      Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
      Assertions.assertEquals(need, response.getBody());
   }

   @Test
   public void testRemoveNeed() throws IOException {
      Need need = new Need(1, "Food", 10.0, 2, "Food");
      this.basketController.addNeed(need);
      Mockito.when(this.mockBasketDAO.removeNeed(1)).thenReturn(true);
      ResponseEntity<Need> response = this.basketController.removeNeed(1);
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
   }

   @Test
   public void testGetCart() throws IOException {
      Need need = new Need(1, "Food", 10.0, 2, "Food");
      this.basketController.addNeed(need);
      Need[] needs = new Need[1];
      Mockito.when(this.mockBasketDAO.getCart()).thenReturn(needs);
      ResponseEntity<Need[]> response = this.basketController.getCart();
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
      Assertions.assertEquals(needs, response.getBody());
   }

   @Test
   public void testRemoveAll() throws IOException {
      Need need = new Need(1, "Food", 10.0, 2, "Food");
      this.basketController.addNeed(need);
      ResponseEntity<Object> response = this.basketController.removeAll();
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
   }

   @Test
   public void testGetCost() throws IOException {
      Need need = new Need(1, "Food", 10.0, 2, "Food");
      this.basketController.addNeed(need);
      double totalCost = need.getCost();
      Mockito.when(this.mockBasketDAO.getCost()).thenReturn(totalCost);
      ResponseEntity<Double> response = this.basketController.getCost();
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
      Assertions.assertEquals(10.0, (Double)response.getBody());
   }

   @Test
   public void testGetQuantity() throws IOException {
      Need need = new Need(1, "Food", 10.0, 2, "Food");
      this.basketController.addNeed(need);
      int totalQuantity = need.getQuantity();
      Mockito.when(this.mockBasketDAO.getQuantity()).thenReturn(totalQuantity);
      ResponseEntity<Integer> response = this.basketController.getQuantity();
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
      Assertions.assertEquals(2, (Integer)response.getBody());
   }
}
