package com.mines.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {
  Button button;
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    button=new Button(this);
    
    button.setOnClickListener(this);
    updateTime();
    setContentView(button);
  }
  public void onClick(View view) {
    updateTime();
  }
  private void updateTime() {
    button.setText(new Date().toString());
  }
}
