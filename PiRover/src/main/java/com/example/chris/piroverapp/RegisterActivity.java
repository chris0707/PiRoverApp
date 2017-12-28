package com.example.chris.piroverapp;
/*PiNivea

 */
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    String content ="";
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerB = (Button)findViewById(R.id.registerButton);

        final EditText editName = (EditText)findViewById(R.id.editName);
        final EditText editUser = (EditText)findViewById(R.id.editUser);
        final EditText editPass = (EditText)findViewById(R.id.editPassword);
        text1 =(TextView)findViewById(R.id.textView6);


        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editName.getText().toString();
                final String username = editUser.getText().toString();
                final String password = editPass.getText().toString();
                final String content = text1.getText().toString();
                if(name.isEmpty() || username.isEmpty() || password.isEmpty() ||content.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.ThemeDialogCustom);
                    builder.setMessage("Register Failed.")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();


                }
                else{

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent); //modify if not working

                            } else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.ThemeDialogCustom);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }
                            //TextView text1 = (TextView)findViewById(R.id.textView6);
                           // String name = jsonObject.getString("test");
                            //text1.setText(name);


                        }catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(name, username, password, content, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);  }

            }
        });

}

    public void onRadioButtonClicked(View view){ //get rid of this code

        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId()){

            case R.id.serialRadio1:
                if(checked) {
                    content = "00001101-0000-1000-8000-00805f9b34fb";
                    text1.setText(content);
                }else{

                    content.isEmpty();

                }
                break;

            case R.id.serialRadio2:
                if(checked) {
                    content = "00001101-0000-1000-8000-12345test";
                    text1.setText(content);
                }else{

                    content.isEmpty();

                }
                break;





        }



    }








}
