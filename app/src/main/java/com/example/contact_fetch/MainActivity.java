package com.example.contact_fetch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public PermissionUti permissionUti;
    private static final int txt_contact = 3;
    private static final int Request_contact = 325;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionUti = new PermissionUti(this);

    }
    private int checkpermission(int permission)
    {
        int status = PackageManager.PERMISSION_DENIED;
        switch (permission) {

            case txt_contact:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                break;
        }

        return status;
    }

    public void requestPermission(int permission) {
        switch (permission) {

            case txt_contact:
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, Request_contact);
        }


    }




    public void showpermissionExplanation(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);



        if (permission == txt_contact) {
            builder.setMessage("this app is required for your read contact permission ");
            builder.setTitle("contact permission request");
        }

        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (permission == txt_contact)
                    requestPermission(txt_contact);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //3333333####################################################


    public void buttonClick(View view) {

        if(checkpermission(txt_contact)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS))
            {
                showpermissionExplanation(txt_contact);
            }
            else if (!permissionUti.checkpermissionprefence("contact"))
            {
                requestPermission(txt_contact);
                permissionUti.updatepermisionprefence("contact");
            }
            else
            {
                Toast.makeText(this, "pls allow contact permission throug settin", Toast.LENGTH_SHORT).show();
                Intent i=new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri= Uri.fromParts("package",this.getPackageName(),null);
                i.setData(uri);
                this.startActivity(i);
            }


        }
        else
        {
            Toast.makeText(this, "you have contact permission", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,ResultActivity.class);
            i.putExtra("message","you can now take see your contacts...");
            startActivity(i);
        }
    }



    }

