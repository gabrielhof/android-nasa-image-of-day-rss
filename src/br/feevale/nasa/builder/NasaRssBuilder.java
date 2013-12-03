package br.feevale.nasa.builder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import br.feevale.nasa.model.NasaRss;

public class NasaRssBuilder {

	private String title;
	private String description;
	private Date date;
	private Bitmap image;
	
	public NasaRssBuilder title(String title) {
		this.title = title;
		return this;
	}
	
	public NasaRssBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public NasaRssBuilder date(Date date) {
		this.date = date;
		return this;
	}
	
	public NasaRssBuilder date(String date, String format) {
		try {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.ENGLISH);
			return date(dateFormatter.parse(date));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public NasaRssBuilder image(Bitmap image) {
		this.image = image;
		return this;
	}
	
	public NasaRssBuilder image(String imageUrl) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
			connection.setDoInput(true);
			connection.connect();
			
			InputStream input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			
			input.close();
			
			return image(bitmap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public NasaRss build() {
		NasaRss rss = new NasaRss();
		rss.setTitle(this.title);
		rss.setDescription(this.description);
		rss.setDate(this.date);
		rss.setImage(this.image);
		
		return rss;
	}
	
}
