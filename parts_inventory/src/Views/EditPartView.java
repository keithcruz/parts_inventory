package Views;



import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import Models.InventoryModel;
import Models.Part;
import parts_inventory.EditPartController;
import parts_inventory.InventoryModelObserver;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;



public class EditPartView extends ChildPanel implements InventoryModelObserver{
	
	private JTextField nameText;
	private JTextField numberText;
	private JTextField vendorText;
	private JTextField unitText;
	private JTextField exNumText;
	private JTextField idText;
	private JLabel lblPrtName;
	private JLabel lblPartNumber;
	private JLabel lblVendor;
	private JLabel lblUnit;
	private JLabel lblExNum;
	private JLabel lblID;
	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnDel;
	private String type;
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void updateObserver(InventoryModel m, Part p) {
		nameText.setText(p.getName());
		numberText.setText(p.partNumber());
		vendorText.setText(p.getVendor());
		unitText.setText(p.getUnit());
		
	}
	
	
	public EditPartView(MasterFrame master, InventoryModel model, String type, Part part)
	{
		
		super(master);
		this.setTitle(type);
		this.type = type;
		initialize();
		if(type.equals("Edit Part"))
		{
			nameText.setText(part.getName());
			numberText.setText(part.partNumber());
			vendorText.setText(part.getVendor());
			unitText.setText(part.getUnit());
			idText.setText(Integer.toString(part.getId()));
			exNumText.setText(part.getExNum());

			
		}
		else {
			unitText.setText("Unknown");
			//locationText.setText("Unknown");
		}
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */

	public void initialize() {
		this.setBounds(100, 100, 505, 450);
		this.setLayout(null);
		
		
		nameText = new JTextField();
		nameText.setBounds(128, 61, 302, 22);
		this.add(nameText);
		nameText.setColumns(10);
		
		numberText = new JTextField();
		numberText.setBounds(128, 96, 302, 22);
		numberText.setEditable(false);
		this.add(numberText);
		numberText.setColumns(10);
		
		
		vendorText = new JTextField();
		vendorText.setColumns(10);
		vendorText.setBounds(128, 131, 302, 22);
		this.add(vendorText);
		
		unitText = new JTextField();
		unitText.setColumns(10);
		unitText.setBounds(128, 166, 302, 22);
		this.add(unitText);
		
		
		exNumText = new JTextField();
		exNumText.setColumns(10);
		exNumText.setBounds(128, 201, 302, 22);
		this.add(exNumText);

		idText = new JTextField();
		idText.setBounds(128, 236, 302, 22);
		idText.setEditable(false);
		idText.setColumns(10);
		this.add(idText);
		
		
		
		lblPrtName = new JLabel("Part Name :");
		lblPrtName.setBounds(33, 61, 80, 19);
		this.add(lblPrtName);
		
		lblPartNumber = new JLabel("Part Number :");
		lblPartNumber.setBounds(20, 96, 83, 19);
		this.add(lblPartNumber);
		
		lblVendor = new JLabel("Vendor :");
		lblVendor.setBounds(52, 131, 50, 19);
		this.add(lblVendor);
		
		lblUnit = new JLabel("Unit of Quantity :  ");
		lblUnit.setBounds(6, 166, 100, 19);
		this.add(lblUnit);
		
		lblExNum = new JLabel("External Part # :  ");
		lblExNum.setBounds(8, 201, 100, 19);
		this.add(lblExNum);
		
		lblID = new JLabel("ID :");
		lblID.setBounds(57, 236, 80, 19);
		this.add(lblID);
		
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(233, 370, 97, 25);
		this.add(btnCancel);
		
		btnSave = new JButton("Save");
//		btnSave.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		btnSave.setBounds(342, 370, 97, 25);
		this.add(btnSave);
		
		btnDel = new JButton("Delete");
		btnDel.setBounds(124, 370, 97, 25);
		this.add(btnDel);
	
	}
	
	
	public JTextField getExNumText()
	{
		return exNumText;
	}
	public JTextField getNameText() {
		return nameText;
	}
	
	public JTextField getNumberText() {
		return numberText;
	}
	
	
	public JTextField getVendorText() {
		return vendorText;
	}
	
	public JTextField getUnitText() {
		return unitText;
	}
	public JTextField getIdText()
	{
		return idText;
	}
	

	public void registerListener(EditPartController controller) {
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
