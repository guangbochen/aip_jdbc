package aip.uts.edu.au.id11376860.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import aip.uts.edu.au.id11376860.database.MysqlDatabase;
import aip.uts.edu.au.id11376860.model.Product;

/**
 * this class implements ProductDAO and searching products
 * @author guangbo
 *
 */
public class ProductDAOImple implements ProductDAO {

	private MysqlDatabase sqldb;
	
	/**
	 * constructor for ProductDAOImple to set mysql dbAccount
	 * @param sqldb
	 * @param products
	 */
	public ProductDAOImple() {
		try {
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
	 * this method returns all the products
	 */
	@Override
	public List<Product> findAll() {
		String sql = "SELECT * FROM products";
		try {
			List<Product> products = doQuery(sql);
			return products;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * this method finds all the products through pagination
	 */
	@Override
	public List<Product> findAllByPagination(int offset, int noOfRecords) {
		String sql = "SELECT * FROM products Order By (Case Category "
				+ "When 'Jerseys' Then 1 "
				+ "When 'Shorts' Then 2 "
				+ "When 'Socks' Then 3 "
				+ "When 'Gloves' Then 4 "
				+ "When 'Jackets' Then 5 "
				+"When 'Equipment' Then 6 "
				+"ELSE 100 END)ASC, Category Desc LIMIT " +offset+ ", " +noOfRecords;
		try {
			//call doQuery method to get list of product
			List<Product> products = doQuery(sql);
			return products;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * this method find all the products via category
	 */
	@Override
	public List<Product> findAllByCategory(String category) {
		List<Product> products = new ArrayList<Product>();
		String sql = "SELECT * FROM products Where category = ?";
		try 
		{
			PreparedStatement preparedStatement = sqldb.getDBConnection().prepareStatement(sql);;
			preparedStatement.setString(1, category);
			
			// execute select SQL stetement
			ResultSet sqlres = preparedStatement.executeQuery();
			while (sqlres.next()) {
				Product product = new Product();
				product.setId(sqlres.getInt("id"));
				product.setCategory(sqlres.getString("category"));
				product.setCode(sqlres.getString("code"));
				product.setDescription(sqlres.getString("description"));
				product.setPrice(sqlres.getDouble("price"));
				products.add(product);
			}
			sqldb.close();
			return products;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * this method searches products according to the category via pagination
	 */
	@Override
	public List<Product> searchProductByCategory(String category, int offset, int noOfRecords) {
		
		List<Product> products = new ArrayList<Product>();
		String sql = "SELECT * FROM products Where category = ?" +" LIMIT " +offset+ ", " +noOfRecords;
		try 
		{
			PreparedStatement preparedStatement = sqldb.getDBConnection().prepareStatement(sql);;
			preparedStatement.setString(1, category);
			
			// execute select SQL stetement
			ResultSet sqlres = preparedStatement.executeQuery();
			while (sqlres.next()) {
				Product product = new Product();
				product.setId(sqlres.getInt("id"));
				product.setCategory(sqlres.getString("category"));
				product.setCode(sqlres.getString("code"));
				product.setDescription(sqlres.getString("description"));
				product.setPrice(sqlres.getDouble("price"));
				products.add(product);
			}
			sqldb.close();
			return products;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * this method returns all the products category in the database
	 */
	@Override
	public List<Product> getProductsCategory() {
		String sql = "SELECT * FROM products Group By category "
				+ "Order By (Case Category "
				+ "When 'Jerseys' Then 1 "
				+ "When 'Shorts' Then 2 "
				+ "When 'Socks' Then 3 "
				+ "When 'Gloves' Then 4 "
				+ "When 'Jackets' Then 5 "
				+"When 'Equipment' Then 6 "
				+"ELSE 100 END)ASC";
		try {
			List<Product> products = doQuery(sql);
			return products;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * this method execute the sql query and returns the find products
	 * @param sql ,sql query
	 * @return, list of products
	 */
	private Vector<Product> doQuery(String sql) {
		
		Vector<Product> products= new Vector<Product>();
		ResultSet sqlres;
		
		try {
			sqldb.connect();
			sqlres = sqldb.query(sql);
			// get a list of product
			while (sqlres.next()) {
				Product product = new Product();
				product.setId(sqlres.getInt("id"));
				product.setCategory(sqlres.getString("category"));
				product.setCode(sqlres.getString("code"));
				product.setDescription(sqlres.getString("description"));
				product.setPrice(sqlres.getDouble("price"));
				products.add(product);
			}
			sqldb.close();
			return (products);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
