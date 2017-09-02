package com.flyingkite.pager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyingkite.dotprogressdrawablesample.R;

public class CyclicTextPagerAdapter extends CyclicPagerAdapter {

    @Override
    public int getPageCount() {
        return 5;
    }

    @Override
    public Object instantiatePageItem(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_text, parent, false);
        TextView t = (TextView) v.findViewById(R.id.itsText);
        t.setText("Cyclic #" + position);
        parent.addView(v);
        return v;
    }

    @Override
    public void destroyPageItem(ViewGroup parent, int position, Object object) {
        parent.removeView((View) object);
    }

}
