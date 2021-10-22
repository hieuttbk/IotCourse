package htpp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import multiServer.ConnectMysqlExample;

public class SimpleHttpServer {
		final static int port = 9999;
	

	public static void main(String[] args) throws Exception {
				
		HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
		System.out.println("httpServer start at port: " + port);
		server.createContext("/v1/temperatures", new MyHandler());
		server.setExecutor(null); // creates a default executor
		
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

		server.start();
	}

	static class MyHandler implements HttpHandler {
		public void handle(HttpExchange httpExchange) throws IOException {
			String requestParamValue = null;
			
			System.out.println(httpExchange.getRequestMethod());
			
			if ("GET".equals(httpExchange.getRequestMethod())) {

				requestParamValue = handleGetRequest(httpExchange);
				handleResponse(httpExchange,requestParamValue); 

			} else if ("POST".equals(httpExchange.getRequestMethod())) {

				handlePostRequest(httpExchange);

			}
			
//			String response = "This is the response";
//			httpExchange.sendResponseHeaders(200, response.length());
//			OutputStream os = httpExchange.getResponseBody();
//			os.write(response.getBytes());
//			os.close();
		}

		private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
			OutputStream outputStream = httpExchange.getResponseBody();
			StringBuilder htmlBuilder = new StringBuilder();
		//	requestParamValue=requestParamValue.replace("\n", "<br>");
			
			htmlBuilder. 
			append("<html>").
			append("<body>").append("<h1>").append(requestParamValue).append("</h1>").
			append("</body>").
			append("</html>");
			
		//	String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString()); // for what !?
			
			String htmlResponse=htmlBuilder.toString();
			
			htmlResponse = requestParamValue;
			System.out.println(htmlResponse);
			
			
			httpExchange.sendResponseHeaders(200, htmlResponse.length());
			OutputStream os = httpExchange.getResponseBody();
			os.write(htmlResponse.getBytes());
			os.close();
			
		}

		private void handlePostRequest(HttpExchange httpExchange) throws IOException {
			// TODO create a db
			System.out.println("Running in handle Post ");
			String address = httpExchange.getRemoteAddress().toString();
			
			
			StringBuilder sb = new StringBuilder();
            InputStream ios = httpExchange.getRequestBody();
            int i;
            while ((i = ios.read()) != -1) {
                sb.append((char) i);
            }         
            String jsonStr = sb.toString();
			
			Object obj = JSONValue.parse(jsonStr);
	        JSONObject jsonObject = (JSONObject) obj; 
	        String name = (String) jsonObject.get("name");
			String value = (String) jsonObject.get("value");
			
			String protocol = "HTTP";
			System.out.println(name + address + protocol + value);
			
			ConnectMysqlExample connectMysqlExample = new ConnectMysqlExample();
			connectMysqlExample.insertDB(name, address, protocol, value);
			
			String htmlResponse = "OK";
			httpExchange.sendResponseHeaders(200, htmlResponse.length());
			OutputStream os = httpExchange.getResponseBody();
			os.write(htmlResponse.getBytes());
			os.close();

		}

		private String handleGetRequest(HttpExchange httpExchange) {
			// TODO return list of temp
			String id = httpExchange.
					getRequestURI().
					toString();
			System.out.println("GET " + id);
			String rt = null;
			if (id!=null) {
				ConnectMysqlExample connectMysqlExample = new ConnectMysqlExample();
				rt = connectMysqlExample.gettDB(id);
				
			}else {
				
			}

			return rt;
		}
	}

}