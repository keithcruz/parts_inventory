package Views;



import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import Models.InventoryItem;
import Models.InventoryModel;
import parts_inventory.EditInventoryController;
import parts_inventory.InventoryModelItemObserver;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;



public class EditInventoryView extends ChildPanel implements InventoryModelItemObserver{
	
	private InventoryModel model;
	private JTextField quantityText;
	//private JTextField locationText;
	private JTextField idText;
	private JComboBox<String> locSelect;
    private JComboBox<String> partSelect;
	private JLabel lblPart;
	private JLabel lblQuantity;
	private JLabel lblLocation;
	private JLabel lblID;
	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnDel;
	private String type;
	//private InventoryView iView;
	private static final long serialVersionUID = 1L;
	private String[] locList = {"Unknown","Facility 1 Warehouse 1","Facility 1 Warehouse 2","Facility 2"};
	
	
	@Override
	public void updateObserver(InventoryModel m, InventoryItem item) {
		idText.setText(Integer.toString(item.getId()));
		locSelect.setSelectedItem(item.getLocation());
		quantityText.setText(Integer.toString(item.getQuantity()));
		if (item.getPart() == null) {
			partSelect.setSelectedItem("No Part Selected");
		}
		else {
			partSelect.setSelectedItem(model.getPartidString(item.getPart().getId()));
		}
		
		
 
		
	}
	
	
	public EditInventoryView(MasterFrame master, InventoryModel model, String type, InventoryItem item)
	{
		
		super(master);
		this.type = type;
		this.model = model;
		initialize();
		
		if(type.equals("Edit Item"))
		{
			
			idText.setText(Integer.toString(item.getId()));
			quantityText.setText(Integer.toString(item.getQuantity()));
			locSelect.setSelectedItem(item.getLocation());
			
			if (item.getPart() != null) {
				partSelect.setSelectedItem(model.getPartidString(item.getPart().getId()));
			}
			else {
				partSelect.setSelectedItem("No Part Selected");
			}
		    



			
		}
		else {
			

			locSelect.setSelectedItem("Unknown");
			partSelect.setSelectedItem("No Part Selected");
		}
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */

	public void initialize() {
		this.setBounds(100, 100, 505, 450);
		this.setLayout(null);
		
		partSelect = new JComboBox<String>(model.getPartStrings());
		partSelect.setBounds(128, 61, 302, 22);
		partSelect.addItem("No Part Selected");
		this.add(partSelect);
		
		idText = new JTextField();
		idText.setBounds(128, 96, 302, 22);
		idText.setEditable(false);
		this.add(idText);
		idText.setColumns(10);
		
		
		locSelect = new JComboBox<String>(locList);
		locSelect.setBounds(128, 131, 302, 22);
		this.add(locSelect);
		
		
		
		
		quantityText = new JTextField();
		quantityText.setColumns(10);
		quantityText.setBounds(128, 166, 302, 22);
		this.add(quantityText);

		
		lblPart = new JLabel("Part :");
		lblPart.setBounds(46, 61, 80, 19);
		this.add(lblPart);
		
		lblID = new JLabel("ID :");
		lblID.setBounds(57, 96, 80, 19);
		this.add(lblID);
		
		lblLocation= new JLabel("Location :");
		lblLocation.setBounds(20, 131, 83, 19);
		this.add(lblLocation);
		
		lblQuantity = new JLabel("Quantity :");
		lblQuantity.setBounds(22, 166, 80, 19);
		this.add(lblQuantity);
		

		
		
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(233, 370, 97, 25);
		this.add(btnCancel);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(342, 370, 97, 25);
		this.add(btnSave);
		
		btnDel = new JButton("Delete");
		btnDel.setBounds(124, 370, 97, 25);
		this.add(btnDel);
	
	}
	
	

	
	public JComboBox<String> getPartSelect() {
		return partSelect;
	}
	
	
	public JTextField getQuantityText() {
		return quantityText;
	}
	
	public JComboBox<String> getLocSelect()
	{
		return locSelect;
	}
	public JTextField getIDText() {
		return idText;
	}
	

	public void registerListener(EditInventoryController controller) {
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
