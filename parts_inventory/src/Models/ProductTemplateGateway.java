package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ProductTemplateGateway {
	
	private final String URL = "jdbc:mysql://devcloud.fulgentcorp.com:3306/znw312";
	private final String USER = "znw312";
	private final String PW = "OqXRT961O0xzt4Ha1uHy";
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	private ArrayList<ProductTemplate> products;
	
	/**
	 * No arg constructor connects to the DB.
	 * @throws Exception
	 */
	public ProductTemplateGateway() throws Exception{
		products = new ArrayList<ProductTemplate>();
		connect();
		products = getProducts();
	}
	
	
	/**
	 * connect method connects to the DB.
	 * @throws Exception
	 */
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
		
		
		
		//get the product_templates table and return an array list of it.
		public ArrayList<ProductTemplate> getProducts(){
			ps = null;
			rs = null;
			
			
			try {
				ps = conn.prepareStatement("SELECT * FROM product_templates");
				rs = ps.executeQuery();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			if(rs == null) {
				System.out.println("product_templates empty");
				return products;
			}
			else {
				try {
					while(rs.next()) {
						int id = rs.getInt("id");
						String pNum = rs.getString("product_number");
						String description = rs.getString("description");
						
						
						ProductTemplate product = new ProductTemplate(pNum, description);
						product.setID(id);
						//System.out.println(pName);
						products.add(product);
						getProductParts(product);
						
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
			
			return products;
			
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
						Part part = getPart(id);
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
		public Part getPart(int pid) {
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
		
		
		/**
		 * Add a product to the product_templates table
		 * @param product
		 */
		public void addProduct(ProductTemplate product) {
			ps = null;
			rs =  null;
			int id = 0;
			String pNum = product.getPartNumber();
			String description = product.getDescription();
			
			String insert = "INSERT INTO product_templates"
					+ "(product_number, description) VALUES"
					+ "(?,?)";
			try {
				ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, pNum);
				ps.setString(2, description);
				
				
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				
				if (rs.next()) {
					id = rs.getInt(1);
				}
				
				product.setID(id);
				products.add(product);
				
				
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
		
		
		/**
		 * Add a product to the product_templates table
		 * @param product
		 */
		public void addProductPart(int productID, int partID, int quant) {
			ps = null;
			rs =  null;
			
			int id = productID;
			int pid = partID;
			
			//System.out.println(id + ", " + pid);
			
			String insert = "INSERT INTO product_parts"
					+ "(product_id, part_id, quantity) VALUES"
					+ "(?,?,?)";
			try {
				ps = conn.prepareStatement(insert);
				ps.setInt(1, id);
				ps.setInt(2, pid);
				ps.setInt(3, quant);
				
				
				ps.executeUpdate();
			
				
				
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
		
		
		/**
		 * Delete a Part from the product_parts table
		 * @param id
		 * @param pid
		 */
		public void deleteProductPart(int id, int pid) {
			PreparedStatement ps3 = null;
			String delete = "DELETE FROM product_parts WHERE product_id = ? AND part_id = ?";
			
			try {
				ps3 = conn.prepareStatement(delete);
				ps3.setInt(1, id);
				ps3.setInt(2, pid);
				ps3.executeUpdate();
			}catch (SQLException e ) {
				e.printStackTrace();
			}finally {
				try {
					if (ps3 != null) {
						ps3.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		/**
		 * delete a product and all associated product parts from their respective tables.
		 * @param id
		 */
		public void deleteProduct(int id) {
			ProductTemplate product = getProductByID(id);
			
			ps = null;
			String delete = "DELETE FROM product_templates WHERE id = ?";
			
			try {
				for(int i = 0; i < product.getParts().size(); i++) {
					deleteProductPart(id, product.getParts().get(i).getId());
				}
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
			
			products.remove(product);
			
		}
		
		
		
		/**
		 * update a product in the database.
		 * @param id
		 * @param pNum
		 * @param description
		 */
		public void updateProduct(int id, String pNum, String description) {
			ProductTemplate product = getProductByID(id);
			products.remove(product);
			product.setPartNumber(pNum);
			product.setDescription(description);
			
			
			ps = null;
			String update = "UPDATE product_templates SET product_number = ?, description = ?"
					+ " WHERE id = ?";
			
			try {
				ps = conn.prepareStatement(update);
				ps.setString(1, pNum);
				ps.setString(2, description);
				ps.setInt(3, id);
				
				
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
			products.add(product);
		}
		
		
		
		/**
		 * update a part in the product_parts table
		 * @param id
		 * @param prevPart
		 * @param newPart
		 * @param quant
		 */
		public void updateProductPart(int id, Part prevPart, Part newPart, int quant) {
			/*
			 * CHECK TO SEE IF PREVPART ID AND NEWPART ID ARE THE SAME.  IF SO RUN UPDATE 
			 * ELSE DELETE OLD PRODUCT AND INSERT THE NEW ONE
			 * 
			 */
			
			if (prevPart.getId() == newPart.getId()) {
				ps = null;
				String update = "UPDATE product_parts SET quantity = ?"
						+ " WHERE product_id = ? AND part_id = ?";
				
				try {
					ps = conn.prepareStatement(update);
					ps.setInt(1, quant);
					ps.setInt(2, id);
					ps.setInt(3, newPart.getId());
					
					
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
			}
			else {
				deleteProductPart(id, prevPart.getId());
				addProductPart(id, newPart.getId(), quant);
			}
		}
		
		
		
		
		/**
		 * get the list of products
		 * @return products
		 */
		public ArrayList<ProductTemplate> getProductList() {
			return products;
		}
		
		
		/**
		 * get a product template by its id
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

}
