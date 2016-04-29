package ca.uwaterloo.Lab0_202_03;

import java.util.Arrays;

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
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            LinearLayout l1 = (LinearLayout) rootView.findViewById(R.id.label2);
            l1.setOrientation(LinearLayout.VERTICAL);
            
            
            LineGraphView graph = new LineGraphView(this.getActivity().getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
            l1.addView(graph);
            graph.setVisibility(View.VISIBLE);
            
            TextView tv= new TextView(rootView.getContext());
        	tv.setText("Light Sensor");
        	TextView light = new TextView(rootView.getContext());
        	TextView max_light = new TextView(rootView.getContext());
        	
        	SensorManager sensormanager = (SensorManager) rootView.getContext().getSystemService(SENSOR_SERVICE);
        	Sensor lightSensor = sensormanager.getDefaultSensor(Sensor.TYPE_LIGHT);
        	SensorEventListener2 l = new SensorEventListener2(light, max_light);
        	sensormanager.registerListener(l, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        	
        	l1.addView(tv);
        	l1.addView(light);
        	l1.addView(max_light);
        	
        	
        	//accelerometer
        	
        	
        	
            TextView tv1 = new TextView (rootView.getContext());
            TextView accel_coor = new TextView (rootView.getContext());
            TextView max_accel = new TextView (rootView.getContext());
            tv1.setText("Accelerometer");           
            
            Sensor accelerometer = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        	SensorEventListener2 listen2 = new SensorEventListener2(accel_coor, max_accel, graph);
        	sensormanager.registerListener(listen2, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        	
            l1.addView(tv1);
            l1.addView(accel_coor);
            l1.addView(max_accel);
            
            //magnetic field
            
        	
        	TextView tv2 = new TextView(rootView.getContext());
            TextView max_magnetic = new TextView(rootView.getContext());
            TextView mag_coor = new TextView(rootView.getContext());
            tv2.setText("Magnetic Field");
            
            
            Sensor magnetic = sensormanager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        	SensorEventListener2 listen3 = new SensorEventListener2(mag_coor, max_magnetic);
        	sensormanager.registerListener(listen3, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
        	
            l1.addView(tv2);
        	l1.addView(mag_coor);
            l1.addView(max_magnetic);
        	
        	//rotation vector
            
            
            TextView rot_coor = new TextView(rootView.getContext());
            TextView tv3 = new TextView(rootView.getContext());
            TextView max_rot = new TextView(rootView.getContext());
            tv3.setText("Rotation Vector");
            
            Sensor rotation = sensormanager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            SensorEventListener2 listen4 = new SensorEventListener2(rot_coor, max_rot);
            sensormanager.registerListener(listen4, rotation, SensorManager.SENSOR_DELAY_NORMAL);
            
            l1.addView(tv3);
            l1.addView(rot_coor);
            l1.addView(max_rot);
            
            
            return rootView;
        }
    }

}
