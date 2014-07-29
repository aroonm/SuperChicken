package com.aroonsworld.gamedev.superchicken;

import java.util.ArrayList;
import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.aroonsworld.gamedev.superchicken.R;

public class SurfaceViewExample extends Activity implements OnTouchListener {
	OurView v;
	int screenW; 
	int screenH;
	int bgrW;
	int bgrH;
	
	Background background;
	Bitmap bkg; 
	
	Chicken player;
	int press=0;
	Bitmap chicken,chicken1;
	int score=0;
	
	ArrayList<Egg> eggListY = new ArrayList<Egg>();
	ArrayList<Egg> eggListR = new ArrayList<Egg>();
	ArrayList<Egg> eggListB = new ArrayList<Egg>();
	ArrayList<Egg> eggListZ = new ArrayList<Egg>();
	Egg eggYY, eggRR, eggBB, eggZZ;
	Bitmap yellow, red, blue, black, exclaim;
	
	private SoundPool soundPool;
	AudioManager audioManager;
	float actualVolume, maxVolume, volume;
	int flap, eggCatch, uhoh;
	boolean loaded = false;
	int sound;
	
	Random r;
	int highScore = 0, totalScore=0;
	int timesPlayed = 0;
	int timesY=0, timesR=0, timesB=0, timesZ=0;
	boolean gameOver = false;
	Paint paint;
	Typeface font1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		v = new OurView(this);
		v.setOnTouchListener(this);
		loadPrefs();
		background = new Background();
		player = new Chicken();
		
		r=new Random();
		for(int i=4; i<=10; i++)
		{
			eggYY = new Egg(i*(400), r.nextInt(300)+120);
			eggListY.add(eggYY);
		}
		for(int i=4; i<=5; i++)
		{
			eggRR = new Egg(i*(2000), r.nextInt(300)+120);
			eggListR.add(eggRR);
		}
		for(int i=4; i<=5; i++)
		{
			eggBB = new Egg(i*(4000), r.nextInt(300)+120);
			eggListB.add(eggBB);
		}
		
		bkg = BitmapFactory.decodeResource(getResources(), R.drawable.brotone);
		
		for(int i=4; i<=10; i++)
		{
			eggZZ = new Egg(i*(1200), r.nextInt(bkg.getHeight())+120);
			eggListZ.add(eggZZ);
		}
		
		yellow = BitmapFactory.decodeResource(getResources(), R.drawable.yellowfin);
		red = BitmapFactory.decodeResource(getResources(), R.drawable.redfin);
		blue = BitmapFactory.decodeResource(getResources(), R.drawable.bluefin);
		black = BitmapFactory.decodeResource(getResources(), R.drawable.blackfin);
		exclaim = BitmapFactory.decodeResource(getResources(), R.drawable.exclamation);
		
		chicken = BitmapFactory.decodeResource(getResources(), R.drawable.chicken);
		chicken1 = BitmapFactory.decodeResource(getResources(), R.drawable.chicken1);
		
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
        flap = soundPool.load(this, R.raw.flap, 1);
        eggCatch = soundPool.load(this, R.raw.buttonsound, 1);
        uhoh = soundPool.load(this, R.raw.uhoh, 1);
        
        font1 = Typeface.createFromAsset(getAssets(), "WALKWAY_BOLD.TTF");
        paint = new Paint();
                
