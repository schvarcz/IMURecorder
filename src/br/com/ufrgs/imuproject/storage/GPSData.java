package br.com.ufrgs.imuproject.storage;

import java.io.IOException;
import android.util.Log;

public class GPSData {
	private SensorInfo mSensorInfo;

	private FileStorage mStorage = null;

	long lastToast = 0;
	
	public GPSData(FileStorage storage,SensorInfo sensorInfo)
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
		return "systemNanoTime;utcMilliTime;latitude;longitude;altitude;speed;bearing";
	}
	
	public String toString()
	{
		double values[] = {
				mSensorInfo.getSystemNanoTime(),
				mSensorInfo.getUtcMilliTime(),
				mSensorInfo.getLatitude(),
				mSensorInfo.getLongitude(),
				mSensorInfo.getAltitude(),
				mSensorInfo.getSpeed(),
				mSensorInfo.getBearing()
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
