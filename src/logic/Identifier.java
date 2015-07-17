package logic;

import java.util.ArrayList;

public class Identifier {
ArrayList<Product> productList = new ArrayList();
	public void createProduct(String id){
		Product product = new Product(id);
		productList.add(product);
		
	}
	
	public String processEventWithoutBoolean(String itemName){
		int stationId;
		switch(itemName){
			case "Milling Speed":
				stationId = 3;
				break;
			case "Milling Heat":
				stationId = 3;
				break;
			case "Drilling Speed":
				stationId=6;
				break;
			case "Drilling Heat":
				stationId = 6;
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
		switch(itemName){
			case "Lichtschranke 1":
				stationId = 0;
				break;
			case "Lichtschranke 2":
				stationId = 1;
				break;
			case "Lichtschranke 3":
				stationId = 2;
				break;
			case "Milling Station":
				stationId = 3;
				break;
			case "Lichtschranke 4":
				stationId = 5;
				break;
			case "Drilling Station":
				stationId = 6;
				break;
			case "Lichtschranke 5":
				stationId = 7;
				break;
			default: 
				stationId = -1;
				break;
		}
		
		String id = findOutId(stationId, finished);
		return id;
	}
	
	
	private String findOutId(int stationOfEvent, boolean finished){
		int index = -1;
		for (int i=0; i < productList.size(); i++){
			if (stationOfEvent != 3 || stationOfEvent != 6){
				if (stationOfEvent==(productList.get(i).getStation()+1) && finished == false){
				index = i;
				} else 
					if (stationOfEvent==productList.get(i).getStation() && finished == true){
						if (index == 7 && finished == true){
							String id = productList.get(i).getId();
							productList.remove(i);
							return id;
						} else {
							return productList.get(i).getId();
						}
					}
				} else {
				if (stationOfEvent==(productList.get(i).getStation()+1)){
					index = i;
					System.out.println("Station 3 or 6 set to " + stationOfEvent);
				} else {
					System.out.println("Product could not be id-fied. Sorry!");
					return null;
				}
			}
		}
		
		
		
		if (index != -1){
			
			productList.get(index).setStation(stationOfEvent);
			return productList.get(index).getId();
		}	else {
			System.out.println("Product could not be identified, because it is null. Sorry!");
			return null;
		}
			
		}
	
	
}
