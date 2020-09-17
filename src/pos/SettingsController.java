package pos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.objects.Store;

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

public class SettingsController {

	// home controller instance
	private static HomeController hc;

	// gui vars
	@FXML private Label username;
	@FXML private Label error1;
	@FXML private Label saved;
	@FXML private AnchorPane base;
	@FXML private TextField address;
	@FXML private TextField phoneNumber;
	@FXML private TextField opening;
	@FXML private TextField closing;

	// a list of all fields
	private List<TextField> fields = new ArrayList<>();

	// store object
	private Store store;

	// formatter
	private DecimalFormat df = new DecimalFormat("0.00");


	// gets home controller instance
	public static void getHomeControllerInstance(HomeController homeController) {
		hc = homeController;
	}

	// initializes basics and resets errors
	public void initialize() {
		store = hc.getStore();
		// if data exists, load it in fields
		if (!store.getAddress().equalsIgnoreCase("address not provided")) {
			System.out.println("test");
			loadData();
		}
		loadFields();
		resetErrors();
		saved.setVisible(false);
		username.setText("User: " + hc.getE().getFirstName() + " " + hc.getE().getLastName());
	}

	// called when save button is pressed2
	@FXML private void save() {
		resetErrors();
		saved.setVisible(false);
		try {
			store = new Store(address.getText(), closing.getText(), opening.getText(), phoneNumber.getText());
			saved.setVisible(true);
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

	// loads store data
	private void loadData() {
		System.out.println("testtt");
		address.setText(store.getAddress());
		phoneNumber.setText(store.getPhone());
		closing.setText(store.getClosing());
		opening.setText(store.getOpening());
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
		fields.add(address);
		fields.add(phoneNumber);
		fields.add(opening);
		fields.add(closing);
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
			FileOutputStream fos = new FileOutputStream("store.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(store);
			oos.close();

		} catch (Exception e) {
			System.out.println("Error saving");
		}
	}

}
