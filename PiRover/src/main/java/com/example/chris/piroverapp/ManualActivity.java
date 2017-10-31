package com.example.chris.piroverapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static java.lang.System.exit;

public class ManualActivity extends AppCompatActivity {

    ImageView forwardButton, reverseButton, leftButton, rightButton;
    Button stop, dc;

    BluetoothAdapter blueAdapter;
    BluetoothSocket blueSocket;
    BluetoothDevice blueDevice;

    OutputStream outputStream;
    InputStream inputStream;

    String a;
    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        stop = (Button)findViewById(R.id.buttonStop);
        dc = (Button)findViewById(R.id.buttonDc);
        final Button connect = (Button)findViewById(R.id.buttonBlue);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                a = "raspberrypi";
                if(isBluetoothAvailable()){
                    try{
                        findBT();
                        Toast.makeText(getBaseContext(), R.string.trying, Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        Toast.makeText(getBaseContext(), R.string.notestablished, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Toast.makeText(getBaseContext(), R.string.connected, Toast.LENGTH_SHORT).show();
                    connected = true;
                }else {
                    Toast.makeText(getBaseContext(), R.string.bluetoothelse, Toast.LENGTH_SHORT).show();
                }


            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected)
                    sendMsg("stop");
            }
        });

        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected) {
                    Toast.makeText(getBaseContext(), R.string.closing, Toast.LENGTH_SHORT).show();
                    sendMsg("stop");
                    connected = false;
                }else {
                    Toast.makeText(getBaseContext(), R.string.noconnection, Toast.LENGTH_SHORT).show();
                }
            }
        });



        forwardButton = (ImageView) findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected)
                    sendMsg("w");

                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Moving: Forward";
                Toast toast = Toast.makeText(context,text,duration);
                //toast.show();
            }
        });

        reverseButton = (ImageView) findViewById(R.id.reverseButton);
        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected)
                    sendMsg("s");
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Moving: Reverse";
                Toast toast = Toast.makeText(context,text,duration);
                //toast.show();
            }
        });

        leftButton = (ImageView)findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected)
                    sendMsg("a");
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Moving: Left";
                Toast toast = Toast.makeText(context,text,duration);
                //toast.show();
            }
        });

        rightButton = (ImageView)findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connected)
                    sendMsg("d");
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Moving: Right";
                Toast toast = Toast.makeText(context,text,duration);
               // toast.show();
            }
        });

    }

    public static boolean isBluetoothAvailable(){
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }

    void findBT() throws IOException{

        blueAdapter = BluetoothAdapter.getDefaultAdapter();

        if(blueAdapter==null){
           exit(0);

        }

        if(!blueAdapter.isEnabled()){
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);

        }

        Set<BluetoothDevice> pairedDevices = blueAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){

            for(BluetoothDevice device : pairedDevices){
                if(device.getName().equals(a)) {

                    blueDevice = device;
                    break;
                }

            }

        }

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        blueSocket = blueDevice.createRfcommSocketToServiceRecord(uuid);
        try{
            blueSocket.connect();

        }catch(IOException e){
            e.printStackTrace();
            exit(0);
        }finally {
            outputStream = blueSocket.getOutputStream();
            inputStream = blueSocket.getInputStream();
        }

    }

    void sendMsg(String msg1){
        try{
            outputStream.write(msg1.getBytes());
        }catch (IOException e){
            exit(0);
        }
    }
}
