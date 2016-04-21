package com.example.intervenant.myapplication.com.example.intervenant.core;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mjourdain on 21/04/2016.
 */
public class Product {

    private String name;
    private String image;
    private String price;
    private String info;

    public Product(String name, String imageUrl, String price, String info) {
        this.name = name;
        this.image = imageUrl;
        this.price = price;
        this.info = info;
    }

    public Product(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString("name");
        this.price = jsonObject.getString("price");
        this.info = jsonObject.getString("info");
        this.image = jsonObject.getString("image");
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() { return image; }

    public String getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }
}
