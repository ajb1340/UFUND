// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.model.Need;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class NeedTest {
   public NeedTest() {
   }

   @Test
   public void testCtor() {
      int expected_id = 6;
      double expected_cost = 69.99;
      int expected_quantity = 21;
      String expected_type = "Game";
      String expected_name = "Call of Duty";
      Need need = new Need(expected_id, expected_name, expected_cost, expected_quantity, expected_type);
      Assertions.assertEquals(expected_id, need.getId());
      Assertions.assertEquals(expected_cost, need.getCost());
      Assertions.assertEquals(expected_quantity, need.getQuantity());
      Assertions.assertEquals(expected_type, need.getType());
      Assertions.assertEquals(expected_name, need.getName());
   }

   @Test
   public void testName() {
      int id = 6;
      double cost = 69.99;
      int quantity = 21;
      String type = "Game";
      String name = "Call of Duty";
      Need need = new Need(id, name, cost, quantity, type);
      String expected_name = "Game Call of Duty";
      need.setName(expected_name);
      Assertions.assertEquals(expected_name, need.getName());
   }

   @Test
   public void testToString() {
      int id = 6;
      double cost = 69.99;
      int quantity = 21;
      String type = "Game";
      String name = "Call of Duty";
      String expected_string = String.format("Need [id=%d, name=%s]", Integer.valueOf(id), name);
      Need need = new Need(id, name, cost, quantity, type);
      String actual_string = need.toString();
      Assertions.assertEquals(expected_string, actual_string);
   }
}
