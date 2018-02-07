package com.example.ammar.shopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import at.markushi.ui.CircleButton;

import static android.view.View.generateViewId;

public class Cart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    ShoppingDB help;int oid;int uli;
    PayPalConfiguration m_configuration;
    Vector prices;
    Intent m_service;
    Vector plusids,minusids,crossids,prodids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button place=(Button)findViewById(R.id.place);
        place.setOnClickListener(this);
        prices= new Vector();
        plusids= new Vector();minusids= new Vector();crossids= new Vector();prodids= new Vector();
        help=new ShoppingDB(getApplicationContext());
        oid=help.getorderscount()+1;
        Bundle extras = getIntent().getExtras();

        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // sandbox for test, production for real
                .clientId("AWvDMZF-l65d7qGELQVfS0Sj076kN_JEpWpWS3thgqaz0oZ65qV7WI88gUNOpvZN5uoEE307qkGyjjmE");
        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        startService(m_service);


        if (extras == null) {

        } else {

            uli=extras.getInt("user");
        }

        int counter=help.getorderdetailscount(oid);
        LinearLayout toadd=(LinearLayout)findViewById(R.id.toaddcart);

        List<Integer> oo=help.getorderdetails(oid);
        for(int i=0;i<oo.size();i+=2)
        {

            LinearLayout ll=(LinearLayout)getLayoutInflater().inflate(R.layout.cart_order,null);
            //        ll.setId(View.generateViewId());
            TextView name,quant,price;
            CircleButton plus,minus,cross;
            name=(TextView)ll.findViewById(R.id.cart_item_name);
            quant=(TextView)ll.findViewById(R.id.cart_quantity);
            price=(TextView)ll.findViewById(R.id.cart_price);
            plus=(CircleButton)ll.findViewById(R.id.cart_plus_button);
            plus.setOnClickListener(this);
            int rid=generateViewId();
            plusids.add(rid);
            plus.setId(rid);
            minus=(CircleButton)ll.findViewById(R.id.cart_minus_button);
            minus.setOnClickListener(this);
            rid=generateViewId();
            minusids.add(rid);
            minus.setId(rid);
            cross=(CircleButton)ll.findViewById(R.id.cart_discard_button);
            cross.setOnClickListener(this);
            rid=generateViewId();
            crossids.add(rid);
            cross.setId(rid);
            quant.setText(Integer.toString(oo.get(i)));
            my_product temp=help.getproductswithid(oo.get(i+1));
            prices.add(Integer.parseInt(temp.getPrice()));
            price.setText(temp.getPrice());
            name.setText(temp.getName());
            prodids.add(temp.getId());
            toadd.addView(ll);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(2).setChecked(true);
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
        getMenuInflater().inflate(R.menu.cart, menu);
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
            Intent i=new Intent(Cart.this,home.class);
            i.putExtra("user",uli);
            startActivity(i);
        }
        else if (id == R.id.profile) {
            // Handle the camera action
        } else if (id == R.id.cart) {


        } else if (id == R.id.likes_list) {

        } else if (id == R.id.wish_list) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.place)
        {
            help.insert_order(oid,uli);
            List<pair> abc=help.getallorders();
            PayPalPayment payment = new PayPalPayment(new BigDecimal(100), "USD", "Paypal",
                    PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class); // it's not paypalpayment, it's paymentactivity !
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, 999);
        }
        else
        {

            for(int i=0;i<plusids.size();i++)
            {
                if(v.getId()==(Integer) plusids.elementAt(i))
                {
                    LinearLayout d=(LinearLayout) v.getParent().getParent();
                    TextView temp=(TextView)d.findViewById(R.id.cart_quantity);
                    int to_increment= Integer.parseInt(temp.getText().toString());
                    to_increment++;
                    TextView price=(TextView) d.findViewById(R.id.cart_price);
                    int pr= Integer.parseInt(price.getText().toString()) ;
                    pr=pr+(Integer) prices.elementAt(i);
                    price.setText(Integer.toString(pr));
                    int prid=(Integer)(plusids.elementAt(i));
                    help.plusorderdetails(Integer.toString(oid),Integer.toString (prid));
                    temp.setText(Integer.toString(to_increment));
                }
            }
            for(int i=0;i<minusids.size();i++)
            {
                if(v.getId()==(Integer) minusids.elementAt(i))
                {
                    LinearLayout d=(LinearLayout) v.getParent().getParent();
                    TextView temp=(TextView)d.findViewById(R.id.cart_quantity);
                    int to_increment= Integer.parseInt(temp.getText().toString());
                    if(to_increment>1) {
                        to_increment--;
                        TextView price=(TextView) d.findViewById(R.id.cart_price);
                        int pr= Integer.parseInt(price.getText().toString()) ;
                        pr=pr-(Integer) prices.elementAt(i);
                        price.setText(Integer.toString(pr));
                    }
                    int prid=(Integer)(minusids.elementAt(i));
                    help.minusorderdetails(Integer.toString(oid),Integer.toString (prid));
                    temp.setText(Integer.toString(to_increment));
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 999)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                // we have to confirm that the payment worked to avoid fraud
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation != null)
                {
                    String state = confirmation.getProofOfPayment().getState();

                    if(state.equals("approved")) // if the payment worked, the state equals approved
                        Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Confirmation is null", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
