package logic;

import java.util.ArrayList;

import model.ERPData;
import model.OPCDataItem;

public class Product {
	private int station;
	
	private String orderNumber;
	
	//for Database
	private long startTime;
	private long endTime;
	private int materialNumber;
	private ArrayList<OPCDataItem> data = new ArrayList<>();
	
	public String getId() {
		return orderNumber;
	}

	Product(ERPData erpData){
		this.orderNumber = erpData.getOrderNumber();
		this.materialNumber = erpData.getMaterialNumber();
		station = 0;
		System.out.println(erpData.getOrderNumber() + " was created with Station " + station);
	}
	
	public int getStation(){
		return station;
	}
	
	public void setStation(int newValue){
		station = newValue;
	}
	
	public void addOPCData(OPCDataItem item){
		data.add(item);
		if (station == 1){
			startTime = item.getTimestamp();
		}
		if (station == 14){
			endTime = item.getTimestamp();
		}
	}
	
	

}
