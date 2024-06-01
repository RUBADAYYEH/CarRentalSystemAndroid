package com.example.carrentalsystem.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalsystem.R;
import com.example.carrentalsystem.adapters.HomeAdapter;
import com.example.carrentalsystem.databinding.FragmentHomeBinding;
import com.example.carrentalsystem.model.Item;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView topDealRV;
    private HomeAdapter adapter;
    private List<Item> itemList;

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

        itemList=new ArrayList<>();
        itemList.add(new Item("Ramallah","Marcedes","100"));
        itemList.add(new Item("Birzeit","Toyota","90"));
        itemList.add(new Item("Nablus","Marcedes","100"));
        adapter=new HomeAdapter(getContext(),itemList);
        topDealRV.setLayoutManager(new LinearLayoutManager(getContext()));
        topDealRV.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}