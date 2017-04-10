package com.conan.myapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.conan.myapp.R;

/**
 * Author        JY
 * PublishDate   2017-04-10
 * Description   功能描述
 * Version       1.0
 * Updated       JY
 */
public class TopBar extends RelativeLayout {

    private LayoutParams mRightParams;
    private TypedArray mTypedArray;
    private TextView mTitleView;
    private Button mRightButton;
    private Button mLeftButton;
    private String mTitle;
    private int mTitleTextColor;
    private float mTitleTextSize;
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    private LayoutParams mLeftParams;
    private LayoutParams mTitleParams;

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        mLeftTextColor = mTypedArray.getColor(R.styleable.TopBar_leftTextColor, 0);
        mLeftBackground = mTypedArray.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftText = mTypedArray.getString(R.styleable.TopBar_leftText);

        mRightTextColor = mTypedArray.getColor(R.styleable.TopBar_rightTextColor, 0);
        mRightBackground = mTypedArray.getDrawable(R.styleable.TopBar_rightBackground);
        mRightText = mTypedArray.getString(R.styleable.TopBar_rightText);

        mTitleTextSize = mTypedArray.getDimensionPixelOffset(R.styleable.TopBar_titleTextSize, 10);
        mTitleTextColor = mTypedArray.getColor(R.styleable.TopBar_titleTextColor, 0);
        mTitle = mTypedArray.getString(R.styleable.TopBar_title);

        mTypedArray.recycle();

        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);

        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftText);

        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);

        mTitleView.setText(mTitle);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        mLeftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(ALIGN_PARENT_LEFT,TRUE);
        addView(mLeftButton, mLeftParams);

        mRightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRightParams.addRule(ALIGN_PARENT_RIGHT,TRUE);
        addView(mRightButton, mRightParams);

        mTitleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(CENTER_IN_PARENT,TRUE);
        addView(mTitleView, mTitleParams);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
