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

        ProgressBar p = (ProgressBar) findViewById(R.id.myProgress);
        p.setIndeterminateDrawable(new DotDrawable());
    }
}
