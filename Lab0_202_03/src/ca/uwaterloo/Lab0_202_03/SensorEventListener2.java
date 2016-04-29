package ca.uwaterloo.Lab0_202_03;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import ca.uwaterloo.sensortoy.LineGraphView;

public class SensorEventListener2 implements SensorEventListener{
	TextView mTv;
	TextView abs1;
	LineGraphView graph1;
	float[] testing = {0,0,0};
	
		public SensorEventListener2(TextView tv, TextView abs){
			mTv = tv;
			abs1 = abs;
			
		}
		
		public SensorEventListener2(TextView tv, TextView abs, LineGraphView graph){
			mTv = tv;
			abs1 = abs;
			graph1 = graph;
		}
		@Override
		public void onAccuracyChanged(Sensor s, int i) {
			

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_LIGHT){
				
				mTv.setText(String.format("%.2f", event.values[0]));
				
				if ( Math.abs(event.values[0]) >= testing[0] ){
					testing[0] = event.values[0];
					abs1.setText(String.format("%.2f", testing[0]));
				}
				
			}
			else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER || event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD
					|| event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
				
				mTv.setText(String.format("(%.2f, %.2f, %.2f)", event.values[0], event.values[1], event.values[2] ));
				
				if (Math.abs(event.values[0]) >= testing[0]){
					testing[0] = Math.abs(event.values[0]);
					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
				}
				else if(Math.abs(event.values[1]) >= testing[1]){
					testing[1] = Math.abs(event.values[1]);
					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
				}
				else if(Math.abs(event.values[2]) >= testing[2]){
					testing[2] = Math.abs(event.values[2]);
					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
				}
			}
			
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				graph1.addPoint(event.values);
			}
//			else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
//				
//				mTv.setText(String.format("(%.2f, %.2f, %.2f)", event.values[0], event.values[1], event.values[2] ));
//				
//				if (Math.abs(event.values[0]) >= Math.abs(testing[0])){
//					testing[0] = event.values[0];
//					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
//				}
//				else if(Math.abs(event.values[1]) >= Math.abs(testing[1])){
//					testing[1] = event.values[1];
//					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
//				}
//				else if(Math.abs(event.values[2]) >= Math.abs(testing[2])){
//					testing[2] = event.values[2];
//					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
//				}
//			}
//			else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
//				
//				mTv.setText(String.format("(%.2f, %.2f, %.2f)", event.values[0], event.values[1], event.values[2] ));
//				
//				if (Math.abs(event.values[0]) >= Math.abs(testing[0])){
//					testing[0] = event.values[0];
//					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
//				}
//				else if(Math.abs(event.values[1]) >= Math.abs(testing[1])){
//					testing[1] = event.values[1];
//					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
//				}
//				else if(Math.abs(event.values[2]) >= Math.abs(testing[2])){
//					testing[2] = event.values[2];
//					abs1.setText(String.format("(%.2f, %.2f, %.2f)", testing[0], testing[1], testing[2] ));
//				}
//			}
		}

	}