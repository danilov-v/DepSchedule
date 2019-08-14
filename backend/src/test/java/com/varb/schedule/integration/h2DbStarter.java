package com.varb.schedule.integration;

import com.varb.schedule.config.H2TcpServerConfiguration;

import java.io.IOException;
import java.sql.SQLException;

public class h2DbStarter {

     public static void main(String[] args) throws SQLException, IOException {
          new H2TcpServerConfiguration("9092").h2TcpServer();
          System.out.println("h2 Server started");
          System.out.println("Press Enter key to stop...");
          System.in.read();
          System.exit(0);
     }

}