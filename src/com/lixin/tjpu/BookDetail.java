package com.lixin.tjpu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


	/**  书籍详细信息页面    */

public class BookDetail extends Activity{
	
	private TextView titleV;
	private TextView authorV;
	private TextView statusV;
	private TextView numberV;
	private TextView typeV;
	private TextView locV;
	private TextView isbnV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_detail);
		
		//获取所有要显示的TextView
		titleV = (TextView) findViewById(R.id.bookTitle);
		authorV = (TextView) findViewById(R.id.bookAuthor);
		statusV= (TextView) findViewById(R.id.bookStatus);
		numberV= (TextView) findViewById(R.id.bookNumber);
		typeV = (TextView) findViewById(R.id.bookType);
		locV = (TextView) findViewById(R.id.bookLoc);
		isbnV = (TextView) findViewById(R.id.bookIsbn);
		
		
		//从结果中截取所需字符
		
		Intent intent = getIntent();
		String detail = intent.getStringExtra("detail");
		String[] cut = detail.split("<!-- Print the title, if one exists -->");
		
		//书名
		int titleStatr = cut[1].indexOf("<strong>");
		int titleEnd = cut[1].indexOf("</strong>");
		String title = cut[1].substring(titleStatr+8, titleEnd);
		
		//作者
		int authorStart = cut[1].indexOf("<!-- Print the author, if one exists -->");
		int authorEnd = cut[1].indexOf("<!-- show the following div if there is a summary put in it -->");
		String author = cut[1].substring(authorStart, authorEnd);
		
		//书籍状态
		int statusStart = cut[1].indexOf("<!-- show if catalog or call-num item view -->");
		int statusEnd = cut[1].indexOf("<!-- check for the existence of MARC Holdings data -->");
		String status = cut[1].substring(statusStart, statusEnd);
		
		//书籍编号
		int numStart = cut[1].indexOf("<!-- Start new callnum -->");
		int numEnd = cut[1].indexOf("<!-- Boundwith holdings -->");
		String number = cut[1].substring(numStart, numEnd);
		
		//ISBN
		int isbnStart = cut[1].indexOf("<!-- Print ISBN number, if exists -->");
		int isbnEnd = cut[1].indexOf("<!--list_of_available/holdings -->");
		
		System.out.println(isbnStart+"--------"+isbnEnd);
		String isbn = cut[1].substring(isbnStart, isbnEnd);
		
		//过滤多余字符，填充TextView
		titleV.setText(filterHtml(title).replace("\n", ""));
		authorV.setText(filterHtml(author).replace("\n", "").replace("&nbsp;", ""));
		statusV.setText(filterHtml(status).replace("\n", "").replace("&nbsp", "").replace(" ", ""));
		isbnV.setText(filterHtml(isbn).replace("\n", ""));
		
		//对图书编号数据进行详细拆分
		String[] ntl = number.split("<([^>]*)>");
		
		//详细编号
		numberV.setText(ntl[2].replace("\n", ""));
		
		//详细种类
		typeV.setText(ntl[6].replace("\n", ""));
		
		//详细位置
		locV.setText(ntl[8].replace("\n", ""));
		
	}
	
	
	//过滤html标签
	public static String filterHtml(String str) {

		Pattern pattern = Pattern.compile( "<([^>]*)>");

		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();

		boolean result1 = matcher.find();

		while (result1) {

			matcher.appendReplacement(sb, "");

			result1 = matcher.find();

		}

		matcher.appendTail(sb);

		return sb.toString();

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
