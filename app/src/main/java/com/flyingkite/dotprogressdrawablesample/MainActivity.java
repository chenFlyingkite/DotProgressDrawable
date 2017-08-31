package com.flyingkite.dotprogressdrawablesample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.flyingkite.dotdrawable.DotDrawable;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMyDrawable(R.id.myProgress);
        setMyDrawable(R.id.myProgress1);
        setMyDrawable(R.id.myProgress2);
    }

    private void setMyDrawable(int id) {
        ProgressBar p = (ProgressBar) findViewById(id);
        p.setIndeterminateDrawable(new DotDrawable());
    }

}
