package Models;


public class InventoryItem {
	
	private int id;
	private Part part;
	private int productID;
	private String description;
	private String location;
	private int quantity;
	
		
	public InventoryItem() {
		this.part = null;
		this.location = "";
		this.quantity = 0;
		
	}
	
	public InventoryItem(Part part, String location, int quantity) {
		this.part = part;
		this.location = location;
		this.quantity = quantity;
		
	}
	
	
	public InventoryItem(int productID, String description, String location, int quantity) {
		this.productID = productID;
		this.description = description;
		this.location = location;
		this.quantity = quantity;
	}
	
	/**
	 * get the product id
	 * @return productID
	 */
	public int getProduct() {
		return this.productID;
	}
	
	/**
	 * set the productID
	 * @param productID
	 */
	public void setProduct(int productID) {
		this.productID = productID;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String toString()
	{
		
		if (this.productID == 0) {
			return("Item ID: "+ Integer.toString(this.getId()) + "     Part ID: " + Integer.toString(this.part.getId()) + "   -   " + this.part.getName() + "      " + this.getLocation()+ "     " + this.getQuantity());
		}
		else {
			return("Item ID: "+ Integer.toString(this.getId()) + "     Product Template ID: " + Integer.toString(productID) + "   -   " + "Product Name: "  + description + "      " + this.getLocation()+ "     " + 
					this.getQuantity());
		}
	}
	public boolean equals(InventoryItem item) {
		return (this.id == item.getId() && this.part.equals(item.getPart()) && this.location.equals(item.getLocation()) &&  this.quantity == item.getQuantity() );


	}
	
	
	

}
