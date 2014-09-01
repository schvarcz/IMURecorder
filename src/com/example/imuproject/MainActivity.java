package com.example.imuproject;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.Math;

import com.example.imuproject.storage.FileStorage;

public class MainActivity extends Activity implements SensorEventListener {

	LocationManager mLocationManager;
	SensorManager mSensorManager;
	Sensor sensores = null;
	
	TextView textAccelerometer, textTemperature;
	EditText etDatasetName;
	ToggleButton btnToggleRecord;
	
	FileStorage mStorage = new FileStorage();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0.3f,locationListener);
		
//		mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//		sensores = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

		textAccelerometer = (TextView)findViewById(R.id.displayAccelerometer);
		textTemperature = (TextView)findViewById(R.id.displayTemperature);
		etDatasetName = (EditText)findViewById(R.id.etDatasetName);
		btnToggleRecord = (ToggleButton)findViewById(R.id.btnToggleRecord);
		
		btnToggleRecord.setOnCheckedChangeListener(onCheckedChange);
		
		textAccelerometer.setText("Loaded");
//		if (sensores ==null)
//			textAccelerometer.setText("Sem accelerometer");
		
	}

	@Override
	protected void onResume() {
//		mSensorManager.registerListener(this, sensores,SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
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

	@Override
	public void onSensorChanged(SensorEvent event) {
		String msg = "";
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
			textAccelerometer.setText(msg);
		}
		else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
		{
			if (btnToggleRecord.isChecked())
				mStorage.writeValues(event.values,event.timestamp);
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
			textAccelerometer.setText(msg);
		}
		else if (event.sensor.getType() == Sensor.TYPE_GRAVITY)
		{
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
			textAccelerometer.setText(msg);
		}
		else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
		{
			float values[] = {0,0,0};
			SensorManager.getOrientation(event.values, values);
//				
//			msg = String.valueOf(values[0]*180./Math.PI) + "\n " +
//					String.valueOf(values[1]*180./Math.PI) + "\n " +
//					String.valueOf(values[2]*180./Math.PI) + "\n " +
//					String.valueOf(event.timestamp);
			textAccelerometer.setText(msg);
		}
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
		{
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
			textAccelerometer.setText(msg);
		}
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED)
		{
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
			textAccelerometer.setText(msg);
		}
		else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
		{
			msg = String.valueOf(event.values[0]) + "; " +
					String.valueOf(event.timestamp);
			textTemperature.setText(msg);
		}
		Log.d("AccelerometerG", msg);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	

	private OnCheckedChangeListener onCheckedChange = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked)
			{
				String datasetName = etDatasetName.getText().toString();
				if (datasetName.equals(""))
				{
					datasetName = "IMU";
				}
				if (!mStorage.isExternalStorageWriteable())
				{
					Toast.makeText(MainActivity.this, "ops... sem external storage!", Toast.LENGTH_LONG).show();
					btnToggleRecord.setChecked(false);
				}
				else if (!mStorage.createDataset(datasetName))
				{
					Toast.makeText(MainActivity.this, "Erro ao criar arquivo de log. "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), Toast.LENGTH_LONG).show();
					btnToggleRecord.setChecked(false);
				}
				else
					Toast.makeText(MainActivity.this, mStorage.getPath(), Toast.LENGTH_LONG).show();
			}
			else
				mStorage.close();
		}
	};
	
}
