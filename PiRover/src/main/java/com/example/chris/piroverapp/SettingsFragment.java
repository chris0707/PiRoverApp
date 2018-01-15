package com.example.chris.piroverapp;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    SeekBar seekBar;
    TextView brightnessValText;
    ToggleButton audioToggle;
    Button saveButton;

    String audio1;
    String audioSelected = "";

    float lightValue = 0.5f;



    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        seekBar = view.findViewById(R.id.seekBar);
        brightnessValText = view.findViewById(R.id.brightnessValue);
        audioToggle = view.findViewById(R.id.toggleButton);
        saveButton = view.findViewById(R.id.saveButton);

        audioToggle.setText(getResources().getString(R.string.frag_toggleOff));
        audioToggle.setTextOff(getResources().getString(R.string.frag_toggleOff));
        audioToggle.setTextOn(getResources().getString(R.string.frag_toggleOn));

        NotificationManager notificationManager =
                (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }else {

            SharedPreferences prefs1 = getActivity().getSharedPreferences(getResources().getString(R.string.audio_name), Context.MODE_PRIVATE);
            audio1 = prefs1.getString(getResources().getString(R.string.audio_pref), getResources().getString(R.string.audio_stringpref));
            if (audio1.equals(getResources().getString(R.string.audio_check))) {
                AudioManager audioManager = (AudioManager) getContext().getSystemService(getContext().AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                audioToggle.setChecked(true);

            } else if (audio1.equals(getResources().getString(R.string.audio_unchecked))) {
                AudioManager audioManager = (AudioManager) getContext().getSystemService(getContext().AUDIO_SERVICE);
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);

                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
                audioToggle.setChecked(false);

            }


            audioToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if (isChecked) {
                        AudioManager audioManager = (AudioManager) getContext().getSystemService(getContext().AUDIO_SERVICE);
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        audioSelected = getResources().getString(R.string.audio_check);


                    } else {

                        AudioManager audioManager = (AudioManager) getContext().getSystemService(getContext().AUDIO_SERVICE);
                        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);

                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
                        audioSelected = getResources().getString(R.string.audio_unchecked);

                    }
                }
            });

        }


        SharedPreferences prefs2 = getActivity().getSharedPreferences(getResources().getString(R.string.bright_name), Context.MODE_PRIVATE);
        //brightSet = prefs1.getString(getResources().getString(R.string.bright_pref), getResources().getString(R.string.bright_stringpref));
        float savedBrightness = prefs2.getFloat(getResources().getString(R.string.bright_pref),0.5f);
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.screenBrightness = savedBrightness;
        getActivity().getWindow().setAttributes(layoutParams);

        brightnessValText.setText(String.valueOf(savedBrightness));
     seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         @Override
         public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

             lightValue = (float)i/100;
             brightnessValText.setText(String.valueOf(lightValue));

             WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
             layoutParams.screenBrightness = lightValue;
             getActivity().getWindow().setAttributes(layoutParams);

         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {

         }

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {

         }
     });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs1 = getActivity().getSharedPreferences(getResources().getString(R.string.audio_name), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = prefs1.edit();
                if(audioSelected== getResources().getString(R.string.audio_check)){
                    editor1.putString(getResources().getString(R.string.audio_pref), audioSelected);
                    editor1.commit();
                }else if (audioSelected==getResources().getString(R.string.audio_unchecked)){
                    editor1.putString(getResources().getString(R.string.audio_pref), audioSelected);
                    editor1.commit();
                }

                SharedPreferences prefs2 = getActivity().getSharedPreferences(getResources().getString(R.string.bright_name), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();
                editor2.putFloat(getResources().getString(R.string.bright_pref), lightValue);
                editor2.commit();


                Toast.makeText(getContext(), getResources().getString(R.string.frag_saveToast), Toast.LENGTH_SHORT).show();


            }
        });








        return view;
    }

}
