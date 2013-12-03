package br.feevale.nasa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import br.feevale.nasa.task.RetrieveNasaRssTask;

public class Main extends Activity {

	private Handler handler;
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		this.handler = new Handler();
		
		refresh(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void refresh(View view) {
		ProgressDialog dialog = ProgressDialog.show(this, "Carregando", "Carregando a Imagem do dia.");
		
		RetrieveNasaRssTask task = new RetrieveNasaRssTask(this, handler, dialog);
		task.execute("http://www.nasa.gov/rss/dyn/image_of_the_day.rss");
	}
	
	public void changeWallpaper(View view) {
		try {
			ImageView imageView = (ImageView) findViewById(R.id.image);
			
			Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
			
			WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
			wallpaperManager.setBitmap(image);
			
			Toast toast = Toast.makeText(this, "Wallpaper alterado!", Toast.LENGTH_SHORT);
			toast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
