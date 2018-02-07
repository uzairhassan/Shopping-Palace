package com.example.ammar.shopping;

/**
 * Created by Uzair_Hassan on 11-Apr-17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;

public class ShoppingDB {
    ContactHelper Helper;

    public ShoppingDB(Context context) {
        Helper = new ContactHelper(context);
        SQLiteDatabase db = Helper.getWritableDatabase();

    }


    public int get_user_id() {
        int id = 0;
        SQLiteDatabase db = Helper.getWritableDatabase();
        try {
            Cursor c = db.rawQuery("SELECT max(" + ContactHelper.user_id + ") FROM " + ContactHelper.TABLE_NAME3 + ";", null);
            while (c.moveToNext()) {
                id = c.getInt(0);
                Log.d("checking", "Working....1");
            }

        } catch (SQLException e) {
            Log.d("check for something", "getting error in getid()");
        }
        Log.d("checking", "RAWQUERY");

        //id += 1;
        Log.d("checking", "getid");

        return id;
    }

    public void insert_category(String name) {

        SQLiteDatabase db = Helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(ContactHelper.category_name, name);
        long id1 = db.insert(ContactHelper.TABLE_NAME2, null, value);


    }

    public void insert_demand(int pid, int uid) {

        SQLiteDatabase db = Helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(ContactHelper.product_id, pid);
        value.put(ContactHelper.user_id, uid);
        long id1 = db.insert(ContactHelper.TABLE_NAME12, null, value);


    }

    public void insert_like(int pid, int uid) {

        SQLiteDatabase db = Helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(ContactHelper.product_id, pid);
        value.put(ContactHelper.user_id, uid);
        long id1 = db.insert(ContactHelper.TABLE_NAME13, null, value);


    }

    public void deleteorderdetails() {

        SQLiteDatabase db = Helper.getWritableDatabase();
        db.rawQuery("TRUNCATE TABLE order_details", null);
    }

    public Long insertorderdetails(int countr, int oid, int pid) {

        SQLiteDatabase db = Helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(ContactHelper.order_id, oid);
        value.put(ContactHelper.order_count, countr);
        value.put(ContactHelper.product_id, pid);

        long id1 = db.insert(ContactHelper.TABLE_NAME9, null, value);
        return id1;


    }

    public int retid(String mail) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] params = new String[]{mail};

        Cursor c = db.rawQuery("select _id from users where email=?", params);

        int res = 0;
        while (c.moveToNext()) {
            res = c.getInt(0);

        }

        return res;

    }

    public Long insert_order(int oid, int uid) {

        SQLiteDatabase db = Helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(ContactHelper.order_id, oid);
        value.put(ContactHelper.user_id, uid);
        long id1 = db.insert(ContactHelper.TABLE_NAME8, null, value);
        return id1;


    }

    public int getorderscount() {
        SQLiteDatabase db = Helper.getWritableDatabase();
        String category = ContactHelper.TABLE_NAME8;
        Cursor c = db.rawQuery("select count(*) from " + " '" + category + "' ", null);
        int res = 0;
        while (c.moveToNext()) {
            res = c.getInt(0);
        }
        return res;
    }

    public long insert_user(String name, String email, String password, String contact, String address, String creditcard, String balance, Boolean type) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ContactHelper.user_name, name);
        value.put(ContactHelper.user_email, email);
        value.put(ContactHelper.user_password, password);
        value.put(ContactHelper.user_address, address);
        value.put(ContactHelper.user_creditcard, creditcard);
        value.put(ContactHelper.user_balance, Integer.parseInt(balance));
        value.put(ContactHelper.user_category, "standard");
        long id1 = db.insert(ContactHelper.TABLE_NAME3, null, value);
        if (type == true) {
            ContentValues ret_value = new ContentValues();
            ret_value.put(ContactHelper.user_id, get_user_id());
            id1 = db.insert(ContactHelper.TABLE_NAME7, null, value);
        }
        return id1;
    }

    public void deleteproducts() {
        SQLiteDatabase db = Helper.getWritableDatabase();
        db.delete(ContactHelper.TABLE_NAME1, null, null);
        ;
    }

    public long insert_product(String id,String name, String desc, String count, String price, String category, String retailer_id, String sell_count, byte[] data) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ContactHelper.product_name, name);
        value.put(ContactHelper.product_id, Integer.parseInt(id));
        value.put(ContactHelper.product_description, desc);
        value.put(ContactHelper.product_item_count, Integer.parseInt(count));
        value.put(ContactHelper.product_price, Integer.parseInt(price));
        value.put(ContactHelper.product_ret_id, Integer.parseInt(retailer_id));
        value.put(ContactHelper.product_sell_count, Integer.parseInt(sell_count));
        value.put(ContactHelper.product_category, category);
        value.put(ContactHelper.product_image, data);


        long id1 = db.insert(ContactHelper.TABLE_NAME1, null, value);
        if (!category.equals("All Categories")) {
            ContentValues value1 = new ContentValues();
            value1.put(ContactHelper.product_name, name);
            value1.put(ContactHelper.product_description, desc);
            value1.put(ContactHelper.product_item_count, Integer.parseInt(count));
            value1.put(ContactHelper.product_price, Integer.parseInt(price));
            value1.put(ContactHelper.product_ret_id, Integer.parseInt(retailer_id));
            value1.put(ContactHelper.product_sell_count, Integer.parseInt(sell_count));
            value1.put(ContactHelper.product_category, "All categories");
            value1.put(ContactHelper.product_image, data);
            id1 = db.insert(ContactHelper.TABLE_NAME1, null, value1);
        }

        return id1;
    }

    public long insert_productwithimage(String name, String desc, String count, String price, String category, String retailer_id, String sell_count, byte[] data, String image_url) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ContactHelper.product_name, name);
        value.put(ContactHelper.product_description, desc);
        value.put(ContactHelper.product_item_count, Integer.parseInt(count));
        value.put(ContactHelper.product_price, Integer.parseInt(price));
        value.put(ContactHelper.product_ret_id, Integer.parseInt(retailer_id));
        value.put(ContactHelper.product_sell_count, Integer.parseInt(sell_count));
        value.put(ContactHelper.product_category, category);
        value.put(ContactHelper.product_image, data);
 //       value.put(ContactHelper.product_url, image_url);
        long id1 = db.insert(ContactHelper.TABLE_NAME1, null, value);

        if (!category.equals("All Categories")) {
            ContentValues value1 = new ContentValues();
            value1.put(ContactHelper.product_name, name);
            value1.put(ContactHelper.product_description, desc);
            value1.put(ContactHelper.product_item_count, Integer.parseInt(count));
            value1.put(ContactHelper.product_price, Integer.parseInt(price));
            value1.put(ContactHelper.product_ret_id, Integer.parseInt(retailer_id));
            value1.put(ContactHelper.product_sell_count, Integer.parseInt(sell_count));
            value1.put(ContactHelper.product_category, "All categories");
            value1.put(ContactHelper.product_image, data);
   //         value1.put(ContactHelper.product_url, image_url);
            id1 = db.insert(ContactHelper.TABLE_NAME1, null, value1);



        }
        return id1;
    }


    public long insertData1(String a) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ContactHelper.NAME, a);
        Log.d("checking", "Value");
        long id1 = db.insert(ContactHelper.TABLE_NAME1, null, value);
        Log.d("checking", "ID");
        return id1;
    }

    public int getproductscount() {
        SQLiteDatabase db = Helper.getWritableDatabase();
        String category = ContactHelper.TABLE_NAME1;
        Cursor c = db.rawQuery("select count(*) from " + " '" + category + "' ", null);
        int res = 0;
        while (c.moveToNext()) {
            res = c.getInt(0);
        }
        return res;
    }

    public int has_category()

    {
        SQLiteDatabase db = Helper.getWritableDatabase();
        String category = ContactHelper.TABLE_NAME2;
        Cursor c = db.rawQuery("select count(*) from " + " '" + category + "' ", null);
        int res = 0;
        while (c.moveToNext()) {
            res = c.getInt(0);
        }
        return res;
    }

    public long insertData2(String a, String e) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ContactHelper.UID2, a);
        value.put(ContactHelper.EMAIL, e);
        Log.d("checking", "Value");
        long id1 = db.insert(ContactHelper.TABLE_NAME2, null, value);
        Log.d("checking", "ID");
        return id1;
    }

    public long insertData3(String a, String P) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(ContactHelper.UID2, a);
        value.put(ContactHelper.PHONE, P);
        Log.d("checking", "Value");
        long id1 = db.insert(ContactHelper.TABLE_NAME3, null, value);
        Log.d("checking", "ID");
        return id1;
    }

    public List<String> getallData() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.UID, ContactHelper.NAME};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME1, columns, null, null, null, null, null, null);
        Log.d("checking", "Query");
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            int cid = c.getInt(0);
            String numberAsString = Integer.toString(cid);
            list.add(numberAsString);
            String name = c.getString(1);
            list.add(name);
            buffer.append(cid + " " + name + "\n");

        }
        return list;
    }

    public int getorderdetailscount(int idd) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        String category = ContactHelper.TABLE_NAME9;
        String[] params = new String[]{String.valueOf(idd)};
        Cursor c = db.rawQuery("select count(*) from order_details where order_id=?", params);

        int res = 0;
        while (c.moveToNext()) {
            res = c.getInt(0);
        }
        return res;
    }

    public int plusorderdetails(String oid ,String pid) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        String category = ContactHelper.TABLE_NAME9;
        String[] params = new String[]{String.valueOf(oid),String.valueOf(pid)};
        Cursor c = db.rawQuery("update order_details set count =count+1 where order_id=? and productid=?", params);

        int res = 0;

        return res;
    }
    public int minusorderdetails(String oid ,String pid) {
        SQLiteDatabase db = Helper.getWritableDatabase();
        String category = ContactHelper.TABLE_NAME9;
        String[] params = new String[]{String.valueOf(oid),String.valueOf(pid)};
        Cursor c = db.rawQuery("update order_details set count =count-1 where order_id=? and productid=?", params);

        int res = 0;

        return res;
    }

    public int getallorderdetailscount() {
        SQLiteDatabase db = Helper.getWritableDatabase();
        String category = ContactHelper.TABLE_NAME9;

        Cursor c = db.rawQuery("select count(*) from order_details ", null);

        int res = 0;
        while (c.moveToNext()) {
            res = c.getInt(0);
        }
        return res;
    }

    public List<Integer> getorderdetails(int idd) {
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.order_count, ContactHelper.order_id, ContactHelper.product_id};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME9, columns, ContactHelper.order_id + " = '" + idd + "'", null, null, null, null, null);
        Log.d("checking", "Query");

        while (c.moveToNext()) {

            int desc = c.getInt(2);
            list.add(c.getInt(0));
            list.add(desc);


        }
        return list;
    }

    public List<Integer> getallorderdetails() {
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.order_count, ContactHelper.order_id, ContactHelper.product_id};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME9, columns, null, null, null, null, null, null);
        Log.d("checking", "Query");

        while (c.moveToNext()) {

            int desc = c.getInt(2);
            list.add(c.getInt(0));
            list.add(c.getInt(1));
            list.add(desc);

        }
        return list;
    }

    public List<pair> getallorders() {
        List<pair> list = new ArrayList<>();
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.order_id, ContactHelper.order_date, ContactHelper.user_id};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME8, columns, null, null, null, null, null, null);
        Log.d("checking", "Query");

        while (c.moveToNext()) {

            list.add(new pair(c.getInt(0), ""));
            list.add(new pair(1, c.getString(1)));
            list.add(new pair(c.getInt(2), ""));


        }
        return list;
    }

    public List<my_product> getallproducts() {
        List<my_product> list = new ArrayList<>();
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.product_image, ContactHelper.product_name, ContactHelper.product_price, ContactHelper.product_description, ContactHelper.product_id, ContactHelper.product_item_count, ContactHelper.product_sell_count, ContactHelper.product_category, ContactHelper.product_ret_id};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME1, columns, null, null, null, null, null, null);
        Log.d("checking", "Query");
        Bitmap img;
        byte[] cid = null;
        while (c.moveToNext()) {
            cid = c.getBlob(0);
            if (cid != null) {
                img = DbBitmapUtility.getImage(cid);
            } else {
                img = null;
            }
            String name = c.getString(1);
            String price = c.getString(2);
            String desc = c.getString(3);
            int id = c.getInt(4);
            int itemcount = c.getInt(5);
            int sellcount = c.getInt(6);
            String cat = c.getString(7);
            int rt = c.getInt(8);
            list.add(new my_product(img, price, name, desc, id, itemcount, sellcount, cat, rt, ""));


        }
        return list;
    }

    public List<my_product> getproducts(String namee) {
        List<my_product> list = new ArrayList<>();
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.product_image, ContactHelper.product_name, ContactHelper.product_price, ContactHelper.product_description, ContactHelper.product_id, ContactHelper.product_item_count, ContactHelper.product_sell_count, ContactHelper.product_category, ContactHelper.product_ret_id, ContactHelper.product_url};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME1, columns, ContactHelper.product_category + " = '" + namee + "'", null, null, null, null, null);
        Log.d("checking", "Query");
        Bitmap img;
        byte[] cid = null;
        while (c.moveToNext()) {
            cid = c.getBlob(0);
            if (cid != null) {
                img = DbBitmapUtility.getImage(cid);
            } else {
                img = null;
            }
            String name = c.getString(1);
            String price = c.getString(2);
            String desc = c.getString(3);
            int id = c.getInt(4);
            int itemcount = c.getInt(5);
            int sellcount = c.getInt(6);
            String cat = c.getString(7);
            int rt = c.getInt(8);
            String url = c.getString(9);
            list.add(new my_product(img, price, name, desc, id, itemcount, sellcount, cat, rt, url));

        }
        return list;
    }

    public my_product getproductswithid(int pid) {
        List<my_product> list = new ArrayList<>();
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.product_image, ContactHelper.product_name, ContactHelper.product_price, ContactHelper.product_description, ContactHelper.product_id, ContactHelper.product_item_count, ContactHelper.product_sell_count, ContactHelper.product_category, ContactHelper.product_ret_id, ContactHelper.product_url};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME1, columns, ContactHelper.product_id + " = '" + pid + "'", null, null, null, null, null);
        Log.d("checking", "Query");
        Bitmap img;
        my_product abc = null;
        byte[] cid = null;
        while (c.moveToNext()) {
            cid = c.getBlob(0);
            if (cid != null) {
                img = DbBitmapUtility.getImage(cid);
            } else {
                img = null;
            }
            String name = c.getString(1);
            String price = c.getString(2);
            String desc = c.getString(3);
            int id = c.getInt(4);
            int itemcount = c.getInt(5);
            int sellcount = c.getInt(6);
            String cat = c.getString(7);
            int rt = c.getInt(8);
            String url = c.getString(9);
            abc = (new my_product(img, price, name, desc, id, itemcount, sellcount, cat, rt, url));

        }
        return abc;
    }

    public List<String> getallDat2() {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.UID2, ContactHelper.EMAIL};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME2, columns, null, null, null, null, null, null);
        Log.d("checking", "Query");
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            int cid = c.getInt(0);
            String numberAsString = Integer.toString(cid);
            list.add(numberAsString);
            String name = c.getString(1);
            list.add(name);
            buffer.append(cid + " " + name + "\n");

        }
        return list;
    }

    public List<String> getallDat3() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.UID2, ContactHelper.PHONE};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME3, columns, null, null, null, null, null, null);
        Log.d("checking", "Query");
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            int cid = c.getInt(0);
            String numberAsString = Integer.toString(cid);
            list.add(numberAsString);
            String name = c.getString(1);
            list.add(name);
            buffer.append(cid + " " + name + "\n");

        }
        return list;
    }

    public int getid() {
        int id = 0;
        SQLiteDatabase db = Helper.getWritableDatabase();
        try {
            Cursor c = db.rawQuery("SELECT max(" + ContactHelper.UID + ") FROM " + ContactHelper.TABLE_NAME1 + ";", null);
            while (c.moveToNext()) {
                id = c.getInt(0);
                Log.d("checking", "Working....1");
            }

        } catch (SQLException e) {
            Log.d("check for something", "getting error in getid()");
        }
        Log.d("checking", "RAWQUERY");

        //id += 1;
        Log.d("checking", "getid");

        return id;
    }

    public List<String> getall1(String id) {

        List<String> list = new ArrayList<String>();

        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.UID, ContactHelper.NAME};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME1, columns, ContactHelper.UID + " = '" + id + "'", null, null, null, null, null);
        Log.d("checking", "Query");
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            int cid = c.getInt(0);
            String numberAsString = Integer.toString(cid);
//            list.add(numberAsString);
            String name = c.getString(1);
            list.add(name);
//            buffer.append(cid+" "+name+"\n");

        }
        return list;
    }

    public List<String> getall2(String id) {

        List<String> list = new ArrayList<String>();

        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.UID2, ContactHelper.EMAIL};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME2, columns, ContactHelper.UID2 + " = '" + id + "'", null, null, null, null, null);
        Log.d("checking", "Query");
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            int cid = c.getInt(0);
            String numberAsString = Integer.toString(cid);
//            list.add(numberAsString);
            String name = c.getString(1);
            list.add(name);
            buffer.append(cid + " " + name + "\n");

        }
        return list;
    }

    public List<String> getall3(String id) {

        List<String> list = new ArrayList<String>();

        SQLiteDatabase db = Helper.getWritableDatabase();
        String[] columns = {ContactHelper.UID2, ContactHelper.PHONE};
        Log.d("checking", "columns");
        Cursor c = db.query(ContactHelper.TABLE_NAME3, columns, ContactHelper.UID2 + " = '" + id + "'", null, null, null, null, null);
        Log.d("checking", "Query");
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            int cid = c.getInt(0);
            String numberAsString = Integer.toString(cid);
//            list.add(numberAsString);
            String name = c.getString(1);
            list.add(name);
            buffer.append(cid + " " + name + "\n");

        }
        return list;
    }


    static class ContactHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "shopping";
        private static final String TABLE_NAME1 = "product";
        private static final String TABLE_NAME2 = "category";
        private static final String TABLE_NAME3 = "users";
        private static final String TABLE_NAME7 = "retailer";
        private static final String TABLE_NAME8 = "orders";
        private static final String TABLE_NAME9 = "order_details";

        private static final String TABLE_NAME10 = "offer";
        private static final String TABLE_NAME11 = "offer_details";
        private static final String TABLE_NAME12 = "demand_list";
        private static final String TABLE_NAME13 = "likes_list";
        private static final String TABLE_NAME14 = "notification";
        private static final String TABLE_NAME15 = "special_event";

        private static final int DATABASE_VERSION = 15;

        private static final String category_id = "id";
        private static final String category_name = "name";

        private static final String demand_date = "date";
        private static final String likes_date = "date";


        private static final String product_category = "category";
        private static final String product_description = "discription";
        private static final String product_id = "productid";
        private static final String product_image = "image";
        private static final String product_item_count = "item_count";
        private static final String product_name = "name";
        private static final String product_price = "price";
        private static final String product_ret_id = "ret_id";
        private static final String product_sell_count = "sell_count";
        private static final String product_url = "imageurl";


        private static final String user_address = "address";
        private static final String user_contact = "contact";
        private static final String user_email = "email";
        private static final String user_password = "password";
        private static final String user_id = "_id";
        private static final String user_dp = "dp";
        private static final String user_creditcard = "creditcard";
        private static final String user_balance = "balance";
        private static final String user_name = "name";
        private static final String user_category = "category";


        private static final String discount = "discount";

        private static final String order_id = "order_id";
        private static final String order_date = "o_date";
        private static final String order_count = "count";

        private static final String event_description = "description";
        private static final String event_date = "e_date";


        private static final String notification_id = "notiid";
        private static final String notification_date = "ndate";
        private static final String notification_text = "count";

        private static final String offer_id = "offerid";
        private static final String offer_date = "odate";
        private static final String offer_description = "description";


        private static final String UID = "_id";
        private static final String UID2 = "Check_id";
        private static final String NAME = "name";
        private static final String EMAIL = "email";
        private static final String PHONE = "phone";

        public ContactHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d("checking", "Present");

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create
            Log.d("checking", "Creating");
            String query1 = "CREATE TABLE " + TABLE_NAME1 + " ( " + product_category + " VARCHAR(255) ," + product_description + " VARCHAR(255) ," + product_id + " INTEGER NOT NULL PRIMARY KEY  ," + product_image + " BLOB ," + product_item_count + " INTEGER ," + product_name + " VARCHAR(255) ," + product_price + " INTEGER ," + product_ret_id + " INTEGER ," + product_sell_count + " INTEGER ," + product_url + " VARCHAR(255) );";
            Log.d("checking", query1);
            try {
                db.execSQL(query1);
            } catch (SQLException e) {
                Log.d("check for something", "getting error1");
            }
            String query2 = "CREATE TABLE " + TABLE_NAME2 + " ( " + category_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," + category_name + " VARCHAR(255) );";
            Log.d("checking", query2);
            try {
                db.execSQL(query2);
            } catch (SQLException e) {
                Log.d("check for something", "getting error2");
            }
            String query3 = "CREATE TABLE " + TABLE_NAME3 + " ( " + user_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," + user_address + " VARCHAR(255) ," + user_balance + " INTEGER ," + user_dp + " BLOB ," + user_contact + " VARCHAR(255) ," + user_creditcard + " VARCHAR(255) ," + user_email + " VARCHAR(255) ," + user_name + " VARCHAR(255) ," + user_password + " VARCHAR(255) ," + user_category + " VARCHAR(255)  );";
            Log.d("checking", query3);

            try {
                db.execSQL(query3);
            } catch (SQLException e) {
                Log.d("check for something", "getting error3");
            }
            String query4 = "CREATE TABLE " + TABLE_NAME7 + " ( " + user_id + " INTEGER NOT NULL   );";
            Log.d("checking", query4);

            try {
                db.execSQL(query4);
            } catch (SQLException e) {
                Log.d("check for something", "getting error4");
            }
            String query5 = "CREATE TABLE " + TABLE_NAME8 + " ( " + order_id + " INTEGER NOT NULL PRIMARY KEY ," + order_date + " DATETIME DEFAULT CURRENT_TIMESTAMP ," + user_id + " INTEGER  );";
            Log.d("checking", query5);

            try {
                db.execSQL(query5);
            } catch (SQLException e) {
                Log.d("check for something", "getting error5");
            }
            String query6 = "CREATE TABLE " + TABLE_NAME9 + " ( " + order_count + " INTEGER ," + order_id + " INTEGER ," + product_id + " INTEGER  );";
            Log.d("checking", query6);

            try {
                db.execSQL(query6);
            } catch (SQLException e) {
                Log.d("check for something", "getting error6");
            }
            String query7 = "CREATE TABLE " + TABLE_NAME10 + " ( " + offer_description + " VARCHAR(255) ," + offer_id + " INTEGER PRIMRY KEY NOT NULL ," + offer_date + " DATETIME DEFAULT CURRENT_TIMESTAMP  );";
            Log.d("checking", query7);

            try {
                db.execSQL(query7);
            } catch (SQLException e) {
                Log.d("check for something", "getting error7");
            }
            String query8 = "CREATE TABLE " + TABLE_NAME11 + " ( " + order_count + " VARCHAR(255) ," + offer_id + " INTEGER  ," + product_id + " INTEGER  );";
            Log.d("checking", query8);

            try {
                db.execSQL(query8);
            } catch (SQLException e) {
                Log.d("check for something", "getting error8");
            }
            String query9 = "CREATE TABLE " + TABLE_NAME12 + " ( " + demand_date + " DATETIME DEFAULT CURRENT_TIMESTAMP ," + product_id + " INTEGER  ," + user_id + " INTEGER  );";
            Log.d("checking", query9);

            try {
                db.execSQL(query9);
            } catch (SQLException e) {
                Log.d("check for something", "getting error9");
            }
            String query10 = "CREATE TABLE " + TABLE_NAME13 + " ( " + likes_date + " DATETIME DEFAULT CURRENT_TIMESTAMP ," + product_id + " INTEGER  ," + user_id + " INTEGER  );";
            Log.d("checking", query10);

            try {
                db.execSQL(query10);
            } catch (SQLException e) {
                Log.d("check for something", "getting error10");
            }
            String query11 = "CREATE TABLE " + TABLE_NAME14 + " ( " + notification_id + " INTEGER ," + notification_date + " DATETIME DEFAULT CURRENT_TIMESTAMP  ," + notification_text + " VARCHAR(255) ," + user_id + " INTEGER  );";
            Log.d("checking", query11);

            try {
                db.execSQL(query11);
            } catch (SQLException e) {
                Log.d("check for something", "getting error11");
            }
            String query12 = "CREATE TABLE " + TABLE_NAME15 + " ( " + event_description + " VARCHAR(255) ," + event_date + " DATETIME DEFAULT CURRENT_TIMESTAMP   );";
            Log.d("checking", query12);

            try {
                db.execSQL(query12);
            } catch (SQLException e) {
                Log.d("check for something", "getting error12");
            }
            Log.d("check for creation", "yes it is obtain");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query1 = "DROP TABLE IF EXISTS " + TABLE_NAME1;
            String query2 = "DROP TABLE IF EXISTS " + TABLE_NAME2;
            String query3 = "DROP TABLE IF EXISTS " + TABLE_NAME3;
            String query7 = "DROP TABLE IF EXISTS " + TABLE_NAME7;
            String query8 = "DROP TABLE IF EXISTS " + TABLE_NAME8;
            String query9 = "DROP TABLE IF EXISTS " + TABLE_NAME9;
            String query10 = "DROP TABLE IF EXISTS " + TABLE_NAME10;
            String query11 = "DROP TABLE IF EXISTS " + TABLE_NAME11;
            String query12 = "DROP TABLE IF EXISTS " + TABLE_NAME12;
            String query13 = "DROP TABLE IF EXISTS " + TABLE_NAME13;
            String query14 = "DROP TABLE IF EXISTS " + TABLE_NAME14;
            String query15 = "DROP TABLE IF EXISTS " + TABLE_NAME15;
            try {
                db.execSQL(query1);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query2);
                Log.d("check for error", "getting hHH2");
                db.execSQL(query3);
                Log.d("check for error", "getting hHH3");
                db.execSQL(query7);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query8);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query9);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query10);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query11);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query12);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query13);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query14);
                Log.d("check for error", "getting hHH1");
                db.execSQL(query15);
                Log.d("check for error", "getting hHH1");
                onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error4");

            }
            Log.d("check for updation", "yes it is obtain");
        }
    }
}
