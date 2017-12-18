package com.example.futzm.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewAnimator;

/**
 * Created by futzm on 12/8/2017.
 */

public class CongratulateActivity extends AppCompatActivity implements MessageFragment.OnCloseClickListener {

    ImageView greetImage;
    Animation animation;
    @Override
    public void onCloseClick() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipbook_activity);
        greetImage=(ImageView)findViewById(R.id.congrat_image);
        animation= AnimationUtils.loadAnimation(this,R.anim.slide_up);
        greetImage.setAnimation(animation);
        final ViewAnimator viewAnimator=(ViewAnimator)findViewById(R.id.viewFLipper);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
            }
        },2000);
        /*
        try {
         //   Thread.sleep(7000);
        } catch (InterruptedException e) {
         //   e.printStackTrace();
        }
        */
        //AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);

    }


}
