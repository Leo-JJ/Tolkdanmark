package com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Kalender_aendringer_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Signatur_activity_demo;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Tidsvaelger_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Tolkbilag1_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Zeshan on 11-01-2016.
 */
public class popupwindows {

    static View layout;
    private RelativeLayout screen;
    private PopupWindow pwindo;
    private GoogleMap googleMap;
    private ImageButton luk, btnClosePopup, navigere, information, tolkebilag, anullerbilag, underskrivbilag;
    private EditText klientsnavn, cpr, sprog, dato, tidfra, tidtil, tolkebruger, tolk, fraktura;
    private FragmentActivity activity;
    private Fragmentmanager fragments = new Fragmentmanager();
    private MarkerOptions markerOptions;
    private LatLng latLng, myPosition;
    private TextView beskedhead, beskedindhold;
    private JSONObject object;
    private double latitude = 0;
    private double longitude = 0;
    private double adlat = 0, adlong = 0;
    private boolean sluttidkun = false;
    public MyLocationListener listener;


    public void initiatePopupWindow(String adresse, FragmentActivity activity) {
        this.activity = activity;
        Toast.makeText(activity, "Finder adresse", Toast.LENGTH_SHORT).show();
        try {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (layout == null) {
                layout = inflater.inflate(R.layout.googlemapspopup, (ViewGroup) activity.findViewById(R.id.popup_maps));
            }
            pwindo = new PopupWindow(layout, activity.getResources().getDisplayMetrics().widthPixels - (activity.getResources().getDisplayMetrics().widthPixels / 15), activity.getResources().getDisplayMetrics().heightPixels - ((activity.getResources().getDisplayMetrics().heightPixels / 20) * 3), true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            luk = (ImageButton) layout.findViewById(R.id.afslut);
            luk.setOnClickListener(cancel_button_click_listener);

            if (googleMap == null) {
                googleMap = ((MapFragment) activity.getFragmentManager().findFragmentById(R.id.map2)).getMap();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.706482, 12.462798), 13));
            }
            googleMap.clear();
            new GeocoderTask().execute(adresse);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Kan ikke hente adresse", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();
        }
    };

    public void initiatePopupWindow(String head, String body, final FragmentActivity activity) {
        try {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.beskeder_popup_fragment, (ViewGroup) activity.findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, activity.getResources().getDisplayMetrics().widthPixels - (activity.getResources().getDisplayMetrics().widthPixels / 15), activity.getResources().getDisplayMetrics().heightPixels - ((activity.getResources().getDisplayMetrics().heightPixels / 20) * 3), true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            beskedhead = (TextView) layout.findViewById(R.id.beskedhead);
            beskedindhold = (TextView) layout.findViewById(R.id.beskedindhold);
            beskedhead.setText(head);
            beskedindhold.setText(body);

            btnClosePopup = (ImageButton) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener2);

            beskedindhold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(beskedindhold.getText());
                    Toast.makeText(activity, "Information kopieret", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener2 = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initiatePopupWindowbilagindhold(final FragmentActivity activity) {
        try {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.bilagindhold_view, (ViewGroup) activity.findViewById(R.id.bilagindhold));
            pwindo = new PopupWindow(layout, activity.getResources().getDisplayMetrics().widthPixels - (activity.getResources().getDisplayMetrics().widthPixels / 15), activity.getResources().getDisplayMetrics().heightPixels - ((activity.getResources().getDisplayMetrics().heightPixels / 20) * 3), true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            LocationManager lm = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            listener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 1, listener);

            klientsnavn = (EditText) layout.findViewById(R.id.Klientensnavn);
            cpr = (EditText) layout.findViewById(R.id.CPR);
            sprog = (EditText) layout.findViewById(R.id.sprog);
            dato = (EditText) layout.findViewById(R.id.dato);
            tidfra = (EditText) layout.findViewById(R.id.tidfra);
            tidtil = (EditText) layout.findViewById(R.id.tidtil);
            tolkebruger = (EditText) layout.findViewById(R.id.Tolkebruger);
            tolk = (EditText) layout.findViewById(R.id.tolk);
            fraktura = (EditText) layout.findViewById(R.id.Fakturaoplysninger);
            anullerbilag = (ImageButton) layout.findViewById(R.id.annuller1);
            underskrivbilag = (ImageButton) layout.findViewById(R.id.Underskriv);


            klientsnavn.setText(object.getString("citizenName"));
            cpr.setText(object.getString("cpr"));
            sprog.setText(object.getString("language"));
            dato.setText(object.getString("order_date"));
            tolkebruger.setText(object.getString("bestilaf"));
            tolk.setText(object.getString("interpreter_name"));
            fraktura.setText("");

            anullerbilag.setOnClickListener(cancel_button_click_listener3);
            underskrivbilag.setOnClickListener(cancel_button_click_listener3);
            tidfra.setShowSoftInputOnFocus(false);
            tidtil.setShowSoftInputOnFocus(false);


            tidfra.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Tidsvaelger_fragment PickerFragment = Tidsvaelger_fragment.newInstance(new Tidsvaelger_fragment.OnTimeRangeSelectedListener() {
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
                                } else {
                                    tidtil.setText(time2 + ":" + min2);
                                }
                                tidfra.clearFocus();

                            }
                        }, true, 0);
                        PickerFragment.show(activity.getSupportFragmentManager(), "datePicker");
                        sluttidkun = false;
                    }
                }
            });

            tidtil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Tidsvaelger_fragment PickerFragment = Tidsvaelger_fragment.newInstance(new Tidsvaelger_fragment.OnTimeRangeSelectedListener() {
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
                                } else {
                                    tidtil.setText(time2 + ":" + min2);
                                }
                                tidtil.clearFocus();
                            }
                        }, true, 1);
                        PickerFragment.show(activity.getSupportFragmentManager(), "datePicker");
                        sluttidkun = true;
                    }
                }
            });

            tidfra.setText(object.getString("starttime"));
            tidtil.setText(object.getString("endtime"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener3 = new View.OnClickListener() {
        public void onClick(View v) {
            if (v == anullerbilag) {
                pwindo.dismiss();
            } else {
                try {
                    if (object.getString("is_tolk_bilag").equals("false")) {

                        if (object.getString("check_tolk_signature").equals("false")) {
                            if (v == underskrivbilag) {
                                LocationManager lm = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                                boolean gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                                Date strDate = null;
                                try {
                                    strDate = sdf.parse(object.getString("order_date") + " " + object.getString("starttime"));
                                } catch (Exception e) {
                                    Toast.makeText(activity.getBaseContext(), "En fejl på datoen er fundet, kontakt tolkdanmark", Toast.LENGTH_SHORT).show();
                                }
                                if (System.currentTimeMillis() > strDate.getTime() + 1 && strDate.getDate() == Calendar.getInstance().getTime().getDate() && strDate.getMonth() == Calendar.getInstance().getTime().getMonth() && strDate.getYear() == Calendar.getInstance().getTime().getYear()) {
                                    if (gps_enabled) {
                                        // API 23: we have to check if ACCESS_FINE_LOCATION and/or ACCESS_COARSE_LOCATION permission are granted
                                        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                                || ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                            Location locationNet = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                                            if (location != null) {
                                                latitude = location.getLatitude();
                                                longitude = location.getLongitude();
                                            } else {
                                                if (locationNet != null) {
                                                    latitude = locationNet.getLatitude();
                                                    longitude = locationNet.getLongitude();
                                                }
                                            }
                                        }
                                        if (adlat != 0 || adlong != 0) {
                                            if (distFrom(latitude, longitude, adlat, adlong) < 1000) {
                                                bilagobjekt a = new bilagobjekt(object.getString("id").toString(), klientsnavn.getText().toString(), cpr.getText().toString(), sprog.getText().toString(), dato.getText().toString(), tidfra.getText().toString()
                                                        , tidtil.getText().toString(), tolkebruger.getText().toString(),
                                                        tolk.getText().toString(), fraktura.getText().toString(),
                                                        object.getString("email"),
                                                        object.getString("interpreter_email"),
                                                        object.getString("reference_id"),
                                                        object.getString("institute"));
                                                Fragment fragment4 = new Signatur_activity_demo();
                                                Bundle args = new Bundle();
                                                args.putSerializable("bilagindholdet", a);
                                                fragment4.setArguments(args);
                                                activity.getSupportFragmentManager().beginTransaction().add(R.id.container, fragment4).commit();
                                                pwindo.dismiss();
                                            } else {
                                                Toast.makeText(activity.getBaseContext(), "Du skal befinde dig på området hvor tolkningen finder sted, før du kan underskrive", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(activity.getBaseContext(), "finder adressens position", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(activity.getBaseContext(), "Du skal slå gps til før du kan underskrive", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(activity.getBaseContext(), "Du kan kun underskrive opgaven på dagen og efter den er påbegyndt.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            bilagobjekt a = new bilagobjekt(object.getString("id").toString(), klientsnavn.getText().toString(), cpr.getText().toString(), sprog.getText().toString(), dato.getText().toString(), tidfra.getText().toString()
                                    , tidtil.getText().toString(), tolkebruger.getText().toString(),
                                    tolk.getText().toString(), fraktura.getText().toString(),
                                    object.getString("email"),
                                    object.getString("interpreter_email"),
                                    object.getString("reference_id"),
                                    object.getString("institute"));
                            Fragment fragment4 = new Signatur_activity_demo();
                            Bundle args = new Bundle();
                            args.putSerializable("bilagindholdet", a);
                            fragment4.setArguments(args);
                            activity.getSupportFragmentManager().beginTransaction().add(R.id.container, fragment4).commit();
                            pwindo.dismiss();
                        }
                    } else {
                        Toast.makeText(activity.getBaseContext(), "Bilaget er allerede underskrevet og sendt.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void initiatePopupWindowchoose(FragmentActivity activity, JSONObject object) {
        this.activity = activity;
        this.object = object;
        try {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.valgmuligheder_view, (ViewGroup) activity.findViewById(R.id.popup_element_first));
            pwindo = new PopupWindow(layout, activity.getResources().getDisplayMetrics().widthPixels - (activity.getResources().getDisplayMetrics().widthPixels / 15), activity.getResources().getDisplayMetrics().heightPixels - ((activity.getResources().getDisplayMetrics().heightPixels / 20) * 3), true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            screen = (RelativeLayout) layout.findViewById(R.id.popup_element_first);
            screen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                }
            });
            navigere = (ImageButton) layout.findViewById(R.id.navigere);
            information = (ImageButton) layout.findViewById(R.id.mereinformation);
            tolkebilag = (ImageButton) layout.findViewById(R.id.tolkebilag);

            navigere.setOnClickListener(choose_buttons);
            information.setOnClickListener(choose_buttons);
            tolkebilag.setOnClickListener(choose_buttons);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener choose_buttons = new View.OnClickListener() {
        public void onClick(View v) {
            if (v == navigere) {
                pwindo.dismiss();
                try {
                    initiatePopupWindow(object.getString("address"), activity);
                } catch (Exception e) {
                    System.out.println("popuperror");
                }
            }
            if (v == information) {
                pwindo.dismiss();
                try {
                    initiatePopupWindow("Tolkdanmark" + " [" + object.getString("reference_id") + "]",
                            "Bestillingstype: " + object.getString("order_type") + "\n" +
                                    "Sprog: " + object.getString("language") + "\n"
                                    + "Dato: " + object.getString("order_date") + "\n"
                                    + "Fra Kl. " + object.getString("starttime") + " til " + object.getString("endtime") + "\n" + "\n"

                                    + "Borger" + "\n"
                                    + "CPR Nr: " + object.getString("cpr") + "\n"
                                    + "Borgernavn: " + object.getString("citizenName") + "\n"

                                    + "Tlf. nr på borger: " + object.getString("phone_customer") + "\n"
                                    + "Besked til borger: " + object.getString("notes_customer") + "\n"


                                    + "Kommentar/Evt. fag" + "\n" + "\n"
                                    + "Noter: " + object.getString("desc") + "\n" + "\n"

                                    + "Kontakt" + "\n"
                                    + "Navn: " + object.getString("bestilaf") + "\n"
                                    + "Institution: " + object.getString("institute") + "\n"
                                    + "Adresse: " + object.getString("address") + "\n"


                            , activity);
                } catch (Exception e) {
                    System.out.println("popuperror");
                }
            }
            if (v == tolkebilag) {
                pwindo.dismiss();

                try {

                    if (object.getString("IsLager").equals("1")) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.getTolkbilag1fragment()).commit();
                        Toast.makeText(activity.getBaseContext(), "Læger Inholds Bilag", Toast.LENGTH_SHORT).show();
                    } else {
                        initiatePopupWindowbilagindhold(activity);
                    }

                    new getadress().execute(object.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(activity.getBaseContext(), "Tjek om oplysningerne er korrekte! ", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private class GeocoderTask extends AsyncTask<String, String, List<Address>> {
        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(activity.getBaseContext());
            List<Address> addresses = null;
            try {
                // Getting a maximum of 1 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(activity.getBaseContext(), "Adresse ikke fundet. ", Toast.LENGTH_SHORT).show();
            }
            try {
                // Adding Markers on Google Map for each matching address
                for (int i = 0; i < addresses.size(); i++) {
                    Address address = (Address) addresses.get(i);
                    // Creating an instance of GeoPoint, to display in Google Map
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    // Enabling MLocation Layer of Google Map
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                activity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                1);
                    }

                    String addressText = String.format("%s, %s",
                            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                            address.getCountryName());
                    markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(addressText);
                    googleMap.addMarker(markerOptions);

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity.getBaseContext(), "Ingen internet forbindelse ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    private class getadress extends AsyncTask<String, String, List<Address>> {
        @Override
        protected List<Address> doInBackground(String... locationName) {
            Geocoder geocoder = new Geocoder(activity.getBaseContext());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocationName(locationName[0], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses == null || addresses.size() == 0) {
            } else {
                adlat = addresses.get(0).getLatitude();
                adlong = addresses.get(0).getLongitude();
            }
        }
    }

    public class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(activity.getBaseContext(), "Du skal slå gps til før du kan underskrive", Toast.LENGTH_SHORT).show();
        }
    }
}
