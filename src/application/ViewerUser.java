package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewerUser implements Initializable{
	
	Connect con = new Connect();
	
	private Stage stage =  null;
	private Scene scene = null;
	private Parent root  = null;
	
	@FXML
    private TableView<books> tableuser;
    @FXML
    private TableColumn<books, Integer> idviewuser;
    @FXML
    private TableColumn<books, String> titleviewuser;
	@FXML
    private TableColumn<books, String> authorviewuser;
	@FXML
    private TableColumn<books, Integer> yearviewuser;
    @FXML
    private TableColumn<books, Integer> priceviewuser;
    
    
    ObservableList<books> listTable = FXCollections.observableArrayList();
    
    ArrayList<Status> stat = new ArrayList<>();
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		idviewuser.setCellValueFactory(cellData -> new SimpleIntegerProperty( cellData.getValue().getIdviewadmin()).asObject());
		titleviewuser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitleviewadmin()));
		authorviewuser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthorviewadmin()));
		yearviewuser.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYearviewadmin()).asObject());
		priceviewuser.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPriceviewadmin()).asObject());
		
		try {
			getDataFromDatabaseStatus();
			listTable.clear();
			String query = "SELECT * FROM `books`";
			ResultSet rs = con.execQuery(query);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				int year = rs.getInt("year");
				int price = rs.getInt("harga");
				
				for(int i = 0; i<stat.size(); i++) {
					if(id == stat.get(i).getId()) {
						listTable.add(new books(id, title, author, year, price));
						break;
					}
				}
				
				
				
			}
			tableuser.setItems(listTable);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//get Data Status
	private void getDataFromDatabaseStatus() {
		stat.clear();
		String query = "SELECT * FROM status";
		ResultSet rs = con.execQuery(query);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String status = rs.getString("status");
				
				if(status.equals("Available")) {
					Status user1 = new Status(id, status);
					stat.add(user1);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
    private Button cancelviewuser;
	
	@FXML
	private Button cancelrentuser;
	
	@FXML
	public void switchUserMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	@FXML
	private Button rentUser;
	
	
	
	
	@FXML
	private TextField bookidrent;
	@FXML
	private TextField pnumberrent;
	@FXML
	private RadioButton paymentdana;
	@FXML
	private RadioButton paymentovo;
	@FXML
	private RadioButton paymentgopay;
	@FXML
	private RadioButton paymentlinkaja;
	
	@FXML
	public void switchRentBook(ActionEvent event) throws IOException {
		String payment = "";
		if(paymentdana.isSelected()) {
			payment = "Dana";
		}
		else if(paymentovo.isSelected()) {
			payment = "OVO";
		}
		else if(paymentgopay.isSelected()) {
			payment = "GOPAY";
		}
		else if(paymentlinkaja.isSelected()) {
			payment = "LinkAja";
		}
		String id = bookidrent.getText();
		String phone = pnumberrent.getText();
		
		if(id.isBlank() || phone.isBlank()) {
			bookidrent.setText("");
			pnumberrent.setText("");
		}
		
		if(!id.matches("[0-9]*") || !phone.matches("[0-9]*")) {
			bookidrent.setText("");
			pnumberrent.setText("");
		}
		
		int idnew = Integer.parseInt(id);
		
		boolean cek = false;
		getDataFromDatabaseStatus();
		for(int i =0; i<stat.size(); i++) {
			cek = false;
			if(idnew == stat.get(i).getId()) {
				cek = true;
				break;
			}
		}
		Controller conn = new Controller();
		
		boolean cek1 = false;
		
		if(cek) {
			cek1 = conn.updaterent(idnew, payment, phone);
			if(cek1) {
				//Disni Rent Book
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("BookWay");
				alert.setContentText("RENT BOOK SUCCESS!\nPayment Method: " + payment);
				alert.setHeaderText("RENT BOOK SUCCESS!");
				alert.showAndWait();
				
				root = FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
				stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();	
			}
			else {
				System.out.println("Gagal!");
			}
		}
		else {
			bookidrent.setText("");
			pnumberrent.setText("");
		}

	}
	
}
    

	
	

