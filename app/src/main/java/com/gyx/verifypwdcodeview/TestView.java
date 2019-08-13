package com.gyx.verifypwdcodeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Created by gaoyuxiang on 2019-08-13.
 * ==============================
 * 功能描述：
 */
public class TestView extends  android.support.v7.widget.AppCompatEditText{
	private Paint rectPaint;
	/**
	 * 横向间隔
	 */
	private int widthSpace;
	/**
	 * 纵向间隔
	 */
	private int heightSpace;
	private int rectangleWidth;
	private int rectangleHeight;
	private int borderWidth;

	public TestView(Context context) {
		super(context);

		setAttr(null, 0);
		init();
	}

	public TestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttr(attrs, 0);
		init();
	}

	public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setAttr(attrs, defStyleAttr);
		init();
	}



	private void setAttr(AttributeSet attrs, int defStyleAttr) {
		TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TestView, defStyleAttr, 0);
		//矩形 宽度
		rectangleWidth = a.getDimensionPixelSize(R.styleable.TestView_rectangleWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

		//矩形 高度
		rectangleHeight = a.getDimensionPixelSize(R.styleable.TestView_rectangleHeight, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

		//边框 厚度
		borderWidth = a.getDimensionPixelSize(R.styleable.TestView_borderWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
		a.recycle();
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		//
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		System.out.println("高度--"+heightSize);
		System.out.println("宽度--"+widthSize);


		/*//高度
		switch (heightMode) {
			case MeasureSpec.EXACTLY:
				heightSize = MeasureSpec.getSize(heightMeasureSpec);
				System.out.println("高度 EXACTLY--"+heightSize);
				break;
			case MeasureSpec.AT_MOST:
				heightSize = widthSize / numLength;
				System.out.println("高度 AT_MOST--"+heightSize);
				break;
		}
		//宽度
		switch (widthMode) {
			case MeasureSpec.EXACTLY:
				widthSize = MeasureSpec.getSize(widthMeasureSpec);
				System.out.println("宽度 EXACTLY--"+widthSize);
				break;
			case MeasureSpec.AT_MOST:
				if (widthSpace > 0) {
					//原有宽度
					widthSize = MeasureSpec.getSize(widthMeasureSpec);
				} else {
					widthSize = (heightSize - borderWidth) * numLength + borderWidth;
				}

				System.out.println("宽度 AT_MOST--"+widthSize);
				break;
		}*/



		widthSize = rectangleWidth * 6;


		//高度
		heightSize = rectangleHeight ;





		System.out.println("最终--"+"宽--"+widthSize+"高--"+heightSize);
		setMeasuredDimension(widthSize, heightSize);
	}

	private void init() {
		//矩形体
		rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//
		rectPaint.setStrokeWidth(widthSpace);
		System.out.println("边框厚度--" + widthSpace);
		setBackgroundDrawable(null);
		setLongClickable(false);
		setTextIsSelectable(false);
		setCursorVisible(false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Path path = new Path();


	}
}







































