package com.aroonsworld.gamedev.superchicken;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.aroonsworld.gamedev.superchicken.R;

public class Trophy extends Activity {

	String highScore, totalScore, timesPlayed, timesY, timesR, timesB, timesZ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.trophypage);
		loadPrefs();
		
		TextView hsView = (TextView)findViewById(R.id.hiscoreboard);
		hsView.setText("High Score "+highScore);
		TextView timesView = (TextView)findViewById(R.id.timesscoreboard);
		timesView.setText("Times Played "+timesPlayed);		
		TextView avsView = (TextView)findViewById(R.id.avscoreboard);
		if(Integer.parseInt(timesPlayed)!=0){
			avsView.setText("Average Score "+(Integer.parseInt(totalScore)/Integer.parseInt(timesPlayed)));
		}
		else{
			avsView.setText("Average Score 0");
		}
		
		TextView yeggsView = (TextView)findViewById(R.id.yeggscoreboard);
		yeggsView.setText(timesY);
		TextView reggsView = (TextView)findViewById(R.id.reggscoreboard);
		reggsView.setText(timesR);
		TextView beggsView = (TextView)findViewById(R.id.beggscoreboard);
		beggsView.setText(timesB);
		TextView zeggsView = (TextView)findViewById(R.id.zeggscoreboard);
		zeggsView.setText(timesZ);
	}

	private void loadPrefs() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		highScore = sp.getString("HIGHSCORE", "0");
		totalScore = sp.getString("TOTALSCORE", "0");
		timesPlayed = sp.getString("TIMESPLAYED", "0");
		timesY = sp.getString("TIMESY", "0");
		timesR = sp.getString("TIMESR", "0");
		timesB = sp.getString("TIMESB", "0");
		timesZ = sp.getString("TIMESZ", "0");
	}

}
