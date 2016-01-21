package com.rios.indicator.indicatior.indicator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rios.indicator.indicatior.R;

import java.util.ArrayList;

public class IndicatorActivity extends Activity {

	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indicator);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mIndicator = (Indicator) findViewById(R.id.indicator);
		mViewList = new ArrayList<View>();
		initList();
		mViewPager.setAdapter(adapter);
		setIndicator();
	}
	private String[] mTitles = {"同城", "笑话", "图片","天气","电影",
			"音乐","机票","彩票","游戏","小说",
			"股票","理财","新闻","军事","社会","娱乐"};
	private void setIndicator() {
		//移动方块背景颜色更改在 shape_indicator.xml
		mIndicator.setViewPager(mViewPager);
		mIndicator.setTitleString(mTitles);//mTitles的大小要和mViewPager的大小一致
//		mIndicator.setColorTextBack(Color.BLACK);//设置字体背景颜色
//		mIndicator.setColorTextCurrent(0xFFF0FFFF);//设置字体选择时的颜色
//		mIndicator.setBackgroundColor(0xFFFF6A6A);//设置背景颜色
//		mIndicator.setTitleTextSize(20);//设置字体大小
		mIndicator.setParams();//使以上参数生效
		mIndicator.setOnClickItemListener(new Indicator.OnClickItemListener() {
			@Override
			public void OnItemClick(View childView, int position) {
				Toast.makeText(IndicatorActivity.this,"你点击的是:"+position+"  "+
						((TextView)childView).getText().toString(),Toast.LENGTH_SHORT).show();
			}
		});
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
			}
			@Override
			public void onPageScrolled(int position, float positionOffset,
									   int positionOffsetPixels) {
				//设置颜色切换位置
				mIndicator.setPosition(position, positionOffset);
			}
			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	private void initList() {
		for (int i = 0; i < 16; i++) {
			TextView textView = new TextView(this);
			textView.setText("ViewPager页面:"+i);
			textView.setTextSize(30);
			textView.setTextColor(Color.BLUE);
			textView.setGravity(Gravity.CENTER);
			mViewList.add(textView);
		}
	}

	private PagerAdapter adapter = new PagerAdapter() {
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}
		
		@Override
		public int getCount() {
			return mViewList.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = mViewList.get(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		
		
	};
	private ArrayList<View> mViewList;
	private Indicator mIndicator;
}
