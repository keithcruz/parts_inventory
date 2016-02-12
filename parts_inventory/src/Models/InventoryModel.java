package Models;

import java.util.*;

import parts_inventory.InventoryModelItemObserver;
import parts_inventory.InventoryModelObserver;



/**
 * InventoryModel class keeps an array list of parts objects and provides methods for adding new parts,
 *  deleting parts and retrieving the parts list.
 * @author Keith Cruz, Brendan Nelson-Weiss
 *
 */
public class InventoryModel {
	private InventoryModelGateway ig;
	private ArrayList<Part> parts;
	private ArrayList<InventoryItem> inventoryStock;
	private ArrayList<InventoryModelObserver> observers = new ArrayList<InventoryModelObserver>();
	private ArrayList<InventoryModelItemObserver> iObservers = new ArrayList<InventoryModelItemObserver>();
	private Session pressian = new Session();
	
	
	public InventoryModel(InventoryModelGateway ig) {
		this.ig = ig;
		this.parts = ig.getPartsList();
		this.inventoryStock = ig.getItemsList();
		pressian = new Session();

	}
	
	public void registerObserver(InventoryModelObserver o) {
		observers.add(o);
	}
	public void registerItemObserver(InventoryModelItemObserver o)
	{
		iObservers.add(o);
	}
	
	public void updateObservers(Part p) {
		for(InventoryModelObserver o : observers) {
			o.updateObserver(this, p);
		}
			
	}
	public void updateItemObservers(InventoryItem item) {
			for(InventoryModelItemObserver o : iObservers) {
				o.updateObserver(this, item);
			}
	}
	
	/**
	 * newPart creates a new part and adds it to the parts ArrayList.
	 * @param name The name of the new part.
	 * @param vendor The vendor of the new part.
     */

