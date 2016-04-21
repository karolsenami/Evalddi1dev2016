package com.example.intervenant.myapplication.com.example.intervenant.core;

import android.app.DownloadManager;
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
import com.example.intervenant.myapplication.ProductDetailActivity;
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

    public static  ArrayList<Product> provideFromFavorite(Context ctx){
        SharedPreferences data = ctx.getSharedPreferences("data", 0);
        ArrayList<Product> list = new ArrayList<>();
        String strJson = data.getString("favoritesProducts","[]");//second parameter is necessary ie.,Value to return if this preference does not exist.
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

//        Log.i("provide","From Provide there are "+list.get(0).name+list.get(0).favorite+" "+list.get(1).name+list.get(1).favorite+" "+list.get(3).name+list.get(3).favorite);
        Log.i("provide","From Provide there are "+list.size());
        return list;
    }

    public static boolean isInFavorite(Context ctx, String name) {
        ArrayList<Product> list;
        list = ProductProvider.provideFromFavorite(ctx);
        int i;
        for (i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(name)) {
                Log.i("isInFav","equals");
                break;
            }
        }
        return i != list.size();
    }

    public static void putProductInFavorite(Context ctx, Product produit){
        ArrayList<Product> list = ProductProvider.provideFromFavorite(ctx);
        Log.i("add","before"+list.size());
//        if(!ProductProvider.isInFavorite(ctx, f)){
        list.add(produit);
/*        } else {
            Log.i("add","was already !");
        }
*/
        Log.i("add","after"+list.size());
        ProductProvider.saveToMemory(ctx,list);
    }


    public static void removeProductFromFavorite(Context ctx, Product p){
        ArrayList<Product> list = ProductProvider.provideFromFavorite(ctx);
        Log.i("remove","before"+list.size());
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(p.getName())) {
                list.remove(i);
                break;
            }
        }
        Log.i("remove","after"+list.size());
        ProductProvider.saveToMemory(ctx,list);
    }

    public static void saveToMemory(Context ctx, ArrayList<Product> list) {
        Log.i("save","saving1");

        SharedPreferences data = ctx.getSharedPreferences("data",0);
        SharedPreferences.Editor prefsEditor = data.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString("favoritesProducts", json);
        prefsEditor.commit();
    }

    public static void cleanFavorite(Context ctx) {
        ArrayList<Product> list = new ArrayList<>();
        saveToMemory(ctx,list);
    }
}
