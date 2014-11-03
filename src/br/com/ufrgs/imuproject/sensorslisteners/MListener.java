package br.com.ufrgs.imuproject.sensorslisteners;

import java.util.ArrayList;

import br.com.ufrgs.imuproject.MActivitySensorListener;
import br.com.ufrgs.imuproject.storage.SensorInfo;

public class MListener {

	SensorInfo mSensorInfo;
	ArrayList<MActivitySensorListener> mActivities = new ArrayList<MActivitySensorListener>();

	public MListener(SensorInfo sensorInfo)
	{
		mSensorInfo = sensorInfo;
	}
	public boolean addActivity(MActivitySensorListener activity)
	{
		return mActivities.add(activity);
	}
	
	public boolean removeActivity(MActivitySensorListener activity)
	{
		return mActivities.remove(activity);
	}
	
	public void updateActivities()
	{
		for(MActivitySensorListener activity: mActivities)
		{
			activity.updateActivity(mSensorInfo);
		}
	}
	
}
