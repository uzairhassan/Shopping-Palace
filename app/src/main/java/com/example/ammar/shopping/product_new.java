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
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class product_new extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GridView.OnItemClickListener {
    List<my_product> items;
    ShoppingDB help;
    String category;
    int uli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_new);
        setTitle("Products");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        help = new ShoppingDB(getApplicationContext());
        items = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            category = null;
        } else {
            category = extras.getString("category");
            items = help.getproducts(category);
            uli = extras.getInt("user");
        }


        grid_adapter ga = new grid_adapter(getApplicationContext(), items);
        GridView gv = (GridView) findViewById(R.id.grid_products);
        gv.setOnItemClickListener(this);
        gv.setAdapter(ga);
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
        getMenuInflater().inflate(R.menu.product_new, menu);
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
            Intent i=new Intent(product_new.this,home.class);
            i.putExtra("user",uli);
            startActivity(i);
        }
        else if (id == R.id.profile) {
            // Handle the camera action
        } else if (id == R.id.cart) {
            Intent i=new Intent(product_new.this,Cart.class);
            i.putExtra("user",uli);
            startActivity(i);


        } else if (id == R.id.likes_list) {
            Intent i=new Intent(product_new.this,LikesList.class);
            startActivity(i);
        } else if (id == R.id.wish_list) {
            Intent i=new Intent(product_new.this,DemandList.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        my_product temp = (my_product) adapterView.getItemAtPosition(position);
        Intent intent = new Intent(product_new.this, show_product.class);
        intent.putExtra("id", temp.getId());
        intent.putExtra("name", temp.getName());
        intent.putExtra("user", uli);

        if (temp.getImage() != null) {
            intent.putExtra("image", DbBitmapUtility.getBytes(temp.getImage()));
            intent.putExtra("watchid", -1);
        } else {
            intent.putExtra("watchid", R.drawable.watch);

        }
        intent.putExtra("desc", temp.getDesc());
        intent.putExtra("price", temp.getPrice());
        intent.putExtra("ic", temp.getItemcount());
        intent.putExtra("sc", temp.getSellcount());

        startActivity(intent);

    }
}
