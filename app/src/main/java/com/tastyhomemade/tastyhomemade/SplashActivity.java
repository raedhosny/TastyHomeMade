package com.tastyhomemade.tastyhomemade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent MainActivityIntent = new Intent("com.tastyhomemade.tastyhomemade.MainActivity");
                startActivity(MainActivityIntent);
                finish();
            }
        });
        t.start();

    }
}
