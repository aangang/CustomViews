package com.aaangang.customviews;

import android.content.Intent;
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

import com.aaangang.customviews.activitys.MyViewForthPath;
import com.aaangang.customviews.activitys.MyViewFst;
import com.aaangang.customviews.activitys.MyViewScd;
import com.aaangang.customviews.activitys.MyViewTrd;
import com.aaangang.customviews.utils.Tools;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //穿入一个null参数，显示图标原本的色彩，不然显示为黑色
        navigationView.setItemIconTintList(null);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id == R.id.myview1) {
            Tools.log("myview1");
            Tools.toast(MainActivity.this,"myview 1");
            mIntent = new Intent(MainActivity.this, MyViewFst.class);
            startActivity(mIntent);
        }else if (id == R.id.myview2) {
            Tools.log("myview2");
            Tools.toast(MainActivity.this,"myview 2");
            mIntent = new Intent(MainActivity.this, MyViewScd.class);
            startActivity(mIntent);
        }else if (id == R.id.myview3) {
            Tools.log("myview3");
            Tools.toast(MainActivity.this,"myview 3");
            mIntent = new Intent(MainActivity.this, MyViewTrd.class);
            startActivity(mIntent);
        }else if (id == R.id.myview4) {
            Tools.log("myview4");
            Tools.toast(MainActivity.this,"myview 4");
            mIntent = new Intent(MainActivity.this, MyViewForthPath.class);
            startActivity(mIntent);
        }else if (id == R.id.myview5) {
            Tools.log("myview5");
            Tools.toast(MainActivity.this,"myview 5");
        }else if (id == R.id.myview6) {
            Tools.log("myview6");
            Tools.toast(MainActivity.this,"myview 6");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
