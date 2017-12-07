package com.example.chris.piroverapp;

import android.content.Intent;
import android.net.Uri;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import org.w3c.dom.Text;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button automaticconf;
    Button manualconf;

    TextView nameText;
    TextView contentText;

    private FloatingActionMenu fam;
    private FloatingActionButton fabChris, fabLawrence;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent =getIntent();

        String name = intent.getStringExtra("name");
        String content = intent.getStringExtra("content");

        nameText = (TextView)findViewById(R.id.nameText);
        contentText = (TextView)findViewById(R.id.serialText);

        nameText.setText(name);
        contentText.setText(content);

        fabChris = (FloatingActionButton) findViewById(R.id.fab1);
        fabLawrence = (FloatingActionButton) findViewById(R.id.fab2);
        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);

        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    showToast("Menu is opened");
                } else {
                    showToast("Menu is closed");
                }
            }
        });

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });



        fabChris.setOnClickListener(onButtonClick());
        fabLawrence.setOnClickListener(onButtonClick());




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

    private View.OnClickListener onButtonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == fabChris){
                    Uri uri  = Uri.parse("https://github.com/chris0707");
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent1);



                }else {
                    Uri uri  = Uri.parse("https://github.com/n01033296");
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent1);
                }
                fam.close(true);
            }
        };

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
