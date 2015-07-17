package logic;

public class Product {
	private int station;
	
	private String id;
	
	public String getId() {
		return id;
	}

	Product(String id){
		this.id = id;
		station = 0;
	}
	
	public int getStation(){
		return station;
	}
	
	public void setStation(int newValue){
		station = newValue;
	}
	

}
