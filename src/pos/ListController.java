package pos;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.objects.Employee;

import java.io.*;
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

public class ListController {

	// instance of employee controller, the tab node
	private static EmployeeController ec;
	private static HomeController hc;

	// edit/ view mode
	private static Mode mode;
	// list indexing
	int index = 0;
	// observable side list
	@FXML private ListView<String> employeelist;
	// the tab pane
	@FXML private TabPane productpane;
	// titled pane used for activation on start up
	@FXML private TitledPane records;
	// observable list which is fed into the side listView
	private ObservableList<String> obs = FXCollections.observableArrayList();
	// a list of all employees
	private List<Employee> employees = new ArrayList<>();

	public ListController() {

	}

	// called from list controller and accepts its instance
	public static void getHomeControllerInstance(HomeController homeController) {
		hc = homeController;
	}

	// called in the employee controller, passes the instance
	public static void getEmployeeControllerInstance(EmployeeController EmployeeController) {
		ec = EmployeeController;
	}

	// initializes an empty product
	public void initialize() {
		// starts with view mode
		setMode(Mode.VIEW);
		// load saved lists from files
		load();
		// set up the employee side view list
		employeelist.setItems(obs);
		// if start up for first time, index 0 otherwise size of employees
		index = employees.size();
		// makes a new employee on start up
		initializeEmployee();
		// adds it to the list
		addEmployeeToList();
		// opens the tab
		openTab(index);
		// loads empty fields
		loademployee();
		// passes an instance of itself to the product controller
		EmployeeController.getListControllerInstance(this);
		// passes an instance of itself to the home controller
		HomeController.getListControllerInstance(this);
		// gives homecontroller a list of all employees saved
		hc.setEmployees(employees);
	}

	private void loademployee() {
		// listener for any clicks
		employeelist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {

				// get the list tab's name
				String value = employeelist.getSelectionModel().selectedItemProperty().getValue();

				// Goes through all tabs and if it is already opened, it ignores the rest of this method
				for (Tab tab : productpane.getTabs()) {
					if (tab.getText().equalsIgnoreCase(value)) {
						return;
					}
				}

				//gets the number from the list tab
				int indexFromList = Integer.parseInt(employeelist.getSelectionModel().getSelectedItem().split(" ")[1]);
				// opens the tab with the list tab's number
				openTab(indexFromList);
				// if the product already has stuff saved, it loads it
				ec.loadEmployeeFields();
			}

		});

		// clears selection
		employeelist.getSelectionModel().clearSelection();

	}

	// after product is initialized, the observable list gets an addition
	public void addEmployeeToList() {
		// index 1 of the string here means index 0 in the obs list
		obs.add("Employee " + index);
		// the list of products (left) all get updated with the obs list
		employeelist.setItems(obs);
	}

	// open tab from add or click on the list
	public void openTab(int index) {
		// opens the tab with the index
		// index of 1 in the string means index of 0 in obs list, list view, and products list
		try {
			Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("employee.fxml"));
			Tab tab = new Tab("Employee " + index, node);
			productpane.getTabs().add(tab);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// when add is pressed, this method is called to make an empty product and add it to the products list
	public void initializeEmployee() {
		Employee e = new Employee();
		employees.add(index, e);
		index++;
	}

	// when the menu item view mode is clicked, this is called
	public void viewMode() {
		setMode(Mode.VIEW);
		// if in view, make the fields not editable
		ec.makeEditable(false);

		// gets the product from products list using the tab index
		int index = Integer.parseInt(productpane.getSelectionModel().getSelectedItem().getText().split(" ")[1]) - 1;
		Employee e = employees.get(index);

		// sets up the view labels
		ec.initLabels(e);

	}

	// when the menu item edit mode is clicked, this is called
	public void editMode() {
		// edit mode is toggled
		setMode(Mode.EDIT);
		// make everything automatically editable
		ec.makeEditable(true);
		// make the labels disappear
		ec.toggleLabels(false);
		// show the editable text fields
		ec.toggleFields(true);
	}

	// loads home
	public void loadHome(AnchorPane base) {
		save();

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

	// saves the employees list and the employeeslist list or obs list
	public void save() {
		try {

			FileOutputStream fos = new FileOutputStream("employees.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(employees);
			oos.close();

			// print this out and read it instead of using oos
			PrintWriter pw = new PrintWriter(new FileOutputStream("obs.dat"));
			for (String s : obs) {
				pw.println(s);
				pw.flush();
			}
			pw.close();

		} catch (Exception e) {
			System.out.println("Error saving");
		}
	}

	// loads objects from files
	public void load() {
		try {
			FileInputStream fis = new FileInputStream("employees.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			employees = (List<Employee>) ois.readObject();
			ois.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("obs.dat"))));
			String line;
			while ((line = in.readLine()) != null) {
				obs.add(line);
			}

		} catch (Exception e) {
			System.out.println("File couldn't be loaded");
		}
	}

	// getters and setters
	public ObservableList<String> getObs() {
		return obs;
	}

	public void setObs(ObservableList<String> obs) {
		this.obs = obs;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ListView<String> getemployeelist() {
		return employeelist;
	}

	public void setemployeelist(ListView<String> employeelist) {
		this.employeelist = employeelist;
	}

	public TabPane getProductpane() {
		return productpane;
	}

	public void setProductpane(TabPane productpane) {
		this.productpane = productpane;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public static Mode getMode() {
		return mode;
	}

	public static void setMode(Mode mode) {
		ListController.mode = mode;
	}

}
