// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardFileDAO;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Tag("Persistence-tier")
public class CupboardFileDAOTest {
   @Autowired
   protected CupboardFileDAO cupboardDAO;

   public CupboardFileDAOTest() {
      try {
         this.cupboardDAO = new CupboardFileDAO("../data/needs.json", new ObjectMapper());
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   @Test
   public void testGetNeedById() throws IOException {
      Need newNeed = new Need(1, "testname", 100.0, 5, "testtype");
      this.cupboardDAO.addNeed(newNeed);
      Need need = this.cupboardDAO.getNeedById(1);
      Assertions.assertEquals(1, need.getId());
      Assertions.assertEquals("testname", need.getName());
   }

   @Test
   public void testGetNeed() throws IOException {
      Need[] needs = this.cupboardDAO.getNeed();
      Assertions.assertNotNull(needs);
      Need[] var5 = needs;
      int var4 = needs.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Need need = var5[var3];
         Assertions.assertEquals(Need.class.getName(), need.getClass().getName());
      }

   }

   @Test
   public void testAdd() throws IOException {
      Need need = new Need(99, "testname", 100.0, 5, "testtype");
      Need reponseNeed = this.cupboardDAO.addNeed(need);
      Assertions.assertNotNull(reponseNeed);
      Assertions.assertEquals("testname", reponseNeed.getName());
      Assertions.assertEquals(100.0, reponseNeed.getCost());
   }

   @Test
   public void testDeleteNeed() throws IOException {
      Need need = new Need(99, "testname", 100.0, 5, "testtype");
      this.cupboardDAO.addNeed(need);
      boolean isDeleted = this.cupboardDAO.deleteNeed(need.getId());
      Assertions.assertTrue(isDeleted);
      isDeleted = this.cupboardDAO.deleteNeed(need.getId());
      Assertions.assertFalse(isDeleted);
   }

   @Test
   public void testUpdateNeed() throws IOException {
      Need need = this.cupboardDAO.getNeedById(1);
      double cost = need.getCost();
      int quantity = need.getQuantity();
      need.setQuantity(quantity + 1);
      need.setCost(cost + 1.0);
      Need expected = this.cupboardDAO.updateNeed(need);
      Assertions.assertEquals(quantity + 1, expected.getQuantity());
      Assertions.assertEquals(cost + 1.0, expected.getCost());
   }

   @Test
   public void testSearchNeeds() throws IOException {
      Need[] needs = this.cupboardDAO.searchNeeds("Nerf Gun");
      Need[] var5 = needs;
      int var4 = needs.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Need need = var5[var3];
         Assertions.assertTrue(need.getName().contains("Nerf Gun"));
      }

   }
}
