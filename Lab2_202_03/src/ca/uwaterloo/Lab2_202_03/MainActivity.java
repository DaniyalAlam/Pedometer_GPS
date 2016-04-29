package ca.uwaterloo.Lab2_202_03;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

		@SuppressLint("InlinedApi")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			LinearLayout l1 = (LinearLayout) rootView.findViewById(R.id.label1);
			
			//Initialize objects for Linear Layout
			TextView title = new TextView(rootView.getContext());
			TextView logger = new TextView(rootView.getContext());
			TextView counter = new TextView(rootView.getContext());
			Button resetButton = new Button(rootView.getContext());
			LineGraphView graph = new LineGraphView(this.getActivity().getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
			graph.setVisibility(View.VISIBLE);
			
			counter.setText(String.format("Steps:\n0"));
			title.setText("Steps Counter");
			
			//Add Views in order on Linear Layout
			l1.addView(title);
            l1.addView(graph);
            l1.addView(resetButton);
            l1.addView(logger);
            l1.addView(counter);
            
            //Code to use Accelerometer Sensor
            SensorManager sensorManager = (SensorManager)rootView.getContext().getSystemService(SENSOR_SERVICE);
            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        	AccelEventListener listen2 = new AccelEventListener(resetButton, counter, logger, graph);
        	sensorManager.registerListener(listen2, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
			
			return rootView;
		}
	}

}
