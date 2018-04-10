package pos.objects;

import javafx.beans.property.SimpleStringProperty;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class MenuItem {
	// fields, SimpleProperty for TableView obj
	private final SimpleStringProperty item;
	private final SimpleStringProperty q;
	private final SimpleStringProperty price;

	// full constructor
	public MenuItem(String q, String item, String price) {
		this.q = new SimpleStringProperty(q);
		this.item = new SimpleStringProperty(item);
		this.price = new SimpleStringProperty(price);
	}

	// getters and setters
	public String getItem() {
		return item.get();
	}

	public void setItem(String item) {
		this.item.set(item);
	}

	public SimpleStringProperty itemProperty() {
		return item;
	}

	public String getQ() {
		return q.get();
	}

	public void setQ(String q) {
		this.q.set(q);
	}

	public SimpleStringProperty qProperty() {
		return q;
	}

	public String getPrice() {
		return price.get();
	}

	public void setPrice(String price) {
		this.price.set(price);
	}

	public SimpleStringProperty priceProperty() {
		return price;
	}
}
