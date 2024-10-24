package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Viewer implements Initializable{
	
	Connect con = new Connect();
	
	private Stage stage =  null;
	private Scene scene = null;
	private Parent root  = null;
	
	@FXML
    private TableView<books> tableadmin;
    @FXML
    private TableColumn<books, Integer> idviewadmin;
    @FXML
    private TableColumn<books, String> titleviewadmin;
	@FXML
    private TableColumn<books, String> authorviewadmin;
	@FXML
    private TableColumn<books, Integer> yearviewadmin;
    @FXML
    private TableColumn<books, Integer> priceviewadmin;
    
    
    ObservableList<books> listTable = FXCollections.observableArrayList();
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		idviewadmin.setCellValueFactory(cellData -> new SimpleIntegerProperty( cellData.getValue().getIdviewadmin()).asObject());
		titleviewadmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitleviewadmin()));
		authorviewadmin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthorviewadmin()));
		yearviewadmin.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYearviewadmin()).asObject());
		priceviewadmin.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPriceviewadmin()).asObject());
		
		try {
			listTable.clear();
			String query = "SELECT * FROM `books`";
			ResultSet rs = con.execQuery(query);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				int year = rs.getInt("year");
				int price = rs.getInt("harga");
				
				listTable.add(new books(id, title, author, year, price));
				
			}
			tableadmin.setItems(listTable);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@FXML
    private Button cancelviewadmin;
	
	@FXML
	public void switchAdminMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"));
		stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	
}
    

	
	

