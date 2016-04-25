package com.example.nitishbhaskar.cherrypick;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ITileClickListener  {

    String name;
    String email;
    String photoUrl;

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

        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.MyPREFERENCES), Context.MODE_PRIVATE);
        Map<String,?> savedStrings = sharedpreferences.getAll();
        View navHeaderView = navigationView.getHeaderView(0);
        TextView username = (TextView) navHeaderView.findViewById(R.id.username);
        TextView userEmail = (TextView) navHeaderView.findViewById(R.id.useremail);
        ImageView profileImage = (ImageView) navHeaderView.findViewById(R.id.userProfileImage);
        username.setText((String)savedStrings.get(getString(R.string.Name)));
        userEmail.setText((String) savedStrings.get(getString(R.string.Email)));
        Picasso.with(getApplicationContext()).load((String) savedStrings.get(getString(R.string.ProfilePicUri))).into(profileImage);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.mainActivityContainer, HomePageFragment.newInstance(R.id.homePageFragment))
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.Homepage) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.buyNavigation) {
            intent = new Intent(this, BuyActivity.class);
            startActivity(intent);

        } else if (id == R.id.sellNavigation) {


        } else if (id == R.id.exchangeNavigation) {

        }
        else if(id == R.id.logoutApp){
            Firebase ref = new Firebase(getString(R.string.firebaseUrl));
            ref.unauth();
            intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void tileClicked(int tileId) {
        Intent intent;
        switch (tileId) {
            case R.id.buyTile: //Navigates to Buy activity
                intent = new Intent(this, BuyActivity.class);
                startActivity(intent);
                break;
        }
    }

}
