package csci498.tthrailk.lunchlistfix;

import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

public class AppWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context ctxt, AppWidgetManager mgr, int[] appWidgetIds) {
		if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			onHCUpdate(ctxt, mgr, appWidgetIds);
		} else {
			ctxt.startService(new Intent(ctxt, WidgetService.class));
		}
	}

}