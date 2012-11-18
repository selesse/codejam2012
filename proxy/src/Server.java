import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		System.out.println("Got request");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				t.getRequestBody()));
		String json = in.readLine();
		
		//System.("wget http://localhost:8888/codejam2012/mset?report bob.json");

		// Make curl system call
		String cmd = "curl -X \"POST\" -H \"Authorization: Basic Y29kZWphbTpBRkxpdGw0TEEyQWQx\" -H \"Content-Type:application/json\" --data-binary \""
				+ json
				+ "\" \"https://stage-api.e-signlive.com/aws/rest/services/codejam\"";
		BufferedReader fromSilanis = new BufferedReader(new InputStreamReader(
				Runtime.getRuntime().exec(cmd).getInputStream()));
		String line = null;
		while ((line = fromSilanis.readLine()) != null) {
			System.out.println(line);
		}
		/*
		 * String response = "This is the response"; t.sendResponseHeaders(200,
		 * response.length()); OutputStream os = t.getResponseBody();
		 * os.write(response.getBytes()); os.close();
		 */
		int i;
		i=0;
	}
}
