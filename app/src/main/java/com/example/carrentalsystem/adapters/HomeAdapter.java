package com.example.carrentalsystem.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response;
import com.example.carrentalsystem.CarDetailsActivity;
import com.example.carrentalsystem.R;
import com.example.carrentalsystem.model.Item;
import com.example.carrentalsystem.reservationform.ReservationDetials;

import org.json.JSONArray;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private static final int VIEW_TYPE_TOP_DEALS = 0;
    private static final int VIEW_TYPE_SPECIAL_DEAL = 1;
    public void setFilteredList(List<Item> filteredList){
           this.itemList=filteredList;
           notifyDataSetChanged();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

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
        holder.carid.setText(itemList.get(position).getCarid());
        holder.color=itemList.get(position).getColor();
        holder.model.setText(itemList.get(position).getModel());



        // Set onClickListener
          //Toyota
        //Honda
        //Ford
        //Cheverolet
        //Nisan
        //BMW
        //Audi
        //mercedes
        //hyundai
        //volkd
        if (itemList.get(position).getCarName().equalsIgnoreCase("bmw"))
        holder.card_iv.setImageResource(R.drawable.bmw);
        else if (itemList.get(position).getCarName().equalsIgnoreCase("volkswagen")){
            holder.card_iv.setImageResource(R.drawable.volkswagen);
        }
        else if (itemList.get(position).getCarName().equalsIgnoreCase("toyota")){
            holder.card_iv.setImageResource(R.drawable.toyota);
        }
        else if (itemList.get(position).getCarName().equalsIgnoreCase("Honda")){
            holder.card_iv.setImageResource(R.drawable.honda);
        }
        else if (itemList.get(position).getCarName().equalsIgnoreCase("Ford")){
            holder.card_iv.setImageResource(R.drawable.ford);
        }
        else if (itemList.get(position).getCarName().equalsIgnoreCase("Cheverolet")){
            holder.card_iv.setImageResource(R.drawable.cheverolet);
        }
        else if (itemList.get(position).getCarName().equalsIgnoreCase("Nisan")){
            holder.card_iv.setImageResource(R.drawable.nisan);
        }
        else if (itemList.get(position).getCarName().equalsIgnoreCase("Audi")){
            holder.card_iv.setImageResource(R.drawable.audi);
        }
        else if (itemList.get(position).getCarName().equalsIgnoreCase("mercedes")){
            holder.card_iv.setImageResource(R.drawable.mer);
        }
        else if (itemList.get(position).getCarName().equalsIgnoreCase("hyundai")){
            holder.card_iv.setImageResource(R.drawable.hyundai);
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
        private TextView carid;
        private String color;

        private TextView model;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            carName = itemView.findViewById(R.id.carname);
            card_iv=itemView.findViewById(R.id.card_iv);
            carid=itemView.findViewById(R.id.carid);
            model=itemView.findViewById(R.id.carmodel);

            itemView.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Dialog dialog=new Dialog(context);
                    dialog.setContentView(R.layout.activity_car_details);
                    ImageView cariv=dialog.findViewById(R.id.cariv);
                    TextView priceInput = dialog.findViewById(R.id.priceInput);
                    priceInput.setText(price.getText().toString());
                    TextView locationInput = dialog.findViewById(R.id.locationInput);
                    locationInput.setText(location.getText().toString());
                    TextView brandInput = dialog.findViewById(R.id.brandInput);
                    brandInput.setText(carName.getText().toString()+"/"+model.getText().toString());
                    TextView seatsInput = dialog.findViewById(R.id.seatsInput);
                    seatsInput.setText(seatsInput.getText().toString());
                    TextView text3 = dialog.findViewById(R.id.text3);
                    text3.setText(text3.getText().toString());
                    Button colorinput=dialog.findViewById(R.id.colorinput);
                    colorinput.setBackgroundColor(Color.parseColor(color));

                    cariv.setImageResource(getCarImageResource(carName.getText().toString()));
                    Button button = dialog.findViewById(R.id.bookBtn);
                    button.setOnClickListener(e -> {
                        Intent intent = new Intent(context, ReservationDetials.class);
                        context.startActivity(intent);
                        dialog.dismiss();
                    });

                    dialog.show();
                }
            });
        }

        public void showDialog() {
         Dialog dialog=new Dialog(context);
         dialog.setContentView(R.layout.activity_car_details);
         dialog.show();
        }
        private int getCarImageResource(String carName) {
            // Convert car name to lowercase and remove spaces
            String resourceName = carName.toLowerCase().replace(" ", "");
            // Get resource ID dynamically
            int resId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
            // Return the resource ID if found, otherwise return a default image resource ID
            return resId != 0 ? resId : R.drawable.mer;
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
