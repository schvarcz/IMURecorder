package br.com.ufrgs.imuproject;

import java.util.ArrayList;

import br.com.ufrgs.imuproject.sensorslisteners.MListener;
import android.app.Service;
import android.os.Binder;

public class LocalBinder extends Binder {
	ArrayList<MListener> listeners = new ArrayList<MListener>();
	Service mService;
	public LocalBinder(Service service)
	{
		mService = service;
	}
	
	public Service getService()
	{
		return mService;
	}

	public void addSensorListener(MListener listener)
	{
		listeners.add(listener);
	}
	
	public ArrayList<MListener> getSensorListeners()
	{
		return listeners;
	}
	
}
