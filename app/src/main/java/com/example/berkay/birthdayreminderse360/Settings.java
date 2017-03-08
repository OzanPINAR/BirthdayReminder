package com.example.berkay.birthdayreminderse360;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    static boolean switchstat;
    Switch switchh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switchh=(Switch) findViewById(R.id.notification_switch);
        switchh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchh.isChecked())
                    switchstat = true;
                else
                    switchstat=false;

                BirthdayReminder.fab.callOnClick();
                BirthdayReminder.reload.callOnClick();
            }
        });

    }

}
