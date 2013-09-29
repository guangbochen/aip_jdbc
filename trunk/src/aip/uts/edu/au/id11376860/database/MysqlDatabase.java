package aip.uts.edu.au.id11376860.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * this class is mysql database bean that connects to the mysql database
 * @author guangbo
 *
 */
public class MysqlDatabase implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String dbDriver = "com.mysql.jdbc.Driver";
	private static final String dbURL = "jdbc:mysql://127.0.0.1/fcSportsware";

	  /* Defining the attributes.
	     Notice that they are all declared private.
	     They cannot (and must not) be accessed directly
	     from other objects, or it would violate the
	     JavaBean rules.
	  */
	  private String dbUser;
	  private String dbPass;
	  private Connection conn;
	  
	  /**
	   * this methods connecting to the mysql database
	   * @throws ClassNotFoundException
	   * @throws SQLException
	   */
	  public void connect() throws ClassNotFoundException, SQLException 
	  {
		  if (dbUser != null) 
		  {
		      Properties props = new Properties();
		      props.setProperty("user", dbUser);
		      props.setProperty("password", dbPass);
	
		      Class.forName(dbDriver);
		      this.conn = DriverManager.getConnection(dbURL, props);
		  }
	  }

	  /**
	   * this method closes the database connection
	   * @throws SQLException
	   */
	  public void close() throws SQLException 
	  {
		  this.conn.close();
	  }

	  /**
	   * this method creates sql set statement
	   * @param sql
	   * @return set statement
	   * @throws SQLException
	   */
	  public ResultSet query(String sql) throws SQLException 
	  {
		  Statement s = conn.createStatement();
		  return (s.executeQuery(sql));
	  }

	  /**
	   * this method creates sql update statement
	   * @param sql
	   * @return update statement
	   * @throws SQLException
	   */
	  public int update(String sql) throws SQLException 
	  {
		  Statement s = conn.createStatement();
		  return (s.executeUpdate(sql));
	  }

	  /**
	   *  this method gets bduser account
	   */
	  public String getDbUser()
	  {
		  return this.dbUser; 
	  }

	  /**
	   * this method sets dbuser account
	   * @param dbUser
	   */
	  public void setDbUser(String dbUser) 
	  {
		 this.dbUser = dbUser;
	  }

	  /**
	   * this method gets dbPass property
	   * @return password
	   */
	  public String getDbPass()
	  { 
		  return this.dbPass;
	  }
	  
	  /**
	   * this method sets dbPass property
	   */
	  public void setDbPass(String dbPass) 
	  {
		  this.dbPass = dbPass;
	  }
	  
	  /**
	   * this method connects to the database and used for sql prepared statement
	   * @return connection for the database
	   */
	  public Connection getDBConnection()
	  {
		  try
		  {
			  if (dbUser != null) 
			  {
			      Properties props = new Properties();
			      props.setProperty("user", dbUser);
			      props.setProperty("password", dbPass);
		
			      Class.forName(dbDriver);
			      this.conn = DriverManager.getConnection(dbURL, props);
			      return conn;
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return null;
	  }
}