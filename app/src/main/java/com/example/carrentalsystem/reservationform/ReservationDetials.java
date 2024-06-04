package com.example.carrentalsystem.reservationform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carrentalsystem.MainActivityForUser;
import com.example.carrentalsystem.R;

public class ReservationDetials extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView availableFromDate;
    private ImageView backArrow;
    String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_detials);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        calendarView=findViewById(R.id.calendarView);
        availableFromDate=findViewById(R.id.availableFromDate);
        backArrow=findViewById(R.id.backArrow);
        calendarView
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                     CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                 Date
                                        = dayOfMonth + "-"
                                        + (month + 1) + "-" + year;

                                // set this date in TextView for Display
                                availableFromDate.setText("select your date "+Date);
                            }
                        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReservationDetials.this, MainActivityForUser.class);
                startActivity(intent);
            }
        });
    }

    public void choosePaymentClick(View view) {
        Intent intent=new Intent(ReservationDetials.this,MainActivityForUser.class);
        intent.putExtra("DATE",Date);
        startActivity(intent);
    }
}