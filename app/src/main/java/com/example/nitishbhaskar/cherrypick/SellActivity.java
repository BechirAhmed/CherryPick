package com.example.nitishbhaskar.cherrypick;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.firebase.client.Firebase;

public class SellActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, INavigate{
        ViewGroup mAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Sell Product");
        mAppBar = (ViewGroup)findViewById(R.id.appBarSell);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sellActivityContainer, SellFragment.newInstance(R.id.sellPageFragment))
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.Homepage) {
            intent = new Intent(this, MainActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this,this.mAppBar, "testTransition");
                startActivity(intent, options.toBundle());
            }
            else {
                startActivity(intent);
            }

        }
        else if (id == R.id.buyNavigation) {
            intent = new Intent(this, BuyActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this,this.mAppBar, "testTransition");
                startActivity(intent, options.toBundle());
            }
            else {
                startActivity(intent);
            }

        } else if (id == R.id.sellNavigation) {
            intent = new Intent(this,SellActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this,this.mAppBar, "testTransition");
                startActivity(intent, options.toBundle());
            }
            else {
                startActivity(intent);
            }

        } else if (id == R.id.exchangeNavigation) {

        }
        else if(id == R.id.logoutApp){
            Firebase ref = new Firebase(getString(R.string.firebaseUrl));
            ref.unauth();
            intent = new Intent(this,LoginActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this,this.mAppBar, "testTransition");
                startActivity(intent, options.toBundle());
            }
            else {
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
