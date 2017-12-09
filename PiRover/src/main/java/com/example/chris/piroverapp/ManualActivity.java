package com.example.chris.piroverapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


import static java.lang.System.exit;

public class ManualActivity extends AppCompatActivity {
    private android.os.Handler handler = new android.os.Handler();

    ImageButton forwardButton, reverseButton, leftButton, rightButton;
    Button dc;


    BluetoothAdapter blueAdapter;
    BluetoothSocket blueSocket;
    BluetoothDevice blueDevice;

    OutputStream outputStream;
    InputStream inputStream;

    String a;
    String message="";
    String colour;
    boolean connected;
    boolean runThreadRunning = false;
    boolean runThreadStop = false;
    ConstraintLayout cons;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        dc = (Button) findViewById(R.id.buttonDc);
        final Button connect = (Button) findViewById(R.id.buttonBlue);


        SharedPreferences prefs = getSharedPreferences("bgColour", MODE_PRIVATE);
        colour = prefs.getString("colour", "WHITE");

        cons = (ConstraintLayout)findViewById(R.id.constraint);
        if(colour.equals("GREEN")){
            cons.setBackgroundColor(Color.GREEN);
        }
        else if(colour.equals("YELLOW")){
            cons.setBackgroundColor(Color.YELLOW);
        }
        else if(colour.equals("BLUE")){
            cons.setBackgroundColor(Color.BLUE);
        }







        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //a = "raspberrypi";
                a = getString(R.string.raspberrypiID);
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


            }
        });


        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connected) {
                    Toast.makeText(getBaseContext(), R.string.closing, Toast.LENGTH_SHORT).show();
                    String stop = getString(R.string.stopButton);
                    sendMsg(stop);
                    exit(0);

                    connected = false;
                } else {
                    Toast.makeText(getBaseContext(), R.string.noconnection, Toast.LENGTH_SHORT).show();

                }
            }
        });


        forwardButton = (ImageButton) findViewById(R.id.forwardButton);
        forwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(connected){
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        String w = getString(R.string.forward);

                        handleRunDown(w);
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        handleRunUp();
                        return true;
                    }
                }}

                return false;
            }

        });


        /*forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected)
                    sendMsg("w");

                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                int text = R.string.m_forward;
                Toast toast = Toast.makeText(context,text,duration);
                //toast.show();
            }
        }); */

        reverseButton = (ImageButton) findViewById(R.id.reverseButton);
        reverseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(connected){
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN: {

                            String s = getString(R.string.reverse);

                            handleRunDown(s);
                            return true;
                        }
                        case MotionEvent.ACTION_UP: {
                            handleRunUp();
                            return true;
                        }
                    }}

                return false;
            }

        });
       /* reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connected)
                    sendMsg("s");
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                int text = R.string.m_reverse;
                Toast toast = Toast.makeText(context, text, duration);
                //toast.show();
            }
        });*/

        leftButton = (ImageButton) findViewById(R.id.leftButton);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(connected){
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN: {

                            String a = getString(R.string.left);

                            handleRunDown(a);
                            return true;
                        }
                        case MotionEvent.ACTION_UP: {
                            handleRunUp();
                            return true;
                        }
                    }}

                return false;
            }

        });
       /* leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connected)
                    sendMsg("a");
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                int text = R.string.m_left;
                Toast toast = Toast.makeText(context, text, duration);
                //toast.show();
            }
        });*/

        rightButton = (ImageButton) findViewById(R.id.rightButton);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(connected){
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN: {

                            String d = getString(R.string.right);

                            handleRunDown(d);
                            return true;
                        }
                        case MotionEvent.ACTION_UP: {
                            handleRunUp();
                            return true;
                        }
                    }}

                return false;
            }

        });
      /* rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connected)
                    sendMsg("d");
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                int text = R.string.m_right;
                Toast toast = Toast.makeText(context, text, duration);
                //toast.show();
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences prefs = getSharedPreferences("bgColour", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String colourSelected = "";

        //cons = (ConstraintLayout)findViewById(R.id.constraint);
        switch (item.getItemId()) {

            case R.id.barGreen:
                cons.setBackgroundColor(Color.GREEN);
                colourSelected = "GREEN";
                editor.putString("colour", colourSelected);
                editor.commit();

                return true;
            case R.id.barYellow:
                cons.setBackgroundColor(Color.YELLOW);
                colourSelected = "YELLOW";
                editor.putString("colour", colourSelected);
                editor.commit();
                return true;
            case R.id.barBlue:
                cons.setBackgroundColor(Color.BLUE);
                colourSelected = "BLUE";
                editor.putString("colour", colourSelected);
                editor.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

   /* SharedPreferences prefs = getSharedPreferences("bgColour", MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    String colourSelected = "";


    private void greenBar() {
        colourSelected = "GREEN";
        editor.putString("colour", colourSelected);
        editor.commit();
    }

    private void yellowBar() {

        colourSelected = "YELLOW";
        editor.putString("colour", colourSelected);
        editor.commit();
    }

    private void blueBar() {
        colourSelected = "BLUE";
        editor.putString("colour", colourSelected);
        editor.commit();
    }*/

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
        String serial = getString(R.string.register_serialButton1);
        //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
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

    }

    void sendMsg(String msg1) {
        try {
            outputStream.write(msg1.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            //exit(0);
        }
    }

    private void handleRunDown(String mes) {
        if (!runThreadRunning)
            startRunThread(mes);
    }

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

    private void handleRunUp(){
        runThreadStop = true;
    }

   /* private void keepSending(String message){
        sendMsg(message);
    }*/

}

