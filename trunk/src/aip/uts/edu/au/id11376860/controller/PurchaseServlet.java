package aip.uts.edu.au.id11376860.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aip.uts.edu.au.id11376860.model.Order;

/**
 * Servlet implementation class PurchaseServlet
 * this class handles HTTP requests from purchase page
 */
public class PurchaseServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       

	/**
	 * this method calls dopost method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * this method handles request from purchase page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
        //if has http request
		if(request.getParameter("action") != null)
		{
			if(request.getParameter("action").equals("submit"))
			{
				//submit purchasing order
				validateForm(request, response);
			}
			else if(request.getParameter("action").equals("cancle"))
			{
				//cancel to purchasing the order
		        RequestDispatcher view = request.getRequestDispatcher("orders");
		        view.forward(request, response);
			}
		}
		
        RequestDispatcher view = request.getRequestDispatcher("purchase.jsp");
        view.forward(request, response);
		
	}
	
	
	/**
	 * this method validates the customer details input
	 * @param request
	 * @param response
	 */
	private void validateForm(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			//get the details of order form via request
			String title = request.getParameter("title");
			String surname = request.getParameter("surname");
			String givenName = request.getParameter("givenName");
			String email = request.getParameter("email");
			String unit = request.getParameter("unit");
			String street = request.getParameter("street");
			String state = request.getParameter("state");
			String suburb = request.getParameter("suburb");
			String postcode = request.getParameter("postcode");
			String country = request.getParameter("country");
			String creditCard = request.getParameter("creditCard");
			Order order = new Order(title,surname,givenName,email,unit,street,state,suburb,postcode,country,creditCard);
			boolean validation = order.formValidation(title,surname,givenName,email,unit,street,state,suburb,postcode,country,creditCard);
			//if the form has empty input
			if(validation == false)
			{
				request.setAttribute("order", order);
				request.setAttribute("isempty", "Can't be empty");
			}
			else
			{
				//forward to confirms purchasing order
				request.getSession().setAttribute("order", order);
		        RequestDispatcher view = request.getRequestDispatcher("purchases");
		        view.forward(request, response);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
