package com.aroonsworld.gamedev.superchicken;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.appszoom.appszoomsdk.AppsZoom;
import com.aroonsworld.gamedev.superchicken.R;

public class Settings extends Activity {

	int sound = 1;
	String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.settings);
		
		AppsZoom.showAd(this);
		
		final ImageView on = (ImageView) findViewById(R.id.on_btn);
		final ImageView off = (ImageView) findViewById(R.id.off_btn);
		Button reset = (Button) findViewById(R.id.buttonreset);

		final MediaPlayer buttonSound = MediaPlayer.create(this,
				R.raw.buttonsound);
		
		loadPrefs();
		if(sound==0){
			off.setVisibility(View.VISIBLE);
			on.setVisibility(View.GONE);
		}
		else{
			off.setVisibility(View.GONE);
			on.setVisibility(View.VISIBLE);
		}

		on.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sound = 0;
				savePrefs("SOUND", "0");
				System.out.println("SOUND IS NOW "+ sound);
				on.setVisibility(View.GONE);
				off.setVisibility(View.VISIBLE);

				
			}
		});

		off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sound = 1;
				savePrefs("SOUND", "1");
				System.out.println("SOUND IS NOW "+ sound);
				off.setVisibility(View.GONE);
				on.setVisibility(View.VISIBLE);
			}
		});

		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sound == 1)
					buttonSound.start();
				deletePrefs();
				Toast.makeText(getApplicationContext(), "Data successfully deleted ..",
						   Toast.LENGTH_SHORT).show();
				savePrefs("FINALNAME",userName);
			}
		});
	}

	private void loadPrefs() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		String loadedSound = sp.getString("SOUND", "1");
		sound = Integer.parseInt(loadedSound);
		userName = sp.getString("FINALNAME", "username");
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
