package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Dagens_Opgaver_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Godkendelse_Opgaver_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Kontankt_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Profil_fragmant;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Velkommen_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.Fragmentmanager;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Beskeder_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Kalender_fragment;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

/**
 * Created by Zeshan on 21-12-2015.
 */
public class Starts_menu_navi_Activity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private Fragmentmanager fragments = new Fragmentmanager();
    int backpress=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        ab.setTitle(" ");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);

        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        String[] values = new String[]{
                "Velkommen",
                "Dagens Opgaver",
                "Kalender",
                "Godkend Opgaver",
                "Beskeder",
                "Tolke Bilag",
                "Min Profil",
                "Kontakt os",
                "Log ud"


        };
        final int[] billedliste = new int[]{
                R.drawable.ic_group_work_white_24dp,
                R.drawable.ic_assignment_white_24dp,
                R.drawable.ic_today_white_24dp,
                R.drawable.ic_work_white_24dp,
                R.drawable.ic_message_white_24dp,
                R.drawable.ic_create_white_24dp,
                R.drawable.ic_person_white_24dp,
                R.drawable.ic_live_help_white_24dp,
                R.drawable.ic_exit_to_app_white_24dp


        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.billede_med_tekst, R.id.tvSimpletext, values){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                ImageView billede = (ImageView) view.findViewById(R.id.imageView2);
                billede.setImageResource(billedliste[position]);
                return view;
            }
        };
        mDrawerList.setAdapter(adapter);
        mDrawerToggle.setAnimateEnabled(true);
        mDrawerToggle.syncState();
        drawerArrow.setColor(R.color.test2);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.getVelkommenfragment()).commit();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Velkommen_fragment()).addToBackStack(new Velkommen_fragment().getTag()).commit();
                            mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Dagens_Opgaver_fragment()).addToBackStack(new Dagens_Opgaver_fragment().getTag()).commit();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Kalender_fragment()).addToBackStack(new Kalender_fragment().getTag()).commit();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Godkendelse_Opgaver_fragment()).addToBackStack(new Godkendelse_Opgaver_fragment().getTag()).commit();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Beskeder_fragment()).addToBackStack(new Beskeder_fragment().getTag()).commit();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 5:
                        //Fragment fragment4 = new Signatur_activity_demo();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment4).addToBackStack(fragment4.getTag()).commit();
                        Toast.makeText(getApplicationContext(), "Muligheden for at se underskrevede bilag kommer snart", Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 6:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Profil_fragmant()).addToBackStack(new Profil_fragmant().getTag()).commit();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 7:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Kontankt_fragment()).addToBackStack(new Kontankt_fragment().getTag()).commit();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 8:
                        Intent intent = new Intent(getBaseContext(), Login_activity.class);
                        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor2 = sharedpreferences.edit();
                        editor2.putString("username", "");
                        editor2.putString("password", "");
                        editor2.commit();
                        Starts_menu_navi_Activity.this.finish();
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onBackPressed(){
            if (!getSupportFragmentManager().popBackStackImmediate()) {
                backpress++;
                if (backpress > 1) {
                    this.finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
                Toast.makeText(getApplicationContext(), " Tryk tilbage igen for at lukke app", Toast.LENGTH_SHORT).show();
            }
    }
}

