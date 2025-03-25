package com.example.saebut2_s4.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.saebut2_s4.R;
import com.example.saebut2_s4.ui.AcceuilActivity;
import com.example.saebut2_s4.ui.ConnectionActivity;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBarHorizontal;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Trouver les ProgressBars
        progressBarHorizontal = findViewById(R.id.progress_horizontal);

        // Thread pour animer la barre de chargement
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBarHorizontal.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(50); // Vitesse de remplissage
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Une fois la barre remplie, on passe à MainActivity
                Intent intent = new Intent(SplashActivity.this, ConnectionActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
