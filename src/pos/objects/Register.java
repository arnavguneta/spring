package pos.objects;

import java.io.Serializable;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class Register implements Serializable {
	/**
	 * change drawer (separate from the register)
	 * change should always be $200
	 */
	private double change;
	/**
	 * the total is all of the money in the register acquired from a cash in
	 * total should only be altered when cash report comes out to be 0 or
	 * when the constructor is called
	 */
	private double total;
	/**
	 * value fed into the object from an initial cash in when the register is created
	 * a cashIN is counting of inventory, specifically the money in the register
	 * to compute if the register is missing money or not
	 */
	private double cashIN;

	// null constructor
	public Register() {
		setChange(-10);
		setTotal(-10);
		setCashIN(-10);
	}

	// full constructor
	public Register(double change, double total) {
		setChange(change);
		setTotal(total);
	}

	// getters and setters
	public double cashReport(double cashIN) {
		return total - cashIN;
	}

	public void cashDrop(double amountDropped) {
		total -= amountDropped;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getCashIN() {
		return cashIN;
	}

	public void setCashIN(double cashIN) {
		this.cashIN = cashIN;
	}
}
