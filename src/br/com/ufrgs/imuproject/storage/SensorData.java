package br.com.ufrgs.imuproject.storage;

import java.io.IOException;

import android.util.Log;

public class SensorData {
	private SensorInfo mSensorInfo;

	private FileStorage mStorage = null;

	long lastToast = 0;
	
	public SensorData(FileStorage storage,SensorInfo sensorInfo)
	{
		mSensorInfo = sensorInfo;
		mStorage = storage;

		//try {
		//	mStorage.writeLine(this.getHeader());
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}
	
	public FileStorage getStorage() {
		return mStorage;
	}

	public void setStorage(FileStorage mStorage) {
		this.mStorage = mStorage;
	}
	
	public void saveData()
	{
		if (System.currentTimeMillis() - lastToast > 10000)
		{
			lastToast = System.currentTimeMillis();
			Log.d("IMU",this.toString());
		}
		try {
			mStorage.writeLine(this.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getHeader()
	{
		return "systemNanoTime;azimuth;pitch;roll;gyrx;gyry;gyrz;accx;accy;accz;linear_accx;linear_accy;linear_accz";
	}
	
	public String toString()
	{
		float mGyroscope[] = mSensorInfo.getGyroscope();
		float mAccelerometer[] = mSensorInfo.getAccelerometer();
		float mLinearAccelerations[] = mSensorInfo.getLinearAccelerations();

		double values[] = {
				mSensorInfo.getSystemNanoTime(),
				mSensorInfo.getAzimuth(), //azimuth
				mSensorInfo.getPitch(), //Pitch
				mSensorInfo.getRoll(), //roll
				mGyroscope[0], //x
				mGyroscope[1], //y
				mGyroscope[2], //z
				mAccelerometer[0], //x
				mAccelerometer[1], //y
				mAccelerometer[2],  //z
				mLinearAccelerations[0], //x
				mLinearAccelerations[1], //y
				mLinearAccelerations[2]  //z
		};
		
		StringBuilder ret = new StringBuilder();
		
		for(int i=0; i<values.length;i++)
		{
			ret.append(values[i]);
			if (i < values.length-1)
				ret.append(";");
		}
		
		return ret.toString();
	}

}
