package com.example.carrentalsystem.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
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
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView topDealRV;
    private HomeAdapter adapter;
    private HomeAdapter adapter1;
    private List<Item> itemList;
    private RecyclerView searchcarsRV;
    private TextView searchresultTV;
    private SearchView searchView;

    RelativeLayout sevenseatslayout;
    RelativeLayout fiveseatslayout;
    RelativeLayout twoseatslayout;

    RelativeLayout lowtohigh;
    RelativeLayout pricehightolow;
    LinearLayout filterlayout;
    public int seatsNumberLayoutClicked;


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
        filterlayout=view.findViewById(R.id.filterlayout);
         sevenseatslayout=view.findViewById(R.id.sevenseatslayout);
         sevenseatslayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (sevenseatslayout.getBackground() == null) {
                     sevenseatslayout.setBackgroundResource(R.drawable.bacjground_withstroke);
                     fiveseatslayout.setBackground(null);
                     twoseatslayout.setBackground(null);
                     seatsNumberLayoutClicked=0;
                     filterListBasedOnSeats(seatsNumberLayoutClicked);
                 } else {
                     sevenseatslayout.setBackground(null);
                     adapter1.setFilteredList(itemList);
                 }


             }
         });
         fiveseatslayout=view.findViewById(R.id.fiveseatslayout);
        fiveseatslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fiveseatslayout.getBackground() == null) {
                    fiveseatslayout.setBackgroundResource(R.drawable.bacjground_withstroke);
                    sevenseatslayout.setBackground(null);
                    twoseatslayout.setBackground(null);
                    seatsNumberLayoutClicked = 1;
                    filterListBasedOnSeats(seatsNumberLayoutClicked);
                }else {
                    fiveseatslayout.setBackground(null);

                    adapter1.setFilteredList(itemList);
                }
            }
        });
         twoseatslayout=view.findViewById(R.id.twoseatslayout);
        twoseatslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twoseatslayout.getBackground() == null) {
                    twoseatslayout.setBackgroundResource(R.drawable.bacjground_withstroke);
                    fiveseatslayout.setBackground(null);
                    sevenseatslayout.setBackground(null);
                    seatsNumberLayoutClicked = 2;
                    filterListBasedOnSeats(seatsNumberLayoutClicked);
                }else {
                    twoseatslayout.setBackground(null);
                    adapter1.setFilteredList(itemList);
                }
            }
        });

         lowtohigh=view.findViewById(R.id.lowtohigh);
         lowtohigh.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 lowtohigh.setBackgroundResource(R.drawable.button_background);
                 pricehightolow.setBackground(null);
                 filterBasedOnPrice(0);
             }
         });
         pricehightolow=view.findViewById(R.id.pricehightolow);
        pricehightolow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pricehightolow.setBackgroundResource(R.drawable.button_background);
               lowtohigh.setBackground(null);
                filterBasedOnPrice(1);
            }
        });


        topDealRV = view.findViewById(R.id.topdealsRV);
        searchcarsRV=view.findViewById(R.id.searchcarsRV);
        searchresultTV=view.findViewById(R.id.searchresultTV);
        queue = Volley.newRequestQueue(getContext());
        searchView=view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        String url = "http://10.0.2.2:80/rest/conn.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                itemList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        itemList.add( new Item(obj.getString("chapterlocation"),obj.getString("brand"),obj.getString("price"),obj.getString("carid"),obj.getString("color"),obj.getString("model"),obj.getString("seatsnumber")));
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                adapter=new HomeAdapter(getContext(),
                        itemList
                         );
                adapter1=new HomeAdapter(getContext(),
                        itemList
                );
                topDealRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                topDealRV.setAdapter(adapter);
                searchcarsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                searchcarsRV.setAdapter(adapter1);

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

    private void filterBasedOnPrice(int i) {
        if (i==0){
            //low to high filter
            Collections.sort(adapter1.getItemList());
            adapter1.setFilteredList(adapter1.getItemList());
        }
        else{
            //high to low filter
            Collections.sort(adapter1.getItemList(), Collections.reverseOrder());
            adapter1.setFilteredList(adapter1.getItemList());
        }
    }

    private  void filterList(String text) {
        adapter1.setFilteredList(itemList);
        List<Item> filteredList=new ArrayList<>();
        if (text.contains(",")) {
            String[] search = text.split(",");
            for (Item item:adapter1.getItemList()){
                if (item.getCarName().toLowerCase().contains(search[0].toLowerCase())&&item.getModel().toLowerCase().contains(search[1].toLowerCase())){
                    if (twoseatslayout.getBackground() != null){

                        if (item.getSeatsNumber().toLowerCase().contains("2")) {
                            filteredList.add(item);
                        }


                    } else if (fiveseatslayout.getBackground() != null) {

                        if (item.getSeatsNumber().toLowerCase().contains("5")) {
                            filteredList.add(item);
                        }


                    }
                    else if (sevenseatslayout.getBackground() != null) {

                        if (item.getSeatsNumber().toLowerCase().contains("7")) {
                            filteredList.add(item);
                        }


                    }
                    else{
                        filteredList.add(item);
                    }
                }
            }

        }
        else{
            for (Item item:adapter1.getItemList()){
                if (item.getCarName().toLowerCase().contains(text.toLowerCase())){
                    if (twoseatslayout.getBackground() != null){

                            if (item.getSeatsNumber().toLowerCase().contains("2")) {
                                filteredList.add(item);
                            }


                    } else if (fiveseatslayout.getBackground() != null) {

                            if (item.getSeatsNumber().toLowerCase().contains("5")) {
                                filteredList.add(item);
                            }


                    }
                    else if (sevenseatslayout.getBackground() != null) {

                            if (item.getSeatsNumber().toLowerCase().contains("7")) {
                                filteredList.add(item);
                            }


                    }
                    else{
                        filteredList.add(item);
                    }
                }
                else if (item.getModel().toLowerCase().contains(text.toLowerCase())){
                    if (twoseatslayout.getBackground() != null){

                        if (item.getSeatsNumber().toLowerCase().contains("2")) {
                            filteredList.add(item);
                        }


                    } else if (fiveseatslayout.getBackground() != null) {

                        if (item.getSeatsNumber().toLowerCase().contains("5")) {
                            filteredList.add(item);
                        }


                    }
                    else if (sevenseatslayout.getBackground() != null) {

                        if (item.getSeatsNumber().toLowerCase().contains("7")) {
                            filteredList.add(item);
                        }


                    }
                    else{
                        filteredList.add(item);
                    }
                }

            }
        }


        if (filteredList.isEmpty() || text.isEmpty()){
        Toast.makeText(getContext(),"No Results Found",Toast.LENGTH_SHORT).show();
          adapter1.setFilteredList(filteredList);
            searchcarsRV.setVisibility(View.GONE);
            searchresultTV.setVisibility(View.GONE);
            fiveseatslayout.setBackground(null);
            sevenseatslayout.setBackground(null);
            twoseatslayout.setBackground(null);
            filterlayout.setVisibility(View.GONE);
      }
      else{
          adapter1.setFilteredList(filteredList);
          searchcarsRV.setVisibility(View.VISIBLE);
          searchresultTV.setVisibility(View.VISIBLE);
            filterlayout.setVisibility(View.VISIBLE);
      }
    }
    private  void filterListBasedOnSeats(int position) {
        adapter1.setFilteredList(itemList);
        List<Item> filteredList=new ArrayList<>();
        if (position ==0){
            //based on 7 seats
            for (Item item:adapter1.getItemList()) {
                if (item.getSeatsNumber().toLowerCase().contains("7")) {
                    filteredList.add(item);
                }
            }
        }else if (position==1){
            //based on 5 seats
            for (Item item:adapter1.getItemList()) {
                if (item.getSeatsNumber().toLowerCase().contains("5")) {
                    filteredList.add(item);
                }
            }
        }else{
            //based on 2 seats
            for (Item item:adapter1.getItemList()) {
                if (item.getSeatsNumber().toLowerCase().contains("2")) {
                    filteredList.add(item);
                }
            }

        }
        if (filteredList.isEmpty()){
            Toast.makeText(getContext(),"No Results Found",Toast.LENGTH_SHORT).show();
            adapter1.setFilteredList(filteredList);
            searchcarsRV.setVisibility(View.GONE);
            searchresultTV.setVisibility(View.GONE);
            fiveseatslayout.setBackground(null);
            sevenseatslayout.setBackground(null);
            twoseatslayout.setBackground(null);
            filterlayout.setVisibility(View.GONE);
        }
        else{
            adapter1.setFilteredList(filteredList);
            searchcarsRV.setVisibility(View.VISIBLE);
            searchresultTV.setVisibility(View.VISIBLE);
            filterlayout.setVisibility(View.VISIBLE);
        }
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