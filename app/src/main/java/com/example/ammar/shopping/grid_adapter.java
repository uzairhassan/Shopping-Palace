package com.example.ammar.shopping;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by uzairhassan on 01/05/2017.
 */

public class grid_adapter extends BaseAdapter {
    private Context mycontext;
    private LayoutInflater myinflater;
    private List<my_product> products;

    public grid_adapter(Context mycontext, List<my_product> products) {
        this.mycontext = mycontext;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        my_product current = products.get(position);
        LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            myinflater = (LayoutInflater) mycontext.getSystemService(mycontext.LAYOUT_INFLATER_SERVICE);
            convertView = myinflater.inflate(R.layout.layout_product, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.layout_product_name);
            holder.price= (TextView) convertView.findViewById(R.id.layout_product_price);
            holder.image= (ImageView) convertView.findViewById(R.id.layout_product_image);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(current.getName());
        holder.price.setText(current.getPrice()+ " $");
        if(current.getImage()!=null) {
            holder.image.setImageBitmap(current.getImage());
        }
        else
        {
            holder.image.setImageResource(R.drawable.watch);
        }
        Bitmap mIcon11 = null;
        String urldisplay=current.getUrl();
//        try {
//            if(!urldisplay.equals("")) {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            }
//        } catch (Exception e) {
//            Log.e("Error", e.getMessage());
//            e.printStackTrace();
//        }
//        holder.image.setImageBitmap(mIcon11);





        return convertView;

    }
    static class ViewHolder
    {
        TextView name;
        TextView price;
        ImageView image;
    }

}
