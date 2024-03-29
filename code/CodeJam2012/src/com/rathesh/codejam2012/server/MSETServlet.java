package com.rathesh.codejam2012.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.ThreadManager;

/**
 * The server side implementation of the RPC service.
 */
public class MSETServlet extends HttpServlet {

  private static final long serialVersionUID = -6640809899580890620L;

  public static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";
  public static Socket tradeBookingSocket = null;
  public static PrintWriter outTradeBooking = null;
  public static BufferedReader inTradeBooking = null;
  private static Report report = new Report();
  // Time in seconds
  public static int time;
  public static DataDump dataDump;
  public static final int priceFeedPort = 8211;
  public static final int tradeBookingPort = 8212;
  // Determines how many points to keep in server memory
  public static final int WINDOW_SIZE = 32400; // Set to 32400 to turn off the
                                               // effects
  private StockExchange stockExchange;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // content type should probably depend on the parameter passed...
    // unless we have json no matter what
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    if (request.getParameter("go") != null) {
      dataDump = new DataDump();
      time = 0;
      Thread t = ThreadManager.createBackgroundThread(new StockExchange());
      t.start();
    }
    else if (request.getParameter("report") != null) {
      out.println(report.toString());
      out.flush();
    }
    else if (request.getParameter("reportFile") != null) {
      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition", "attachment; filename=report.json");
      out.println(report.toString());
      out.flush();
    }
    else if (request.getParameter("data") != null) {
      synchronized (dataDump) {
        out.println(dataDump);
      }
      out.flush();
    }

    out.close();
  }

  public static boolean sendSell(String name, String type) {
    // Send 'S' through trade booking socket
    // The exchange responds with a price, keep note of it for silanis :)
    outTradeBooking.println('S');
    outTradeBooking.flush();

    StringBuilder line = new StringBuilder();
    try {
      do {
        char c = (char) inTradeBooking.read();
        if (c == 'E') {
          return false;
        }
        line.append(c);
      }
      while (inTradeBooking.ready());
      double price = Double.parseDouble(line.toString());
      Transaction transaction = new Transaction(time, "sell", price, name, type);
      report.add(transaction);
      
      return true;
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }

  }

  public static boolean sendBuy(String name, String type) {
    // Send 'B' through trade booking socket
    // The exchange responds with a price, keep note of it for silanis :)
    outTradeBooking.println('B');
    outTradeBooking.flush();
    StringBuilder line = new StringBuilder();
    try {
      do {
        char c = (char) inTradeBooking.read();
        if (c == 'E') {
          return false;
        }
        line.append(c);
      }
      while (inTradeBooking.ready());
      double price = Double.parseDouble(line.toString());
      Transaction transaction = new Transaction(time, "buy", price, name, type);
      report.add(transaction);
      return true;
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}
