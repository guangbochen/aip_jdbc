package aip.uts.edu.au.id11376860.model;

import java.io.Serializable;
import java.sql.ResultSet;

import aip.uts.edu.au.id11376860.database.MysqlDatabase;

/**
 * this class validates user login
 * @author guangbo
 */
public class Admin implements Serializable {
	private MysqlDatabase sqldb;
	private String name;
	private String password;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * this methods validate admin login input
	 * @return true if found user
	 */
	public boolean validateLogin(String name, String password) 
	{
		boolean isadmin = false;
		String sql = "Select * From admin ";
		ResultSet sqlres;
		try {
			sqldb = new MysqlDatabase();
			sqldb.setDbUser("root");
			sqldb.setDbPass("root");
			sqldb.connect();
			sqlres = sqldb.query(sql);
			while (sqlres.next()) {
				if(sqlres.getString("name").equals(name) && sqlres.getString("password").equals(password))
				{
					//set login user is admin
					isadmin = true;
					this.name = name;
					break;
				}
			}
			sqldb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isadmin;
	}
	
}
