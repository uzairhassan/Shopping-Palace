package com.example.ammar.shopping;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.widgets.AnimatedEditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;

public class Login extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private String email, password;
    private EditText etEmail, etPassword;
    ShoppingDB help;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TYPE = "type";
    TextView signup;
    Intent mServiceIntent;
    private set_products mSensorService;
    int user_logged_in;

    private static String url_create_product = "https://shopping37.000webhostapp.com/check_login.php";
    private static String url_getproductscount = "https://shopping37.000webhostapp.com/getproductscount.php";
    private static String url_getproducts = "https://shopping37.000webhostapp.com/getproductwithid.php";

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_logged_in = 0;
        help = new ShoppingDB(getApplicationContext());
        mSensorService = new set_products(getBaseContext());
        mServiceIntent = new Intent(getBaseContext(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }
        FancyButton btn = (FancyButton) findViewById(R.id.login_login);
        btn.setOnClickListener(this);
        etEmail = (AnimatedEditText) findViewById(R.id.login_mail);
        etPassword = (EditText) findViewById(R.id.login_password);
        signup = (TextView) findViewById(R.id.login_signup);
        signup.setOnClickListener(this);
        if(Utility.isNetworkAvailable(getApplicationContext()))
        {
            new getdata().execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please connect Internet",Toast.LENGTH_SHORT).show();
        }



    }

    public static boolean ValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_login) {
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            boolean success = true;
            user_logged_in = help.retid(email);
            if (ValidEmail(email) == false) {
                etEmail.setError("Email is not valid");
                success = false;
            }
            if (password.length() < 5) {
                etPassword.setError("At least 5 character");
                success = false;
            }
            if (success) {

                if(Utility.isNetworkAvailable(getApplicationContext()))
                {
                    new LoginUser().execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please connect Internet",Toast.LENGTH_SHORT).show();
                }
            }

        } else if (v.getId() == R.id.login_signup) {
            super.onBackPressed();
        }
    }

    class LoginUser extends AsyncTask<String, String, String> {

        String error = null;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Checking from server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_email", email));
            params.add(new BasicNameValuePair("user_password", password));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "GET", params);
            // checking for success tag
            try {
                if (json != null) {
                    int success = 1;
                    try {
                        success = json.getInt(TAG_SUCCESS);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (success == 1) {
                        String type = json.getString(TAG_TYPE);
                        user_logged_in=json.getInt("id");
                        // successfully created product
                        if (type.equals("Retailor")) {
                            Intent i = new Intent(Login.this, add_product.class);
                            i.putExtra("user", user_logged_in);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(Login.this, home.class);
                            i.putExtra("user", user_logged_in);
                            startActivity(i);
                        }
                    } else {
                        error = json.getString("message");
                    }
                } else {
                    error = "Please Connect Internet !";
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if (error != null)
                Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show();
            pDialog.dismiss();
        }

    }


    class getdata extends AsyncTask<String, String, String> {

        String error = null;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(Login.this);
//            pDialog.setMessage("Checking from server...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("user_email", email));
//            params.add(new BasicNameValuePair("user_password", password));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_getproductscount,
                    "GET", params);
            // checking for success tag
            try {
                if (json != null) {
                    int success = 1;
                    try {
                        success = json.getInt(TAG_SUCCESS);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (success == 1) {
                        int count = json.getInt("counter");
                        Log.i("Entring count", Integer.toString(count) + "service ");

                        // successfully created product
                        help.deleteproducts();
                        for (int i = 1; i <= count; i++) {
                            Log.i("Entring LOOP", Integer.toString(count) + "service ");
                            params.clear();
                            params.add(new BasicNameValuePair("id", Integer.toString(i)));
                            JSONObject json2 = jsonParser.makeHttpRequest(url_getproducts,
                                    "GET", params);

                            String category = json2.getString("category");
                            int ret_id = json2.getInt("ret_id");
                            int item_count = json2.getInt("item_count");
                            int sell_count = json2.getInt("sell_count");
                            String name = json2.getString("name");
                            int price = json2.getInt("price");
                            String description = json2.getString("description");
                            String image_url = json2.getString("image_url");
                            Log.i("Entring LOOP", Integer.toString(count) + name);
                            Log.i("Entring LOOP", Integer.toString(count) + description);
                            Log.i("Entring LOOP", Integer.toString(count) + image_url);
                            Bitmap abc = null;
                            byte[] arr = null;
                            if (!image_url.equals("")) {
                                abc = ImageUtil.convert(image_url);
                                arr = DbBitmapUtility.getBytes(abc);

                            }
                            help.insert_product(Integer.toString(i), name, description, Integer.toString(item_count), Integer.toString(price), category, Integer.toString(ret_id), Integer.toString(sell_count), arr);
                        }
                    } else {
                        error = json.getString("message");
                    }
                } else {
                    error = "Please Connect Internet !";
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if (error != null)
                Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show();
            //      pDialog.dismiss();
        }

    }

}
