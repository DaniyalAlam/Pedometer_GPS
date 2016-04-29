package ca.uwaterloo.lab4_202_03;

import mapper.MapView;
import mapper.NavigationalMap;
import mapper.VectorUtils;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ca.uwaterloo.sensortoy.LineGraphView;


public class AccelEventListener implements SensorEventListener{
	
	private enum Mode{WAITING, RISING, FALLING};
	
	Mode mode = Mode.WAITING;
	LineGraphView graph1;
	float[] smoothAccel = new float[3];
	float[] accel = new float[3];
	float[] magnetic = new float[3];
	float[] R = new float[9];
	float[] average = {0,0,0,0,0,0,0,0,0,0,0};
	float azimuth;
	TextView logger1;
	float stepcounterN = 0;
	float stepcounterE = 0;
	int stepcounter = 0;
	TextView counter;
	float degrees = 0;
	float angle = 0;
	TextView counterN;
	TextView counterE;
	Button button1;
	MapView mv;
	PointF point, nextpoint, refpoint;
	float xval, yval, xvald, yvald, diffx, diffy = 0;
	TextView stop;
	NavigationalMap map;
	
	
	public AccelEventListener(Button button, TextView counter, TextView counterN, TextView counterE, TextView logger, LineGraphView graph, 
			MapView mv, TextView stop, NavigationalMap map){
		graph1 = graph;
		logger1 = logger;
		this.counterN = counterN;
		this.counterE = counterE;
		button1 = button;
		this.counter = counter;
		this.mv = mv;
		this.stop = stop;
		this.map = map;
	}
	
	
	@Override
	public void onAccuracyChanged(Sensor s, int i) {
		

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			System.arraycopy(event.values, 0, accel, 0, 3);
		}
		
		if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
			System.arraycopy(event.values, 0, magnetic, 0, 3);
		}
		
		if(accel != null && magnetic !=null){
			
			if(SensorManager.getRotationMatrix(R, null, accel, magnetic)){
				float[] orientation = new float[3];
				SensorManager.getOrientation(R, orientation);
//				if (orientation[0] < 0) orientation[0] += 2*(float)Math.PI;
				
				for (int i = 0; i <= 9; i++){
					average[i] = average[i+1];
				}
				average[10] = orientation[0];
				
				float sum = 0;
				for (int i = 0; i<=10; i++){
					sum += average[i];
				}
				
				azimuth = sum/10;
				
				degrees = (float) Math.toDegrees(azimuth);
//				azimuth = Math.round(azimuth * 360 / (2 * 3.14159f));
//                if (azimuth < 0 && azimuth > -180)
//                    azimuth += 360;
				
				logger1.setText(String.format("Please match this value to the direction value below: %.2f", degrees));
			}
		
		
		point = mv.getUserPoint();	
		nextpoint = new PointF(0,0);
		refpoint = new PointF(0,0);
		xval = point.x;
		yval = point.y;
		xvald = mv.getDestinationPoint().x;
		yvald = mv.getDestinationPoint().y;
		
		
		if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
			
			//Low pass filter to smooth values
			smoothAccel[2] += (event.values[2] - smoothAccel[2])/50;
			graph1.addPoint(smoothAccel);
			
			//State Machine
			switch (mode){
			
			case WAITING: 
				if (smoothAccel[2] > 0.20){
					mode = Mode.RISING;
				}
				
				break;
				
			case RISING: 
				
				if (smoothAccel[2] < -0.30){
					mode = Mode.FALLING;
				}
				
				break;
			
			case FALLING:
				if (smoothAccel[2] > -0.10){
					
					stepcounterN += Math.cos(azimuth);
					stepcounterE += Math.sin(azimuth);
					counterN.setText(String.format("North: %f", stepcounterN));
					counterE.setText(String.format("East: %f", stepcounterE));
					
					diffx = xval - xvald;
					diffy = yval - yvald;
					
					
					
					if(Math.abs(diffx) > .25 || Math.abs(diffy) > .25){
						xval += 0.45*Math.sin(azimuth);
						yval -= 0.45*Math.cos(azimuth);
						nextpoint.set(xval,yval);
						
						if(map.calculateIntersections(point, nextpoint).size() == 0) {
							//point.set(xval, yval);
							mv.setUserPoint(nextpoint);
							refpoint.set(xval, 0);;
							pathFinder find = new pathFinder(mv.getUserPoint(), mv.getDestinationPoint(), mv, map);
							find.findSmallestPath();
							stop.setText(String.format("Directions: %.2f", Math.toDegrees(VectorUtils.angleBetween(nextpoint, refpoint, find.getPathvals().get(1)))));
						}
					}
					
					if(Math.abs(diffx) < .25 && Math.abs(diffy) < .25){
						stop.setText("STOP!!");
					}
						
					stepcounter++;
					counter.setText(String.format("Steps: %d", stepcounter));
					mode = Mode.WAITING;
					
				}
				break;
			
			default:
				break;
			}
		}
		}
		
		
		//Button to reset steps and graph
		button1.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	stepcounter = 0;
	        	counter.setText(String.format("Steps: %d", stepcounter));
	            stepcounterN = 0;
	            counterN.setText(String.format("North: %f", stepcounterN));
	            stepcounterE = 0;
	            counterE.setText(String.format("East: %f", stepcounterE));
	            graph1.purge();
	        }
		});
	}
}
