package edu.usc.cs404.catchup;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Button;
import android.widget.Toast;


public class InviteContactsActivity extends AppCompatActivity {
    //https://www.youtube.com/watch?v=-yDVBAyic0U
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_contacts);

        l1 = (ListView)findViewById(R.id.listView);
    }

    public void get(View v){
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null,null);
        startManagingCursor(cursor);

        String [] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};

        int [] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,cursor,from, to);

        l1.setAdapter(simpleCursorAdapter);
        l1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "You selected "+ i, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
