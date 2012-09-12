package csci498.tthrailk.lunchlistfix;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class LunchList extends TabActivity {
	
	List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	RestaurantAdapter adapter 		= null;
	//AutoCompleteTextView oRestaurantsAddresses;
	RadioGroup types	= null;
	EditText name 		= null;
	EditText address	= null;
	EditText notes 		= null;
	Restaurant current	= null;
	AlertDialog mDialog = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        types 		= (RadioGroup) 	findViewById(R.id.types);
        name		= (EditText) 	findViewById(R.id.name);
        address		= (EditText) 	findViewById(R.id.addr);
        notes 		= (EditText) 	findViewById(R.id.notes);
        Button save = (Button) 		findViewById(R.id.save);
        
        save.setOnClickListener(onSave);
        
        createListView();
        createTabs();
    }
    
    private void createListView() {
    	ListView list 	= (ListView) findViewById(R.id.restaurants);
    	adapter 		= new RestaurantAdapter();
    	list.setAdapter(adapter);
    	list.setOnItemClickListener(onListClick);
    }
    
    private void createTabs() {
    	TabHost.TabSpec tSpec = getTabHost().newTabSpec("tag1");
    	tSpec.setContent(R.id.restaurants);
    	tSpec.setIndicator("List", getResources().getDrawable(R.drawable.list));
    	getTabHost().addTab(tSpec);
    	
    	tSpec = getTabHost().newTabSpec("tag2");
    	tSpec.setContent(R.id.details);
    	tSpec.setIndicator("Details", getResources().getDrawable(R.drawable.restaurant));
    	
    	getTabHost().addTab(tSpec);
    	getTabHost().setCurrentTab(1);
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			current 		= new Restaurant();
			current.setName(name.getText().toString());
			current.setAddress(address.getText().toString());
			current.setNotes(notes.getText().toString());
			
			switch (types.getCheckedRadioButtonId()) {
				case R.id.sit_down:
					current.setType("sit_down");
					break;
				case R.id.take_out:
					current.setType("take_out");
					break;
				case R.id.delivery:
					current.setType("delivery");
					break;
			}
			
			adapter.add(current);
		}
	};

	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			current = restaurantList.get(position);
			name.setText(current.getName());
			address.setText(current.getAddress());
			notes.setText(current.getNotes());
			
			if (current.getType().equals("sit_down")) {
				types.check(R.id.sit_down);
			}
			else if (current.getType().equals("take_out")) {
				types.check(R.id.take_out);
			}
			else {
				types.check(R.id.delivery);
			}
			getTabHost().setCurrentTab(1);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
 		new MenuInflater(this).inflate(R.menu.option, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	  if (item.getItemId() == R.id.toast) {
	    String message="No restaurant selected";
	    
	    if (current != null) {
	      message = current.getNotes();
	    }
	    
	    //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	    initiateTwoButtonAlert(message, "Ok");
	    return true;
	  }
	  
	  return super.onOptionsItemSelected(item);
	}
	
	class RestaurantAdapter extends ArrayAdapter<Restaurant> {
	RestaurantAdapter() {
			super(LunchList.this, R.layout.row, restaurantList);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View row 				= convertView;
			RestaurantHolder holder = null;
			
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
				holder = new RestaurantHolder(row);
				row.setTag(holder);
			}
			else {
				holder = (RestaurantHolder) row.getTag();
			}
			
			holder.populateFrom(restaurantList.get(position));
			
			return(row);
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
		
		void populateFrom(Restaurant r) {
			name.setText(r.getName());
			address.setText(r.getAddress());
			
			if (r.getType().equals("sit_down")) {
				icon.setImageResource(R.drawable.ball_red);
				name.setBackgroundColor(color.darker_gray);
				if (name.getText().toString().contains("a")) {
					name.setAllCaps(true);
				}
			}
			else if (r.getType().equals("take_out")) {
				icon.setImageResource(R.drawable.ball_yellow);
				name.setTextColor(Color.GREEN);
			}
			else {
				icon.setImageResource(R.drawable.ball_green);
				if (address.getText().toString().contains("666")) {
					name.setTextColor(Color.RED);
					address.setTextColor(Color.RED);
				}
			}
		}
	}
	
	private void initiateTwoButtonAlert(String displayText, String positiveButtonText) {
	     mDialog = new AlertDialog.Builder(this)
	             .setTitle(getResources().getString(R.string.app_name))
	             .setMessage(displayText)
	             .setIcon(R.drawable.toast)
	             .setPositiveButton(positiveButtonText, null)
	             .show();
	 
	     WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
	     layoutParams.dimAmount = 0.9f;
	     mDialog.getWindow().setAttributes(layoutParams);
	     mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
	 }
	
}
	
