package com.jiang.jroundview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 见 {@link JrvTextView} 与 {@link JrvDrawable}
 */
public class JrvLinearLayout extends LinearLayout implements JrvInterface<JrvLinearLayout> {

    public JrvLinearLayout(Context context) {
        this(context, null);
    }

    public JrvLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.JrvStyle);
    }

    public JrvLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        JrvDrawable bg = JrvDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        JrvHelper.setBackgroundKeepingPadding(this, bg);
    }

    /**
     * 设置背景色
     *
     * @param color 颜色值
     */
    @Override
    public JrvLinearLayout setJrvBackgroundColor(@ColorInt int color) {
        Drawable background = getBackground();
        if (background instanceof JrvDrawable) {
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            ((JrvDrawable) background).setBgData(colorStateList);
        }
        return this;
    }

    /**
     * 设置按钮的描边粗细和颜色
     *
     * @param width 边框宽度，单位是px
     * @param color 边框颜色值
     * @return this
     */
    @Override
    public JrvLinearLayout setJrvBorderWidthColor(int width, @ColorInt int color) {
        Drawable background = getBackground();
        if (background instanceof JrvDrawable) {
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            ((JrvDrawable) background).setStrokeData(width, colorStateList);
        }
        return this;
    }

    /**
     * 设置 是否自适应圆角
     *
     * @param isRadiusAdjustBounds 是否自适应圆角
     */
    @Override
    public JrvLinearLayout setJrvIsRadiusAdjustBounds(boolean isRadiusAdjustBounds) {
        Drawable background = getBackground();
        if (background instanceof JrvDrawable) {
            ((JrvDrawable) background).setIsRadiusAdjustBounds(isRadiusAdjustBounds);
        }
        return this;
    }

    /**
     * 设置圆角大小
     *
     * @param radius 单位是px
     */
    @Override
    public JrvLinearLayout setJrvRadius(float radius) {
        Drawable background = getBackground();
        if (background instanceof JrvDrawable) {
            ((JrvDrawable) background).setRadius(radius);
        }
        return this;
    }

    /**
     * 分别设置view 的四个圆角大小
     * 单位都是px
     *
     * @param topLeftRadius     左上方
     * @param topRightRadius    右上方
     * @param bottomRightRadius 右下方
     * @param bottomLeftRadius  左下方
     */
    @Override
    public JrvLinearLayout setJrvRadius(float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        Drawable background = getBackground();
        if (background instanceof JrvDrawable) {
            ((JrvDrawable) background).setRadius(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
        }
        return this;
    }

    /**
     * 渐变色
     */
    @Override
    public JrvLinearLayout setGradient(@ColorInt int[] colors) {
        if (colors != null && colors.length > 0) {
            Drawable background = getBackground();
            if (background instanceof JrvDrawable) {
                ((JrvDrawable) background).setGradient(colors);
            }
        }
        return this;
    }

    @Override
    public JrvLinearLayout setGradientOrientation(GradientDrawable.Orientation orientation) {
        Drawable background = getBackground();
        if (background instanceof JrvDrawable) {
            ((JrvDrawable) background).setOrientation(orientation);
        }
        return this;
    }
}