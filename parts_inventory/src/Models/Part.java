package Models;
import java.util.Random;
//import java.util.UUID;
//test 2

/**
 * The Part class creates a object that has fields for part number, name and quantity.
 * @author Keith Cruz, Brendan Nelson-Weiss
 *
 */
public class Part {
	private final String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
	private Random rnd = new Random();
	private String partNumber;
	private final int SZ = 35;
	private String name;
	private String vendor;
	private String uoq;
	private String exNum;
	private int id;
	
	/**
	 * No arg constructor sets the partNumber and name fields to empty string and quantity to null.
	 */
	public Part() {
		this.partNumber =  "P" + pNumber();
		this.name = "";
		this.vendor = "";

		this.uoq = "";


		this.exNum = "";

	}
	
	/**
	 * Part constructor takes a String for partNumber and name and an int for the quantity.
	 * @param name The name for the part.
	 * @param vendor The vendor for the part.
	 */


	public Part(String name, String vendor, String uoq, String exNum) {
		
		this.partNumber = "P" + pNumber();
		this.name = name;
		this.vendor = vendor;
		this.uoq = uoq;
		this.exNum = exNum;

	}
	
	public Part(String pNum, String name, String vendor, String uoq, String exNum) {
		this.partNumber = pNum;
		this.name = name;
		this.vendor = vendor;
		this.uoq = uoq;
		this.exNum = exNum;
		
	}
	
	/**
	 * partNumber method returns the part number of the part object.
	 * @return The partNumber of the part object.
	 */
	public String partNumber() {
		return this.partNumber;
	}

	
	/**
	 * setPartNumber sets the partNumber field of the part object.
	 * @param partNumber The part number of the part object.
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * getName returns the name of the part object.
	 * @return The name of the part object.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * setName sets the name field of the part object.
	 * @param name The name of the part object.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getVendor returns the vendor of the part object.
	 * @return The vendor of the part object.
	 */
	public String getVendor() {
		return this.vendor;
	}
	public String getExNum()
	{
		return this.exNum;
	}
	
	public void setExNum(String exNum) {
		this.exNum = exNum;
	}
	
	/**
	 * setVendor sets the vendor field of the part object.
	 * @param vendor The vendor of the part object.
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public String getUnit() {
		return this.uoq;
	}
	
	public void setUnit(String uoq) {
		this.uoq = uoq;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	
	public String toString() {

		return id + "    " + name + "                    " + partNumber + "                    " + vendor + "                    "  +  uoq;

	}
	
	public boolean equals(Part p) {
		if (this.name.equals(p.getName()) && this.partNumber.equals(p.partNumber()) && this.vendor.equals(p.getVendor()) &&  this.exNum.equals(p.getExNum()) ) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		String code = this.name + this.partNumber().toString() + this.vendor + this.exNum;
		return code.length();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Create a new part number.
	 * @return product number
	 */
	public String pNumber()
	{
	    char[] text = new char[SZ];
	    for (int i = 0; i < SZ; i++)
	    {
	        text[i] = charPool.charAt(rnd.nextInt(charPool.length()));
	    }
	    return new String(text);
	}
	

}
