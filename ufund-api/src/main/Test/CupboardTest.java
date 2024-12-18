// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.model.Cupboard;
import com.ufund.api.ufundapi.model.Need;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class CupboardTest {
   public CupboardTest() {
   }

   @Test
   public void testCtor() {
      Cupboard cupboard = new Cupboard();
      Assertions.assertNotNull(cupboard);
   }

   @Test
   public void testAddNeed() {
      Need need1 = new Need(1, "Cool Toy", 14.99, 3, "Donation");
      Need need2 = new Need(2, "Cute Toy", 10.99, 5, "Donation");
      Need need3 = new Need(3, "Big Toy", 17.99, 2, "Donation");
      ArrayList<Need> expectedCupboard = new ArrayList();
      expectedCupboard.add(need1);
      expectedCupboard.add(need2);
      expectedCupboard.add(need3);
      Cupboard cupboard = new Cupboard();
      cupboard.addNeed(need1);
      cupboard.addNeed(need2);
      cupboard.addNeed(need3);
      Assertions.assertEquals(expectedCupboard.get(0), cupboard.getNeedById(1));
      Assertions.assertEquals(expectedCupboard.get(1), cupboard.getNeedById(2));
      Assertions.assertEquals(expectedCupboard.get(2), cupboard.getNeedById(3));
      Need need4 = new Need(4, "Tiny Toy", 5.99, 3, "Donation");
      expectedCupboard.add(need4);
      cupboard.addNeed(4, "Tiny Toy", 5.99, 3, "Donation");
      Assertions.assertEquals(expectedCupboard.get(3), cupboard.getNeedById(4));
   }

   @Test
   public void testGetNeed() {
      Need need1 = new Need(1, "Cool Toy", 14.99, 3, "Donation");
      Need need2 = new Need(2, "Cute Toy", 10.99, 5, "Donation");
      Need need3 = new Need(3, "Big Toy", 17.99, 2, "Donation");
      ArrayList<Need> expectedCupboard = new ArrayList();
      expectedCupboard.add(need1);
      expectedCupboard.add(need2);
      expectedCupboard.add(need3);
      Cupboard cupboard = new Cupboard();
      cupboard.addNeed(need1);
      cupboard.addNeed(need2);
      cupboard.addNeed(need3);
      Assertions.assertEquals(expectedCupboard.get(0), cupboard.getNeed("Cool Toy"));
      Assertions.assertEquals(expectedCupboard.get(1), cupboard.getNeed("Cute Toy"));
      Assertions.assertEquals(expectedCupboard.get(2), cupboard.getNeed("Big Toy"));
   }

   @Test
   public void testSearchNeed() {
      Need need1 = new Need(1, "Cool Toy", 14.99, 3, "Donation");
      Need need2 = new Need(2, "Cute Toy", 10.99, 5, "Donation");
      Need need3 = new Need(3, "Big Toy", 17.99, 2, "Donation");
      ArrayList<Need> expectedCupboard = new ArrayList();
      expectedCupboard.add(need1);
      expectedCupboard.add(need2);
      expectedCupboard.add(need3);
      Cupboard cupboard = new Cupboard();
      cupboard.addNeed(need1);
      cupboard.addNeed(need2);
      cupboard.addNeed(need3);
      Need[] coolToys = cupboard.searchNeed("Cool");
      Assertions.assertEquals(expectedCupboard.get(0), coolToys[0]);
      Assertions.assertEquals(1, coolToys.length);
      Need[] cuteToys = cupboard.searchNeed("Cute");
      Assertions.assertEquals(expectedCupboard.get(1), cuteToys[0]);
      Assertions.assertEquals(1, cuteToys.length);
      Need[] bigToys = cupboard.searchNeed("Big");
      Assertions.assertEquals(expectedCupboard.get(2), bigToys[0]);
      Assertions.assertEquals(1, bigToys.length);
   }

   @Test
   public void testDeleteNeed() {
      Need need1 = new Need(1, "Cool Toy", 14.99, 3, "Donation");
      Need need2 = new Need(2, "Cute Toy", 10.99, 5, "Donation");
      Need need3 = new Need(3, "Big Toy", 17.99, 2, "Donation");
      ArrayList<Need> expectedCupboard = new ArrayList();
      expectedCupboard.add(need1);
      expectedCupboard.add(need2);
      expectedCupboard.add(need3);
      Cupboard cupboard = new Cupboard();
      cupboard.addNeed(need1);
      cupboard.addNeed(need2);
      cupboard.addNeed(need3);
      Assertions.assertEquals(expectedCupboard.get(0), cupboard.getNeedById(1));
      Assertions.assertEquals(expectedCupboard.get(1), cupboard.getNeedById(2));
      Assertions.assertEquals(expectedCupboard.get(2), cupboard.getNeedById(3));
      expectedCupboard.remove(need1);
      cupboard.deleteNeed("Cool Toy");
      Need[] coolToys = cupboard.searchNeed("Cool");
      Assertions.assertEquals(0, coolToys.length);
      expectedCupboard.remove(need2);
      cupboard.deleteNeed("Cute Toy");
      Need[] cuteToys = cupboard.searchNeed("Cute");
      Assertions.assertEquals(0, cuteToys.length);
      expectedCupboard.remove(need3);
      cupboard.deleteNeed("Big Toy");
      Need[] bigToys = cupboard.searchNeed("Big");
      Assertions.assertEquals(0, bigToys.length);
      Need[] allToys = cupboard.searchNeed("Toy");
      Assertions.assertEquals(0, allToys.length);
   }
}
