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
	private ArrayList<String> idList = new ArrayList<>();

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
	

	String currentProductId;

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
		l1.setBounds(272, 20, 60, 80);
		topPanel.add(l1);
		topPanel.add(r1);
		shapeList.add(r1);

		r2 = new Rect(2);
		r2.setBounds(320, 75, 100, 100);
		l2.setBounds(382, 20, 60, 80);
		topPanel.add(l2);
		topPanel.add(r2);
		shapeList.add(r2);

		r3 = new Rect(3);
		r3.setBounds(430, 75, 100, 100);
		l3.setBounds(492, 20, 60, 80);
		topPanel.add(l3);
		topPanel.add(r3);
		shapeList.add(r3);

		r4 = new Circle(4);
		r4.setBounds(430, 155, 100, 100);
		ms.setBounds(478, 187, 60, 80);
		topPanel.add(ms);
		topPanel.add(r4);
		shapeList.add(r4);

		r5 = new Rect(5);
		r5.setBounds(540, 75, 100, 100);
		l4.setBounds(602, 20, 60, 80);
		topPanel.add(l4);
		topPanel.add(r5);
		shapeList.add(r5);

		r6 = new Circle(6);
		r6.setBounds(540, 155, 100, 100);
		ds.setBounds(588, 187, 60, 80);
		topPanel.add(ds);
		topPanel.add(r6);
		shapeList.add(r6);

		r7 = new Rect(7);
		r7.setBounds(650, 75, 100, 100);
		l5.setBounds(712, 20, 60, 80);
		topPanel.add(l5);
		topPanel.add(r7);
		shapeList.add(r7);

		Identifier.getInstance().attach(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		// f�r jedes Product getCurrentStatus() L1, L2, Milling etc.

		JList src = (JList) e.getSource();
		String selected = src.getSelectedValue().toString();
		System.out.println("Selected product: " + selected);

		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).equals(selected)) {
				registerShapes(Identifier.getInstance().getProductById(idList.get(i)));
				for (int j = 0; j < shapeList.size(); j++) {
					shapeList.get(j).update(Identifier.getInstance().getProductById(idList.get(i)).getStation(), src.getSelectedValue().toString());
					shapeList.get(j).setProductId(src.getSelectedValue().toString());
				}
			}
		}
	}

	// getProduct
	public void update(String id) {
		if (!model.contains(id)) {
			idList.add(id);
			System.out.println(id + " wurde hinzugef�gt.");
			model.addElement(id);
		}
	}
	
	public void updateDeleted(String id){
		if (model.contains(id)) {
		model.removeElement(id);
		for (int i=0; i < idList.size(); i++){
			if(idList.equals(id)){
				idList.remove(i);
			}
		}
		validate();
		if (currentProductId == id) {

			currentProductId = null;

		}
		System.out.println(id + " ist fertig und wurde entfernt.");
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

		if (currentProductId != null) {
			
			Product currentProduct = Identifier.getInstance().getProductById(currentProductId);

			currentProduct.detach(r1);
			currentProduct.detach(r2);
			currentProduct.detach(r3);
			currentProduct.detach(r4);
			currentProduct.detach(r5);
			currentProduct.detach(r6);
			currentProduct.detach(r7);

		}
		currentProductId = p.getId();
	}
}