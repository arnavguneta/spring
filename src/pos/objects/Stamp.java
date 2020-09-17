package pos.objects;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class Stamp implements Serializable {
	// field variables
	private final SimpleStringProperty type;
	private final SimpleStringProperty date;
	private final SimpleStringProperty time;

	// constructor for object than goes into the table view
	public Stamp(String type, String date, String time) {
		this.type = new SimpleStringProperty(type);
		this.date = new SimpleStringProperty(date);
		this.time = new SimpleStringProperty(time);
	}

	// getters and setters

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public SimpleStringProperty typeProperty() {
		return type;
	}

	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	public SimpleStringProperty dateProperty() {
		return date;
	}

	public String getTime() {
		return time.get();
	}

	public void setTime(String time) {
		this.time.set(time);
	}

	public SimpleStringProperty timeProperty() {
		return time;
	}
}
