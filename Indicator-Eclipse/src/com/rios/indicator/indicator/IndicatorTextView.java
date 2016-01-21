package com.rios.indicator.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class IndicatorTextView extends TextView {

    private String mAttrText;
    private Paint mPaint;
    private int mHeight;
    private RectF mRect;
    private int mWidth;
    private int mMargin;
    private float mDensity;
    private float mTextSize;
    private int mMarginInt;
    private int mColorTextBack;
    private int mColorTextCurrent;

    public IndicatorTextView(Context context) {
        this(context, null);
    }

    public IndicatorTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorTextView(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDensity = context.getResources().getDisplayMetrics().density;
        mRect = new RectF();
        mMarginInt = (int) (5 * mDensity);
        mColorTextBack = Color.RED;
        mColorTextCurrent = Color.WHITE;
    }

    public void setMarginInt(int margin){
        this.mMarginInt = margin;
    }

    public void setColorTextBack(int color){
        this.mColorTextBack = color;
    }
    public void setColorTextCurrent(int color){
        this.mColorTextCurrent =  color;
    }


    /**
     * 设置参数
     */
    public void setParams() {
        mAttrText = getText().toString();
        mTextSize = getTextSize();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColorTextBack);
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.STROKE);
        invalidate();
        measure(0, 0);
        mMargin = (int) (mMarginInt);
        Rect rect = new Rect();
        mPaint.getTextBounds(mAttrText, 0, mAttrText.length(), rect);
        float height = rect.height();
        float w2 = (getMeasuredHeight()- height-mMargin)/2;
        mHeight = (int) (getMeasuredHeight() - mMargin - w2);
        mWidth = getMeasuredWidth();
    }

    /**
     * 绘画字体
     *  canvas.clipRect  局部绘画
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mColorTextBack);
        canvas.drawText(mAttrText, 0, mHeight, mPaint);
        mPaint.setColor(mColorTextCurrent);
        canvas.clipRect(mRect);
        canvas.drawText(mAttrText, 0, mHeight, mPaint);
    }

    /**
     * 左边子View颜色转换百分比
     * @param percent
     */
    public void setRectLeft(float percent) {
        int position = (int) ((mWidth + 2 * mMargin) * percent);
        if (position < 2 * mMargin) {
            position = -position;
        } else if (position > 2 * mMargin) {
            position = position - 2 * mMargin;
        } else if (position == 2 * mMargin) {
            position = 0;
        }
        mRect.set(-2 * mMargin, 0, position, mHeight + mMargin);
        invalidate();
    }

    /**
     * 右边子View颜色转换百分比
     * @param percent
     */
    public void setRectRight(float percent) {
        mRect.set((mWidth + 2 * mMargin) * percent, 0, mWidth + 2 * mMargin,
                mHeight + mMargin);
        invalidate();
    }

    /**
     * 清除颜色
     */
    public void clearColor() {
        mRect.set(0, 0, 0, 0);
        invalidate();
    }
}
