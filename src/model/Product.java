package model;

import java.util.ArrayList;

import ui.Shape;
import logic.ProductObserverList;

public class Product {
	private String name = "Vincent";
	private int station;
	private int customerNumber;
	private String orderNumber;

	private float a1;
	private float a2;
	private float b1;
	private float b2;
	private float em1;
	
	private float em2;
	private String overallStatus;
	private long ts_start;
	private long ts_stop;

	// for Database
	private long startTime;
	private long endTime;
	private int materialNumber;
	private ArrayList<OPCDataItem> data = new ArrayList<>();

	public String getId() {
		return orderNumber;
	}

	public Product(ERPData erpData) {
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
		notifyObservers();
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
		ProductObserverList.getInstance().observerList.add(s);
	}

	public void detach(Shape s) {
		ProductObserverList.getInstance().observerList.remove(s);
	}

	public void notifyObservers() {

		for (int i = 0; i < ProductObserverList.getInstance().observerList.size(); i++) {
			Shape shape = ProductObserverList.getInstance().observerList.get(i);
			shape.update(station, orderNumber);
		}
	}
	
	public String getJSONString(){
		String json = "{\"a1\" : \"" + a1 + "\",";
		json = json + "\"name\" : \"" + name + "\",";
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
				json = json + opcDataItem.getValue().toString()+", ";
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
	
	public float getA1() {
		return a1;
	}

	public void setA1(float a1) {
		this.a1 = a1;
	}

	public float getA2() {
		return a2;
	}

	public void setA2(float a2) {
		this.a2 = a2;
	}

	public float getB1() {
		return b1;
	}

	public void setB1(float b1) {
		this.b1 = b1;
	}

	public float getB2() {
		return b2;
	}

	public void setB2(float b2) {
		this.b2 = b2;
	}

	public float getEm1() {
		return em1;
	}

	public void setEm1(float em1) {
		this.em1 = em1;
	}

	public ArrayList<OPCDataItem> getData() {
		return data;
	}

	public void setData(ArrayList<OPCDataItem> data) {
		this.data = data;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public float getEm2() {
		return em2;
	}

	public void setEm2(float em2) {
		this.em2 = em2;
	}

	public String getOverallStatus() {
		return overallStatus;
	}

	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	public long getTs_start() {
		return ts_start;
	}

	public void setTs_start(long ts_start) {
		this.ts_start = ts_start;
	}

	public long getTs_stop() {
		return ts_stop;
	}

	public void setTs_stop(long ts_stop) {
		this.ts_stop = ts_stop;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(int materialNumber) {
		this.materialNumber = materialNumber;
	}


}
