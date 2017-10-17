package com.example.chris.piroverapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ManualActivity extends AppCompatActivity {

    Button forwardButton;
    Button reverseButton;
    Button leftButton;
    Button rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        forwardButton = (Button)findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Moving: Forward";
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }
        });

        reverseButton = (Button)findViewById(R.id.reverseButton);
        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Moving: Reverse";
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }
        });

        leftButton = (Button)findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Moving: Left";
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }
        });

        rightButton = (Button)findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Moving: Right";
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }
        });

    }
}
