package com.nileworx.flagsquiz;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class SoundClass {

	public Context context;
	MediaPlayer sound;

	SharedPreferences mSharedPreferences;

	// ==============================================================================

	public SoundClass(Context context) {
		this.context = context;
		sound = new MediaPlayer();

		mSharedPreferences = context.getSharedPreferences("MyPref", 0);
	}

	// ==============================================================================

	public void playSound(final int effect) {
		if (mSharedPreferences.getInt("sound", 1) == 1) {
			sound = new MediaPlayer();

			AssetFileDescriptor fd = context.getResources().openRawResourceFd(effect);
			try {
				sound.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
				sound.prepare();
				sound.start();
//				if (effect == R.raw.clock_tick) {
//					sound.setLooping(true);
//				} 
								
				sound.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						// Do the work after completion of audio
						
							mp.release();
						
																		
					}
				});

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public void stopSound(final int effect) {
		if (mSharedPreferences.getInt("sound", 1) == 1) {
//			sound = new MediaPlayer();

//			AssetFileDescriptor fd = context.getResources().openRawResourceFd(effect);
			try {
//				sound.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());				
				sound.stop();
				sound.release();

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}	

}