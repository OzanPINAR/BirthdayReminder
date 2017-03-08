package com.example.berkay.birthdayreminderse360;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.Toast;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.app.ListActivity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class BirthdayReminder extends AppCompatActivity {
    static String arg1;
    static TextView contact_Id;
    TextView surnameTxt;
    ImageView profile;
    Button btn;
    static Button btnNtf;
    static Button reload;
    static FloatingActionButton fab;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        reload = (Button) findViewById(R.id.reload);
        reload.setAlpha(0);
        btnNtf = (Button) findViewById(R.id.btnNtf);
        btnNtf.setAlpha(0);
        //showNotification();
        btnNtf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showNotification();
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        reload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ContactRepo repo = new ContactRepo(BirthdayReminder.this);
                ArrayList<HashMap<String, String>> contactList = repo.getContactList();
                final ListView lv = (ListView) findViewById(R.id.lv1);
                if (contactList.size() != 0) {

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            BirthdayReminder.contact_Id = (TextView) view.findViewById(R.id.contact_Id);
                            String contactId =  BirthdayReminder.contact_Id.getText().toString();
                            Intent objIndent = new Intent(getApplicationContext(), ContactDetail.class);
                            objIndent.putExtra("contact_Id", Integer.parseInt(contactId));
                            startActivity(objIndent);
                        }
                    });
                    ListAdapter adapter = new SimpleAdapter(getApplicationContext(), contactList, R.layout.view_contact_entry, new String[]{"id", "name", "yil"}, new int[]{R.id.contact_Id, R.id.contact_name, R.id.contact_Age});
                    lv.setAdapter(adapter);
                }
                else
                   lv.setAdapter(null);
            }

        });
        ContactRepo repo = new ContactRepo(BirthdayReminder.this);
        ArrayList<HashMap<String, String>> contactList = repo.getContactList();
        final ListView lv = (ListView) findViewById(R.id.lv1);
        if (contactList.size() != 0) {

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    BirthdayReminder.contact_Id = (TextView) view.findViewById(R.id.contact_Id);
                    String contact_Id = BirthdayReminder.contact_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(), ContactDetail.class);
                    objIndent.putExtra("contact_Id", Integer.parseInt(contact_Id));
                    startActivity(objIndent);
                }
            });
            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), contactList, R.layout.view_contact_entry, new String[]{"id", "name", "yil"}, new int[]{R.id.contact_Id, R.id.contact_name, R.id.contact_Age});
            lv.setAdapter(adapter);
        }
        else
            lv.setAdapter(null);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void showNotification() {
        Intent intent = new Intent("com.example.berkay.birthdayreminderse360.BirthdayReminder");
        NotificationManager manager = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification myNotication;
        PendingIntent pendingIntent = PendingIntent.getActivity(BirthdayReminder.this, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(BirthdayReminder.this);

        builder.setAutoCancel(false);
        builder.setContentTitle("Birthday Reminder");
        builder.setContentText(arg1);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);

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
            startActivity(new Intent(this, Settings.class));
        }
        if (id == R.id.navigate) {
            startActivity(new Intent(this, Add.class));
        }
        if(id==R.id.About){
            startActivity(new Intent(this, About.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BirthdayReminder Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.berkay.birthdayreminderse360/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BirthdayReminder Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.berkay.birthdayreminderse360/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
