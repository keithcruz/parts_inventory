package Models;

/**
 * Session class creates a session with access rights set.
 * 
 *
 */
public class Session {
	private User user;
	private boolean canViewProductTemplates;
	private boolean canAddProductTemplates;
	private boolean canDeleteProductTemplates;
	private boolean canCreateProducts;
	private boolean canViewInventory;
	private boolean canAddInventory;
	private boolean canViewParts;
	private boolean canAddParts;
	private boolean canDeleteParts;
	private boolean canDeleteInventory;
	
	/**
	 * No arg constructor that sets the default access values.
	 */
	public Session() {
		this.canViewProductTemplates = false;
		this.canAddProductTemplates = false;
		this.canDeleteProductTemplates = false;
		this.canCreateProducts = false;
		this.canViewInventory = false;
		this.canAddInventory = false;
		this.canViewParts = false;
		this.canAddParts = false;
		this.canDeleteParts = false;
		this.canDeleteInventory = false;
	}
	
	
	/**
	 * Constructor takes a string as a role that will determine the access rights.
	 * @param role
	 */
	public Session(String role) {
		this.canViewInventory = true;
		this.canViewParts = true;
		
		if (role.equals("Production Manager")) {
			this.canViewProductTemplates = true;
			this.canAddProductTemplates = true;
			this.canDeleteProductTemplates = true;
			this.canCreateProducts = true;
			this.canViewInventory = true;
			this.canAddInventory = false;
			this.canViewParts = true;
			this.canAddParts = false;
			this.canDeleteParts = false;
			this.canDeleteInventory = false;
		}
		else if (role.equals("Inventory Manager")) {
			this.canViewProductTemplates = false;
			this.canAddProductTemplates = false;
			this.canDeleteProductTemplates = false;
			this.canCreateProducts = false;
			this.canViewInventory = true;
			this.canAddInventory = true;
			this.canViewParts = true;
			this.canAddParts = true;
			this.canDeleteParts = false;
			this.canDeleteInventory = false;
		}
		else if (role.equals("Admin")) {
			this.canViewProductTemplates = true;
			this.canAddProductTemplates = true;
			this.canDeleteProductTemplates = true;
			this.canCreateProducts = true;
			this.canViewInventory = true;
			this.canAddInventory = true;
			this.canViewParts = true;
			this.canAddParts = true;
			this.canDeleteParts = true;
			this.canDeleteInventory = true;
		}
	}
	
	
	/**
	 * Getters and setters.
	 * 
	 */
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isCanViewProductTemplates() {
		return canViewProductTemplates;
	}
	public void setCanViewProductTemplates(boolean canViewProductTemplates) {
		this.canViewProductTemplates = canViewProductTemplates;
	}
	public boolean isCanAddProductTemplates() {
		return canAddProductTemplates;
	}
	public void setCanAddProductTemplates(boolean canAddProductTemplates) {
		this.canAddProductTemplates = canAddProductTemplates;
	}
	public boolean isCanDeleteProductTemplates() {
		return canDeleteProductTemplates;
	}
	public void setCanDeleteProductTemplates(boolean canDeleteProductTemplates) {
		this.canDeleteProductTemplates = canDeleteProductTemplates;
	}
	public boolean isCanCreateProducts() {
		return canCreateProducts;
	}
	public void setCanCreateProducts(boolean canCreateProducts) {
		this.canCreateProducts = canCreateProducts;
	}
	public boolean isCanViewInventory() {
		return canViewInventory;
	}
	public void setCanViewInventory(boolean canViewInventory) {
		this.canViewInventory = canViewInventory;
	}
	public boolean isCanAddInventory() {
		return canAddInventory;
	}
	public void setCanAddInventory(boolean canAddInventory) {
		this.canAddInventory = canAddInventory;
	}
	public boolean isCanViewParts() {
		return canViewParts;
	}
	public void setCanViewParts(boolean canViewParts) {
		this.canViewParts = canViewParts;
	}
	public boolean isCanAddParts() {
		return canAddParts;
	}
	public void setCanAddParts(boolean canAddParts) {
		this.canAddParts = canAddParts;
	}
	public boolean isCanDeleteParts() {
		return canDeleteParts;
	}
	public void setCanDeleteParts(boolean canDeleteParts) {
		this.canDeleteParts = canDeleteParts;
	}
	public boolean isCanDeleteInventory() {
		return canDeleteInventory;
	}
	public void setCanDeleteInventory(boolean canDeleteInventory) {
		this.canDeleteInventory = canDeleteInventory;
	}
}
