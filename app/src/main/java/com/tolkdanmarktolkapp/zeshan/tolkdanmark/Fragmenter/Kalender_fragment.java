package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalender.CalendarView;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.popupwindows;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.DB_logik;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Kalender_fragment extends Fragment implements CalendarView.RobotoCalendarListener, View.OnClickListener {

    private CalendarView robotoCalendarView;
    private int currentMonthIndex;
    private Calendar currentCalendar;
    private Date dato;
    private ListView listview;
    private ArrayList<JSONObject> Dagensopgaver = new ArrayList<>();
    private ArrayList<String> dagensBeskeder = new ArrayList<>();
    private Calendar Today;
    private ImageButton idag,tilfoj;
    private ArrayAdapter adapter;
    private ArrayList<Date> datoer = new ArrayList<>();
    private com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.popupwindows popupwindows = new popupwindows();
    private DB_logik data = new DB_logik();


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.fragment_main, container, false);
        idag = (ImageButton) rod.findViewById(R.id.idag);
        idag.setOnClickListener(this);
        tilfoj = (ImageButton) rod.findViewById(R.id.tilfoj);
        tilfoj.setOnClickListener(this);
        // Gets the calendar from the view
        robotoCalendarView = (CalendarView) rod.findViewById(R.id.robotoCalendarPicker);
        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);
        // Initialize the RobotoCalendarPicker with the current index and date
        currentMonthIndex = 0;
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        // Mark current day
        Today=currentCalendar;
        dagensBeskeder.clear();
        Hentkalenderopgaver();
        robotoCalendarView.markDayAsCurrentDay(currentCalendar.getTime());
        Markdaysincurrentmunth(datoer, currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.YEAR));
        listview = (ListView) rod.findViewById(R.id.listView3);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if(Dagensopgaver.get(position).getString("institute").equals("Optaget")) {
                        Toast.makeText(getActivity(), "Du er optaget mellem "+
                                Dagensopgaver.get(position).getString("starttime")+"-"+ Dagensopgaver.get(position).getString("endtime") , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        popupwindows.initiatePopupWindowchoose(getActivity(), Dagensopgaver.get(position));
                        Tolkbilag1_fragment.object = Dagensopgaver.get(position);
                        Tolkbilag2_fragment.object = Dagensopgaver.get(position);
                        Tolkbilag3_fragment.object = Dagensopgaver.get(position);
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Kan ikke hente adresse", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.kalendercelldesign, R.id.opgavested, dagensBeskeder);
        listview.setAdapter(adapter);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        return rod;
    }

    public void visdagensopgaver(final Date dato) {
        Dagensopgaver.clear();
        dagensBeskeder.clear();
        try {
        for(int i = 0; i < data.allOpgaverJSONArray.length(); i++){
            String string = data.allOpgaverJSONArray.getJSONObject(i).getString("startdatetime");
            String[] parts = string.split("-");
            Date date1 = new GregorianCalendar(Integer.valueOf(parts[2].substring(0,4)),Integer.valueOf(parts[1])-1,Integer.valueOf(parts[0])).getTime();
                if(date1.getYear() == dato.getYear() && date1.getMonth() == dato.getMonth() && date1.getDate() == dato.getDate()){
                    Dagensopgaver.add(data.allOpgaverJSONArray.getJSONObject(i));
                    dagensBeskeder.add("Institut: " + data.allOpgaverJSONArray.getJSONObject(i).getString("institute") + "\n"
                            + "Adresse: " + data.allOpgaverJSONArray.getJSONObject(i).getString("address") + "\n");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.kalendercelldesign, R.id.opgavested, dagensBeskeder) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tider = (TextView) view.findViewById(R.id.tider);
                View farve = view.findViewById(R.id.farve);
                ImageView imagesing = (ImageView) view.findViewById(R.id.imageView4);
                TextView opgavested = (TextView) view.findViewById(R.id.opgavested);
                try {
                    tider.setText(Dagensopgaver.get(position).getString("starttime") + "\n" + "    |" + "\n" + Dagensopgaver.get(position).getString("endtime"));
                    farve.setBackgroundColor(Color.parseColor(Dagensopgaver.get(position).getString("color")));

                    if(Dagensopgaver.get(position).getString("is_tolk_bilag").equals("true")){
                        //imagesing.setBackgroundResource(R.drawable.ic_action_name);
                        imagesing.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_name));

                    }else{
                        imagesing.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_signature_nej));

                        //imagesing.setBackgroundResource(R.drawable.ic_signature_nej);

                    }
                    opgavested.setText("Institut: " + Dagensopgaver.get(position).getString("institute") + "\n"
                            + "Adresse: " + Dagensopgaver.get(position).getString("address") + "\n");
                } catch (Exception e) {
                    System.out.println("Fejl");
                }
                return view;
            }

        };
        listview.setAdapter(adapter);
    }

    private void Hentkalenderopgaver() {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Bundle bundle = getActivity().getIntent().getExtras();
                    ArrayList<String> opgavertemp = data.Getopgaver(bundle.getString("UserID"), Today);

                    for(int i =0; i< opgavertemp.size(); i++){
                        String string =opgavertemp.get(i);
                        String[] parts = string.split("-");
                        Date date1 = new GregorianCalendar(Integer.valueOf(parts[2].substring(0,4)),Integer.valueOf(parts[1])-1,Integer.valueOf(parts[0])).getTime();
                        datoer.add(date1);
                    }
                    return "";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Opgaverne blev ikke hentet korrekt";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                updateCalendar();
                Markdaysincurrentmunth(datoer, currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.YEAR));
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }

    @Override
    public void onDateSelected(Date date) {
        if(isdateselected(date)){
            dato = date;
            visdagensopgaver(date);
            adapter.notifyDataSetChanged();
            robotoCalendarView.markDayAsSelectedDay(date);
        }
        else {
            visdagensopgaver(date);
            adapter.notifyDataSetChanged();
            robotoCalendarView.markDayAsSelectedDay(date);
            dato =date;
        }
    }

    private boolean isdateselected(Date date) {
        for(int i =0; i<datoer.size();i++ ){
            if(date.getYear() == datoer.get(i).getYear() && date.getMonth() == datoer.get(i).getMonth() && date.getDate() == datoer.get(i).getDate()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        updateCalendar();
        Markdaysincurrentmunth(datoer, currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.YEAR));
    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        updateCalendar();
        Markdaysincurrentmunth(datoer, currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.YEAR));
    }

    private void updateCalendar() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        robotoCalendarView.initializeCalendar(currentCalendar);
        if(currentCalendar.getTime().getYear() == Today.getTime().getYear() && currentCalendar.getTime().getMonth() == Today.getTime().getMonth() && currentCalendar.getTime().getDate() == Today.getTime().getDate()){
            robotoCalendarView.markDayAsCurrentDay(Today.getTime());
        }
        else{
            robotoCalendarView.unmarkDayAsCurrentDay(Today.getTime());
        }
    }

    public void Markdaysincurrentmunth(ArrayList<Date> date, int currentmonth, int year){
        for (int i =0; i< date.size();i++){
            if(date.get(i).getMonth() == currentmonth && date.get(i).toString().split(" ")[date.get(i).toString().split(" ").length - 1].equals(""+year+"")  ) {
                robotoCalendarView.markFirstUnderlineWithStyle(R.color.RED_COLOR, date.get(i));
            }
        }

    }

    @Override
    public void onClick(View v) {
        if(v == idag){
            currentMonthIndex = 0;
            currentCalendar = Calendar.getInstance(Locale.getDefault());
            // Mark current day
            Today=currentCalendar;
            robotoCalendarView.markDayAsCurrentDay(currentCalendar.getTime());
            updateCalendar();
            Markdaysincurrentmunth(datoer, currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.YEAR));
        }
        else if(v == tilfoj){
            try {
                Bundle bundle = getActivity().getIntent().getExtras();
                String string = dato.toString();
                String[] parts = string.split(" ");
                Intent intent = new Intent(getActivity(), Kalender_aendringer_fragment.class);
                intent.putExtra("year", currentCalendar.get(Calendar.YEAR));
                intent.putExtra("month", currentCalendar.get(Calendar.MONTH) + 1);
                intent.putExtra("dayofmonth", parts[2]);
                intent.putExtra("UserID",bundle.getString("UserID"));
                startActivity(intent);
                getActivity().overridePendingTransition (R.anim.incoming, R.anim.outgoing);
            }
            catch(Exception e){
                Toast.makeText(getContext(), "Tryk på dato først", Toast.LENGTH_SHORT).show();
            }

        }
    }


}


