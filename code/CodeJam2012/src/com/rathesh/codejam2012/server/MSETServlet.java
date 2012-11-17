package com.rathesh.codejam2012.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MSETServlet extends HttpServlet {

  public static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";

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

    }
    out.println(headWithTitle("Hello WWW") + "<BODY>\n" + "<H1>Hello WWW</H1>\n" + "</BODY></HTML>");
  }
  
  protected static void startStockExchange() {
    // TODO in here we need to
    // 0. Create strategies and managers
    // 1. Get the price feed (send 'H' to msExchange)
    // 2. Set the trade booking socket (global)
    // 3. While still receiving prices (not receiving 'C')
    // 4. Update strategies which will update managers, Managers will call
    // sendBuy or Sell
    // 5. Update clock
  }

  public static void sendSell(String name, String type) {
    // TODO in here we need to
    // Send 'S' through trade booking socket
    // The exchange responds with a price, keep note of it for silanis :)
  }

  public static void sendBuy(String name, String type) {
    // TODO in here we need to
    // Send 'B' through trade booking socket
    // The exchange responds with a price, keep note of it for silanis :)

  }

}
