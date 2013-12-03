package br.feevale.nasa.task;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import br.feevale.nasa.R;
import br.feevale.nasa.model.NasaRss;
import br.feevale.nasa.parser.NasaHttpRssParser;
import br.feevale.nasa.parser.NasaRssParser;

public class RetrieveNasaRssTask extends AsyncTask<String, Void, NasaRss> {

	private Handler activityHandler;
	private Activity activity;
	
	private Dialog dialog;
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
	
	public RetrieveNasaRssTask(Activity activity, Handler activityHandler, Dialog dialog) {
		this.activityHandler = activityHandler;
		this.activity = activity;
		
		this.dialog = dialog;
	}
	
	@Override
	protected NasaRss doInBackground(String... params) {
		NasaRssParser rssParser = new NasaHttpRssParser(params[0]);
		rssParser.parse();
		
		NasaRss rss = rssParser.getRss();
		activityHandler.post(new UpdateActivityProcess(rss));
		
		return rss;
	}
	
	class UpdateActivityProcess implements Runnable {

		private NasaRss rss;
		
		public UpdateActivityProcess(NasaRss rss) {
			this.rss = rss;
		}
		
		@Override
		public void run() {
			ImageView image = (ImageView) activity.findViewById(R.id.image);
			image.setImageBitmap(rss.getImage());
			
			TextView title = (TextView) activity.findViewById(R.id.title);
			title.setText(rss.getTitle());
			
			TextView description = (TextView) activity.findViewById(R.id.description);
			description.setText(rss.getDescription());
			
			TextView date = (TextView) activity.findViewById(R.id.date);
			date.setText(dateFormatter.format(rss.getDate()));
			
			dialog.dismiss();
		}
		
	}

}
