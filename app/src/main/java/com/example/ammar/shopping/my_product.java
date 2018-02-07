package com.example.ammar.shopping;

import android.graphics.Bitmap;

/**
 * Created by uzairhassan on 01/05/2017.
 */

public class my_product {
    private Bitmap image;
    private String name;
    private String price;
    private String desc;
    private  int itemcount;
    private String url;
    private int sellcount;
    private int retid;
    String category;
    int id;
    my_product(Bitmap i,String p, String n,String d,int idd,int ic,int sc,String cat,int retid,String uu)
    {
        name=n;price=p;image=i;id=idd;desc=d;itemcount=ic;sellcount=sc;category=cat;this.retid=retid;url=uu;
    }
int getRetid()
{
    return retid;
}
    String getName()
    {
     return name;
    }
    String getPrice()
    {
        return  price;
    }
    Bitmap getImage()
    {
        return  image;
    }
    int getId(){return id;}
    String getDesc(){return desc;}
    int getItemcount()
    {
        return  itemcount;
    }
    int getSellcount()
    {
        return  sellcount;
    }
    String getCategory()
    {
        return category;
    }
    String getUrl()
    {
        return url;
    }
}
