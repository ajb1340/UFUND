// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ufund.api.ufundapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.persistence.AccountDAO;
import com.ufund.api.ufundapi.persistence.AccountFileDAO;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Tag("Persistence-tier")
public class AccountDAOTest {
   @Autowired
   protected AccountDAO accountDAO;

   public AccountDAOTest() {
      try {
         this.accountDAO = new AccountFileDAO("../data/accounts.json", new ObjectMapper());
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   @Test
   public void testGetAccountByUsername() throws IOException {
      String username = "SumTingWong";
      String expected_password = "passweird";
      boolean expected_isAdmin = false;
      this.accountDAO.deleteAccount(username);
      this.accountDAO.addAccount(username, expected_password, expected_isAdmin);
      Account account = this.accountDAO.getAccount(username);
      Assertions.assertEquals(true, account.checkPassword(expected_password));
      Assertions.assertEquals(expected_isAdmin, account.isAdmin());
      String fake_username = "Nonexistent";
      Account bad_account = this.accountDAO.getAccount(fake_username);

      assert bad_account == null;

   }

   @Test
   public void testUpdateAccount() throws IOException {
      String username = "SumTingWong";
      String password = "passweird";
      boolean isAdmin = false;
      this.accountDAO.deleteAccount(username);
      this.accountDAO.addAccount(username, password, isAdmin);
      String expected_password = "newpass";
      boolean expected_isAdmin = true;
      Account account = new Account(username, expected_password, expected_isAdmin);
      Account updated_account = this.accountDAO.updateAccount(account);
      Assertions.assertEquals(true, this.accountDAO.getAccount(username).checkPassword(expected_password));
      Assertions.assertEquals(expected_isAdmin, this.accountDAO.getAccount(username).isAdmin());

      assert updated_account != null;

      String fake_username = "ImNotAPerson";
      Account fake_account = new Account(fake_username, "password", false);
      Account bad_updated_account = this.accountDAO.updateAccount(fake_account);
      Assertions.assertEquals(true, this.accountDAO.getAccount(username).checkPassword(expected_password));
      Assertions.assertEquals(expected_isAdmin, this.accountDAO.getAccount(username).isAdmin());

      assert bad_updated_account == null;

   }

   @Test
   public void testAddAndDeleteAccount() throws IOException {
      String expected_username = "TesterIArdlyKnowEr";
      String expected_password = "12bucklemyshoe";
      boolean expected_isAdmin = true;
      Account account = new Account(expected_username, expected_password, expected_isAdmin);
      this.accountDAO.addAccount(account);

      assert this.accountDAO.getAccount(expected_username) != null;

      Assertions.assertEquals(true, this.accountDAO.getAccount(expected_username).checkPassword(expected_password));
      Assertions.assertEquals(expected_isAdmin, this.accountDAO.getAccount(expected_username).isAdmin());
      String expected_username2 = "ParameterIArdlyKnowEr";
      this.accountDAO.addAccount(expected_username2, expected_password, expected_isAdmin);

      assert this.accountDAO.getAccount(expected_username2) != null;

      Assertions.assertEquals(true, this.accountDAO.getAccount(expected_username2).checkPassword(expected_password));
      Assertions.assertEquals(expected_isAdmin, this.accountDAO.getAccount(expected_username2).isAdmin());
      boolean result = this.accountDAO.addAccount(account);

      assert !result;

      this.accountDAO.deleteAccount(expected_username);
      this.accountDAO.deleteAccount(expected_username2);

      assert this.accountDAO.getAccount(expected_username) == null;

      assert this.accountDAO.getAccount(expected_username2) == null;

      String fake_username = "FakerIArdlyKnowEr";
      boolean result2 = this.accountDAO.deleteAccount(fake_username);

      assert !result2;

   }
}
