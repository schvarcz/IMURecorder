package br.com.ufrgs.imuproject;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import br.com.ufrgs.imuproject.sensorslisteners.MListener;
import br.com.ufrgs.imuproject.storage.SensorData;

import com.example.imuproject.R;

public class MainActivity extends Activity implements MActivitySensorListener {

	
	TextView textAccelerometer, textGyroscope, textOrientation, textGPS, textSystemTime;
	SeekBar sbStopRecord;
	
	boolean mBound = false;
	LocalBinder localBinder;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textAccelerometer = (TextView)findViewById(R.id.displayAccelerometer);
		textGyroscope = (TextView)findViewById(R.id.displayGyroscope);
		textOrientation = (TextView)findViewById(R.id.displayOrientation);
		textGPS = (TextView)findViewById(R.id.displayGPS);
		textSystemTime = (TextView)findViewById(R.id.displaySystemTime);

		sbStopRecord = (SeekBar)findViewById(R.id.seekStopRecord);
		sbStopRecord.setOnSeekBarChangeListener(new SlideToUnlock(this));
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		Intent it = new Intent(MainActivity.this,SensorReadings.class);
		it.putExtra("datasetName", getIntent().getExtras().getString("datasetName"));
		bindService(it,mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (mBound)
			unbindService(mConnection);
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {

			for(MListener listener: localBinder.getSensorListeners())
			{
				listener.removeActivity(MainActivity.this);
			}
			mBound = false;
			localBinder = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			if (binder == null)
			{
				mBound = false;
				return;
			}
			localBinder = (LocalBinder)binder;
			for(MListener listener: localBinder.getSensorListeners())
			{
				listener.addActivity(MainActivity.this);
			}
			mBound = true;
		}
	};

	@Override
	public void updateActivity(SensorData sensorData) {
		StringBuilder msg = new StringBuilder();
		float[] accelerometer = sensorData.getAccelerometer();
		msg.append("X: ");msg.append(accelerometer[0]);msg.append("\n");
		msg.append("Y: ");msg.append(accelerometer[1]);msg.append("\n");
		msg.append("Z: ");msg.append(accelerometer[2]);msg.append("\n");
		textAccelerometer.setText(msg.toString());

		msg = new StringBuilder();
		float[] gyroscope = sensorData.getGyroscope();
		msg.append("X: ");msg.append(gyroscope[0]);msg.append("\n");
		msg.append("Y: ");msg.append(gyroscope[1]);msg.append("\n");
		msg.append("Z: ");msg.append(gyroscope[2]);msg.append("\n");
		textGyroscope.setText(msg.toString());
		
		msg = new StringBuilder();
		float[] orientation = sensorData.getOrientation();
		msg.append("X: ");msg.append(orientation[0]);msg.append("\n");
		msg.append("Y: ");msg.append(orientation[1]);msg.append("\n");
		msg.append("Z: ");msg.append(orientation[2]);msg.append("\n");
		textOrientation.setText(msg.toString());
		
		msg = new StringBuilder();
		msg.append("Longitude: ");msg.append(sensorData.getLongitude());msg.append("\n");
		msg.append("Latitude: ");msg.append(sensorData.getLatitude());msg.append("\n");
		msg.append("Altitude: ");msg.append(sensorData.getAltitude());msg.append("\n");
		msg.append("Speed: ");msg.append(sensorData.getSpeed());msg.append("\n");
		msg.append("Bearing: ");msg.append(sensorData.getBearing());msg.append("\n");
		msg.append("UtcMilliTime: ");msg.append(sensorData.getUtcMilliTime());msg.append("\n");
		textGPS.setText(msg.toString());

		textSystemTime.setText(String.valueOf(sensorData.getSystemNanoTime()));
	}
	
}
