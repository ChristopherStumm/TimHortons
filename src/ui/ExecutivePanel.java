package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import connections.executivePanelRequests;

public class ExecutivePanel extends JPanel{
	
	private final connections.executivePanelRequests EPR;
	
	public ExecutivePanel(){
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		EPR = new executivePanelRequests();
		
		//EPR.getAllData();
	}

}
