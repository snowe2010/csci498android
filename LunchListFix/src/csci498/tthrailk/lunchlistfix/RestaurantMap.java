package csci498.tthrailk.lunchlistfix;

import com.google.android.maps.MapActivity;

public class RestaurantMap extends MapActivity {
	public static final String EXTRA_LATITUDE = "csci498.tthrailk.EXTRA_LATITUDE";
	public static final String EXTRA_LONGITUDE = "csci498.tthrailk.EXTRA_LONGITUDE";
	public static final String EXTRA_NAME = "csci498.tthrailk.EXTRA_NAME";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		double lat = getIntent().getDoubleExtra(EXTRA_LATITUDE, 0);
		double lon = getIntent().getDoubleExtra(EXTRA_LONGITUDE, 0);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
