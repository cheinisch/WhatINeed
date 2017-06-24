package de.christian_heinisch.hilferundumskfz;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static de.christian_heinisch.hilferundumskfz.R.id.adView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean mTwoPane;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Prüfung auf Tabletlayout
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // AdView einfügen (auch in der Entwicklungsversion)
        if(BuildConfig.FLAVOR.equalsIgnoreCase("lite") || BuildConfig.FLAVOR.equalsIgnoreCase("development")){
            mAdView = (AdView) findViewById(adView);
            /*mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId("ca-app-pub-1904028679449407/7256890770");*/
            adAd();
        }

        //Prüfen, ob noch die alte APP installiert ist
        if(BuildConfig.FLAVOR.equalsIgnoreCase("alt")){
            //Öffne DialogFragment
            Bundle args = new Bundle();
            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            // Create and show the dialog.
            DialogAlteVersionFragment newFragment = new DialogAlteVersionFragment();
            newFragment.setArguments(args);
            newFragment.show(ft, "dialog");
        }

        // Entferne Menüpunkte, die in div. Versionen nicht zur Verfügung stehen

        // ist die Flavor Version "lite" oder "development"
        if(BuildConfig.FLAVOR.equalsIgnoreCase("lite") || BuildConfig.FLAVOR.equalsIgnoreCase("development")) {
            // Setzte das Navigationsitem mit der ID 3 auf unsichtbar
            navigationView.getMenu().getItem(3).setVisible(false);
        }
        // Was brauche ich Fragment wird aufgerufen
        iNeed();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_information) {
            iNeed();

        }else if(id == R.id.nav_punkte){

            punkte();

        }else if (id == R.id.nav_errorcode) {
            errorcode();
        } else if (id == R.id.nav_about) {
            about();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void titelleiste(String title){

        // Titelleisten Text wird gesetzt
        setTitle(title);

    }

    public void errorcode() {

        // Lese Text für die Titelleiste aus der String.xml aus und übergebe diesen an Titelleiste setzten
        titelleiste(getString(R.string.error_titel));

        ErrorCodeFragment overviewFragment = new ErrorCodeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                overviewFragment,
                overviewFragment.getTag()
        )
                .addToBackStack(null)
                .commit();
    }

    public void iNeed() {

        titelleiste(getString(R.string.what_i_need_titel));

        WhatINeed_Fragment iNeedFragment = new WhatINeed_Fragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                iNeedFragment,
                iNeedFragment.getTag()
        )
                .addToBackStack(null)
                .commit();
    }

    public void punkte() {

        titelleiste(getString(R.string.punkte_titel));

        /*
        Fragment f = new PunkteFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_main, f);
        ft.addToBackStack(null);
        ft.commit();*/

        PunkteFragment aboutFragment = new PunkteFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                aboutFragment,
                aboutFragment.getTag()
        )
                .addToBackStack(null)
                .commit();

    }


    public void about() {

        titelleiste(getString(R.string.about_titel));

        AboutFragment aboutFragment = new AboutFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.content_main,
                aboutFragment,
                aboutFragment.getTag()
        )
                .addToBackStack(null)
                .commit();
    }

    // Funktionen für AdView
    private void adAd(){
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("CAE3699E16A6B13A6D6A9CBF37A6F1EA").build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.i("Ads", "onAdClosed");
            }
        });

    }
}
