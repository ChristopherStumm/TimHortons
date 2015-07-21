package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;
import logic.*;

public abstract class Shape extends JComponent {

	private static int shapeID = 0;

	protected Product prod;
	protected Color color = Color.BLACK;

	public Shape() {
		shapeID++;
		prod = new Product();
		prod.attach(this);
	}

	public void setActive() {
		color = Color.RED;
		repaint();
	}

	public void setDeactive() {
		color = Color.BLACK;
		repaint();
	}

	public void update() {
		// getStatus() // int zurück bei vincent
		if (prod.getStation() == shapeID) {
			this.setActive();
		} else {
			this.setDeactive();
		}
	}

	public int getStation() {
		// return identifier.getStatus(); // oder direkt in update meth im
		// abgleich
		return 0;
	}
}
