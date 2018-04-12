package edu.usc.cs404.catchup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.appsee.Appsee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class EmailActivity extends AppCompatActivity {

    private static final String uniqueID = "404404";
    private ArrayList<String> friendsList;
    private DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private FriendsDBHandler DBHandler = new FriendsDBHandler();

    public ArrayList<String> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        //generate list
        list = new ArrayList<String>();
        Appsee.setDebugToLogcat(true);
        Appsee.start();
    }





    public void get(View v){
        EditText edit = (EditText)findViewById(R.id.emailText);
        String result = edit.getText().toString();

        //Print email for debugging purposes
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        //Test send email function
        //sendEmail(result);
        //CHECK database to see if email exists. if so, add friends!

        list.add(result);

        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.notificationView);
        lView.setAdapter(adapter);
        getWindow().getDecorView().findViewById(android.R.id.content).invalidate();



        try {



            sendFriendRequest(result);
            Toast.makeText(getApplicationContext(), "Friend-Name Added!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Here we can display a fail message and invite the friend to the app
            sendEmail(result);
            Toast.makeText(getApplicationContext(),
                    "Invite to Catchup sent!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }





    public void sendFriendRequest(String email){
//comment this back in to test
        
       /* firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();

        HashMap<String, ArrayList<String>> friends = new HashMap<String, ArrayList<String>>();
        ArrayList<String> pending = new ArrayList<>();
        ref = ref.child(user.getUid()).child("Friends");
        pending.add(email);
        friends.put("Pending", pending);
        Log.d("Inserting MY friend", Collections.singletonList(friends).toString());
        ref.setValue(friends);

        DBHandler.pendingFriends(email);*/


        Uri alarmSound = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);

        Intent emailIntent = new Intent(this, EmailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, emailIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Notification Channel
        CharSequence channelName = "Notification Channel";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(uniqueID, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        //Build notification
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, uniqueID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_add_friend)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setSound(null)
                .setContentTitle("New friend request from " + email)
                .setContentText("Open Catchup to accept or decline")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker("New friend request from " + email)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //Issue Notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int)(System.currentTimeMillis()/1000), builder.build());

    }

    protected void sendEmail(String email) {

        String[] TO = {email};
        //String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your friend has invited you to Catchup!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(EmailActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
