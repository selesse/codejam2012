import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server implements HttpHandler {

  public static void main(String[] args) {
    HttpServer server;
    try {
      server = HttpServer.create(new InetSocketAddress("localhost", 4444), 0);
      server.createContext("/", new Server());
      server.setExecutor(null); // creates a default executor
      server.start();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void handle(HttpExchange t) throws IOException {
    System.out.println("Got request");

    String wget = "/opt/local/bin/wget http://localhost:8888/codejam2012/mset?report -O report.json";
    String cmd = "curl -Ss -X \"POST\" -H \"Authorization: Basic Y29kZWphbTpBRkxpdGw0TEEyQWQx\" -H \"Content-Type:application/json\" --data-binary @"
        + System.getProperty("user.dir") + "/report.json"
        + " \"https://stage-api.e-signlive.com/aws/rest/services/codejam\"";

    Process p = Runtime.getRuntime().exec(wget);
    
    try {
      p.waitFor();
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
    

    // System.out.println("Running command " + wget);
    // System.out.println("Now I'm running " + cmd);

    String[] curl_cmd = { "/bin/bash", "-c", cmd };
    for (String s : curl_cmd) {
      System.out.println(s);
    }
    p = Runtime.getRuntime().exec(curl_cmd);
    try {
      p.waitFor();
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String ceremonyId = getOutAndErrStream(p);
    System.out.println(ceremonyId);
    
    // URI uri = t.getRequestURI();
    // String q = uri.getRawQuery();
    // String callback = q.substring(q.indexOf("jQuery"), q.indexOf("&",
    // q.indexOf("jQuery")));
    // System.out.println(callback);

    com.sun.net.httpserver.Headers responseHeaders = t.getResponseHeaders();
    responseHeaders.add("Access-Control-Allow-Origin", "http://localhost");
    responseHeaders.add("Content-Type", "application/json");
    t.sendResponseHeaders(200, ceremonyId.getBytes().length);
    
    t.getResponseBody().write(ceremonyId.trim().getBytes());
    t.getResponseBody().flush();
    t.getResponseBody().close();
  }

  private String getOutAndErrStream(Process p) {

    StringBuffer cmd_out = new StringBuffer("");
    if (p != null) {
      BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String buf = "";
      try {
        while ((buf = is.readLine()) != null) {
          cmd_out.append(buf);
          cmd_out.append(System.getProperty("line.separator"));
        }
        is.close();
        is = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((buf = is.readLine()) != null) {
          cmd_out.append(buf);
          cmd_out.append("\n");
        }
        is.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return cmd_out.toString();
  }
}
