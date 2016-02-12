package parts_inventory;

import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;





import Models.ProductTemplateModel;
import Models.ProductTemplate;
import Models.InventoryModel;
import Views.AddRProductView;
import Views.TemplateListView;
import Views.EditTemplateView;



/**
 * InventoryViewController class controls interaction. between the InventoryView and the InventoryModel.
 * @author Keith Cruz, Brendan Nelson-Weiss
 *
 */
public class TListViewController implements ActionListener {
	private ProductTemplateModel model;

	private TemplateListView tempView;
	private ArrayList<EditTemplateView> tEdits;
	private ProductTemplate editedTemp;
	private InventoryModel iMod;
	
	/**
	 * InventoryViewController constructor takes arguments for an InventoryModel and InventoryView object.
	 * @param model The InventoryModel that will be controlled.
	 * @param view The InventoryView that will be controlled.
	 */

	public TListViewController(ProductTemplateModel model, InventoryModel iMod, TemplateListView tempView) {
		this.model = model;
		this.iMod = iMod;
		this.tempView = tempView;
		this.tEdits = new ArrayList<EditTemplateView>();
		this.editedTemp = null;
	}
	
	public ProductTemplate getEditedTemp() {
		return editedTemp;
	}
	
	public void setEditedTemp(int n) {
		editedTemp = model.getProductByID(n);
		
	}
	
	public EditTemplateView getEdit(String n) {
		EditTemplateView e = null;
		for (int i = 0; i < tEdits.size(); i++) {
			if (tEdits.get(i).getIdText().getText().equals(n)) {
				e = tEdits.get(i);
				
			}
		}
		return e;
		
	}
	
	public ArrayList<EditTemplateView> getTempEdits() {
		return tEdits;
	}

	
	/**
	 * actionPerformed performs various actions based on the button or action pressed by the user.
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		
		if (command.equals("Edit Template")) {
			ProductTemplate t = null;
				if (!tempView.getList().isSelectionEmpty()) {
					String str = tempView.getList().getSelectedValue().toString();
					String s[] = str.split(" +");
					t = model.getProductByID(Integer.parseInt(s[0]));
					editedTemp = t;


					EditTemplateView editView = new EditTemplateView(tempView.getMaster(),model,iMod, "Edit Template", t);
					tempView.getMaster().openMDIChild(editView);

					
					tEdits.add(editView);
					model.registerObserver(editView); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< NEED TEMPLATE OBSERVER
					

					EditTemplateController c = new EditTemplateController(model, editView, this);

					editView.registerListener(c);
					
					editView.setVisible(true);
			}
			
		}
		else if (command.equals("Add Template")){


			ProductTemplate t = new ProductTemplate();



			EditTemplateView editView = new EditTemplateView(tempView.getMaster(),model,iMod, "Add Template", t);
			tempView.getMaster().openMDIChild(editView);
			EditTemplateController c = new EditTemplateController(model, editView, this);

			editView.registerListener(c);
			editView.setVisible(true);
			
		}
		else if (command.equals("Build Product")){
			AddRProductView addPView = new AddRProductView(tempView.getMaster(),model,"Build Product");
			tempView.getMaster().openMDIChild(addPView);
			BuildProductViewController c = new BuildProductViewController(iMod, model, addPView, this);
			addPView.registerListener(c);
			addPView.setVisible(true);
		}


	}

}