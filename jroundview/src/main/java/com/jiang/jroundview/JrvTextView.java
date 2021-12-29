package com.jiang.jroundview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

import android.util.AttributeSet;

/**
 * 使按钮能方便地指定圆角、边框颜色、边框粗细、背景色
 * <p>
 * 注意: 因为该控件的圆角采用 View 的 background 实现, 所以与原生的 <code>android:background</code> 有冲突。
 * <ul>
 * <li>如果在 xml 中用 <code>android:background</code> 指定 background, 该 background 不会生效。</li>
 * <li>如果在该 View 构造完后用 {@link #setBackgroundResource(int)} 等方法设置背景, 该背景将覆盖圆角效果。</li>
 * </ul>
 * </p>
 * <p>
 * 如需在 xml 中指定圆角、边框颜色、边框粗细、背景色等值,采用 xml 属性 {@link com.jiang.jroundview.R.styleable#JrvTextView}
 * </p>
 * <p>
 * 如需在 Java 中指定以上属性, 需要通过 {@link #getBackground()} 获取 {@link JRoundViewDrawable} 对象,
 * 然后使用 {@link JRoundViewDrawable} 提供的方法进行设置。
 *
 * <h2>Usage</h2>
 * 在xml中使用:
 * <pre>
 *
 * com.jiang.jroundview.JRoundTextView
 *                     android:layout_width="match_parent"
 *                     android:layout_height="30dp"
 *                     android:background="@color/pure_white"
 *                     android:gravity="center"
 *                     android:id="@+id/clickBtn"
 *                     app:jrv_backgroundColor="@color/pure_white"
 *                     app:jrv_borderColor="@color/red_fa3246"
 *                     app:jrv_borderWidth="1dp"
 *
 * </pre>
 * 在代码中使用:
 * <pre class="prettyprint">
 *  Button myButton = findViewById(R.id.my_button);
 *  myButton.setYtRadius(30).setYtBorderWidthColor(20,Color.GREEN);
 * </pre>
 * </p>
 * <p>
 *
 * @see JRoundViewDrawable
 * </p>
 */

public class JrvTextView extends AppCompatTextView implements JrvInterface<JrvTextView> {

    public JrvTextView(Context context) {
        this(context, null);
    }

    public JrvTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.JrvStyle);
    }

    public JrvTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        JRoundViewDrawable bg = JRoundViewDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        JrvHelper.setBackgroundKeepingPadding(this, bg);
    }

    /**
     * 设置背景色
     *
     * @param color 颜色值
     */
    @Override
    public JrvTextView setJrvBackgroundColor(@ColorInt int color) {
        Drawable background = getBackground();
        if (background instanceof JRoundViewDrawable) {
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            ((JRoundViewDrawable) background).setBgData(colorStateList);
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
    public JrvTextView setJrvBorderWidthColor(int width, @ColorInt int color) {
        Drawable background = getBackground();
        if (background instanceof JRoundViewDrawable) {
            ColorStateList colorStateList = ColorStateList.valueOf(color);
            ((JRoundViewDrawable) background).setStrokeData(width, colorStateList);
        }
        return this;
    }

    /**
     * 设置 是否自适应圆角
     *
     * @param isRadiusAdjustBounds 是否自适应圆角
     */
    @Override
    public JrvTextView setJrvIsRadiusAdjustBounds(boolean isRadiusAdjustBounds) {
        Drawable background = getBackground();
        if (background instanceof JRoundViewDrawable) {
            ((JRoundViewDrawable) background).setIsRadiusAdjustBounds(isRadiusAdjustBounds);
        }
        return this;
    }

    /**
     * 设置圆角大小
     *
     * @param radius 单位是px
     */
    @Override
    public JrvTextView setJrvRadius(float radius) {
        Drawable background = getBackground();
        if (background instanceof JRoundViewDrawable) {
            ((JRoundViewDrawable) background).setRadius(radius);
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
    public JrvTextView setJrvRadius(float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        Drawable background = getBackground();
        if (background instanceof JRoundViewDrawable) {
            ((JRoundViewDrawable) background).setRadius(topLeftRadius,topRightRadius,bottomLeftRadius,bottomRightRadius);
        }
        return this;
    }
}