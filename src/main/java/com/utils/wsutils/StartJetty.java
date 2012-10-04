package com.utils.wsutils;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.management.MBeanContainer;



/**
 * Seperate startup class for people that want to run the examples
 * directly.
 */
public class StartJetty {

  /**
   * Main function, starts the jetty server.
   * 
   * @param args
   */
  public static void run (int port) throws Exception {
	  
    Server server = new Server();
    SelectChannelConnector connector = new SelectChannelConnector();
    connector.setPort(port);
    server.addConnector(connector);

    WebAppContext web = new WebAppContext();
    web.setContextPath("/");
    web.setWar("src/main/webapp");
    server.addHandler(web);

    MBeanServer mBeanServer = ManagementFactory
        .getPlatformMBeanServer();
    MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
    server.getContainer().addEventListener(mBeanContainer);
    mBeanContainer.start();

 
      System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
      server.start();
      while (System.in.available() == 0) {
        Thread.sleep(5000);
      }
      server.stop();
      server.join();
   
  }
}
