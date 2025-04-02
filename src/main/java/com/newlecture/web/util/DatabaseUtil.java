package com.newlecture.web.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {

  public static Connection getConnection() {
    try {
      String dbUrl = System.getProperty("db.url");
      String dbUsername = System.getProperty("db.username");
      String dbPassword = System.getProperty("db.password");
      Class.forName("org.mariadb.jdbc.Driver");

      return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

    } catch (Exception e) {
      e.getStackTrace();
    }

    return null;
  }

}
