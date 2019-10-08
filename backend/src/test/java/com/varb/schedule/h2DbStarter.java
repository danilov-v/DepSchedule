package com.varb.schedule;

import com.varb.schedule.config.H2Configuration;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class h2DbStarter {

     @Test
     public void tcpServerStarter() throws SQLException, IOException {
          new H2Configuration().tcpServerStart("9092");
          System.out.println("h2 tcpServer started");
          System.out.println("Press Enter key to stop...");
          System.in.read();
          System.exit(0);
     }

     @Test
     public void webServerStarter() throws SQLException, IOException {
          new H2Configuration().webServerStart("8082");
          System.out.println("h2 webServer started");
          System.out.println("Press Enter key to stop...");
          System.in.read();
          System.exit(0);
     }

}