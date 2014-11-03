package br.com.ufrgs.imuproject.sensorslisteners;
import br.com.ufrgs.imuproject.storage.GPSData;
import br.com.ufrgs.imuproject.storage.SensorInfo;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class GPSListener extends MListener implements LocationListener {

	
	GPSData mGPSData;
	public GPSListener(SensorInfo sensorInfo, GPSData gpsData) {
		super(sensorInfo);
		mGPSData = gpsData;
	}

	@Override
	public void onLocationChanged(Location location) {
		mSensorInfo.setSystemNanoTime(System.nanoTime());
		mSensorInfo.setGPSInformation(location);
		mGPSData.saveData();
		this.updateActivities();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

}
