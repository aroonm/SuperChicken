package com.aroonsworld.gamedev.superchicken;

import com.aroonsworld.gamedev.superchicken.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
 
public class Score extends Activity {

	int finalScore, finalHiScore;
	String finalName;
	int sound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.score);
		loadPrefs();
		Intent intent = getIntent();
		finalScore = intent.getIntExtra("KEYscore", 0);
		finalHiScore = intent.getIntExtra("KEYhiscore", 0);
		
		Button namePiece = (Button)findViewById(R.id.namepiece);
		namePiece.setText(finalName);
		namePiece.setTextSize(28);
		namePiece.setTextColor(Color.WHITE);

		Button playAgain = (Button) findViewById(R.id.again);
		Button menuCall = (Button) findViewById(R.id.back);
		Button scoreButton = (Button) findViewById(R.id.score);
		Button hiScoreButton = (Button) findViewById(R.id.highscore);

		playAgain.setText("Play Again");
		menuCall.setText("Menu");
		scoreButton.setText("Score    " + finalScore);
		hiScoreButton.setText("HighScore    " + finalHiScore);

		final MediaPlayer buttonSound = MediaPlayer.create(this,
				R.raw.buttonsound);

		playAgain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(sound==1)
					buttonSound.start();
				Intent playIntent = new Intent(
						"com.aroonsworld.superchicken.SURFACEVIEWEXAMPLE");
				startActivity(playIntent);
			}
		});

		menuCall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(sound==1)
						buttonSound.start();
				Score.this.finish();
				//Intent playIntent = new Intent("com.aroonsworld.superchicken.MENUSPLASH");
				//startActivity(playIntent);
				Intent playIntent;
				playIntent = new Intent("com.aroonsworld.superchicken.MENUSPLASH");
				playIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				playIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);                  
				playIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(playIntent);
			}
		});

	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Score.this);
		builder.setMessage("Go back to the Menu?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Score.this.finish();
								//Intent playIntent = new Intent("com.aroonsworld.superchicken.MENUSPLASH");
								//startActivity(playIntent);
								
								Intent playIntent;
								playIntent = new Intent("com.aroonsworld.superchicken.MENUSPLASH");
								playIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								playIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);                  
								playIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
								startActivity(playIntent);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
		
	}
	
	private void loadPrefs() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		finalName = sp.getString("FINALNAME", "username");
		String loadedSound = sp.getString("SOUND", sound + "");
		sound = Integer.parseInt(loadedSound);
	}
	
}
