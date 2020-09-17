package pos.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class Store implements Serializable{

	/**
	 * list of all employees
	 * object created in controller
	 */
	private List<Employee> employees = new ArrayList<>();
	/**
	 * street address
	 * i.e 1100 waters dr, 30022, Alpharetta, GA
	 */
	private String address;
	/**
	 * time
	 * used to compute hours worked for the day
	 */
	private String closing;
	private String opening;
	/**
	 * owner passed in as an employee object
	 */
	private String phone;

	// null constructor
	public Store() {
		this("address not provided", "closing not provided", "opening not provided", "phone number not provided");
	}

	// full constructor
	public Store(String address, String closing, String opening, String phone) {
		setAddress(address);
		setClosing(closing);
		setOpening(opening);
		setPhone(phone);
	}

	// getters and setters
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClosing() {
		return closing;
	}

	public void setClosing(String closing) {
		this.closing = closing;
	}

	public String getOpening() {
		return opening;
	}

	public void setOpening(String opening) {
		this.opening = opening;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
