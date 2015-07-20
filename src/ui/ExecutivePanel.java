package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import connections.executivePanelRequests;

public class ExecutivePanel extends JPanel{
	
	private final connections.executivePanelRequests EPR;
	private final GraphicsFactory graphicsFactory;
	private final String[] LIST_DATA = {"Erste Auswertung", "Zweite Auswertung"};
	private JPanel centerContainer;
	
	public ExecutivePanel(){
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		EPR = new executivePanelRequests();
		graphicsFactory = new GraphicsFactory(EPR);
		setUpSideBar();
		setUpMainView();
	}

	private void setUpMainView() {
		centerContainer = new JPanel();
		centerContainer.setBackground(Color.WHITE);
		centerContainer.setVisible(true);
		this.add(centerContainer, BorderLayout.CENTER);
		
	}

	private void setUpSideBar() {
		//creating the panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setVisible(true);
		
		//adding the list
		JList<String> list = new JList<String>(LIST_DATA);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		list.addMouseListener(new ReportListListener(list, this));
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		panel.add(listScroller);
		this.add(panel, BorderLayout.WEST);
	}
	
	void handleFirstEvent(){
		centerContainer.removeAll();
		//asdasds
		
		//Chart chart = GraphicsFactory.getFirstGraphic();
		//SwingWrapper wrapper = new SwingWrapper(chart);
		//JFrame frame = wrapper.displayChart();
		
		JPanel chartPanel = graphicsFactory.getProductFailureBarchart();
		centerContainer.add(chartPanel);
		this.updateUI();
	}
	
	void handleSecondEvent(){
		centerContainer.removeAll();
		
		JTable table = graphicsFactory.getFailureTable();
		JScrollPane scrollPane = new JScrollPane(table);
		centerContainer.add(scrollPane);
		this.updateUI();
	}

}
