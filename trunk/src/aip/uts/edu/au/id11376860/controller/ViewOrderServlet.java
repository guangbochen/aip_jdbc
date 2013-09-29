package aip.uts.edu.au.id11376860.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aip.uts.edu.au.id11376860.dao.OrderDAO;
import aip.uts.edu.au.id11376860.dao.OrderDAOImple;
import aip.uts.edu.au.id11376860.model.Order;
import aip.uts.edu.au.id11376860.model.OrderedProduct;
import aip.uts.edu.au.id11376860.model.Product;

/**
 * Servlet implementation class ViewOrderServlet
 * this class handles http requests from viewOrder page
 */
public class ViewOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * class constructor
	 */
    public ViewOrderServlet() {
        super();
    }

    /**
     * this method calls dopost request
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * this method handles request from viewOrder page and forward response to it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //if has http request
		if(request.getParameter("action") != null) {
			
			if(request.getParameter("action").equals("check")) {
				searchingOrders(request,response);
			}
		}
		
        RequestDispatcher view = request.getRequestDispatcher("viewOrder.jsp");
        view.forward(request, response);
	}
	
	/**
	 * this method validates the searching form
	 * @param request
	 * @param response
	 */
	private void searchingOrders(HttpServletRequest request, HttpServletResponse response) {
		String number = request.getParameter("orderNumber");
		String name = request.getParameter("surname");
		if(number.equals("") || name.equals(""))
		{
			request.setAttribute("emptyNum", "empty order number");
			request.setAttribute("emptyName", "empty surname");
			request.setAttribute("error", "has-error");
			//set user input value to avoid repeating 
			request.setAttribute("number", number);
			request.setAttribute("name", name);
		}
		else
		{
			OrderDAO order = new OrderDAOImple();
			OrderedProduct op = new OrderedProduct();
			//searching order according to the user input
			order.findOrderByOrderNumAndSurname(number,name);
			ArrayList<OrderedProduct> ops = order.getOrderedProduct();
			//forward searching details
			request.setAttribute("order", order.getOrder());
			request.setAttribute("orderedProducts", order.getOrderedProduct());
			request.setAttribute("grandTotal", op.getGrandTotal(ops));
			//shows searching input
			request.setAttribute("snumber", number);
			request.setAttribute("sname", name);
		}
		
	}

}
