package application;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Checker {
	
	Connect con = new Connect();
	ArrayList<appuser> user = new ArrayList<>();
	ArrayList<books> booklist = new ArrayList<>();
	
	private String username;
	private int j;
	
	public String getusername() {
		return user.get(j).getUsername();
	}
	
	
	public int checkadminrole() {
//		System.out.println(username);
		if(user.get(j).getRole()==1) return 1;
		else if(user.get(j).getRole()==2) return 2;
		return 0;
	}
	
	public boolean CheckLogin(String usern, String pass) {
		getDataFromDatabaseAppUser();
		boolean cek = false;
		if(!usern.isBlank() && !pass.isBlank()) {
			if(!user.isEmpty()) {
				for(j=0; j<user.size(); j++) {
					if(usern.equalsIgnoreCase(user.get(j).getUsername())) {
						if(keamanan(pass).equals(user.get(j).getPassword())) {
							cek = true;
							this.username = user.get(j).getUsername();
							break;
						}
					}
				}
			}
		}
		return cek;
	}
	
	public boolean CheckRegister(String usern, String pass) {
		if(!usern.isBlank() && !pass.isBlank()) {
			return getDataFromDatabaseAppUserTemp(usern);
		}
		return false;
	}
	
	public void insert(String uname, String pass) {
		String passhash = keamanan(pass);
		String query = "INSERT INTO `appuser`(`username`, `password`, `role_id`) VALUES ('" + uname + "','" + passhash +"', '1')";
		con.executeUpdate(query);
	}
	
	private boolean getDataFromDatabaseAppUserTemp(String username) {
		String query = "SELECT count(*) as count FROM appuser where username = '"+username+"';";
		ResultSet rs = con.execQuery(query);
		try {
			while(rs.next()) {
				int count = rs.getInt("count");
				if(count > 0) {
					return false;
				}else {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private void getDataFromDatabaseAppUser() {
		user.clear();
		String query = "SELECT * FROM appuser";
		ResultSet rs = con.execQuery(query);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int role_id = rs.getInt("role_id");
				
				appuser user1 = new appuser(id, username, password, role_id);
				
				user.add(user1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String keamanan(String pass){
		try {
			MessageDigest digs = MessageDigest.getInstance("MD5");
			
			digs.update(new String(pass).getBytes("UTF8"));
			
			String str = new String(digs.digest());
			return str;
		}  catch (Exception ex) {
			// TODO Auto-generated catch block
			return "";
		}
	}
	
	public boolean addnewbook(String title, String author, int year, int price) {
			String query = "INSERT INTO `books`(`title`, `author`, `year`, `harga`) VALUES ('" + title + "','" + author +"', '" + year +"','" + price +"')";
			con.executeUpdate(query);
			
			query = "INSERT INTO `status`(`status`) VALUES ('Available')";
			con.executeUpdate(query);
			
			return true;
	}
	
	public boolean rent(int bookid, String payment, String phone, String uname){
		getDataFromDatabaseBooks();
		int k=0;
		String title = "";
		int price = 0;
//		//get price
		for(k=0; k<booklist.size(); k++) {
			if(bookid == booklist.get(k).getIdviewadmin()) {
				title = booklist.get(k).getTitleviewadmin();
				price = booklist.get(k).getPriceviewadmin();
				break;
			}
		}
		//get title
		String query = "INSERT INTO `history`(`username`, `book_id`, `book_title`, `harga`, `phone`, `payment`, `date_rent`) VALUES ('" + uname + "','" + bookid +"', '" + title +"','" + price +"','"+ phone +"','"+ payment +"', NOW())";
		con.executeUpdate(query);
		
		query = "UPDATE `status` SET `status`='Rent' WHERE `id` = '"+ bookid +"'";
		con.executeUpdate(query);
		return true;
	}
	
	private void getDataFromDatabaseBooks() {
		user.clear();
		String query = "SELECT * FROM books";
		ResultSet rs = con.execQuery(query);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				int year = rs.getInt("year");
				int price = rs.getInt("harga");
				
				books user1 = new books(id, title, author, year, price);
				
				booklist.add(user1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public boolean updatereturn(int idtx, int idbook) {
		String query = "UPDATE `history` SET `date_return`= NOW() WHERE `id` = '"+ idtx +"'";
		con.executeUpdate(query);
		
		query = "UPDATE `status` SET `status`='Available' WHERE `id` = '"+ idbook +"'";
		con.executeUpdate(query);
		return true;
	}
}
