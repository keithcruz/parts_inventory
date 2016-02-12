package parts_inventory;


import Models.InventoryModel;
import Models.Part;


public interface InventoryModelObserver {
	abstract public void updateObserver(InventoryModel m, Part p);

}
