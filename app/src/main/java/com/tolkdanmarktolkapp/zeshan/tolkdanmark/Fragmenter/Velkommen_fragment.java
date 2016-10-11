package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.Fragmentmanager;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.parse.ParseInstallation;

/**
 * Created by Zeshan on 08-01-2016.
 */
public class Velkommen_fragment extends Fragment implements View.OnClickListener {

    private RelativeLayout velkommenview;
    private ImageButton beskeder,kalender,opgaver,kontakt;
    private Fragmentmanager fragments = new Fragmentmanager();
    private boolean animationvist = false;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.velkommen_view, container, false);
        velkommenview  = (RelativeLayout) rod.findViewById(R.id.velkommenview);
        imageView = (ImageView) rod.findViewById(R.id.imageView3);
       
        beskeder = (ImageButton) rod.findViewById(R.id.beskeder);
        kalender = (ImageButton) rod.findViewById(R.id.kalender);
        opgaver = (ImageButton) rod.findViewById(R.id.opgaver);
        kontakt = (ImageButton) rod.findViewById(R.id.kontakt);

        beskeder.setOnClickListener(this);
        kalender.setOnClickListener(this);
        opgaver.setOnClickListener(this);
        kontakt.setOnClickListener(this);

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        Bundle bundle = getActivity().getIntent().getExtras();
        installation.put("device_id", bundle.getString("UserID"));
        System.out.println(bundle.getString("UserID"));
        installation.saveInBackground();

        return rod;

    }

    @Override
    public void onClick(View v) {
        if(v == beskeder)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.getBeskederfragment()).addToBackStack(fragments.getBeskederfragment().getTag()).commit();
        }
        if(v == opgaver)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.getDagensopagverfragment()).addToBackStack(fragments.getDagensopagverfragment().getTag()).commit();

        }
        if(v == kalender)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.getKalenderfragment()).addToBackStack(fragments.getKalenderfragment().getTag()).commit();

        }
        if(v == kontakt)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.getKontaktfragment()).addToBackStack(fragments.getKontaktfragment().getTag()).commit();

        }
    }
}
