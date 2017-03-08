package com.example.berkay.birthdayreminderse360;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Add extends AppCompatActivity {
    DatePicker datePicker1;
    EditText nameTxt;
    EditText surnameTxt;
    Button addBtn;
    Button addBtn1;
    ImageView profile;
    private int _Contact_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        nameTxt = (EditText) findViewById(R.id.Name);
        surnameTxt = (EditText) findViewById((R.id.Surname));
        addBtn =(Button) findViewById(R.id.Save);
        addBtn1 =(Button) findViewById(R.id.Cancel);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContactRepo repo = new ContactRepo(v.getContext());
                Contact contact = new Contact();
                contact.name= (nameTxt.getText().toString());
                contact.surname=surnameTxt.getText().toString();
                contact.gun=(datePicker1.getDayOfMonth());
                contact.ay=(datePicker1.getMonth()+1);
                contact.yil=(datePicker1.getYear());
                contact.contact_ID=_Contact_Id;
                repo.insert(contact);
                BirthdayReminder.fab.callOnClick();
                BirthdayReminder.reload.callOnClick();
                finish();
            }
        });

        profile = (ImageView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
            }
        });
         datePicker1 = (DatePicker) findViewById(R.id.datePicker);
    }
    public void getDate(){
        int day =datePicker1.getDayOfMonth();
        int month =datePicker1.getMonth()+1;
        int year = datePicker1.getYear();
        Toast.makeText(getBaseContext(), "Today : " +day+ " / "+ month +"  /  " +year,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_birthday_reminder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }


        return super.onOptionsItemSelected(item);
    }

}