		setContentView(v);
		
	}

	private void loadPrefs() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		String loadedscore = sp.getString("HIGHSCORE", highScore+"");
		highScore = Integer.parseInt(loadedscore);		
		String loadedtimes = sp.getString("TIMESPLAYED", timesPlayed+"");
		timesPlayed = Integer.parseInt(loadedtimes);		
		String loadedY = sp.getString("TIMESY", timesY+"");
		timesY = Integer.parseInt(loadedY);
		String loadedR = sp.getString("TIMESR", timesR+"");
		timesR = Integer.parseInt(loadedR);
		String loadedB = sp.getString("TIMESB", timesB+"");
		timesB = Integer.parseInt(loadedB);
		String loadedZ = sp.getString("TIMESZ", timesZ+"");
		timesZ = Integer.parseInt(loadedZ);
		String loadedTotScore = sp.getString("TOTALSCORE", totalScore+"");
		totalScore = Integer.parseInt(loadedTotScore);		
		String soundLoad = sp.getString("SOUND", "1");
		sound = Integer.parseInt(soundLoad);
	}

	private void savePrefs(String key, String value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	@Override
	protected void onPause() {
		super.onPause();
		v.pause();
	}

	@Override
	public void onBackPressed() {
		v.pause();
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SurfaceViewExample.this);
		builder.setMessage("Head back to Menu Screen?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								v.resume();
								SurfaceViewExample.this.finish();

								//Intent intent = new Intent("com.aroonsworld.superchicken.MENUSPLASH");
								//startActivity(intent);
								
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
						v.resume();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		v.resume();
	}

	public class OurView extends SurfaceView implements Runnable {
		Thread t = null;
		SurfaceHolder holder;
		boolean isItOk = false;
		boolean spriteLoaded = false;

		public OurView(Context context) {
			super(context);
			holder = getHolder();
		}
		
		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			screenW = w;
			screenH = h;
			bkg = Bitmap.createScaledBitmap(bkg, w, h, true);
			bgrW = bkg.getWidth();
			bgrH = bkg.getHeight();
		}



		@Override
		public void run() {
			while (isItOk == true) {
				if (!(holder.getSurface().isValid())) {
					continue;
				}
				Canvas c = holder.lockCanvas();
				update(c);
				paint(c);
				holder.unlockCanvasAndPost(c);
			}
		}

		protected void update(final Canvas c) {
			background.update(c.getWidth());
						
			player.update();
						
			for(int i=0;i<eggListY.size();i++){
				eggYY = eggListY.get(i);
				eggYY.update();
				if(eggYY.getX()<-100){
					eggListY.remove(i);
					eggListY.add(eggListY.size(), new Egg((7*400),r.nextInt(300)+120));
				}
				if(checkCollision()==1) eggYY.setX(-101);
				if(eggYY.getX()>-101 && eggYY.getX()<-yellow.getWidth()){
					gameOver = true;
				}
			}
			
			for(int i=0;i<eggListR.size();i++){
				eggRR = eggListR.get(i);
				eggRR.update();
				if(eggRR.getX()<-100){
					eggListR.remove(i);
					eggListR.add(eggListR.size(), new Egg((2*2000),r.nextInt(300)+120));
				}
				if(checkCollision()==2) eggRR.setX(-101);
				if(eggRR.getX()>-101 && eggRR.getX()<-red.getWidth()){
					gameOver = true;
				}
			}
			
			for(int i=0;i<eggListB.size();i++){
				eggBB = eggListB.get(i);
				eggBB.update();
				if(eggBB.getX()<-100){
					eggListB.remove(i);
					eggListB.add(eggListB.size(), new Egg((2*4000),r.nextInt(300)+120));
				}
				if(checkCollision()==3) eggBB.setX(-101);
				if(eggBB.getX()>-101 && eggBB.getX()<-blue.getWidth()){
					gameOver = true;
				}
			}
			
			for(int i=0;i<eggListZ.size();i++){
				eggZZ = eggListZ.get(i);
				eggZZ.update();
				if(eggZZ.getX()<-50){
					eggListZ.remove(i);
					eggListZ.add(eggListZ.size(), new Egg((7*1200),r.nextInt(300)+120));
				}
				if(checkCollision()==4){
					eggZZ.setX(-51);
					gameOver = true;
				}
			}
			if(score > highScore) {
				highScore = score;
				savePrefs("HIGHSCORE", highScore+"");
			}
			if(gameOver || checkCollision()==4) {				
				if (loaded && sound==1) {
	                soundPool.play(uhoh, volume, volume, 1, 0, 1f);
	            }
				++timesPlayed;
				totalScore+=score;
				savePrefs("TIMESPLAYED", timesPlayed+"");
				savePrefs("TIMESY", timesY+"");
				savePrefs("TIMESR", timesR+"");
				savePrefs("TIMESB", timesB+"");
				savePrefs("TIMESZ", timesZ+"");
				savePrefs("TOTALSCORE", totalScore+"");
				
				Intent intent = new Intent(getApplicationContext(), Score.class);
				intent.putExtra("KEYscore", score);
				intent.putExtra("KEYhiscore", highScore);
				startActivity(intent);
			}
		}
		
		private int checkCollision() {
			if (((((eggYY.getX() + yellow.getWidth()) > player.getCenterX()) && (eggYY.getX()
					+ yellow.getWidth() < player.getCenterX() + chicken.getWidth())))
					&& (((eggYY.getY() + yellow.getHeight()) > player.getCenterY()) && ((eggYY.getY() + yellow
							.getHeight()) < (player.getCenterY() + chicken.getHeight() + 20)))) {
				if (loaded && sound==1) {
	                soundPool.play(eggCatch, volume, volume, 1, 0, 1f);
	            }
				timesY+=1;
				score+=1;
				return 1;
			}
			if (((((eggRR.getX() + red.getWidth()) > player.getCenterX()) && (eggRR.getX()
					+ red.getWidth() < player.getCenterX() + chicken.getWidth())))
					&& (((eggRR.getY() + red.getHeight()) > player.getCenterY()) && ((eggRR.getY() + red
							.getHeight()) < (player.getCenterY() + chicken.getHeight() + 20)))) {
				if (loaded && sound==1) {
	                soundPool.play(eggCatch, volume, volume, 1, 0, 1f);
	            }
				timesR+=1;
				score+=3;
				return 2;
			}
			if (((((eggBB.getX() + blue.getWidth()) > player.getCenterX()) && (eggBB.getX()
					+ blue.getWidth() < player.getCenterX() + chicken.getWidth())))
					&& (((eggBB.getY() + blue.getHeight()) > player.getCenterY()) && ((eggBB.getY() + blue
							.getHeight()) < (player.getCenterY() + chicken.getHeight() + 20)))) {
				if (loaded && sound==1) {
	                soundPool.play(eggCatch, volume, volume, 1, 0, 1f);
	            }
				timesB+=1;
				score+=5;
				return 3;
			}
			if (((((eggZZ.getX() + black.getWidth()) > player.getCenterX()) && (eggZZ.getX()
					+ black.getWidth() < player.getCenterX() + chicken.getWidth())))
					&& (((eggZZ.getY() + black.getHeight()) > player.getCenterY()) && ((eggZZ.getY() + black
							.getHeight()) < (player.getCenterY() + chicken.getHeight() + 20)))) {
				if (loaded && sound==1) {
	                soundPool.play(eggCatch, volume, volume, 1, 0, 1f);
	            }
				timesZ+=1;
				gameOver = true;
				return 4;
			}
			return 0;
		}

		private void paint(Canvas c){
			c.drawBitmap(bkg, background.getBkx(), 0, null);
			c.drawBitmap(bkg, background.getBkx() + c.getWidth(), 0, null);
			
			paint.setColor(Color.BLACK);
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(60);
			paint.setTypeface(font1);
			
			if(gameOver==false)
				c.drawText(score+"", bkg.getWidth()/2, 170, paint);			
			
			if((press%2)!=0){
				c.drawBitmap(chicken, player.getCenterX(), player.getCenterY(), null);
			}
			if((press%2)==0){
				c.drawBitmap(chicken1, player.getCenterX(), player.getCenterY(), null);
			}
			
			for(int j=0;j<eggListY
					.size();j++){
				eggYY = eggListY.get(j);
				if(eggYY.getX()<200){
					c.drawBitmap(exclaim, eggYY.getX()+((yellow.getWidth()/2)-(exclaim.getWidth()/2)), 
							eggYY.getY()-(yellow.getHeight()+(yellow.getHeight()/2)), null);
				}
				c.drawBitmap(yellow, eggYY.getX(), eggYY.getY()- yellow.getHeight()/3, null);
			}	
			
			if(score > 10){
				for(int j=0;j<eggListR.size();j++){
					eggRR = eggListR.get(j);
					if(eggRR.getX()<200){
						c.drawBitmap(exclaim, eggRR.getX()+((red.getWidth()/2)-(exclaim.getWidth()/2)), 
								eggRR.getY()-(red.getHeight()+(red.getHeight()/2)), null);
					}
					c.drawBitmap(red, eggRR.getX(), eggRR.getY()- red.getHeight()/3, null);
				}
			}
			if(score > 20){
				for(int j=0;j<eggListB.size();j++){
					eggBB = eggListB.get(j);
					if(eggBB.getX()<200){
						c.drawBitmap(exclaim, eggBB.getX()+((blue.getWidth()/2)-(exclaim.getWidth()/2)), 
								eggBB.getY()-(blue.getHeight()+(blue.getHeight()/2)), null);
					}
					c.drawBitmap(blue, eggBB.getX(), eggBB.getY()- blue.getHeight()/3, null);
				}
			}
			for(int j=0;j<eggListZ.size();j++){
				eggZZ = eggListZ.get(j);
				c.drawBitmap(black, eggZZ.getX(), eggZZ.getY()- black.getHeight()/3, null);
			}
		}

		public void pause() {
			isItOk = false;
			while (true) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}

		public void resume() {
			isItOk = true;
			t = new Thread(this);
			t.start();
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		switch (me.getAction()) {
		case MotionEvent.ACTION_DOWN:
            if (loaded && sound==1) {
                soundPool.play(flap, volume, volume, 1, 0, 1f);
            }
			player.jump();
			press++;
			break;

		}
		return false;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	
	
}
