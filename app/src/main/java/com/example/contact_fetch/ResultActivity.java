package com.example.contact_fetch;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;

public class ResultActivity extends AppCompatActivity {
    TextView tv;
    ListView lv;
    String gmailid;
    ArrayAdapter adpt;
    EditText et;
    ArrayList<String> mylist=new ArrayList<>();
    public  int count=0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv=findViewById(R.id.result);
        lv=findViewById(R.id.listview);
        adpt=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,mylist);
        lv.setAdapter(adpt);
        if(getIntent().getExtras()!=null)
        {
            String s=getIntent().getExtras().getString("message");
            tv.setText(s);

            contactactread();
        }
    }

    private void contactactread() {

        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();

        for (Account ac : accounts) {
            gmailid = ac.name;//give log in gmail id

            String actype = ac.type;
            // Take your time to look at all available accounts
            System.out.println("Accounts : " + gmailid + ", " + actype);
            Toast.makeText(this, "accounts"+gmailid+"nn"+actype, Toast.LENGTH_SHORT).show();
        }


        myRef = database.getReference(gmailid);

        ///////////////////////
        ContentResolver contentResolver = getBaseContext().getContentResolver();

        if (contentResolver == null)
            return;

        String[] fieldListProjection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };

        Cursor phones = contentResolver
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                        , fieldListProjection, null, null, null);
        HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();

        if (phones != null && phones.getCount() > 0) {
            while (phones.moveToNext()) {
                String normalizedNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
                if (Integer.parseInt(phones.getString(phones.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    if (normalizedNumbersAlreadyFound.add(normalizedNumber)) {
                        String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                        String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("test", " Print all values");
                        //Toast.makeText(this, "name"+"   "+name+"\n phonr no."+"    "+phoneNumber, Toast.LENGTH_SHORT).show();
                        count=count+1;
                        String n=count+">"+"name:"+"   "+name+"\n phone no:"+"    "+phoneNumber;
                        addadata(n);
                    }
                }
            }
            phones.close();


        }
        myRef.setValue(mylist);
        ////////////////////////
    }


    private void addadata(String name) {
       // myRef.setValue(name);

        mylist.add(name);
        adpt.notifyDataSetChanged();
    }


}
