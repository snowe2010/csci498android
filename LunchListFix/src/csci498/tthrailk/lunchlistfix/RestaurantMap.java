package csci498.tthrailk.lunchlistfix;

import com.google.android.maps.MapActivity;

public class RestaurantMap extends MapActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
