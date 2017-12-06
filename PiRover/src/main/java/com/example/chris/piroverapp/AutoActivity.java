package com.example.chris.piroverapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    Boolean check = Boolean.FALSE;

    String a;
    String message="";
    boolean connected;
    boolean runThreadRunning = false;
    boolean runThreadStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        toggleButton = (Button)findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (check == Boolean.FALSE) {
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    CharSequence text = "Automode: ON";
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                    check = Boolean.TRUE;
                }

                else {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "Automode: OFF";
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                    check = Boolean.FALSE;

                }
            }
        });
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

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

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

}
