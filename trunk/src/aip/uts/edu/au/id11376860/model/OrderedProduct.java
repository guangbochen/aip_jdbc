package aip.uts.edu.au.id11376860.model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class stores the information of the shopping cart
 * @author guangbo
 */
public class OrderedProduct implements Serializable {

	private int id;
	private int orderId;
	private int productId;
	private int quantity;
	private double lineTotal;
	private Product product;
	
	
	/**
	 * OrderedProduct constructor without parameters
	 */
	public OrderedProduct() {
		super();
	}
	/**
	 * OrderedProduct constructor
	 * @param id
	 * @param orderId
	 * @param productId
	 * @param quantity
	 * @param lineTotal
	 * @param product
	 */
	public OrderedProduct(int id, int orderId, int productId, int quantity,
			double lineTotal, Product product) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.lineTotal = lineTotal;
		this.product = product;
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
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the lineTotal
	 */
	public double getLineTotal() {
		return lineTotal;
	}
	
	/**
	 * @param lineTotal the lineTotal to set
	 */
	public void setLineTotal(double price, int quantity) {
		this.lineTotal = Math.round((price*quantity)*100.0)/100.0;
	}
	
	/**
	 * this method sets the lineTotal
	 * @param lineTotal
	 */
	public void setLineTotals(double lineTotal) {
		this.lineTotal = lineTotal;
	}
	
	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * this method calculates the grand total of customer order
	 * @param ops, ArrayList contains a list of ordered products
	 * @return double grandTotal
	 */
	public double getGrandTotal(ArrayList<OrderedProduct> ops)
	{
		double grandTotal = 0;
		for(OrderedProduct op : ops)
		{
			grandTotal += op.getLineTotal();
		}
		grandTotal = Math.round(grandTotal*100.0)/100.0;
		return grandTotal;
	}
}
