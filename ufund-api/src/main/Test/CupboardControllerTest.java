// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.controller.CupboardController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class CupboardControllerTest {
   private CupboardController cupboardController;
   private CupboardDAO mockCupboardDAO;

   public CupboardControllerTest() {
   }

   @BeforeEach
   public void setupCupboardController() {
      this.mockCupboardDAO = (CupboardDAO)Mockito.mock(CupboardDAO.class);
      this.cupboardController = new CupboardController(this.mockCupboardDAO);
   }

   @Test
   public void testGetNeed() throws IOException {
      Need need = new Need(1, "Food", 10.0, 2, "Food");
      Mockito.when(this.mockCupboardDAO.getNeedById(1)).thenReturn(need);
      ResponseEntity<Need> response = this.cupboardController.getNeed(1);
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
      Assertions.assertEquals(need, response.getBody());
   }

   @Test
   public void testGetNeedNotFound() throws IOException {
      Mockito.when(this.mockCupboardDAO.getNeedById(1)).thenReturn((Object)null);
      ResponseEntity<Need> response = this.cupboardController.getNeed(1);
      Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
   }

   @Test
   public void testGetNeedHandleException() throws IOException {
      int id = 1;
      ((CupboardDAO)Mockito.doThrow(new Throwable[]{new IOException()}).when(this.mockCupboardDAO)).getNeedById(id);
      ResponseEntity<Need> response = this.cupboardController.getNeed(Integer.valueOf(id));
      Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
   }

   @Test
   public void testAddNeed() throws IOException {
      Need need = new Need(1, "Clothes", 5.0, 3, "Clothes");
      Mockito.when(this.mockCupboardDAO.addNeed(need)).thenReturn(need);
      ResponseEntity<Need> response = this.cupboardController.addNeed(need);
      Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
      Assertions.assertEquals(need, response.getBody());
   }

   @Test
   public void testAddNeedHandleException() throws IOException {
      Need need = new Need(1, "Books", 5.0, 2, "Book");
      ((CupboardDAO)Mockito.doThrow(new Throwable[]{new IOException()}).when(this.mockCupboardDAO)).addNeed(need);
      ResponseEntity<Need> response = this.cupboardController.addNeed(need);
      Assertions.assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
   }

   @Test
   public void testDeleteNeed() throws IOException {
      Mockito.when(this.mockCupboardDAO.deleteNeed(1)).thenReturn(true);
      ResponseEntity<Need> response = this.cupboardController.deleteNeed(1);
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
   }

   @Test
   public void testDeleteNeedNotFound() throws IOException {
      Mockito.when(this.mockCupboardDAO.deleteNeed(1)).thenReturn(false);
      ResponseEntity<Need> response = this.cupboardController.deleteNeed(1);
      Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
   }

   @Test
   public void testDeleteNeedHandleException() throws IOException {
      ((CupboardDAO)Mockito.doThrow(new Throwable[]{new IOException()}).when(this.mockCupboardDAO)).deleteNeed(1);
      ResponseEntity<Need> response = this.cupboardController.deleteNeed(1);
      Assertions.assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
   }

   @Test
   public void testUpdateNeed() throws IOException {
      Need need = new Need(1, "Food", 5.0, 10, "Food");
      Mockito.when(this.mockCupboardDAO.updateNeed(need)).thenReturn(need);
      ResponseEntity<Need> response = this.cupboardController.updateNeed(need);
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
      Assertions.assertEquals(need, response.getBody());
   }

   @Test
   public void testUpdateNeedNotFound() throws IOException {
      Need need = new Need(1, "Food", 5.0, 10, "Food");
      Mockito.when(this.mockCupboardDAO.updateNeed(need)).thenReturn((Object)null);
      ResponseEntity<Need> response = this.cupboardController.updateNeed(need);
      Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
   }

   @Test
   public void testUpdateNeedHandleException() throws IOException {
      Need need = new Need(1, "Food", 5.0, 10, "Food");
      ((CupboardDAO)Mockito.doThrow(new Throwable[]{new IOException()}).when(this.mockCupboardDAO)).updateNeed(need);
      ResponseEntity<Need> response = this.cupboardController.updateNeed(need);
      Assertions.assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
   }

   @Test
   public void testGetNeeds() throws IOException {
      List<Need> needs = new ArrayList();
      needs.add(new Need(1, "Food", 5.0, 10, "Food"));
      needs.add(new Need(2, "Clothes", 5.0, 2, "Clothes"));
      Need[] needsArray = (Need[])needs.toArray(new Need[needs.size()]);
      Mockito.when(this.mockCupboardDAO.getNeed()).thenReturn(needsArray);
      ResponseEntity<Need[]> response = this.cupboardController.getNeed();
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
      Assertions.assertEquals(needs, response.getBody());
   }

   @Test
   public void testGetNeedsHandleException() throws IOException {
      ((CupboardDAO)Mockito.doThrow(new Throwable[]{new IOException()}).when(this.mockCupboardDAO)).getNeed();
      ResponseEntity<Need[]> response = this.cupboardController.getNeed();
      Assertions.assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
   }

   @Test
   public void testSearchNeeds() throws IOException {
      String keyword = "Food";
      Need[] needs = new Need[]{new Need(1, "Food", 5.0, 10, "Food"), new Need(2, "Clothes", 5.0, 2, "Clothes")};
      Mockito.when(this.mockCupboardDAO.searchNeeds(keyword)).thenReturn(needs);
      ResponseEntity<Need[]> response = this.cupboardController.searchNeeds(keyword);
      Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
      Assertions.assertEquals(needs, response.getBody());
   }
}
