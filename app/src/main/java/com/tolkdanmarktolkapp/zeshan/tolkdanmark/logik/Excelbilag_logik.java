package com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;


/**
 * Created by Zeshan on 11-01-2016.
 */
public class Excelbilag_logik {


    public void savetoexcel(File imagelocation, Context context, bilagobjekt bilagindhold, Activity ac)  {

        DateTime startTime, endTime;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        startTime = formatter.parseDateTime(bilagindhold.getTidfra());
        endTime = formatter.parseDateTime(bilagindhold.getTidtil());

        Period p = new Period(startTime, endTime);
        long hours = p.getHours();
        long minutes = p.getMinutes();
        String antaltimer;
        if(hours == 0){
            antaltimer = p.getMinutes()+" m";
        }
        else if(minutes == 0){
            antaltimer = p.getHours()+" t";
        }
        else{
                antaltimer = p.getHours()+" t " + p.getMinutes()+" m";

        }
        Toast.makeText(ac, "Sender Bilag...", Toast.LENGTH_LONG).show();

        DB_logik.Generatetolkbilg(bilagindhold.getKlientsnavn(),bilagindhold.getCpr(),
                    bilagindhold.getDato(), antaltimer,bilagindhold.getTidtil(),
                    bilagindhold.getTolkebruger(), bilagindhold. getTidfra(), bilagindhold. getTolk(),
                    bilagindhold.getFraktura(), imagelocation,bilagindhold.getId(),
                    bilagindhold.getEmail(), bilagindhold.getInterpreter_email(),
                    bilagindhold.getSprog(), bilagindhold.getgetreference_id(),bilagindhold.getInstitution(), ac);

    }

    /*public void savetoexcelregion(File imagelocation, Context context, regionbilagobjekt regionbilagindhold, Activity ac)  {

        DateTime startTime, endTime;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        startTime = formatter.parseDateTime(regionbilagindhold.getTidfra());
        endTime = formatter.parseDateTime(regionbilagindhold.getTidtil());

        Period p = new Period(startTime, endTime);
        long hours = p.getHours();
        long minutes = p.getMinutes();
        String antaltimer;
        if(hours == 0){
            antaltimer = p.getMinutes()+" m";
        }
        else if(minutes == 0){
            antaltimer = p.getHours()+" t";
        }
        else{
            antaltimer = p.getHours()+" t " + p.getMinutes()+" m";

        }
        Toast.makeText(ac, "Sender Bilag...", Toast.LENGTH_LONG).show();

        DB_logik.Generatetolkbilg(bilagindhold.getKlientsnavn(),bilagindhold.getCpr(),
                bilagindhold.getDato(), antaltimer,bilagindhold.getTidtil(),
                bilagindhold.getTolkebruger(), bilagindhold. getTidfra(), bilagindhold. getTolk(),
                bilagindhold.getFraktura(), imagelocation,bilagindhold.getId(),
                bilagindhold.getEmail(), bilagindhold.getInterpreter_email(),
                bilagindhold.getSprog(), bilagindhold.getgetreference_id(),bilagindhold.getInstitution(), ac);

    }*/
}
