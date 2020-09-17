package pos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.text.DecimalFormat;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class FunctionController {

	// instance of home controller
	private static HomeController hc;

	// gui vars
	@FXML private AnchorPane base;
	@FXML private Label username;
	@FXML private Label salary;

	private DecimalFormat df = new DecimalFormat("0.00");
	public FunctionController() {

	}

	// gets home controller instance from homecontroller call in init
	public static void getHomeControllerInstance(HomeController homeController) {
		hc = homeController;
	}

	// initializes user label and feeds its instance to cashIN
	public void initialize() {
		CashINController.getFunctionControllerInstance(this);
		username.setText("User: " + hc.getE().getFirstName() + " " + hc.getE().getLastName());
		salary.setText("Salary: $" + df.format(hc.getE().getHoursWorkedWeek() * hc.getE().getWage()));
	}

	// loads sales with the appropriate base
	public void loadSales() {
		hc.setBase(base);
		hc.loadSales();
	}

	// opens cash in screen
	public void loadCashIN() {
		hc.loadStage("cashin", base, "Cash In", 482, 460);
	}

	public void loadPayOut() {
		hc.loadStage("payout", base, "Pay Out", 507, 400);
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

}
