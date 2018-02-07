//package com.example.ammar.shopping;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
//import java.util.Vector;
//
//public class categories_list extends AppCompatActivity implements ListView.OnItemClickListener {
//int user_logged_in;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setTitle("Categories");
//        Bundle extras = getIntent().getExtras();
//        if (extras == null) {
//            user_logged_in = 0;
//        } else {
//            user_logged_in = extras.getInt("user");
//
//
//        }
//
//        LinearLayout mContainer = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_categories_list, null);
//        setContentView(mContainer);
//        ListView activeList = (ListView) mContainer.findViewById(R.id.catt);
//        activeList.setOnItemClickListener(this);
//        //ListView lv=(ListView)findViewById(R.id.catt);
//
//        Vector categories = (new hold_categories().getcat());
//        String[] choice = new String[categories.size()];
//        for (int i = 0; i < categories.size(); i++) {
//            choice[i] = categories.elementAt(i).toString();
//        }
//        //  ArrayAdapter<String> abc= new ArrayAdapter<String>(this,R.layout.activity_categories_list,choice);
//        activeList.setAdapter(new categories_adapter(getApplicationContext(), choice));
//        //       setContentView(activeList);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        String temp = (String) adapterView.getItemAtPosition(position);
//        Intent intent = new Intent(categories_list.this, show_products.class);
//        intent.putExtra("category", temp);
//        intent.putExtra("user",user_logged_in);
//        startActivity(intent);
//
//    }
//}
