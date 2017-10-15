package com.example.chris.piroverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button automaticconf;
    Button manualconf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        automaticconf = (Button)findViewById(R.id.automaticbutt);
        automaticconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AutoActivity.class);
                startActivity(intent);
            }
        });

        manualconf = (Button)findViewById(R.id.manualconf);
        manualconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,ManualActivity.class);
                startActivity(intent);
            }
        });
    }
}
