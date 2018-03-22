package pos.objects;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Employee {
	/**
	 * list of all employees
	 * temporary- might move to a controller later
	 */
	private List<Employee> employees = new ArrayList<>();
	
	private String firstName;
	private String lastName;
	/**
	 * HashMap for username, password
	 * username is the key, password is the value accessed
	 */
	private HashMap<String,String> account;
	/** 
	 * last 4 digits of SSN
	 */
	private int SSN;
	/**
	 * street address
	 * i.e 1100 waters dr, 30022, Alpharetta, GA
	 */
	private String address;
	/**
	 * type of payment
	 * 3 forms: direct deposit, check, cash
	 */
	private String payType;
	private int phoneNumber;
	private String email;
	/**
	 * time
	 * hour.minute
	 * used to compute hours worked for the day 
	 */
	private double clockIN;
	private double clockOUT;
	/**
	 * displayed on the GUI
	 * convert clockIN and clockOUT to amount of hours in decimals
	 */
	private double hoursWorkedDay;
	/**
	 * after clockOUT, add hoursWorkedDay to this
	 */
	private double hoursWorkedWeek;
	private double wage;
	
	public Employee() {
		this("first name not provided", "last name not provided", new HashMap<String, String>(), -9, "address not provided", "pay type not specified", -9, "email was not provided", -9);
		employees.add(this);
	}
	
	public Employee(String firstName, String lastName, HashMap<String, String> account, int SSN, String address, String payType, int phoneNumber, String email, int wage) {
		employees.add(this);
		setFirstName(firstName);
		setLastName(lastName);
		setAccount(account);
		setSSN(SSN);
		setAddress(address);
		setPayType(payType);
		setEmail(email);
		setPhoneNumber(phoneNumber);
		setWage(wage);
	}

	public void fire() {
		employees.remove(this);
	}
	
	public void resetHoursWorked() {
		this.setHoursWorkedWeek(0);
	}
	
	public void raise(int amount) {
		setWage(wage + amount);
	}
	
	public double getWage() {
		return wage;
	}

	public void setWage(double wage) {
		this.wage = wage;
	}
	public HashMap<String, String> getAccount() {
		return account;
	}

	public void setAccount(HashMap<String, String> account) {
		this.account = account;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSSN() {
		return SSN;
	}

	public void setSSN(int sSN) {
		SSN = sSN;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getClockIN() {
		return clockIN;
	}

	public void setClockIN(double clockIN) {
		this.clockIN = clockIN;
	}

	public double getClockOUT() {
		return clockOUT;
	}

	public void setClockOUT(double clockOUT) {
		this.clockOUT = clockOUT;
	}

	public double getHoursWorkedDay() {
		return hoursWorkedDay;
	}

	public void setHoursWorkedDay(double hoursWorkedDay) {
		this.hoursWorkedDay = hoursWorkedDay;
	}

	public double getHoursWorkedWeek() {
		return hoursWorkedWeek;
	}

	public void setHoursWorkedWeek(double hoursWorkedWeek) {
		this.hoursWorkedWeek = hoursWorkedWeek;
	}
	
	
}
