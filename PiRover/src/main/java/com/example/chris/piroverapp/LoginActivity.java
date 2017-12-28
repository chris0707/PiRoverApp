package com.example.chris.piroverapp;
/*PiNivea

 */
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    TextView offlineLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginB = (Button)findViewById(R.id.loginButton);
        //final Button exitB = (Button)findViewById(R.id.exitButton);

        final EditText etUsername = (EditText)findViewById(R.id.etUser);
        final EditText etPassword = (EditText)findViewById(R.id.etPass);

        final TextView registerLink = (TextView)findViewById(R.id.registerText);
        final TextView aboutTextView;
        aboutTextView = (TextView)findViewById(R.id.aboutTextView);
        offlineLink = (TextView)findViewById(R.id.offlineText);

        aboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, NavigationDrawer.class);
                startActivity(intent);
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        offlineLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guestName = getResources().getString(R.string.guest_name);
                String guestSerial = getResources().getString(R.string.register_serialButton1);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("name", guestName);
                intent.putExtra("content", guestSerial);
                startActivity(intent);
            }
        });



       /* exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });*/

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String name = jsonResponse.getString("name");
                                String content = jsonResponse.getString("content");

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("content", content);

                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.ThemeDialogCustom);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }


                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });



    }
    public void onBackPressed() {
      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
