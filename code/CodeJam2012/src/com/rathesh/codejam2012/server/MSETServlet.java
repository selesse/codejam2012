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
  private Socket tradeBookingSocket = null;
  private static PrintWriter outTradeBooking = null;
  private static BufferedReader inTradeBooking = null;
  private static List<ReportData> transactions = new ArrayList<ReportData>();
  private static int time;
  private static DataDump dataDump = new DataDump();
  private final int priceFeedPort = 8211;
  private final int tradeBookingPort = 8212;
  
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
      startStockExchange();
    }
    else if (request.getParameter("report") != null) {
      // TODO
    }
    else if (request.getParameter("data") != null) {
      out.println(dataDump);
      out.flush();
    }
  }

  private void startStockExchange() {
    Socket priceSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    // 0. Create strategies and managers
    int slowN = 20, fastN = 5;
    Strategy SMASlow = new SMAStrategy(slowN, false);
    Strategy SMAFast = new SMAStrategy(fastN, true);
    Strategy LWMASlow = new LWMAStrategy(slowN, false);
    Strategy LWMAFast = new LWMAStrategy(fastN, true);
    Strategy EMASlow = new EMAStrategy(slowN, false);
    Strategy EMAFast = new EMAStrategy(fastN, true);
    Strategy TMASlow = new TMAStrategy(slowN, false);
    Strategy TMAFast = new TMAStrategy(fastN, true);

    try {
      // Set sockets
      priceSocket = new Socket("localhost", priceFeedPort);
      tradeBookingSocket = new Socket("localhost", tradeBookingPort);

      // Get streams
      out = new PrintWriter(priceSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(priceSocket.getInputStream()));
      outTradeBooking = new PrintWriter(tradeBookingSocket.getOutputStream(), true);
      inTradeBooking = new BufferedReader(
          new InputStreamReader(tradeBookingSocket.getInputStream()));

      // 2. Start price feed with 'H'
      out.println('H');
      out.flush();
      // 3. While still receiving prices (not receiving 'C')
      String token = "";
      char c;

      while ((c = (char) in.read()) != 'C') {
        while (c != '|') {
          token += c;
          c = (char) in.read();
        }
        double price = Double.parseDouble(token);
        // 4. Update strategies which will update managers, Managers will call
        // sendBuy or Sell
        SMASlow.update(price);
        SMAFast.update(price);
        LWMASlow.update(price);
        LWMAFast.update(price);
        EMASlow.update(price);
        EMAFast.update(price);
        TMASlow.update(price);
        TMAFast.update(price);
        // 5. Update clock
        token = "";
        // Ignore the delimiter
        in.read();
      }
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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
      ReportData transaction = new ReportData(time, "SELL", price, name, type);
      transactions.add(transaction);
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
      ReportData transaction = new ReportData(time, "BUY", price, name, type);
      transactions.add(transaction);
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }

}
