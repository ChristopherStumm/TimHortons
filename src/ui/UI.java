package ui;

// Imports
import java.awt.*;
import java.util.ArrayList;


//import java.awt.event.*;
import javax.swing.*;
// import javax.swing.border.Border;
import javax.swing.event.*;

import logic.*;
import model.Product;

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
	
	private JLabel l1 = new JLabel("LS 1");
	private JLabel l2 = new JLabel("LS 2");
	private JLabel l3 = new JLabel("LS 3");
	private JLabel l4 = new JLabel("LS 4");
	private JLabel l5 = new JLabel("LS 5");
	private JLabel ms = new JLabel("Milling St.");
	private JLabel ds = new JLabel("Drilling St.");
	

	Product currentProduct;

	// Constructor of main frame
	public UI() {
		// Set the frame characteristics
		setTitle("Fabric UI");
		setBounds(400, 400, 900, 300);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Create a panel to hold all other components
		
		topPanel = new JPanel();
		topPanel.setLayout(null);
		add(topPanel);

		// Create a new listbox control

		model = new DefaultListModel();
		listbox = new JList(model);
		listbox.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listbox.addListSelectionListener(this);

		scrollPane = new JScrollPane(listbox);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		topPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBounds(5, 5, 200, 263);
		topPanel.add(scrollPane);

		// adding elements
		
		r1 = new Rect(1);
		r1.setBounds(210, 75, 100, 100);
		l1.setBounds(210, 65, 60, 80);
		topPanel.add(l1);
		topPanel.add(r1);
		shapeList.add(r1);

		r2 = new Rect(2);
		r2.setBounds(320, 75, 100, 100);
		topPanel.add(r2);
		shapeList.add(r2);

		r3 = new Rect(3);
		r3.setBounds(430, 75, 100, 100);
		topPanel.add(r3);
		shapeList.add(r3);

		r4 = new Circle(4);
		r4.setBounds(430, 155, 100, 100);
		topPanel.add(r4);
		shapeList.add(r4);

		r5 = new Rect(5);
		r5.setBounds(540, 75, 100, 100);
		topPanel.add(r5);
		shapeList.add(r5);

		r6 = new Circle(6);
		r6.setBounds(540, 155, 100, 100);
		topPanel.add(r6);
		shapeList.add(r6);

		r7 = new Rect(7);
		r7.setBounds(650, 75, 100, 100);
		topPanel.add(r7);
		shapeList.add(r7);

		Identifier.getInstance().attach(this);
	}

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
				for (int j = 0; j < shapeList.size(); j++) {
					shapeList.get(j).update(listData.get(i).getStation(), src.getSelectedValue().toString());
					shapeList.get(j).setProductId(src.getSelectedValue().toString());
				}
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
			model.removeElement(p.getId());
			listData.remove(p);
			System.out.println(p.getId() + " ist fertig und wurde entfernt.");
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

		}
		currentProduct = p;
	}
}