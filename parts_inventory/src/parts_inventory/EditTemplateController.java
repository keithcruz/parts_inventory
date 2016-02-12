package parts_inventory;
import java.awt.event.ActionListener;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import Models.Part;
import Models.ProductTemplateModel;
import Models.ProductTemplate;
import Views.EditTemplateView;



/**
 * AddEditController class controls interaction. between the AddEditView and the InventoryModel.
 * @author Keith Cruz, Brendan Nelson-Weiss
 *
 */
public class EditTemplateController implements ActionListener {
	private ProductTemplateModel model;
	private EditTemplateView view;
	private TListViewController iController;

	
	/**
	 * AddEditController constructor takes arguments for an InventoryModel and AddEditView.
	 * @param model The InventoryModel to be controlled.
	 * @param view The AddEditView to be controlled.
	 */

	public EditTemplateController(ProductTemplateModel model, EditTemplateView view, TListViewController iController) {
		this.model = model;
		this.view = view;
		this.iController = iController;

	}
	
	/**
	 * actionPerformed performs various actions based on the button or action pressed by the user.
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		String intPattern = "^[0-9]*$";
		String symANPattern = "^[^\t^\n^\f^\r]*$";
		String descript = "";
		ArrayList<String> partsIn = new ArrayList<String>();
		ArrayList<Part> parts = new ArrayList<Part>();
		HashMap<Part,Integer> partQs = new HashMap<Part,Integer>();
		ArrayList<Integer> partsCounts = new ArrayList<Integer>();
		boolean done = false;
		if(command.equals("Save"))
		{
			Part tempP;
			
			

			for(JComboBox<String> bleh : view.getpParts())
			{
				partsIn.add(bleh.getSelectedItem().toString());
			}
			for(JTextField blarg : view.getpQuants())
			{
				if(!blarg.getText().matches(intPattern))
				{
					view.errorWindow("All Part Quantities must be numerical");
					done = true;
					break;
				}
				else
				{
				partsCounts.add(Integer.parseInt(blarg.getText()));
				}
			}
			if(!done)
			{
				
				for(int k = 0; k < partsIn.size(); k++)
				{
	
					String bleh = partsIn.get(k);
					if(bleh.equals("Select Part"))
					{
						view.errorWindow("All Parts must be selected or set for removal.");
						done = true;
						break;
					}
					else if(bleh.equals("Remove"))
					{
						
					}
					else
					{
						String s[] = bleh.split(" +");
						tempP = view.iMod.getPartByID(Integer.parseInt(s[0]));
						if(parts.contains(tempP))
						{
							view.errorWindow("Each Part Can Only be included once.");
							done = true;
							break;
						}
						else
						
							partQs.put(tempP,partsCounts.get(k));
							parts.add(tempP);
						}
					}
				}
			

			 if(!view.getdescriptText().getText().matches(symANPattern) || view.getdescriptText().getText().length() > 255) {
				view.errorWindow("Description must be alphanumeric or symbols and a maximum of 255 characters.");
			}
			 else
			 {
				 descript = view.getdescriptText().getText();
			 }
		   }
		
		
		
		
		if (command.equals("Save") &&  done == false) 
		{
			if(parts.isEmpty())
			{
				view.errorWindow("Must have at least one part in a template");
				
			}
			else
			{
				
				
				
				String num = view.getNumberText().getText();								
				if (view.getTpe().equals("Edit Template")) 
				{
					if(!(model.getSession().isCanAddProductTemplates()))
					{
						view.errorWindow("Insufficient Priveleges to act");
					}
					else
					{

						int id = Integer.parseInt(view.getIdText().getText());
						model.editProduct(id, num, descript);
						ArrayList<Part> oldParts = model.getProductByID(id).getParts();
						HashMap<Part,Integer> oldQs = model.getProductByID(id).getHashMap();
	
						
						for(int j = 0;j < parts.size(); j++)
						{
							Part p2 = parts.get(j);
							//System.out.println(p2.getID());
							
							Boolean inOld = false;
							
							for (int i = 0; i < oldParts.size(); i++) {
								if (oldParts.get(i).getId() == p2.getId()) {
									inOld = true;
								}
							}
							
							if(inOld)
							{
								if(partQs.get(p2).equals(oldQs.get(p2)))
								{
									//do nothing
								}
								else
								{
									model.editProductPart(id,  p2,  p2, partQs.get(p2));
								}
							}
							else
							{
								model.addProductPart(id, p2, partQs.get(p2));
							}
						}
						for(int i = 0; i < oldParts.size(); i++)
						{
							Part p = oldParts.get(i);
							if( !(parts.contains(p)))
							{
								model.deleteProductPart(id, p.getId());
							}
						}
						
	
						iController.setEditedTemp(id);
	
						view.getMaster().runUpdates();
						view.hideInternalFrame();
					}

				}
				else if (view.getTpe().equals("Add Template")) {
					if(!(model.getSession().isCanAddProductTemplates()))
					{
						view.errorWindow("Insufficient Priveleges to act");
					}
					else
					{
					 

						int id = model.addProduct(descript);

						for(Part p : parts)
						{
							model.addProductPart(id, p, partQs.get(p));
							
						}

						iController.setEditedTemp(id);
						
						
						


						view.getMaster().runUpdates();

						view.hideInternalFrame();
					}

					
				}
			}
			
		}
		
		if (command.equals("Cancel")) {

			view.hideInternalFrame();
		}
		
		if (command.equals("Delete")) {
			if(!(model.getSession().isCanDeleteProductTemplates()))
			{
				view.errorWindow("Insufficient Priveleges to act");
			}
			else
			{
				boolean found = false;
				if(view.getIdText().getText().equals(""))
				{
					view.errorWindow("Template does not Exist");
				}
				else
				{
					int id = Integer.parseInt(view.getIdText().getText());
					ArrayList<EditTemplateView> tEdits = iController.getTempEdits();
					for (EditTemplateView tEdit : tEdits) {
						if (Integer.parseInt(tEdit.getIdText().getText()) == id) {
							tEdit.hideInternalFrame();
						}
					}
					
					
					ArrayList<ProductTemplate> temps = model.getProductTemplates();
					for (ProductTemplate cTemp: temps)
					{
						if (cTemp.getID() == id);
						{
							found = true;
						}
					}
					
					if (found) 
					{
					
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
						
							e1.printStackTrace();
					}
					model.deleteProduct(id);
					view.getMaster().runUpdates();
					view.hideInternalFrame();			
				
				}
				else
					{
						view.errorWindow("Product Template not found");
					}
				}
			}
		}
			
		 if(command.equals("Add Part"))
		{
			view.addPart();
		}
		 
	}
		
}