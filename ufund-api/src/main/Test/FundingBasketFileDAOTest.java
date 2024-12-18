// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketFileDAO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class FundingBasketFileDAOTest {
   private FundingBasketFileDAO fundingBasketDAO;
   private Need need12 = new Need(12, "Nerf Gun", 69.0, 20, "Toy");
   private Need need13 = new Need(13, "Updated Water Bottle", 15.0, 5, "Container");
   private Need need14 = new Need(14, "Updated Water Bottle", 10.0, 1, "Container");
   private Need need15 = new Need(15, "Car", 40.0, 2, "Toy");
   private final String testFilePath = "fundingbasket.json";
   private ObjectMapper objectMapper = new ObjectMapper();

   public FundingBasketFileDAOTest() {
   }

   @BeforeEach
   public void setup() throws IOException {
      Need[] initialNeeds = new Need[]{new Need(12, "Nerf Gun", 69.0, 20, "Toy"), new Need(13, "Updated Water Bottle", 15.0, 5, "Container"), new Need(14, "Updated Water Bottle", 10.0, 1, "Container"), new Need(15, "Car", 40.0, 2, "Toy")};
      this.objectMapper.writeValue(new File("fundingbasket.json"), initialNeeds);
      this.fundingBasketDAO = new FundingBasketFileDAO("fundingbasket.json", this.objectMapper);
   }

   @AfterEach
   public void teardown() {
      File file = new File("fundingbasket.json");
      if (file.exists()) {
         file.delete();
      }

   }

   @Test
   public void testNextId() {
      int expectedId = this.fundingBasketDAO.getNextId();
      int actualId = FundingBasketFileDAO.nextId();
      Assertions.assertEquals(expectedId, actualId);
   }

   @Test
   public void testGetNeedArray() {
      ArrayList<Need> expectedNeeds = new ArrayList();
      expectedNeeds.add(this.need12);
      expectedNeeds.add(this.need13);
      expectedNeeds.add(this.need14);
      expectedNeeds.add(this.need15);
      Need[] expected = new Need[expectedNeeds.size()];
      expectedNeeds.toArray(expected);
      Need[] actualNeeds = this.fundingBasketDAO.getNeedArray();
      Assertions.assertArrayEquals(expected, actualNeeds);
   }

   @Test
   public void testGetCart() {
      ArrayList<Need> expectedCart = new ArrayList();
      expectedCart.add(this.need12);
      expectedCart.add(this.need13);
      expectedCart.add(this.need14);
      expectedCart.add(this.need15);
      Need[] expected = new Need[expectedCart.size()];
      expectedCart.toArray(expected);
      Need[] actualCart = this.fundingBasketDAO.getCart();
      Assertions.assertArrayEquals(expected, actualCart);
   }

   @Test
   public void testAddNeed() throws IOException {
      ArrayList<Need> expectedNeeds = new ArrayList();
      expectedNeeds.add(this.need12);
      expectedNeeds.add(this.need13);
      expectedNeeds.add(this.need14);
      expectedNeeds.add(this.need15);
      Need newNeed = new Need(16, "Pirate", 20.0, 5, "Toy");
      expectedNeeds.add(newNeed);
      Need[] expected = new Need[expectedNeeds.size()];
      expectedNeeds.toArray(expected);
      this.fundingBasketDAO.addNeed(newNeed);
      Need[] actualNeeds = this.fundingBasketDAO.getNeedArray();
      Assertions.assertArrayEquals(expected, actualNeeds);
   }

   @Test
   public void testRemoveNeed() throws IOException {
      ArrayList<Need> expectedNeeds = new ArrayList();
      expectedNeeds.add(this.need12);
      expectedNeeds.add(this.need13);
      expectedNeeds.add(this.need14);
      Need[] expected = new Need[expectedNeeds.size()];
      expectedNeeds.toArray(expected);
      this.fundingBasketDAO.removeNeed(15);
      Need[] actualNeeds = this.fundingBasketDAO.getNeedArray();
      Assertions.assertArrayEquals(expected, actualNeeds);
   }

   @Test
   public void testRemoveAll() throws IOException {
      Need[] expectedNeeds = new Need[0];
      this.fundingBasketDAO.removeAll();
      Need[] actualNeeds = this.fundingBasketDAO.getNeedArray();
      Assertions.assertArrayEquals(expectedNeeds, actualNeeds);
   }

   @Test
   public void testGetCost() throws IOException {
      double expectedCost = 134.0;
      double actualCost = this.fundingBasketDAO.getCost();
      Assertions.assertEquals(expectedCost, actualCost);
   }

   @Test
   public void testGetQuantity() throws IOException {
      int expectedQuantity = 28;
      int actualQuantity = this.fundingBasketDAO.getQuantity();
      Assertions.assertEquals(expectedQuantity, actualQuantity);
   }

   @Test
   public void testCheckout() throws IOException {
      Need[] expectedNeeds = new Need[0];
      this.fundingBasketDAO.checkout();
      Need[] actualNeeds = this.fundingBasketDAO.getNeedArray();
      Assertions.assertArrayEquals(expectedNeeds, actualNeeds);
   }
}
