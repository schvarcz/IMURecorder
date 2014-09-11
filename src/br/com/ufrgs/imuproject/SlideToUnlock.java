package br.com.ufrgs.imuproject;

import android.app.Activity;
import android.widget.SeekBar;

public class SlideToUnlock implements SeekBar.OnSeekBarChangeListener {
	
	int startProgress = 0;
	Activity mActivity;
	
	public SlideToUnlock(Activity activity)
	{
		mActivity = activity;
	}
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (seekBar.getProgress() > 95)
		{
			mActivity.finish();
		}
		else
		{
			startProgress = 0;
			seekBar.setProgress(startProgress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if(Math.abs(startProgress - progress) > 25)
		{
			seekBar.setProgress(startProgress);
		}
		else
		{
			startProgress = seekBar.getProgress();
		}
	}

}
