package pos.objects;

import java.util.ArrayList;
import java.util.List;

/*
APCS-A PSET 0-0
Arnav Guneta
Period 2
Johns Creek High School
3/21/18 
 */
public class Store {

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
	 * hour.minute
	 * used to compute hours worked for the day
	 */
	private double closing;
	private double opening;
	/**
	 * owner passed in as an employee object
	 */
	private Employee owner;
	private int phone;

	public Store() {
		this("address not provided", -9.0, -9.0, new Employee(), -9);
	}

	public Store(String address, double closing, double opening, Employee owner, int phone) {
		setAddress(address);
		setClosing(closing);
		setOpening(opening);
		setOwner(owner);
		setPhone(phone);
	}

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

	public double getClosing() {
		return closing;
	}

	public void setClosing(double closing) {
		this.closing = closing;
	}

	public double getOpening() {
		return opening;
	}

	public void setOpening(double opening) {
		this.opening = opening;
	}

	public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}
}
