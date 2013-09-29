package aip.uts.edu.au.id11376860.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import aip.uts.edu.au.id11376860.database.MysqlDatabase;
import aip.uts.edu.au.id11376860.model.Order;
import aip.uts.edu.au.id11376860.model.OrderedProduct;
import aip.uts.edu.au.id11376860.model.Product;

/**
 * this class implements OrderDAO and do basic CRUD and searching of the database
 * @author guangbo
 *
 */
public class OrderDAOImple implements OrderDAO{

	private MysqlDatabase sqldb;
	private final static String defaultStatus = "ORDERED";
	private final static String prefixOfUniqueId = "guchen";
	private static String orderNumber = null;
	private static Order order = null;
	private static ArrayList<OrderedProduct> listOrders = null;
	
	/**
	 * OrderDAOImple constructor and initialize the database account 
	 */
	public OrderDAOImple() {
		try 
		{
			sqldb = new MysqlDatabase();
			sqldb.setDbUser("root");
			sqldb.setDbPass("root");
		}
		catch (Exception e)
		{
			e.printStackTrace();	
		}	
	}

	/**
	 * this method adds customer order details into orders tables
	 */
	@Override
	public void addOrder(Order o) {
		
		//generate unique order id 
		String orderNumber = generateOrderNum();
		String sql = "INSERT INTO orders("
				+ "orderNumber, title, surname, givenName, email, unitNumber, "
				+ "street, state, suburb, postCode, country, paymentDetails, status, grandTotal) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try 
		{
			PreparedStatement st = sqldb.getDBConnection().prepareStatement(sql);;
			st.setString(1, orderNumber);
			st.setString(2, o.getTitle());
			st.setString(3, o.getSurname());
			st.setString(4, o.getGivenName());
			st.setString(5, o.getEmail());
			st.setString(6, o.getUnitNumber());
			st.setString(7, o.getStreet());
			st.setString(8, o.getState());
			st.setString(9, o.getSuburb());
			st.setString(10, o.getPostCode());
			st.setString(11, o.getCountry());
			st.setString(12, o.getPaymentDetails());
			st.setString(13, defaultStatus);
			st.setDouble(14, o.getGrandTotal());
			// execute insert SQL stetement
			st.executeUpdate();
			sqldb.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * this method add ordered product to the database
	 */
	@Override
	public void addOrderProducts(ArrayList<OrderedProduct> list) 
	{
		
		String sql = "INSERT INTO orderedProducts("
				+ "productId, orderNumber, quantity, lineTotal ) VALUES"
				+ "(?,?,?,?)";
		try 
		{
			if(orderNumber != null)
			{
				PreparedStatement st = sqldb.getDBConnection().prepareStatement(sql);;
				for(OrderedProduct op : list)
				{
					st.setInt(1, op.getProduct().getId());
					st.setString(2, orderNumber);
					st.setInt(3, op.getQuantity());
					st.setDouble(4, op.getLineTotal());
					// execute insert SQL statement
					st.executeUpdate();
				}
				sqldb.close();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * this method generates unique order number
	 * @return order number
	 */
	private String generateOrderNum()
	{
		NumberFormat nf = new DecimalFormat("0000");
		String sql = "SELECT MAX(id) FROM orders Where id";
		int id = 0;
		try 
		{
			sqldb.connect();
			ResultSet sqlres = sqldb.query(sql);
			while (sqlres.next()) 
			{
				id = sqlres.getInt("MAX(id)") + 1;
			}
			sqldb.close();
			//if the database is empty set first id as 1
			if(id == 0)
				id = 1;
			//converting int to string and add prefix to it
			String orderId = nf.format(id);
			orderNumber = prefixOfUniqueId + orderId;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return orderNumber;
	}
	
	/**
	 * this method returns unique order number
	 */
	@Override
	public String getUniqueOrderNum()
	{
		return orderNumber;
	}

	/**
	 * this method find order by order number and order's surname
	 */
	@Override
	public void findOrderByOrderNumAndSurname(String orderNumber, String surname) {
		
		String sql = "SELECT p.*, op.*, o.status FROM orderedProducts op JOIN "
				+ "orders o ON o.orderNumber = op.orderNumber "
				+ "JOIN  products p ON p.id = op.productId "
				+ "WHERE o.orderNumber = ?" +" AND o.surname = ?";
		try 
		{
			listOrders = new ArrayList<OrderedProduct>();
			order = new Order();
			PreparedStatement preparedStatement = sqldb.getDBConnection().prepareStatement(sql);;
			preparedStatement.setString(1, orderNumber);
			preparedStatement.setString(2, surname);
			
			// execute select SQL stetement
			ResultSet sqlres = preparedStatement.executeQuery();
			while (sqlres.next()) {
				Product p = new Product();
				OrderedProduct orderProduct = new OrderedProduct();
				p.setCategory(sqlres.getString("p.category"));
				p.setCode(sqlres.getString("p.code"));
				p.setDescription(sqlres.getString("p.description"));
				orderProduct.setQuantity(sqlres.getInt("op.quantity"));
				orderProduct.setLineTotals(sqlres.getDouble("op.lineTotal"));
				orderProduct.setProduct(p);
				listOrders.add(orderProduct);
				order.setStatus(sqlres.getString("o.status"));
			}
			sqldb.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method returns list of orderedProduct
	 */
	@Override
	public ArrayList<OrderedProduct> getOrderedProduct() {
		return listOrders;
	}

	/**
	 * this method return an order
	 */
	@Override
	public Order getOrder() {
		return order;
	}

	/**
	 * this method finds the outstanding orders for the admin
	 */
	@Override
	public ArrayList<Order> findOutStandingOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM orders WHERE status =?"+" OR status =?";
		try {
			
			PreparedStatement preparedStatement = sqldb.getDBConnection().prepareStatement(sql);;
			preparedStatement.setString(1, "ORDERED");
			preparedStatement.setString(2, "PAID");
			
			// execute select SQL stetement
			ResultSet sqlres = preparedStatement.executeQuery();
			// get a list of product
			while (sqlres.next()) {
				Order o = new Order();
				o.setOrderNumber(sqlres.getString("orderNumber"));
				o.setSurname(sqlres.getString("surname"));
				o.setCountry(sqlres.getString("country"));
				o.setPostCode(sqlres.getString("postCode"));
				o.setGrandTotal(Double.parseDouble(sqlres.getString("grandTotal")));
				o.setStatus(sqlres.getString("status"));
				orders.add(o);
			}
			sqldb.close();
			return (orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	/**
	 * this method finds the order and ordered products details for admin by order number
	 */
	@Override
	public void findUpdatingOrder(String orderNumber) {
		String sql = "SELECT p.*, op.*, o.* FROM orderedProducts op JOIN "
				+ "orders o ON o.orderNumber = op.orderNumber "
				+ "JOIN  products p ON p.id = op.productId "
				+ "WHERE o.orderNumber = ?";
		try 
		{
			listOrders = new ArrayList<OrderedProduct>();
			order = new Order();
			PreparedStatement preparedStatement = sqldb.getDBConnection().prepareStatement(sql);;
			preparedStatement.setString(1, orderNumber);
			
			// execute select SQL stetement
			ResultSet sqlres = preparedStatement.executeQuery();
			while (sqlres.next()) {
				Product p = new Product();
				OrderedProduct orderProduct = new OrderedProduct();
				p.setCategory(sqlres.getString("p.category"));
				p.setCode(sqlres.getString("p.code"));
				p.setDescription(sqlres.getString("p.description"));
				orderProduct.setQuantity(sqlres.getInt("op.quantity"));
				orderProduct.setLineTotals(sqlres.getDouble("op.lineTotal"));
				orderProduct.setProduct(p);
				listOrders.add(orderProduct);
				order.setOrderNumber(sqlres.getString("o.orderNumber"));
				order.setTitle(sqlres.getString("o.title"));
				order.setSurname(sqlres.getString("o.surname"));
				order.setGivenName(sqlres.getString("o.givenName"));
				order.setEmail(sqlres.getString("o.email"));
				order.setUnitNumber(sqlres.getString("o.unitNumber"));
				order.setStreet(sqlres.getString("o.street"));
				order.setState(sqlres.getString("o.state"));
				order.setSuburb(sqlres.getString("o.suburb"));
				order.setPostCode(sqlres.getString("o.postcode"));
				order.setCountry(sqlres.getString("o.country"));
				order.setPaymentDetails(sqlres.getString("o.paymentDetails"));
				order.setStatus(sqlres.getString("o.status"));
				order.setGrandTotal(Double.parseDouble(sqlres.getString("o.grandTotal")));
			}
			sqldb.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method updates the order status by admin
	 */
	@Override
	public void updateOrder(String orderNumber, String status)
	{
		String sql = "UPDATE orders SET status = ?" + "WHERE orderNumber = ?";
		try 
		{
			PreparedStatement preparedStatement = sqldb.getDBConnection().prepareStatement(sql);;
			preparedStatement.setString(1, status);
			preparedStatement.setString(2, orderNumber);
			// execute update SQL stetement
			preparedStatement.executeUpdate();
			sqldb.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
}
