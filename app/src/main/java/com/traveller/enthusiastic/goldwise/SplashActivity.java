package com.traveller.enthusiastic.goldwise;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();

    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;

    private void StartAnimations() {
        Animation multiAnim = AnimationUtils.loadAnimation(this, R.anim.animate);
        //Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        ImageView iv = (ImageView) findViewById(R.id.splash_img);

        iv.clearAnimation();
        //l.startAnimation(anim);
        iv.startAnimation(multiAnim);
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    do {
                        sleep(100);
                        waited += 450;
                    }while (waited < 4500);
                        startHomeActivity();


                    SplashActivity.this.finish();

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashActivity.this.finish();
                }

            }
        };
        splashTread.start();

    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

