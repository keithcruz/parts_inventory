package parts_inventory;
import java.awt.event.ActionListener;


import java.awt.event.ActionEvent;
import java.util.ArrayList;

import Models.InventoryModel;
import Models.Part;
import Views.EditPartView;


/**
 * AddEditController class controls interaction. between the AddEditView and the InventoryModel.
 * @author Keith Cruz, Brendan Nelson-Weiss
 *
 */
public class EditPartController implements ActionListener {
	private InventoryModel model;
	private EditPartView view;

	private InventoryViewController iController;

	
	/**
	 * AddEditController constructor takes arguments for an InventoryModel and AddEditView.
	 * @param model The InventoryModel to be controlled.
	 * @param view The AddEditView to be controlled.
	 */

	public EditPartController(InventoryModel model, EditPartView view, InventoryViewController iController) {
		this.model = model;
		this.view = view;
		this.iController = iController;

	}
	
	/**
	 * actionPerformed performs various actions based on the button or action pressed by the user.
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		String pattern = "^[a-zA-Z0-9]*$";

		String symANPattern = "^[^\t^\n^\f^\r]*$";
		
		if (command.equals("Save")) {
			if (!view.getNameText().getText().matches(pattern) || view.getNameText().getText().length() > 20 || view.getNameText().getText().equals("")) {
				view.errorWindow("part name must be alphanumeric and less than 20 characters.");
			}

			else if (!view.getVendorText().getText().matches(pattern) || view.getVendorText().getText().length() > 255 || view.getNameText().getText().equals("")) {
				view.errorWindow("vendor must be alphanumeric and less than 255 characters.");
			}
			else if(!view.getUnitText().getText().equals("Linear Feet") && !view.getUnitText().getText().equals("Pieces")) {
				view.errorWindow("unit must either be Linear Feet or Pieces");
			}

			else 
			{
				/*String loc = view.getLocText().getText();*/
				 if(view.getExNumText().getText() != "")
				{
					if (!view.getExNumText().getText().matches(symANPattern) || view.getExNumText().getText().length() > 50) {
						view.errorWindow("External part number must be alphanumeric or symbols and a maximum of 50 characters.");
					} 
				}
				String nm = view.getNameText().getText();
				String ven = view.getVendorText().getText();
				String uni = view.getUnitText().getText();
				String exNum = view.getExNumText().getText();
				//System.out.println(view.getTpe());
				

				
				if (view.getTpe().equals("Edit Part")) {
					if(!(model.getSession().isCanAddParts()))
					{
						view.errorWindow("Insufficient Priveleges to act");
					}
					else
					{
					


						int id = Integer.parseInt(view.getIdText().getText());
						model.editPart( nm, ven, uni,exNum, id);
						
	
	
						if (model.getByName(iController.getEditedPart().getName()) != null && !iController.getEditedPart().getName().equals(nm)) {
							model.deletePart(iController.getEditedPart());
						}
						iController.setEditedPart(nm);
	
						view.getMaster().runUpdates();
						view.hideInternalFrame();
					}

				}
				else if (view.getTpe().equals("Add Part")) {
					
					if(!(model.getSession().isCanAddParts()))
					{
						view.errorWindow("Insufficient Priveleges to act");
					}
					else
					{
					 

						//model.newPart(nm,  ven, uni,exNum);
//						Part p = new Part(nm,ven,uni,exNum);
//						p.setId(model.getPartIdCount());
//						model.incPartIdCount();
						model.newPart(nm, ven, uni, exNum);
						
						



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
			
			if(!(model.getSession().isCanDeleteParts()))
			{
				view.errorWindow("Insufficient Priveleges to act");
			}
			else
			{

			
					String id = view.getIdText().getText();
					Part p = model.getPartById(Integer.parseInt(id));
				
	
					boolean boo = false;
					if (!id.equals("")) {
						boo = true;
					}
	
					
					if (boo) {
						if(model.checkInventoryforPart(p))
						{
							view.errorWindow("Cannot delete a part that is still used by an inventory item");
						}
						else
						{
							ArrayList<EditPartView> edits = iController.getPartEdits();
							for (EditPartView edit : edits) {
								if (edit.getIdText().getText().equals(id)) {
									edit.setVisible(false);
								}
							
							}
							try {
								Thread.sleep(500);
							} catch (InterruptedException e1) {
								 //TODO Auto-generated catch block
								e1.printStackTrace();
							}
							model.deletePart(p);
	
							view.getMaster().runUpdates();
							view.hideInternalFrame();
	
						}
					}
					else {
						view.errorWindow("part does not extist");
					}
			}
				
				
		}
			
	}
}


