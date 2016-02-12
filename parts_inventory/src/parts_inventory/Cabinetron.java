//Brendan Nelson-Weiss & Keith Cruz
package parts_inventory;
import java.awt.EventQueue;


import java.util.ArrayList;

import Models.InventoryModel;
import Models.InventoryModelGateway;
import Models.ProductTemplateGateway;
import Models.ProductTemplateModel;
import Views.MasterFrame;
import  Models.Authenticator;
import Models.User;





public class Cabinetron 
{
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					ArrayList<User> usrs = new ArrayList<User>();
					usrs.add(new User("Tom Jones","Tom@Jones.com"));
					usrs.add(new User("Sue Smith","Sue@Smith.com"));
					usrs.add(new User("Ragnar Nelson","Ragnar@Nelson.com"));
					ArrayList<String> accs= new ArrayList<String>();					
					accs.add("Tom@Jones.com,abc123,Production Manager");
					accs.add("Sue@Smith.com,qwe123,Inventory Manager");
					accs.add("Ragnar@Nelson.com,asd123,Admin");
					Authenticator secureMe = new Authenticator(accs);
					InventoryModelGateway ig = new InventoryModelGateway();
					InventoryModel model = new InventoryModel(ig);
					ProductTemplateGateway tg = new ProductTemplateGateway();
					ProductTemplateModel model2 = new ProductTemplateModel(tg);
		  

					MasterFrame master = new MasterFrame("Cabinetron",model,model2,secureMe,usrs);
					MasterController mC = new MasterController(master);
					master.registerListener(mC);
					master.createAndShowGUI();

				}
				catch (Exception e)
				{

					e.printStackTrace();
				}
			}
		});
	}
		
}
