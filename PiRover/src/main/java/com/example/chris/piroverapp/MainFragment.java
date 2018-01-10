package com.example.chris.piroverapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


public class MainFragment extends Fragment implements View.OnClickListener {

    Intent intent;
    View view;
    Uri uri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);
        Button autobutton = (Button) view.findViewById(R.id.autocheck);
        autobutton.setOnClickListener(this);
        Button manubutton = (Button) view.findViewById(R.id.manualcheck);
        manubutton.setOnClickListener(this);

        FloatingActionButton chrisbutton = (FloatingActionButton)view.findViewById(R.id.fab1);
        chrisbutton.setOnClickListener(this);
        FloatingActionButton lawrencebutton = (FloatingActionButton)view.findViewById(R.id.fab2);
        lawrencebutton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab1:
                uri  = Uri.parse("https://github.com/chris0707");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
                break;

            case R.id.fab2:
                uri = Uri.parse("https://github.com/n01033296");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.autocheck:
                intent = new Intent(getActivity(), AutoActivity.class);
                startActivity(intent);
                break;

            case R.id.manualcheck:
                intent = new Intent(getActivity(), ManualActivity.class);
                startActivity(intent);
                break;
        }
    }
}