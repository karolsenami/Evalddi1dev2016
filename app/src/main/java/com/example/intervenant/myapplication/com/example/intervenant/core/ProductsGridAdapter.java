package com.example.intervenant.myapplication.com.example.intervenant.core;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.intervenant.myapplication.R;

import java.util.ArrayList;

/**
 * Created by mjourdain on 21/04/2016.
 */
public class ProductsGridAdapter extends BaseAdapter {

    ArrayList<Product> products;

    public ProductsGridAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View gridView;

        if (view == null) {
            gridView = new View(viewGroup.getContext());

            gridView = inflater.inflate(R.layout.grid_item, null);

            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
            Glide.with(viewGroup.getContext()).load(products.get(i).getImageUrl()).into(imageView);
            TextView textView = (TextView)gridView.findViewById(R.id.grid_item_text);
            textView.setText(products.get(i).getName());
        } else {
            gridView = view;
        }


        return gridView;
    }

    public void update(ArrayList<Product> array) {
        products.clear();
        products.addAll(array);
        this.notifyDataSetChanged();
    }
}
