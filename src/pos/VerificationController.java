package pos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.objects.Employee;

import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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

public class VerificationController {

	// an instance of the home controller
	private static HomeController hc;

	// gui variables
	@FXML private AnchorPane base;
	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private Label error;
	@FXML private Button login;

	// field vars
	private boolean verified;
	private String target;

	// list of all employees
	private List<Employee> employees = new ArrayList<>();

	public VerificationController() {

	}

	// called from list controller and accepts its instance
	public static void getHomeControllerInstance(HomeController homeController) {
		hc = homeController;
	}

	// initializes the verification stage
	public void initialize() {
		// passes its reference to home controller
		HomeController.getVerificationControllerInstance(this);

		// loads the employees list from file
		try {
			FileInputStream fis = new FileInputStream("employees.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			employees = (List<Employee>) ois.readObject();
			ois.close();

		} catch (Exception e) {
			System.out.println("File couldn't be loaded");
		}

		onEnter();

	}

	// loads home stage
	@FXML public void loadHome() {
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

	/**
	 * triggered by login button press
	 * checks the validity of a user
	 */
	public void check() {
		// resets all errors
		setVerified(false);
		error.setVisible(false);
		username.styleProperty().setValue("");
		password.styleProperty().setValue("");

		// if the fields arent empty and the employee exists
		if (!(username.getText().isEmpty() || password.getText().isEmpty())) {
			for (Employee e : employees) {
				// and if the username and pass correctly match
				if (e.getUsername().equalsIgnoreCase(username.getText()) && e.getPassword()
						.equals(password.getText())) {
					// pass a copy of the verified employee to the home controller
					hc.setE(e);
					// make verified true
					setVerified(true);

					// if the target screen is time punch, it loads time punch
					if (target.equalsIgnoreCase("time punch")) {
						hc.loadStage("timepunch", base, "Time Punch", 650, 400);
					}

					// if the target screen is sales, it loads ring sales
					if (target.equalsIgnoreCase("ring sales")) {
						hc.loadStage("sales", base, "Ring Sales", 600, 400);
					}

					// if the target screen is functions, it loads functions
					if (target.equalsIgnoreCase("functions")) {
						hc.loadStage("functions", base, "Functions", 450, 350);
					}

					// if the target screen is settings, it loads settings
					if (target.equalsIgnoreCase("settings")) {
						hc.loadStage("settings", base, "Settings", 360, 460);
					}

				} else {
					// displays error messages
					error.setVisible(true);
					username.styleProperty().setValue("-fx-background-color: red , white , white; ");
					password.styleProperty().setValue("-fx-background-color: red , white , white; ");
				}
			}
		} else {
			// displays error messages
			error.setVisible(true);
			username.styleProperty().setValue("-fx-background-color: red , white , white; ");
			password.styleProperty().setValue("-fx-background-color: red , white , white; ");
		}
	}

	@FXML
	public void onEnter(){
		password.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				check();
			}
		});
	}

	// getters and setters

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public static HomeController getHc() {
		return hc;
	}

	public static void setHc(HomeController hc) {
		VerificationController.hc = hc;
	}
}
