package application;

import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {
	private Checker checker = new Checker();
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button closebutton;
	
	@FXML
	private Button LoginButton;
	@FXML
	private TextField userlogin;
	@FXML
	private PasswordField passlogin;
	@FXML
	private Label labelinvalid;
	
	@FXML
	private TextField userregis;
	@FXML
	private PasswordField passregis;
	@FXML
	private Label labelinvalidregis;
	
	@FXML
	private Button logoutuser;
	@FXML
	private Label labelnameuser;
	@FXML
	private Button rentbookButton;
	@FXML
	private Button returnbookButton;
	@FXML
	private Button viewbookButton;
	
	@FXML
	private Button logoutadmin;
	@FXML
	private Button viewbookButtonAdmin;
	@FXML
	private Button addbookButton;
	
	
	@FXML
	private TextField titleText;
	@FXML
	private TextField authorText;
	@FXML
	private TextField yearText;
	@FXML
	private TextField priceText;
	@FXML
	private Button AddButton;
	@FXML
	private Button cancelAdmin;
	@FXML
	private Label labeltulisan;
	
	@FXML
	private Label labeladdadmin;
	
	
	private String UsernameLogin;
	private String PasswordLogin;
	private String UsernameRegis;
	private String PasswordRegis;
	
	
	
	
	public void exit() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("BookWay");
		alert.setContentText("WARNING!");
		alert.setHeaderText("Close The Application");
		alert.showAndWait();
		
		System.exit(0);
		
	}
	
	public void switchSceneRegis(ActionEvent event)throws IOException {
		root = FXMLLoader.load(getClass().getResource("Registrasi.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchSceneLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchSceneMain(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	public void switchSceneLoginfromRegis(ActionEvent event) throws IOException {
		boolean cek;
		this.UsernameRegis = userregis.getText();
		PasswordRegis = passregis.getText();
		
		cek = checker.CheckRegister(UsernameRegis, PasswordRegis);
		if(!cek || passregis.getLength()<8) {
			userregis.setText("");
			passregis.setText("");
			labelinvalidregis.setText("Your username already exists, please use another username");
		}
		else if(cek) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("BookWay");
			alert.setContentText("Username: " + UsernameRegis);
			alert.setHeaderText("Register Success!");
			alert.showAndWait();
			
			checker.insert(UsernameRegis, PasswordRegis);
			root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();		
		}
		
	}
	
	public void LogintoMenu(ActionEvent event) throws IOException {
		boolean cek1 = false;
			
		UsernameLogin = userlogin.getText();
		PasswordLogin = passlogin.getText();
		cek1 = checker.CheckLogin(UsernameLogin, PasswordLogin);
		
		String uname = checker.getusername();
		
		Connect con = new Connect();
		
		int a=0;
		String query = "SELECT * FROM tmp";
		ResultSet rs = con.execQuery(query);
		try {
			while(rs.next()) {
				a++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(a==0) {
			query = "INSERT INTO `tmp`(`name`) VALUES ('" + uname + "')";
			con.executeUpdate(query);
		}
		else {
			query = "DELETE FROM `tmp`";
			con.executeUpdate(query);
			query = "INSERT INTO `tmp`(`name`) VALUES ('" + uname + "')";
			con.executeUpdate(query);
		}
		
		if(!cek1) {
			userlogin.setText("");
			passlogin.setText("");
			labelinvalid.setText("Username or Password Invalid!");
		}
		else if(cek1){		
			int id= checker.checkadminrole();
			if(id == 1) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("BookWay");
				alert.setContentText("Username: " + uname);
				alert.setHeaderText("Login USER Success!");
				alert.showAndWait();
				
				Parent root = FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
				stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();	
			}
			else if(id == 2) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("BookWay");
				alert.setContentText("Login ADMIN Success!");
				alert.setHeaderText("LOGIN INFORMATION");
				alert.showAndWait();
				
				Parent root = FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"));
				stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			
		}
		
	}
	
	public void switchViewBooksUser(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ViewBook.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}
	
	public void switchViewBooksAdmin(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ViewBookAdmin.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}
	
	public void cancelViewUser(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void cancelViewAdmin(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchReturnBook(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("ReturnBook.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}
	
	public void switchAddBook(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}
	
	public void switchRentBook(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("RentBook.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}	
	
	public void AddNewBook(ActionEvent event) throws IOException {
		boolean cek = false;
		String title = titleText.getText();
		String author = authorText.getText();
		String year = yearText.getText();
		String price = priceText.getText();
		
		if(title.isBlank() || author.isBlank() || author.isBlank() || price.isBlank()){
			titleText.setText("");
			authorText.setText("");
			yearText.setText("");
			priceText.setText("");
		}
			
				
					
		if(!year.matches("[0-9]*") || !price.matches("[0-9]*")) {
			titleText.setText("");
			authorText.setText("");
			yearText.setText("");
			priceText.setText("");
		}
		else {
			int year1 = Integer.valueOf(year);
			int price1 = Integer.valueOf(price);
			cek = checker.addnewbook(title, author, year1, price1);
		}
		
		
		if(cek) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("BookWay");
			alert.setContentText("Title: " + title +"\nAuthor: " + author + "\nYear " + year + "\nPrice: Rp" + price);
			alert.setHeaderText("ADD BOOK SUCCESS!");
			alert.showAndWait();
			
			Parent root = FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"));
			stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			stage.show();		
		}
		
	}
	
	public void switchAdminMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	
	public boolean updaterent(int bookid, String payment, String phone) {
//		System.out.println(tmp.getName());
		String uname = null;
		Connect con = new Connect();
		String query = "SELECT * FROM tmp";
		ResultSet rs = con.execQuery(query);
		try {
			while(rs.next()) {
				uname = rs.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checker.rent(bookid, payment, phone, uname);
		return true;
	}


	
}
