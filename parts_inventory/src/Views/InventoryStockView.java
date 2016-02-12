package Views;
import java.util.*;
import java.awt.Component;





import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;


import javax.swing.JList;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;

import Models.InventoryItem;
import Models.InventoryModel;
import parts_inventory.InventoryViewController;


public class InventoryStockView extends ChildPanel{
	private InventoryModel model;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private static final long serialVersionUID = 1L;


	/**
	 * Create the application.
	 */
	public InventoryStockView(MasterFrame master, InventoryModel model) {
		super(master);
		this.model = model;
		listModel = new DefaultListModel<String>();
		list = new JList<String>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setTitle("Inventory");
		this.setBounds(100, 100, 800, 499);
		
		this.setLayout(null);
		
		JButton addButton = new JButton("Add Item");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		addButton.setBounds(553, 406, 107, 33);
		this.add(addButton);
		
		JButton editButton = new JButton("Edit Item");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		editButton.setBounds(659, 406, 107, 33);
		this.add(editButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 55, 600, 312);
		this.add(scrollPane);
		
		list = new JList<String>();
		popModel(model);
		list.setModel(listModel);
		scrollPane.setViewportView(list);
	}
	
	public void registerListener(InventoryViewController controller) {
		Component[] components = this.getComponents();
		
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(controller);
			}
		}
	}
	
	public JPanel getPnl() {
		return this;
	}
	
	public JList<String> getList() {
		return list;
	}
	
	public void popModel(InventoryModel m) {
		ArrayList<InventoryItem> iStock = m.getInventoryStock();
		for (InventoryItem item : iStock) {
			listModel.addElement(item.toString());
		}
	}
	
	public void update() {
		listModel.clear();
		popModel(model);
		list.setModel(listModel);
	}
	
	public void windowClosing(WindowEvent e) {
		model.close();
	}
}