 ColorTransformIndicator

![image](https://github.com/rios168/ColorTransformIndicator/blob/master/00gif.gif)       ![image](https://github.com/rios168/ColorTransformIndicator/blob/master/01.jpg)    ![image](https://github.com/rios168/ColorTransformIndicator/blob/master/02.jpg)
![image](https://github.com/rios168/ColorTransformIndicator/blob/master/03.jpg)    ![image](https://github.com/rios168/ColorTransformIndicator/blob/master/04.jpg)    ![image](https://github.com/rios168/ColorTransformIndicator/blob/master/05.jpg)
![image](https://github.com/rios168/ColorTransformIndicator/blob/master/06.jpg)    ![image](https://github.com/rios168/ColorTransformIndicator/blob/master/07.jpg)

 

使用
------

```
private String[] mTitles = {"同城", "笑话", "图片","天气","电影",
			"音乐","机票","彩票","游戏","小说",
			"股票","理财","新闻","军事","社会","娱乐"};
private void setIndicator() {
		//移动方块背景颜色更改在 shape_indicator.xml
		mIndicator.setViewPager(mViewPager);
		mIndicator.setTitleString(mTitles);//mTitles的大小要和mViewPager的大小一致
	    mIndicator.setColorTextBack(Color.BLACK);//设置字体背景颜色
		mIndicator.setColorTextCurrent(0xFFF0FFFF);//设置字体选择时的颜色
		mIndicator.setBackgroundColor(0xFFFF6A6A);//设置背景颜色
		mIndicator.setTitleTextSize(20);//设置字体大小
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
```

教程
--
http://blog.csdn.net/rios168/article/details/50554680

