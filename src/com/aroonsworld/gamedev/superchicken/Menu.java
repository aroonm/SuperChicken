package com.aroonsworld.gamedev.superchicken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aroonsworld.gamedev.superchicken.R;

public class Menu extends Activity implements AnimationListener {

	String userName;
	Animation slideRight, fadeIn;

	private SoundPool soundPool;
	AudioManager audioManager;
	float actualVolume, maxVolume, volume;
	int buttonSound;
	boolean loaded = false;
	int sound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.menu);
		loadPrefs();

		LinearLayout upmostLayout = (LinearLayout) findViewById(R.id.title);
		fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		fadeIn.setAnimationListener(this);
		upmostLayout.startAnimation(fadeIn);

		final TextView mEdit = (TextView) findViewById(R.id.welcome);
		mEdit.setText("Welcome back, " + userName);
		slideRight = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_right);
		slideRight.setAnimationListener(this);
		mEdit.startAnimation(slideRight);

		LinearLayout downmostLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		fadeIn.setAnimationListener(this);
		downmostLayout.startAnimation(fadeIn);

		Button play = (Button) findViewById(R.id.play);
		Button inst = (Button) findViewById(R.id.instbutton);
		Button share = (Button) findViewById(R.id.sharebutton);
		Button devs = (Button) findViewById(R.id.devbutton);
		Button trophy = (Button) findViewById(R.id.trophybutton);
		Button settings = (Button) findViewById(R.id.settingsbutton);

		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                    int status) {
                loaded = true;
            }
        });
        buttonSound = soundPool.load(this, R.raw.buttonsound, 1);
        
		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loadPrefs();
				if (loaded && sound==1) {
					
		            soundPool.play(buttonSound, volume, volume, 1, 0, 1f);
		        }
				Intent playIntent = new Intent(
						"com.aroonsworld.superchicken.SURFACEVIEWEXAMPLE");
				startActivity(playIntent);
				/*
				 * Intent playIntent; playIntent = new
				 * Intent("com.aroonsworld.superchicken.SURFACEVIEWEXAMPLE");
				 * playIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 * playIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				 * playIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				 * startActivity(playIntent);
				 */
			}
		});

		inst.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				loadPrefs();
				if (loaded && sound==1) {
		            soundPool.play(buttonSound, volume, volume, 1, 0, 1f);
		        }
				Intent instIntent = new Intent(
						"com.aroonsworld.superchicken.INSTRUCTIONS");
				startActivity(instIntent);
			}
		});

		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				loadPrefs();
				if (loaded && sound==1) {
		            soundPool.play(buttonSound, volume, volume, 1, 0, 1f);
		        }
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT,
						"Play 'Super Chicken' on Android" + "\n");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
				startActivity(Intent.createChooser(shareIntent,
						"Share Super Chicken"));
			}
		});

		devs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadPrefs();
				if (loaded && sound==1) {
		            soundPool.play(buttonSound, volume, volume, 1, 0, 1f);
		        }
				Intent abdevIntent = new Intent(
						"com.aroonsworld.superchicken.ABOUTDEVELOPER");
				startActivity(abdevIntent);
			}
		});

		trophy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadPrefs();
				if (loaded && sound==1) {
		            soundPool.play(buttonSound, volume, volume, 1, 0, 1f);
		        }
				Intent trophyIntent = new Intent(
						"com.aroonsworld.superchicken.TROPHY");
				startActivity(trophyIntent);
			}
		});

		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadPrefs();
				if (loaded && sound==1) {
		            soundPool.play(buttonSound, volume, volume, 1, 0, 1f);
		        }
				Intent settingsIntent = new Intent(
						"com.aroonsworld.superchicken.SETTINGS");
				startActivity(settingsIntent);
			}
		});

	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
		builder.setMessage("Do you really want to quit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// v.resume();

								Menu.this.finish();
								/*
								 * Intent intent = new
								 * Intent(Intent.ACTION_MAIN);
								 * intent.addCategory(Intent.CATEGORY_HOME);
								 * intent
								 * .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								 * startActivity(intent);
								 */
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// v.resume();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	private void loadPrefs() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		userName = sp.getString("FINALNAME", "username");
		String loadSound = sp.getString("SOUND", "1");
		sound = Integer.parseInt(loadSound);
	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}
