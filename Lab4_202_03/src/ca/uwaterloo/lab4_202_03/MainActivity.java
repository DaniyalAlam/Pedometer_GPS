package ca.uwaterloo.lab4_202_03;

import java.util.Arrays;

import mapper.MapLoader;
import mapper.MapView;
import mapper.NavigationalMap;
import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.uwaterloo.sensortoy.LineGraphView;

public class MainActivity extends ActionBarActivity{
	
	private static MapView mv;
	private static NavigationalMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		mv = new MapView(getApplicationContext(), 900, 900, 50, 50);
		registerForContextMenu(mv);
		map = MapLoader.loadMap(getExternalFilesDir(null) , "Lab-room-peninsula.svg");
		mv.setMap(map);
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
			TextView steps = new TextView(rootView.getContext());
			TextView counterN = new TextView(rootView.getContext());
			TextView counterE = new TextView(rootView.getContext());
			TextView stop = new TextView(rootView.getContext());
			
			Button resetButton = new Button(rootView.getContext());
			LineGraphView graph = new LineGraphView(this.getActivity().getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
			
			steps.setText("Steps: 0");
			counterN.setText(String.format("North: 0"));
			counterE.setText(String.format("East: 0"));
			resetButton.setText("Reset");
			title.setText("Steps Counter");
			stop.setText(String.format("Please set your start and end points"));
			
			//Add Views in order on Linear Layout
			l1.addView(title);
            l1.addView(resetButton);
            l1.addView(steps);
            l1.addView(counterN);
            l1.addView(counterE);
            
            
///////////////////////////////////////////////////////////////////////////////////////////////////            
            
            
            l1.addView(mv);
            l1.addView(logger);
            l1.addView(stop);
            
            positionEventListener  listen0 = new positionEventListener(map, stop);
            mv.addListener(listen0);
            

            
 /////////////////////////////////////////////////////////////////////////////////////////////////           
            
            
            
            
            
            //Code to use Accelerometer Sensor
            SensorManager sensorManager = (SensorManager)rootView.getContext().getSystemService(SENSOR_SERVICE);
            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        	AccelEventListener listen2 = new AccelEventListener(resetButton, steps, counterN, counterE, logger, graph, mv, stop, map);
        	sensorManager.registerListener(listen2, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        	
        	Sensor magneticvector = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        	sensorManager.registerListener(listen2, magneticvector, SensorManager.SENSOR_DELAY_FASTEST);
        	
        	Sensor linearaccel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        	sensorManager.registerListener(listen2, linearaccel, SensorManager.SENSOR_DELAY_FASTEST);
			
			return rootView;
		}
	}
	
 	@Override
	public void onCreateContextMenu ( ContextMenu menu , View v , ContextMenuInfo menuInfo ) {
	super.onCreateContextMenu ( menu , v , menuInfo );
	mv.onCreateContextMenu ( menu , v , menuInfo );
	}
	@Override
	public boolean onContextItemSelected ( MenuItem item ) {
	return super.onContextItemSelected ( item ) || mv.onContextItemSelected ( item );
	}


}
