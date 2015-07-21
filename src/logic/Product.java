package logic;

import java.util.ArrayList;

import ui.Shape;
import model.ERPData;
import model.OPCDataItem;

public class Product {
	private int station;
	private int customerNumber;
	private String orderNumber;

	float a1;
	float a2;
	float b1;
	float b2;
	float em1;
	float em2;
	String overallStatus;
	long ts_start;
	long ts_stop;

	// for Database
	private long startTime;
	private long endTime;
	private int materialNumber;
	private ArrayList<OPCDataItem> data = new ArrayList<>();

	ArrayList<Shape> observerList = new ArrayList<Shape>();

	public String getId() {
		return orderNumber;
	}

	Product(ERPData erpData) {
		this.orderNumber = erpData.getOrderNumber();
		this.materialNumber = erpData.getMaterialNumber();
		this.customerNumber = erpData.getCustomerNumber();
		station = 0;
		System.out.println(erpData.getOrderNumber()
				+ " was created with Station " + station);
	}

	public Product() {

	}

	public int getStation() {
		return station;
	}

	public void setStation(int newValue) {
		station = newValue;
	}

	public void addOPCData(OPCDataItem item) {
		data.add(item);
		if (station == 1) {
			startTime = item.getTimestamp();
		}
		if (station == 14) {
			endTime = item.getTimestamp();
		}
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void attach(Shape s) {
		observerList.add(s);
	}

	public void detach(Shape s) {
		observerList.remove(s);
	}

	public void notifyObservers() {
		for (int i = 0; i < observerList.size(); i++) {
			Shape shape = observerList.get(i);
			shape.update();
		}
	}
	
	public String getJSONString(){
		String json = "{\"a1\" : \"" + a1 + "\",";
		json = json + "\"a2\" : \"" + a2 + "\",";
		json = json + "\"b1\" : \"" + b1 + "\",";
		json = json + "\"b2\" : \"" + b2 + "\",";
		json = json + "\"em1\" : \"" + em1 + "\",";
		json = json + "\"em2\" : \"" + em2 + "\",";
		json = json + "\"overallStatus\" : \"" + overallStatus + "\",";
		json = json + "\"ts_start\" : \"" + ts_start + "\",";
		json = json + "\"ts_stop\" : \"" + ts_stop + "\",";
		json = json + "\"startTime\" : \"" + startTime + "\",";
		json = json + "\"endTime\" : \"" + endTime + "\",";
		json = json + "\"materialNumber\" : \"" + materialNumber + "\",";
		json = json + "\"station\" : \"" + a1 + "\",";
		json = json + "\"customerNumber\" : \"" + customerNumber + "\",";
		json = json + "\"orderNumber\" : \"" + orderNumber + "\",";
		json = json + "\"data\" : [";
		
		for (OPCDataItem opcDataItem : data) {
			boolean isLast = data.size() == data.indexOf(opcDataItem) + 1;
			boolean valueIsNumber = false;
			String value = opcDataItem.getItemName();
			if(value.equalsIgnoreCase("Milling Speed") || value.equalsIgnoreCase("Milling Heat") || 
					value.equalsIgnoreCase("Drilling Speed") || value.equalsIgnoreCase("Drilling Heat")){
				valueIsNumber = true;
			}
			json = json + "{\"value\" : ";
			if(valueIsNumber){
				json = json + opcDataItem.getValue().toString();
			}else{
				json = json + "\"" + opcDataItem.getValue().toString() + "\",";
			}
			json = json + "\"timestamp\" : \""+opcDataItem.getTimestamp() + "\",";
			json = json + "\"itemName\" : \""+opcDataItem.getItemName() + "\"}";
			if(!isLast) json = json + ",";
		}
		json = json + "]}";
		System.out.println(json);
		return json;
	}

}
