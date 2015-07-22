package logic;

import java.util.ArrayList;

import ui.Shape;

public class ProductObserverList {
	private static ProductObserverList instance = null;
	
	public static ProductObserverList getInstance() {
		if (instance == null) {
			instance = new ProductObserverList();
		}
		return instance;
	}
	
	public ArrayList<Shape> observerList = new ArrayList<Shape>();
	

}
