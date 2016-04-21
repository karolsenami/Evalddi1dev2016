package com.example.intervenant.myapplication.com.example.intervenant.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by intervenant on 18/04/16.
 */
public class ProductProvider {
    public static void provideFromServer(final Context ctx, Response.Listener<JSONObject> cb) {


        String url = "https://api.myjson.com/bins/27dd6";
        RequestQueue mRequestQueue = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest (Request.Method.GET, url, null,cb, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        mRequestQueue.add(jsObjRequest);
    }

    public static  ArrayList<Product> provideFromCart(Context ctx){
        SharedPreferences data = ctx.getSharedPreferences("data", 0);
        ArrayList<Product> list = new ArrayList<>();
        String strJson = data.getString("cartProducts","[]");//second parameter is necessary ie.,Value to return if this preference does not exist.
        if(strJson != null) {
            try {
                JSONArray jsonData = new JSONArray(strJson);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Product>>() {}.getType();
                list = gson.fromJson(jsonData.toString(), listType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("provide","There was "+list.size()+" items in the cart");
        return list;
    }

    public static int countInCart(Context ctx, String name) {
        ArrayList<Product> list;
        list = ProductProvider.provideFromCart(ctx);
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(name)) {
                count ++;
            }
        }
        return count;
    }

    public static void putProductInCart(Context ctx, Product product){
        ArrayList<Product> list = ProductProvider.provideFromCart(ctx);
        if(list == null)
            list = new ArrayList<>();
        list.add(product);

        ProductProvider.saveToMemory(ctx,list);
    }


    public static void removeProductFromCart(Context ctx, String productName){
        ArrayList<Product> list = ProductProvider.provideFromCart(ctx);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(productName)) {
                list.remove(i);
                break;
            }
        }
        Log.i("remove","after"+list.size());
        ProductProvider.saveToMemory(ctx,list);
    }

    public static void saveToMemory(Context ctx, ArrayList<Product> list) {
        SharedPreferences data = ctx.getSharedPreferences("data",0);
        SharedPreferences.Editor prefsEditor = data.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString("cartProducts", json);
        prefsEditor.commit();
    }

    public static double getTotalCartPrice(Context ctx) {
        ArrayList<Product> list = ProductProvider.provideFromCart(ctx);
        double price = 0;
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            price += Double.valueOf(p.getPrice());
        }
        return price;
    }

    public static void cleanCart(Context ctx) {
        ArrayList<Product> list = new ArrayList<>();
        saveToMemory(ctx,list);
    }
}
