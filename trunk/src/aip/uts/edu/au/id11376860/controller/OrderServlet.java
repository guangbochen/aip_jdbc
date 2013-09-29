package aip.uts.edu.au.id11376860.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aip.uts.edu.au.id11376860.model.OrderedProduct;

/**
 * Servlet implementation class OrderServlet
 * This class shows details of customer ordering products via HTTP Session
 */
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * this method calls doPost Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * this method displays orders and handles http request
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<OrderedProduct> ops = new ArrayList<OrderedProduct>();
		//get ordered product from http session
		HttpSession sess = request.getSession();
		ops = (ArrayList<OrderedProduct>) sess.getAttribute("shoppingCart");
        //if has http request
		if(request.getParameter("action") != null)
		{
			if(request.getParameter("action").equals("update"))
			{
				//update product quantity
				updateQuantity(request, ops);
			}
			else if(request.getParameter("action").equals("delete"))
			{
				//remove delete order
				removeOrder(request, ops);
			}
			else if(request.getParameter("action").equals("cancel"))
			{
				//cancel the entire order
				sess.setAttribute("shoppingCart", null);
				ops = null;
			}
			else if(request.getParameter("action").equals("purchase"))
			{
				//purchase the order
				purchaseOrder(request, response, ops);
			}
		}
		
		//update the grand Total
		if( ops!=null )
		{
			OrderedProduct op = new OrderedProduct();
			double grandTotal = op.getGrandTotal(ops);
			request.setAttribute("grandTotal", grandTotal);
		}
		else
		{
			//if has no orders set grandTotal as 0
			double grandTotal = 0.0;
			request.setAttribute("grandTotal", grandTotal);
		}
		
        request.setAttribute("shoppingCart", ops);
		//forward OrderServlet view to the orders jsp page
        RequestDispatcher view = request.getRequestDispatcher("orders.jsp");
        view.forward(request, response);
	}
	
	
	/**
	 * this method updates product quantity upon user input
	 * @param request, http request
	 * @param ops, ArrayList contains ordered products
	 */
	private void updateQuantity(HttpServletRequest request, ArrayList<OrderedProduct> ops)
	{
		try
		{
			int pid = Integer.parseInt(request.getParameter("pid"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			for(OrderedProduct op : ops)
			{
				//update quantity & line total of the order upon product id
				if(op.getProduct().getId() == pid && quantity >0)
				{
					double price = op.getProduct().getPrice();
					op.setQuantity(quantity);
					op.setLineTotal(price, quantity);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//update the session 
		HttpSession sess = request.getSession();
		sess.setAttribute("shoppingCart", ops);
	}
	
	/**
	 * this method remove order upon the user request
	 * @param request, http request
	 * @param ops, ArrayList contains ordered products
	 */
	private synchronized void removeOrder(HttpServletRequest request, ArrayList<OrderedProduct> ops)
	{
		try
		{
			int pid = Integer.parseInt(request.getParameter("pid"));
			for (Iterator<OrderedProduct> it = ops.iterator(); it.hasNext(); )
			{
				OrderedProduct op = (OrderedProduct) it.next();  
				if(op.getProductId() == pid )
				{
					it.remove();
				}
			}
			//update the session 
			HttpSession sess = request.getSession();
			sess.setAttribute("shoppingCart", ops);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * this method validate purchasing order action, if is not empty then purchasing the order
	 * @param request
	 * @param response
	 * @param ops
	 */
	private void purchaseOrder(HttpServletRequest request, HttpServletResponse response, ArrayList<OrderedProduct> ops)
	{
		if(ops == null || ops.isEmpty())
		{
			//set empty order message
			request.setAttribute("emptyOrder", "Please select at least one product to purchas.");
		}
		else
		{
	        RequestDispatcher view = request.getRequestDispatcher("purchase");
	        try {
				view.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

