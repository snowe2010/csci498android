package csci498.tthrailk.lunchlistfix;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class RestaurantOverlay extends ItemizedOverlay<OverlayItem> {

	private OverlayItem item = null;

	public RestaurantOverlay(Drawable marker, GeoPoint point, String name) {
		super(marker);
		boundCenterBottom(marker);
		item = new OverlayItem(point, name, name);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return item;
	}

	@Override
	public int size() {
		return 1;
	}

}
