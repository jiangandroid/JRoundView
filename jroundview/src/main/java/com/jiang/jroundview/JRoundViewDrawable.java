package com.jiang.jroundview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.Nullable;

import android.util.AttributeSet;

/**
 * 可以方便地生成圆角矩形/圆形 {@link android.graphics.drawable.Drawable}。
 * <p>
 * <ul>
 * <li>使用 {@link #setBgData(ColorStateList)} 设置背景色。</li>
 * <li>使用 {@link #setStrokeData(int, ColorStateList)} 设置描边大小、描边颜色。</li>
 * <li>使用 {@link #setIsRadiusAdjustBounds(boolean)} 设置圆角大小是否自动适应为 {@link android.view.View} 的高度的一半, 默认为 true。</li>
 * </ul>
 */
public class JRoundViewDrawable extends GradientDrawable {

    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private boolean mRadiusAdjustBounds = true;
    private ColorStateList mFillColors;
    private int mStrokeWidth = 0;
    private ColorStateList mStrokeColors;


    private boolean hasNativeStateListAPI() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 设置按钮的背景色(只支持纯色,不支持 Bitmap 或 Drawable)
     */
    public void setBgData(@Nullable ColorStateList colors) {
        if (hasNativeStateListAPI()) {
            super.setColor(colors);
        } else {
            mFillColors = colors;
            final int currentColor;
            if (colors == null) {
                currentColor = Color.TRANSPARENT;
            } else {
                currentColor = colors.getColorForState(getState(), 0);
            }
            setColor(currentColor);
        }
//        GradientDrawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM,new int[]{Color.RED,Color.YELLOW});
    }

    /**
     * 设置按钮的描边粗细和颜色
     */
    public void setStrokeData(int width, @Nullable ColorStateList colors) {
        if (hasNativeStateListAPI()) {
            super.setStroke(width, colors);
        } else {
            mStrokeWidth = width;
            mStrokeColors = colors;
            final int currentColor;
            if (colors == null) {
                currentColor = Color.TRANSPARENT;
            } else {
                currentColor = colors.getColorForState(getState(), 0);
            }
            setStroke(width, currentColor);
        }
    }

    /**
     * 设置圆角大小是否自动适应为 View 的高度的一半
     */
    public void setIsRadiusAdjustBounds(boolean isRadiusAdjustBounds) {
        mRadiusAdjustBounds = isRadiusAdjustBounds;
    }

    public void setRadius(float radius) {
        if (radius >= 0) {
            setCornerRadius(radius);
            setIsRadiusAdjustBounds(false);
        }
    }

    public void setRadius(float radiusTopLeft, float radiusTopRight, float radiusBottomLeft, float radiusBottomRight) {
        if (radiusTopLeft > 0 || radiusTopRight > 0 || radiusBottomLeft > 0 || radiusBottomRight > 0) {
            float[] radii = new float[]{
                    radiusTopLeft, radiusTopLeft,
                    radiusTopRight, radiusTopRight,
                    radiusBottomLeft, radiusBottomLeft,
                    radiusBottomRight, radiusBottomRight
            };
            setCornerRadii(radii);
            setIsRadiusAdjustBounds(false);
        }
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        boolean superRet = super.onStateChange(stateSet);
        if (mFillColors != null) {
            int color = mFillColors.getColorForState(stateSet, 0);
            setColor(color);
            superRet = true;
        }
        if (mStrokeColors != null) {
            int color = mStrokeColors.getColorForState(stateSet, 0);
            setStroke(mStrokeWidth, color);
            superRet = true;
        }
        return superRet;
    }

    @Override
    public boolean isStateful() {
        return (mFillColors != null && mFillColors.isStateful())
                || (mStrokeColors != null && mStrokeColors.isStateful())
                || super.isStateful();
    }

    @Override
    protected void onBoundsChange(Rect r) {
        super.onBoundsChange(r);
        if (mRadiusAdjustBounds) {
            // 修改圆角为短边的一半
            setCornerRadius(Math.min(r.width(), r.height()) / 2);
        }
    }

    public static JRoundViewDrawable fromAttributeSet(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JrvTextView, defStyleAttr, 0);
        //背景色
        ColorStateList colorBg = typedArray.getColorStateList(R.styleable.JrvTextView_jrv_backgroundColor);
        //边框颜色
        ColorStateList colorBorder = typedArray.getColorStateList(R.styleable.JrvTextView_jrv_borderColor);
        //边框宽
        int borderWidth = typedArray.getDimensionPixelSize(R.styleable.JrvTextView_jrv_borderWidth, 0);
        //是否自适应圆角，即半圆
        boolean isRadiusAdjustBounds = typedArray.getBoolean(R.styleable.JrvTextView_jrv_isRadiusAdjustBounds, false);
        //圆角
        int mRadius = typedArray.getDimensionPixelSize(R.styleable.JrvTextView_jrv_radius, 0);
        //左上圆角
        int mRadiusTopLeft = typedArray.getDimensionPixelSize(R.styleable.JrvTextView_jrv_radiusTopLeft, 0);
        //右上圆角
        int mRadiusTopRight = typedArray.getDimensionPixelSize(R.styleable.JrvTextView_jrv_radiusTopRight, 0);
        //左下圆角
        int mRadiusBottomLeft = typedArray.getDimensionPixelSize(R.styleable.JrvTextView_jrv_radiusBottomLeft, 0);
        //右下圆角
        int mRadiusBottomRight = typedArray.getDimensionPixelSize(R.styleable.JrvTextView_jrv_radiusBottomRight, 0);
        typedArray.recycle();

        JRoundViewDrawable bg = new JRoundViewDrawable();
        bg.setBgData(colorBg);
        bg.setStrokeData(borderWidth, colorBorder);
        if (mRadiusTopLeft > 0 || mRadiusTopRight > 0 || mRadiusBottomLeft > 0 || mRadiusBottomRight > 0) {
            float[] radii = new float[]{
                    mRadiusTopLeft, mRadiusTopLeft,
                    mRadiusTopRight, mRadiusTopRight,
                    mRadiusBottomRight, mRadiusBottomRight,
                    mRadiusBottomLeft, mRadiusBottomLeft
            };
            bg.setCornerRadii(radii);
            isRadiusAdjustBounds = false;
        } else {
            bg.setCornerRadius(mRadius);
            if (mRadius > 0) {
                isRadiusAdjustBounds = false;
            }
        }
        bg.setIsRadiusAdjustBounds(isRadiusAdjustBounds);
        return bg;
    }
}