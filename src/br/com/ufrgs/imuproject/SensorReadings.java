package br.com.ufrgs.imuproject;

import br.com.ufrgs.imuproject.sensorslisteners.GPSListener;
import br.com.ufrgs.imuproject.sensorslisteners.IMUListener;
import br.com.ufrgs.imuproject.storage.FileStorage;
import br.com.ufrgs.imuproject.storage.SensorData;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class SensorReadings extends Service {

	LocalBinder mBinder = new LocalBinder(this);
	LocationManager mLocationManager;
	SensorManager mSensorManager;
	Sensor sensorAccelerometer = null;
	Sensor sensorLinearAcceleration = null;
	Sensor sensorGyroscope = null;
	Sensor sensorOrientation = null;
	Sensor sensorMagneticField = null;

	FileStorage mStorage = new FileStorage();
	SensorData mSensorData = new SensorData(mStorage);

	GPSListener mGPSListener = new GPSListener(mSensorData);
	IMUListener mIMUListener = new IMUListener(mSensorData);

	@Override
	public void onCreate(){
		super.onCreate();

	}

	@Override
	public IBinder onBind(Intent intent) {

		//Should be more smart to deal with multiples datasets requests?
		String datasetName = intent.getExtras().getString("datasetName");
		if (datasetName.trim().equals(""))
		{
			datasetName = "IMU";
		}

		if (!mStorage.isExternalStorageWriteable())
		{
			Toast.makeText(this, "ops... no external storage!", Toast.LENGTH_LONG).show();
			return null;
		}
		else if (!mStorage.createDataset(datasetName))
		{
			Toast.makeText(this, "Error to create the log file.", Toast.LENGTH_LONG).show();
			return null;
		}
		else
			Toast.makeText(this, mStorage.getPath(), Toast.LENGTH_LONG).show();

		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0.3f,mGPSListener);

		mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(mIMUListener, sensorAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
		sensorLinearAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mSensorManager.registerListener(mIMUListener, sensorLinearAcceleration,SensorManager.SENSOR_DELAY_NORMAL);
		sensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		mSensorManager.registerListener(mIMUListener, sensorGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
		sensorOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mSensorManager.registerListener(mIMUListener, sensorOrientation,SensorManager.SENSOR_DELAY_NORMAL);
		sensorMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mSensorManager.registerListener(mIMUListener, sensorMagneticField,SensorManager.SENSOR_DELAY_NORMAL);

		Toast.makeText(this, "Everything is turned on!", Toast.LENGTH_LONG).show();

		mBinder.addSensorListener(mGPSListener);
		mBinder.addSensorListener(mIMUListener);

		return mBinder;
	}

	@Override
	public void onDestroy() {
		mLocationManager.removeUpdates(mGPSListener);
		mSensorManager.unregisterListener(mIMUListener);
		mStorage.close();
		super.onDestroy();
	}

}
