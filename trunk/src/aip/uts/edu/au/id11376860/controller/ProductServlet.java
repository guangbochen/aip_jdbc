package aip.uts.edu.au.id11376860.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aip.uts.edu.au.id11376860.dao.ProductDAO;
import aip.uts.edu.au.id11376860.dao.ProductDAOImple;
import aip.uts.edu.au.id11376860.model.OrderedProduct;
import aip.uts.edu.au.id11376860.model.Product;

/**
 * Servlet implementation class ProductServlet
 * this class handles http requests from products page
 */
public class ProductServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int noOfRecords;
	private int noOfPages;

	/**
	 * this method calls dopost method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * this method handles request from products page and shows products via pagination
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		//calls Product DAO
		ProductDAO pdao = new ProductDAOImple();
		List<Product> productList = null;
		noOfRecords = 0;
		noOfPages = 0;
		//set default page number is one
		int page = 1;
		//set maximum number of items in one page
		int recordsPerPage = 10;
		
		//if has http page request
		if(request.getParameter("page") != null) {
			try 
			{
				page = Integer.parseInt(request.getParameter("page"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		//if has others http request
		if(request.getParameter("action") != null)
		{
			if(request.getParameter("action").equals("category"))
			{
				//get relevant products upon category
				String category = request.getParameter("category");
				productList = pdao.searchProductByCategory(category, (page-1)*recordsPerPage,recordsPerPage);
				noOfRecords = pdao.findAllByCategory(category).size();
				//set selected category
		        request.setAttribute("category", category);
			}
			else if(request.getParameter("action").equals("order"))
			{
				//calls addShoppingCart method to add customer order
				addShoppingCart(request);
		        RequestDispatcher view = request.getRequestDispatcher("orders");
		        view.forward(request, response);
			}
		}
		else
		{
			//displays all the products in pagination
			noOfRecords = pdao.findAll().size();
			productList = pdao.findAllByPagination((page-1)*recordsPerPage,recordsPerPage);
		}
		
		// calculates the total number of pages in pagination
		noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		
		//get a list product categories for category filter
		List<Product> productCategory = pdao.getProductsCategory();
		
		//forward productList to the products page
		request.setAttribute("productList", productList);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("productCategory", productCategory);
        RequestDispatcher view = request.getRequestDispatcher("products.jsp");
        view.forward(request, response);
	}
	
	
	/**
	 * this method add selected product to the shopping cart/updates my orders
	 * @param request, http request
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addShoppingCart(HttpServletRequest request)
	{
		try
		{
			HttpSession sess = request.getSession();
			ArrayList<OrderedProduct> ops = (ArrayList<OrderedProduct>) sess.getAttribute("shoppingCart");
			int pid = Integer.parseInt(request.getParameter("id"));
			String category = request.getParameter("category");
			String code = request.getParameter("code");
			String description  = request.getParameter("description");
			double price = Double.parseDouble(request.getParameter("price"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			//if the order session does not exist create new http session for orders
			if(sess.isNew() || ops == null)
			{
				ops = new ArrayList<OrderedProduct>();
				OrderedProduct op = new OrderedProduct();
				Product p = new Product(pid,category, code, description, price);
				op.setQuantity(quantity);
				op.setProduct(p);
				op.setLineTotal(price,quantity);
				ops.add(op);
				sess.setAttribute("shoppingCart", ops);
			}
			else
			{
				boolean notExistingOrder = true;
				//find the existing order and add quantity upon the quantity it contains
				for(OrderedProduct op : ops)
				{
					if(op.getProduct().getId() == pid)
					{
						//update the total quantity 
						int totalQuantity = op.getQuantity() + quantity;
						op.setQuantity(totalQuantity);
						//update the line total
						op.setLineTotal(price, op.getQuantity());
						notExistingOrder = false;
					}		
				}
				//if it is a new order then add it to the order list
				if(notExistingOrder)
				{
					
					Product p = new Product(pid,category, code, description, price);
					OrderedProduct op = new OrderedProduct();
					op.setQuantity(quantity);
					op.setProduct(p);
					op.setLineTotal(price,op.getQuantity());
					ops.add(op);
				}
			}
			//update the http shoppingCart session 
			sess.setAttribute("shoppingCart", ops);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
