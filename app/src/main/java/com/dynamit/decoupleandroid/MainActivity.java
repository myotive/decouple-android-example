package com.dynamit.decoupleandroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.dynamit.decoupleandroid.fragments.DaggerFragment;
import com.dynamit.decoupleandroid.fragments.NonDaggerFragment;
import com.dynamit.decoupleandroid.fragments.OttoFragment;
import com.dynamit.decoupleandroid.network.api.TMDbAPI;
import com.squareup.otto.Bus;
import com.squareup.otto.DeadEvent;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    Bus bus;

    @Inject
    TMDbAPI tmDbAPI;

    RelativeLayout mainContent;

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

        mainContent = (RelativeLayout)findViewById(R.id.main_content);


        SampleApplication.getApplication(this).getApplicationComponent().inject(this);
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

        if(id == R.id.nav_non_dagger){
            changeFragments(NonDaggerFragment.newInstance());
        }
        else if(id == R.id.nav_dagger){
            changeFragments(DaggerFragment.newInstance());
        }
        else if (id == R.id.nav_otto) {
            changeFragments(OttoFragment.newInstance());
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragments(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(mainContent.getId(), fragment)
                .commit();
    }

    @Subscribe
    public void onDeadEvent(DeadEvent deadEvent){
        Log.i(TAG, "onDeadEvent: " + deadEvent.source.toString());
    }
}
