package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.DB_logik;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zeshan on 15-12-2015.
 */

public class Kalender_aendringer_fragment extends FragmentActivity implements Datovaelger_Fragment.OnDateRangeSelectedListener, Tidsvaelger_fragment.OnTimeRangeSelectedListener, AdapterView.OnItemSelectedListener {
    private Button Annuller, gem;
    private Spinner spinner;
    private TextView startdato,slutdato, starttid,sluttid;
    private TextView postnr;
    private EditText kommentar;
    private Switch aSwitch;
    private boolean sluttidkun = false;
    private boolean slutdatokun = false;
    private DB_logik dbLogik = new DB_logik();
    Bundle bundle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender_aendrings_view);
        bundle = getIntent().getExtras();
        Annuller = (Button) findViewById(R.id.annuller);
        gem = (Button) findViewById(R.id.Gem);
        gem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        postnr = (TextView) findViewById(R.id.postnr);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        startdato = (TextView) findViewById(R.id.startdatotext);
        slutdato = (TextView) findViewById(R.id.slutdatotext);
        starttid = (TextView) findViewById(R.id.starttidtext);
        sluttid = (TextView) findViewById(R.id.sluttidtext);

        kommentar = (EditText) findViewById(R.id.kommentar);

        String day = bundle.getString("dayofmonth");
        int month = bundle.getInt("month");
        int year = bundle.getInt("year");
        if(month <10){
            startdato.setText(day+"-"+"0"+month+"-"+year);
            slutdato.setText(day + "-" +"0"+ month + "-" + year);
        }else {
            startdato.setText(day+"-"+month+"-"+year);
            slutdato.setText(day + "-" + month + "-" + year);
        }



        startdato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Datovaelger_Fragment dateRangePickerFragment = Datovaelger_Fragment.newInstance(Kalender_aendringer_fragment.this, true, 0);
                    dateRangePickerFragment.show(getSupportFragmentManager(), "datePicker");
                    slutdatokun=false;
                }}
        });

        slutdato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Datovaelger_Fragment dateRangePickerFragment = Datovaelger_Fragment.newInstance(Kalender_aendringer_fragment.this, true, 1);
                    dateRangePickerFragment.show(getSupportFragmentManager(), "datePicker");
                    slutdatokun=true;
                }
            }
        });

        starttid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Tidsvaelger_fragment PickerFragment = Tidsvaelger_fragment.newInstance(Kalender_aendringer_fragment.this, true,0);
                    PickerFragment.show(getSupportFragmentManager(), "datePicker");
                    sluttidkun=false;
                }
            }
        });

        sluttid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Tidsvaelger_fragment PickerFragment = Tidsvaelger_fragment.newInstance(Kalender_aendringer_fragment.this, true,1);
                    PickerFragment.show(getSupportFragmentManager(), "datePicker");
                    sluttidkun=true;
                }
            }
        });

        starttid.setText("10:30");
        sluttid.setText("11:00");

        List<String> categories = new ArrayList<String>();
        categories.add("Optaget");
        categories.add("Anden by");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        gem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");

                try {
                    Date start = sdf.parse(startdato.getText().toString()+" "+starttid.getText().toString());
                    Date slut = sdf.parse(slutdato.getText().toString()+" "+sluttid.getText().toString());
                    if(start.getTime() < slut.getTime()) {
                        if(System.currentTimeMillis() < start.getTime()) {
                            if (postnr.isShown()) {
                                dbLogik.setKalenderopgaver(starttid.getText().toString(), sluttid.getText().toString(), startdato.getText().toString(), slutdato.getText().toString(), bundle.getString("UserID"), kommentar.getText().toString(), postnr.getText().toString(), "available");
                                Kalender_aendringer_fragment.this.finish();
                                Toast.makeText(getBaseContext(), "Dit kalender er nu opdateret", Toast.LENGTH_SHORT).show();

                            } else {
                                dbLogik.setKalenderopgaver(starttid.getText().toString(), sluttid.getText().toString(), startdato.getText().toString(), slutdato.getText().toString(), bundle.getString("UserID"), kommentar.getText().toString(), postnr.getText().toString(), "unavailable");
                                Kalender_aendringer_fragment.this.finish();
                                Toast.makeText(getBaseContext(), "Dit kalender er nu opdateret", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getBaseContext(), "startdaton skal være efter dagensdato", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Startdatoen kan ikke være før slutdatoen", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "En fejl på datoen er fundet, tjek venligst at de står korrekt", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Annuller.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Kalender_aendringer_fragment.this.finish();
            }
        });

        aSwitch = (Switch) findViewById(R.id.radioButton2);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String day = bundle.getString("dayofmonth");
                    int month = bundle.getInt("month");
                    int year = bundle.getInt("year");
                    if(month <10){
                        startdato.setText(day+"-"+"0"+month+"-"+year);
                        slutdato.setText(day + "-" +"0"+ month + "-" + year);
                        startdato.setEnabled(false);
                        slutdato.setEnabled(false);
                    }else {
                        startdato.setText(day+"-"+month+"-"+year);
                        slutdato.setText(day + "-" + month + "-" + year);
                        startdato.setEnabled(false);
                        slutdato.setEnabled(false);
                    }
                    starttid.setText("00:00");
                    starttid.setEnabled(false);
                    sluttid.setText("23:59");
                    sluttid.setEnabled(false);

                }
                else{
                    starttid.setEnabled(true);
                    sluttid.setEnabled(true);
                    startdato.setEnabled(true);
                    slutdato.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        if(!slutdatokun) {
            if(startDay>9) {
                if((startMonth + 1) >9) {
                    startdato.setText(startDay + "-" + (startMonth + 1) + "-" + startYear);
                }
                else{
                    startdato.setText(startDay + "-" +"0"+(startMonth + 1) + "-" + startYear);
                }
            }
            else{
                if((startMonth + 1) >9) {
                    startdato.setText("0" + startDay + "-" + (startMonth + 1) + "-" + startYear);
                }
                else{
                    startdato.setText("0" + startDay + "-" + "0"+(startMonth + 1) + "-" + startYear);
                }
            }
        }
        else{
            if(endDay>9) {
                if((endMonth + 1) >9) {
                    slutdato.setText(endDay + "-" + (endMonth + 1) + "-" + endYear);
                }
                else{
                    slutdato.setText(endDay + "-" + "0"+(endMonth + 1) + "-" + endYear);
                }
            }
            else{
                if((endMonth + 1) >9) {
                    slutdato.setText("0" + endDay + "-" + (endMonth + 1) + "-" + endYear);
                }
                else{
                    slutdato.setText("0" + endDay + "-" +"0"+(endMonth + 1) + "-" + endYear);

                }
            }
        }
    }

    @Override
    public void onTimeRangeSelected(int startHour, int startMin, int endHour, int endMin) {
        String time1=String.valueOf(startHour),time2=String.valueOf(endHour),min1=String.valueOf(startMin),min2=String.valueOf(endMin);
        if(startHour == 0 || startHour < 10){
            time1 = ("0"+startHour );
        }
        if(endHour == 0|| endHour < 10){
            time2 = ("0"+endHour );
        }
        if(startMin == 0|| startMin < 10){
            min1 = ("0"+startMin );
        }
        if(endMin == 0|| endMin < 10){
            min2 = ("0"+endMin);
        }
        if(!sluttidkun) {
            starttid.setText(time1 + ":" + min1);
            sluttid.setText(time2 + ":" + min2);
        }
        else{
            sluttid.setText(time2 + ":" + min2);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 1) {
            postnr.setVisibility(View.VISIBLE);
        }
        else if(position==0){
            postnr.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

