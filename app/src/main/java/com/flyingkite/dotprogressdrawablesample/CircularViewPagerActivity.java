package com.flyingkite.dotprogressdrawablesample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.flyingkite.pager.CyclicTextPagerAdapter;
import com.flyingkite.pager.TextPagerAdapter;

public class CircularViewPagerActivity extends Activity {
    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new TextPagerAdapter());

        ViewPager pager2 = (ViewPager) findViewById(R.id.pager2);
        pager2.setAdapter(new CyclicTextPagerAdapter());

    }
}
