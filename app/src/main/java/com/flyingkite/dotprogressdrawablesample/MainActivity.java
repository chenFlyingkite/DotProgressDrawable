package com.flyingkite.dotprogressdrawablesample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.flyingkite.dotdrawable.DotDrawable;
import com.flyingkite.util.Say;

public class MainActivity extends Activity {
    private ProgressBar p0;
    private ProgressBar p1;
    private ProgressBar p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p0 = (ProgressBar) findViewById(R.id.myProgress);
        p1 = (ProgressBar) findViewById(R.id.myProgress1);
        p2 = (ProgressBar) findViewById(R.id.myProgress2);

        setMyDrawable(p0);
        setMyDrawable(p1);
        setMyDrawable(p2);

        p0.setOnClickListener(new View.OnClickListener() {
            private int scale = 0;
            private int max = 4;
            @Override
            public void onClick(View v) {
                DotDrawable d = (DotDrawable) p0.getIndeterminateDrawable();
                scale = (scale + 1) % max;
                d.setDotScale(4 + scale);
                Say.Log("click change scale " + scale);
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            private int scale = 0;
            private int max = 4;
            @Override
            public void onClick(View v) {
                DotDrawable d = (DotDrawable) p1.getIndeterminateDrawable();
                scale = (scale + 1) % max;
                d.setAcceleration(1 + scale);
                Say.Log("click change acc " + scale);
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            private boolean pos = true;
            @Override
            public void onClick(View v) {
                DotDrawable d = (DotDrawable) p2.getIndeterminateDrawable();
                d.setPause(pos);
                Say.Log("click change pos " + pos);
                pos = !pos;
            }
        });

        ImageView img = (ImageView) findViewById(R.id.img);
        img.setImageDrawable(new DotDrawable());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Say.Log("resume");
    }

    private void setMyDrawable(ProgressBar p) {
        p.setIndeterminateDrawable(new DotDrawable());
    }

}
