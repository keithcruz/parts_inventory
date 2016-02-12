package parts_inventory;
import java.awt.event.ActionListener;

//import java.util.UUID;
import java.awt.event.ActionEvent;
//import java.util.ArrayList;

import Models.InventoryItem;
import Models.InventoryModel;
import Models.Part;
import Views.EditInventoryView;
//import Views.InventoryStockView;

/**
 * AddEditController class controls interaction. between the AddEditView and the InventoryModel.
 * @author Keith Cruz, Brendan Nelson-Weiss
 *
 */
public class EditInventoryController implements ActionListener {
	private InventoryModel model;
	private EditInventoryView view;

	private InventoryViewController iController;
	
	private String itemStamp;
	
	/**
	 * AddEditController constructor takes arguments for an InventoryModel and AddEditView.
	 * @param model The InventoryModel to be controlled.
	 * @param view The AddEditView to be controlled.
	 */

	public EditInventoryController(InventoryModel model, EditInventoryView view, InventoryViewController iController) {
		this.model = model;
		this.view = view;

		this.iController = iController;
		
		if (view.getTpe().equals("Edit Item")) {
			int itemId = Integer.parseInt(view.getIDText().getText());
			itemStamp = model.setStamp(itemId);
		}
		else {
			itemStamp = "";
		}
	}
	
