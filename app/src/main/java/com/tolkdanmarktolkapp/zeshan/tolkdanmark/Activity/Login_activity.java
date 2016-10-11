package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Activity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.*;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.JsonLogik;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.DataBaseHjaelper_logik;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class Login_activity extends Activity implements View.OnClickListener {

    private EditText username, password;
    private Button login;
    private DataBaseHjaelper_logik db;
    private JsonLogik Logik = new JsonLogik();
    private Bundle bundle = new Bundle();
    private String responsejson;
    private boolean succes = false;
    public static boolean clicked = false;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.velkommenmenu);
        username = (EditText) findViewById(R.id.Brugernavn);
        password = (EditText) findViewById(R.id.Adgangskode);
        login = (Button) findViewById(R.id.Login);
        db = new DataBaseHjaelper_logik(this);
        login.setOnClickListener(this);
        sharedpreferences = Login_activity.this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        username.setText(sharedpreferences.getString("username",""));
        password.setText(sharedpreferences.getString("password",""));

        if (ActivityCompat.checkSelfPermission(Login_activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    Login_activity.this,
                    new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION },
                    1);
        }

        if(!sharedpreferences.getString("username","").equals("")){
            clicked = true;
            Indsetdata(username.getText().toString(), password.getText().toString());
        }


        ActionBar ab = getActionBar();
        ab.setTitle(" ");
    }

    @Override
    public void onClick(View v) {
        if (v ==login) {
            if(isNetworkConnected() && !clicked) {
                clicked = true;
                Indsetdata(username.getText().toString(), password.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Du har ikke internet forbindelse", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void Indsetdata(final String username, final String password) {
        class AsyncTask1 extends AsyncTask<Object, String, Object> {
            @Override
            protected Object doInBackground(Object... arg0) {
                new Timer().schedule(new TimerTask() {
                    @Override public void run() {
                        if(!succes) {
                            publishProgress("Tjek internet forbindelse");
                        }
                    }
                }, 10000);
                try {
                    Cursor rs = db.CreateTable(username ,password);
                        UUID uuid = UUID.randomUUID();
                        responsejson=Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/MyService.asmx/UserAuthentication?un=" + username.trim() + "&pw=" + password.trim() + "&mkey=" + uuid);
                        succes=true;
                        try{
                            JSONObject jsonObject = new JSONObject(responsejson);
                            if(jsonObject.getString("loginid").equals("-1"))
                            {
                                this.publishProgress("Forkert Brugernavn eller Adgangskode");
                                clicked = false;
                                rs.close();
                                return 0;
                            }
                            else {
                                if(jsonObject.getString("role").equals("tolk")) {
                                    long k = db.insertUser(username, password, uuid.toString(), jsonObject.getString("loginid"));
                                    SharedPreferences sharedpreferences = getSharedPreferences("PUSHID", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("USERID", jsonObject.get("loginid").toString());
                                    editor.commit();
                                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                                    SharedPreferences.Editor editor2 = sharedpreferences.edit();
                                    editor2.putString("username", username);
                                    editor2.putString("password", password);
                                    editor2.commit();
                                    Intent intent = new Intent(getApplicationContext(), Starts_menu_navi_Activity.class);
                                    bundle.putString("UserID", jsonObject.get("loginid").toString());
                                    intent.putExtras(bundle);
                                    rs.close();
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    this.publishProgress("Du har ikke tolk rettigheder");
                                    clicked = false;
                                }
                            }
                        }
                        catch(Exception e)
                        {
                            clicked = false;
                        }


                    return "text";
                } catch (Exception e) {
                    succes=false;
                    clicked = false;
                    e.printStackTrace();
                    return "Ordene blev ikke hentet korrekt: " + e;
                }
            }
            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                Toast.makeText(getApplicationContext(), ""+values[0], Toast.LENGTH_SHORT).show();
            }
            @Override
            protected void onPostExecute(Object resultat) {

            }

        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }
    @Override
    public void onBackPressed(){
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

}