package com.lixin.tjpu;

import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.os.Bundle;

/** ѡ��ҳ�� */

public class CourseActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);
    }
    
    //��̨ͳ��
    
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}