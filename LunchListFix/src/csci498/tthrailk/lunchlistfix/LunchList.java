package csci498.tthrailk.lunchlistfix;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LunchList extends Activity {
	
	Restaurant r = new Restaurant();
	RadioGroup types;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        types = (RadioGroup) findViewById(R.id.types);
        addRadioButtonListeners();
        
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(onSave);
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
    	 
    	types.check(ecButton1.getId());
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
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
		}
	};
}
