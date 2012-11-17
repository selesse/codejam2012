/**
 * 
 */
package com.rathesh.codejam2012.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.google.common.collect.Lists;
import com.rathesh.codejam2012.server.strategies.EMAStrategy;
import com.rathesh.codejam2012.server.strategies.LWMAStrategy;
import com.rathesh.codejam2012.server.strategies.SMAStrategy;
import com.rathesh.codejam2012.server.strategies.Strategy;
import com.rathesh.codejam2012.server.strategies.TMAStrategy;

/**
 * @author Alex Bourgeois
 *
 */
public class StockExchange implements Runnable {

  @Override
  public void run() {
    startStockExchange();    
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
      priceSocket = new Socket("localhost", MSETServlet.priceFeedPort);
      MSETServlet.tradeBookingSocket = new Socket("localhost", MSETServlet.tradeBookingPort);

      // Get streams
      out = new PrintWriter(priceSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(priceSocket.getInputStream()));
      MSETServlet.outTradeBooking = new PrintWriter(MSETServlet.tradeBookingSocket.getOutputStream(), true);
      MSETServlet.inTradeBooking = new BufferedReader(
          new InputStreamReader(MSETServlet.tradeBookingSocket.getInputStream()));

      // 2. Start price feed with 'H'
      out.println('H');
      out.flush();
      // 3. While still receiving prices (not receiving 'C')
      String token = "";
      char c;
      MSETServlet.dataDump = new DataDump();
      List<Double> prices = Lists.newArrayList();

      while ((c = (char) in.read()) != 'C') {
        while (c != '|') {
          token += c;
          c = (char) in.read();
        }
        double price = Double.parseDouble(token);
        prices.add(price);
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

        MSETServlet.dataDump.setPrices(prices);
        MSETServlet.dataDump.setSmaSlow(SMASlow.getAverages());
        MSETServlet.dataDump.setSmaFast(SMAFast.getAverages());
        MSETServlet.dataDump.setLwmaSlow(LWMASlow.getAverages());
        MSETServlet.dataDump.setLwmaFast(LWMAFast.getAverages());
        MSETServlet.dataDump.setEmaSlow(EMASlow.getAverages());
        MSETServlet.dataDump.setEmaFast(EMAFast.getAverages());
        MSETServlet.dataDump.setTmaSlow(TMASlow.getAverages());
        MSETServlet.dataDump.setTmaFast(TMAFast.getAverages());
      }
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
