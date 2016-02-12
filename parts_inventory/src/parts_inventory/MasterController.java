package parts_inventory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import Models.User;
import Views.InventoryStockView;
import Views.InventoryView;
import Views.MasterFrame;
import Views.TemplateListView;

public class MasterController implements ActionListener{
	
	private MasterFrame view;
	
	public MasterController(MasterFrame f)
	{
		this.view = f;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		
		if(command.equals("Quit"))
		{
			
				view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
			
		}
		else if(command.equals("Parts List"))
		{
			
					if(!(view.getIModel().getSession().isCanViewParts()))
					{
						view.errorWindow("Insufficient Priveleges to act");
					}
					else
					{
						InventoryView child = new InventoryView(view, view.getIModel());
						InventoryViewController iController = new InventoryViewController(view.getIModel(), child);
						view.addToUpdatePanels(child);
						child.registerListener(iController);
						view.openMDIChild(child);
					}
				
		}
		else if(command.equals("Inventory"))
		{
					if(!(view.getIModel().getSession().isCanViewInventory()))
					{
						view.errorWindow("Insufficient Priveleges to act");
					}
					else
					{
						InventoryStockView child = new InventoryStockView(view, view.getIModel());
						InventoryViewController iController = new InventoryViewController(view.getIModel(), child);
						view.addToUpdatePanels(child);
						child.registerListener(iController);
						view.openMDIChild(child);
					}
					
			
		}
		else if(command.equals("Templates"))
		{
			
				
					if(!(view.getTModel().getSession().isCanViewProductTemplates()))
					{
						view.errorWindow("Insufficient Priveleges to act");
					}
					else
					{TemplateListView child = new TemplateListView(view, view.getTModel());
					TListViewController iController = new TListViewController(view.getTModel(), view.getIModel(), child);
					
					view.addToUpdatePanels(child);
					child.registerListener(iController);
					view.openMDIChild(child);
					}
				
		}
		else if(command.equals("Tom Jones"))
		{
			
					for (User u : view.getUsers())
					{
						if(u.getName().equals("Tom Jones"))
						{
							view.setNewTitle( view.getPTitle() + "- Tom Jones");
							view.getIModel().setSession(view.getAuth().Authenticate(u, "abc123"));
							view.getTModel().setSession(view.getAuth().Authenticate(u, "abc123"));
						}
					}
				
	
		}
		else if(command.equals("Sue Smith"))
		{
			
					for (User u : view.getUsers())
					{
						if(u.getName().equals("Sue Smith"))
						{
							view.setNewTitle( view.getPTitle() + "- Sue Smith");
							view.getIModel().setSession(view.getAuth().Authenticate(u, "qwe123"));
							view.getTModel().setSession(view.getAuth().Authenticate(u, "qwe123"));
						}
					}
				
	
		}
		else if(command.equals("Ragnar Nelson"))
		{
			
					for (User u : view.getUsers())
					{
						if(u.getName().equals("Ragnar Nelson"))
						{
							view.setNewTitle( view.getPTitle() + "- Ragnar Nelson");
							view.getIModel().setSession(view.getAuth().Authenticate(u, "asd123"));
							view.getTModel().setSession(view.getAuth().Authenticate(u, "asd123"));
						}
					}
				
	
		}
		
		
		
	}
	
	

}
