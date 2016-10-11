package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TabHost;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;

public class Datovaelger_Fragment extends DialogFragment implements View.OnClickListener{

    private OnDateRangeSelectedListener onDateRangeSelectedListener;

    private TabHost tabHost;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private Button butSetDateRange;
    boolean is24HourMode;
    private int end;

    public Datovaelger_Fragment() {
        // Required empty public constructor
    }

    public static Datovaelger_Fragment newInstance(OnDateRangeSelectedListener callback, boolean is24HourMode, int end) {
        Datovaelger_Fragment dateRangePickerFragment = new Datovaelger_Fragment();
        dateRangePickerFragment.initialize(callback, is24HourMode, end);
        return dateRangePickerFragment;
    }

    public void initialize(OnDateRangeSelectedListener callback, boolean is24HourMode, int end) {
        onDateRangeSelectedListener = callback;
        this.is24HourMode = is24HourMode;
        this.end = end;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.datovaelger_view, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        tabHost = (TabHost) root.findViewById(R.id.tabHost);
        butSetDateRange = (Button) root.findViewById(R.id.but_set_time_range);
        startDatePicker = (DatePicker) root.findViewById(R.id.start_date_picker);
        endDatePicker = (DatePicker) root.findViewById(R.id.end_date_picker);
        butSetDateRange.setOnClickListener(this);
        tabHost.findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec startDatePage = tabHost.newTabSpec("start");
        startDatePage.setContent(R.id.start_date_group);
        startDatePage.setIndicator(getString(R.string.title_tab_start_date));

        TabHost.TabSpec endDatePage = tabHost.newTabSpec("end");
        endDatePage.setContent(R.id.end_date_group);
        endDatePage.setIndicator(getString(R.string.ttile_tab_end_date));
        tabHost.addTab(startDatePage);
        tabHost.addTab(endDatePage);
        if(end == 1){
            tabHost.setCurrentTab(1);
        }
        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    public void setOnDateRangeSelectedListener(OnDateRangeSelectedListener callback) {
        this.onDateRangeSelectedListener = callback;
    }

    @Override
    public void onClick(View v) {
        System.out.println(v.toString());
        dismiss();
        onDateRangeSelectedListener.onDateRangeSelected(startDatePicker.getDayOfMonth(),startDatePicker.getMonth(),startDatePicker.getYear(),
                endDatePicker.getDayOfMonth(),endDatePicker.getMonth(),endDatePicker.getYear());
    }

    public interface OnDateRangeSelectedListener {
        void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear);
    }

}
