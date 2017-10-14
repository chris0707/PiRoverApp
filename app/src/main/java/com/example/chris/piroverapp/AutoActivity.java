package com.example.chris.piroverapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class
AutoActivity extends AppCompatActivity {

    Button toggleButton;
    Boolean check = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        toggleButton = (Button)findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (check == Boolean.FALSE) {
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    CharSequence text = "Automode: ON";
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                    check = Boolean.TRUE;
                }

                else {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "Automode: OFF";
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                    check = Boolean.FALSE;

                }
            }
        });
    }
}
