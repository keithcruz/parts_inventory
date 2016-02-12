
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


import javax.swing.JScrollPane;

import Models.ProductTemplate;
import Models.ProductTemplateModel;
import parts_inventory.TListViewController;
import parts_inventory.TemplateModelObserver;


public class TemplateListView extends ChildPanel implements TemplateModelObserver{
	private ProductTemplateModel model;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private static final long serialVersionUID = 1L;

public void updateObserver(ProductTemplateModel model, ProductTemplate t)
{
	this.model = model;
	listModel = new DefaultListModel<String>();
	list = new JList<String>();
	initialize();
}
	/**
	 * Create the application.
	 */
	public TemplateListView(MasterFrame master, ProductTemplateModel model) {
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

		this.setTitle("Product Template List");
		this.setBounds(100, 100, 800, 499);
		this.setLayout(null);

		JButton buildButton = new JButton("Build Product");
		buildButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		buildButton.setBounds(413, 406, 120, 33);
		this.add(buildButton);
		
		JButton addButton = new JButton("Add Template");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		addButton.setBounds(533, 406, 120, 33);
		this.add(addButton);
		
		JButton editButton = new JButton("Edit Template");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		editButton.setBounds(653, 406, 120, 33);
		this.add(editButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 55, 600, 312);
		this.add(scrollPane);
		
		list = new JList<String>();
		popModel(model);
		list.setModel(listModel);
		scrollPane.setViewportView(list);
	}
	
	public void registerListener(TListViewController controller) {
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
	
	public void popModel(ProductTemplateModel m) {
		ArrayList<ProductTemplate> temps = m.getProductTemplates();
		for (ProductTemplate temp : temps) {
			listModel.addElement(temp.toString());
		}
	}
	
	public void update() {
		listModel.clear();
		popModel(model);
		list.setModel(listModel);
	}
	
}