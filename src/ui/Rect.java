package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Rect extends Shape {

	public Rect(int id) {
		super(id);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(50, 50, 50, 50);
	}

	public Dimension getPreferredSize() {
		return new Dimension(100, 100); // appropriate constants
	}
}