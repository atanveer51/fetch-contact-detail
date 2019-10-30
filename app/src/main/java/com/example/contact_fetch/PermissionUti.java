package com.example.contact_fetch;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionUti {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public PermissionUti(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(context.getString(R.string.permission_prefence),context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void updatepermisionprefence(String permission)
    {
        switch (permission)
        {
            case "camra":
                editor.putBoolean(context.getString(R.string.permission_camra),true);
                editor.commit();
                break;

            case "storage":
                editor.putBoolean(context.getString(R.string.permission_storage),true);
                editor.commit();
                break;

            case "contact":
                editor.putBoolean(context.getString(R.string.permission_contact),true);
                editor.commit();
                break;
        }

    }

    public boolean checkpermissionprefence(String permission)
    {
        boolean isshown=false;

        switch (permission)
        {
            case "camra":
               isshown=sharedPreferences.getBoolean(context.getString(R.string.permission_camra),false);
                break;

            case "storage":
                isshown=sharedPreferences.getBoolean(context.getString(R.string.permission_storage),false);
                break;

            case "contact":
                isshown=sharedPreferences.getBoolean(context.getString(R.string.permission_contact),false);
                break;
        }
        return isshown;

    }
}
