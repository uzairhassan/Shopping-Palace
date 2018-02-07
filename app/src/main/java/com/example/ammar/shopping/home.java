package com.example.ammar.shopping;

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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Vector;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ListView.OnItemClickListener {
int uid;
    String current="categories";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        Intent intent = getIntent();
        if (intent.getExtras() == null) {
            // Do first time stuff here
        }
        else {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                uid = 0;
            } else {
                uid = extras.getInt("user");


            }
        }

//        LinearLayout mContainer = (LinearLayout) getLayoutInflater().inflate(R.layout.content_home, null);
//        setContentView(mContainer);
        ListView activeList = (ListView) findViewById(R.id.catt);
        activeList.setOnItemClickListener(this);
        //ListView lv=(ListView)findViewById(R.id.catt);

        Vector categories = (new hold_categories().getcat());
        String[] choice = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            choice[i] = categories.elementAt(i).toString();
        }
        //  ArrayAdapter<String> abc= new ArrayAdapter<String>(this,R.layout.activity_categories_list,choice);
        activeList.setAdapter(new categories_adapter(getApplicationContext(), choice));
        //       setContentView(activeList);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

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
        getMenuInflater().inflate(R.menu.home, menu);
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
        if (id == R.id.categories) {
            // Do nothing
        }
        else if (id == R.id.profile) {
            Intent i=new Intent(home.this,Profile.class);
            startActivity(i);
        } else if (id == R.id.cart) {
            Intent i=new Intent(home.this,Cart.class);
            i.putExtra("user",uid);
            startActivity(i);

        } else if (id == R.id.likes_list) {
            Intent i=new Intent(home.this,LikesList.class);
            startActivity(i);
        } else if (id == R.id.wish_list) {
            Intent i=new Intent(home.this,DemandList.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String temp = (String) adapterView.getItemAtPosition(position);
        Intent intent = new Intent(home.this, product_new.class);
        intent.putExtra("category", temp);
        intent.putExtra("user",uid);
        startActivity(intent);
    }
}
