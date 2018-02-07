package com.example.ammar.shopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static android.R.attr.category;
import static android.R.attr.type;
import static com.example.ammar.shopping.DbBitmapUtility.getBytes;

public class add_product extends AppCompatActivity implements Button.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView iv;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_COUNTER = "counter";
    private static int counter;
    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    Button getimg;
    Button submit;
    ShoppingDB help;
    private Bitmap imageview;
    private static String url_all_gameName = "https://shopping37.000webhostapp.com/getcount.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Spinner dropdown = (Spinner) findViewById(R.id.add_product_count);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        getimg = (Button) findViewById(R.id.add_product_getimg);
        getimg.setOnClickListener(this);
        submit = (Button) findViewById(R.id.add_product_submit);
        submit.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.add_product_img);
        help = new ShoppingDB(getApplicationContext());
        addcategories();
       new getproducts().execute();

    }

    void addcategories() {

        Vector categories = (new hold_categories().getcat());


        String[] choice = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            choice[i] = categories.elementAt(i).toString();

        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, choice);
        Spinner choices = (Spinner) findViewById(R.id.add_product_category);

        choices.setAdapter(adapter2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri contactUri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver()
                            .query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    int column = cursor.getColumnIndex(projection[0]);
                    String number = cursor.getString(column);
                    cursor.close();

                    Bitmap selectedimage = BitmapFactory.decodeFile(number);
                    imageview = selectedimage;

                    Drawable d = new BitmapDrawable(selectedimage);
                    iv.getLayoutParams().height = 95;
                    iv.getLayoutParams().width = 130;
                    iv.setBackground(d);
                }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_product_getimg) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        } else if (view.getId() == R.id.add_product_submit) {
            EditText name = (EditText) findViewById(R.id.add_product_name);
            EditText price = (EditText) findViewById(R.id.add_product_price);

            EditText retailor = (EditText) findViewById(R.id.add_product_retid);
            //  EditText count=(EditText) findViewById(R.id.signup_confirm_password);
            Spinner category = (Spinner) findViewById(R.id.add_product_category);
            String type = category.getSelectedItem().toString();

            EditText description = (EditText) findViewById(R.id.add_product_description);
            ImageView image = (ImageView) findViewById(R.id.add_product_img);
            byte[] data=null;
            if(imageview!=null) {
                 data = DbBitmapUtility.getBytes(imageview);
            }

            Spinner count = (Spinner) findViewById(R.id.add_product_count);
            String typ = count.getSelectedItem().toString();
            new getproducts().execute();
            counter++;
            help.insert_product(Integer.toString(counter),name.getText().toString(), description.getText().toString(), typ, price.getText().toString(), type, retailor.getText().toString(), "0", data);
            price.setText(Integer.toString(help.getproductscount()));
        }
    }

     class getproducts extends AsyncTask<String, String, String> {

        String error = null;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(add_product.this);
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
                        counter = json.getInt(TAG_COUNTER);

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
            EditText price = (EditText) findViewById(R.id.add_product_price);
            price.setText(Integer.toString(counter));
 //           pDialog.dismiss();
        }

    }
}
