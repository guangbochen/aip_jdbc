package aip.uts.edu.au.id11376860.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aip.uts.edu.au.id11376860.model.Admin;

/**
 * Servlet implementation class IndexServlet
 * this class handles index request and forwards relevant responses
 */
public class IndexServlet extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
       
	/**
	 * class constructor
	 */
    public IndexServlet() 
    {
        super();
    }

    /**
     * this method calls doPost method
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * this method handles post requests
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getParameter("action") != null)
		{
			if(request.getParameter("action").equals("login"))
			{
				try
				{
					//validates admin login
					validateLogin(request, response);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			else if(request.getParameter("action").equals("logout"))
			{
				//logout admin
				HttpSession sess = request.getSession();
				sess.setAttribute("admin", null);
			}
		}
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
	}

	/**
	 * this method validates admin login
	 * @param request
	 * @param response
	 */
	private void validateLogin(HttpServletRequest request, HttpServletResponse response) 
	{
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		//empty name or password
		if(name.equals("") || password.equals(""))
		{
			request.setAttribute("invalid", "empty ");
			request.setAttribute("haserror", "has-error");
			//set user input value to avoid repeating 
			request.setAttribute("username", name);
			request.setAttribute("password", password);
			
		}
		else 
		{
			boolean validate = false;
			Admin admin = new Admin();
			validate = admin.validateLogin(name, password);
			
			if(validate == true) 
			{
				//login successfully
				HttpSession sess = request.getSession();
				sess.setAttribute("admin", admin);
			}
			else 
			{
				//invalid login
				request.setAttribute("invalid", " invalid ");
				request.setAttribute("haserror", "has-error");
				request.setAttribute("invalidLogin", "Incorrect password or user name, please try again!");
				//set user input value to avoid repeating 
				request.setAttribute("username", name);
				request.setAttribute("password", password);
			}
		}//end of else
	}
	
}

