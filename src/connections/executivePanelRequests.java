package connections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class executivePanelRequests {
	
	private final String URL_BODY = "http://personalchef.ddns.net:3001/";
	private final String ALL_DATA_REQUEST = URL_BODY + "getData";
	private final String FAILURE_LIST_REQUEST = URL_BODY + "failureList";
	private final String TOTAL_ORDER_REQUEST = URL_BODY + "totalOrders";
	private final String TOTAL_FAILURE_REQUEST = URL_BODY + "totalFailures";
	
	public int getTotalOrders(){
		return 1000;
	}
	
	public int getFailures(){
		return 97;
	}

	public void getAllData() {
		 try {
			URL urlObj = new URL(ALL_DATA_REQUEST);
			
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();	 
	 
			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			JsonArray jsonObj = new JsonParser().parse(response.toString()).getAsJsonArray();
			JsonObject first = (JsonObject) jsonObj.get(0);
			System.out.println(jsonObj.toString());
			System.out.println(first.toString());
			String custoNr = first.get("customerNumber").toString();
			System.out.println(custoNr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
