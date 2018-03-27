package com.example.connor.hellyeah;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*Connor Birnie
S1630777
CBIRNI200
this class handles the splash view being displayed on the app
*/
public class SplashScreen extends AppCompatActivity {
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8D908D")));//grabbing the actionbar to display correct colour

        Thread splash = new Thread(){
            @Override
            public void run() {
                try {
                    //displaying the Image of the splash screen and then moving to main activity
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        splash.start();//run
    }
}
