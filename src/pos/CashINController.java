package pos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.objects.Employee;
import pos.objects.Register;

import javax.xml.soap.Text;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
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

public class CashINController {

	// gui vars
	@FXML private Label username;
	@FXML private Label error1;
	@FXML private Label ou;
	@FXML private AnchorPane base;
	@FXML private TextField aRegister;
	@FXML private TextField aChangeDrawer;
	@FXML private TextField item1;
	@FXML private TextField item2;
	@FXML private TextField item3;
	@FXML private TextField item4;

	// instances of required controllers
	private static HomeController hc;
	private static FunctionController fc;

	// a list of all fields
	private List<TextField> fields = new ArrayList<>();

	// register
	private Register register;

	// formatter
	private DecimalFormat df = new DecimalFormat("0.00");

	// gets home controller instance from home controller
	public static void getHomeControllerInstance(HomeController homeController) {
		hc = homeController;
	}

	// gets function controller instance from function controller
	public static void getFunctionControllerInstance(FunctionController functionController) {
		fc = functionController;
	}

	// initializes gui basics and resets errors
	public void initialize() {
		register = hc.getRegister();
		loadFields();
		resetErrors();
		ou.setVisible(false);
		username.setText("User: " + hc.getE().getFirstName() + " " + hc.getE().getLastName());
	}

	// called when save button is pressed
	@FXML private void save() {
		resetErrors();
		// initialize register obj
		try {
			register.setCashIN(Double.parseDouble(aRegister.getText()));
			register.setChange(Double.parseDouble(aChangeDrawer.getText()));

			// set over/under to visible
			ou.setVisible(true);

			// proper over/under label
			if (register.getTotal() - register.getCashIN() < 0) {
				ou.setText("Over/Under: $" + df.format(Math.abs(register.getTotal() - register.getCashIN())));
			} else if (register.getTotal() - register.getCashIN() == 0) {
				ou.setText("Over/Under: $" + df.format(Math.abs(register.getTotal() - register.getCashIN())));
			} else {
				ou.setText("Over/Under: -$" + df.format(Math.abs(register.getTotal() - register.getCashIN())));
			}

			// if no shortage, make cash in = total
			if (register.getTotal() - register.getCashIN() == 0) {
				register.setTotal(register.getCashIN());
			}

		} catch (Exception e) {
			System.out.println("error occured");
			// if save was pressed and some fields were null
			// goes through all fields
			for (TextField field : fields) {
				// if the field is empty, make it red and pop up an error
				if (field.getText().isEmpty()) {
					error1.setVisible(true);
					field.styleProperty().setValue("-fx-background-color: red , white , white; ");
				}
			}
		}
	}

	// resets all error messages
	private void resetErrors() {
		error1.setVisible(false);
		for (TextField field : fields) {
			field.styleProperty().setValue("");
		}
	}

	// loads all fields for error check
	private void loadFields() {
		fields.add(aRegister);
		fields.add(aChangeDrawer);
		fields.add(item1);
		fields.add(item2);
		fields.add(item3);
		fields.add(item4);
	}

	// loads home
	public void loadHome() {
		saveOBJ();

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
	public void saveOBJ() {
		try {

			FileOutputStream fos = new FileOutputStream("register.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(register);
			oos.close();

		} catch (Exception e) {
			System.out.println("Error saving");
		}
	}

}
