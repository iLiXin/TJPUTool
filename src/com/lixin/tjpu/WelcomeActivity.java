package com.lixin.tjpu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class WelcomeActivity extends Activity{
	private ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		setContentView(R.layout.welcome);
	    image=(ImageView)findViewById(R.id.image);  
        AnimationSet  animationset=new AnimationSet(true);  
        AlphaAnimation alphaAnimation=new AlphaAnimation(0, 1);  
        alphaAnimation.setDuration(4000);  
        animationset.addAnimation(alphaAnimation);  
        image.startAnimation(animationset);  
        new Handler().postDelayed(new Runnable(){   
                
            @Override   
            public void run() {   
                Intent mainIntent = new Intent(WelcomeActivity.this,LoginActivity.class);   
                WelcomeActivity.this.startActivity(mainIntent);   
                WelcomeActivity.this.finish();   
            }   
                 
           }, 5000);   
	}

}