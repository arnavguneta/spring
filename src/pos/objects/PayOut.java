package pos.objects;

import javafx.beans.property.SimpleStringProperty;

/*
APCS-A PSET 0-0
Arnav Guneta
Period 2
Johns Creek High School
4/11/18 
 */
public class PayOut {

	private final SimpleStringProperty name;
	private final SimpleStringProperty salary;

	public PayOut(String name, String salary) {
		this.name = new SimpleStringProperty(name);
		this.salary = new SimpleStringProperty(salary);
	}

	public String getName() {
		return name.get();
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getSalary() {
		return salary.get();
	}

	public SimpleStringProperty salaryProperty() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary.set(salary);
	}
}
