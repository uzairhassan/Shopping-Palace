package com.example.ammar.shopping;

import java.util.Vector;

/**
 * Created by uzairhassan on 01/05/2017.
 */

 public class hold_categories {
    container cont;
    Vector getcat()
    {
        return cont.categories;
    }
    hold_categories()
    {
        cont= new container();
    }
    static  class container{
        public Vector categories;
        container()
        {
             categories=new Vector();
            categories.add("All Categories");
            categories.add("Drinks");
            categories.add("Food");
            categories.add("Clothes");
            categories.add("Cleaners");
            categories.add("Paper Goods");
            categories.add("Books");
            categories.add("DVD");
            categories.add("Sports");
            categories.add("Games");
            categories.add("Kitchen Items");
            categories.add("Medicine");
            categories.add("Beauty");
            categories.add("Home Appliances");
            categories.add("Pets");
            categories.add("Footwear");
            categories.add("Wearables");
            categories.add("Electronics");
            categories.add("Mobile/Tablets");
            categories.add("Computer/Laptop");
            categories.add("Jewellery");
            categories.add("Stationary");
            categories.add("Toys");
            categories.add("Cosmetics");
            categories.add("Deodrant");
        }
    }
}
