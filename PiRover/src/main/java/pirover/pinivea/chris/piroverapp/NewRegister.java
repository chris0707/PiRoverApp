package pirover.pinivea.chris.piroverapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class NewRegister extends AppCompatActivity {

    private EditText username1, password1, confirmPassword, fullname;

    private Button register;
    private RequestQueue requestQueue;
    private static final String URL2 = "http://thebarebarzz.xyz/RegisterCheck.php";

    private StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_register);

        fullname = (EditText)findViewById(R.id.editName);
        username1 = (EditText)findViewById(R.id.editUser);
        password1 = (EditText)findViewById(R.id.editPassword);
        confirmPassword = (EditText)findViewById(R.id.editConfirm);
        register = (Button)findViewById(R.id.registerButton);


        requestQueue = Volley.newRequestQueue(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = fullname.getText().toString();
                final String username = username1.getText().toString();
                final String password = password1.getText().toString();
                final String confirmP = confirmPassword.getText().toString();
                final String content = "test";


                if(name.isEmpty() || username.isEmpty() || password.isEmpty() ||content.isEmpty() || confirmP.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewRegister.this, R.style.ThemeDialogCustom);
                    builder.setMessage(R.string.Reg_Activity_set_message)
                            .setNegativeButton(R.string.set_negative, null)
                            .create()
                            .show();


                }else {

                    request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                assert jsonObject != null;
                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), "SUCCESS" + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), NewLogin.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("username", username1.getText().toString());
                            hashMap.put("password", password1.getText().toString());
                            hashMap.put("content", content);
                            hashMap.put("name", fullname.getText().toString());

                            return hashMap;

                        }
                    };
                    requestQueue.add(request);
                }
            }

        });
    }
}
