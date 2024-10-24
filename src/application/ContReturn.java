package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContReturn implements Initializable{
	private Stage stage =  null;
	private Scene scene = null;
	private Parent root  = null;
	
	ArrayList<Return> rtn = new ArrayList<>();
	
	@FXML
	private Button returnbookbutton;
	@FXML
	private Button backbuttonreturn;
	@FXML
	private TextField idreturnbook;
	
	public void backtoMenuUser(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();			
	}
	
	Checker ceker = new Checker();
	
	public void ReturnBook(ActionEvent event) throws IOException {
		String cekid = idreturnbook.getText();
		int bookid=0;
		boolean cek = false;
		int newcekid= Integer.parseInt(cekid);
		if(!cekid.isBlank() || cekid.matches("[0-9]*")) {
			for(int q=0; q<rtn.size(); q++) {
				if(newcekid == rtn.get(q).getId()){
					cek = true;
					bookid = rtn.get(q).getBook_id();
					break;
				}
			}
		}
		
		if(cek) {
			boolean cek111 = false;
			cek111 = ceker.updatereturn(newcekid, bookid);
			if(cek111) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("BookWay");
				alert.setContentText("RETURN BOOK SUCCESS!");
				alert.setHeaderText("RETURN BOOK SUCCESS!");
				alert.showAndWait();
				
				root = FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
				stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();	
			}
			else {
				System.exit(0);
			}
		}
		else if(!cek) {
			idreturnbook.setText("");
		}
	}
	
	@FXML
	private TableView<Return> tablereturnbook;
	@FXML
    private TableColumn<Return, Integer> txidrt;
	@FXML
    private TableColumn<Return, Integer> bookidrt;
	@FXML
    private TableColumn<Return, String> booktitlert;
	@FXML
    private TableColumn<Return, Integer> pricert;
	@FXML
    private TableColumn<Return, String> paymentrt;
	@FXML
    private TableColumn<Return, String> datert;
	
	
	Connect con = new Connect();
	
	private String getUname() {
		
		String uname=null;
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
		return uname;
	}
	
	ObservableList<Return> listTable = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		txidrt.setCellValueFactory(cellData -> new SimpleIntegerProperty( cellData.getValue().getId()).asObject());
		bookidrt.setCellValueFactory(cellData -> new SimpleIntegerProperty( cellData.getValue().getBook_id()).asObject());
		booktitlert.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getTitle()));
		pricert.setCellValueFactory(cellData -> new SimpleIntegerProperty( cellData.getValue().getPrice()).asObject());
		paymentrt.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getPayment()));
		datert.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getDate()));
		String unamee = getUname();
		try {
			listTable.clear();
			
			String query = "SELECT * FROM `history`";
			ResultSet rs = con.execQuery(query);
//			System.out.println("aman");
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				int bookid = rs.getInt("book_id");
				String booktitle = rs.getString("book_title");
				int harga = rs.getInt("harga");
				String payment = rs.getString("payment");
				Timestamp date = rs.getTimestamp("date_rent");
				Timestamp datert = rs.getTimestamp("date_return");
				
				String date1 = date.toString();
				
//				listTable.add(new Return(id, bookid, booktitle, harga, payment, date1));
			
				if(unamee.equals(username)) {
//					listTable.add(new Return(id, bookid, booktitle, harga, payment, date1));
					if(datert == null) {
						listTable.add(new Return(id, bookid, booktitle, harga, payment, date1));
						rtn.add(new Return(id, bookid, booktitle, harga, payment, date1));
					}
				}

			}
			tablereturnbook.setItems(listTable);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
