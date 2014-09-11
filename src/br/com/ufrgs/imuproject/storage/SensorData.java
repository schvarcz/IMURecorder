package br.com.ufrgs.imuproject.storage;

import java.io.IOException;

import android.location.Location;
import android.util.Log;

public class SensorData {
	private long systemNanoTime;

	private long mUtcMilliTime;
	private double mLatitude;
	private double mLongitude;
	private double mAltitude;
	private double mSpeed;
	private double mBearing;

	private float mGyroscope[] = {0.f,0.f,0.f};
	private float mAccelerometer[] = {0.f,0.f,0.f};
	private float mLinearAccelerations[] = {0.f,0.f,0.f};

	private float mOrientation[] = {0.f,0.f,0.f};

	private FileStorage mStorage = null;

	long lastToast = 0;
	
	public SensorData(FileStorage storage)
	{
		mStorage = storage;

//		try {
//			mStorage.writeLine(this.getHeader());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public void setGPSInformation(Location location)
	{
		this.setUtcMilliTime(location.getTime());
		this.setLatitude(location.getLatitude());
		this.setLongitude(location.getLongitude());
		this.setAltitude(location.getAltitude());
		this.setSpeed(location.getSpeed());
		this.setBearing(location.getBearing());
	}

	public float[] getAccelerometer() {
		return mAccelerometer;
	}
	
	public void setAccelerometer(float[] values)
	{
		mAccelerometer = values;
	}
	
	public float[] getLinearAccelerations() {
		return mLinearAccelerations;
	}

	public void setLinearAccelerations(float[] mLinearAccelerations) {
		this.mLinearAccelerations = mLinearAccelerations;
	}

	public float[] getGyroscope() {
		return mGyroscope;
	}
	
	public void setGyroscope(float gyroscope[])
	{
		mGyroscope = gyroscope;
	}
	
	public float[] getOrientation() {
		return mOrientation;
	}

	public void setOrientation(float[] mOrientation) {
		this.mOrientation = mOrientation;
	}
	
	public long getSystemNanoTime() {
		return systemNanoTime;
	}

	public void setSystemNanoTime(long systemNanoTime) {
		this.systemNanoTime = systemNanoTime;
	}

	public long getUtcMilliTime() {
		return mUtcMilliTime;
	}

	public void setUtcMilliTime(long mUtcMilliTime) {
		this.mUtcMilliTime = mUtcMilliTime;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}

	public double getAltitude() {
		return mAltitude;
	}

	public void setAltitude(double mAltitude) {
		this.mAltitude = mAltitude;
	}

	public double getSpeed() {
		return mSpeed;
	}

	public void setSpeed(double mSpeed) {
		this.mSpeed = mSpeed;
	}

	public double getBearing() {
		return mBearing;
	}

	public void setBearing(double mBearing) {
		this.mBearing = mBearing;
	}

	public float getAzimuth() {
		return this.mOrientation[0];
	}

	public void setAzimuth(float azimuth) {
		this.mOrientation[0] = azimuth;
	}

	public float getPitch() {
		return this.mOrientation[1];
	}

	public void setPitch(float pitch) {
		this.mOrientation[1] = pitch;
	}

	public float getRoll() {
		return this.mOrientation[2];
	}

	public void setRoll(float roll) {
		this.mOrientation[2] = roll;
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
		return "systemNanoTime;utcMilliTime;latitude;longitude;altitude;speed;bearing;azimuth;pitch;roll;gyrx;gyry;gyrz;accx;accy;accz;linear_accx;linear_accy;linear_accz";
	}
	
	public String toString()
	{
		double values[] = {
				systemNanoTime,
				mUtcMilliTime,
				mLatitude,
				mLongitude,
				mAltitude,
				mSpeed,
				mBearing,
				mOrientation[0], //azimuth
				mOrientation[1], //Pitch
				mOrientation[2], //roll
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
