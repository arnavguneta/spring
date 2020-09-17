package pos;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import pos.objects.Employee;
import pos.objects.Register;
import pos.objects.Store;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class HomeController {

	// instances of verification, list, and time punch controllers
	private static VerificationController vc;
	private static ListController lc;
	private static TimePunchController tpc;

	// GUI variables
	@FXML private Button timePunch;
	@FXML private Button ring;
	@FXML private Button functions;
	@FXML private Button settings;
	@FXML private Button employee;
	@FXML private Button power;
	@FXML private Label date;
	@FXML private Label timeLabel;

	// the base anchorpage which everything is built upon
	@FXML private AnchorPane base;

	// storage variables
	private List<Employee> employees = new ArrayList<>();
	private Employee e = new Employee();
	private Register register;
	private Store store;

	// variables for clock
	private int minute;
	private int hour;
	private int second;

	// methods called in verification, list, and time punch and used to obtain instances of specified classes
	public static void getVerificationControllerInstance(VerificationController verificationController) {
		vc = verificationController;
	}

	public static void getListControllerInstance(ListController listController) {
		lc = listController;
	}

	public static void getTimePunchControllerInstance(TimePunchController timePunchController) {
		tpc = timePunchController;
	}

	// sets up date and other basic home functions and sends verification controller its instance
	public void initialize() {
		date.setText(new SimpleDateFormat("MM/dd/yy").format(new Date()));
		VerificationController.getHomeControllerInstance(this);
		ListController.getHomeControllerInstance(this);
		TimePunchController.getHomeControllerInstance(this);
		SalesController.getHomeControllerInstance(this);
		FunctionController.getHomeControllerInstance(this);
		CashINController.getHomeControllerInstance(this);
		SettingsController.getHomeControllerInstance(this);

		store = new Store();
		register = new Register(200, 200);

		load();

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			Calendar cal = Calendar.getInstance();
			second = cal.get(Calendar.SECOND);
			minute = cal.get(Calendar.MINUTE);
			hour = cal.get(Calendar.HOUR);
			String ampm;
			if (cal.get(Calendar.AM_PM) == 0) {
				ampm = "AM";
			} else {
				ampm = "PM";
			}

			if (second < 10 && second < 10)
				timeLabel.setText(hour + ":0" + (minute) + ":0" + (second) + " " + ampm);
			else if (minute < 10)
				timeLabel.setText(hour + ":0" +(minute) + ":" + (second) + " " + ampm);
			else if (second < 10) {
				timeLabel.setText(hour + ":0" + (minute) + ":" + (second) + " " + ampm);
			}
			else
				timeLabel.setText(hour + ":" + (minute) + ":" + (second) + " " + ampm);

		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

	/**
	 * activated whenever the button power is clicked
	 * powers off the POS system
	 */
	public void powerOff() {

		// creates an alert with the message specified below and waits for a confirmation
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
				"Are you sure you want to power off the POS system?");

		Optional<ButtonType> result = alert.showAndWait();
		// on confirmation:
		if (result.isPresent() && result.get() == ButtonType.OK) {
			// power off GUI
			Stage stage = (Stage) base.getScene().getWindow();
			stage.close();

		}

	}

	/**
	 * activated whenever the button timePunch is clicked
	 * loads the time punch screen using the loadStage() method
	 */
	public void loadTimePunch() {
		authUser("time punch");
	}

	/**
	 * activated whenever the button ring is clicked
	 * loads the ring sales screen but needs authorization
	 */
	public void loadSales() {
		authUser("ring sales");
	}

	/**
	 * activated whenever the button functions is clicked
	 * loads the functions screen but needs authorization
	 */
	public void loadFunctions() {
		authUser("functions");
	}

	/**
	 * activated whenever the button settings is clicked
	 * loads the settings screen but needs authorization
	 */
	public void loadSettings() {
		authUser("settings");
	}

	/**
	 * activated whenever the button employees is clicked
	 * loads the employee screen using the loadStage() method
	 */
	public void loadEmployees() {
		loadStage("list", base, "Employees", 650, 480);
	}

	/**
	 * authorization for time punch, functions, and ring sales
	 * opens verification screen
	 * @param target is the screen it would open next if the user was verified
	 */
	public void authUser(String target) {
		loadStage("verification", base, "Authentication", 450, 300);
		vc.setTarget(target);
	}

	/**
	 * method layout used by other listener methods
	 * used to load different scenes on the same stage
	 *
	 * @param fileName the fxml filename that contains the specific GUI needed to be opened
	 * @param base the anchor pane which is the base, important since it is also called from vc
	 * @param title title of the stage
	 * @param width width of the stage
	 * @param height height of the stage
	 */
	public void loadStage(String fileName, AnchorPane base, String title, int width, int height) {
		// uses the base anchor pane to get the stage
		Stage stage = (Stage) base.getScene().getWindow();

		try {
			// sets up the stage with the new scene
			Parent root = FXMLLoader.load(getClass().getResource(fileName + ".fxml"));
			stage.setScene(new Scene(root, width, height));
			stage.setTitle(title);
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

			FileInputStream fis1 = new FileInputStream("register.dat");
			ObjectInputStream ois1 = new ObjectInputStream(fis1);
			register = (Register) ois1.readObject();
			ois1.close();

			FileInputStream fis2 = new FileInputStream("store.dat");
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			store = (Store) ois2.readObject();
			ois2.close();
		} catch (Exception e) {
			System.out.println("File couldn't be loaded {home}");
		}
	}

	// getters and setters
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee getE() {
		return e;
	}

	public void setE(Employee e) {
		this.e = e;
	}

	public AnchorPane getBase() {
		return base;
	}

	public void setBase(AnchorPane base) {
		this.base = base;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
}
