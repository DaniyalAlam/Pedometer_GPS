package ca.uwaterloo.lab1_202_03;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import ca.uwaterloo.sensortoy.LineGraphView;


public class MultiSensorEventListener implements SensorEventListener {
	
	TextView outputValue;
	TextView outputRecord;
	LineGraphView outputgraph = null;
	
	float [] recordValue = {0,0,0};
	
	public MultiSensorEventListener(TextView outputView, TextView outputRecordView){
		outputValue = outputView;
		outputRecord = outputRecordView;
	}
	
	public MultiSensorEventListener(TextView outputView, TextView outputRecordView, LineGraphView graph){
		outputValue = outputView;
		outputRecord = outputRecordView;
		outputgraph = graph;
	}
	
	public void onAccuracyChanged(Sensor s, int i) {}
	
	public void onSensorChanged(SensorEvent se) {
		
		if (se.sensor.getType() == Sensor.TYPE_LIGHT) {
			outputValue.setText(String.format("x: %.3f\n", se.values[0]));
			
			if(recordValue[0] < Math.abs(se.values[0])) {
				recordValue[0] = Math.abs(se.values[0]);
			}
			outputRecord.setText(String.format("x: %.3f\n", recordValue[0]));
		}
		else if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER || se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD
					|| se.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
			outputValue.setText(String.format("x: %.3f\ny: %.3f\nz: %.3f\n", se.values[0], se.values[1], se.values[2]));
			
			if(outputgraph != null) {
				outputgraph.addPoint(se.values);
			}
			
			if(recordValue[0] < Math.abs(se.values[0])) {
				recordValue[0] = Math.abs(se.values[0]);
			}
			if(recordValue[1] < Math.abs(se.values[1])) {
				recordValue[1] = Math.abs(se.values[1]);
			}
			if(recordValue[2] < Math.abs(se.values[2])) {
				recordValue[2] = Math.abs(se.values[2]);
			}
			outputRecord.setText(String.format("x: %.3f\ny: %.3f\nz: %.3f\n", recordValue[0], recordValue[1], recordValue[2]));
		}
	}
}
