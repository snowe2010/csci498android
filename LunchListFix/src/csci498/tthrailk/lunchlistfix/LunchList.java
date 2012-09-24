package csci498.tthrailk.lunchlistfix;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class LunchList extends ListActivity {
	
	Cursor restaurantList;
	RestaurantAdapter adapter;
	//AutoCompleteTextView oRestaurantsAddresses;
	RadioGroup types;
	EditText name;
	EditText address;
	EditText notes;
	RestaurantHelper helper;
	public final static String ID_EXTRA = "csci498.tthrailk._ID";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        createListView();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	helper.close();
    }
    
    private void createListView() {
    	helper 			= new RestaurantHelper(this);
    	restaurantList 	= helper.getAll();
    	startManagingCursor(restaurantList);
    	adapter 		= new RestaurantAdapter(restaurantList);
    	setListAdapter(adapter);
    }
    
    @Override
	public void onListItemClick(ListView parent, View view, int position, long id) {
				Intent i = new Intent(LunchList.this, DetailForm.class);
				i.putExtra(ID_EXTRA, String.valueOf(id));
				startActivity(i);
	}

	class RestaurantAdapter extends CursorAdapter {
		
		RestaurantAdapter(Cursor c) {
			super(LunchList.this, c);
		}
		
		@Override
		public void bindView(View row, Context context, Cursor c) {
			RestaurantHolder holder = (RestaurantHolder) row.getTag();
		}
		
		@Override 
		public View newView(Context context, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
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
}
	
