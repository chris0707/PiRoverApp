package pirover.pinivea.chris.piroverapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chris.piroverapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewLogin extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    private TextView register, loginGuest;
    private RequestQueue requestQueue;
    private static final String URL1 = "http://thebarebarzz.xyz/LoginCheck.php";

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login);

        username = (EditText)findViewById(R.id.etUser);
        password = (EditText)findViewById(R.id.etPass);
        login = (Button)findViewById(R.id.loginButton);
        register = (TextView) findViewById(R.id.registerText);
        loginGuest = (TextView) findViewById(R.id.offlineText);

        requestQueue = Volley.newRequestQueue(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username1 = username.getText().toString();
                request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if(jsonObject.names().get(0).equals("success")){

                                Toast.makeText(getApplicationContext(),"Success! " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                String name = jsonObject.getString(getResources().getString(R.string.intent_putName));
                               // String content = jsonObject.getString(getResources().getString(R.string.intent_putContent));

                                Intent intent = new Intent(NewLogin.this, NavigationDrawer.class);
                                intent.putExtra(getResources().getString(R.string.intent_putName), name);
                                intent.putExtra(getResources().getString(R.string.intent_putUsername), username1);
                                //intent.putExtra(getResources().getString(R.string.intent_putContent), content);
                                startActivity(intent);

                               // startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
                            }else {
                                Toast.makeText(getApplicationContext(),"Error. " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("username", username.getText().toString());
                        hashMap.put("password", password.getText().toString());

                        return hashMap;

                    }
                };
                requestQueue.add(request);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewRegister.class));
            }
        });

        loginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guestName = getResources().getString(R.string.guest_name);
                String guestSerial = getResources().getString(R.string.register_serialButton1);
                Intent intent = new Intent(NewLogin.this, NavigationDrawer.class);
                intent.putExtra(getResources().getString(R.string.intent_putName), guestName);
                intent.putExtra(getResources().getString(R.string.intent_putContent), guestSerial);
                startActivity(intent);
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
