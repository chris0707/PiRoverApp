package pirover.pinivea.chris.piroverapp;
/*PiNivea
*Christopher Albarillo N01076336
*Lawrence Puig N01033296
*Heakeme Williams N01126779
 */
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

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    String content ="";
    TextView text1;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerB = (Button)findViewById(R.id.registerButton);

        final EditText editName = (EditText)findViewById(R.id.editName);
        final EditText editUser = (EditText)findViewById(R.id.editUser);
        final EditText editPass = (EditText)findViewById(R.id.editPassword);
        confirmPassword = (EditText) findViewById(R.id.editConfirm);


        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editName.getText().toString();
                final String username = editUser.getText().toString();
                final String password = editPass.getText().toString();
                final String content = "test";
                final String confirmP = confirmPassword.getText().toString();

                if(name.isEmpty() || username.isEmpty() || password.isEmpty() ||content.isEmpty() || confirmP.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.ThemeDialogCustom);
                    builder.setMessage(R.string.Reg_Activity_set_message)
                            .setNegativeButton(R.string.set_negative, null)
                            .create()
                            .show();


                }
                else{


                if(Objects.equals(password, confirmP)){
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
                                builder.setMessage(R.string.Reg_Activity_set_message)
                                        .setNegativeButton(R.string.set_negative, null)
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
                queue.add(registerRequest);}

                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.ThemeDialogCustom);
                    builder.setMessage(R.string.Reg_Activity_set_reason)
                            .setNegativeButton(R.string.set_negative, null)
                            .create()
                            .show();

                }

                }

            }
        });


}








}
