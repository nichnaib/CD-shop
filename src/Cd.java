
class Cd {

	private String title, titleSongs, photo, price, date, band, description, genre, musicians, numberSongs, cd_id;

	public Cd(String title, String titleSongs, String photo, String price, String date, String band, String description, String genre, String musicians, String numberSongs, String cd_id){
		this.title = title;
		this.titleSongs = titleSongs;
		this.photo=photo;
		this.price = price;
		this.date = date;
		this.band = band;
		this.description = description;
		this.genre = genre;
		this.musicians = musicians;
		this.numberSongs = numberSongs;
		this.cd_id = cd_id;

	}
	
	public String getTitle(){
		return title;
	}
	public String getTitleSongs(){
		return titleSongs;
	}
	public String getPhoto(){
		return photo;
	}
	public String getPrice(){
		return price;
	}
	public String getDate(){
		return date;
	}
	public String getBand(){
		return band;
	}
	public String getDescription(){
		return description;
	}
	public String getGenre(){
		return genre;
	}
	public String getMusicians(){
		return musicians;
	}
	public String getNumberSongs(){
		return numberSongs;
	}
	public String getCdId(){
		return cd_id;
	}
}
