package com.example.carrentalsystem.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrentalsystem.CarDetailsActivity;
import com.example.carrentalsystem.R;
import com.example.carrentalsystem.adapters.HomeAdapter;
import com.example.carrentalsystem.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView topDealRV;
    private HomeAdapter adapter;
    private List<Item> itemList;


    private RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topDealRV = view.findViewById(R.id.topdealsRV);
        queue = Volley.newRequestQueue(getContext());

        String url = "http://10.0.2.2:80/rest/conn.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                itemList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        itemList.add( new Item(obj.getString("chapterlocation"),obj.getString("brand"),obj.getString("price")));
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                adapter=new HomeAdapter(getContext(),
                        itemList
                         );
                topDealRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                topDealRV.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);

       /* itemList=new ArrayList<>();
        Item item1 = new Item("Ramallah","Marcedes","100");

        itemList.add(item1);
        itemList.add(new Item("Birzeit","volkswagen","90"));
        itemList.add(new Item("Nablus","bmw","100"));
        adapter=new HomeAdapter(getContext(),itemList);
        topDealRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        topDealRV.setAdapter(adapter);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    public void OnItemPosition(int position) {
       Intent intent =new Intent(getContext(),
               CarDetailsActivity.class);
       startActivity(intent);
    }
}