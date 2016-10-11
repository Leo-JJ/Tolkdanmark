package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.DB_logik;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.popupwindows;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Zeshan on 21-12-2015.
 */
public class Dagens_Opgaver_fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listview;
    private ArrayList<String> Dagensopgaver = new ArrayList<>();
    private ArrayAdapter adapter;
    private ImageButton Resfresh;

    private DB_logik data = new DB_logik();
    private popupwindows popupwindows = new popupwindows();
    private boolean statushenteropgaver=false;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.dagensopgaver, container, false);
        listview = (ListView) rod.findViewById(R.id.dagensopgaver);
        listview.setOnItemClickListener(this);
        Resfresh = (ImageButton) rod.findViewById(R.id.Resfresh);
        Resfresh.setOnClickListener(this);
        Dagensopgaver.clear();
        Dagensopgaver.add("Henter dagens opgaver");
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.dagensopgaver_design, R.id.opgavested, Dagensopgaver){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                    TextView opgaver = (TextView) view.findViewById(R.id.tider);
                    View farve =  view.findViewById(R.id.farve);
                    opgaver.setText("00:00" +"\n"+"    |"+"\n"+"00:00");
                    farve.setBackgroundColor(Color.BLUE);
                    return view;
            }
        };

        listview.setAdapter(adapter);
        Hentopgaver();
        return rod;
    }

    public void lavopgaveliste(final JSONArray OpgaverJSONArray){
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.dagensopgaver_design, R.id.tider, Dagensopgaver){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tider = (TextView) view.findViewById(R.id.tider);
                View farve = view.findViewById(R.id.farve);
                TextView opgavested = (TextView) view.findViewById(R.id.opgavested);
                try {
                    tider.setText(OpgaverJSONArray.getJSONObject(position).getString("starttime")+"\n"+"    |"+"\n"+ OpgaverJSONArray.getJSONObject(position).getString("endtime"));
                    farve.setBackgroundColor(Color.parseColor(OpgaverJSONArray.getJSONObject(position).getString("color")));
                    opgavested.setText("Institut: " + OpgaverJSONArray.getJSONObject(position).getString("institute") + "\n"
                            + "Adresse: " + OpgaverJSONArray.getJSONObject(position).getString("address") + "\n");
                } catch (Exception e) {
                }
                return view;
            }

        };
        listview.setAdapter(adapter);

    }

    private void Hentopgaver() {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Bundle bundle = getActivity().getIntent().getExtras();
                    ArrayList<String> Dagensopgavertemp = data.Getdagensopagver(bundle.getString("UserID"), Calendar.getInstance(Locale.getDefault()) );
                    if(Dagensopgavertemp.size() != 0){
                        Dagensopgaver.clear();
                        for(int i =0; i< Dagensopgavertemp.size(); i++){
                            Dagensopgaver.add(Dagensopgavertemp.get(i));
                        }
                    }
                    else{
                        Dagensopgaver.clear();
                        Dagensopgaver.add("Ingen opgaver idag");
                    }
                    return "";
                } catch (Exception e) {
                    Dagensopgaver.clear();
                    Dagensopgaver.add("Kan ikke hente opgaver, tjek internet forbindelse");
                    return "Opgaverne blev ikke hentet korrekt";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                if(data.Opgavedatahentet) {
                    data.Opgavedatahentet=false;
                    lavopgaveliste(data.OpgaverJSONArray);
                }
                    adapter.notifyDataSetChanged();
                try{Toast.makeText(getActivity(), "Opdateret", Toast.LENGTH_SHORT).show();}
                catch (NullPointerException e){}
                Resfresh.animate().cancel();
                statushenteropgaver=false;
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }


    @Override
    public void onClick(View v) {
        if(v == Resfresh){
            Resfresh.animate().setInterpolator(new LinearInterpolator()).rotation(43200).setStartDelay(0).setDuration(120000).start();
            if(!statushenteropgaver) {
                Toast.makeText(getActivity(), "Opdatere...", Toast.LENGTH_SHORT).show();
                statushenteropgaver=true;
                Hentopgaver();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                if(data.OpgaverJSONArray.getJSONObject(position).getString("institute").equals("Optaget")) {
                    Toast.makeText(getActivity(), "Du er optaget mellem "+
                            data.OpgaverJSONArray.getJSONObject(position).getString("starttime")+"-"+ data.OpgaverJSONArray.getJSONObject(position).getString("endtime") , Toast.LENGTH_SHORT).show();
                }
                else{
                    popupwindows.initiatePopupWindowchoose(getActivity(), data.OpgaverJSONArray.getJSONObject(position));
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), "Prøver at hente opgaver", Toast.LENGTH_SHORT).show();
            }

    }

}
