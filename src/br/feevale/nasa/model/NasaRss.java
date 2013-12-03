package br.feevale.nasa.model;

import java.util.Date;

import android.graphics.Bitmap;

public class NasaRss {

	private String title;
	private String description;
	private Date date;
	private Bitmap image;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Bitmap getImage() {
		return image;
	}
	
	public void setImage(Bitmap image) {
		this.image = image;
	}
}