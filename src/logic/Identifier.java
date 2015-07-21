package logic;

import java.util.ArrayList;

import com.google.gson.Gson;

import connections.DatabaseConnection;
import model.ERPData;
import model.LogFile;
import model.OPCDataItem;

public class Identifier {
	
	private static Identifier instance = null;

	protected Identifier() {
		// Exists only to defeat instantiation.
	}

	public static Identifier getInstance() {
		if (instance == null) {
			instance = new Identifier();
		}
		return instance;
	}
	
	
ArrayList<Product> productList = new ArrayList<>();
	public void createProduct(ERPData erpData){
		boolean alreadyCreated = false;
		for (int i = 0; i < productList.size(); i++){
			if (productList.get(i).getId().equals(erpData.getMaterialNumber())){
				alreadyCreated = true;
			}
		}
		if (!alreadyCreated){
		Product product = new Product(erpData);
		productList.add(product);
		}
	}
	
	public String processEventWithoutBoolean(String itemName, OPCDataItem item){
		int stationId;
		switch(itemName){
			case "Milling Speed":
				stationId = 6;
				break;
			case "Milling Heat":
				stationId = 6;
				break;
			case "Drilling Speed":
				stationId=10;
				break;
			case "Drilling Heat":
				stationId = 10;
				break;
			default:
				stationId = -1;
				break;
		}
		String id = findOutId(stationId, true, item);
		return id;
	}
	
	public String processEventWithBoolean(String itemName, boolean finished, OPCDataItem item){
		int stationId;
		if (finished){
		switch(itemName){
			case "Lichtschranke 1":
				stationId = 2;
				break;
			case "Lichtschranke 2":
				stationId = 4;
				break;
			case "Milling Station":
				stationId = 6;
				break;
			case "Lichtschranke 3":
				stationId = 8;
				break;
			case "Drilling Station":
				stationId = 10;
				break;
			case "Lichtschranke 4":
				stationId = 12;
				break;
			case "Lichtschranke 5":
				stationId = 14;
				break;
			default: 
				stationId = -1;
				break;
		}
		} else {
			switch(itemName){
			case "Lichtschranke 1":
				stationId = 1;
				break;
			case "Lichtschranke 2":
				stationId = 3;
				break;
			case "Lichtschranke 3":
				stationId = 5;
				break;
			case "Milling Station":
				stationId = 7;
				break;
			case "Drilling Station":
				stationId = 11;
				break;
			case "Lichtschranke 4":
				stationId = 9;
				break;
			case "Lichtschranke 5":
				stationId = 13;
				break;
			default: 
				stationId = -1;
				break;
		}
		}
		
		String id = findOutId(stationId, finished, item);
		return id;
	}
	
	
	private String findOutId(int stationOfEvent, boolean finished, OPCDataItem item){
		int index = -1;
		System.out.println("Station of Event: " + stationOfEvent);
		for (int i=0; i < productList.size(); i++){
			System.out.println("Station of Product: " + productList.get(i).getStation());
			if (stationOfEvent != 6 && stationOfEvent != 10){
			if (productList.get(i).getStation() == (stationOfEvent-1)){
				index = i;
			}
			} else {
				if (productList.get(i).getStation() == stationOfEvent){
					index = i;
				} else if (productList.get(i).getStation() == (stationOfEvent-1)){
					index = i;
				}
			}
			
			
		}
		
		
		
		if (index != -1){
			if (productList.get(index).getStation() != 24){
			productList.get(index).setStation(stationOfEvent);
			}
			productList.get(index).notifyObservers();
			productList.get(index).addOPCData(item);
			System.out.println(productList.size());
			return productList.get(index).getId();
		}	else {
			System.out.println("Product could not be identified. Sorry!");
			return null;
		}
			
		}
	
	public void finishProduct(LogFile logFile){
		System.out.println("Product will now upload data to database");
		System.out.println(productList.size());
		System.err.println(this.toString());
		for (int i=0; i < productList.size(); i++){
			//if (productList.get(i).getStation()==14){
				Product product = productList.get(i);
				System.out.println(product.toString());
				//Daten ins Product schreiben
				product.a1 = logFile.getA1();
				product.a2 = logFile.getA2();
				product.b1 = logFile.getB1();
				product.b2 = logFile.getB2();
				product.em1 = logFile.getEm1();
				product.em2 = logFile.getEm2();
				product.overallStatus = logFile.getOverallStatus();
				product.ts_start = logFile.getTs_start();
				product.ts_stop = logFile.getTs_stop();
				
				//Hier Schnittstelle zu Datenbank hin
				//Zu Gson konvertieren und Chris für DB schicken
				Gson dbGson = new Gson();
				
				String productString = dbGson.toJson(productList.get(i));
			
				DatabaseConnection.saveProductInformation(productList.get(i).getCustomerNumber(), productString);
				
				//productList.remove(i);
			}
		//}
	}
	
}
