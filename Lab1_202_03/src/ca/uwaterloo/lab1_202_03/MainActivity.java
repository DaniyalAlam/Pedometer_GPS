package ca.uwaterloo.lab1_202_03;

import java.util.Arrays;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.uwaterloo.sensortoy.LineGraphView;

public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			LinearLayout linearlayout;
			
			View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
			
			LineGraphView graph = new LineGraphView(this.getActivity().getApplicationContext(),
					100,
					Arrays.asList("x", "y", "z"));

			
			graph.setVisibility(View.VISIBLE);
			
					TextView tvLightLabel = createTextView(rootView, "Light Intensity Sensor");
					TextView tvAccelLabel = createTextView(rootView, "Accelerometer");
					TextView tvMagFieldLabel = createTextView(rootView, "Magnetic Field");
					TextView tvRotationLabel = createTextView(rootView, "Rotation");
					
					TextView tvLightx = createTextView(rootView, null);
					TextView tvAccelxyz = createTextView(rootView, null);
					TextView tvMagFieldxyz = createTextView(rootView, null);
					TextView tvRotationxyz = createTextView(rootView, null);
					
					TextView tvRecordLightLabel = createTextView(rootView, "Highest Light Intensity Value");
					TextView tvRecordAccelLabel = createTextView(rootView, "Highest Accelerometer Values");
					TextView tvRecordMagFieldLabel = createTextView(rootView, "Highest Magnetic Field Values");
					TextView tvRecordRotationLabel = createTextView(rootView, "Highest Rotation Values");
					
					TextView tvRecordLightx = createTextView(rootView, null);
					TextView tvRecordAccelxyz = createTextView(rootView, null);
					TextView tvRecordMagFieldxyz = createTextView(rootView, null);
					TextView tvRecordRotationxyz = createTextView(rootView, null);
					
					linearlayout = (LinearLayout) rootView.findViewById(R.id.linear_layout);
					linearlayout.addView(graph);
					linearlayout.addView(tvLightLabel);
					linearlayout.addView(tvLightx);
					linearlayout.addView(tvAccelLabel);
					linearlayout.addView(tvAccelxyz);
					linearlayout.addView(tvMagFieldLabel);
					linearlayout.addView(tvMagFieldxyz);
					linearlayout.addView(tvRotationLabel);
					linearlayout.addView(tvRotationxyz);
					linearlayout.addView(tvRecordLightLabel);
					linearlayout.addView(tvRecordLightx);
					linearlayout.addView(tvRecordAccelLabel);
					linearlayout.addView(tvRecordAccelxyz);
					linearlayout.addView(tvRecordMagFieldLabel);
					linearlayout.addView(tvRecordMagFieldxyz);
					linearlayout.addView(tvRecordRotationLabel);
					linearlayout.addView(tvRecordRotationxyz);
					linearlayout.setOrientation(LinearLayout.VERTICAL);
					
					SensorManager sensorManager = (SensorManager)
							rootView.getContext().getSystemService(SENSOR_SERVICE);
					Sensor lightSensor =
							sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
					Sensor accelSensor =
							sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
					Sensor magfieldSensor =
							sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
					Sensor rotationSensor =
							sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
					
					SensorEventListener lightEventListener = new MultiSensorEventListener(tvLightx, tvRecordLightx);
					SensorEventListener accelEventListener = new MultiSensorEventListener(tvAccelxyz, tvRecordAccelxyz, graph);
					SensorEventListener magfieldEventListener = new MultiSensorEventListener(tvMagFieldxyz, tvRecordMagFieldxyz);
					SensorEventListener rotationEventListener = new MultiSensorEventListener(tvRotationxyz, tvRecordRotationxyz);
					
					sensorManager.registerListener(lightEventListener, lightSensor,
															SensorManager.SENSOR_DELAY_NORMAL);
					sensorManager.registerListener(accelEventListener, accelSensor,
															SensorManager.SENSOR_DELAY_NORMAL);
					sensorManager.registerListener(magfieldEventListener, magfieldSensor,
															SensorManager.SENSOR_DELAY_NORMAL);
					sensorManager.registerListener(rotationEventListener, rotationSensor,
															SensorManager.SENSOR_DELAY_NORMAL);
					
			return rootView;
		}
	}
	
	public static TextView createTextView(View rootView, String sensorLabel) {
		
		TextView tv = new TextView(rootView.getContext());
		tv.setText(sensorLabel);
		
		return tv;
	}
}
