package parts_inventory;


import Models.ProductTemplateModel;
import Models.ProductTemplate;


public interface TemplateModelObserver {
	abstract public void updateObserver(ProductTemplateModel m, ProductTemplate p);

}