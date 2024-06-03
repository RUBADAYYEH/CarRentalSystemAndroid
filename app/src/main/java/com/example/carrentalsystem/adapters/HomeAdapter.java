package com.example.carrentalsystem.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response;
import com.example.carrentalsystem.CarDetailsActivity;
import com.example.carrentalsystem.R;
import com.example.carrentalsystem.model.Item;

import org.json.JSONArray;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private static final int VIEW_TYPE_TOP_DEALS = 0;
    private static final int VIEW_TYPE_SPECIAL_DEAL = 1;


    public HomeAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

            view = LayoutInflater.from(context).inflate(R.layout.top_deal_car_card, parent, false);
            return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.price.setText(itemList.get(position).getPrice());
        holder.carName.setText(itemList.get(position).getCarName());
        holder.location.setText(itemList.get(position).getLocation());



        // Set onClickListener

        if (itemList.get(position).getCarName().contains("bmw"))
        holder.card_iv.setImageResource(R.drawable.bmw);
        else if (itemList.get(position).getCarName().contains("volks")){
            holder.card_iv.setImageResource(R.drawable.volkswagen);
        }
        else {
            holder.card_iv.setImageResource(R.drawable.mer);
        }

    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatButton viewButton;
        private TextView price;
        private TextView location;
        private TextView carName;
        private ImageView card_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            carName = itemView.findViewById(R.id.carname);
            card_iv=itemView.findViewById(R.id.card_iv);
            itemView.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });
        }

        public void showDialog() {
         Dialog dialog=new Dialog(context);
         dialog.setContentView(R.layout.activity_car_details);
         dialog.show();
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Example condition for different view types
        if (position == 1) {
            return VIEW_TYPE_SPECIAL_DEAL;
        } else {
            return VIEW_TYPE_TOP_DEALS;
        }
    }
}
