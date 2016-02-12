package Views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Models.InventoryModel;
import Models.ProductTemplateModel;
import Models.Authenticator;
import Models.Session;
import Models.User;

import java.util.*;

import parts_inventory.EditPartController;
import parts_inventory.InventoryViewController;
import parts_inventory.TListViewController;
import parts_inventory.MasterController;



/*
 * MasterFrame : a little MDI skeleton that has communication from child to JInternalFrame 
 * 					and from child to the top-level JFrame (MasterFrame)  
 */
public class MasterFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private int newFrameX = 0, newFrameY = 0;
	private InventoryModel model;
	private ProductTemplateModel model2;
	private Authenticator auth;
	private ArrayList<ChildPanel> updatePanels = new ArrayList<ChildPanel>();
	private ArrayList<User> usrs;
	private  String title;
	private  String pTitle;
	private JMenuBar menuBar;
	//used to cascade or stagger starting x,y of JInternalFrames
	
	public MasterFrame(String title, final InventoryModel m, final ProductTemplateModel m2, final Authenticator auth, final ArrayList<User> usrs) {
		super(title);
		pTitle = title;
		this.model = m;
		this.model2 = m2;
		this.auth = auth;
		this.usrs = usrs;
		this.title = "";
		
		
		
		//create menu for adding inner frames
		
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuItem = new JMenuItem("Quit");
		menu.add(menuItem);
		menuBar.add(menu);
		
		menu = new JMenu("Sections");
		menuItem = new JMenuItem("Parts List");
		
		menu.add(menuItem);
		menuItem = new JMenuItem("Inventory");
		
		menu.add(menuItem);
		menuItem = new JMenuItem("Templates");
		
		
		menu.add(menuItem);
		menuBar.add(menu);

		setJMenuBar(menuBar);
		   
		menu = new JMenu("Logins");
		menuItem = new JMenuItem("Tom Jones");
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Sue Smith");

		menu.add(menuItem);
		menuItem = new JMenuItem("Ragnar Nelson");
		menu.add(menuItem);
		menuBar.add(menu);
		
		
		
		
		
		desktop = new JDesktopPane();
		add(desktop);
	}
	
	//create the child panel, insert it into a JInternalFrame and show it
	public void openMDIChild(ChildPanel child) {
		JInternalFrame frame = new JInternalFrame(child.getTitle(), false, true, false, true );
		frame.add(child, BorderLayout.CENTER);
		frame.pack();
		//wimpy little cascade for new frame starting x and y
		frame.setLocation(newFrameX, newFrameY);
		newFrameX = (newFrameX + 10) % desktop.getWidth(); 
		newFrameY = (newFrameY + 10) % desktop.getHeight(); 
		desktop.add(frame);
		frame.setVisible(true);
	}

	//display a child's message in a dialog centered on MDI frame
	public void displayChildMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	//creates and displays the JFrame
	public void createAndShowGUI() {
		this.setSize(900, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setVisible(true);
	}
	public void windowClosing(WindowEvent e) {
		model.close();
	}
	public void runUpdates()
	{
		for (ChildPanel p : updatePanels)
		{
			if(p instanceof InventoryView)
			{
				((InventoryView) p).update();
			}
			if(p instanceof InventoryStockView)
			{
				((InventoryStockView) p).update();
			}
			if(p instanceof TemplateListView)
			{
				((TemplateListView) p).update();
			}
		}
	}
	public void errorWindow(String error) {
		final JFrame err = new JFrame("error");
		err.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		err.setBounds(600, 400, 600, 100);
		err.getContentPane().setLayout(null);
		
		JLabel errLabel = new JLabel(error);
		errLabel.setBounds(0, 0, error.length() + 500, 15);
		err.getContentPane().add(errLabel);
		
		JButton errButton = new JButton("ok");
		errButton.setBounds(250, 20, 100, 20);
		err.getContentPane().add(errButton);
		errButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				
				if (c.getActionCommand().equals("ok")) {
					err.setVisible(false);
				}
			}
		});
		err.setVisible(true);
		
	}
	public void registerListener(MasterController controller) {
		ArrayList<JMenuItem> components = new ArrayList<JMenuItem>();
		for(int i = 0; i < menuBar.getMenuCount(); i++)
		{
			JMenu jmen = menuBar.getMenu(i);
			for(int j = 0; j < jmen.getItemCount();j++ )
			{
				components.add(jmen.getItem(j));
			}
			
		}
		for (JMenuItem component : components) {
			
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(controller);
		}
	}
	public InventoryModel getIModel()
	{
		return this.model;
	}
	public ProductTemplateModel getTModel()
	{
		return this.model2;
	}
	public void addToUpdatePanels(ChildPanel p)
	{
		updatePanels.add(p);
	}
	public ArrayList<User> getUsers()
	{
		return usrs;
	}
	public Authenticator getAuth()
	{
		return auth;
	}
	public void setNewTitle(String title)
	{
		this.title = title;
		this.setTitle(title);
		
	}
	public String getPTitle()
	{
		return pTitle;
	}

}


