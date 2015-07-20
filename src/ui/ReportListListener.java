package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JPanel;

public class ReportListListener implements MouseListener{
	
	private JList<String> list;
	private ExecutivePanel panel;
	
	public ReportListListener(JList<String> list, ExecutivePanel panel) {
		this.list = list;
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked" + list.getSelectedValue());
		String selectedItem = list.getSelectedValue();
		
		if(selectedItem.equalsIgnoreCase("Erste Auswertung")){
			panel.handleFirstEvent();
		}else if(selectedItem.equalsIgnoreCase("Zweite Auswertung")){
			panel.handleSecondEvent();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
