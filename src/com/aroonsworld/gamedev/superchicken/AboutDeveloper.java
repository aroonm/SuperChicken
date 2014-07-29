package com.aroonsworld.gamedev.superchicken;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.aroonsworld.gamedev.superchicken.R;

public class AboutDeveloper extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.aboutdeveloper);
		

		
		TextView intro = (TextView)findViewById(R.id.intro);
		intro.setText(" We create games for you to play and to love. \n\n" +
				      "Our motto; Beautiful games. For free. Forever.");
	}
}
