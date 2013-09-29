package aip.uts.edu.au.id11376860.dao;
import java.util.ArrayList;
import aip.uts.edu.au.id11376860.model.Order;
import aip.uts.edu.au.id11376860.model.OrderedProduct;

/**
 * this is order interface for processing and searching orders
 * @author guangbo
 */
public interface OrderDAO {

	//Basic CRUD methods
	public void addOrder(Order order);
	public void addOrderProducts(ArrayList<OrderedProduct> list);
	public ArrayList<OrderedProduct> getOrderedProduct();
	public Order getOrder();
	
	//searching methods
	public void findOrderByOrderNumAndSurname(String orderNumber, String surname);
	public ArrayList<Order> findOutStandingOrders();
	
	//get unique order number 
	public String getUniqueOrderNum();
	
	//admin searching & updating
	public void findUpdatingOrder(String orderNumber);
	public void updateOrder(String orderNumber, String status);
}
