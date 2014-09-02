package br.com.ufrgs.imuproject.sensorslisteners;
import br.com.ufrgs.imuproject.storage.SensorData;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class GPSListener extends MListener implements LocationListener {

	
	
	public GPSListener(SensorData sensorData) {
		super(sensorData);
	}

	@Override
	public void onLocationChanged(Location location) {
		mSensorData.setSystemNanoTime(System.nanoTime());
		mSensorData.setGPSInformation(location);
		mSensorData.saveData();
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
