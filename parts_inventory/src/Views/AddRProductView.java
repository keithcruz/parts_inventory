package Views;


import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import Models.InventoryModel;
import Models.ProductTemplate;
import Models.ProductTemplateModel;
import parts_inventory.EditPartController;
import parts_inventory.TemplateModelObserver;
import parts_inventory.BuildProductViewController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.util.ArrayList;


public class AddRProductView extends ChildPanel implements TemplateModelObserver {
	



	private JLabel lblLoc;
	private JLabel lblTemp;
	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnDel;
	private String type;
	private JList<String> list;
	private JComboBox<String> templateSelect;
	private JComboBox<String> locSelect;
	private ArrayList<ProductTemplate> templates;
	private String[] locList = {"Unknown","Facility 1 Warehouse 1","Facility 1 Warehouse 2","Facility 2"};
	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void updateObserver(ProductTemplateModel model, ProductTemplate t)
	{
		templates = model.getProductTemplates();
		initialize();
	}
	
	
	public AddRProductView(MasterFrame master, ProductTemplateModel tMod, String type  )
	{
		
		super(master);
		this.setTitle(type);
		this.type = type;
		templates = tMod.getProductTemplates();
			initialize();	
	}
	
	/**
	 * Initialize the contents of the frame.
	 */

	public void initialize() {
		this.setBounds(100, 100, 505, 450);
		this.setLayout(null);
		
		
		templateSelect = new JComboBox<String>();
		for(ProductTemplate t : templates)
		{
			templateSelect.addItem(t.toString());
		}
		templateSelect.addItem("None");
		templateSelect.setSelectedItem("None");
		templateSelect.setBounds(128, 61, 302, 22);
		this.add(templateSelect);
		
		locSelect = new JComboBox<String>(locList);
		locSelect.setSelectedItem("Unknown");
		locSelect .setBounds(128, 96, 302, 22);
		this.add(locSelect );
		
		
		
		
		
		
		lblTemp = new JLabel("Template :");
		lblTemp.setBounds(33, 61, 80, 19);
		this.add(lblTemp);
		
		lblLoc = new JLabel("Location :");
		lblLoc.setBounds(20, 96, 83, 19);
		this.add(lblLoc);				
		
		
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(233, 370, 97, 25);
		this.add(btnCancel);
		
		btnSave = new JButton("Build");
		btnSave.setBounds(342, 370, 97, 25);
		this.add(btnSave);
	
	}
	
	

	public JComboBox<String> getLocSelect()
	{
		return locSelect;
	}
	public JComboBox<String> getTemplateSelect()
	{
		return templateSelect;
	}
	
	
	
	

	public void registerListener(BuildProductViewController controller) {
		Component[] components = this.getComponents();
		
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(controller);
			}
		}
	}
	
	public String getTpe() {
		return type;
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
}