package com.aroonsworld.gamedev.superchicken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.aroonsworld.gamedev.superchicken.R;

public class GroupSplash extends Activity {
	
	@Override
	protected void onCreate(Bundle AroonIsAwesome) {
		super.onCreate(AroonIsAwesome);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.grouppage);
		Thread logoTimer = new Thread() {
			public void run() {
				try {
					sleep(5000);
					Intent menuIntent = new Intent(
							"com.aroonsworld.superchicken.MENUSPLASH");
					startActivity(menuIntent);
					GroupSplash.this.finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		logoTimer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
