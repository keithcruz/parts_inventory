package Models;

import java.util.*;

public class ProductTemplate {
	
	private final String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
	private Random rnd = new Random();
	private String partNumber;
	private String description;
	private int id;
	private ArrayList<Part> parts;
	private HashMap<Part, Integer> hashMap;
	
	/**
	 * No arg constructor
	 */
	public ProductTemplate() {
		
		this.partNumber = "A" + pNumber();
		this.description = "";
		this.parts = new ArrayList<Part>();
		this.hashMap = new HashMap<Part, Integer>();
		
	}
	
	/**
	 * Constructor with a description
	 * @param description
	 */
	public ProductTemplate(String description) {
		
		this.partNumber = "A" + pNumber();
		this.description = description;
		this.parts = new ArrayList<Part>();
		this.hashMap = new HashMap<Part, Integer>();
		
	}
	
	/**
	 * Constructor with a part number and a description.	
	 * @param partNumber
	 * @param description
	 */
	public ProductTemplate(String partNumber, String description) {
		this.partNumber = partNumber;
		this.description = description;
		this.parts = new ArrayList<Part>();
		this.hashMap = new HashMap<Part, Integer>();
		
	}
	
	/**
	 * Constructor with part number, description, and parts
	 * @param partNumber
	 * @param description
	 * @param parts
	 */
	public ProductTemplate(String partNumber, String description, ArrayList<Part> parts) {
		this.partNumber = partNumber;
		this.description = description;
		this.parts = parts;
		this.hashMap = new HashMap<Part, Integer>();
		
	}
	
	/**
	 * Constructor with a part number, description, and hash map.
	 * @param partNumber
	 * @param description
	 * @param hashMap
	 */
	public ProductTemplate(String partNumber, String description, HashMap<Part, Integer> hashMap) {
		this.partNumber = partNumber;
		this.description = description;
		parts = new ArrayList<Part>();
		this.hashMap = hashMap;
		
	}
	
	/**
	 * Constructor with part number, description, parts, and hash map.
	 * @param partNumber
	 * @param description
	 * @param parts
	 * @param hashMap
	 */
	public ProductTemplate(String partNumber, String description, ArrayList<Part> parts, HashMap<Part, Integer> hashMap) {
		this.partNumber = partNumber;
		this.description = description;
		this.parts = parts;
		this.hashMap = hashMap;
		
	}
	
	
	
	/**
	 * get the ID
	 * @return id
	 */
	public int getID() {
		return this.id;
	}
	
	
	/**
	 * set the id
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * get the part number
	 * @return partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}
	
	/**
	 * set the part number
	 * @param partNumber
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	/**
	 * get the description
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * set the description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * get the hash map
	 * @return hashMap
	 */
	public HashMap<Part, Integer> getHashMap() {
		return hashMap;
	}
	
	/**
	 * set the hash map
	 * @param hashMap
	 */
	public void setHashMap(HashMap<Part, Integer> hashMap) {
		this.hashMap = hashMap;
	}
	
	/**
	 * get the parts
	 * @return parts
	 */
	public ArrayList<Part> getParts() {
		return parts;
	}
	
	/**
	 * set the parts
	 * @param parts
	 */
	public void setParts(ArrayList<Part> parts) {
		this.parts = parts;
	}
	
	/**
	 * set the part and quantity into the hash map
	 * @param part
	 * @param quant
	 */
	public void setPartAmount(Part part, int quant) {
		this.hashMap.put(part, quant);
	}
	
	/**
	 * add a part to the list of parts.
	 * @param part
	 */
	public void addPart(Part part) {
		this.parts.add(part);
	}
	
	
	/**
	 * toString method returns a string representation of the ProductTemplate
	 */
	public String toString() {
		String str = id + " " + partNumber + " " + description;
		return str;
	}
	
	
	/**
	 * Show the part and quantity of a product template.
	 * @param part
	 * @return str
	 */
	public String showPartandQuantity(Part part) {
		String str = part.getName() + " " + hashMap.get(part);
		return str;
	}
	
	
	/**
	 * Return an arraylist of strings. Each string has a part with quantity.
	 * @return strs
	 */
	public ArrayList<String> listPartsAndQuants() {
		ArrayList<String> strs = new ArrayList<String>();
		for (Part part : parts) {
			strs.add(showPartandQuantity(part));
		}
		return strs;
	}
	
	/**
	 * Create a new product number.
	 * @return product number
	 */
	public String pNumber()	{
	    char[] text = new char[19];
	    for (int i = 0; i < 19; i++) {
	        text[i] = charPool.charAt(rnd.nextInt(charPool.length()));
	    }
	    return new String(text);
	}
	
	
}