	public void newPart(String name,String vendor, String uoq, String exNum) {

		Part part = new Part(name, vendor, uoq,exNum);


		

		
		if (parts.contains(part)) {
			System.out.println("part already in inventory");
		}
		else {
			ig.addPart(part);
			parts = ig.getPartsList();
			//parts.add(part);
			
		}
		
		updateObservers(part);
	}
	
	
	//add an inventory item
	public void newItem(Part part, String loc, int quantity) {
		boolean existingPart = false;
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getID() == part.getID()) {
				existingPart = true;
			}
		}
		
		if (!existingPart) {
			throw new IllegalArgumentException("new inventory item must be an existing part");
		}
		else {
			for(int i = 0; i < inventoryStock.size(); i++) {
				if (inventoryStock.get(i).getPart() != null && inventoryStock.get(i).getPart().getID() == part.getID() && inventoryStock.get(i).getLocation().equals(loc)) {
					throw new IllegalArgumentException("new item can't have same part and location as existing item");
				}
			}
			InventoryItem item = new InventoryItem(part, loc, quantity);
			ig.addInventory(item);
			inventoryStock = ig.getItemsList();
			updateItemObservers(item);
		}
	}

	/**
	 * Add a new product to the inventory.  Returns true on success fals otherwise.
	 * @param template
	 * @param loc
	 * @return bool
	 */
	public boolean newProduct(ProductTemplate template, String loc) {
		boolean bool = ig.addProduct(template, loc);
		if (bool) {
			InventoryItem item = new InventoryItem(template.getID(), template.getDescription(), loc, 1);
			inventoryStock = ig.getItemsList();
			
			updateItemObservers(item);
		}
		return bool;
	}
	
	/**
	 * editPart assumes the part to be edited and the part name is the same for each part.  Will remove the part
	 * from the inventory and then put the part back with the new information that was passed to editPart.
	 * @param name The new name.
	 * @param vendor The new vendor.
     */
	


	public void editPart(String name, String vendor, String uoq, String exNum, int id) {

		
		Part part = getPartByID(id);
		part.setName(name);
		part.setVendor(vendor);
		part.setUnit(uoq);
		part.setExNum(exNum);
		
		ig.updatePart(id,  name,  vendor, uoq, exNum);

		
		
		for (int i = 0; i < inventoryStock.size(); i++) {
			if (inventoryStock.get(i).getPart() != null && inventoryStock.get(i).getPart().getID() == part.getID()) {
				String stamp = setStamp(inventoryStock.get(i).getId());
				editItem(part, inventoryStock.get(i).getId(),
						inventoryStock.get(i).getLocation(), inventoryStock.get(i).getQuantity(), stamp);
			}
//			if (i.getPart().getID() == part.getID()) {
//				editItem(i.getPart(), i.getId(), i.getLocation(), i.getQuantity());
//			}
		}
		
		parts = ig.getPartsList();
		updateObservers(part);
	}
	
	
	
	//Edit an inventory item.
	public boolean editItem(Part part, int id, String loc, int quant, String stamp) {
		Boolean inParts = false;
		for (int i = 0; i < parts.size(); i++) {

			if (part != null && parts.get(i).getID() == part.getID()) {
				inParts = true;
			}
		}
		
		if (part != null && !inParts) {
			throw new IllegalArgumentException("Part must exist in parts database before adding it to inventory");

		}
		
		
		//InventoryItem item = getItemByID(id);
		/*if (item.getLocation().equals(loc) && item.getPart().getID() == part.getID() && item.getQuantity() == quant) {
			throw new IllegalArgumentException("Must change location, part or quantity or select cancel");
		}*/
		Boolean bool = ig.updateItem(id, part, loc, quant, stamp);
		if (bool) {
			inventoryStock = ig.getItemsList();
		
			InventoryItem item = getItemByID(id);
			updateItemObservers(item);
		}
		return bool;
	}
	
	public String setStamp(int id) {
		return ig.setStartStamp(id);
	}
	
	/**
	 * deletePart removes the part from the ArrayList of parts.
	 * @param part The part to be removed.
	 */
	public void deletePart(Part part) {

		for (int i = 0; i < inventoryStock.size(); i++) {
			if (inventoryStock.get(i).getPart() != null && inventoryStock.get(i).getPart().getID() == part.getID()) {
				throw new IllegalArgumentException("can't delete a part in inventory");
			}
		}
		
		ig.deletePart(part);
		parts = ig.getPartsList();
		
		//updateObservers();
	}
	
	//Delete an inventory item
	public void deleteItem(InventoryItem item) {
		if (item.getQuantity() != 0) {
			throw new IllegalArgumentException("Inventory quantity must be zero before deletion.");
		}
		
		ig.deleteItem(item);
		inventoryStock = ig.getItemsList();
		
	}
	
	/**
	 * getParts returns the ArrayList of parts.
	 * @return The ArrayList of parts.
	 */
	public ArrayList<Part> getParts() {
		return parts;
	}
	public String[] getPartStrings()
	{
		ArrayList<String> tmp = new ArrayList<String>();
		for(Part p : parts )
		{
			tmp.add(Integer.toString(p.getId()) + " - " + p.getName() );
		}
		return tmp.toArray(new String[tmp.size()]);
	}
	public String getPartidString(int id)
	{
		return( Integer.toString(getPartById(id).getId()) + " - " + getPartById(id).getName());
	}
	
	//Get the list of items
	public ArrayList<InventoryItem> getItems() {
		return inventoryStock;
	}
	
	//Get a part by its id
	public Part getPartByID(int id) {
		Part p = null;
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getID() == id) {
				p = parts.get(i);
			}
		}
		return p;
	}
	
	//Get an inventory item by its id
	public InventoryItem getItemByID(int id) {
		InventoryItem item = null;
		for (int i = 0; i < inventoryStock.size(); i++) {
			if (inventoryStock.get(i).getId() == id) {
				item = inventoryStock.get(i);
			}
		}
		return item;
	}
	
	public Part getByName(String n) {
		Part p = null;
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getName().equals(n)) {
				p = parts.get(i);
				
			}
		}
		return p;
		
	}
	public Part getPartById(int n) {
		Part p = null;
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getId() == (n)) {
				p = parts.get(i);
				
			}
		}
		return p;
		
	}
	public InventoryItem getItemById(int aID) {
		InventoryItem itm = null;
		for (int i = 0; i < inventoryStock.size(); i++) {
			if (inventoryStock.get(i).getId() == aID) {
				itm = inventoryStock.get(i);
				
			}
		}
		return itm;
		
	}
	
	public boolean inParts(int id)
	{
		boolean ret = false;
		for(Part p : this.parts)
		{
			if(p.getId()== id)
			{
				ret = true;
			}
		}
		return (ret);
	}

	public ArrayList<InventoryItem> getInventoryStock() {
		return inventoryStock;
	}

	public void editInventoryStock(ArrayList<InventoryItem> inventoryStock) {
		this.inventoryStock = inventoryStock;
	}
	public boolean inInventory(int aID) {
		for (int i = 0; i < inventoryStock.size(); i++) {
			if (inventoryStock.get(i).getId()== aID) {
				return true;
				
			}
		}
		return false;
	}


	//close all connections
	public void close() {
		ig.closeAll();
	}
	
	public boolean checkInventoryforPart(Part p)
	{
		boolean ret = false;
		for(InventoryItem itm : this.inventoryStock)
		{
			if(itm.getPart() != null && itm.getPart().equals(p))
			{
				ret = true;
			}
		}
		return (ret);
	}
	public boolean inItems(int id)
	{
		boolean ret = false;
		for(InventoryItem itm : this.inventoryStock)
		{
			if(itm.getId()== id)
			{
				ret = true;
			}
		}
		return (ret);
	}
	public void newItem(InventoryItem item)
	{
		if (inventoryStock.contains(item)) {
			System.out.println("Item already in Stock");
		}
		else {
			inventoryStock.add(item);
		}
	}

	/**
	 * @return the pressian
	 */
	public Session getSession() {
		return this.pressian;
	}

	/**
	 * @param pressian the pressian to set
	 */
	public void setSession(Session pressian) {
		this.pressian = pressian;
	}
	
	
}
