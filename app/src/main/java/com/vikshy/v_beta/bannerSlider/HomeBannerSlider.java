package com.vikshy.v_beta.bannerSlider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.common.base.Strings;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.vikshy.v_beta.R;
import com.vikshy.v_beta.firebase.ConfigUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Source : https://github.com/daimajia/AndroidImageSlider/wiki/Start-Using
 * Created by laxman.muttineni on 26/01/18.
 */

public class HomeBannerSlider implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private static HomeBannerSlider instance;
    private Context context;

    public HomeBannerSlider(Context context) {
        this.context = context;
    }

    public static HomeBannerSlider getInstance(Context context) {
        if (instance == null) {
            synchronized (HomeBannerSlider.class) {
                if (instance == null) {
                    instance = new HomeBannerSlider(context);
                }
            }
        }
        return instance;
    }

    public SliderLayout prepareSliderLayout(SliderLayout sliderLayout,
                                            FirebaseRemoteConfig firebaseRemoteConfig) {
        for(int i = 1; i <= ConfigUtils.MAX_BANNERS; i++){
            DefaultSliderView sliderView = new DefaultSliderView(context);
            // initialize a SliderLayout
            String bannerId = ConfigUtils.getBannerkey(i);
            if(!Strings.isNullOrEmpty(firebaseRemoteConfig.getString(bannerId))) { //todo : handle for int
                sliderView
                        .description(bannerId)
                        .image(firebaseRemoteConfig.getString(bannerId))  //todo : handle for int
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                //add your extra information
                sliderView.bundle(new Bundle());
                sliderView.getBundle().putString("extra",i + "");
                sliderLayout.addSlider(sliderView);
            }
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(firebaseRemoteConfig.getLong(ConfigUtils.BANNER_TIME));
        sliderLayout.addOnPageChangeListener(this);
        // sliderLayout.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        return sliderLayout;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Log.d("Banner Slider", "Clicked : " + slider.getBundle().get("extra"));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("Banner Slider", "Page scrolled: " + position);
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Banner Slider", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}
