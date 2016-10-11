package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.Fragmentmanager;


import org.json.JSONObject;

/**
 * Created by Jiahua on 26-09-2016.
 */

public class Tolkbilag1_fragment extends Fragment implements View.OnClickListener {

    private Button next = null;
    private EditText klientsnavn, cpr, sprog, dato, tidfra, tidtil, tolkebruger, tolk, tolkcpr, fraktura;
    public static JSONObject object;
    private Fragmentmanager fragments = new Fragmentmanager();

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.tolkbilag1_view, container, false);

        next = (Button) rod.findViewById(R.id.next);
        next.setOnClickListener(this);

        klientsnavn = (EditText) rod.findViewById(R.id.kNavn);
        cpr = (EditText) rod.findViewById(R.id.kCpr);
        tolk = (EditText) rod.findViewById(R.id.tNavn);
        tolkcpr = (EditText) rod.findViewById(R.id.tCpr);

        try {
            klientsnavn.setText(object.getString("citizenName"));
            cpr.setText(object.getString("cpr"));
            tolk.setText(object.getString("interpreter_name"));
            tolkcpr.setText(object.getString("tolkcpr"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == next) {
            getFragmentManager().beginTransaction().replace(R.id.container, fragments.getTolkbilag2fragment()).addToBackStack(fragments.getTolkbilag2fragment().getTag()).commit();
        }
    }
}
