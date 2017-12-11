package com.example.chris.piroverapp;
/*PiNivea

 */
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    ConstraintLayout cons;

    private FloatingActionMenu fam;
    private FloatingActionButton fabChris, fabLawrence, fabCallChris;
    String colour;



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

        /*SharedPreferences prefs = getSharedPreferences("bgColour", MODE_PRIVATE);
        colour = prefs.getString("colour", "WHITE");


        cons = (ConstraintLayout)findViewById(R.id.constraint);

        if(colour.equals("GREEN")){
            cons.setBackgroundColor(Color.GREEN);
        }
        else if(colour.equals("YELLOW")){
            cons.setBackgroundColor(Color.YELLOW);
        }
        else if(colour.equals("RED")){
            cons.setBackgroundColor(Color.RED);

        }*/


        fabChris = (FloatingActionButton) findViewById(R.id.fab1);
        fabLawrence = (FloatingActionButton) findViewById(R.id.fab2);
        fabCallChris = (FloatingActionButton) findViewById(R.id.fab3);
        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);




       /* fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {

                //Opens the menu
            }
        });*/

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
        fabCallChris.setOnClickListener(onButtonClick());



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



                }else if(view == fabLawrence) {
                    Uri uri  = Uri.parse("https://github.com/n01033296");
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent1);
                }else{



                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                    }else{
                        String chrisNumber = getResources().getString(R.string.chris_number);
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+chrisNumber));
                        startActivity(intent);
                    }


                }
                fam.close(true);
            }
        };

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
