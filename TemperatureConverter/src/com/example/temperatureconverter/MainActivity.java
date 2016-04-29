package com.example.temperatureconverter;

import com.example.temperatureconverter.R;
 
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
 
public class MainActivity extends ActionBarActivity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        final EditText cel = (EditText) findViewById(R.id.editCel);
        final EditText fer = (EditText) findViewById(R.id.editFer);
        Button buttonconvert = (Button) findViewById(R.id.buttonConvert);
 
        buttonconvert.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
 
                Double c = Double.valueOf(cel.getText().toString());
                Double f = (c - 32) * 5 / 9;
                fer.setText(String.valueOf(f));
 
                // t °C = (t °F - 32) × 5/9
            }
        });
 
    }
 
}