	/**
	 * actionPerformed performs various actions based on the button or action pressed by the user.
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		//String pattern = "^[a-zA-Z0-9]*$";
		String intPattern = "^[0-9]*$";
		//String symANPattern = "^[^\t^\n^\f^\r]*$";

		
		if (command.equals("Save")) {
			if(!(model.getSession().isCanAddInventory()))
			{
				view.errorWindow("Insufficient Priveleges to act");
			}
			else {
				InventoryItem im = null;
				int itemId = 0;
				
				if (!view.getIDText().getText().equals("")) {
					itemId = Integer.parseInt(view.getIDText().getText());
					im = model.getItemById(itemId);
				}
				
				 if (view.getQuantityText().getText().equals(""))
				{
					view.errorWindow("You must enter a quantity");
				}
				 else if(im != null && im.getProduct() == 0 && view.getPartSelect().getSelectedItem().equals("No Part Selected"))
				 {
					 view.errorWindow("You must select a Part");
				 }
				else if ( (!view.getQuantityText().getText().matches(intPattern)) || (Integer.parseInt(view.getQuantityText().getText()) > Integer.MAX_VALUE) || (Integer.parseInt(view.getQuantityText().getText()) < 0 ) ){
					view.errorWindow("quantity must be a positive integer and must be less than " + Integer.MAX_VALUE);
					}
	
				else if (!view.getLocSelect().getSelectedItem().equals("Facility 1 Warehouse 1") &&
						!view.getLocSelect().getSelectedItem().equals("Facility 1 Warehouse 2") &&
						!view.getLocSelect().getSelectedItem().equals("Facility 2")) {
					view .errorWindow("Enter either: Facility 1 Warehouse 1, Facility 1 Warehouse 2, or Facility 2");
				}
				else 
				{
					String loc = view.getLocSelect().getSelectedItem().toString();
					String partS = view.getPartSelect().getSelectedItem().toString();
					int partId = 0;
					if (im == null || im.getProduct() == 0) {
						partId = Integer.parseInt(partS.substring(0,partS.indexOf(' ')));
					}
	
					int quant = Integer.parseInt(view.getQuantityText().getText());
					Part aPart = model.getPartById(partId);
	
					
					if (view.getTpe().equals("Edit Item")) {
						
							
							/*int itemId = Integer.parseInt(view.getIDText().getText());
							InventoryItem im = model.getItemById(itemId);*/
							boolean pA = false;
							for (int i = 0; i < model.getInventoryStock().size(); i++) {
								if (aPart != null && model.getInventoryStock().get(i).getPart() != null && im.getProduct() == 0 && model.getInventoryStock().get(i).getPart().equals(aPart) && 
										(model.getInventoryStock().get(i).getLocation().equals(loc))) {
									
									if(model.getInventoryStock().get(i).getId() != itemId)
									{
										view .errorWindow("That part and location combination already exists");
										pA = true;
									}
								}
							}
							if (!pA)
							{
								
								if (!model.editItem(aPart, itemId, loc,quant, itemStamp)) {
									view.errorWindow("Edit Failed. Please Retry");
									view.hideInternalFrame();
								}
								else {
									view.hideInternalFrame();
									iController.setEditedItem(itemId);
									view.getMaster().runUpdates();
								}
							}
	
						
					}
					else if (view.getTpe().equals("Add Item")) {
						
						
						
							
	
							boolean pA = false;
							for (int i = 0; i < model.getInventoryStock().size(); i++) {
								if (model.getInventoryStock().get(i).getPart() != null && model.getInventoryStock().get(i).getPart().equals(aPart) && (model.getInventoryStock().get(i).getLocation().equals(loc))) {
										view .errorWindow("That part and location combination already exists");
										pA = true;
									
								}
							}
							if (!pA)
							{
								model.newItem(aPart, loc, quant);
							}	
		
		
		
							view.getMaster().runUpdates();
							view.hideInternalFrame();
		
						
					}
				}
			}
		}
		
		if (command.equals("Cancel")) {
			view.setVisible(false);
		}
		
		if (command.equals("Delete")) {
			if(!(model.getSession().isCanDeleteInventory()))
			{
				view.errorWindow("Insufficient Priveleges to act");
			}
			else{
			
				if (view.getQuantityText().getText().equals(""))
				{
					view.errorWindow("You must enter a quantity");
				}
				if(Integer.parseInt(view.getQuantityText().getText()) != 0)
				{
					view.errorWindow("The Quantity must be zero before you can delete a part");
				}
				 else if(view.getPartSelect().getSelectedItem().equals("No Part Selected"))
				 {
					 view.errorWindow("You must select a Part");
				 }
				else if ( (!view.getQuantityText().getText().matches(intPattern)) || (Integer.parseInt(view.getQuantityText().getText()) > Integer.MAX_VALUE) || (Integer.parseInt(view.getQuantityText().getText()) < 0 ) ){
					view.errorWindow("quantity must be zero or larger and must be less than " + Integer.MAX_VALUE);
					}
	
				else if (!view.getLocSelect().getSelectedItem().equals("Facility 1 Warehouse 1") &&
						!view.getLocSelect().getSelectedItem().equals("Facility 1 Warehouse 2") &&
						!view.getLocSelect().getSelectedItem().equals("Facility 2")) {
					view .errorWindow("Enter either: Facility 1 Warehouse 1, Facility 1 Warehouse 2, or Facility 2");
				}
				else 
				{
					String loc = view.getLocSelect().getSelectedItem().toString();
					String partS = view.getPartSelect().getSelectedItem().toString();
					int partId = Integer.parseInt(partS.substring(0,partS.indexOf(' ')));
					int itemId = Integer.parseInt(view.getIDText().getText());
					int quant = Integer.parseInt(view.getQuantityText().getText());
					Part aPart = model.getPartById(partId);
	
	
					InventoryItem itm = new InventoryItem(aPart,loc,quant);
					itm.setId(itemId);
				
					boolean boo = false;
					for (int i = 0; i < model.getInventoryStock().size(); i++) {
						if (model.getInventoryStock().get(i).getId() ==(itm.getId())) {
							boo = true;
							System.out.print("found");
						}
					}
					
					if (boo) {
	
	
							
							
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							
							e1.printStackTrace();
						}
						model.deleteItem(itm);
						view.getMaster().runUpdates();
						view.hideInternalFrame();
	
					}
					else {
						view.errorWindow("Item does not extist");
					}
				}
				
				
			}
			
		}
	}

}
