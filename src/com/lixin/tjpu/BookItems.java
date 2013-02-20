package com.lixin.tjpu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.umeng.analytics.MobclickAgent;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

	

public class BookItems extends ListActivity{

	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library_result);
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.book_items,
                new String[]{"title","year","author","status"},
                new int[]{R.id.title,R.id.year,R.id.author,R.id.status});
        setListAdapter(adapter);
        
        ListView listView = new ListView(this);
        View emptyView = findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
		
	}
	
	
	
	
	
	
	
	private List<HashMap<String, String>> getData(){
	
		Intent intent = getIntent();
		String result = intent.getStringExtra("result");
		
        List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
        try{
        
		//统计结果数
	    String[] items = result.split("<strong>#");
	    System.out.println(items.length);
	    
	    for(int i=1;i<items.length;i++){
	    
	    	
		    HashMap<String, String> map = new HashMap<String, String>();
		    //划分各属性
		    String[] item = items[i].split("<strong>");
		    
		    if(item.length>=3){
		    	
		    int yearIndex =  item[1].indexOf("</strong>");
		    
		    
		    
		    //获取年份
		    String year = item[1].substring(0, yearIndex);
//		    System.out.println("year"+year+"year");
		    
		    //获取名称
		    int nameIndex = item[2].indexOf("</strong>");
		    String fullName =  item[2].substring(0, nameIndex);
		    int realIndex = fullName.lastIndexOf(" ");
		    String name = fullName.substring(0, realIndex);
//		    System.out.println("name" + name+"name");
		    
		    //获取作者
		    int authorIndex = item[2].indexOf("</label>");
		    String author = item[2].substring(nameIndex+55, authorIndex-9);
//		    System.out.println("author"+author+"author");
		    
		    //获取状态
		    int statusIndex0 = item[2].indexOf("<p>");
		    int statusIndex1 = item[2].indexOf("</p>");
		    String status = item[2].substring(statusIndex0+19, statusIndex1-21);
//		    System.out.println("status"+status+"status");
		    
		    map.put("title", name);
		    map.put("year", year);
		    map.put("author", author);
		    map.put("status", status);
		    
		    list.add(map);
		    }
	    }
		return list;
	    		
        }catch (Exception e) {
		// TODO: handle exception
        	return list;
        }
	
	}

	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		System.out.println("点击了"+id);
		
		Intent intent = getIntent();
		String temp = intent.getStringExtra("result");
		String tempUrl = temp.substring(temp.length()-56, temp.length());
		String finalUrl = tempUrl + "/9?first_hit=1&last_hit=20&form_type=&VIEW%5E" + (id+1) + "=%C8%AB%B2%BF%CF%B8%BD%DA";
		System.out.println(finalUrl);
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet getMethod = new HttpGet(finalUrl); 
		
		try {
			HttpResponse response = httpClient.execute(getMethod); //发起GET请求  
			
			int resCode = response.getStatusLine().getStatusCode(); //获取响应码  
			
			String result = EntityUtils.toString(response.getEntity(), "GBK");//获取服务器响应内容 
			
			System.out.println(resCode);
//			System.out.println(result);
			
			Intent intent1 = new Intent();
			intent1.putExtra("detail", result);
			intent1.setClass(BookItems.this, BookDetail.class);
			BookItems.this.startActivity(intent1);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
