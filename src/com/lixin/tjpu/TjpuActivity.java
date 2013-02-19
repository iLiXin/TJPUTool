package com.lixin.tjpu;

import com.umeng.analytics.MobclickAgent;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class TjpuActivity extends TabActivity{

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
    	menu.add(0, 0, 1, R.string.about);
    	menu.add(0, 1, 2, com.lixin.tjpu.R.string.exit);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		System.out.println("ItemId"+item.getItemId());
		
		if(item.getItemId()==0){
			Intent intent = new Intent();
			intent.setClass(TjpuActivity.this, AboutActivity.class);
			TjpuActivity.this.startActivity(intent);
			
		}
		else if(item.getItemId()==1){
			System.exit(0);
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		TabHost tabHost = getTabHost();
		
		Intent grade = new Intent();
		grade.setClass(this, GradeActivity.class);
		TabHost.TabSpec gradeSpec = tabHost.newTabSpec("��ѯ");
		Resources res = getResources();
		gradeSpec.setIndicator("��ѯ", res.getDrawable(R.drawable.search));
		gradeSpec.setContent(grade);
		tabHost.addTab(gradeSpec);
		
		Intent course = new Intent();
		course.setClass(this, CourseActivity.class);
		TabHost.TabSpec courseSpec = tabHost.newTabSpec("ѡ��");
		courseSpec.setIndicator("ѡ��", res.getDrawable(R.drawable.course));
		courseSpec.setContent(course);
		tabHost.addTab(courseSpec);
		
		Intent library = new Intent();
		library.setClass(this, LibraryActivity.class);
		TabHost.TabSpec librarySpec = tabHost.newTabSpec("ͼ���");
		librarySpec.setIndicator("ͼ���", res.getDrawable(R.drawable.library));
		librarySpec.setContent(library);
		tabHost.addTab(librarySpec);
	}
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
	
}
