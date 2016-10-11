package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;


/**
 * Created by Zeshan on 08-01-2016.
 */
public class Kontankt_fragment extends Fragment{

    Button bu;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.kontakt_view, container, false);


        return rod;

    }

}
