package com.jtl.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setCustomDensity(this,this.getApplication(),360);
        setContentView(R.layout.activity_main);
    }
    private static float sNoncompatDensity;
    private static float sNoncompactScaledDensity;
    // 硬件是px为默认单位的。dp只是google的计量方式。因此只要该表dp和px之间的映射关系即可实现屏幕适配
    public void setCustomDensity(Activity activity, Application application,float uiSize){
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0){
            sNoncompatDensity = displayMetrics.density;
            sNoncompactScaledDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig!=null && newConfig.fontScale>0){
                        sNoncompactScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        // 求出宽为UI设计图大小时，Density，DPI，以及ScaledDensity的大小
        float targetDensity = displayMetrics.widthPixels/uiSize;//按照UI设计图设计时的Density的值。此处为360
        float targetScaledDensity = targetDensity * (sNoncompactScaledDensity/sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);
        // 修改系统Application中的默认大小
        displayMetrics.density = targetDensity;
        displayMetrics.scaledDensity = targetScaledDensity;
        displayMetrics.densityDpi = targetDensityDpi;
        // 修改Activity中的默认大小
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}