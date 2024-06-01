package com.example.carrentalsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalsystem.R;
import com.example.carrentalsystem.model.Item;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;

    public HomeAdapter(Context context, List<Item> itemList) {
        this.context=context;
        this.itemList=itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.top_deals_card,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.price.setText(itemList.get(position).getPrice());
      holder.carName.setText(itemList.get(position).getCarName());
      holder.location.setText(itemList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView price;
        private TextView location;
        private TextView carName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price);
            location=itemView.findViewById(R.id.location);
            carName=itemView.findViewById(R.id.carname);
        }
    }
}
