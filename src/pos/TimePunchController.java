package pos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.objects.Employee;
import pos.objects.Stamp;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class TimePunchController {
	// homecontroller instance
	private static HomeController hc;

	// data of time stamps inserted into the table
	private final ObservableList<Stamp> data = FXCollections.observableArrayList();
	// TableView setup
	@FXML TableView<Stamp> table;
	@FXML TableColumn typeCOL;
	@FXML TableColumn dateCOL;
	@FXML TableColumn timeCOL;
	// GUI field variables
	@FXML private Button home;
	@FXML private Button clockin;
	@FXML private Button clockout;
	@FXML private Label username;
	@FXML private Label totalHours;
	@FXML private AnchorPane base;

	// TableView variables
	private String type;
	private String date;
	private String time;

	// employee from login and a list of all employees
	private Employee employee;
	private List<Employee> employees = new ArrayList<>();

	// called from home controller and accepts its instance
	public static void getHomeControllerInstance(HomeController homeController) {
		hc = homeController;
	}

	// initializes the program with the appropriate conditions
	public void initialize() {
		typeCOL.setCellValueFactory(new PropertyValueFactory("type"));
		dateCOL.setCellValueFactory(new PropertyValueFactory("date"));
		timeCOL.setCellValueFactory(new PropertyValueFactory("time"));

		// sneds hc its instance
		HomeController.getTimePunchControllerInstance(this);
		// gets the verified employee from log in
		employee = hc.getE();

		// loads the employees list from file
		try {
			FileInputStream fis = new FileInputStream("employees.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			employees = (List<Employee>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			System.out.println("Error reading file");
		}

		// if the employee is already clocked in
		if (employee.getClockIN() != null) {
			if (employee.getClockIN().get(0).equalsIgnoreCase("Clock In")) {
				// creates a serializable list for clock in
				List<String> clockIN = employee.getClockIN();

				type = "Clock In";
				date = clockIN.get(1);
				time = clockIN.get(2);

				// stamp created and added to table
				Stamp stamp = new Stamp(type, date, time);

				updateTable(stamp);
				// clockout is erased
				employee.setClockOUT(null);
			}
		}
		// if the employee has clockout data and is reopening the program
		if (employee.getClockOUT() != null) {
			if (employee.getClockOUT().get(0).equalsIgnoreCase("Clock Out")) {
				// delete all clock in and clock out data
				employee.setClockOUT(null);
				employee.setClockIN(null);
			}
		}

		// disable/ enable buttons appropriately
		toggleBtns();

		// if the person is already clocked in
		if (type == "Clock In") {
			// log the current time
			String tempTime = new SimpleDateFormat("hh:mm:ss").format(new Date());
			String[] tempTimeSplit = tempTime.split(":");
			String[] timeSplit = time.split(":");
			//total hours: gets the hour difference, gets the minute difference- divided by 60 to convert to hours
			double hours = (Math.abs(Double.parseDouble(timeSplit[0]) - Double.parseDouble(tempTimeSplit[0]))) + (
					(Math.abs(Double.parseDouble(timeSplit[1]) - Double.parseDouble(tempTimeSplit[1]))) / 60);
			NumberFormat formatter = new DecimalFormat("#0.00");
			// display hours worked for current session
			totalHours.setText("Total Hours: " + formatter.format(hours));

		} else {
			totalHours.setText("Total Hours: 0.0");
		}

		// update username labels
		username.setText(employee.getFirstName() + " " + employee.getLastName());

	}

	/**
	 * activated when clockIN button is pressed
	 * calls the initDateTime method with appropriate params
	 */
	public void clockIN() {
		type = "Clock In";
		date = new SimpleDateFormat("MM/dd/yy").format(new Date());
		time = new SimpleDateFormat("hh:mm:ss a").format(new Date());
		Stamp stamp = new Stamp(type, date, time);

		List<String> clockIN = new ArrayList<>();
		clockIN.add(type);
		clockIN.add(date);
		clockIN.add(time);

		employee.setClockIN(clockIN);
		employee.setClockOUT(null);

		updateTable(stamp);
		toggleBtns();
	}

	/**
	 * activated when clockOUT button is pressed
	 * calls the initDateTime method with appropriate params
	 * creates an arraylist which is stored in the employee object (serializable version of ObsLists)
	 */
	public void clockOUT() {
		type = "Clock Out";
		date = new SimpleDateFormat("MM/dd/yy").format(new Date());
		time = new SimpleDateFormat("hh:mm:ss a").format(new Date());
		Stamp stamp = new Stamp(type, date, time);

		List<String> clockOUT = new ArrayList<>();
		clockOUT.add(type);
		clockOUT.add(date);
		clockOUT.add(time);

		employee.setClockOUT(clockOUT);
		employee.setClockIN(null);

		updateTable(stamp);
		toggleBtns();

		employee.setHoursWorkedWeek(employee.getHoursWorkedWeek() + Double.parseDouble(totalHours.getText()));
	}

	/**
	 * toggles buttons appropriately
	 * if clocked in, allow clock out, disable clock in
	 * if clocked out, allow clock in, disable clock out
	 * if null, allow clock in, disable clock out
	 */
	public void toggleBtns() {
		if (type == null) {
			clockin.setDisable(false);
			clockout.setDisable(true);
		} else if (type == "Clock In") {
			clockin.setDisable(true);
			clockout.setDisable(false);
		} else if (type == "Clock Out") {
			clockin.setDisable(false);
			clockout.setDisable(true);
		}
	}

	/**
	 * sets up the variables required by the TableView
	 * adds a new entry to the TableView
	 *
	 * @param stamp time stamp
	 */
	public void updateTable(Stamp stamp) {
		data.add(stamp);
		table.setItems(data);
	}

	// loads home stage
	public void loadHome() {
		int index = -9;

		// iterates thru employees list to find the logged in employees index
		for (Employee e : employees) {
			if (e.getUsername().equalsIgnoreCase(employee.getUsername())) {
				index = employees.indexOf(e);
			}
		}

		// replaces the previous obj at the index with the logged in employee with appropriate clock in and clock out fields
		if (index != -9) {
			employees.remove(index);
			employees.add(index, employee);
		}

		// resaves the employees list
		try {
			FileOutputStream fos = new FileOutputStream("employees.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(employees);
			oos.close();
		} catch (IOException e) {
			System.out.println("Error saving employee list from TPC");
		}

		Stage stage = (Stage) base.getScene().getWindow();

		try {
			Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
			stage.setScene(new Scene(root, 720, 400));
			stage.setTitle("POS Home");
			stage.show();
		} catch (Exception e) {
			System.out.println("Error");
		}

	}

}
