package logic;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import activemq.QueueConnectionUsingCamel;
import connections.DatabaseConnection;
import model.ERPData;
import model.LogFile;
import model.OPCDataItem;
import model.Product;
import ui.*;

public class Identifier {

	private boolean heatOrSpeed = false;

	private static Identifier instance = null;

	private ArrayList<UI> observerList = new ArrayList<>();

	private boolean occupied = false;

	private QueueConnectionUsingCamel occupyListener;

	Product[] productList = new Product[14];
	
	String lastFinishedProduct = "";
	
	boolean lock = false;

	protected Identifier() {
		// Exists only to defeat instantiation.
	}

	public static Identifier getInstance() {
		if (instance == null) {
			instance = new Identifier();
		}
		return instance;
	}

	public void createProduct(ERPData erpData) {
		boolean alreadyCreated = false;
		int indexOfFirstNull = 0;
		boolean nullFound = false;
		for (int i = 0; i < productList.length; i++) {
			if (productList[i] == null) {
				if (!nullFound) {
					indexOfFirstNull = i;
					nullFound = true;
				}
			} else {
				if (productList[i].getId().equals(erpData.getMaterialNumber())) {
					alreadyCreated = true;
				}
			}
		}
		if (!alreadyCreated) {
			Product product = new Product(erpData);
			productList[indexOfFirstNull] = product;
			notifyObserversCreated(product.getId());
		}
	}

	public String processEvent(OPCDataItem item) {
		int stationId;
		String itemName = item.getItemName();
		boolean productCreated = false;
		
		for(int i = 0; i < productList.length; i++){
			if (productList[i] != null){
				productCreated = true;
			}
		}
		
		while (!productCreated){
			try {
				Thread.sleep(100);
				for(int i = 0; i < productList.length; i++){
					if (productList[i] != null){
						productCreated = true;
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		if (item.getValue() instanceof Boolean) {
			boolean finished = (boolean) item.getValue();

			if (finished) {
				switch (itemName) {
				case "Lichtschranke 1":
					stationId = 2;
					heatOrSpeed = false;
					break;
				case "Lichtschranke 2":
					stationId = 4;
					heatOrSpeed = false;
					break;
				case "Milling Station":
					stationId = 6;
					heatOrSpeed = false;
					break;
				case "Lichtschranke 3":
					stationId = 8;
					heatOrSpeed = false;
					break;
				case "Drilling Station":
					stationId = 10;
					heatOrSpeed = false;
					break;
				case "Lichtschranke 4":
					stationId = 12;
					heatOrSpeed = false;
					break;
				case "Lichtschranke 5":
					stationId = 14;
					heatOrSpeed = false;
					break;
				default:
					stationId = -1;
					break;
				}
			} else {
				switch (itemName) {
				case "Lichtschranke 1":
					stationId = 1;
					heatOrSpeed = false;
					break;
				case "Lichtschranke 2":
					stationId = 3;
					heatOrSpeed = false;
					break;
				case "Lichtschranke 3":
					stationId = 5;
					heatOrSpeed = false;
					break;
				case "Milling Station":
					stationId = 7;
					heatOrSpeed = false;
					break;
				case "Drilling Station":
					stationId = 11;
					heatOrSpeed = false;
					break;
				case "Lichtschranke 4":
					stationId = 9;
					heatOrSpeed = false;
					break;
				case "Lichtschranke 5":
					stationId = 13;
					heatOrSpeed = false;
					break;
				default:
					stationId = -1;
					break;
				}

			}
			String id = findOutId(stationId, finished, item);
			return id;
		} else {
			switch (itemName) {
			case "Milling Speed":
				stationId = 6;
				heatOrSpeed = true;
				break;
			case "Milling Heat":
				stationId = 6;
				heatOrSpeed = true;
				break;
			case "Drilling Speed":
				stationId = 10;
				heatOrSpeed = true;
				break;
			case "Drilling Heat":
				stationId = 10;
				heatOrSpeed = true;
				break;
			default:
				stationId = -1;
				break;
			}
			String id = findOutId(stationId, true, item);
			return id;
		}

	}

	private String findOutId(int stationOfEvent, boolean finished,
			OPCDataItem item) {
		if (item == null){
			System.out.println("OPC Data is null");
		}
		ArrayList<Integer> indexes = new ArrayList<>();
		System.out.println("Station of Event: " + stationOfEvent);
		for (int i = 0; i < productList.length; i++) {
			if (productList[i] != null) {
				System.out.println("Station of Product: "
						+ productList[i].getStation());
				if (stationOfEvent != 6 && stationOfEvent != 10) {
					if (productList[i].getStation() == (stationOfEvent - 1)) {
						indexes.add(i);
					}
				} else {
					if (productList[i].getStation() == stationOfEvent
							&& heatOrSpeed) {
						indexes.add(i);
					} else if (productList[i].getStation() == (stationOfEvent - 1)
							&& !heatOrSpeed) {
						indexes.add(i);
					}
				}
			}

		}
		for (int i = 0; i < indexes.size(); i++){
			if (productList[indexes.get(i)].getStation() != 14) {
				productList[indexes.get(i)].setStation(stationOfEvent);
			}
			productList[indexes.get(i)].notifyObservers();
			productList[indexes.get(i)].addOPCData(item);
			return productList[indexes.get(i)].getId();
		}
		return null;

	}

	public void finishProduct(LogFile logFile) {
		if (!lock){
			lock = true;
		for (int i = 0; i < productList.length; i++) {
			if (productList[i] != null) {
				if (productList[i].getStation() == 14 ) {
					Product product = productList[i];
					String productId = product.getId();
					System.out.println("Product " + product + " will now upload data to database");
					
					// Daten ins Product schreiben
					product.setA1(logFile.getA1());
					product.setA2(logFile.getA2());
					product.setB1(logFile.getB1());
					product.setB2(logFile.getB2());
					product.setEm1(logFile.getEm1());
					product.setEm2(logFile.getEm2());
					product.setOverallStatus(logFile.getOverallStatus());
					product.setTs_start(logFile.getTs_start());
					product.setTs_stop(logFile.getTs_stop());

					// Hier Schnittstelle zu Datenbank hin
					// Zu Gson konvertieren und Chris f�r DB schicken

					// Gson gson = new Gson();
					// String productString = gson.toJson(productList[i]);
					String productString = productList[i].getJSONString();

					DatabaseConnection.saveProductInformation(
							productList[i].getCustomerNumber(), productString);
					System.out.println("ProductId: " + productId);
					

					productList[i] = null;	
									
					notifyObserversDeleted(productId);
				}

			}
		}
		}
		lock = false;
	}

	public void attach(UI ui) {
		observerList.add(ui);
	}

	public void detach(UI ui) {
		observerList.remove(ui);
	}

	public void notifyObserversCreated(String productId) {
		for (int i = 0; i < observerList.size(); i++) {
			observerList.get(i).update(productId);
		}
	}
	
	public void notifyObserversDeleted(String id) {
		for (int i = 0; i < observerList.size(); i++) {
			observerList.get(i).updateDeleted(id);
		}
	}

	public Product getProductById(String id){
		Product p = new Product();
		for (int i=0; i < productList.length; i++){
			if (productList[i] != null){
			if (productList[i].getId().equals(id)){
				p = productList[i];
				return p;
			}
			}
		}
		return p;
	}
	
}