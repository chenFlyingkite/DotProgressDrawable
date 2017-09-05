package com.flyingkite.dotprogressdrawablesample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.flyingkite.logs.Logs;
import com.flyingkite.util.Say;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] ids = {R.id.goDots, R.id.goPager};
        for (int i : ids) {
            findViewById(i).setOnClickListener(click);
        }
        int callId = R.id.goPager;
        //findViewById(callId).callOnClick();
        Logs.e("Hi, this is from QQ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Say.Log("Main resume");
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.goDots:
                    startActivity(new Intent(MainActivity.this, DotActivity.class));
                    break;
                case R.id.goPager:
                    startActivity(new Intent(MainActivity.this, CircularViewPagerActivity.class));
                    break;
            }
        }
    };
}
