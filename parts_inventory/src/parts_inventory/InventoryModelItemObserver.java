package parts_inventory;


import Models.InventoryItem;
import Models.InventoryModel;


public interface InventoryModelItemObserver {
	abstract public void updateObserver(InventoryModel m, InventoryItem itm);

}