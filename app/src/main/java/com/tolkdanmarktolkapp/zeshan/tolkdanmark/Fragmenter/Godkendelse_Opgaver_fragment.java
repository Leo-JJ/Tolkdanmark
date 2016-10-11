package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.DB_logik;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Zeshan on 26-11-2015.
 */
public class Godkendelse_Opgaver_fragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listView,listView2;
    private Button Afvis,Godkend;
    private ArrayList<String> Openoffers = new ArrayList<>();
    private ArrayList<String> timedoffers = new ArrayList<>();
    private ArrayAdapter adapter, adapter2;
    private DB_logik data = new DB_logik();
    private boolean statushenteropgave = false;
    private ImageButton Resfreshopgavetilbud, fjernalleopgaver;
    private ArrayList<String> Packed_id = new ArrayList<>();
    private ArrayList<Integer> packet_id_number = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.godkendelse_opgaver_view, container, false);
        listView = (ListView) rod.findViewById(R.id.listView);
        listView.setOnItemClickListener(Godkendelse_Opgaver_fragment.this);
        listView2 = (ListView) rod.findViewById(R.id.listView2);
        listView2.setOnItemClickListener(Godkendelse_Opgaver_fragment.this);
        Hentopenoffers();
        Henttimedoffers();
        Openoffers.clear();
        Openoffers.add("Henter Opgave Tilbud");
        timedoffers.clear();
        timedoffers.add("Henter Opgave Tilbud");

        listView.setVisibility(View.GONE);

        Resfreshopgavetilbud = (ImageButton) rod.findViewById(R.id.Resfreshopgavetilbud);
        Resfreshopgavetilbud.setOnClickListener(this);
        fjernalleopgaver = (ImageButton) rod.findViewById(R.id.fjernalleopgaver);
        fjernalleopgaver.setOnClickListener(this);
        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);


        return rod;

    }
    public void lavopgavelistetimedoffers(final JSONArray OpgaverJSONArray) {
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.listviewlayout, R.id.tider, timedoffers) {
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Afvis = (Button) view.findViewById(R.id.Afvis);
                Godkend = (Button) view.findViewById(R.id.Godkend);

                TextView tider = (TextView) view.findViewById(R.id.tider);
                TextView opgavebeskrivelse = (TextView) view.findViewById(R.id.opgavebeskrivelse);
                try {
                    tider.setText(OpgaverJSONArray.getJSONObject(position).getString("starttime")+"\n"+ "     |" +"\n"+
                            OpgaverJSONArray.getJSONObject(position).getString("endtime"));
                    opgavebeskrivelse.setText("Bestillingstype: " + OpgaverJSONArray.getJSONObject(position).getString("order_type") + "\n"
                            + "Dato: " + OpgaverJSONArray.getJSONObject(position).getString("order_date") + "\n"
                            + "institution: " + OpgaverJSONArray.getJSONObject(position).getString("institution") + "\n"
                            + "Adresse: " + OpgaverJSONArray.getJSONObject(position).getString("street")
                            + " " + OpgaverJSONArray.getJSONObject(position).getString("etage")
                            + " " + OpgaverJSONArray.getJSONObject(position).getString("postCode")
                            + " " + OpgaverJSONArray.getJSONObject(position).getString("city") + "\n"
                            + " " + (!OpgaverJSONArray.getJSONObject(position).getString("antal_sider").equals("0") ? OpgaverJSONArray.getJSONObject(position).getString("antal_sider") :"") + "\n"
                    );
                } catch (JSONException e) {
                    System.out.println("ingen opgaver");
                }

                Godkend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                                Toast.makeText(getActivity(), data.setacceptoffer(OpgaverJSONArray.getJSONObject(position).getString("order_tilbud_24_id"), "timedoffer"), Toast.LENGTH_SHORT).show();
                            Resfreshopgavetilbud.callOnClick();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        timedoffers.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                Afvis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if(data.setrejectedoffer(OpgaverJSONArray.getJSONObject(position).getString("order_tilbud_24_id"), "timedoffer")){
                                Toast.makeText(getActivity(), "Opgaven er afvist", Toast.LENGTH_SHORT).show();
                            }
                            Resfreshopgavetilbud.callOnClick();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        timedoffers.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
    public void lavopgavelisteopenoffers(final JSONArray OpgaverJSONArray) {
        adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.listviewlayout, R.id.tider, Openoffers) {
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Afvis = (Button) view.findViewById(R.id.Afvis);
                Godkend = (Button) view.findViewById(R.id.Godkend);
                try {

                    if (OpgaverJSONArray.getJSONObject(position).getString("packet_id").equals("")) {
                        TextView tider = (TextView) view.findViewById(R.id.tider);
                        TextView opgavebeskrivelse = (TextView) view.findViewById(R.id.opgavebeskrivelse);
                        view.findViewById(R.id.farve).setBackgroundResource(R.color.groupcolor);

                        tider.setText(OpgaverJSONArray.getJSONObject(position).getString("starttime") + "\n" + "     |" + "\n" +
                                OpgaverJSONArray.getJSONObject(position).getString("endtime"));
                        opgavebeskrivelse.setText("Bestillingstype: " + OpgaverJSONArray.getJSONObject(position).getString("order_type") + "\n"
                                + "Dato: " + OpgaverJSONArray.getJSONObject(position).getString("order_date") + "\n"
                                + "institution: " + OpgaverJSONArray.getJSONObject(position).getString("institution") + "\n"
                                + "Adresse: " + OpgaverJSONArray.getJSONObject(position).getString("street")
                                + " " + OpgaverJSONArray.getJSONObject(position).getString("etage")
                                + " " + OpgaverJSONArray.getJSONObject(position).getString("postCode")
                                + " " + OpgaverJSONArray.getJSONObject(position).getString("city") + "\n"
                                + " " + (!OpgaverJSONArray.getJSONObject(position).getString("antal_sider").equals("0") ? OpgaverJSONArray.getJSONObject(position).getString("antal_sider") :"") + "\n");
                        view.setVisibility(View.VISIBLE);

                    }
                    else{
                        if(!Packed_id.contains(OpgaverJSONArray.getJSONObject(position).getString("packet_id")) || packet_id_number.contains(position)) {
                            Packed_id.add(OpgaverJSONArray.getJSONObject(position).getString("packet_id"));
                            packet_id_number.add(position);
                            TextView tider = (TextView) view.findViewById(R.id.tider);
                            view.findViewById(R.id.farve).setBackgroundResource(R.color.normalcolor);
                            TextView opgavebeskrivelse = (TextView) view.findViewById(R.id.opgavebeskrivelse);
                            tider.setText(OpgaverJSONArray.getJSONObject(position).getString("starttime") + "\n" + "     |" + "\n" +
                                    OpgaverJSONArray.getJSONObject(position).getString("endtime"));


                            String message = "" ;
                            for(int i = 0; i < OpgaverJSONArray.length(); i++){
                                if(OpgaverJSONArray.getJSONObject(position).getString("packet_id").equals(OpgaverJSONArray.getJSONObject(i).getString("packet_id"))){
                                    message = message + "Bestillingstype: " + OpgaverJSONArray.getJSONObject(i).getString("order_type") + "\n" +
                                            "Dato: " + OpgaverJSONArray.getJSONObject(i).getString("order_date") + "\n"
                                            + "institution: " + OpgaverJSONArray.getJSONObject(i).getString("institution") + "\n"
                                            + "Adresse: " + OpgaverJSONArray.getJSONObject(i).getString("street")
                                            + " " + OpgaverJSONArray.getJSONObject(i).getString("etage")
                                            + " " + OpgaverJSONArray.getJSONObject(i).getString("postCode")
                                            + " " + OpgaverJSONArray.getJSONObject(i).getString("city") +  "\n"
                                            + (!OpgaverJSONArray.getJSONObject(position).getString("antal_sider").equals("0") ? OpgaverJSONArray.getJSONObject(position).getString("antal_sider") :"") + "\n" +
                                            "\n" + "\n";
                                }
                            }


                            opgavebeskrivelse.setText(message);
                            view.setVisibility(View.VISIBLE);
                        }
                        else{
                        view.setVisibility(View.GONE);

                        }
                    }
                }
                catch(JSONException e){
                    System.out.println("ingen opgaver");
                }

                Godkend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if(OpgaverJSONArray.getJSONObject(position).getString("packet_id").equals("")){
                                Toast.makeText(getActivity(), data.setacceptoffer(OpgaverJSONArray.getJSONObject(position).getString("order_tilbud_24_id"),"openoffer"), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Bundle bundle = getActivity().getIntent().getExtras();

                                Toast.makeText(getActivity(), data.setacceptofferingroup(OpgaverJSONArray.getJSONObject(position).getString("order_tilbud_24_id"),"openoffer",bundle.getString("UserID"),OpgaverJSONArray.getJSONObject(position).getString("packet_id")), Toast.LENGTH_SHORT).show();
                            }
                            Resfreshopgavetilbud.callOnClick();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Openoffers.remove(position);
                        adapter2.notifyDataSetChanged();
                    }
                });
                Afvis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if(!OpgaverJSONArray.getJSONObject(position).getString("packet_id").equals("")){
                                for(int i = 0; i < OpgaverJSONArray.length(); i++){
                                    if(OpgaverJSONArray.getJSONObject(position).getString("packet_id").equals(OpgaverJSONArray.getJSONObject(i).getString("packet_id"))){
                                        data.setrejectedoffer(OpgaverJSONArray.getJSONObject(i).getString("order_tilbud_24_id"), "openoffer");
                                    }
                                }
                            }
                            else {
                                if (data.setrejectedoffer(OpgaverJSONArray.getJSONObject(position).getString("order_tilbud_24_id"), "openoffer")) {
                                    Toast.makeText(getActivity(), "Opgaven er afvist", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Resfreshopgavetilbud.callOnClick();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Openoffers.remove(position);
                        adapter2.notifyDataSetChanged();
                    }
                });
                return view;
            }
        };
        listView2.setAdapter(adapter2);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);
    }


    private void Hentopenoffers() {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Bundle bundle = getActivity().getIntent().getExtras();
                    ArrayList<String> openofferstemp = data.GetOpenoffer(bundle.getString("UserID"));
                    if(openofferstemp.size() != 0){
                        Openoffers.clear();
                        for(int i =0; i< openofferstemp.size(); i++){
                            Openoffers.add(openofferstemp.get(i));
                        }
                    }
                    else{
                        Openoffers.clear();
                        Openoffers.add("Ingen Tilbud");
                    }
                    return "";
                } catch (Exception e) {
                    Openoffers.clear();
                    Openoffers.add("Kan ikke hente opgavetilbud, tjek internet forbindelse");
                    return "opgavetilbud blev ikke hentet korrekt";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                if(data.GetOpenoffer) {
                    data.GetOpenoffer=false;
                    lavopgavelisteopenoffers(data.GetOpenofferSONArray);
                    adapter2.notifyDataSetChanged();
                    try{
                    }
                    catch (NullPointerException e){}
                }
                statushenteropgave =false;
                Resfreshopgavetilbud.animate().cancel();
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }
    private void Henttimedoffers() {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Bundle bundle = getActivity().getIntent().getExtras();
                    ArrayList<String> openofferstemp = data.Gettimeoffer(bundle.getString("UserID"));
                    if(openofferstemp.size() != 0){
                        timedoffers.clear();
                        for(int i =0; i< openofferstemp.size(); i++){
                            timedoffers.add(openofferstemp.get(i));
                        }
                    }
                    else{
                        timedoffers.clear();
                        timedoffers.add("Ingen Tilbud");
                    }
                    return "";
                } catch (Exception e) {
                    timedoffers.clear();
                    timedoffers.add("Kan ikke hente opgavetilbud, tjek internet forbindelse");
                    return "opgavetilbud blev ikke hentet korrekt";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                if(data.Gettimeoffer) {
                    data.Gettimeoffer=false;
                    lavopgavelistetimedoffers(data.GettimeofferSONArray);
                    adapter.notifyDataSetChanged();
                    try{
                    }
                    catch (NullPointerException e){}
                }
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == Resfreshopgavetilbud){
            Resfreshopgavetilbud.animate().setInterpolator(new LinearInterpolator()).rotation(43200).setStartDelay(0).setDuration(120000).start();
            if(!statushenteropgave) {
                Packed_id.clear();
                packet_id_number.clear();
                statushenteropgave = true;
                listView.setAdapter(adapter);
                Hentopenoffers();
                Henttimedoffers();
            }
        }
        if(v == fjernalleopgaver){
            try {
                for (int i = 0; i < data.GetOpenofferSONArray.length(); i++) {
                    data.setrejectedoffer(data.GetOpenofferSONArray.getJSONObject(i).getString("order_tilbud_24_id"), "openoffer");
                }
                Packed_id.clear();
                packet_id_number.clear();
                listView.setAdapter(adapter);
                Hentopenoffers();
                Henttimedoffers();
            }catch(Exception e){
                System.out.println("");
            }
        }
    }


}