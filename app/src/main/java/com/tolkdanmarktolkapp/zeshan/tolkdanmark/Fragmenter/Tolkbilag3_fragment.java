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
import android.widget.TextView;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.Fragmentmanager;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.regionbilagobjekt;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jiahua on 27-09-2016.
 */

public class Tolkbilag3_fragment extends Fragment implements View.OnClickListener {

    private Spinner evaType1, evaType2, evaType3, evaType4;
    private EditText evaComment, laegeydernr;
    private TextView evaCounter1, evaCounter2, evaCounter3, evaCounter4; // Slettes senere, det er kun for at teste
    private Fragmentmanager fragments = new Fragmentmanager();
    private regionbilagobjekt regionbilagindholdet;
    private String e1, e2, e3, e4;
    private Button next;
    public static JSONObject object;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.tolkbilag3_view, container, false);

        next = (Button) rod.findViewById(R.id.next);
        next.setOnClickListener(this);

        evaType1 = (Spinner) rod.findViewById(R.id.evaType1);
        evaType2 = (Spinner) rod.findViewById(R.id.evaType2);
        evaType3 = (Spinner) rod.findViewById(R.id.evaType3);
        evaType4 = (Spinner) rod.findViewById(R.id.evaType4);

        evaComment = (EditText) rod.findViewById(R.id.evaComment);
        laegeydernr = (EditText) rod.findViewById(R.id.laegeydernr);

        // Slettes senere, det er kun for at teste
        evaCounter1 = (TextView) rod.findViewById(R.id.evaCounter1);
        evaCounter2 = (TextView) rod.findViewById(R.id.evaCounter2);
        evaCounter3 = (TextView) rod.findViewById(R.id.evaCounter3);
        evaCounter4 = (TextView) rod.findViewById(R.id.evaCounter4);

        try {
            laegeydernr.setText(object.getString(""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        evaComment.setText("");

        String[] eva1 = new String[]{
                "God",
                "Mindre god"
        };
        final int[] val1 = {1, 0};

        String[] eva2 = new String[]{
                "God",
                "Mindre god"
        };
        final int[] val2 = {1, 0};

        String[] eva3 = new String[]{
                "Ja",
                "Nej"
        };
        final int[] val3 = {1, 0};

        String[] eva4 = new String[]{
                "Ja",
                "Nej"
        };
        final int[] val4 = {1, 0};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, eva1);
        evaType1.setAdapter(adapter1);
        evaType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e1 = String.valueOf(val1[position]);
                evaCounter1.setText(e1); //Slettes senere, det er kun for at test
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, eva2);
        evaType2.setAdapter(adapter2);
        evaType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e2 = String.valueOf(val2[position]);
                evaCounter2.setText(e2); //Slettes senere, det er kun for at test
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, eva3);
        evaType3.setAdapter(adapter3);
        evaType3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e3 = String.valueOf(val3[position]);
                evaCounter3.setText(e3); //Slettes senere, det er kun for at test
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, eva4);
        evaType4.setAdapter(adapter4);
        evaType4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e4 = String.valueOf(val4[position]);
                evaCounter4.setText(e4); //Slettes senere, det er kun for at test
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(getArguments() != null) {
            regionbilagindholdet = (regionbilagobjekt) getArguments().getSerializable("regionbilagindholdet");
        }

        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == next) {

            regionbilagindholdet.setLaegeeanr(laegeydernr.getText().toString());
            regionbilagindholdet.setEva1(e1);
            regionbilagindholdet.setEva2(e2);
            regionbilagindholdet.setEva3(e3);
            regionbilagindholdet.setEva4(e4);
            regionbilagindholdet.setEvaComment(evaComment.getText().toString());

            Bundle bundle = new Bundle();
            bundle.putSerializable("regionbilagindholdet", regionbilagindholdet);
            fragments.getLaegeunderskriftfragment().setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.container, fragments.getLaegeunderskriftfragment()).addToBackStack(fragments.getLaegeunderskriftfragment().getTag()).commit();
        }
    }

}