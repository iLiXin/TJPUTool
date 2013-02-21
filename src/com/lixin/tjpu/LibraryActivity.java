package com.lixin.tjpu;

/** 图书馆页面 */

import java.io.IOException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.umeng.analytics.MobclickAgent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LibraryActivity extends Activity {
	
	TextView textView;
	EditText editText;
	Button searchButton;
	String finalUrl;
	String tempUrl;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);
        
        //取得所有控件
        textView = (TextView) findViewById(R.id.libra);
        editText = (EditText) findViewById(R.id.search);
        searchButton = (Button) findViewById(R.id.searchButton);

        //为Button设置监听器
        searchButton.setOnClickListener(new searchListener());
        
        //new session
        String url0 = "http://211.81.31.33/uhtbin/cgisirsi/N2yrsw6IqR/43220271/2/1000";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet getMethod = new HttpGet(url0);  
        try{
        	
	        HttpResponse response = httpClient.execute(getMethod); //发起GET请求  
		    int resCode = response.getStatusLine().getStatusCode(); //获取响应码  
		    String result = EntityUtils.toString(response.getEntity(), "GBK");//获取服务器响应内容 
		    System.out.println(resCode);
		    String session[] = result.split("HREF=");
		    String sessionUrl = "http://211.81.31.33" + session[1].substring(0, 32);
		    
		    //图书馆目录连接，取得真实Session值
		    HttpGet getMethod1 = new HttpGet(sessionUrl);
		    HttpResponse response1 = httpClient.execute(getMethod1); 
		    
		    //通过真实Session值构造新URL
		    String result1 = EntityUtils.toString(response1.getEntity(), "utf-8");
		    String menuResult[] = result1.split("nstead -->");
		    tempUrl = "http://211.81.31.33" + menuResult[1].substring(20, 57);
		    
	    
        }
        catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    //查询按钮的响应事件
    class searchListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			HttpClient httpClient1 = new DefaultHttpClient();
			
			try{
				
				//取得输入框中的关键字
			    String temp = editText.getText().toString();
			    
			    //判断关键字是否为空
			    if(temp.equals("")){
			    	  Toast.makeText(LibraryActivity.this, "请输入关键字！", Toast.LENGTH_SHORT).show();
			    }
			    else{
			    	
			    //对关键字重新编码	
			    String keyword = URLEncoder.encode(temp, "GB2312");
			    
			    //构造最终发起查询请求的字符串
				finalUrl = tempUrl + "/123?searchdata1="+keyword+"&srchfield1=GENERAL%5ESUBJECT%5E%5E%5E%B4%CA%D7%E9%B6%CC%D3%EF&library=%CC%EC%BD%F2%B9%A4%B4%F3&sort_by=TI";
				HttpPost postMethod = new HttpPost(finalUrl);
				
				//发起请求
			    HttpResponse response2 = httpClient1.execute(postMethod); 
			    
			    //对返回的结果重新编码
			    String result20 = EntityUtils.toString(response2.getEntity(), "GBK");
			    String result2 = new String(result20.getBytes("GBK"),"GBK");
			    
			    //将结果发送给BookItems处理，附带cookie值
			    Intent intent = new Intent();
			    intent.putExtra("result", result2 + tempUrl);
			    intent.setClass(LibraryActivity.this, BookItems.class);
			    LibraryActivity.this.startActivity(intent);
		    
		    }
		    
		    
			}catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		}
		
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
