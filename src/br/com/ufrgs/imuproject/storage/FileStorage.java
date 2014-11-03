package br.com.ufrgs.imuproject.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Environment;

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
	
	public boolean createDataset(String name, String path)
	{
		File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
				,"Datasets/"+path);
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
	
	public void writeValues(float values[], long timestamp) throws IOException
	{
		StringBuilder line = new StringBuilder();
		for(float value: values)
		{
			line.append(value);
			line.append(";");
		}

		line.append(timestamp);
		this.writeLine(line.toString());
	}
	
	public void writeValues(float values[]) throws IOException
	{
		StringBuilder line = new StringBuilder();
		for(float value: values)
		{
			line.append(value);
			line.append(";");
		}
		this.writeLine(line.toString());
	}
	
	public void write(String line) throws IOException
	{
		currentFileBuffered.write(line);
	}
	
	public void writeLine(String line) throws IOException
	{
		this.write(line);
		currentFileBuffered.newLine();
	}
	
	public void close()
	{
		if (currentFileBuffered != null)
		{
			try {
				currentFileBuffered.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		currentFile = null;
		currentFileBuffered = null;
	}
}
