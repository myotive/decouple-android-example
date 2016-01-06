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
import android.widget.Toast;

import com.dynamit.decoupleandroid.fragments.NonDaggerFragment;
import com.dynamit.decoupleandroid.fragments.OttoFragment;
import com.dynamit.decoupleandroid.fragments.StarWarsFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.DeadEvent;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import retrofit.RetrofitError;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Injection
    @Inject
    Bus bus;

    // Views
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

        // Obtain reference to SampleApplication, grab the ApplicationComponent
        // and then inject the scoped Bus (makes Bus a Singleton).
        SampleApplication.getApplication(this).getApplicationComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register to bus through Android lifecycle
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister from bus through Android lifecycle to prevent unintended issues when
        // app is backgrounded or activity is destroyed.
        bus.unregister(this);
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
            changeFragments(StarWarsFragment.newInstance());
        }
        else if (id == R.id.nav_otto) {
            changeFragments(OttoFragment.newInstance());
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

    /**
     * Handle retrofit errors.
     * @param error
     */
    @Subscribe
    public void onRetrofitError(RetrofitError error){
        Log.i(TAG, "Retrofit Error");
        Toast.makeText(MainActivity.this, "Unexpected Network Error Occurred", Toast.LENGTH_SHORT).show();

    }

    /**
     * Handles when an event is raised but there are no subscribers on Bus.
     * @param deadEvent
     */
    @Subscribe
    public void onDeadEvent(DeadEvent deadEvent){

        String eventClassName = deadEvent.event.getClass().getSimpleName();
        Log.i(TAG, "onDeadEvent: " + eventClassName);
        Toast.makeText(MainActivity.this,
                "No Subscribers to " + eventClassName, Toast.LENGTH_SHORT)
                .show();
    }
}
