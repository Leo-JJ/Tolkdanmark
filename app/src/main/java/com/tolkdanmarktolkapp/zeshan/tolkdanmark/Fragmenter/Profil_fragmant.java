package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.DB_logik;

import org.json.JSONArray;

/**
 * Created by Zeshan on 21-12-2015.
 */

public class Profil_fragmant extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    private EditText navn, cprnr, adresse,etage,postnr, byen,telefonrnr, telefonr2, email, regnr, kontonr, banknavn, kørekortnr, registreringsnr,kommentar;
    private CheckBox hardubil, harkorekort;
    private Button gem;
    private DB_logik data = new DB_logik();
    private JSONArray profiledata;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.profil_view, container, false);
        hentprofildata();
        navn = (EditText) rod.findViewById(R.id.navn);
        cprnr = (EditText) rod.findViewById(R.id.cprnr);
        adresse = (EditText) rod.findViewById(R.id.adresse);
        etage = (EditText) rod.findViewById(R.id.etage);
        postnr = (EditText) rod.findViewById(R.id.postnr);
        byen = (EditText) rod.findViewById(R.id.by);
        telefonrnr = (EditText) rod.findViewById(R.id.telefonnr);
        telefonr2 = (EditText) rod.findViewById(R.id.telefonnr2);
        email = (EditText) rod.findViewById(R.id.email);
        regnr = (EditText) rod.findViewById(R.id.regnr);
        kontonr = (EditText) rod.findViewById(R.id.kontonr);
        banknavn = (EditText) rod.findViewById(R.id.banknavn);
        kørekortnr = (EditText) rod.findViewById(R.id.korekortnr);
        registreringsnr = (EditText) rod.findViewById(R.id.registreringsnr);
        kommentar = (EditText) rod.findViewById(R.id.kommentar);
        hardubil = (CheckBox) rod.findViewById(R.id.hardubiljanej);
        harkorekort = (CheckBox) rod.findViewById(R.id.korekortjanej);
        gem = (Button) rod.findViewById(R.id.Gem);
        gem.setOnClickListener(this);
        hardubil.setOnCheckedChangeListener(this);
        harkorekort.setOnCheckedChangeListener(this);
        return rod;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == hardubil){
            if(isChecked) {
                registreringsnr.setVisibility(View.VISIBLE);
            }
            else{
                registreringsnr.setVisibility(View.INVISIBLE);
            }
        }
        else if (buttonView == harkorekort ){
            if (isChecked) {
                kørekortnr.setVisibility(View.VISIBLE);
            }
            else{
                kørekortnr.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void hentprofildata() {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Bundle bundle = getActivity().getIntent().getExtras();
                    profiledata = data.Getpofiledata(bundle.getString("UserID"));
                    return "";
                } catch (Exception e) {
                    return " blev ikke hentet korrekt";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                try {
                    navn.setText(profiledata.getJSONObject(0).getString("name"));
                    cprnr.setText(profiledata.getJSONObject(0).getString("cpr"));
                    adresse.setText(profiledata.getJSONObject(0).getString("address"));
                    etage.setText(profiledata.getJSONObject(0).getString("etage"));
                    postnr.setText(profiledata.getJSONObject(0).getString("postalCode"));
                    byen.setText(profiledata.getJSONObject(0).getString("city"));
                    telefonrnr.setText(profiledata.getJSONObject(0).getString("telephone"));
                    telefonr2.setText(profiledata.getJSONObject(0).getString("mobile"));
                    email.setText(profiledata.getJSONObject(0).getString("email"));
                    regnr.setText(profiledata.getJSONObject(0).getString("regnr"));
                    kontonr.setText(profiledata.getJSONObject(0).getString("kontonr"));
                    banknavn.setText(profiledata.getJSONObject(0).getString("bank_no"));
                    kørekortnr.setText(profiledata.getJSONObject(0).getString("driving_license_nr"));
                    registreringsnr.setText(profiledata.getJSONObject(0).getString("car_regnr"));
                    kommentar.setText(profiledata.getJSONObject(0).getString("description"));
                    hardubil.setChecked(profiledata.getJSONObject(0).getString("has_car").equals("True"));
                    harkorekort.setChecked(profiledata.getJSONObject(0).getString("has_driving_license").equals("True"));
                } catch (Exception e) {
                    System.out.println("kan ikke hente profil data");
                }
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }
    private void gemprofildata(final String name, final String cprnr, final String adresse, final String etage, final String  postnr, final String  byen, final String telefonrnr, final String telefonr2, final String  email, final String regnr, final String  kontonr, final String  banknavn, final String  kørekortnr, final String registreringsnr, final String kommentar, final boolean harkort, final boolean harbil, final String userid) {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Bundle bundle = getActivity().getIntent().getExtras();
                    return data.gemprofildata(name, cprnr,adresse,etage,postnr,byen,telefonrnr,telefonr2,email,regnr,kontonr,banknavn,kørekortnr,registreringsnr,kommentar,harkort,harbil,userid);
            } catch (Exception e) {
                return " blev ikke hentet korrekt";
            }
        }
        @Override
        protected void onPostExecute(Object resultat) {
            if(resultat.equals("true")){
                Toast.makeText(getContext(), "profildata er gemt", Toast.LENGTH_SHORT).show();
            }
        }
    }
    AsyncTask1 as = new AsyncTask1();
    as.execute();
}

    private void validateadress(final String adress) {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    if(data.validateadress(adress)){
                        return "true";
                    }
                } catch (Exception e) {
                    return "blev ikke hentet korrekt";
                }
                return "blev ikke hentet";
            }
            @Override
            protected void onPostExecute(Object resultat) {
                System.out.println(resultat);
                if(resultat.equals("true")){
                    Bundle bundle = getActivity().getIntent().getExtras();
                    gemprofildata(navn.getText().toString(),cprnr.getText().toString(),adresse.getText().toString(),etage.getText().toString(),postnr.getText().toString(),
                            byen.getText().toString(),telefonrnr.getText().toString(),telefonr2.getText().toString(),email.getText().toString(),regnr.getText().toString(),kontonr.getText().toString(),
                            banknavn.getText().toString(),kørekortnr.getText().toString(),registreringsnr.getText().toString(),kommentar.getText().toString(),harkorekort.isChecked(), hardubil.isChecked(),bundle.getString("UserID"));
                    hentprofildata();
                    Toast.makeText(getContext(), "Profil er opdateret", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "adressen kan ikke valideres", Toast.LENGTH_SHORT).show();
                }
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }
    @Override
    public void onClick(View v) {
        if (v == gem) {
           validateadress(adresse.getText().toString());
        }
    }


}
