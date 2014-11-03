package br.com.ufrgs.imuproject.sensorslisteners;

import br.com.ufrgs.imuproject.storage.SensorData;
import br.com.ufrgs.imuproject.storage.SensorInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class IMUListener extends MListener implements SensorEventListener {


	float gravity[] = new float[3];
	float geomagnetic[] = new float[3];
	SensorData mSensorData;
	
	public IMUListener(SensorInfo sensorInfo, SensorData sensorData) {
		super(sensorInfo);
		mSensorData = sensorData;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			gravity = event.values;
			mSensorInfo.setAccelerometer(event.values);
			mSensorInfo.setSystemNanoTime(event.timestamp);
			this.updateOrientation();
			mSensorData.saveData();
			this.updateActivities();
		}
		else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
		{
			mSensorInfo.setLinearAccelerations(event.values);
			mSensorInfo.setSystemNanoTime(event.timestamp);
			mSensorData.saveData();
			this.updateActivities();
		}
		else if (event.sensor.getType() == Sensor.TYPE_GRAVITY)
		{
		}
		else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
		{
		}
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
		{
			mSensorInfo.setGyroscope(event.values);
			mSensorInfo.setSystemNanoTime(event.timestamp);
			mSensorData.saveData();
			this.updateActivities();
		}
		else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
		{
			geomagnetic = event.values;
			this.updateOrientation();
			mSensorInfo.setSystemNanoTime(event.timestamp);
			mSensorData.saveData();
		}
		else if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
		{
//			mSensorData.setOrientation(event.values);
//			mSensorData.setSystemNanoTime(event.timestamp);
//			mSensorData.saveData();
//			this.updateActivities();
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void updateOrientation()
	{
		float R[] = new float[9];
		float I[] = new float[9];
		if(SensorManager.getRotationMatrix(R, I, gravity, geomagnetic))
		{
			float values[] = new float[3];
			SensorManager.getOrientation(R, values);
			values[0] = (float)(values[0]*180./Math.PI);
			values[1] = (float)(values[1]*180./Math.PI);
			values[2] = (float)(values[2]*180./Math.PI);
			mSensorInfo.setOrientation(values);
		}
	}
}
