package com.lixin.tjpu;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BookDetail extends Activity{
	
	
	TextView titleV;
	TextView authorV;
	TextView statusV;
	TextView numberV;
	TextView typeV;
	TextView locV;
	TextView isbnV;
	TextView chubansheV;
	TextView yemaV;
	TextView jianjieV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_detail);
		titleV = (TextView) findViewById(R.id.bookTitle);
		authorV = (TextView) findViewById(R.id.bookAuthor);
		statusV= (TextView) findViewById(R.id.bookStatus);
		numberV= (TextView) findViewById(R.id.bookNumber);
		typeV = (TextView) findViewById(R.id.bookType);
		locV = (TextView) findViewById(R.id.bookLoc);
		isbnV = (TextView) findViewById(R.id.bookIsbn);
		chubansheV = (TextView) findViewById(R.id.bookChubanshe);
		yemaV = (TextView) findViewById(R.id.bookYema);
		jianjieV = (TextView) findViewById(R.id.bookJianjie);
		
		Intent intent = getIntent();
		String detail = intent.getStringExtra("detail");
		String[] cut = detail.split("<!-- Print the title, if one exists -->");
		
		int titleStatr = cut[1].indexOf("<strong>");
		int titleEnd = cut[1].indexOf("</strong>");
		String title = cut[1].substring(titleStatr+8, titleEnd);
		
		int authorStart = cut[1].indexOf("<!-- Print the author, if one exists -->");
		int authorEnd = cut[1].indexOf("<!-- show the following div if there is a summary put in it -->");
		String author = cut[1].substring(authorStart, authorEnd);
		
		int statusStart = cut[1].indexOf("<!-- show if catalog or call-num item view -->");
		int statusEnd = cut[1].indexOf("<!-- check for the existence of MARC Holdings data -->");
		String status = cut[1].substring(statusStart, statusEnd);
		
		int numStart = cut[1].indexOf("<!-- Start new callnum -->");
		int numEnd = cut[1].indexOf("<!-- Boundwith holdings -->");
		String number = cut[1].substring(numStart, numEnd);
		
		int isbnStart = cut[1].indexOf("<!-- Print ISBN number, if exists -->");
		int isbnEnd = cut[1].indexOf("<!--list_of_available/holdings -->");
		
		System.out.println(isbnStart+"--------"+isbnEnd);
		String isbn = cut[1].substring(isbnStart, isbnEnd);
		
//		String chubanshe = test[112];
//		String yema = test[115];
//		String jianjie = test[182];
		
		titleV.setText(filterHtml(title).replace("\n", ""));
		authorV.setText(filterHtml(author).replace("\n", "").replace("&nbsp;", ""));
		statusV.setText(filterHtml(status).replace("\n", "").replace("&nbsp", "").replace(" ", ""));
		String[] ntl = number.split("<([^>]*)>");
		
		numberV.setText(ntl[2].replace("\n", ""));
		typeV.setText(ntl[6].replace("\n", ""));
		locV.setText(ntl[8].replace("\n", ""));
		isbnV.setText(filterHtml(isbn).replace("\n", ""));
//		chubansheV.setText(chubanshe);
//		yemaV.setText(yema);
//		jianjieV.setText(jianjie);
	}
	
	
	//¹ýÂËhtml±êÇ©
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
	
	
	
	
	
	
	public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);

    }
	
}
