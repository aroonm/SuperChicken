package com.aroonsworld.gamedev.superchicken;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.appszoom.appszoomsdk.AppsZoom;
import com.aroonsworld.gamedev.superchicken.R;

public class MainActivity extends Activity {

	String name;
	String finalName;
	
	@Override
	protected void onCreate(Bundle AroonIsAwesome) {
		super.onCreate(AroonIsAwesome);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//deletePrefs();
		loadPrefs();
		AppsZoom.start(this);
				
		if (finalName == "" || finalName == " " || finalName == null
				|| finalName == "username") {
			setContentView(R.layout.activity_main);
			
			Button cont = (Button) findViewById(R.id.cont);
			final MediaPlayer buttonSound = MediaPlayer.create(this,
					R.raw.buttonsound);
			final EditText mEdit = (EditText) findViewById(R.id.user);
			
			cont.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					buttonSound.start();

					name = mEdit.getText().toString();
					savePrefs("FINALNAME", name);
					Intent playIntent = new Intent(
							"com.aroonsworld.superchicken.GROUPSPLASH");
					startActivity(playIntent);
					MainActivity.this.finish();
				}
			});
		} 
		else {
			/*
			Intent groupIntent = new Intent(
					"com.aroonsworld.superchicken.GROUPSPLASH");
			startActivity(groupIntent);*/
			
			setContentView(R.layout.grouppage);
			Intent groupIntent;
			groupIntent = new Intent("com.aroonsworld.superchicken.GROUPSPLASH");
			groupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			groupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);                  
			groupIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(groupIntent);
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void loadPrefs() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		finalName = sp.getString("FINALNAME", "username");
	}

	private void savePrefs(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	private void deletePrefs() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

}