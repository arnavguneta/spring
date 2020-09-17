package pos.objects;

import java.io.Serializable;
import java.util.List;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class Employee implements Serializable {

	// basic field variables
	private String firstName;
	private String lastName;
	private String username;
	private String password;
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
	private String phoneNumber;
	private String email;
	/**
	 * clockIN/ clockOUT stamps
	 * saved in employee object and used for time punch screen
	 */
	private List<String> clockIN;
	private List<String> clockOUT;

	/**
	 * after clockOUT, add hoursWorkedDay to this
	 */
	private double hoursWorkedWeek = 0;
	private double wage;

	// null constructor
	public Employee() {
		this("first name not provided", "last name not provided", "username not provided", "password not provided", -9,
				"address not provided", "pay type not specified", "phone number not provided", "email was not provided",
				-9);
	}

	// full constructor
	public Employee(String firstName, String lastName, String username, String password, int SSN, String address,
			String payType, String phoneNumber, String email, int wage) {
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
		setSSN(SSN);
		setAddress(address);
		setPayType(payType);
		setEmail(email);
		setPhoneNumber(phoneNumber);
		setWage(wage);
	}

	// getters and setters
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getClockIN() {
		return clockIN;
	}

	public void setClockIN(List<String> clockIN) {
		this.clockIN = clockIN;
	}

	public List<String> getClockOUT() {
		return clockOUT;
	}

	public void setClockOUT(List<String> clockOUT) {
		this.clockOUT = clockOUT;
	}

	public double getHoursWorkedWeek() {
		return hoursWorkedWeek;
	}

	public void setHoursWorkedWeek(double hoursWorkedWeek) {
		this.hoursWorkedWeek = hoursWorkedWeek;
	}

}
