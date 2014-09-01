package com.example.imuproject;

import com.example.imuproject.storage.FileStorage;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class SensorReadings extends Service implements SensorEventListener, LocationListener  {

	LocationManager mLocationManager;
	SensorManager mSensorManager;
	Sensor sensores = null;

	FileStorage mStorageAccelerometer = new FileStorage();
	FileStorage mStorageLocation = new FileStorage();

	@Override
	public void onCreate(){
		super.onCreate();

		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0.3f,this);
		
		mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sensores = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mSensorManager.registerListener(this, sensores,SensorManager.SENSOR_DELAY_NORMAL);
		
		mStorageAccelerometer.createDataset("accelerometer.txt");
		mStorageLocation.createDataset("gps.txt");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
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
		}
		else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
		{
//			if (btnToggleRecord.isChecked())
				mStorageAccelerometer.writeValues(event.values,event.timestamp);
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
		}
		else if (event.sensor.getType() == Sensor.TYPE_GRAVITY)
		{
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
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
		}
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
		{
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
		}
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED)
		{
			msg = String.valueOf(event.values[0]) + "\n " +
					String.valueOf(event.values[1]) + "\n " +
					String.valueOf(event.values[2]) + "\n " +
					String.valueOf(event.timestamp);
		}
		else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
		{
			msg = String.valueOf(event.values[0]) + "; " +
					String.valueOf(event.timestamp);
		}
		Log.d("AccelerometerG", msg);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		float values[] = {
				(float) location.getLongitude(),
				(float) location.getLatitude(),
				(float) location.getAltitude(),
				location.getAccuracy()
		};
		
		mStorageLocation.writeValues(values, location.getTime());
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

}
