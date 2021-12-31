package com.jiang.jroundview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以方便地生成圆角矩形/圆形 {@link android.graphics.drawable.Drawable}。
 * <p>
 * <ul>
 * <li>使用 {@link #setBgData(ColorStateList)} 设置背景色。</li>
 * <li>使用 {@link #setStrokeData(int, ColorStateList)} 设置描边大小、描边颜色。</li>
 * <li>使用 {@link #setIsRadiusAdjustBounds(boolean)} 设置圆角大小是否自动适应为 {@link android.view.View} 的高度的一半, 默认为 true。</li>
 * </ul>
 */
class JrvDrawable extends GradientDrawable {

    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private boolean mRadiusAdjustBounds = true;
    private ColorStateList mFillColors;
    private int mStrokeWidth = 0;
    private ColorStateList mStrokeColors;
    private int[] mGradientColors;

    /**
     * 设置按钮的背景色(只支持纯色,不支持 Bitmap 或 Drawable)
     */
    public void setBgData(@Nullable ColorStateList colors) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setColor(colors);
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
    }

    public void setGradient(List<ColorStateList> list) {
        if (list != null && list.size() > 0) {
            int[] colors = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ColorStateList color = list.get(i);
                colors[i] = color.getColorForState(getState(), 0);
            }
            setGradient(colors);
        }
    }

    /**
     * 设置渐变色
     */
    public void setGradient(@ColorInt int[] colors) {
        mGradientColors = colors;
        if (colors != null && colors.length > 0) {
            setColors(colors);
        }
    }

    /**
     * 设置按钮的描边粗细和颜色
     */
    public void setStrokeData(int width, @Nullable ColorStateList colors) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        if (mRadiusAdjustBounds) {
            // 不知道为什么调用 invalidateSelf 无效，不触发onBoundsChange，只能手动调用
            onBoundsChange(getBounds());
        } else {
            setRadius(0);
        }
    }

    /**
     * 设置统一圆角
     */
    public void setRadius(float radius) {
        if (radius >= 0) {
            mRadiusAdjustBounds = false;
            setCornerRadius(radius);
        }
    }

    /**
     * 分别设置各个圆角
     */
    public void setRadius(float radiusTopLeft, float radiusTopRight, float radiusBottomLeft, float radiusBottomRight) {
        if (radiusTopLeft > 0 || radiusTopRight > 0 || radiusBottomLeft > 0 || radiusBottomRight > 0) {
            float[] radii = new float[]{
                    radiusTopLeft, radiusTopLeft,
                    radiusTopRight, radiusTopRight,
                    radiusBottomLeft, radiusBottomLeft,
                    radiusBottomRight, radiusBottomRight
            };
            mRadiusAdjustBounds = false;
            setCornerRadii(radii);
        }
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        Log.e("JRV", "onStateChange");
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
        boolean result = (mFillColors != null && mFillColors.isStateful())
                || (mStrokeColors != null && mStrokeColors.isStateful())
                || (mGradientColors != null && mGradientColors.length > 0)
                || super.isStateful();
        Log.e("JRV", "isStateful  " + result);
        return result;
    }

    @Override
    protected void onBoundsChange(Rect r) {
        Log.e("JRV", "onBoundsChange");
        super.onBoundsChange(r);
        if (mRadiusAdjustBounds) {
            // 修改圆角为短边的一半
            setCornerRadius(Math.min(r.width(), r.height()) / 2);
        }
    }

    public static JrvDrawable fromAttributeSet(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JrvTextView, defStyleAttr, 0);
        //背景色
        ColorStateList colorBg = typedArray.getColorStateList(R.styleable.JrvTextView_jrv_backgroundColor);
        //渐变起始色
        ColorStateList gradientColorStart = typedArray.getColorStateList(R.styleable.JrvTextView_jrv_gradientColorStart);
        //渐变起始色
        ColorStateList gradientColorMiddle = typedArray.getColorStateList(R.styleable.JrvTextView_jrv_gradientColorMiddle);
        //渐变结束色
        ColorStateList gradientColorEnd = typedArray.getColorStateList(R.styleable.JrvTextView_jrv_gradientColorEnd);
        //渐变方向
        int orientationCode = typedArray.getInt(R.styleable.JrvTextView_jrv_gradientOrientation, 0);
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

        List<ColorStateList> gradientColors = new ArrayList<>();
        if (gradientColorStart != null) {
            gradientColors.add(gradientColorStart);
        }
        if (gradientColorMiddle != null) {
            gradientColors.add(gradientColorMiddle);
        }
        if (gradientColorEnd != null) {
            gradientColors.add(gradientColorEnd);
        }

        JrvDrawable bg = new JrvDrawable();
        //背景色
        if (gradientColors.size() > 0) {//优先判断渐变
            Orientation orientation;
            switch (orientationCode) {
                case 1:
                    orientation = Orientation.TR_BL;
                    break;
                case 2:
                    orientation = Orientation.RIGHT_LEFT;
                    break;
                case 3:
                    orientation = Orientation.BR_TL;
                    break;
                case 4:
                    orientation = Orientation.BOTTOM_TOP;
                    break;
                case 5:
                    orientation = Orientation.BL_TR;
                    break;
                case 6:
                    orientation = Orientation.LEFT_RIGHT;
                    break;
                case 7:
                    orientation = Orientation.TL_BR;
                    break;
                default:
                    orientation = Orientation.TOP_BOTTOM;
                    break;
            }
            bg.setGradient(gradientColors);
            bg.setOrientation(orientation);
        } else {
            bg.setBgData(colorBg);
        }
        //边框
        bg.setStrokeData(borderWidth, colorBorder);
        //圆角
        if (mRadiusTopLeft > 0 || mRadiusTopRight > 0 || mRadiusBottomLeft > 0 || mRadiusBottomRight > 0) {
            //优先处理自定义圆角大小
            float[] radii = new float[]{
                    mRadiusTopLeft, mRadiusTopLeft,
                    mRadiusTopRight, mRadiusTopRight,
                    mRadiusBottomRight, mRadiusBottomRight,
                    mRadiusBottomLeft, mRadiusBottomLeft
            };
            bg.setCornerRadii(radii);
        } else if (mRadius > 0) {
            //其次处理统一圆角大小
            bg.setCornerRadius(mRadius);
        } else {
            //最后处理自适应半圆圆角
            bg.setIsRadiusAdjustBounds(isRadiusAdjustBounds);
        }
        return bg;
    }
}