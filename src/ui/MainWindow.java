package ui;

import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

public class MainWindow extends JFrame{
	
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	
	
	
	public MainWindow(){
        this.setSize(900, 600);
        setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Dingleberry");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        System.out.println("Window shown");
        
        setupLayout();
        setupMenubar();
	}

	private void setupMenubar() {
		
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("A Menu");
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item");
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "This doesn't really do anything");
		menu.add(menuItem);

		menuItem = new JMenuItem("Both text and icon",
		                         new ImageIcon("images/middle.gif"));
		menu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menu.add(menuItem);

		//a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		//a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		//a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("An item in the submenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		submenu.add(menuItem);
		menu.add(submenu);

		//Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
		        "This menu does nothing");
		menuBar.add(menu);
		menuBar.setVisible(true);
		this.setJMenuBar(menuBar);
	}

	private void setupLayout() {
		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent panel1 = new ExecutivePanel();

		JComponent panel2 = new OperativePanel();
		tabbedPane.addTab("Operative View", panel1);
		tabbedPane.addTab("Executive View", panel2);
		
		this.add(tabbedPane);
	}
}
