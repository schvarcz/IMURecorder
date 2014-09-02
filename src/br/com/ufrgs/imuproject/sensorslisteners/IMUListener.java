package br.com.ufrgs.imuproject.sensorslisteners;

import br.com.ufrgs.imuproject.storage.SensorData;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class IMUListener extends MListener implements SensorEventListener {


	public IMUListener(SensorData sensorData) {
		super(sensorData);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			mSensorData.setAccelerometer(event.values);
			mSensorData.setSystemNanoTime(event.timestamp);
			mSensorData.saveData();
			this.updateActivities();
		}
		else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
		{
		}
		else if (event.sensor.getType() == Sensor.TYPE_GRAVITY)
		{
		}
		else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
		{
		}
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
		{
			mSensorData.setGyroscope(event.values);
			mSensorData.setSystemNanoTime(event.timestamp);
			mSensorData.saveData();
			this.updateActivities();
		}
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED)
		{
		}
		else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
		{
		}
		else if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
		{
			mSensorData.setOrientation(event.values);
			mSensorData.setSystemNanoTime(event.timestamp);
			mSensorData.saveData();
			this.updateActivities();
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}
