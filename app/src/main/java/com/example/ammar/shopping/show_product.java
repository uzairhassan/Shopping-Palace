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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class show_product extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    String name, desc, price;
    int id;
    ShoppingDB help;
    byte[] img;
    int uli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        help= new ShoppingDB(getApplicationContext());
        Button btn=(Button)findViewById(R.id.cart);
        btn.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();

        if (extras == null) {

        } else {

            name = extras.getString("name");
            uli=extras.getInt("user");
            id = extras.getInt("id");
            price = extras.getString("price");
            desc = extras.getString("desc");
            int watchid=extras.getInt("watchid");
            if(watchid!=-1)
            {
                img = extras.getByteArray("image");

            }
            else
            {
                img = null;
            }
            img = extras.getByteArray("image");

            //   Image popup on image click


            int ic=extras.getInt("ic");
            int sc=extras.getInt("sc");
            TextView namee = (TextView) findViewById(R.id.product_name);
            namee.setText("Name: "+name);
            TextView descc = (TextView) findViewById(R.id.product_desc);
            descc.setText("Description: "+desc);
            TextView sold = (TextView) findViewById(R.id.product_sold);
            sold.setText("Sell Count: "+Integer.toString(sc));
            TextView avail = (TextView) findViewById(R.id.product_available);
            avail.setText("Items Available: "+Integer.toString(ic));
            TextView prc = (TextView) findViewById(R.id.price);
            prc.setText("Price: "+price);

            ImageView imgg = (ImageView) findViewById(R.id.imageqq);
            if(img==null)
            {
                imgg.setImageResource(R.drawable.watch);
            }
            else
            {
                imgg.setImageBitmap(DbBitmapUtility.getImage(img));
            }


        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
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
        getMenuInflater().inflate(R.menu.show_product, menu);
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
            Intent i=new Intent(show_product.this,home.class);
            i.putExtra("user",uli);
            startActivity(i);
        }
        else if (id == R.id.profile) {
            // Handle the camera action
        } else if (id == R.id.cart) {
            Intent i=new Intent(show_product.this,Cart.class);
            i.putExtra("user",uli);
            startActivity(i);

        } else if (id == R.id.likes_list) {
            Intent i=new Intent(show_product.this,LikesList.class);
            startActivity(i);
        } else if (id == R.id.wish_list) {
            Intent i=new Intent(show_product.this,DemandList.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.cart)
        {
            int oid=help.getorderscount()+1;
            Long idd=help.insertorderdetails(1,oid,id);

            Toast.makeText(getApplicationContext(),"Item added to cart",Toast.LENGTH_SHORT).show();
        }
    }
}
