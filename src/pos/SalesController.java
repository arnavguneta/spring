package pos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pos.objects.MenuItem;
import pos.objects.Register;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class SalesController {

	// instance of homecontroller
	private static HomeController hc;

	// data that is inserted into the table
	private final ObservableList<MenuItem> data = FXCollections.observableArrayList();

	// tableview vars
	@FXML TableView<MenuItem> table;
	@FXML TableColumn qCOL;
	@FXML TableColumn itemCOL;
	@FXML TableColumn priceCOL;

	// gui vars
	@FXML private TitledPane orderPane;
	@FXML private AnchorPane base;
	@FXML private Label username;

	// menu item vars
	private String q;
	private String item;
	private String price;
	private Register register;

	// iterators/ indexers
	private int index = 1;
	private double total = 0;

	// decimal formatter used for doubles
	private DecimalFormat df = new DecimalFormat("#.00");

	// called in home, used to obtain HomeController instance
	public static void getHomeControllerInstance(HomeController homeController) {
		hc = homeController;
	}

	// initializes table view and user label
	public void initialize() {
		username.setText("User: " + hc.getE().getFirstName() + " " + hc.getE().getLastName());
		qCOL.setCellValueFactory(new PropertyValueFactory("q"));
		itemCOL.setCellValueFactory(new PropertyValueFactory("item"));
		priceCOL.setCellValueFactory(new PropertyValueFactory("price"));
		table.setItems(data);
		register = hc.getRegister();
	}

	// triggered by home button, loads home screen
	public void loadHome() {
		// saves register object
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

	/**
	 * dummy method
	 * everything is randomly generated since it is just an example
	 * triggered by press of item buttons
	 */
	public void dummyItem() {
		// if total is already 0
		if (total == 0) {
			// quantity is always one for dummy purposes
			q = "1";
			// item label
			item = "Item " + index;
			index++;
			// random number generator for random prices $1-$16
			Random rand = new Random();

			double n = rand.nextDouble();
			int i = rand.nextInt(15) + 1;
			double c = i + n;
			price = "$" + df.format(c);
			// menuitem obj created from previous vars
			MenuItem mi = new MenuItem(q, item, price);
			// added to table
			data.add(mi);
			table.setItems(data);
		} else {
			// resets table view and calls itself to continue with more transactions
			total = 0;
			data.clear();
			table.setItems(data);
			index = 1;
			dummyItem();
		}
	}

	// calculates the total
	// TODO add total to register object
	public void total() {
		// for loop to iterate through price column
		for (int i = 0; i < index - 1; i++) {
			// gets price value
			price = (String) priceCOL.getCellData(i);
			// seperates value from $ sign
			if (price.contains("$")) {
				price = price.replace("$", "");
			}
			// adds to total
			total += Double.parseDouble(price);
		}

		// a divider from the rest of the menu items
		MenuItem itemspacer = new MenuItem("", "---------------", "");
		data.add(itemspacer);

		// sub total of all items, tax not included
		MenuItem tot = new MenuItem("", "Sub Total", "$" + df.format(total));
		data.add(tot);

		// sales tax on the total (4%)
		MenuItem tax = new MenuItem("", "Tax", "$" + df.format(total * .04));
		data.add(tax);

		// a divider in price column
		MenuItem pricespacer = new MenuItem("", "", "--------");
		data.add(pricespacer);

		// grand total with tax included
		MenuItem gtot = new MenuItem("", "Total", "$" + df.format(total * 1.04));
		data.add(gtot);

		// table update
		table.setItems(data);

		// adds to register
		register.setTotal(register.getTotal() + (total *1.04));
	}

	// saves the employees list and the employeeslist list or obs list
	public void save() {
		try {

			FileOutputStream fos = new FileOutputStream("register.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(register);
			oos.close();

		} catch (Exception e) {
			System.out.println("Error saving");
		}
	}

	// getters and setters

	public HomeController getHc() {
		return hc;
	}

	public void setHc(HomeController hc) {
		this.hc = hc;
	}
}
