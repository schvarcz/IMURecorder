package com.example.imuproject.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class FileStorage {

	BufferedWriter currentFileBuffered = null;
	File currentFile = null;
	
	public String getPath()
	{
		if(currentFile != null)
			return currentFile.getAbsolutePath();
		return "File não criado";
	}
	public boolean isExternalStorageWriteable()
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
			return true;
		return false;
	}
	
	public boolean createDataset(String name)
	{
		File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
				,"");
		if (!folder.exists() && !folder.mkdirs())
		{
			return false;
		}

		if(!name.endsWith(".txt"))
			name += ".txt";
		File file = new File(folder, name);
		try {
			if (file.exists() || file.createNewFile())
			{
				currentFile = file;
				currentFileBuffered = new BufferedWriter(new FileWriter(file));
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void writeValues(float values[], long timestamp)
	{
		try {
			for(float value: values)
			{
				currentFileBuffered.write(String.valueOf(value)+";");
			}
			currentFileBuffered.write(String.valueOf(timestamp)+";");
			currentFileBuffered.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		if (currentFileBuffered != null)
		try {
			currentFileBuffered.close();
			currentFile = null;
			currentFileBuffered = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
