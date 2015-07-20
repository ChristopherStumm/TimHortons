package ui;
// Imports
import java.awt.*;
import java.util.ArrayList;

//import java.awt.event.*;
import javax.swing.*;
// import javax.swing.border.Border;
import javax.swing.event.*;

public class UI extends JFrame implements ListSelectionListener {

	// Instance attributes used in this example
	private JPanel topPanel;
	private JPanel drawPanel;
	private JPanel fabricPanel;
	private JList listbox;
	private JScrollPane scrollPane;
	private DefaultListModel model;
	
	protected ArrayList<String> listData;
	
//	= { "Product 1", "Product 2", "Product 3",
//			"Product 4", "Product 1", "Product 2", "Product 3",
//			"Product 4", "Product 1", "Product 2", "Product 3",
//			"Product 4", "Product 1", "Product 2", "Product 3", "Product 4" };

	// Other shapes need to be instanced here as well
	Rect r1;

	// Constructor of main frame
	public UI() {
		// Set the frame characteristics
		setTitle("Fabric UI");
		setBounds(400, 400, 900, 300);
		setBackground(Color.RED);

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.WEST);
		
		// Create a new listbox control

		model = new DefaultListModel();
		listbox = new JList(model);
		listbox.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listbox.addListSelectionListener(this);
		scrollPane = new JScrollPane(listbox);
		topPanel.add(scrollPane, BorderLayout.WEST);

		// adding elements

		for (int i = 0; i < listData.size(); i++) {
			model.addElement(listData.get(i));
		}

		drawPanel = new JPanel();
		drawPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		getContentPane().add(drawPanel, BorderLayout.CENTER);

		fabricPanel = new JPanel();
		fabricPanel.setLayout(new FlowLayout());
		drawPanel.add(fabricPanel, BorderLayout.CENTER);

		r1 = new Rect();
		fabricPanel.add(r1);

		Rect r2 = new Rect();
		fabricPanel.add(r2);

		Rect r3 = new Rect();
		fabricPanel.add(r3);

		Shape r4 = new Circle();
		fabricPanel.add(r4);

		Shape r5 = new Circle();
		fabricPanel.add(r5);

		Rect r6 = new Rect();
		fabricPanel.add(r6);

		Rect r7 = new Rect();
		fabricPanel.add(r7);

		System.out.println(r1);

		drawPanel.validate();
	}

	// Main entry point for this example
	public static void main(String args[]) {
		// Create an instance of the test application
		UI mainFrame = new UI();
		mainFrame.setVisible(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		// für jedes Product getCurrentStatus() L1, L2, Milling etc.

		r1.setActive();
		repaint();

		int index = listbox.getSelectedIndex();
		System.out.println(model.getElementAt(index));
		// switch to product that should be shown
	}
	
	
	// getProduct
	public void update(){
		
		for(int i = 0; i < listData.size(); i++){
			if(getProduct().equals(listData.get(i))){
				listData.set(i, null);
			} else {
				listData.add(getProduct());
			}
		}
	}
	
	public String getProduct(){
		// 
		// return getProduct();
		return null;
	}
}