package com.example.tonyngeno.hairsalke;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   new Handler().postDelayed(new Runnable() {
       @Override
       public void run() {
           final Intent mainIntent = new Intent(MainActivity.this , Register.class);
           MainActivity.this.startActivity(mainIntent);
           MainActivity.this.finish();
       }
   }, 3000);

    }
}
