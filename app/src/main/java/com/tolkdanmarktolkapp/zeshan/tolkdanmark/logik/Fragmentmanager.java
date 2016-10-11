package com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik;

import android.support.v4.app.Fragment;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Dagens_Opgaver_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Godkendelse_Opgaver_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Beskeder_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Kalender_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Kontankt_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Laegeunderskrift_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Profil_fragmant;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Tolkbilag1_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Tolkbilag2_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Tolkbilag3_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Tolkensunderskrift_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Velkommen_fragment;

/**
 * Created by Zeshan on 09-01-2016.
 */
public class Fragmentmanager {

    public static Fragment Kalenderfragment = new Kalender_fragment();
    public static Fragment Dagensopagverfragment = new Dagens_Opgaver_fragment();
    public static Fragment Velkommenfragment = new Velkommen_fragment();
    public static Fragment Godkendelseopgaverfragment = new Godkendelse_Opgaver_fragment();
    public static Fragment Beskederfragment = new Beskeder_fragment();
    public static Fragment Profilfragment = new Profil_fragmant();
    public static Fragment kontaktfragment = new Kontankt_fragment();
    private static Fragment Tolkbilag1fragment = new Tolkbilag1_fragment();
    private static Fragment Tolkbilag2fragment = new Tolkbilag2_fragment();
    private static Fragment Tolkbilag3fragment = new Tolkbilag3_fragment();
    private static Fragment Laegeunderskriftfragment = new Laegeunderskrift_fragment();
    private static Fragment Tolkensunderskriftfragment = new Tolkensunderskrift_fragment();

    public Fragment getKalenderfragment() {
        return Kalenderfragment;
    }

    public Fragment getDagensopagverfragment() {
        return Dagensopagverfragment;
    }

    public Fragment getVelkommenfragment() {
        return Velkommenfragment;
    }

    public Fragment getGodkendelseopgaverfragment() {
        return Godkendelseopgaverfragment;
    }

    public Fragment getBeskederfragment() {
        return Beskederfragment;
    }

    public Fragment getProfilfragment() {
        return Profilfragment;
    }

    public Fragment getKontaktfragment() {
        return kontaktfragment;
    }

    public Fragment getTolkbilag1fragment() {

        return Tolkbilag1fragment;
    }

    public Fragment getTolkbilag2fragment() {

        return Tolkbilag2fragment;
    }

    public Fragment getTolkbilag3fragment() {

        return Tolkbilag3fragment;
    }

    public static Fragment getLaegeunderskriftfragment() {

        return Laegeunderskriftfragment;
    }

    public static Fragment getTolkensunderskriftfragment() {

        return Tolkensunderskriftfragment;
    }

}
