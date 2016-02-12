package parts_inventory;

import java.awt.event.ActionListener;


import java.awt.event.ActionEvent;
import java.util.ArrayList;

import Models.InventoryModel;
import Models.Part;
import Models.ProductTemplateModel;
import Models.ProductTemplate;
import Views.EditPartView;
import Views.AddRProductView;



/**
 * AddEditController class controls interaction. between the AddEditView and the InventoryModel.
 * @author Keith Cruz, Brendan Nelson-Weiss
 *
 */
public class BuildProductViewController implements ActionListener {
	private InventoryModel model;
	private ProductTemplateModel tModel;
	private AddRProductView view;

	private TListViewController iController;

	
	/**
	 * AddEditController constructor takes arguments for an InventoryModel and AddEditView.
	 * @param model The InventoryModel to be controlled.
	 * @param view The AddEditView to be controlled.
	 */

	public BuildProductViewController(InventoryModel model, ProductTemplateModel model2 ,AddRProductView view, TListViewController iController) {
		this.model = model;
		this.tModel = model2;
		this.view = view;
		this.iController = iController;

	}
	
	/**
	 * actionPerformed performs various actions based on the button or action pressed by the user.
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		
		if (command.equals("Build")) {
			if(!(model.getSession().isCanCreateProducts()))
			{
				view.errorWindow("Insufficient Priveleges to act");
			}
			else if(view.getLocSelect().getSelectedItem().equals("Unknown"))
			{
				view.errorWindow("Must pick a known location");
			}
			else
			{
				boolean success = false;
				String str = view.getTemplateSelect().getSelectedItem().toString();
				String s[] = str.split(" +");
				int blarg = Integer.parseInt(s[0]);
				ProductTemplate t = tModel.getProductByID(blarg);
				if(t == null)
				{
					success = false;
				}
				else
				{
					success = model.newProduct(t, view.getLocSelect().getSelectedItem().toString());
				}	
				if(success)
				{
					view.getMaster().runUpdates();
					view.hideInternalFrame();
				}
				else
				{
					view.errorWindow("Error Building Product");
				}
			}
	
		}
			
		
		
		if (command.equals("Cancel")) {

			view.hideInternalFrame();
		}
		
			
	}
}


