package com.rathesh.codejam2012.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.rathesh.codejam2012.server.strategies.EMAStrategy;
import com.rathesh.codejam2012.server.strategies.LWMAStrategy;
import com.rathesh.codejam2012.server.strategies.SMAStrategy;
import com.rathesh.codejam2012.server.strategies.Strategy;
import com.rathesh.codejam2012.server.strategies.TMAStrategy;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MSETServlet extends HttpServlet {

  public static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";
  public static Socket tradeBookingSocket = null;
  public static PrintWriter outTradeBooking = null;
  public static BufferedReader inTradeBooking = null;
 private static Report report = new Report();
  private static int time;
  public static DataDump dataDump = new DataDump();
  public static final int priceFeedPort = 8211;
  public static final int tradeBookingPort = 8212;
  
  public DataDump getData() {
    return dataDump;
  }

  public static String headWithTitle(String title) {
    return (DOCTYPE + "\n" + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // content type should probably depend on the parameter passed...
    // unless we have json no matter what
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    if (request.getParameter("go") != null) {
      Thread t = new Thread(new StockExchange());
      t.start();
    }
    else if (request.getParameter("report") != null) {
      // TODO
    }
    else if (request.getParameter("data") != null) {
      out.println(dataDump);
      out.flush();
    }
  }

  public static void sendSell(String name, String type) {
    // TODO in here we need to
    // Send 'S' through trade booking socket
    // The exchange responds with a price, keep note of it for silanis :)
    outTradeBooking.println('S');
    outTradeBooking.flush();
    
    String line;
    try {
      line = inTradeBooking.readLine();
      double price = Double.parseDouble(line);
      Transaction transaction = new Transaction(time, "SELL", price, name, type);
      report.add(transaction);
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
   
    
  }

  public static void sendBuy(String name, String type) {
    // TODO in here we need to
    // Send 'B' through trade booking socket
    // The exchange responds with a price, keep note of it for silanis :)
    outTradeBooking.println('B');
    outTradeBooking.flush();
    String line;
    try {
      line = inTradeBooking.readLine();
      double price = Double.parseDouble(line);
      Transaction transaction = new Transaction(time, "BUY", price, name, type);
      report.add(transaction);
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }

}
