package com.lixin.tjpu;

import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.os.Bundle;


	/**  ����ҳ��    */

public class AboutActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

	
	
	//��̨ͳ��
	
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
