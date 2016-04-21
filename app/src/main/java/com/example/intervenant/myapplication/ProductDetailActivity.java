package com.example.intervenant.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.intervenant.myapplication.com.example.intervenant.core.Product;
import com.example.intervenant.myapplication.com.example.intervenant.core.ProductProvider;

/**
 * Created by mjourdain on 21/04/2016.
 */
public class ProductDetailActivity extends AppCompatActivity {
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(intent != null){

            String name =  intent.getStringExtra("name");
            String imageUrl = intent.getStringExtra("imageUrl");
            String price= intent.getStringExtra("price");
            String info = intent.getStringExtra("info");
            product = new Product(name,imageUrl,price,info);

            TextView priceTextView = (TextView)findViewById(R.id.price);
            priceTextView.setText(product.getPrice());
            TextView infoTextView = (TextView)findViewById(R.id.info);
            infoTextView.setText(product.getInfo());
            ImageView imageView = (ImageView)findViewById(R.id.detail_image);
            Glide.with(this).load(product.getImageUrl()).into(imageView);
        }


        Button myButton = (Button) findViewById(R.id.cart_button);
        if (myButton != null) {
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductProvider.putProductInCart(ProductDetailActivity.this,product);
                }
            });
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //this.finish();
                break;
        }
        return true;
    }
}
