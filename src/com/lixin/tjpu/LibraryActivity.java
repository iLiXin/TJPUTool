package com.lixin.tjpu;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
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
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);
        
        textView = (TextView) findViewById(R.id.libra);
        editText = (EditText) findViewById(R.id.search);
        searchButton = (Button) findViewById(R.id.searchButton);

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
//	    System.out.println(result);
	    String session[] = result.split("HREF=");
//	    System.out.println("-------"+session[1].substring(0, 32));
	    String sessionUrl = "http://211.81.31.33" + session[1].substring(0, 32);
	    
	    //图书馆目录
	    HttpGet getMethod1 = new HttpGet(sessionUrl);
	    HttpResponse response1 = httpClient.execute(getMethod1); 
	    
	    String result1 = EntityUtils.toString(response1.getEntity(), "utf-8");
//	    System.out.println(result1);
	    String menuResult[] = result1.split("nstead -->");
//	    System.out.println("++++++++"+menuResult[1].substring(20, 57));
	    tempUrl = "http://211.81.31.33" + menuResult[1].substring(20, 57);
	    
	    
        }
        catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    class searchListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			HttpClient httpClient1 = new DefaultHttpClient();
			
			
			
			try{
				
			
			
			
		    String temp = editText.getText().toString();
		    
		    if(temp.equals("")){
		    	  Toast.makeText(LibraryActivity.this, "请输入关键字！", Toast.LENGTH_SHORT).show();
		    }
		    else{
		    	
		    String keyword = URLEncoder.encode(temp, "GB2312");
		    
//		    System.out.println("key==="+keyword);
		    
		    
			finalUrl = tempUrl + "/123?searchdata1="+keyword+"&srchfield1=GENERAL%5ESUBJECT%5E%5E%5E%B4%CA%D7%E9%B6%CC%D3%EF&library=%CC%EC%BD%F2%B9%A4%B4%F3&sort_by=TI";
			HttpPost postMethod = new HttpPost(finalUrl);
			
		    HttpResponse response2 = httpClient1.execute(postMethod); 
		    
		    String result20 = EntityUtils.toString(response2.getEntity(), "GBK");
		    String result2 = new String(result20.getBytes("GBK"),"GBK");
		    
		    Intent intent = new Intent();
		    intent.putExtra("result", result2 + tempUrl);
		    intent.setClass(LibraryActivity.this, BookItems.class);
		    LibraryActivity.this.startActivity(intent);
		    
		    }
		    
		    
			}catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
		
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