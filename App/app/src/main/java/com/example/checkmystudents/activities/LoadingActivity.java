package com.example.checkmystudents.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.checkmystudents.R;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final ImageView iv = findViewById(R.id.logo);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.loading_animation);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                finish();
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_up_in, R.anim.activity_up_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
