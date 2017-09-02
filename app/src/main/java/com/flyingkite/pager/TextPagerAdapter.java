package com.flyingkite.pager;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyingkite.dotprogressdrawablesample.R;

public class TextPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_text, parent, false);
        TextView t = (TextView) v.findViewById(R.id.itsText);
        t.setText("#" + position);
        parent.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup parent, int position, Object object) {
        parent.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }
}
