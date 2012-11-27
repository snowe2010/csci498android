package csci498.tthrailk.lunchlistfix;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LunchFragment extends ListFragment {

	public final static String ID_EXTRA = "csci498.tthrailk._ID";
	Cursor restaurantList;
	RestaurantAdapter adapter;
	RestaurantHelper helper;
	SharedPreferences prefs;
	OnRestaurantListener listener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
    	super.onResume();
    	
		helper	= new RestaurantHelper(getActivity());
    	prefs	= PreferenceManager.getDefaultSharedPreferences(getActivity());
    	initList();
    	prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }
    
    private void initList() {
    	if (restaurantList != null) {
    		restaurantList.close();
    	}

    	restaurantList = helper.getAll(prefs.getString("sort_order", "name"));
    	adapter	= new RestaurantAdapter(restaurantList);
    	setListAdapter(adapter);
    }

    @Override
    public void onPause() {
    	super.onPause();
    	helper.close();
    }

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
    	public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key) {
    		if (key.equals("sort_order")) {
    			initList();
    		}
    	}
    };
    
    @Override
    public void onListItemClick(ListView parent, View view, int position, long id) {
    	if (listener != null) {
    		listener.onRestaurantSelected(id);
    	}
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	inflater.inflate(R.menu.option, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == R.id.add) {
    		startActivity(new Intent(getActivity(), DetailForm.class));
    		return true;
    	} else if (item.getItemId() == R.id.prefs) {
    		startActivity(new Intent(getActivity(), EditPreferences.class));
    		return true;
    	} else if (item.getItemId() == R.id.help) {
    		  startActivity(new Intent(getActivity(), HelpPage.class));
    	}
    	return super.onOptionsItemSelected(item);
    }

    public void setOnRestaurantListener(OnRestaurantListener listener) {
    	this.listener = listener;
    }
    
	class RestaurantAdapter extends CursorAdapter {

		RestaurantAdapter(Cursor c) {
			super(getActivity(), c);
		}

		@Override
		public void bindView(View row, Context context, Cursor c) {
			RestaurantHolder holder = (RestaurantHolder) row.getTag();
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context context, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			RestaurantHolder holder = new RestaurantHolder(row);
			row.setTag(holder);
			return row;
		}
	}

	static class RestaurantHolder {
		private TextView name 		= null;
		private TextView address 	= null;
		private ImageView icon 		= null;

		RestaurantHolder(View row) {
			name 	=	(TextView) row.findViewById(R.id.title);
			address =	(TextView) row.findViewById(R.id.address);
			icon 	= 	(ImageView) row.findViewById(R.id.icon);
		}

		void populateFrom(Cursor c, RestaurantHelper helper) {
			name.setText(helper.getName(c));
			address.setText(helper.getAddress(c));

			if (helper.getType(c).equals("sit_down")) {
				icon.setImageResource(R.drawable.ball_red);
			}
			else if (helper.getType(c).equals("take_out")) {
				icon.setImageResource(R.drawable.ball_yellow);
			}
			else {
				icon.setImageResource(R.drawable.ball_green);
			}
		}
	}
	
	public interface OnRestaurantListener {
		void onRestaurantSelected(long id);
	}
	
}