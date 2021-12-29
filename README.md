# JRoundView
自定义控件实现圆角、边框、填充等；

集成：
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  dependencies {
	        implementation 'com.github.jiangandroid:JRoundView:v0.0.1'
	}
  
  
 支持控件列表：
 JrvConstraintLayout
 JrvFrameLayout
 JrvLinearLayout
 JrvRelativeLayout
 JrvTextView
 
 使用：
 
        <com.jiang.jroundview.JrvTextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="20dp"
            android:text="JrvTextView"
            android:textColor="@color/white"
            android:textSize="24dp"
            app:jrv_backgroundColor="#000000"
            app:jrv_borderColor="@color/white"
            app:jrv_borderWidth="3dp"
            app:jrv_radius="2dp"
            app:jrv_radiusBottomLeft=""
            app:jrv_radiusBottomRight=""
            app:jrv_radiusTopLeft=""
            app:jrv_radiusTopRight=""
            app:jrv_isRadiusAdjustBounds="true" />
  
