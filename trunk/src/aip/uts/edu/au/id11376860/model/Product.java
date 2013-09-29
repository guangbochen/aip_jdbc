package aip.uts.edu.au.id11376860.model;
import java.io.Serializable;

/**
 * This class stores product details as a product object
 * @author guangbo
 */
public class Product implements Serializable {
	
	private int id;
	private String category;
	private String code;
	private String description;
	private double price;
	
	/**
	 * product constructor
	 * @param id
	 * @param category
	 * @param code
	 * @param description
	 * @param price
	 */
	public Product(int id, String category, String code, String description, double price) {
		super();
		this.id = id;
		this.category = category;
		this.code = code;
		this.description = description;
		this.price = price;
	}
	
	/**
	 * product empty constructor
	 */
	public Product() 
	{
		super();
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
}
