package com.example.ammar.shopping;


import android.app.Activity;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SignUp extends Activity implements View.OnClickListener {
    FancyButton signup;
    String typ;
    ShoppingDB help;
    boolean typp = false;
    Spinner type;
    ProgressDialog pDialog;
    private String Name, Email, Password, Balance, Credit, Address, Dp, Contact, Confirm;
    JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String url_create_product = "https://shopping37.000webhostapp.com/user.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Spinner dropdown = (Spinner) findViewById(R.id.signup_type);
        String[] items = new String[]{"Customer", "Retailor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        signup = (FancyButton) findViewById(R.id.signup_signup);
        signup.setOnClickListener(this);
        help = new ShoppingDB(getApplicationContext());
        TextView loginn = (TextView) findViewById(R.id.elogin);
        loginn.setOnClickListener(this);
        //  SQLiteDatabase db=help.getWritableDatabase();
    }
public void is_palindrome(char[]arr)
{
    int l=arr.length;
    for(int i=0,j=l-1;i<l/2;i++,j--)
    {

    }
}
    @Override
    protected void onPause() {
        super.onPause();
        //          pDialog.dismiss();
    }

    public static boolean ValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }

    public static boolean equal(String pass, String pass1) {
        boolean result = true;
        if (pass.length() == pass1.length()) {
            int i;
            for (i = 0; i < pass.length(); i++) {
                if (pass.charAt(i) != pass1.charAt(i)) {
                    result = false;
                    break;
                }
            }
            return result;
        } else
            return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup_signup) {
            EditText name = (AnimatedEditText) findViewById(R.id.signup_name);
            EditText email = (AnimatedEditText) findViewById(R.id.signup_email);
            EditText password = (EditText) findViewById(R.id.signup_password);
            EditText confirm = (EditText) findViewById(R.id.signup_confirm_password);
            EditText credit = (AnimatedEditText) findViewById(R.id.signup_credit_card_number);

            EditText address = (AnimatedEditText) findViewById(R.id.signup_address);
            EditText contact = (AnimatedEditText) findViewById(R.id.signup_contact_number);
            type = (Spinner) findViewById(R.id.signup_type);
            typ = type.getSelectedItem().toString();
            boolean success = true;

            Name = name.getText().toString();
            Email = email.getText().toString();
            Password = password.getText().toString();
            Address = address.getText().toString();
            Credit = credit.getText().toString();

            Contact = contact.getText().toString();
            Confirm = confirm.getText().toString();
            if (ValidEmail(Email) == false) {
                email.setError("Email is not valid");
                success = false;
            }
            if (Password.length() < 5) {
                password.setError("At least 5 character");
                success = false;
            }
            if (equal(Password, Confirm) == false) {
                confirm.setError("both passwords are not equal");
                success = false;
            }
            if(Name.isEmpty()){
                name.setError("Enter Name");
            }
            if(Address.isEmpty()){
                address.setError("Enter Address");
            }
            if(Credit.isEmpty()){
                credit.setError("Enter credit card number");
            }
            if(Contact.isEmpty()){
                contact.setError("Enter Contact Number");
            }
            if (success == true) {  //everything okay no error
                if (Utility.isNetworkAvailable(this)) {
                    new CreateNewUser().execute(url_create_product);
                    help.insert_user(name.getText().toString(), email.getText().toString(), password.getText().toString(), contact.getText().toString(), address.getText().toString(), credit.getText().toString(), "0", typp);
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please connect Internet", Toast.LENGTH_LONG).show();
                }
                if (typ == "Retailor") {
                    typp = true;
                }
            }
        } else if (v.getId() == R.id.elogin) {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
        }

    }


class CreateNewUser extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(SignUp.this);
        pDialog.setMessage("Uploading to server...");
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
        params.add(new BasicNameValuePair("user_name", Name));
        params.add(new BasicNameValuePair("user_email", Email));
        params.add(new BasicNameValuePair("user_password", Password));
        params.add(new BasicNameValuePair("user_creditcard", Credit));
        params.add(new BasicNameValuePair("user_address", Address));
        params.add(new BasicNameValuePair("user_balance", Balance));
        params.add(new BasicNameValuePair("user_contact", Contact));
        params.add(new BasicNameValuePair("user_type", typ));

        // params.add(new BasicNameValuePair("user_password",Password));
        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                "POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully created product
                Intent i = new Intent(getApplicationContext(), add_product.class);
                startActivity(i);

                // closing this screen
                finish();
            } else {
                // failed to create product
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
        pDialog.dismiss();
    }

}
    }

