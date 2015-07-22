package ui;

// Imports
import java.awt.*;
import java.util.ArrayList;

//import java.awt.event.*;
import javax.swing.*;
// import javax.swing.border.Border;
import javax.swing.event.*;
import logic.*;

public class UI extends JFrame implements ListSelectionListener {

	// Instance attributes used in this example
	private JPanel topPanel;
	private JPanel drawPanel;
	private JPanel fabricPanel;
	private JList listbox;
	private JScrollPane scrollPane;
	private DefaultListModel model;
	private ArrayList<Shape> shapeList = new ArrayList<>();
	protected ArrayList<Product> listData = new ArrayList<>();

	// Other shapes need to be instanced here as well
	private Shape r1;
	private Shape r2;
	private Shape r3;
	private Shape r4;
	private Shape r5;
	private Shape r6;
	private Shape r7;

	Product currentProduct;

	// Constructor of main frame
	public UI() {
		// Set the frame characteristics
		setTitle("Fabric UI");
		setBounds(400, 400, 900, 300);
		setBackground(Color.RED);

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout(new WrapLayout());
		getContentPane().add(topPanel, BorderLayout.CENTER);

		// Create a new listbox control

		model = new DefaultListModel();
		listbox = new JList(model);
		listbox.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listbox.addListSelectionListener(this);

		scrollPane = new JScrollPane(listbox);
		listbox.setSize(200, 300);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		topPanel.setBorder(BorderFactory.createEmptyBorder());
		topPanel.add(scrollPane);

		model.addElement("Test");

		// adding elements

		drawPanel = new JPanel();
		drawPanel.setLayout(new BorderLayout());
		drawPanel.setBorder(BorderFactory.createEmptyBorder());

		topPanel.add(drawPanel);

		fabricPanel = new JPanel();
		fabricPanel.setLayout(new FlowLayout());
		drawPanel.add(fabricPanel, BorderLayout.CENTER);

		r1 = new Rect(1);
		fabricPanel.add(r1);

		r2 = new Rect(2);
		fabricPanel.add(r2);

		r3 = new Rect(3);
		fabricPanel.add(r3);

		r4 = new Circle(4);
		fabricPanel.add(r4);

		r5 = new Circle(5);
		fabricPanel.add(r5);

		r6 = new Rect(6);
		fabricPanel.add(r6);

		r7 = new Rect(7);
		fabricPanel.add(r7);

		setBounds(400, 400, 900, 300);

		drawPanel.validate();

		Identifier.getInstance().attach(this);
	}

	// // Main entry point for this example
	// public static void main(String args[]) {
	// // Create an instance of the test application
	// UI mainFrame = new UI();
	// mainFrame.setVisible(true);
	// }

	@Override
	public void valueChanged(ListSelectionEvent e) {

		// für jedes Product getCurrentStatus() L1, L2, Milling etc.

		JList src = (JList) e.getSource();
		String selected = src.getSelectedValue().toString();
		System.out.println("Selected product: " + selected);

		for (int i = 0; i < listData.size(); i++) {
			if (listData.get(i).getId().equals(selected)) {
				listData.get(i).getStation();
				registerShapes(listData.get(i));
			}
		}
	}

	// getProduct
	public void update(Product p) {
		if (model.contains(p.getId()) == false) {
			listData.add(p);
			System.out.println(p.getId() + " wurde hinzugefügt.");
			model.addElement(p.getId());
		} else {
			System.out.println(p.getId() + " ist fertig und wurde entfernt.");
			listData.remove(p);
			model.removeElement(p.getId());
		}
	}

	private void registerShapes(Product p) {
		p.attach(r1);
		p.attach(r2);
		p.attach(r3);
		p.attach(r4);
		p.attach(r5);
		p.attach(r6);
		p.attach(r7);

		if (currentProduct != null) {

			currentProduct.detach(r1);
			currentProduct.detach(r2);
			currentProduct.detach(r3);
			currentProduct.detach(r4);
			currentProduct.detach(r5);
			currentProduct.detach(r6);
			currentProduct.detach(r7);

			currentProduct = p;
		}
	}
}