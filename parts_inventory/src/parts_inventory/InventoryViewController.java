package parts_inventory;

import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import Models.InventoryItem;
import Models.InventoryModel;
import Models.Part;
import Views.EditInventoryView;
import Views.EditPartView;
import Views.InventoryStockView;
import Views.InventoryView;


/**
 * InventoryViewController class controls interaction. between the InventoryView and the InventoryModel.
 * @author Keith Cruz, Brendan Nelson-Weiss
 *
 */
public class InventoryViewController implements ActionListener {
	private InventoryModel model;
	private InventoryView partView;
	private InventoryStockView itemView;
	private ArrayList<EditPartView> pEdits;
	private ArrayList<EditInventoryView> iEdits;
	private Part editedPart;
	private InventoryItem editedItem;
	
	/**
	 * InventoryViewController constructor takes arguments for an InventoryModel and InventoryView object.
	 * @param model The InventoryModel that will be controlled.
	 * @param view The InventoryView that will be controlled.
	 */

	public InventoryViewController(InventoryModel model, InventoryStockView itemView) {
		this.model = model;
		this.itemView = itemView;
		this.pEdits = new ArrayList<EditPartView>();
		this.iEdits = new ArrayList<EditInventoryView>();
		this.editedPart = null;
		this.editedItem = null;
	}
	public InventoryViewController(InventoryModel model, InventoryView partView)
	{
		this.model = model;
		this.partView = partView;

		this.pEdits = new ArrayList<EditPartView>();
		this.iEdits = new ArrayList<EditInventoryView>();
		this.editedPart = null;
		this.editedItem = null;
	}
	
	public Part getEditedPart() {
		return editedPart;
	}
	
	public void setEditedPart(String n) {
		editedPart = model.getByName(n);
		
	}
	
	public EditPartView getEdit(String n) {
		EditPartView e = null;
		for (int i = 0; i < pEdits.size(); i++) {
			if (pEdits.get(i).getNameText().getText().equals(n)) {
				e = pEdits.get(i);
				
			}
		}
		return e;
		
	}
	
	public ArrayList<EditPartView> getPartEdits() {
		return pEdits;
	}
	public void setEditedItem(int id)
	{
		this.editedItem = model.getItemById(id);
	}
	public InventoryItem getEditedItem()
	{
			return editedItem;
	}
	public ArrayList<EditInventoryView> getItemEdits() {
		return iEdits;
	}
	
	/**
	 * actionPerformed performs various actions based on the button or action pressed by the user.
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		
		if (command.equals("Edit Part")) {
			Part p = null;
				if (!partView.getList().isSelectionEmpty()) {
					String str = partView.getList().getSelectedValue().toString();
					String s[] = str.split(" +");
					p = model.getPartByID(Integer.parseInt(s[0]));
					editedPart = p;

					EditPartView editView = new EditPartView(partView.getMaster(),model, "Edit Part", p);
					partView.getMaster().openMDIChild(editView);

					
					pEdits.add(editView);
					model.registerObserver(editView);
					

					EditPartController c = new EditPartController(model, editView, this);

					editView.registerListener(c);
					editView.setVisible(false);
					editView.setVisible(true);
			}
			
		}
		else if (command.equals("Add Part")){


			Part p = new Part( "", "", "Unknown","");



			EditPartView editView = new EditPartView(partView.getMaster(),model, "Add Part", p);
			partView.getMaster().openMDIChild(editView);
			EditPartController c = new EditPartController(model, editView, this);

			editView.registerListener(c);
			editView.setVisible(true);
			
		}
		else if (command.equals("Add Item")){


			InventoryItem itm = new InventoryItem( null, "", 0);



			EditInventoryView editView = new EditInventoryView(itemView.getMaster(),model, "Add Item", itm);
			itemView.getMaster().openMDIChild(editView);
			EditInventoryController c = new EditInventoryController(model, editView, this);

			editView.registerListener(c);
			editView.setVisible(true);
			
		}
		else if (command.equals("Edit Item")) {
			InventoryItem itm = null;
				if (!itemView.getList().isSelectionEmpty()) {
					String str = itemView.getList().getSelectedValue().toString();
					String s[] = str.split(" ");
					itm = model.getItemById(Integer.parseInt(s[2]));
					editedItem = itm;

					EditInventoryView editView = new EditInventoryView(itemView.getMaster(),model, "Edit Item", itm);
					itemView.getMaster().openMDIChild(editView);

					
					
					iEdits.add(editView);
					model.registerItemObserver(editView);
					

					EditInventoryController c = new EditInventoryController(model, editView, this);

					editView.registerListener(c);
					editView.setVisible(false);
					editView.setVisible(true);
			}
			
		}
	}

}
