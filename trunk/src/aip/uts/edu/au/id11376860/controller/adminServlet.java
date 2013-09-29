package aip.uts.edu.au.id11376860.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aip.uts.edu.au.id11376860.dao.OrderDAO;
import aip.uts.edu.au.id11376860.dao.OrderDAOImple;
import aip.uts.edu.au.id11376860.model.Admin;
import aip.uts.edu.au.id11376860.model.Order;
import aip.uts.edu.au.id11376860.model.OrderedProduct;

/**
 * Servlet implementation class adminServlet, 
 * this class handles admin request and forwards relevant responses
 */
public class adminServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * adminServlet constructor
	 */
    public adminServlet() 
    {
        super();
    }

    /**
     * this method calls dopost methods
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * this method handles post request
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getParameter("action") != null)
		{
			
			if(request.getParameter("action").equals("manage"))
			{
				//validate admin login
				validateAdmin(request,response);
			}
			else if(request.getParameter("action").equals("listOrders"))
			{
				//get outstanding orders
				getOutstandingOrders(request,response);
			}
			else if(request.getParameter("action").equals("searchOrder"))
			{
				//searching outstanding order details
				searchOrder(request,response);
				RequestDispatcher view = request.getRequestDispatcher("admin_update.jsp");
		        view.forward(request, response);
			}
			else if(request.getParameter("action").equals("updateOrder"))
			{
				//update order status
				updateOrder(request,response);
			}
		}
		RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
        view.forward(request, response);
	}
	
	/**
	 * this method validate user login, if is admin forward to admin page
	 * @param request
	 * @param response
	 */
	private void validateAdmin(HttpServletRequest request, HttpServletResponse response)
	{
		try{
			RequestDispatcher view = null;
			HttpSession sess = request.getSession();
			Admin admin = (Admin) sess.getAttribute("admin");
			if(admin != null)
			{
		        view = request.getRequestDispatcher("admin.jsp");
		        view.forward(request, response);
			}
			else
			{
		        view = request.getRequestDispatcher("index");
		        view.forward(request, response);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * this methods get the outstanding orders from database via calling OrderDAO method
	 */
	private void getOutstandingOrders(HttpServletRequest request, HttpServletResponse response)
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		OrderDAO odao = new OrderDAOImple();
		orders = odao.findOutStandingOrders();
		request.setAttribute("orders", orders);
	}
	
	/**
	 * this method searches the order and orderedProduct by order number
	 */
	private void searchOrder(HttpServletRequest request, HttpServletResponse response)
	{
		try{
			String orderNumber = request.getParameter("nu");
			if(!orderNumber.equals(""))
			{
				Order order = new Order();
				ArrayList<OrderedProduct> ops = new ArrayList<OrderedProduct>();
				OrderDAO odao = new OrderDAOImple();
				odao.findUpdatingOrder(orderNumber);
				order = odao.getOrder();
				ops = odao.getOrderedProduct();
				request.setAttribute("order", order);
				request.setAttribute("ops", ops);
			}
			else
			{
				request.setAttribute("isempty", " Empty");
				request.setAttribute("error", "has-error");
			}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * this method updates the order status
	 */
	private void updateOrder(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String orderNumber = request.getParameter("orderNumber");
			String status = request.getParameter("status");
			if(!orderNumber.equals("") && !status.equals(""))
			{
				OrderDAO odao = new OrderDAOImple();
				odao.updateOrder(orderNumber, status);
				request.setAttribute("updateSuccess", "You have update " + orderNumber +" status to "+ status +" successfully. ");
			}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}
}
