package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.Fragmentmanager;

import org.json.JSONObject;

/**
 * Created by Jiahua on 26-09-2016.
 */

public class Tolkbilag2_fragment extends Fragment implements Datovaelger_Fragment.OnDateRangeSelectedListener, Tidsvaelger_fragment.OnTimeRangeSelectedListener {

    private Spinner tolkningtype, ydelsesomfang, ydelsestype;
    private TextView textView1, textView2; //Slettes senere det er kun for at teste
    private EditText dato, fratid, tiltid, sprog;
    private boolean sluttidkun = false;
    private Fragmentmanager fragments = new Fragmentmanager();
    public static JSONObject object;
    private Button next = null;
    private String s1, s2, s3, s4;
    private int[] val4 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.tolkbilag2_view, container, false);
        next = (Button) rod.findViewById(R.id.next);

        tolkningtype = (Spinner) rod.findViewById(R.id.spinnerTolkningtype);
        ydelsesomfang = (Spinner) rod.findViewById(R.id.spinnerOmfang);
        ydelsestype = (Spinner) rod.findViewById(R.id.spinnerType);
        textView1 = (TextView) rod.findViewById(R.id.textView1); //Slettes senere, det er kun for at test
        textView2 = (TextView) rod.findViewById(R.id.textView2); //Slettes senere, det er kun for at test
        sprog = (EditText) rod.findViewById(R.id.Sprog);
        dato = (EditText) rod.findViewById(R.id.Dato);
        fratid = (EditText) rod.findViewById(R.id.startTid);
        tiltid = (EditText) rod.findViewById(R.id.slutTid);

        try {
            dato.setText(object.getString("order_date"));
            fratid.setText(object.getString("starttime"));
            tiltid.setText(object.getString("endtime"));
            sprog.setText(object.getString("language"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] tolkforbindelse = new String[]{
                "Valg",
                "Ambulant besøg",
                "Førstegangstolkning under ét indlæggelsesforløb",
                "Senere tolkning under ét índlæggelsesfprløb"
        };
        final int[] val1 = {0, 1, 2, 3};

        String[] ydelsensomfang = new String[]{
                "Valg",
                "Planlagt tolkning 08-17 hverdage",
                "Planlagt tolkning 17-08 hverdage",
                "Akuttolkning 08-17 hverdage",
                "Akuttolkning 17-08 hverdage",
                "Akuttolkning lør/søn/helligdage",
                "Patienten udeblevet",
                "Tolken udeblevet",
                "Tolkning aflyst indenfor 12 timer"
        };
        final int[] val2 = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        String[] ydelsenstype = new String[]{
                "Valg",
                "Konsultation",
                "Telefonkonsultation",
                "Webcamtolkning"
        };
        final int[] val3 = {0, 1, 2, 3};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tolkforbindelse);
        tolkningtype.setAdapter(adapter);
        tolkningtype.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s1 = String.valueOf(val1[position]);
                textView1.setText(s1); //Slettes senere, det er kun for at test
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ydelsensomfang);
        ydelsesomfang.setAdapter(adapter1);
        ydelsesomfang.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s2 = String.valueOf(val2[position]);
                valueUpdate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ydelsenstype);
        ydelsestype.setAdapter(adapter2);
        ydelsestype.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s3 = String.valueOf(val3[position]);
                valueUpdate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == next && s1 == "0") {
                    Toast.makeText(getActivity(), "Vælge venligst tolkforbindelse", Toast.LENGTH_SHORT).show();
                } else if (v == next && s2 == "0") {
                    Toast.makeText(getActivity(), "Vælge venligst ydelsensomfang", Toast.LENGTH_SHORT).show();
                } else if (v == next && s3 == "0") {
                    Toast.makeText(getActivity(), "Vælge venligst ydelsenstype", Toast.LENGTH_SHORT).show();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragments.getTolkbilag3fragment()).addToBackStack(fragments.getTolkbilag3fragment().getTag()).commit();
                }
            }
        });
        dato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Datovaelger_Fragment dateRangePickerFragment = Datovaelger_Fragment.newInstance(Tolkbilag2_fragment.this, true, 0);
                    dateRangePickerFragment.show(getFragmentManager(), "datePicker");
                }
            }
        });
        fratid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Tidsvaelger_fragment PickerFragment = Tidsvaelger_fragment.newInstance(Tolkbilag2_fragment.this, true, 0);
                    PickerFragment.show(getFragmentManager(), "datePicker");
                    sluttidkun = false;
                }
            }
        });

        tiltid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Tidsvaelger_fragment PickerFragment = Tidsvaelger_fragment.newInstance(Tolkbilag2_fragment.this, true, 1);
                    PickerFragment.show(getFragmentManager(), "datePicker");
                    sluttidkun = true;
                }
            }
        });

        return rod;
    }

    public void valueUpdate() {
        if (s2 == "1" && s3 == "1") {
            s4 = String.valueOf(val4[1]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "1" && s3 == "2") {
            s4 = String.valueOf(val4[2]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "1" && s3 == "3") {
            s4 = String.valueOf(val4[3]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "2" && s3 == "1") {
            s4 = String.valueOf(val4[4]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "2" && s3 == "2") {
            s4 = String.valueOf(val4[5]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "2" && s3 == "3") {
            s4 = String.valueOf(val4[6]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "3" && s3 == "1") {
            s4 = String.valueOf(val4[7]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "3" && s3 == "2") {
            s4 = String.valueOf(val4[8]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "3" && s3 == "3") {
            s4 = String.valueOf(val4[9]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "4" && s3 == "1") {
            s4 = String.valueOf(val4[10]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "4" && s3 == "2") {
            s4 = String.valueOf(val4[11]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "4" && s3 == "3") {
            s4 = String.valueOf(val4[12]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "5" && s3 == "1") {
            s4 = String.valueOf(val4[13]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "5" && s3 == "2") {
            s4 = String.valueOf(val4[14]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "5" && s3 == "3") {
            s4 = String.valueOf(val4[15]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "6" && s3 == "1") {
            s4 = String.valueOf(val4[16]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "6" && s3 == "2") {
            s4 = String.valueOf(val4[17]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "6" && s3 == "3") {
            s4 = String.valueOf(val4[18]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "7" && s3 == "1") {
            s4 = String.valueOf(val4[19]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "7" && s3 == "2") {
            s4 = String.valueOf(val4[20]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "7" && s3 == "3") {
            s4 = String.valueOf(val4[21]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "8" && s3 == "1") {
            s4 = String.valueOf(val4[22]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "8" && s3 == "2") {
            s4 = String.valueOf(val4[23]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        } else if (s2 == "8" && s3 == "3") {
            s4 = String.valueOf(val4[24]);
            textView2.setText(s4); //Slettes senere, det er kun for at test
        }
    }

    @Override
    public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        if (startDay > 9) {
            if ((startMonth + 1) > 9) {
                dato.setText(startDay + "-" + (startMonth + 1) + "-" + startYear);
            } else {
                dato.setText(startDay + "-" + "0" + (startMonth + 1) + "-" + startYear);
            }
        } else {
            if ((startMonth + 1) > 9) {
                dato.setText("0" + startDay + "-" + (startMonth + 1) + "-" + startYear);
            } else {
                dato.setText("0" + startDay + "-" + "0" + (startMonth + 1) + "-" + startYear);
            }
        }
    }

    @Override
    public void onTimeRangeSelected(int startHour, int startMin, int endHour, int endMin) {
        String time1 = String.valueOf(startHour), time2 = String.valueOf(endHour), min1 = String.valueOf(startMin), min2 = String.valueOf(endMin);
        if (startHour == 0 || startHour < 10) {
            time1 = ("0" + startHour);
        }
        if (endHour == 0 || endHour < 10) {
            time2 = ("0" + endHour);
        }
        if (startMin == 0 || startMin < 10) {
            min1 = ("0" + startMin);
        }
        if (endMin == 0 || endMin < 10) {
            min2 = ("0" + endMin);
        }
        if (!sluttidkun) {
            fratid.setText(time1 + ":" + min1);
            tiltid.setText(time2 + ":" + min2);
        } else {
            tiltid.setText(time2 + ":" + min2);
        }
    }
}