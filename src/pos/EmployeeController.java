package pos;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import pos.objects.Employee;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeController {

	// instance of the list controller
	private static ListController lc;
	// string wrapper for css (used in empty field error)
	private final ReadOnlyStringWrapper css = new ReadOnlyStringWrapper();
	// a list of all fields
	private List<TextField> fields = new ArrayList<>();

	// edit mode vars
	@FXML private TextField name;
	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private TextField SSN;
	@FXML private TextField address;
	@FXML private TextField email;
	@FXML private TextField phoneNumber;
	@FXML private TextField wage;

	// view mode vars
	@FXML private Label nameV;
	@FXML private Label usernameV;
	@FXML private Label passwordV;
	@FXML private Label SSNV;
	@FXML private Label addressV;
	@FXML private Label emailV;
	@FXML private Label phoneNumberV;
	@FXML private Label wageV;

	// fxml vars
	@FXML private Button delete;
	@FXML private Label error1;
	@FXML private AnchorPane base;

	public EmployeeController() {

	}

	// called from list controller and accepts its instance
	public static void getListControllerInstance(ListController listController) {
		lc = listController;
	}

	// initializes tab node
	public void initialize() {
		// resets all error messages
		resetErrors();
		// passes its instance to list controller
		ListController.getEmployeeControllerInstance(this);
		// makes everything uneditable by default
		makeEditable(false);
		// clears all field
		clearFields();
		// loads all fields with appropriate values
		loadFields();

	}

	// this is called whenever the add button is pressed
	@FXML public void add() {
		// sets up an empty blank employee
		lc.initializeEmployee();
		lc.addEmployeeToList();
		lc.openTab(lc.index);

		// if being called from a singular tab, it re-enables the delete button
		if (lc.getProductpane().getTabs().size() == 1) {
			getDelete().setDisable(true);
		} else {
			getDelete().setDisable(false);
		}
	}

	// called when the edit button is pressed
	@FXML private void edit() {
		// makes fields editable
		makeEditable(true);

		// if it is in view mode, hide the labels and show the fields
		if (lc.getMode() == Mode.VIEW) {
			toggleLabels(false);
			toggleFields(true);
		}
	}

	@FXML private void delete() {
		// gets the employee from the employees list using the tab index
		int index =
				Integer.parseInt(lc.getProductpane().getSelectionModel().getSelectedItem().getText().split(" ")[1]) - 1;
		Employee e = lc.getEmployees().get(index);

		// if this is the only tab open, disable, otherwise continue
		if (lc.getemployeelist().getItems().size() == 1) {
			getDelete().setDisable(true);
			return;
		} else {
			getDelete().setDisable(false);
		}

		// creates an alert with the message specified below and waits for a confirmation
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
				"Are you sure you want to delete '" + lc.getProductpane().getSelectionModel().getSelectedItem()
						.getText() + "'?");

		Optional<ButtonType> result = alert.showAndWait();
		// on confirmation:
		if (result.isPresent() && result.get() == ButtonType.OK) {
			// it removes it from the list and adds a place holder to keep the indexes in check
			lc.getEmployees().remove(e);
			lc.getEmployees().add(index, new Employee());

			// gets the name of the tab being deleted
			String name = lc.getProductpane().getSelectionModel().getSelectedItem().getText();
			// closes the tab
			lc.getProductpane().getTabs().remove(lc.getProductpane().getSelectionModel().getSelectedIndex());

			// from the list view, compare the names with the tab names
			for (String s : lc.getObs()) {
				// if it does match, remove it from the list and update the list view
				if (s.equalsIgnoreCase(name)) {
					lc.getObs().remove(s);
					lc.getemployeelist().setItems(lc.getObs());
				}
			}

		}
	}

	// called when save button is pressed
	@FXML private void save() {
		// gets the open tab and get the index[for employees list] from there
		int indexFromTab =
				Integer.parseInt(lc.getProductpane().getSelectionModel().getSelectedItem().getText().split(" ")[1]) - 1;
		// loads the employee from the employees list
		Employee e = lc.getEmployees().get(indexFromTab);
		// makes the all fields non editable unless in edit mode
		makeEditable(false);

		// resets all errors
		resetErrors();

		// tries to save all values into the employee container class
		try {
			String first = name.getText().split(" ")[0];
			String last = name.getText().split(" ")[1];

			e.setFirstName(first);
			e.setLastName(last);
			e.setUsername(username.getText());
			e.setPassword(password.getText());
			e.setAddress(address.getText());
			e.setEmail(email.getText());
			e.setSSN(Integer.parseInt(SSN.getText()));

			e.setWage(Double.parseDouble(wage.getText()));
			e.setPhoneNumber(phoneNumber.getText());

		} catch (Exception ex) {
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

		// if its in view mode, initialize the labels with the new values and display them
		if (lc.getMode() == Mode.VIEW) {
			initLabels(e);
		}
	}

	// toggles all view labels
	public void toggleLabels(Boolean b) {
		nameV.setVisible(b);
		usernameV.setVisible(b);
		passwordV.setVisible(b);
		SSNV.setVisible(b);
		addressV.setVisible(b);
		emailV.setVisible(b);
		phoneNumberV.setVisible(b);
		wageV.setVisible(b);
	}

	// toggles all fields
	public void toggleFields(Boolean b) {
		name.setVisible(b);
		username.setVisible(b);
		password.setVisible(b);
		SSN.setVisible(b);
		address.setVisible(b);
		email.setVisible(b);
		phoneNumber.setVisible(b);
		wage.setVisible(b);
	}

	// initializes all labels
	public void initLabels(Employee e) {
		// if the employee has preset values, then show the labels
		if (lc.getMode() == Mode.VIEW) {
			if (e.getSSN() != -9) {
				toggleLabels(true);
				toggleFields(false);
				// sets values for display labels
				nameV.setText(e.getFirstName() + " " + e.getLastName());
				usernameV.setText(e.getUsername());
				passwordV.setText(e.getPassword());
				SSNV.setText(Integer.toString(e.getSSN()));
				addressV.setText(e.getAddress());
				emailV.setText(e.getEmail());
				phoneNumberV.setText(e.getPhoneNumber());
				DecimalFormat df = new DecimalFormat("#.00");
				wageV.setText("$" + df.format(e.getWage()));
			} else {
				// if the employee doesn't have preset values, meaning it is empty, ask for input
				toggleLabels(false);
				toggleFields(true);
			}
		}

	}

	// toggles the state of the fields
	public void makeEditable(Boolean bool) {
		// if in edit mode, make everything editable
		if (!bool && lc.getMode() == Mode.EDIT)
			bool = true;
		name.setEditable(bool);
		username.setEditable(bool);
		password.setEditable(bool);
		SSN.setEditable(bool);
		address.setEditable(bool);
		email.setEditable(bool);
		phoneNumber.setEditable(bool);
		wage.setEditable(bool);
	}

	// called when opened from list tab
	public void loadEmployeeFields() {
		// gets the index from the list
		int indexFromList =
				Integer.parseInt(lc.getemployeelist().getSelectionModel().getSelectedItem().split(" ")[1]) - 1;
		// gets the employee out from the employees list
		Employee e = lc.getEmployees().get(indexFromList);
		initLabels(e);
		// if its not null, load the saved data
		if (e.getWage() != -9) {
			name.setText(e.getFirstName() + " " + e.getLastName());
			username.setText(e.getUsername().toLowerCase());
			password.setText(e.getPassword());
			SSN.setText(Integer.toString(e.getSSN()));
			address.setText(e.getAddress());
			email.setText(e.getEmail());
			phoneNumber.setText(e.getPhoneNumber());
			wage.setText(Double.toString(e.getWage()));
		}
	}

	// calls load home in the ListController file
	public void loadHome() {
		lc.loadHome(base);
	}

	// clears all fields
	private void clearFields() {
		name.clear();
		username.clear();
		password.clear();
		SSN.clear();
		address.clear();
		email.clear();
		phoneNumber.clear();
		wage.clear();
	}

	// puts all fields in the field list
	private void loadFields() {
		fields.add(name);
		fields.add(username);
		fields.add(password);
		fields.add(SSN);
		fields.add(address);
		fields.add(email);
		fields.add(phoneNumber);
		fields.add(wage);
	}

	// resets all error messages
	private void resetErrors() {
		error1.setVisible(false);
		for (TextField field : fields) {
			field.styleProperty().setValue("");
		}
	}

	// getters and setters
	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	}
}
