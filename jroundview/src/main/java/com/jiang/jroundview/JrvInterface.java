package com.jiang.jroundview;

import androidx.annotation.ColorInt;

/**
 * @author jiangjunjie01
 * Date： 2021/12/29
 */
interface JrvInterface<T> {

    /**
     * 设置背景色
     *
     * @param color 颜色值
     */
    T setJrvBackgroundColor(@ColorInt int color);


    /**
     * 设置按钮的描边粗细和颜色
     *
     * @param width 边框宽度，单位是px
     * @param color 边框颜色值
     * @return this
     */
    T setJrvBorderWidthColor(int width, @ColorInt int color);

    /**
     * 设置 是否自适应圆角
     *
     * @param isRadiusAdjustBounds 是否自适应圆角
     */
    T setJrvIsRadiusAdjustBounds(boolean isRadiusAdjustBounds);

    /**
     * 设置圆角大小
     *
     * @param radius 单位是px
     */
    T setJrvRadius(float radius);

    /**
     * 分别设置view 的四个圆角大小
     * 单位都是px
     *
     * @param topLeftRadius     左上方
     * @param topRightRadius    右上方
     * @param bottomRightRadius 右下方
     * @param bottomLeftRadius  左下方
     */
    T setJrvRadius(float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius);
}