package com.example.ammar.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by uzairhassan on 01/05/2017.
 */

public class categories_adapter extends BaseAdapter {
    private Context mycontext;
    private LayoutInflater myinflater;
    private String[] categories;

    public categories_adapter(Context mycontext, String[] categories) {
        this.mycontext = mycontext;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Object getItem(int position) {
        return categories[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        String current = categories[position];
        LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            myinflater = (LayoutInflater) mycontext.getSystemService(mycontext.LAYOUT_INFLATER_SERVICE);
            convertView = myinflater.inflate(R.layout.layout_categories_textview, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.categories_inflate);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(current);
        return convertView;

    }
    static class ViewHolder
    {
        TextView name;
    }
}
