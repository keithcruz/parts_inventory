package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class InventoryModelGateway {
	
	private final String URL = "jdbc:mysql://devcloud.fulgentcorp.com:3306/znw312?zeroDateTimeBehavior=convertToNull";
	private final String USER = "znw312";
	private final String PW = "OqXRT961O0xzt4Ha1uHy";
	private final String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
	private Random rnd = new Random();
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	private ArrayList<Part> parts;
	private ArrayList<InventoryItem> items;
	
	//private String session;
	
	//Create the connection.
	public InventoryModelGateway() throws Exception{
		parts = new ArrayList<Part>();
		items = new ArrayList<InventoryItem>();
		connect();
		parts = getParts();
		items = getInventory();
		
		
		
	}
	
	public void connect() throws Exception{
		
		MysqlDataSource myDS = null;
		 try {
	        	myDS = new MysqlDataSource();
	        	myDS.setURL(URL);
	        	myDS.setUser(USER);
	        	myDS.setPassword(PW);
	        	
	        } catch(RuntimeException e) {
	        	System.out.println("connection failed in first try");
	            e.printStackTrace();
	           
	        }
		 DataSource dsource = myDS;
		 if (dsource == null) {
			 throw new Exception("null DataSource");
		 }
		 try {
			 conn = myDS.getConnection();
		 }catch(SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println("connection failed in second try");
		}
	}
	
	//close everything
	public void closeAll() {
		try {
			rs.close();
			ps.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//get the parts table and return an array list of it.
	public ArrayList<Part> getParts(){
		ps = null;
		rs = null;
		//parts = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM parts_table");
			rs = ps.executeQuery();
			//rs.first();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(rs == null) {
			System.out.println("parts_table empty");
			return parts;
		}
		else {
			try {
				while(rs.next()) {
					int id = rs.getInt("id");
					String pNum = rs.getString("part_number");
					String pName = rs.getString("part_name");
					String vendor = rs.getString("vendor");
					String uoq = rs.getString("u_of_q");
					String ext = rs.getString("ext_part_number");
					
					Part part = new Part(pNum, pName, vendor, uoq, ext);
					part.setID(id);
					//System.out.println(pName);
					parts.add(part);
					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return parts;
		
	}
	
	//add a part to the parts table and add to the array list.
	public void addPart(Part part) {
		ps = null;
		rs =  null;
		int id = 0;
		String pNum = part.partNumber();
		String pName = part.getName();
		String vendor = part.getVendor();
		String uoq = part.getUnit();
		String ext = part.getExNum();
		String insert = "INSERT INTO parts_table"
				+ "(part_number, part_name, vendor, u_of_q, ext_part_number) VALUES"
				+ "(?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pNum);
			ps.setString(2, pName);
			ps.setString(3, vendor);
			ps.setString(4, uoq);
			ps.setString(5, ext);
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getInt(1);
			}
			
			part.setID(id);
			parts.add(part);
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//get the inventory table and return an array list of it.
	public ArrayList<InventoryItem> getInventory() {
		ps = null;
		rs = null;
		//items = null;
		
		try {
			ps = conn.prepareStatement("SELECT * from inventory");
			rs = ps.executeQuery();
			//rs.first();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(rs == null) {
			System.out.println("inventory table empty");
			return items;
		}
		else {
			try {
				while(rs.next()) {
					int id = rs.getInt("id");
					int part_id = rs.getInt("part_id");
					int product_id = rs.getInt("product_template_id");
					String loc = rs.getString("location");
					int quant = rs.getInt("quantity");
					
					InventoryItem item = null;
					if (product_id != 0) {
						String description = getDescription(product_id);
						item = new InventoryItem(product_id, description, loc, quant);
					}
					else {
						Part p = getPartByID(part_id);
						item = new InventoryItem(p, loc, quant);
					}
					
					item.setId(id);
					
					items.add(item);
					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return items;
		
	}
	
	//add an inventory item to the inventory table and add it to the array list.
	public void addInventory(InventoryItem item) {
		ps = null;
		rs =  null;
		int id = 0;
		
		int part_id = item.getPart().getID();
		String loc = item.getLocation();
		int quant = item.getQuantity();
		String insert = "INSERT INTO inventory"
				+ "(part_id, location, quantity) VALUES"
				+ "(?,?,?)";
		try {
			ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, part_id);
			ps.setString(2, loc);
			ps.setInt(3, quant);
			
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			while(rs.next()) {
				id = rs.getInt(1);
			}
			item.setId(id);
			items.add(item);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	//add a product to the inventory.
		public boolean addProduct(ProductTemplate template, String location) {
			PreparedStatement ps1 = null;
			ResultSet rs1 =  null;
			int id = 0;
			
			ArrayList<Part> prodParts = template.getParts();
			HashMap<Part, Integer> hash = template.getHashMap();
			boolean success = true;
			
			/*int part_id = item.getPart().getID();
			String loc = item.getLocation();
			int quant = item.getQuantity();*/
			
			
			String insert = "INSERT INTO inventory"
					+ "(part_id, product_template_id, location, quantity) VALUES"
					+ "(?,?,?,?)";
			try {
				conn.setAutoCommit(false);
				
				ArrayList<InventoryItem> matches = new ArrayList<InventoryItem>();
				
				for (Part p : prodParts) {
					for (InventoryItem i : items) {
						if (i.getPart() != null && p.getId() == i.getPart().getId()) {
							matches.add(i);
						}
					}
				}
				
				outerloop:
				for (Part p : prodParts) {
					for (InventoryItem i : matches) {
						if (p.getId() == i.getPart().getId() && i.getLocation().equals("Facility 1 Warehouse 1") && i.getQuantity() >= hash.get(p)) {
							if (i.getQuantity() > hash.get(p)) {
								int newQuant = i.getQuantity() - hash.get(p);
								updateItem(i.getId(), i.getPart(), i.getLocation(), newQuant, getEditStamp(i.getId()));
								break outerloop;
							}
							else if (i.getQuantity() == hash.get(p)) {
								deleteItem(i);
								break outerloop;
							}
						}
						else if (p.getId() == i.getPart().getId() && i.getLocation().equals("Facility 1 Warehouse 2") && i.getQuantity() >= hash.get(p)) {
							if (i.getQuantity() > hash.get(p)) {
								int newQuant = i.getQuantity() - hash.get(p);
								updateItem(i.getId(), i.getPart(), i.getLocation(), newQuant, getEditStamp(i.getId()));
								break outerloop;
							}
							else if (i.getQuantity() == hash.get(p)) {
								deleteItem(i);
								break outerloop;
							}
						}
						else if (p.getId() == i.getPart().getId() && i.getLocation().equals("Facility 2") && i.getQuantity() >= hash.get(p)) {
							if (i.getQuantity() > hash.get(p)) {
								int newQuant = i.getQuantity() - hash.get(p);
								updateItem(i.getId(), i.getPart(), i.getLocation(), newQuant, getEditStamp(i.getId()));
								break outerloop;
							}
							else if (i.getQuantity() == hash.get(p)) {
								deleteItem(i);
								break outerloop;
							}
						}
						else {
							success = false;
							break;
						}
					}
				}
				
				
				ps1 = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
				ps1.setInt(1, 0);
				ps1.setInt(2, template.getID());
				ps1.setString(3, location);
				ps1.setInt(4, 1);
				
				
				ps1.executeUpdate();
				rs1 = ps1.getGeneratedKeys();
				while(rs1.next()) {
					id = rs1.getInt(1);
				}
				InventoryItem item = new InventoryItem(template.getID(), template.getDescription(), location, 1);
				item.setId(id);
				if (success) {
					conn.commit();
					items.add(item);
				}
				else {
					conn.rollback();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps1 != null) {
						ps1.close();
					}
					if (rs1 != null) {
						rs1.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			return success;
			
		}
	
	public void deletePart(Part part) {
		int id = part.getID();
		ps = null;
		String delete = "DELETE FROM parts_table WHERE id = ?";
		
		try {
			ps = conn.prepareStatement(delete);
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch (SQLException e ) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		parts.remove(part);
	}
	
	public void deleteItem(InventoryItem item) {
		int id = item.getId();
		ps = null;
		String delete = "DELETE FROM inventory WHERE id = ?";
		
		try {
			ps = conn.prepareStatement(delete);
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch (SQLException e ) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		items.remove(item);
	}
	
	//edit a part
	public void updatePart(int id, String name, String vendor, String uoq, String exNum) {
		Part part = getPartByID(id);
		parts.remove(part);
		part.setName(name);
		part.setVendor(vendor);
		part.setUnit(uoq);
		part.setExNum(exNum);
		
		
		ps = null;
		String update = "UPDATE parts_table SET part_name = ?, vendor = ?, u_of_q = ?, ext_part_number = ?"
				+ " WHERE id = ?";
		
		try {
			ps = conn.prepareStatement(update);
			ps.setString(1, name);
			ps.setString(2, vendor);
			ps.setString(3, uoq);
			ps.setString(4, exNum);
			ps.setInt(5, id);
			
			ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		parts.add(part);
	}
	
	
	/**
	 * Get the item_stamp of the current editing session and set the item_stamp to it.
	 * @param id
	 */
	public String setStartStamp(int id) {
		ps = null;
		rs = null;
		String start = "";
				
		try {
			ps = conn.prepareStatement("SELECT item_stamp FROM inventory WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(rs == null) {
			System.out.println("Item does not exist");
			
		}
		else {
			try {
				while(rs.next()) {
					if (rs.getString("item_stamp") != null){
						start = rs.getString("item_stamp").trim();
					}
					else {
						start = "0";
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return start;
	}
	
	/**
	 * get the description of the product template.
	 * @param pid
	 * @return description
	 */
	public String getDescription(int pid) {
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String description = "";
				
		try {
			ps1 = conn.prepareStatement("SELECT description FROM product_templates WHERE id = ?");
			ps1.setInt(1, pid);
			rs1 = ps1.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(rs1 == null) {
			System.out.println("Template does not exist");
			
		}
		else {
			try {
				while(rs1.next()) {
					description = rs1.getString("description");
					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps1 != null) {
						ps1.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return description;
	}
	
	/**
	 * Get the the stamp of the current edit session.
	 * @param id
	 */
	public String getEditStamp(int id) {
		ps = null;
		rs = null;
		String sess = "";
				
		try {
			ps = conn.prepareStatement("SELECT item_stamp FROM inventory WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(rs == null) {
			System.out.println("Item does not exist");
			
		}
		else {
			try {
				while(rs.next()) {
					if (rs.getString("item_stamp") != null){
						sess = rs.getString("item_stamp").trim();
					}
					else {
						sess = "0";
					}
				
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return sess;
	}
	
	
	
	//edit an inventory item
	public boolean updateItem(int id, Part part, String loc, int quant, String stamp) {
		InventoryItem item = getItemByID(id);
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		boolean success = true;
		/**
		 * If it is a product must take parts from inventory.
		 */
		if (item.getProduct() != 0) {
			/*items.remove(item);
			item.setLocation(loc);
			item.setQuantity(quant);*/
			
			int prodID = item.getProduct();
			
			ps1 = null;
			String currStamp = getEditStamp(id);
			String update = "UPDATE inventory SET product_template_id = ?, location = ?, quantity = ?"
					+ " WHERE id = ?";
			
			if (!stamp.equals(currStamp)) {
				return false;
			}
			
			try {
				conn.setAutoCommit(false);
				
				
				if (quant > item.getQuantity()) {
					
					int diff = quant - item.getQuantity();
					ProductTemplate product = getProduct(prodID);
					ArrayList<Part> prodParts = product.getParts();
					HashMap<Part, Integer> hash = product.getHashMap();
				
				
					ArrayList<InventoryItem> matches = new ArrayList<InventoryItem>();
					//System.out.println(prodParts);
					//System.out.println(items);
					for (Part p : prodParts) {
						for (InventoryItem i : items) {
							if (i.getPart() != null && p.getId() == i.getPart().getId()) {
								matches.add(i);
							}
						}
					}
					outerloop:
					for (Part p : prodParts) {
						for (InventoryItem i : matches) {
							if (p.getId() == i.getPart().getId() && i.getLocation().equals("Facility 1 Warehouse 1") && i.getQuantity() >= (hash.get(p) * diff)) {
								if (i.getQuantity() > (hash.get(p) * diff)) {
									int newQuant = i.getQuantity() - (hash.get(p) * diff);
									updateItem(i.getId(), i.getPart(), i.getLocation(), newQuant, getEditStamp(i.getId()));
									break outerloop;
								}
								else if (i.getQuantity() == (hash.get(p) * diff)) {
									deleteItem(i);
									break outerloop;
								}
							}
							else if (p.getId() == i.getPart().getId() && i.getLocation().equals("Facility 1 Warehouse 2") && i.getQuantity() >= (hash.get(p) * diff)) {
								if (i.getQuantity() > hash.get(p)) {
									int newQuant = i.getQuantity() - (hash.get(p) * diff);
									updateItem(i.getId(), i.getPart(), i.getLocation(), newQuant, getEditStamp(i.getId()));
									break outerloop;
								}
								else if (i.getQuantity() == (hash.get(p) * diff)) {
									deleteItem(i);
									break outerloop;
								}
							}
							else if (p.getId() == i.getPart().getId() && i.getLocation().equals("Facility 2") && i.getQuantity() >= (hash.get(p) * diff)) {
								if (i.getQuantity() > (hash.get(p) * diff)) {
									int newQuant = i.getQuantity() - (hash.get(p) * diff);
									updateItem(i.getId(), i.getPart(), i.getLocation(), newQuant, getEditStamp(i.getId()));
									break outerloop;
								}
								else if (i.getQuantity() == (hash.get(p) * diff)) {
									deleteItem(i);
									break outerloop;
								}
							}
							else {
								success = false;
								break;
							}
						}
					}
				}
				
				
				ps1 = conn.prepareStatement(update);
				ps1.setInt(1, prodID);
				ps1.setString(2,  loc);
				ps1.setInt(3, quant);
				ps1.setInt(4, id);
				ps1.executeUpdate();
				
				
				if (success) {
					conn.commit();
					items.remove(item);
					item.setLocation(loc);
					item.setQuantity(quant);
					item.setProduct(prodID);
					items.add(item);
				}
				else {
					conn.rollback();
					
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps1 != null) {
						ps1.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return success;
		}
		
		else {
			int partID = part.getID();
			items.remove(item);
			item.setPart(part);
			item.setLocation(loc);
			item.setQuantity(quant);
			ps = null;
			String currStamp = getEditStamp(id);
			ps = null;
			String update = "UPDATE inventory SET part_id = ?, location = ?, quantity = ?"
					+ " WHERE id = ?";
			
			if (!stamp.equals(currStamp)) {
				return false;
			}
			
			try {
				ps = conn.prepareStatement(update);
				ps.setInt(1, partID);
				ps.setString(2,  loc);
				ps.setInt(3, quant);
				ps.setInt(4, id);
				
							
				ps.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			items.add(item);
			return true;
		}
	}
	
	
	
	
	
	//get the product_templates table and return an array list of it.
			public ProductTemplate getProduct(int id){
				PreparedStatement ps1 = null;
				ResultSet rs1 = null;
				ProductTemplate product = null;
				
				
				try {
					ps1 = conn.prepareStatement("SELECT * FROM product_templates WHERE id = ?");
					ps1.setInt(1, id);
					rs1 = ps1.executeQuery();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
				if(rs1 == null) {
					System.out.println("product_templates empty");
					return product;
				}
				else {
					try {
						while(rs1.next()) {
							
							String pNum = rs1.getString("product_number");
							String description = rs1.getString("description");
							
							
							product = new ProductTemplate(pNum, description);
							product.setID(id);
							//System.out.println(pName);
							getProductParts(product);
							
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}finally {
						try {
							if (ps1 != null) {
								ps1.close();
							}
						}catch(SQLException e) {
							e.printStackTrace();
						}
					}
					
				}
				
				return product;
				
			}
	
	
	
	/**
	 * Gets the parts associated with a product template and sets the
	 * hashmap and arraylist of parts.
	 * @param product
	 */
	public void getProductParts(ProductTemplate product){
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		int pid = product.getID();
		
		try {
			ps1 = conn.prepareStatement("SELECT part_id, quantity FROM product_parts"
					+ " WHERE product_id = ?");
			ps1.setInt(1, pid);
			rs1 = ps1.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(rs1 == null) {
			System.out.println("product_templates empty");
			//return pids;
		}
		else {
			try {
				while(rs1.next()) {
					int id = rs1.getInt("part_id");
					Part part = getProductPart(id);
					int quant = rs1.getInt("quantity");
					product.addPart(part);
					product.setPartAmount(part, quant);

					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps1 != null) {
						ps1.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		//return pids;
		
	}
	
	
	
	
	/**
	 * Helper function that returns a Part object when given a part id.
	 * @param pid
	 * @return Part
	 */
	public Part getProductPart(int pid) {
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Part part = null;
		
		try {
			ps2 = conn.prepareStatement("SELECT * FROM parts_table"
					+ " WHERE id = ?");
			ps2.setInt(1, pid);
			rs2 = ps2.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(rs2 == null) {
			System.out.println("parts_table empty");
			return part;
		}
		else {
			try {
				while(rs2.next()) {
					int id = rs2.getInt("id");
					String pNum = rs2.getString("part_number");
					String pName = rs2.getString("part_name");
					String vendor = rs2.getString("vendor");
					String uoq = rs2.getString("u_of_q");
					String ext = rs2.getString("ext_part_number");
					
					part = new Part(pNum, pName, vendor, uoq, ext);
					part.setID(id);
											
					
					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (ps2 != null) {
						ps2.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return part;
		
	}
	
	
	
	
	
	//get a part from the array list by the id.
	public Part getPartByID(int id) {
		Part p = null;
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getID() == id) {
				p = parts.get(i);
			}
		}
		return p;
	}
	
	//get a item from the array list by the id.
	public InventoryItem getItemByID(int id) {
		InventoryItem item = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getId() == id) {
				item = items.get(i);
			}
		}
		return item;
	}
	
	
	//return the list of Parts
	public ArrayList<Part> getPartsList() {
		return parts;
	}
	
	//return the list of inventory items
	public ArrayList<InventoryItem> getItemsList() {
		return items;
	}
	
	
	/**
	 * Set the sessionNum
	 * @return seesionNum
	 */
	public String sessionNum()	{
		char[] text = new char[20];
		for (int i = 0; i < 20; i++)	{
			text[i] = charPool.charAt(rnd.nextInt(charPool.length()));
		}
		return new String(text);
	}

}
