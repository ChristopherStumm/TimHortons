package logic;

import java.util.ArrayList;

public class Identifier {
ArrayList<Product> productList = new ArrayList();
	public void createProduct(String id){
		boolean alreadyCreated = false;
		for (int i = 0; i < productList.size(); i++){
			if (productList.get(i).getId().equals(id)){
				alreadyCreated = true;
			}
		}
		if (!alreadyCreated){
		Product product = new Product(id);
		productList.add(product);
		}
	}
	
	public String processEventWithoutBoolean(String itemName){
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
		String id = findOutId(stationId, true);
		return id;
	}
	
	public String processEventWithBoolean(String itemName, boolean finished){
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
		
		String id = findOutId(stationId, finished);
		return id;
	}
	
	
	private String findOutId(int stationOfEvent, boolean finished){
		int index = -1;
		for (int i=0; i < productList.size(); i++){
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
			productList.get(index).setStation(stationOfEvent);
			if (stationOfEvent == 14){
				//Hier Schnittstelle zu Datenbank hin
			}
			return productList.get(index).getId();
		}	else {
			System.out.println("Product could not be identified. Sorry!");
			return null;
		}
			
		}
	
	
}
