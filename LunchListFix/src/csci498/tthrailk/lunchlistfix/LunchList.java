package csci498.tthrailk.lunchlistfix;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LunchList extends Activity {
	
	List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	ArrayAdapter<Restaurant> adapter = null;
	RadioGroup types;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        types = (RadioGroup) findViewById(R.id.types);
        addRadioButtonListeners();
        
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(onSave);
        
        ListView list = (ListView) findViewById(R.id.restaurants);
        adapter = new ArrayAdapter<Restaurant>(this, android.R.layout.simple_list_item_1, restaurantList);
        list.setAdapter(adapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void addRadioButtonListeners() {
//    	RadioGroup extraCreditGroup = (RadioGroup) findViewById(R.id.types);
    	 
    	RadioButton ecButton1 = new RadioButton(this);
    	ecButton1.setText(R.string.ec_radio_button_1);
    	types.addView(ecButton1);
    	 
    	RadioButton ecButton2 = new RadioButton(this);
    	ecButton2.setText(R.string.ec_radio_button_2);
    	types.addView(ecButton2);
    	 
    	RadioButton ecButton3 = new RadioButton(this);
    	ecButton3.setText(R.string.ec_radio_button_3);
    	types.addView(ecButton3);
    	
/*    	RadioButton addB1 = new RadioButton(this);
    	RadioButton addB2 = new RadioButton(this);
    	RadioButton addB3 = new RadioButton(this);
    	RadioButton addB4 = new RadioButton(this);
    	RadioButton addB5 = new RadioButton(this);
    	RadioButton addB6 = new RadioButton(this);

    	addB1.setText("extra");
    	addB2.setText("extra2");
    	addB3.setText("extra3");
    	addB4.setText("extra4");
    	addB5.setText("extra5");
    	addB6.setText("extra6");
    	
    	types.addView(addB1);
    	types.addView(addB2);
    	types.addView(addB3);
    	types.addView(addB4);
    	types.addView(addB5);
    	types.addView(addB6);
    	*/
    	types.check(ecButton1.getId());
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Restaurant r = new Restaurant();
			EditText name = (EditText) findViewById(R.id.name);
			EditText address = (EditText) findViewById(R.id.addr);
			
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			switch (types.getCheckedRadioButtonId()) {
				case R.id.sit_down:
					r.setType("sit_down");
					break;
				case R.id.take_out:
					r.setType("take_out");
					break;
				case R.id.delivery:
					r.setType("delivery");
					break;
			}
			
			adapter.add(r);
			
		}
	};
}
