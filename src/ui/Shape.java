package ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

public abstract class Shape extends JComponent {

	private static int shapeID = 0;

	// protected Identifier identifier;
	protected Color color = Color.BLACK;

	public Shape() {
		shapeID++;
		// identifier = new Identifier();
		// identifier.attach(this);
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
		if (getStation() == shapeID) {
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
