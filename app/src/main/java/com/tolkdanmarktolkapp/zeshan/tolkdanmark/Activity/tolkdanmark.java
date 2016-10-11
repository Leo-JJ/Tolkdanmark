package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Zeshan on 13-01-2016.
 */
public class tolkdanmark extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "fe5Lzyb9qfBIYgBbTFPyi9Kj1S7iE4CEESFSD1cf", "zOlpvqxYQFwjg1zGm9p2J1dfIKFQ8Jqb5m3urT0t");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        SharedPreferences pref = getSharedPreferences("PUSHID", Context.MODE_PRIVATE);

        installation.put("device_id", pref.getString("USERID","superman"));
        installation.saveInBackground();

        }

}
