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
import aip.uts.edu.au.id11376860.model.Order;
import aip.uts.edu.au.id11376860.model.OrderedProduct;

/**
 * Servlet implementation class ProcessOrderServlet
 * This class allows user to process the purchase or cancel the purchase
 */
public class ProcessOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * class constructor
	 */
    public ProcessOrderServlet() 
    {
        super();
    }

    /**
     * this method class dopost method
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * this method handles post request from confirmPurchase page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Order order = (Order) request.getSession().getAttribute("order");
		
        //if has http request
		if(request.getParameter("action") != null)
		{
			if(request.getParameter("action").equals("cancle"))
			{
				//cancel to purchase order
				cancelOrder(request,response);
			}
			else if(request.getParameter("action").equals("proceed"))
			{
				//proceed the order
				proceedOrder(request,response,order);
			}
		}
		
		//forward to confirm purchase as default page
        RequestDispatcher view = request.getRequestDispatcher("confirmPurchase.jsp");
        view.forward(request, response);
	}
	
	/**
	 * this method cancels the purchasing action
	 * @param request
	 * @param response
	 */
	private void cancelOrder(HttpServletRequest request, HttpServletResponse response)
	{
        try {
        RequestDispatcher view = request.getRequestDispatcher("orders");
			view.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method process the order and saves order into the database
	 * @param request
	 * @param response
	 * @param order
	 */
	private void proceedOrder(HttpServletRequest request, HttpServletResponse response, Order order)
	{
		try 
		{
			//get shopping cart details
			HttpSession sess = request.getSession();
			ArrayList<OrderedProduct> ops = (ArrayList<OrderedProduct>) sess.getAttribute("shoppingCart");
			OrderedProduct op = new OrderedProduct();
			if(order != null && ops != null)
			{
				//save order to the database
				order.setGrandTotal(op.getGrandTotal(ops));
				OrderDAO orderDAO = new OrderDAOImple();
				orderDAO.addOrder(order);
				orderDAO.addOrderProducts(ops);
	        	sess.setAttribute("shoppingCart", null);
	        	sess.setAttribute("order", null);
	        	request.setAttribute("orderNumber", orderDAO.getUniqueOrderNum());
	        	//if saved into database successfuly forward to notice page
		        RequestDispatcher view = request.getRequestDispatcher("purchaseNotice.jsp");
				view.forward(request, response);
			}	
			else
			{
				//forward to confirm purchase as default page if repeat the action with empty order
		        RequestDispatcher view = request.getRequestDispatcher("index");
		        view.forward(request, response);
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end of try block
	}
}
