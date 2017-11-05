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

import com.aaangang.customviews.activitys.MyViewEightMitrix;
import com.aaangang.customviews.activitys.MyViewFivBezier;
import com.aaangang.customviews.activitys.MyViewForthPath;
import com.aaangang.customviews.activitys.MyViewFst;
import com.aaangang.customviews.activitys.MyViewGroupTest;
import com.aaangang.customviews.activitys.MyViewNighMitrixCamera;
import com.aaangang.customviews.activitys.MyViewScd;
import com.aaangang.customviews.activitys.MyViewSevSearchPath;
import com.aaangang.customviews.activitys.MyViewSixPathMeasure;
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
        fab.setVisibility(View.INVISIBLE);

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
            mIntent = new Intent(MainActivity.this, MyViewFivBezier.class);
            startActivity(mIntent);
        }else if (id == R.id.myview6) {
            Tools.log("myview6");
            Tools.toast(MainActivity.this,"myview 6");
            mIntent = new Intent(MainActivity.this, MyViewSixPathMeasure.class);
            startActivity(mIntent);
        }else if (id == R.id.myview7) {
            Tools.log("myview7");
            Tools.toast(MainActivity.this,"myview 7");
            mIntent = new Intent(MainActivity.this, MyViewSevSearchPath.class);
            startActivity(mIntent);
        }else if (id == R.id.myview8) {
            Tools.log("myview8");
            Tools.toast(MainActivity.this,"myview 8");
            mIntent = new Intent(MainActivity.this, MyViewEightMitrix.class);
            startActivity(mIntent);
        }else if (id == R.id.myview9) {
            Tools.log("myview9");
            Tools.toast(MainActivity.this,"myview 9");
            mIntent = new Intent(MainActivity.this, MyViewNighMitrixCamera.class);
            startActivity(mIntent);
        }

        else if (id == R.id.myview10) {
            Tools.log("myview10");
            Tools.toast(MainActivity.this,"myview 10");
            mIntent = new Intent(MainActivity.this, MyViewGroupTest.class);
            startActivity(mIntent);
        }

        else if (id == R.id.myview11) {
            Tools.log("myview1");
            Tools.toast(MainActivity.this,"myview 11");
            mIntent = new Intent(MainActivity.this, MyViewFst.class);
            startActivity(mIntent);
        }else if (id == R.id.myview12) {
            Tools.log("myview12");
            Tools.toast(MainActivity.this,"myview 12");
            mIntent = new Intent(MainActivity.this, MyViewScd.class);
            startActivity(mIntent);
        }else if (id == R.id.myview13) {
            Tools.log("myview13");
            Tools.toast(MainActivity.this,"myview 13");
            mIntent = new Intent(MainActivity.this, MyViewTrd.class);
            startActivity(mIntent);
        }else if (id == R.id.myview14) {
            Tools.log("myview14");
            Tools.toast(MainActivity.this,"myview 14");
            mIntent = new Intent(MainActivity.this, MyViewForthPath.class);
            startActivity(mIntent);
        }else if (id == R.id.myview15) {
            Tools.log("myview15");
            Tools.toast(MainActivity.this,"myview 15");
            mIntent = new Intent(MainActivity.this, MyViewFivBezier.class);
            startActivity(mIntent);
        }else if (id == R.id.myview16) {
            Tools.log("myview16");
            Tools.toast(MainActivity.this,"myview 16");
            mIntent = new Intent(MainActivity.this, MyViewSixPathMeasure.class);
            startActivity(mIntent);
        }else if (id == R.id.myview17) {
            Tools.log("myview17");
            Tools.toast(MainActivity.this,"myview 17");
            mIntent = new Intent(MainActivity.this, MyViewSevSearchPath.class);
            startActivity(mIntent);
        }else if (id == R.id.myview18) {
            Tools.log("myview18");
            Tools.toast(MainActivity.this,"myview 18");
            mIntent = new Intent(MainActivity.this, MyViewEightMitrix.class);
            startActivity(mIntent);
        }else if (id == R.id.myview19) {
            Tools.log("myview19");
            Tools.toast(MainActivity.this,"myview 19");
            mIntent = new Intent(MainActivity.this, MyViewNighMitrixCamera.class);
            startActivity(mIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
