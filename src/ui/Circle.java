package ui;
import java.awt.Dimension;
import java.awt.Graphics;

public class Circle extends Shape {

	public Circle(int id) {
		super(id);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillOval(50, 0, 50, 50);
	}

	public Dimension getPreferredSize() {
		return new Dimension(100, 100); // appropriate constants
	}

}
