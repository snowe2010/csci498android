package csci498.tthrailk.lunchlistfix;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class FeedService extends IntentService {

	public static final String EXTRA_URL = "csci498.tthrailk.EXTRA_URL";
	
	public FeedService() {
		super("FeedService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		RSSReader reader=new RSSReader();
		  
		  try {
		    RSSFeed result=reader.load(arg0.getStringExtra(EXTRA_URL));
		  }
		  catch (Exception e) {
		    Log.e("LunchList", "Exception parsing feed", e);
		  }
	}

}
