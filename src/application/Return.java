package application;

public class Return {
	private int id;
	private int book_id;
	private String title;
	private int price;
	private String payment;
	private String date;
	
	public Return(int id, int book_id, String title, int price, String payment, String date) {
		this.id = id;
		this.book_id = book_id;
		this.title = title;
		this.price = price;
		this.payment = payment;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
