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

}
