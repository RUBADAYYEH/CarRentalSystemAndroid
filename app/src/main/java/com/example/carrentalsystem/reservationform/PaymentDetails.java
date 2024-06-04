package com.example.carrentalsystem.reservationform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carrentalsystem.MainActivityForUser;
import com.example.carrentalsystem.R;

public class PaymentDetails extends AppCompatActivity {
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_detials);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backArrow=findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentDetails.this, MainActivityForUser.class);
                Toast.makeText(PaymentDetails.this,"Booking Process cancelled",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    public void confirmPaymentClick(View view) {
        Intent intent=new Intent(this,MainActivityForUser.class);
        startActivity(intent);
    }
}