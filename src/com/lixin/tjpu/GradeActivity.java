package com.lixin.tjpu;

/** 成绩查询页面 */

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GradeActivity extends Activity {
	
	//声明所有控件
	Button gradeButton = null;
	Button scheduleButton = null;
	Button xiaoliButton = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade);
        
        //获取所有按钮控件并添加响应事件
        gradeButton = (Button) findViewById(R.id.bxqcjcx);
        gradeButton.setOnClickListener(new cxButtonListener());
        scheduleButton = (Button) findViewById(R.id.bxqkb);
        scheduleButton.setOnClickListener(new kbButtonListener());
        xiaoliButton = (Button) findViewById(R.id.xlcx);
        xiaoliButton.setOnClickListener(new xlButtonListener());
        
    }
    
    //成绩查询Listener
    
    class cxButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			String baseUrl = "http://jwpt.tjpu.edu.cn:8081/bxqcjcxAction.do";  
	        
	        HttpClient httpClient = new DefaultHttpClient(); 
	        
	        //取得Cookie，并保存在Application中
	        CookieStore cookie = ((AbstractHttpClient)httpClient).getCookieStore();
			MyApp myCookie = (MyApp) getApplication();
			cookie = myCookie.getCookies();
			List<Cookie> cookies = cookie.getCookies();
			
			//判断cookies内容是否为空
			if (cookies.isEmpty()) {     
				System.out.println("cookie为空啊");
			}else{
				System.out.println(cookies.get(0).getValue());
			}
			
			//将cookie加入到新的请求头中
			HttpPost httpPost = new HttpPost(baseUrl);  
			httpPost.setHeader("Cookie", "JSESSIONID=" + cookies.get(0).getValue()); 
			   
			 
			try{  
		 
			    HttpResponse response = httpClient.execute(httpPost); //发起POST请求  
			  
			    int resCode = response.getStatusLine().getStatusCode(); //获取响应码  
			    String result = EntityUtils.toString(response.getEntity(), "utf-8");//获取服务器响应内容 
			    System.out.println(resCode);

			    //讲数据结果发送给GradeResult页面进行处理
			    Intent intent = new Intent();
			    intent.putExtra("grades", result);
				intent.setClass(GradeActivity.this, GradeResult.class);
				GradeActivity.this.startActivity(intent);
				
			}catch (ClientProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
    	
    }
    
    
    //课表查询Listener
    
    class kbButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			//直接跳转到Schedule页面
			Intent intent = new Intent();
			intent.setClass(GradeActivity.this, Schedule.class);
			GradeActivity.this.startActivity(intent);
		
		}
    	
    }
    
    //校历查询Listener
    
    class xlButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			showToast(GradeActivity.this, "此功能尚未完善", 2000);
		}
    	
    }
    
    
    //toast频率
    private static Toast mToast = null;  
    public static void showToast(Context context, String text, int duration) {  
        if (mToast == null) {  
            mToast = Toast.makeText(context, text, duration);  
        } else {  
            mToast.setText(text);  
            mToast.setDuration(duration);  
        }  
  
        mToast.show();  
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