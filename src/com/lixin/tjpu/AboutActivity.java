package com.lixin.tjpu;

import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.os.Bundle;


	/**  关于页面    */

public class AboutActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

	
	
	//后台统计
	
	@Override
	protected void onPause() {
		
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
}
