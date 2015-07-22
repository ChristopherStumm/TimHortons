package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;
import logic.*;

public abstract class Shape extends JComponent {

	private int shapeID = 0;
	protected Color color = Color.BLACK;
	private int productStation;
	private String productId;

	public Shape(int id) {
		shapeID = id;
	}

	public void setActive() {
		color = Color.RED;
		repaint();
	}

	public void setDeactive() {
		color = Color.BLACK;
		repaint();
	}

	public void update(int station, String id) {
		if (id.equals(productId)){
		this.setDeactive();
		switch (station) {
		case 0:
			// nichts
			break;

		case 1:
			if (shapeID == 1) {
				this.setActive();
			}
			break;

		case 2:
			if (shapeID == 1) {
				this.setActive();
			}
			break;

		case 3:
			if (shapeID == 2) {
				this.setActive();
			}

			break;

		case 4:
			if (shapeID == 2) {
				this.setActive();
			}
			break;

		case 5:
			if (shapeID == 3) {
				this.setActive();
			}
			break;

		case 6:
			if (shapeID == 4) {
				this.setActive();
			}
			break;

		case 7:
			if (shapeID == 4) {
				this.setActive();
			}
			break;

		case 8:
			if (shapeID == 3) {
				this.setActive();
			}
			break;

		case 9:
			if (shapeID == 5) {
				this.setActive();
			}
			break;

		case 10:
			if (shapeID == 6) {
				this.setActive();
			}
			break;

		case 11:
			if (shapeID == 6) {
				this.setActive();
			}
			break;

		case 12:
			if (shapeID == 5) {
				this.setActive();
			}
			break;
		case 13:
			if (shapeID == 7) {
				this.setActive();
			}
			break;

		case 14:
			if (shapeID == 7) {
				this.setActive();
			}
			break;
		}
		}
	}

	public void setProductStation(int station) {
		productStation = station;
	}
	
	public void setProductId(String productId){
		this.productId = productId;
	}
}
