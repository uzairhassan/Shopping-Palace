package com.example.ammar.shopping;

/**
 * Created by uzairhassan on 02/05/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;


/**
 * Created by fabio on 30/01/2016.
 */
public class set_products extends Service {
    ShoppingDB help;
    private static final String TAG_COUNTER = "counter";
    private static int counter2;
    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_NAME = "name";
    public int counter = 0;
    JSONArray products = null;
    private static String url_all_gameName = "https://shopping37.000webhostapp.com/getcount.php";
    private static String url_all_products = "https://shopping37.000webhostapp.com/upload_products.php";
    private static String url_all_orderdetails = "https://shopping37.000webhostapp.com/upload_order_details.php";
    private static String url_count_orderdetails = "https://shopping37.000webhostapp.com/getorderdetailscount.php";
    private static String getorderscount = "https://shopping37.000webhostapp.com/getorderscount.php";
    private static String uploadorders = "https://shopping37.000webhostapp.com/uploadorders.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NOTI = "noti";
    private static final String TAG_ID = "id";
    private static final String TAG_GID = "game_id";
    private static final String TAG_DETAIL = "detail";

    public set_products(Context applicationContext) {
        super();
 //       Log.i("HERE", "here I am!");
    }

    public set_products() {
    }

    NotificationCompat.Builder builder;
    NotificationManager manager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        jsonParser = new JSONParser();
        help = new ShoppingDB(getApplicationContext());
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
 //       Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("mgl.service.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 20000, 20000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    class getproducts extends AsyncTask<String, String, String> {

        String error = null;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_all_gameName,
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
                        counter2 = json.getInt(TAG_COUNTER);
      //                  Log.i(NotifyServiceRestartBroadcastReceiver.class.getSimpleName(), Integer.toString(counter2) + "service ");

                        int sqcount = help.getproductscount();
                        if (sqcount > counter2) {
         //                   Log.i(NotifyServiceRestartBroadcastReceiver.class.getSimpleName(), Integer.toString(counter2) + "in if ");

                            for (int i = counter2 + 1; i <= sqcount; i++) {
          //                      Log.i(NotifyServiceRestartBroadcastReceiver.class.getSimpleName(), Integer.toString(counter2) + "in for ");

                                params.clear();
                                my_product temp = help.getproductswithid(i);
                                params.add(new BasicNameValuePair("category", temp.getCategory()));
                                params.add(new BasicNameValuePair("description", temp.getDesc()));
                                params.add(new BasicNameValuePair("item_count", Integer.toString(temp.getItemcount())));
                                params.add(new BasicNameValuePair("name", temp.getName()));
                                params.add(new BasicNameValuePair("price", temp.getPrice()));
                                params.add(new BasicNameValuePair("sell_count", Integer.toString(temp.getSellcount())));
                                params.add(new BasicNameValuePair("ret_id", Integer.toString(temp.getRetid())));
                                params.add(new BasicNameValuePair("id", Integer.toString(temp.getId())));
                                Bitmap img=temp.getImage();

                                String imag="";
                                if(img!=null)
                                {
                                    imag=ImageUtil.convert(img);
                                }
                                params.add(new BasicNameValuePair("image_url", imag));

                                JSONObject json2 = jsonParser.makeHttpRequest(url_all_products,
                                        "POST", params);
                                int success2 = 1;
                                success2 = json2.getInt(TAG_SUCCESS);
                            }

                        }
                        params.clear();
                        JSONObject json2 = jsonParser.makeHttpRequest(url_count_orderdetails,
                                "POST", params);
                        int success2 = 1;
                        success2 = json2.getInt(TAG_SUCCESS);
                        if (success2 == 1) {
                            counter2 = json2.getInt(TAG_COUNTER);
            //                Log.i(NotifyServiceRestartBroadcastReceiver.class.getSimpleName(), Integer.toString(counter2) + "service ");
                            sqcount = help.getallorderdetailscount();
                            if (sqcount > counter2) {
                                List<Integer> oo = help.getallorderdetails();
                                for (int i = 0; i < oo.size(); i += 3) {
                                    params.clear();
                                    int oid, pid, count;
                                    count = oo.get(i);
                                    oid = oo.get(i + 1);
                                    pid = oo.get(i + 2);
                                    params.add(new BasicNameValuePair("item_count", Integer.toString(count)));
                                    params.add(new BasicNameValuePair("oid", Integer.toString(oid)));
                                    params.add(new BasicNameValuePair("pid", Integer.toString(pid)));
                                    JSONObject json3 = jsonParser.makeHttpRequest(url_all_orderdetails,
                                            "POST", params);
                                    int success3=json3.getInt(TAG_SUCCESS);
                                }
                            }


                        }

                        params.clear();
                        JSONObject json3 = jsonParser.makeHttpRequest(getorderscount,
                                "POST", params);
                         success2 = 1;
                        success2 = json3.getInt(TAG_SUCCESS);
                        if (success2 == 1) {
                            counter2 = json3.getInt(TAG_COUNTER);
            //                Log.i(NotifyServiceRestartBroadcastReceiver.class.getSimpleName(), Integer.toString(counter2) + "service ");
                            sqcount = help.getorderscount();
                            if (sqcount > counter2) {
                                List<pair> oo = help.getallorders();
                                for (int i = 0; i < oo.size(); i += 3) {
                                    params.clear();
                                    int oid, pid;String time;

                                    oid = oo.get(i).ikey;
                                    time=oo.get(i+1).skey;
                                    pid = oo.get(i + 2).ikey;
                                    params.add(new BasicNameValuePair("item_count", time));
                                    params.add(new BasicNameValuePair("oid", Integer.toString(oid)));
                                    params.add(new BasicNameValuePair("pid", Integer.toString(pid)));
                                    JSONObject json4 = jsonParser.makeHttpRequest(uploadorders,
                                            "POST", params);
                                    int success3=json4.getInt(TAG_SUCCESS);
                                }
                            }
                        }



                        // successfully created product
//                        if (type.equals("Retailor")) {
//                            Intent i = new Intent(Login.this, add_product.class);
//                            startActivity(i);
//                        } else {
//                            startActivity(new Intent(Login.this, categories_list.class));
//
//                        }
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

        }

    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
        //        Log.i("in timer", "in timer ++++  " + (counter++));
if(Utility.isNetworkAvailable(getApplicationContext())) {
    new getproducts().execute();
}
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}