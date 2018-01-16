package pirover.pinivea.chris.piroverapp;
/*PiNivea
*Christopher Albarillo N01076336
*Lawrence Puig N01033296
*Heakeme Williams N01126779
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
import com.example.chris.piroverapp.R;

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
        offlineLink = (TextView)findViewById(R.id.offlineText);

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
                Intent intent = new Intent(LoginActivity.this, NavigationDrawer.class);
                intent.putExtra(getResources().getString(R.string.intent_putName), guestName);
                intent.putExtra(getResources().getString(R.string.intent_putContent), guestSerial);
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
                            boolean success = jsonResponse.getBoolean(getResources().getString(R.string.intent_putSuccess));

                            if(success){
                                String name = jsonResponse.getString(getResources().getString(R.string.intent_putName));
                                String content = jsonResponse.getString(getResources().getString(R.string.intent_putContent));

                                Intent intent = new Intent(LoginActivity.this, NavigationDrawer.class);

                                intent.putExtra(getResources().getString(R.string.intent_putName), name);
                                intent.putExtra(getResources().getString(R.string.intent_putUsername), username);
                                intent.putExtra(getResources().getString(R.string.intent_putContent), content);

                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.ThemeDialogCustom);
                                builder.setMessage(R.string.set_message)
                                        .setNegativeButton(R.string.set_negative, null)
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
                int test;
            }
        });



    }
    public void onBackPressed() {

      /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ThemeDialogCustom);
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.alert_message));
        builder.setPositiveButton(getResources().getString(R.string.alert_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.alert_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
