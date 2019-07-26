package com.varb.schedule.integration;

import com.varb.schedule.config.H2ServerConfiguration;
import java.io.IOException;
import java.sql.SQLException;

public class h2DbStarter {

     public static void main(String[] args) throws SQLException, IOException {
          new H2ServerConfiguration("9092", null).h2TcpServer();
          System.out.println("h2 Server started");
          System.out.println("Press Enter key to stop...");
          System.in.read();
          System.exit(0);
     }

}