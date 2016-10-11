package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.popupwindows;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.DB_logik;

import java.util.ArrayList;

/**
 * Created by Zeshan on 16-12-2015.
 */
public class Beskeder_fragment extends Fragment  implements View.OnClickListener, AdapterView.OnItemClickListener{
    ListView listview;
    ImageButton Resfreshbeskeder;
    private ArrayList<String> Beskeder = new ArrayList<>();
    private ArrayAdapter adapter;
    private DB_logik data = new DB_logik();
    private com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.popupwindows popupwindows = new popupwindows();
    private boolean statushenterbeskeder=false;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.beskeder, container, false);
        Hentbeskeder();
        listview = (ListView) rod.findViewById(R.id.listView2);
        listview.setOnItemClickListener(this);

        Beskeder.clear();
        Beskeder.add("Henter beskeder");

        Resfreshbeskeder = (ImageButton) rod.findViewById(R.id.Resfreshbeskeder);
        Resfreshbeskeder.setOnClickListener(this);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text2, Beskeder);
        listview.setAdapter(adapter);
        return rod;
    }

    private void Hentbeskeder() {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Bundle bundle = getActivity().getIntent().getExtras();
                    ArrayList<String> beskedertemp = data.Getbeskeder(bundle.getString("UserID"));
                    if(beskedertemp.size() != 0){
                        Beskeder.clear();
                        for(int i =0; i< beskedertemp.size(); i++){
                            Beskeder.add(beskedertemp.get(i));
                        }
                    }
                    else{
                        Beskeder.clear();
                        Beskeder.add("Ingen beskeder");
                    }
                    return "";
                } catch (Exception e) {
                    Beskeder.clear();
                    Beskeder.add("Kan ikke hente beskeder, tjek internet forbindelse");
                    return "beskederne blev ikke hentet korrekt";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                adapter.notifyDataSetChanged();
                try{Toast.makeText(getActivity(), "Opdateret", Toast.LENGTH_SHORT).show();}
                catch (NullPointerException e){}
                statushenterbeskeder=false;
                Resfreshbeskeder.animate().cancel();
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }

    private void Beskedlaest(final int groupPosition ) {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    if(data.datahentet) {
                        if (data.BeskederJSONArray.getJSONObject(groupPosition).getString("read").equals("")) {
                            data.Setbeskedlaest(data.BeskederJSONArray.getJSONObject(groupPosition).getString("message_tolk_id"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "beskederne blev ikke hentet korrekt";
                }
                return null;
            }
            @Override
            protected void onPostExecute(Object resultat) {

            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == Resfreshbeskeder){
            Resfreshbeskeder.animate().setInterpolator(new LinearInterpolator()).rotation(43200).setStartDelay(0).setDuration(120000).start();
            if(!statushenterbeskeder) {
                Toast.makeText(getActivity(), "Opdatere...", Toast.LENGTH_SHORT).show();
                statushenterbeskeder = true;
                listview.setAdapter(adapter);
                Hentbeskeder();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Beskedlaest(position);
            if(data.datahentet) {
                popupwindows.initiatePopupWindow(data.BeskederJSONArray.getJSONObject(position).getString("subject"), data.BeskederJSONArray.getJSONObject(position).getString("body"), getActivity());
            }
            else{
                Toast.makeText(getActivity(), "Henter beskeder...", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}