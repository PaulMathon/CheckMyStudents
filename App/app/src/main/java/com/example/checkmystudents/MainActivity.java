package com.example.checkmystudents;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.checkmystudents.fragments.CoursesFragment;
import com.example.checkmystudents.activities.LoginActivity;
import com.example.checkmystudents.fragments.HelpFragment;
import com.example.checkmystudents.fragments.ProfileFragment;
import com.example.checkmystudents.fragments.SettingsFragment;
import com.example.checkmystudents.fragments.StudentsFragment;
import com.example.checkmystudents.fragments.ResumeFragment;
import com.example.checkmystudents.fragments.SignatureFragment;

public class MainActivity extends AppCompatActivity
        implements CoursesFragment.OnFragmentInteractionListener,
        StudentsFragment.OnFragmentInteractionListener,
        ResumeFragment.OnFragmentInteractionListener,
        SignatureFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        SettingsFragment.OnFragmentInteractionListener,
        HelpFragment.OnFragmentInteractionListener{

    private String teacherLogin;

    private DrawerLayout drawer;

    private boolean originFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        teacherLogin = getIntent().getExtras().getString("login");

        if (savedInstanceState == null ) {
            CoursesFragment fragment = CoursesFragment.newInstance(teacherLogin);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,fragment)
                    .addToBackStack(null)
                    .commit();
            originFragment = true;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        ProfileFragment.newInstance(teacherLogin)).commit();
                break;
            case R.id.nav_settings :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
            case R.id.nav_help :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpFragment()).commit();
                Log.d("INFO MENU", "T'es cliqué sur l'item d'aide");
                break;
            case R.id.nav_logout :
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (originFragment) {
                Log.d("iNFO","T'es dans le if");
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            } else {
                Log.d("iNFO","T'es dans le else");
                CoursesFragment fragment = CoursesFragment.newInstance(teacherLogin);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }

    }

    public String getMyData() {
        Log.d("Login", teacherLogin);
        return teacherLogin;
    }

    @Override
    public void onFragmentInteraction(boolean fragmentStatus) {
        originFragment = fragmentStatus;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResume() {
        super.onResume();
        CoursesFragment fragment = CoursesFragment.newInstance(teacherLogin);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_in,R.anim.activity_out,R.anim.activity_in,R.anim.activity_out)
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit();
    }


}
