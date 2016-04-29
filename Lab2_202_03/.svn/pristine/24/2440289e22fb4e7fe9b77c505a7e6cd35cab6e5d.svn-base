package ca.uwaterloo.Lab2_202_03;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ca.uwaterloo.sensortoy.LineGraphView;


public class AccelEventListener implements SensorEventListener{
	
	private enum Mode{WAITING, RISING, FALLING};
	
	Mode mode = Mode.WAITING;
	LineGraphView graph1;
	float[] smoothAccel = {0,0,0};
	TextView logger1;
	int stepcounter = 0;
	TextView counter1;
	Button button1;
	
	
	public AccelEventListener(Button button, TextView counter, TextView logger, LineGraphView graph){
		graph1 = graph;
		logger1 = logger;
		counter1 = counter;
		button1 = button;
	}
	
	@Override
	public void onAccuracyChanged(Sensor s, int i) {
		

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		//Low pass filter to smooth values
		smoothAccel[2] += (event.values[2] - smoothAccel[2])/50;
		graph1.addPoint(smoothAccel);
		
		//Display current value of z
		logger1.setText(String.format("%.2f", smoothAccel[2]));
		
		//State Machine
		switch (mode){
		
		case WAITING: 
			if (smoothAccel[2] > 0.25){
				mode = Mode.RISING;
			}
			
			if (smoothAccel[2] > 1.6){
				mode = Mode.WAITING;
			}
			break;
			
		case RISING: 
			
			if (smoothAccel[2] < -0.30){
				mode = Mode.FALLING;
			}
			
			if (smoothAccel[2] < -2){
				mode = Mode.WAITING;
			}
			break;
		
		case FALLING:
			if (smoothAccel[2] > -0.10){
				stepcounter++;
				counter1.setText(String.format("Steps:\n%d", stepcounter));
				mode = Mode.WAITING;
			}
			break;
		
		default:
			break;
		}
		
		
		//Button to reset steps and graph
		button1.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            stepcounter = 0;
	            counter1.setText(String.format("Steps:\n%d", stepcounter));
	            graph1.purge();
	        }
		});
	}
}