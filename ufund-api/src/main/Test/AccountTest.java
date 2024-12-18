// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.ufund.api.ufundapi.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class AccountTest {
   public AccountTest() {
   }

   @Test
   public void testCtor() {
      String expected_username = "joeSchmoe";
      String expected_password = "abc123";
      boolean expected_isAdmin = false;
      Account account = new Account(expected_username, expected_password, expected_isAdmin);
      Assertions.assertEquals(expected_username, account.getUsername());
      Assertions.assertEquals(true, account.checkPassword(expected_password));
      Assertions.assertEquals(expected_isAdmin, account.isAdmin());
   }

   @Test
   public void testPassword() {
      String username = "joeMama";
      String password = "ThisIsABadPassword";
      boolean isAdmin = false;
      Account account = new Account(username, password, isAdmin);
      String expected_password = "ThisIsASlightlyBetterPassword";
      account.changePassword(expected_password);
      Assertions.assertEquals(true, account.checkPassword(expected_password));
   }

   @Test
   public void testToString() {
      String username = "Amy_Stake";
      String password = "controller_i_ardly_know_er";
      boolean isAdmin = true;
      String expected_toString = String.format("Account [username=%s, password=%s, isAdmin=%s]", username, password, isAdmin ? "TRUE" : "FALSE");
      Account account = new Account(username, password, isAdmin);
      Assertions.assertEquals(expected_toString, account.toString());
   }
}
