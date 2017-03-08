package com.example.berkay.birthdayreminderse360;

/**
 * Created by Berkay on 26.12.2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ContactRepo {
    private DBHelper dbHelper;

    public ContactRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Contact contact) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contact.KEY_surname, contact.surname);
        values.put(Contact.KEY_gun,contact.gun);
        values.put(Contact.KEY_ay,contact.ay);
        values.put(Contact.KEY_yil,contact.yil);
        values.put(Contact.KEY_name, contact.name);


        // Inserting Row
        long contact_Id = db.insert(Contact.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) contact_Id;
    }

    public void delete(int contact_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Contact.TABLE, Contact.KEY_ID + "= ?", new String[]{String.valueOf(contact_Id)});
        db.close(); // Closing database connection
    }

    public void update(Contact contact) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contact.KEY_surname, contact.surname);
        values.put(Contact.KEY_gun,contact.gun);
        values.put(Contact.KEY_ay,contact.ay);
        values.put(Contact.KEY_yil,contact.yil);
        values.put(Contact.KEY_name, contact.name);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Contact.TABLE, values, Contact.KEY_ID + "= ?", new String[]{String.valueOf(contact.contact_ID)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getContactList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Contact.KEY_ID + "," +
                Contact.KEY_name + "," +
                Contact.KEY_surname + "," +
                Contact.KEY_ay + "," +
                Contact.KEY_gun + "," +
                Contact.KEY_yil +
                " FROM " + Contact.TABLE;


        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM");
        if (cursor.moveToFirst()) {
            do {
                long day1 = 4;
                HashMap<String, String> contact = new HashMap<String, String>();
                contact.put("id", cursor.getString(cursor.getColumnIndex(Contact.KEY_ID)));
                contact.put("name", cursor.getString(cursor.getColumnIndex(Contact.KEY_name)));
                Date date1 = null;
                Date date2 = null;

                try {
                    date1 = myFormat.parse(cursor.getString(cursor.getColumnIndex(Contact.KEY_gun)) + " " + cursor.getString(cursor.getColumnIndex(Contact.KEY_ay)));
                    date1= myFormat.parse(myFormat.format(date1));
                    date2 = new Date();
                    date2=myFormat.parse(date2.toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long diff = date2.getTime() - date1.getTime();
                day1 = (diff / (24 * 60 * 60 * 1000))- 16436;
                //Toast.makeText(getApplicationContext(),day1,Toast.LENGTH_SHORT).show();
                if ((day1 == 0)&&(Settings.switchstat)) {

                    BirthdayReminder.arg1 = cursor.getString(cursor.getColumnIndex(Contact.KEY_name)) + " " + cursor.getString(cursor.getColumnIndex(Contact.KEY_surname)) + " has a birthday Today ";
                    BirthdayReminder.btnNtf.callOnClick();
                    contact.put("yil", "Turns " + String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contact.KEY_yil)))) + "Today ");
                }
                    else {
                    contact.put("yil", "Turns " + String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contact.KEY_yil)))) + " years old" + " on " + cursor.getString(cursor.getColumnIndex(Contact.KEY_gun)) + "th of " + cursor.getString(cursor.getColumnIndex(Contact.KEY_ay)));


                }
                contactList.add(contact);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactList;

    }



    public Contact getContactById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Contact.KEY_ID + "," +
                Contact.KEY_name + "," +
                Contact.KEY_surname + "," +
                Contact.KEY_gun + "," +
                Contact.KEY_ay + "," +
                Contact.KEY_yil +
                " FROM " + Contact.TABLE
                + " WHERE " +
                Contact.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Contact contact = new Contact();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                contact.contact_ID =cursor.getInt(cursor.getColumnIndex(Contact.KEY_ID));
                contact.name =cursor.getString(cursor.getColumnIndex(Contact.KEY_name));
                contact.surname  =cursor.getString(cursor.getColumnIndex(Contact.KEY_surname));
                contact.gun =cursor.getInt(cursor.getColumnIndex(Contact.KEY_gun));
                contact.ay =cursor.getInt(cursor.getColumnIndex(Contact.KEY_ay));
                contact.yil =cursor.getInt(cursor.getColumnIndex(Contact.KEY_yil));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contact;
    }

}
