package pos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.objects.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.List;

/*
APCS-A PSET 0-0
Arnav Guneta
Period 2
Johns Creek High School
4/11/18 
 */
public class PayOutController {

	@FXML private AnchorPane base;
	@FXML private TableView<PayOut> tab;
	@FXML private TableColumn nameCOL;
	@FXML private TableColumn salaryCOL;

	private List<Employee> employees;

	private String name;
	private String salary;
	// data of time stamps inserted into the table
	private final ObservableList<PayOut> data = FXCollections.observableArrayList();

	private DecimalFormat df = new DecimalFormat("0.00");

	//initializes program
	public void initialize() {
		nameCOL.setCellValueFactory(new PropertyValueFactory("name"));
		salaryCOL.setCellValueFactory(new PropertyValueFactory("salary"));
		load();
		setUpTable();

	}

	public void setUpTable() {
		for (Employee e : employees) {
			name = e.getFirstName() + " " + e.getLastName();
			salary = "$" + df.format(e.getHoursWorkedWeek() * e.getWage());

			PayOut po = new PayOut(name, salary);
			data.add(po);
		}

		tab.setItems(data);
	}

	// loads home
	public void loadHome() {
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

	// loads the employees from employees.dat file
	public void load() {
		try {
			FileInputStream fis = new FileInputStream("employees.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			employees = (List<Employee>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			System.out.println("File couldn't be loaded {pay}");
		}
	}
}
