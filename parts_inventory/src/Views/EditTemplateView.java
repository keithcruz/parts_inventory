package Views;


import java.util.*;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;



import java.awt.Dimension;

import Models.InventoryModel;
import Models.Part;
import Models.ProductTemplateModel;
import Models.ProductTemplate;
import parts_inventory.EditTemplateController;
import parts_inventory.TemplateModelObserver;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;



public class EditTemplateView extends ChildPanel implements TemplateModelObserver{
	

	private JTextField numberText;
	private JTextField idText;
	private JTextArea descriptText;
	private JLabel lblPartNumber;
	private JLabel lblDescipt;
	private JLabel lblID;
	private JScrollPane scroll;
	private JPanel innerPan;
	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnDel;
	private JButton btnAddPart;
	private String type;
	private ArrayList<JLabel> pLabels;
	private ArrayList<JComboBox<String>> pParts;
	private ArrayList<JTextField> pQuants;
	
	private int num;
	private int innerY;
	private int maxY;
	public InventoryModel iMod;
	static final long serialVersionUID = 1;
	
	
	@Override
	public void updateObserver(ProductTemplateModel m, ProductTemplate p) {
				
		descriptText.setText(p.getDescription());
		idText.setText(Integer.toString(p.getID()));
		int tempNum = 0;
		pParts.clear();
		pQuants.clear();
		pLabels.clear();
		JComboBox<String> tempCB;
		JTextField tempTF;
		JLabel tempL;
		for(Part part : p.getParts())
		{
			tempNum++;
			tempCB = new JComboBox<String>(this.iMod.getPartStrings());
			tempTF = new JTextField();
			tempL = new JLabel("Part " + tempNum + " :");
			tempCB.addItem("Remove");
			tempCB.setSelectedItem(this.iMod.getPartidString(part.getId()));
			tempTF.setText(Integer.toString(p.getHashMap().get(part)));
			
			pParts.add(tempCB);
			pQuants.add(tempTF);
			pLabels.add(tempL);
		}
		
		numberText.setText(p.getPartNumber());
		initInner();
		
	}
	
	
	
	public EditTemplateView(MasterFrame master, ProductTemplateModel model, InventoryModel iMod, String type, ProductTemplate temp)
	{
		super(master);
		this.setTitle(type);
		this.type = type;
		this.iMod = iMod;
		this.innerY = 5;
		this.pParts = new ArrayList<JComboBox<String>>();
		this.pQuants = new ArrayList<JTextField>();
		this.pLabels = new ArrayList<JLabel>();
		this.num = 0;
		JComboBox<String> tempCB;
		JTextField tempTF;
		JLabel tempL;
		
		for(Part p : temp.getParts())
		{
			num++;
			tempCB = new JComboBox<String>(this.iMod.getPartStrings());
			tempTF = new JTextField();
			tempL = new JLabel("Part " + num + " :");
			tempCB.addItem("Remove");
			tempCB.setSelectedItem(this.iMod.getPartidString(p.getId()));
			tempTF.setText(Integer.toString(temp.getHashMap().get(p)));
			
			pParts.add(tempCB);
			pQuants.add(tempTF);
			pLabels.add(tempL);
		}
		initialize();
		numberText.setText(temp.getPartNumber());
		
		if(type.equals("Edit Template"))
		{
			
			
			descriptText.setText(temp.getDescription());
			idText.setText(Integer.toString(temp.getID()));
		}
		else {
			

		}
		
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */

	public void initialize() {
		this.setBounds(100, 100, 505, 450);
		this.setLayout(null);
		
		



		
		
		
		lblID = new JLabel("Template ID :");
		lblID.setBounds(33, 61, 80, 19);
		this.add(lblID);
		
		idText = new JTextField();
		idText.setBounds(128, 61, 302, 22);
		idText.setEditable(false);
		this.add(idText);
		
		lblPartNumber = new JLabel("Product Num. :");
		lblPartNumber.setBounds(20, 96, 83, 19);
		this.add(lblPartNumber);
		
		numberText = new JTextField();
		numberText.setBounds(128, 96, 302, 22);
		numberText.setEditable(false);
		this.add(numberText);

		
		lblDescipt = new JLabel("Description :");
		lblDescipt.setBounds(42, 131, 83, 19);
		this.add(lblDescipt);
		
		descriptText = new JTextArea();
		descriptText.setBounds(128, 131, 302, 22);
		descriptText.setEditable(true);
		descriptText.setColumns(10);
		this.add(descriptText);
		
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(233, 370, 97, 25);
		this.add(btnCancel);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(342, 370, 97, 25);
		this.add(btnSave);
		
		btnDel = new JButton("Delete");
		btnDel.setBounds(124, 370, 97, 25);
		this.add(btnDel);
		
		btnAddPart = new JButton("Add Part");
		btnAddPart.setBounds(15,370,97,25);
		this.add(btnAddPart);
		
		innerPan = new JPanel();
		maxY = 200;
		innerPan.setPreferredSize(new Dimension(400, maxY));
		innerPan.setLayout(null);
		
		this.initInner();

		scroll = new JScrollPane(innerPan);
		scroll.setBounds(10, 163, 450, 200);
	 	scroll.createVerticalScrollBar();
		this.add(scroll);
		
		
	
	}
	
	
	
	public JTextField getNumberText() {
		return numberText;
	}
		

	public JTextArea getdescriptText() {
		return descriptText;
	}
	public JTextField getIdText()
	{
		return idText;
	}
	public ArrayList<JTextField> getpQuants() {
		return pQuants;
	}
	public ArrayList<JComboBox<String>> getpParts() {
		return pParts;
	}
	

	public void registerListener(EditTemplateController controller) {
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

	 
	 public void initInner()
	 {
		 innerPan.removeAll();
		 innerY = 5;
			for (JLabel l : pLabels)
			{
				l.setBounds(5,innerY, 60, 19);
				innerPan.add(l);
				innerY+=25;
			}
			innerY = 5;
			for (JComboBox<String> j : pParts)
			{
				j.setBounds(70,innerY, 280, 22);
				innerPan.add(j);
				innerY+=25;
				if(innerY >= maxY)
				{
					maxY+=50;
					innerPan.setPreferredSize(new Dimension(400, maxY));
				}
			}
			innerY = 5;
			for (JTextField j : pQuants)
			{
				j.setBounds(360,innerY, 70, 22);
				innerPan.add(j);
				innerY+=25;
			}
	 }
	 public void addPart()
	 {
		 this.num++;
		JComboBox<String> tempCB = new JComboBox<String>(this.iMod.getPartStrings());
		tempCB.addItem("Select Part");
		tempCB.addItem("Remove");
		tempCB.setSelectedItem("Select Part");
		JTextField tempTF = new JTextField();
		tempTF.setText("0");
		JLabel tempL = new JLabel("Part " + num + " :");
			
		this.pParts.add(tempCB);
		this.pQuants.add(tempTF);
		this.pLabels.add(tempL);
		initInner();
		//innerPan.revalidate();
		innerPan.repaint();
	 }


}