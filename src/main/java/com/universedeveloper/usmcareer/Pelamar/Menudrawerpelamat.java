package com.universedeveloper.usmcareer.Pelamar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.universedeveloper.usmcareer.Login;
import com.universedeveloper.usmcareer.R;
import com.universedeveloper.usmcareer.Tab1;

public class Menudrawerpelamat extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public final static String TAG_ID_PELAMAR = "id_user";
    SharedPreferences sharedpreferences;
    String id_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudrawerpelamat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id_user = getIntent().getStringExtra("id_user");
        Toast.makeText(this, "id "+ id_user, Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menudrawerpelamat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
       /// if (item.getItemId()==R.id.add)
           /// startActivity(new Intent(Menudrawerpelamat.this, EditProfilePelamar.class));
        //noinspection SimplifiableIfStatement
        ///if (id == R.id.action_settings) {
           /// return true;
        ///}

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {


        } else if (id == R.id.nav_ProfilPelamar) {
            Intent intent = new Intent(Menudrawerpelamat.this, ProfilePelamar.class);
            intent.putExtra(TAG_ID_PELAMAR, id_user);
            startActivity(intent);
        } else if (id == R.id.nav_pendidikan) {
            Intent intent = new Intent(Menudrawerpelamat.this, InputDataPendidikan.class);
            intent.putExtra(TAG_ID_PELAMAR, id_user);
            startActivity(intent);
        } else if (id == R.id.nav_AplikasiSaya) {
            Intent intent = new Intent(Menudrawerpelamat.this, AplikasiSayaPelamar.class);
            startActivity(intent);
        } else if (id == R.id.nav_Keluar) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(Tab1.session_status, false);
                    editor.putString(TAG_ID_PELAMAR, null);

                    editor.apply();

                    Intent intent = new Intent(Menudrawerpelamat.this, Login.class);
                     intent.putExtra(TAG_ID_PELAMAR, id_user);
                    finish();
                    startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
