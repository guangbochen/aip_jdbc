package aip.uts.edu.au.id11376860.dao;

import java.util.List;

import aip.uts.edu.au.id11376860.model.Product;


/**
 * this is product interface for searching products
 * @author guangbo
 */
public interface ProductDAO {
	
	// searching
	public List<Product> findAll();
	public List<Product> findAllByPagination(int offset, int noOfRecords);
	public List<Product> findAllByCategory(String category);
	public List<Product> searchProductByCategory(String category, int offset, int noOfRecords);
	public List<Product> getProductsCategory();
}
