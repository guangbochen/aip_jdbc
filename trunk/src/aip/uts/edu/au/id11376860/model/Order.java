package aip.uts.edu.au.id11376860.model;
import java.io.Serializable;

/**
 * This class stores customer ordering details
 * @author guangbo
 */
public class Order implements Serializable {
	private int id;
	private String orderNumber;
	private String title;
	private String surname;
	private String givenName;
	private String email;
	private String unitNumber;
	private String street;
	private String state;
	private String suburb;
	private String postCode;
	private String country;
	private String paymentDetails;
	private String status;
	private double grandTotal;
	
	
	
	/**
	 * order constructor
	 */
	public Order() {
		super();
	}

	/**
	 * order constructor with all parameters
	 * @param title
	 * @param surname
	 * @param givenName
	 * @param email
	 * @param unitNumber
	 * @param street
	 * @param state
	 * @param suburb
	 * @param postCode
	 * @param country
	 * @param paymentDetails
	 * @param status
	 */
	public Order(String title, String surname, String givenName, String email,
			String unitNumber, String street, String state, String suburb,
			String postCode, String country, String paymentDetails, double grandTotal) 
	{
		super();
		this.title = title;
		this.surname = surname;
		this.givenName = givenName;
		this.email = email;
		this.unitNumber = unitNumber;
		this.street = street;
		this.state = state;
		this.suburb = suburb;
		this.postCode = postCode;
		this.country = country;
		this.paymentDetails = paymentDetails;
		this.grandTotal = grandTotal;
	}

	/**
	 * constructor for Order without grandTotl
	 * @param title
	 * @param surname
	 * @param givenName
	 * @param email
	 * @param unitNumber
	 * @param street
	 * @param state
	 * @param suburb
	 * @param postCode
	 * @param country
	 * @param paymentDetails
	 */
	public Order(String title, String surname, String givenName, String email,
			String unitNumber, String street, String state, String suburb,
			String postCode, String country, String paymentDetails) 
	{
		super();
		this.title = title;
		this.surname = surname;
		this.givenName = givenName;
		this.email = email;
		this.unitNumber = unitNumber;
		this.street = street;
		this.state = state;
		this.suburb = suburb;
		this.postCode = postCode;
		this.country = country;
		this.paymentDetails = paymentDetails;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}
	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the unitNumber
	 */
	public String getUnitNumber() {
		return unitNumber;
	}
	/**
	 * @param unitNumber the unitNumber to set
	 */
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the suburb
	 */
	public String getSuburb() {
		return suburb;
	}
	/**
	 * @param suburb the suburb to set
	 */
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the paymentDetails
	 */
	public String getPaymentDetails() {
		return paymentDetails;
	}
	/**
	 * @param paymentDetails the paymentDetails to set
	 */
	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the grandTotal
	 */
	public double getGrandTotal() {
		return grandTotal;
	}

	/**
	 * @param grandTotal the grandTotal to set
	 */
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	/**
	 * this methods validate the customer order form
	 * @param title
	 * @param surname
	 * @param givenName
	 * @param email
	 * @param unitNumber
	 * @param street
	 * @param state
	 * @param suburb
	 * @param postCode
	 * @param country
	 * @param paymentDetails
	 * @return true if is not empty, otherwise false
	 */
	public boolean formValidation(String title, String surname, String givenName, String email,
			String unitNumber, String street, String state, String suburb,
			String postCode, String country, String paymentDetails) 
	{
		if(title.equals("") || surname.equals("") || givenName.equals("") || email.equals("") 
				|| unitNumber.equals("") || street.equals("") || state.equals("") || suburb.equals("") 
				|| postCode.equals("") || country.equals("") || paymentDetails.equals(""))
		{
			return false;
		}
		else if(title.trim().isEmpty() || surname.trim().isEmpty() || givenName.trim().isEmpty() || email.trim().isEmpty()
			|| unitNumber.trim().isEmpty() || street.trim().isEmpty() || state.trim().isEmpty() || suburb.trim().isEmpty()
			|| postCode.trim().isEmpty() || country.trim().isEmpty() || paymentDetails.trim().isEmpty())
		{
			return false;
		}
		return true;
	}

}
