package com.example.connect3;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String coinColor = "";
    Integer coinC = 0;
    Intent intent;
    public void select(View view) {
        ImageView coin = (ImageView) view;
        coinColor = coin.getTag().toString();
        Log.i("tag", coinColor);

        if (coinColor.equals("red")) {
            coinC = 1;
        } else if (coinColor.equals("yellow")) {
            coinC = 2;
        }
        Log.i("info", Integer.toString(coinC));
        intent.putExtra("coin", coinC);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(MainActivity.this, Intro.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}