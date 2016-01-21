package com.rios.indicator.indicatior.indicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.rios.indicator.indicatior.R;

public class Indicator extends FrameLayout implements OnClickListener {

    private int mViewWidth;
    private int mLeftMargin;
    private LayoutParams mParamsChildBack;
    private int mVisibleCount;
    private int mHeight;
    private int mChildCount;
    private int mMeasuredWidth;
    private int mScrollOffset;
    private float mRawX;
    private int mScrollX;
    private int mWidthPixels;
    private ViewPager mViewPager;
    private float mDensity;
    private TextView mBackText;
    private LinearLayout mLinearLayout;
    private float mTextSize;
    private int mTitleMargin;
    private int mColorTextBack;
    private int mColorTextCurrent;
    private String[] mTitles = {"同城", "笑话", "图片"};

    public Indicator(Context context) {
        this(context, null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitData(getContext());
        InitLinearLayout(getContext());
    }

    public void setParams() {
        InitBackText(getContext());
        mLinearLayout.removeAllViews();
        addLayoutView(getContext());
        addView(mBackText);
        addView(mLinearLayout);
        setLenght();
        getScreenWidth();
    }

    private void InitData(Context context) {
        mBackText = new TextView(context);
        mDensity = context.getResources().getDisplayMetrics().density;
        mTextSize = 8 * mDensity;
        mTitleMargin = (int) (5 * mDensity);
        mScrollX = 0;
        mColorTextBack = Color.BLACK;
        mColorTextCurrent = Color.WHITE;
    }

    /**
     * 设置移动的背景
     *
     * @param context
     */
    private void InitBackText(Context context) {
        mBackText.setBackgroundResource(R.drawable.shape_indicator);
        //设置预设宽度
        mBackText.setText(mTitles[0]);
        mBackText.setTextSize(mTextSize);
        mBackText.setTextColor(Color.TRANSPARENT);
        //测量并且设置宽度
        mBackText.measure(0, 0);
        int mBacktextWidth = mBackText.getMeasuredWidth();
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(mTitleMargin, mTitleMargin, mTitleMargin, mTitleMargin);
        params.width = mBacktextWidth;
        mBackText.setLayoutParams(params);
        mParamsChildBack = (LayoutParams) mBackText.getLayoutParams();
    }

    /**
     * 增加一个LinearLayout
     *
     * @param context
     */
    private void InitLinearLayout(Context context) {
        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 添加标题
     *
     * @param context
     */
    public void addLayoutView(Context context) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(mTitleMargin, mTitleMargin, mTitleMargin, mTitleMargin);
        for (int i = 0; i < mTitles.length; i++) {
            IndicatorTextView indicatorTextView = new IndicatorTextView(context);
            indicatorTextView.setTextSize(mTextSize);
            indicatorTextView.setText(mTitles[i]);
            indicatorTextView.setMarginInt(mTitleMargin);
            indicatorTextView.setColorTextBack(mColorTextBack);
            indicatorTextView.setColorTextCurrent(mColorTextCurrent);
            mLinearLayout.addView(indicatorTextView);
            //设置使用指定参数
            indicatorTextView.setParams();
            indicatorTextView.setLayoutParams(params);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    /**
     * 测量总宽度,Margin,子View的个数,单击事件
     */
    private void setLenght() {
        mChildCount = mLinearLayout.getChildCount();
        View childAt = mLinearLayout.getChildAt(1);
        childAt.measure(0, 0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt
                .getLayoutParams();
        mLeftMargin = layoutParams.leftMargin;
        int rightMargin = layoutParams.rightMargin;
        mMeasuredWidth = childAt.getMeasuredWidth();
        mLinearLayout.measure(0, 0);
        mHeight = mLinearLayout.getMeasuredHeight();
        mViewWidth = mMeasuredWidth + mLeftMargin + rightMargin;
        LayoutParams params = new LayoutParams((mViewWidth) * mChildCount,
                mHeight);
        mLinearLayout.setLayoutParams(params);
        for (int i = 0; i < mChildCount; i++) {
            View view = mLinearLayout.getChildAt(i);
            view.setTag(i);
            view.setOnClickListener(this);
            view.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    onTouchEvent(event);
                    return false;
                }
            });
        }
    }

    /**
     * 获取屏幕像素 ,可见的个数
     */
    private void getScreenWidth() {
        mWidthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
        mVisibleCount = mWidthPixels / mViewWidth;
    }

    /**
     * 测量并设置ViewGroup宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((mViewWidth) * mChildCount, mHeight);
    }

    /**
     * 外部调用 设置背景的位置 ,字体的颜色
     *
     * @param position
     * @param offset
     */
    public void setPosition(int position, float offset) {
        //背景位置
        mParamsChildBack.leftMargin = (int) (mLeftMargin + mViewWidth
                * position + mViewWidth * offset);
        mBackText.setLayoutParams(mParamsChildBack);

        /**
         * 移动位置
         * offset =0 or =1时 代表开始或结束
         */
        if (offset != 0 && offset != 1) {
            mScrollX = getScrollX();
            int scrollPosition = (int) (mScrollX / mViewWidth + 0.5f);
            mScrollOffset = scrollPosition + mVisibleCount - 2;
            if (scrollPosition + 1 <= position && position <= mScrollOffset) {

            } else {//当前不可见时,移动ViewGroup
                if (position <= scrollPosition) {//向左 -1
                    if (position <= 0) {
                        scrollTo(0, 0);
                    } else {
                        scrollTo((int) (mScrollX - mViewWidth * (1 - offset)), 0);
                    }
                } else if (position >= mScrollOffset) {//向右 +1
                    if (position + 2 == mTitles.length) {
                        scrollTo(mViewWidth * mChildCount - mWidthPixels, 0);
                    } else {
                        scrollTo((int) (mViewWidth * (offset) + mScrollX), 0);
                    }
                }
            }
        }

        //设置相连2个子View要显示的颜色
        IndicatorTextView childAt1 = (IndicatorTextView) mLinearLayout
                .getChildAt(position);
        childAt1.setRectRight(offset);
        if (position + 1 < mChildCount) {
            IndicatorTextView childAt2 = (IndicatorTextView) mLinearLayout
                    .getChildAt(position + 1);
            childAt2.setRectLeft(offset);
        }
        invalidate();
    }

    /**
     * 单击事件,关联ViewPager跳转
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mViewPager == null) {
            return;
        }
        int tag = (Integer) v.getTag();
        mViewPager.setCurrentItem(tag, false);
        if (mListener != null)
            mListener.OnItemClick(v, tag);
        for (int i = 0; i < mChildCount; i++) {
            if (tag == i) {
                continue;
            }
            IndicatorTextView childAt = (IndicatorTextView) mLinearLayout
                    .getChildAt(i);
            childAt.clearColor();
        }
    }

    /**
     * 手动调整位置
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRawX = event.getRawX();
                mScrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = event.getRawX() - mRawX;
                this.scrollTo((int) (mScrollX - offsetX), 0);
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() < 0) {
                    ViewHelper.setScrollX(this, 0);
                }
                if (getScrollX() > ((mViewWidth) * mChildCount) - mWidthPixels) {
                    ViewHelper.setScrollX(this, ((mViewWidth) * mChildCount)
                            - mWidthPixels);
                }
                break;
        }
        return true;
    }

    /**
     * 外部调用 设置标题
     *
     * @param mTitles
     */
    public void setTitleString(String[] mTitles) {
        this.mTitles = mTitles;
    }

    /**
     * 外部调用 关联ViewPager
     */
    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    public void setColorTextBack(int color) {
        this.mColorTextBack = color;
    }

    public void setColorTextCurrent(int color) {
        this.mColorTextCurrent = color;
    }

    public void setTitleTextSize(float size) {
        this.mTextSize = size;
    }

    private OnClickItemListener mListener;

    public interface OnClickItemListener {
        public void OnItemClick(View childView, int position);
    }

    public void setOnClickItemListener(OnClickItemListener listener) {
        mListener = listener;
    }

}
