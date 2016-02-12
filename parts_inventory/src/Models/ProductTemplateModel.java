package Models;

import java.util.*;

import parts_inventory.TemplateModelObserver;

public class ProductTemplateModel {
	private ProductTemplateGateway pg;
	private ArrayList<ProductTemplate> products;
	private ArrayList<TemplateModelObserver> observers = new ArrayList<TemplateModelObserver>();
	private Session pressian = new Session();
	
	/**
	 * Constructor takes a ProductTemplateGateway as an arg.
	 * @param pg
	 */
	public ProductTemplateModel(ProductTemplateGateway pg) {
		this.pg = pg;
		this.products = pg.getProductList();
		pressian = new Session();
	}
	
	public void registerObserver(TemplateModelObserver o) {
		observers.add(o);
	}
	public void updateObservers(ProductTemplate temp) {
		for(TemplateModelObserver o : observers) {
			o.updateObserver(this, temp);
		}
}
	
	/**
	 * get the arraylist of product templates.
	 * @return products
	 */
	public ArrayList<ProductTemplate> getProductTemplates() {
		return products;
	}
	
	
	/**
	 * Get a product template from tharraylist by the id given
	 * @param id
	 * @return product
	 */
	public ProductTemplate getProductByID(int id) {
		ProductTemplate product = null;
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getID() == id) {
				product = products.get(i);
			}
		}
		return product;
	}
	
	
	/**
	 * Add a product with the given description
	 * @param description
	 */
	public int addProduct(String description) {
		ProductTemplate product = new ProductTemplate(description);
		
		if (products.contains(product)) {
			System.out.println("part already in inventory");
		}
		else {
			pg.addProduct(product);
			products = pg.getProductList();
			
			
		}
		updateObservers(product);
		
		return(product.getID());
	}
	
	
	/**
	 * Add a product part with the given quantity.
	 * @param product
	 * @param part
	 * @param quant
	 */
	public void addProductPart(int productID, Part part, int quant) {
		ProductTemplate product = getProductByID(productID);
		
		
		if (product.getParts().contains(part)) {
			System.out.println("part already in product template");
		}
		else {
			pg.addProductPart(product.getID(), part.getId(), quant);
			product.addPart(part);
			product.setPartAmount(part, quant);
		}
		updateObservers(product);
		
	}
	
	
	/**
	 * Delete a product part from a products list of parts and it's hash map.
	 * @param productID
	 * @param partID
	 */
	public void deleteProductPart(int productID, int partID) {
		pg.deleteProductPart(productID, partID);
		ProductTemplate product = getProductByID(productID);
		Part part = null;
		
		//Getting the part from the products list of parts
		for (int i = 0; i < product.getParts().size(); i++) {
			if (product.getParts().get(i).getId() == partID) {
				part = product.getParts().get(i);
			}
		}
		
		product.getHashMap().remove(part);
		product.getParts().remove(part);
		updateObservers(product);
		
	}
	
	
	/**
	 * delete a product
	 * @param productID
	 */
	public void deleteProduct(int productID) {
		pg.deleteProduct(productID);
		products = pg.getProductList();
		updateObservers(products.get(0));
	}
	
	
	/**
	 * edit a product
	 * @param id
	 * @param pNum
	 * @param description
	 */
	public void editProduct(int id, String pNum, String description) {
		ProductTemplate product = getProductByID(id);
		product.setPartNumber(pNum);
		product.setDescription(description);
		
		pg.updateProduct(id, pNum, description);
		products = pg.getProductList();
		updateObservers(product);
		
	}
	
	
	/**
	 * edit a Product part
	 * @param id
	 * @param prevPart
	 * @param newPart
	 * @param quant
	 */
	public void editProductPart(int id, Part prevPart, Part newPart, int quant) {
		ProductTemplate product = getProductByID(id);
		for(int i = 0; i < product.getParts().size(); i++) {
			if (product.getParts().get(i).getId() == prevPart.getId()) {
				Part p = product.getParts().get(i);
				product.getParts().remove(p);
				product.getHashMap().remove(p);
			}
		}
		
		product.getParts().add(newPart);
		product.getHashMap().put(newPart, quant);
		pg.updateProductPart(id, prevPart,newPart, quant);
		products = pg.getProductList();
		updateObservers(product);
	}

	/**
	 * @return the pressian
	 */
	public Session getSession() {
		return pressian;
	}

	/**
	 * @param pressian the pressian to set
	 */
	public void setSession(Session pressian) {
		this.pressian = pressian;
	}
	
	
	
}
