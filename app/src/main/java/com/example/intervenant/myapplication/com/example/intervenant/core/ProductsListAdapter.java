package com.example.intervenant.myapplication.com.example.intervenant.core;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.intervenant.myapplication.R;

import java.util.ArrayList;

public class ProductsListAdapter extends BaseAdapter {

    ArrayList<Product> cartProducts;

    public ProductsListAdapter(ArrayList<Product> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @Override
    public int getCount() {
        return cartProducts.size();
    }

    @Override
    public Product getItem(int i) {
        return cartProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {
        Product product = getItem(i);
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
            holder.priceText  = (TextView) view.findViewById(R.id.price_text);
            holder.imgView = (ImageView) view.findViewById(R.id.cart_img);
            holder.nameText  = (TextView) view.findViewById(R.id.name_text);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.nameText.setText(product.getName());
        holder.priceText.setText(product.getPrice());
        Glide.with(viewGroup.getContext()).load(cartProducts.get(i).getImageUrl()).into(holder.imgView);

        Button button = (Button) view.findViewById(R.id.remove_button);
        button.setTag(i);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                Log.i("removeButtonClick","remove at "+position);
                ProductProvider.removeProductFromCart(viewGroup.getContext(),getItem(position).getName());
                update(ProductProvider.provideFromCart(viewGroup.getContext()));
            }
        });
        return view;
    }

    public void update(ArrayList<Product> array) {
        cartProducts.clear();
        cartProducts.addAll(array);
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        ImageView imgView;
        TextView priceText;
        TextView nameText;
    }
}
