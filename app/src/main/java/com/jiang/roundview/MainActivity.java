package com.jiang.roundview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jiang.jroundview.JrvTextView;

public class MainActivity extends AppCompatActivity {

    private JrvTextView vJrvTextView;
    boolean mRadiusAdjustBounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vJrvTextView = findViewById(R.id.vJrvTextView);
        final int[] colors = new int[]{Color.YELLOW, Color.RED, Color.GREEN};
        vJrvTextView.setGradient(colors);

        vJrvTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colors[0] = Color.BLACK;
                vJrvTextView.setGradient(colors);
                vJrvTextView.setGradientOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                mRadiusAdjustBounds = !mRadiusAdjustBounds;
                vJrvTextView.setJrvIsRadiusAdjustBounds(mRadiusAdjustBounds);
            }
        });
    }
}