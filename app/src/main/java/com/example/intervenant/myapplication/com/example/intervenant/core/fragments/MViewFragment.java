package com.example.intervenant.myapplication.com.example.intervenant.core.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.intervenant.myapplication.ProductDetailActivity;
import com.example.intervenant.myapplication.R;
import com.example.intervenant.myapplication.com.example.intervenant.core.Product;
import com.example.intervenant.myapplication.com.example.intervenant.core.ProductProvider;
import com.example.intervenant.myapplication.com.example.intervenant.core.ProductsGridAdapter;
import com.example.intervenant.myapplication.com.example.intervenant.core.ProductsListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MViewFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private int type;

    private ListView listView;
    private GridView gridView;


    private ArrayList<Product> products;
    private ArrayList<Product> cartProducts;

    ProductsListAdapter listAdapter;
    ProductsGridAdapter gridAdapter;

    private OnFragmentInteractionListener mListener;

    public MViewFragment() {}

    public static MViewFragment newInstance(int type) {
        MViewFragment fragment = new MViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_PARAM1);

            switch (type) {
                case 0:
                    //gridView
                    products = new ArrayList<>();
                    gridAdapter = new ProductsGridAdapter(products);
                    break;
                case 1:
                    //gridView
                    cartProducts = new ArrayList<>();
                    listAdapter = new ProductsListAdapter(cartProducts);
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (type) {
            case 0:
                ProductProvider.provideFromServer(getContext(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();
                        JSONArray json = response.optJSONArray("data");

                        Type listType = new TypeToken<List<Product>>() {
                        }.getType();

                        ArrayList<Product> array = gson.fromJson(json.toString(), listType);
                        gridAdapter.update(array);
                    }
                });
                break;

            case 1:
                ArrayList<Product> array = ProductProvider.provideFromCart(getContext());
                listAdapter.update(array);
                break;

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (type == 0) {
            View view = inflater.inflate(R.layout.fragment_gridview, container, false);

            gridView = (GridView) view.findViewById(R.id.gridview);
            gridView.setAdapter(gridAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent detailIntent = new Intent(getContext(), ProductDetailActivity.class);
                    Product product = gridAdapter.getItem(position);
                    detailIntent.putExtra("name", product.getName());
                    detailIntent.putExtra("imageUrl", product.getImageUrl());
                    detailIntent.putExtra("price", product.getPrice());
                    detailIntent.putExtra("info", product.getInfo());
                    startActivity(detailIntent);
                }
            });

            return view;
        } else {
            View view =  inflater.inflate(R.layout.fragment_listview, container, false);

            listView = (ListView)view.findViewById(R.id.listView);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("onItemClick List","Just cicked at "+position);
                    Toast toast = Toast.makeText(getContext(),"Slide to remove nigga", Toast.LENGTH_LONG);
                    toast.show();
/*                    Intent detailIntent = new Intent(getContext(), ProductDetailActivity.class);
                    Product product = listAdapter.getItem(position);
                    detailIntent.putExtra("name", product.getName());
                    detailIntent.putExtra("imageUrl", product.getImageUrl());
                    detailIntent.putExtra("price", product.getPrice());
                    detailIntent.putExtra("info", product.getInfo());
                    startActivity(detailIntent);
                    */
                }
            });

            return view;
        }

    }


    /*
        // TODO: Rename method, update argument and hook method into UI event
        public void onButtonPressed(Uri uri) {
            if (mListener != null) {
                mListener.onFragmentInteraction(uri);
            }
        }
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ListView getListView() {
        return listView;
    }

    public GridView getGridView() {
        return gridView;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Product product);
    }
}
