package com.lixin.tjpu;

import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.os.Bundle;

/** 选课页面 */

public class CourseActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);
    }
    
    //后台统计
    
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}