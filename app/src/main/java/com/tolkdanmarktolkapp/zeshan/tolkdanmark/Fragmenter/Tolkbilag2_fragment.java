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
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.bilagobjekt;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.regionbilagobjekt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by Jiahua on 26-09-2016.
 */

public class Tolkbilag2_fragment extends Fragment implements Datovaelger_Fragment.OnDateRangeSelectedListener, Tidsvaelger_fragment.OnTimeRangeSelectedListener {

    private Spinner spinnerForbindelse, spinnerOmfang, spinnerType;
    private TextView forbindelseValue, omfangValue; //Slettes senere det er kun for at teste
    private EditText dato, tidfra, tidtil, sprog;
    private boolean sluttidkun = false;
    private regionbilagobjekt regionbilagindholdet;
    private Fragmentmanager fragments = new Fragmentmanager();
    public static JSONObject object;
    private Button next = null;
    private String s1, s2, s3, s4;
    private int[] val4 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.tolkbilag2_view, container, false);

        try {
            next = (Button) rod.findViewById(R.id.next);

            spinnerForbindelse = (Spinner) rod.findViewById(R.id.spinnerForbindelse);
            spinnerOmfang = (Spinner) rod.findViewById(R.id.spinnerOmfang);
            spinnerType = (Spinner) rod.findViewById(R.id.spinnerType);
            forbindelseValue = (TextView) rod.findViewById(R.id.forbindelseValue); //Slettes senere, det er kun for at test
            omfangValue = (TextView) rod.findViewById(R.id.omfangValue); //Slettes senere, det er kun for at test
            sprog = (EditText) rod.findViewById(R.id.Sprog);
            dato = (EditText) rod.findViewById(R.id.Dato);
            tidfra = (EditText) rod.findViewById(R.id.startTid);
            tidtil = (EditText) rod.findViewById(R.id.slutTid);

            dato.setText(object.getString("order_date"));
            tidfra.setText(object.getString("starttime"));
            tidtil.setText(object.getString("endtime"));
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
        spinnerForbindelse.setAdapter(adapter);
        spinnerForbindelse.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s1 = String.valueOf(val1[position]);
                forbindelseValue.setText(s1); //Slettes senere, det er kun for at test
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ydelsensomfang);
        spinnerOmfang.setAdapter(adapter1);
        spinnerOmfang.setOnItemSelectedListener(new OnItemSelectedListener() {
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
        spinnerType.setAdapter(adapter2);
        spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {
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
                if (v == next && Objects.equals(s1, "0")) {
                    Toast.makeText(getActivity(), "Vælge venligst tolkforbindelse", Toast.LENGTH_SHORT).show();
                } else if (v == next && Objects.equals(s2, "0")) {
                    Toast.makeText(getActivity(), "Vælge venligst ydelsensomfang", Toast.LENGTH_SHORT).show();
                } else if (v == next && Objects.equals(s3, "0")) {
                    Toast.makeText(getActivity(), "Vælge venligst ydelsenstype", Toast.LENGTH_SHORT).show();
                } else {
                    regionbilagindholdet.setSprog(sprog.getText().toString());
                    regionbilagindholdet.setDato(dato.getText().toString());
                    regionbilagindholdet.setTidfra(tidfra.getText().toString());
                    regionbilagindholdet.setTidtil(tidtil.getText().toString());
                    regionbilagindholdet.setForbindelse(s1);
                    //regionbilagindholdet.setForbindelse(forbindelseValue.getText().toString());
                    regionbilagindholdet.setOmfang(s4);
                    //regionbilagindholdet.setOmfang(omfangValue.getText().toString());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("regionbilagindholdet", regionbilagindholdet);
                    fragments.getTolkbilag3fragment().setArguments(bundle);
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
        tidfra.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Tidsvaelger_fragment PickerFragment = Tidsvaelger_fragment.newInstance(Tolkbilag2_fragment.this, true, 0);
                    PickerFragment.show(getFragmentManager(), "datePicker");
                    sluttidkun = false;
                }
            }
        });

        tidtil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Tidsvaelger_fragment PickerFragment = Tidsvaelger_fragment.newInstance(Tolkbilag2_fragment.this, true, 1);
                    PickerFragment.show(getFragmentManager(), "datePicker");
                    sluttidkun = true;
                }
            }
        });

        if (getArguments() != null) {
            regionbilagindholdet = (regionbilagobjekt) getArguments().getSerializable("regionbilagindholdet");
        }

        return rod;
    }

    @Override
    public void onResume() {
        try {
            dato.setText(object.getString("order_date"));
            tidfra.setText(object.getString("starttime"));
            tidtil.setText(object.getString("endtime"));
            sprog.setText(object.getString("language"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    public void valueUpdate() {
        //String s4;
        if (Objects.equals(s2, "1") && Objects.equals(s3, "1")) {
            s4 = String.valueOf(val4[1]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "1") && Objects.equals(s3, "2")) {
            s4 = String.valueOf(val4[2]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "1") && Objects.equals(s3, "3")) {
            s4 = String.valueOf(val4[3]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "2") && Objects.equals(s3, "1")) {
            s4 = String.valueOf(val4[4]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "2") && Objects.equals(s3, "2")) {
            s4 = String.valueOf(val4[5]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "2") && Objects.equals(s3, "3")) {
            s4 = String.valueOf(val4[6]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "3") && Objects.equals(s3, "1")) {
            s4 = String.valueOf(val4[7]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "3") && Objects.equals(s3, "2")) {
            s4 = String.valueOf(val4[8]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "3") && Objects.equals(s3, "3")) {
            s4 = String.valueOf(val4[9]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "4") && Objects.equals(s3, "1")) {
            s4 = String.valueOf(val4[10]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "4") && Objects.equals(s3, "2")) {
            s4 = String.valueOf(val4[11]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "4") && Objects.equals(s3, "3")) {
            s4 = String.valueOf(val4[12]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "5") && Objects.equals(s3, "1")) {
            s4 = String.valueOf(val4[13]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "5") && Objects.equals(s3, "2")) {
            s4 = String.valueOf(val4[14]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "5") && Objects.equals(s3, "3")) {
            s4 = String.valueOf(val4[15]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "6") && Objects.equals(s3, "1")) {
            s4 = String.valueOf(val4[16]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "6") && Objects.equals(s3, "2")) {
            s4 = String.valueOf(val4[17]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "6") && Objects.equals(s3, "3")) {
            s4 = String.valueOf(val4[18]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "7") && Objects.equals(s3, "1")) {
            s4 = String.valueOf(val4[19]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "7") && Objects.equals(s3, "2")) {
            s4 = String.valueOf(val4[20]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "7") && Objects.equals(s3, "3")) {
            s4 = String.valueOf(val4[21]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "8") && Objects.equals(s3, "1")) {
            s4 = String.valueOf(val4[22]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "8") && Objects.equals(s3, "2")) {
            s4 = String.valueOf(val4[23]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
        } else if (Objects.equals(s2, "8") && Objects.equals(s3, "3")) {
            s4 = String.valueOf(val4[24]);
            omfangValue.setText(s4); //Slettes senere, det er kun for at test
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
            tidfra.setText(time1 + ":" + min1);
            tidtil.setText(time2 + ":" + min2);
        } else {
            tidtil.setText(time2 + ":" + min2);
        }
    }
}