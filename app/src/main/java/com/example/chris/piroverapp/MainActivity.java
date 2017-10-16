package com.example.chris.piroverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button automaticconf;
    Button manualconf;

    TextView nameText;
    TextView contentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent =getIntent();

        String name = intent.getStringExtra("name");
        String content = intent.getStringExtra("content");

        nameText = (TextView)findViewById(R.id.nameText);
        contentText = (TextView)findViewById(R.id.serialText);

        nameText.setText(name);
        contentText.setText(content);






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
