package pirover.pinivea.chris.piroverapp;
/*PiNivea
*Christopher Albarillo N01076336
*Lawrence Puig N01033296
*Heakeme Williams N01126779
 */
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.lang.System.exit;

public class
AutoActivity extends AppCompatActivity {
    private android.os.Handler handler = new android.os.Handler();

    BluetoothAdapter blueAdapter;
    BluetoothSocket blueSocket;
    BluetoothDevice blueDevice;

    OutputStream outputStream;
    InputStream inputStream;


    Button toggleButton;
    Button connect, dc, startB, saveData, stopB;
    Boolean check = Boolean.FALSE;
    TextView viewData;

    String a;
    String message="";
    boolean connected;
    boolean runThreadRunning = false;
    boolean runThreadStop = false;

    private static final String URL = "http://thebarebarzz.xyz/UpdateCheck.php";
    private RequestQueue requestQueue;
    private StringRequest request;
    String userName;
    String currentDateTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);



        connect = (Button)findViewById(R.id.buttonBlue);
        dc = (Button)findViewById(R.id.buttonDc);
        startB =(Button)findViewById(R.id.startButton);
       // stopB = (Button)findViewById(R.id.stopButton);
        saveData = (Button) findViewById(R.id.saveDataButton);
        viewData = findViewById(R.id.viewText);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //a = "raspberrypi";
                a = getString(R.string.raspberrypiID);
                try {
                    if (isBluetoothAvailable()) {
                        try {
                            findBT();
                            Toast.makeText(getBaseContext(), R.string.trying, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(getBaseContext(), R.string.notestablished, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(), R.string.connected, Toast.LENGTH_SHORT).show();
                        connected = true;
                    } else {
                        Toast.makeText(getBaseContext(), R.string.bluetoothelse, Toast.LENGTH_SHORT).show();
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), R.string.noconnection, Toast.LENGTH_SHORT).show();

                }
            }
        });

        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connected) {
                    Toast.makeText(getBaseContext(), R.string.closing, Toast.LENGTH_SHORT).show();
                    //sendMsg("stop");
                    String stop = getString(R.string.stopButton);
                    sendMsg(stop);
                    exit(0);

                    connected = false;
                } else {
                    Toast.makeText(getBaseContext(), R.string.noconnection, Toast.LENGTH_SHORT).show();

                }
            }
        });

        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected){
                    String start = getString(R.string.startAuto);
                    sendMsg(start);
                    getMsg();


                } else{
                    Toast.makeText(AutoActivity.this, getResources().getString(R.string.toast_autoMap), Toast.LENGTH_SHORT).show();
                }
            }
        });



        requestQueue = Volley.newRequestQueue(this);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sendData = (String) viewData.getText() + currentDateTimeString;
                final String userNameString;
              /*  Intent intent = getIntent();
                Bundle bundle = intent.getExtras();

                if(bundle != null){
                    userName = bundle.getString("username");
                }*/
                currentDateTimeString = " "+DateFormat.getDateTimeInstance().format(new Date());
                userName = getIntent().getStringExtra("username2");

                //test for update database
                //viewData.setText(userName);
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                                Toast.makeText(getApplicationContext(), "Update Success! " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(getApplicationContext(), "Update Error! " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();

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
                    protected Map<String, String> getParams() throws AuthFailureError{
                        HashMap<String,  String> hashMap = new HashMap<String, String>();
                        hashMap.put("username", userName);
                        hashMap.put("content", sendData);

                        return hashMap;
                    }
                };
                requestQueue.add(request);
                

            }
        });



    /*    stopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected){
                    String stop = getString(R.string.stopAuto);
                    sendMsg(stop);

                }else{
                    Toast.makeText(AutoActivity.this, getResources().getString(R.string.toast_autoStop), Toast.LENGTH_SHORT).show();
                }
            }
        });*/


    }


    public static boolean isBluetoothAvailable() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }

    void findBT() throws IOException {

        blueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (blueAdapter == null) {
            exit(0);

        }

        if (!blueAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);

        }

        Set<BluetoothDevice> pairedDevices = blueAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {

            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals(a)) {

                    blueDevice = device;
                    break;
                }

            }

        }

        //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        String serial = getString(R.string.register_serialButton1);
        UUID uuid = UUID.fromString(serial);

        blueSocket = blueDevice.createRfcommSocketToServiceRecord(uuid);
        try {
            blueSocket.connect();

        } catch (IOException e) {
            e.printStackTrace();
            exit(0);
        } finally {
            outputStream = blueSocket.getOutputStream();
            inputStream = blueSocket.getInputStream();
        }
        //test


    }


    void sendMsg(String msg1) {
        try {
            outputStream.write(msg1.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            //exit(0);
        }
    }

    void getMsg(){
        byte[] buffer = new byte[1024];
        int bytes;
        final String obstacleText = getString(R.string.auto_dataTitle);

        try{
            bytes = inputStream.read(buffer);
            final String incomingMessage = new String(buffer, 0, bytes);
            Log.d("AutoActivity", "InputStream: " + incomingMessage);

            viewData.setText(incomingMessage);
        }catch (IOException e){
            e.printStackTrace();

        }

    }

  /*  private void handleRunDown(String mes) {
        if (!runThreadRunning)
            startRunThread(mes);
    }*/

    private void startRunThread(final String mes) {
        Thread r = new Thread() {

            @Override
            public void run() {
                try {
                    runThreadRunning = true;
                    while (!runThreadStop) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                sendMsg(mes);

                                //keepSending(message);
                            }
                        });
                        try {
                            Thread.sleep(50);

                        } catch (InterruptedException e) {
                            throw new RuntimeException("Could not wait...", e);
                        }

                    }
                } finally {
                    runThreadRunning = false;
                    runThreadStop = false;
                }
            }
        };

        r.start();
    }





}
