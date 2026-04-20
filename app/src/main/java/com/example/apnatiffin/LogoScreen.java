package com.example.apnatiffin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LogoScreen extends AppCompatActivity {
    ImageView ap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_screen);

        ap = findViewById(R.id.ap);

        // Zoom in aur zoom out animation
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1.2f, 0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        ap.startAnimation(scaleAnimation);


        new Handler().postDelayed(() -> {
            Intent in=new Intent(LogoScreen.this,LoginPage.class);
            startActivity(in);
            finish();
        },3000);
    }
}