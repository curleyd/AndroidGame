package com.spaceshooter.framework.implementation;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spaceshooter.game.R;
import com.spaceshooter.framework.Audio;
import com.spaceshooter.framework.FileIO;
import com.spaceshooter.framework.Game;
import com.spaceshooter.framework.Graphics;
import com.spaceshooter.framework.Input;
import com.spaceshooter.framework.Screen;

@SuppressLint("NewApi")
public abstract class AndroidGame extends Activity implements Game {
	
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
			int frameBufferWidth = isPortrait ? 600: 800;
			int frameBufferHeight = isPortrait ? 800: 600;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
		
		Point point = new Point();
        try {
        	getWindowManager().getDefaultDisplay().getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = getWindowManager().getDefaultDisplay().getWidth();
            point.y = getWindowManager().getDefaultDisplay().getHeight();
        }
		
		float scaleX = (float) frameBufferWidth / point.x;
		float scaleY = (float) frameBufferHeight / point.y;
		
		Screen.x = point.x;
		Screen.y = point.y;
		
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getInitScreen();
		setContentView(renderView);
		
		context = renderView.getContext();
		
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");			

    }
    
    @Override
    public void onResume() {
    	
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
    	
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();
        
        if(isFinishing())
        	screen.dispose();
        
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {

        return screen;
    }
    
    @Override
	public void setHighScores(final int score) {
		
		if(checkScore(score)){
			runOnUiThread(new Runnable() {
                
				@Override
                public void run() {
					
                	final Dialog dialog = new Dialog(context);
                	
	                dialog.setContentView(R.layout.prompts);
	                dialog.setTitle("New High Score!");
	
	                final EditText editTextKeywordToBlock = (EditText)dialog.findViewById(R.id.editTextKeywordsToBlock);
		                Button btnBlock = (Button)dialog.findViewById(R.id.buttonBlockByKeyword);
		                dialog.show();
		                
		                       
		            btnBlock.setOnClickListener(new View.OnClickListener() {
		                   
	                    @Override
	                    public void onClick(View v){
	                    	 
		                    SharedPreferences prefs = context.getSharedPreferences("highScores", Context.MODE_PRIVATE);
		    					Editor editor = prefs.edit();
		    					
		                	SharedPreferences NHSprefs = context.getSharedPreferences("NhighScores", Context.MODE_PRIVATE);
		    					Editor editor1 = NHSprefs.edit();
		    				
		    				int[] scores = getHighScores();
		    				String[] names = getNHighScores();
		    				String name = "Name";
		    				
		    				name = editTextKeywordToBlock.getText().toString();	
		    								    				
		    				System.out.println("name: " + name + "| ET: " + editTextKeywordToBlock.getText().toString());
		    				
		    					if(score > scores[0]){
		    						
		    						scores[0] = score;
		    						Arrays.sort(scores);
		    							
		    							for(int i = 0; i < 10; i++){
		    								
		    								if(scores[i] == score){	
		    									
		    	        							for(int j = 0; j < i; j++)
		    	        								names[j] = names[j+1];
		    	        											    	        							
		    		        						names[i] = name.substring(0, Math.min(name.length(), 7));	
		    								}
		    							}
		    					}
		    						
		    							for(int i = 0; i < 10; i++){								
		    								String key = "" + i;
		    								
		    								editor.putInt(key, scores[i]);
		    								editor1.putString(key, names[i]);
		    								
		    								editor.commit();
		    								editor1.commit();
		    								System.out.println(names[i] + " " + scores[i]);								
		    							}						
		                    	 
		                    	 		        				 
		        				 Toast.makeText(getApplicationContext(), "Highscore Saved", Toast.LENGTH_SHORT).show();
		        				 dialog.dismiss();

	                    }
		            });
                }
            });		
			
		}
	}
}
