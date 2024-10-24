package application;

public class books {
	private Integer idviewadmin;
	private String titleviewadmin;
	private String authorviewadmin;
	private Integer yearviewadmin;
	private Integer priceviewadmin;
	
	public books(int id, String title, String author, int year, int price) {
		this.idviewadmin = id;
		this.titleviewadmin = title;
		this.authorviewadmin = author;
		this.yearviewadmin = year;
		this.priceviewadmin = price;
	}

	public int getIdviewadmin() {
		return idviewadmin;
	}

	public void setIdviewadmin(int idviewadmin) {
		this.idviewadmin = idviewadmin;
	}

	public String getTitleviewadmin() {
		return titleviewadmin;
	}

	public void setTitleviewadmin(String titleviewadmin) {
		this.titleviewadmin = titleviewadmin;
	}

	public String getAuthorviewadmin() {
		return authorviewadmin;
	}

	public void setAuthorviewadmin(String authorviewadmin) {
		this.authorviewadmin = authorviewadmin;
	}

	public int getYearviewadmin() {
		return yearviewadmin;
	}

	public void setYearviewadmin(int yearviewadmin) {
		this.yearviewadmin = yearviewadmin;
	}

	public int getPriceviewadmin() {
		return priceviewadmin;
	}

	public void setPriceviewadmin(int priceviewadmin) {
		this.priceviewadmin = priceviewadmin;
	}

	
	
	
	
	
}
