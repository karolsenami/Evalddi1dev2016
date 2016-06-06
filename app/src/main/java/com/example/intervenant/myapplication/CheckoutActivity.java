package com.example.intervenant.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.intervenant.myapplication.R;
import com.example.intervenant.myapplication.com.example.intervenant.core.ProductProvider;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Intent intent = getIntent();
        if(intent != null){
            Double totalPrice =  intent.getDoubleExtra("totalPrice",0);
            TextView totalPriceTextView = (TextView)findViewById(R.id.total_price_text);
            totalPriceTextView.setText(String.valueOf(totalPrice));

            Button myButton = (Button) findViewById(R.id.pay_button);
            if (myButton != null) {
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //on va fakey lbordel
                        ProductProvider.cleanCart(getBaseContext());
                        finish();
                    }
                });
            }
        }
    }
}
