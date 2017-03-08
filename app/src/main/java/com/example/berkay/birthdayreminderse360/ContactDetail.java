package com.example.berkay.birthdayreminderse360;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactDetail extends AppCompatActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextAge;
    EditText editTextMonth;
    EditText editTextDay;
    private int _Contact_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextEmail);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextMonth =(EditText) findViewById(R.id.editTextMonth);
        editTextDay =(EditText) findViewById(R.id.editTextDay);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);



        Intent intent = getIntent();
        _Contact_Id =intent.getIntExtra("contact_Id",0);

        //int contactid=Integer.getInteger(_Contact_Id);
        ContactRepo repo = new ContactRepo(this);
        Contact contact = new Contact();
        contact = repo.getContactById(_Contact_Id);

        editTextAge.setText(String.valueOf(contact.yil));
        editTextMonth.setText(String.valueOf(contact.ay));
        editTextDay.setText(String.valueOf(contact.gun));
        editTextName.setText(contact.name);
        editTextSurname.setText(contact.surname);
        //Toast.makeText(getApplicationContext(),contactid,Toast.LENGTH_SHORT).show();
    }



    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            ContactRepo repo = new ContactRepo(this);
            Contact contact = new Contact();
            contact.gun= Integer.parseInt(editTextDay.getText().toString());
            contact.ay= Integer.parseInt(editTextMonth.getText().toString());
            contact.yil= Integer.parseInt(editTextAge.getText().toString());
            contact.surname=editTextSurname.getText().toString();
            contact.name=editTextName.getText().toString();
            contact.contact_ID=_Contact_Id;

            if ((_Contact_Id)==0){
                _Contact_Id = (repo.insert(contact));

                Toast.makeText(this,"New Contact Insert",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(contact);
                Toast.makeText(this,"Contact Record updated",Toast.LENGTH_SHORT).show();
                BirthdayReminder.fab.callOnClick();
                BirthdayReminder.reload.callOnClick();
                finish();


            }
        }else if (view== findViewById(R.id.btnDelete)){
            ContactRepo repo = new ContactRepo(this);
            repo.delete(_Contact_Id);
            Toast.makeText(this, "Contact Record Deleted", Toast.LENGTH_SHORT).show();
            BirthdayReminder.fab.callOnClick();
            BirthdayReminder.reload.callOnClick();
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }


    }

}
