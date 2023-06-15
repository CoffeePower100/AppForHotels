package com.example.appforhotels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HotelMainPage extends AppCompatActivity {

    ImageView hImg;
    TextView hName, hAddr, hPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main_page);

        hImg = findViewById(R.id.hImg);

        try {
            Picasso.get()
                    .load(this.getIntent().getStringExtra("img"))
                    .resize(100, 80)
                    .centerCrop()
                    .into(hImg);
        }
        catch (Exception err)
        {
        }

        hName = findViewById(R.id.hName);
        hName.setText(this.getIntent().getStringExtra("name"));


        hAddr = findViewById(R.id.hAddr);
        hAddr.setText(this.getIntent().getStringExtra("addr"));


        hPrice = findViewById(R.id.hPrice);
        hPrice.setText(this.getIntent().getStringExtra("price"));
    }
}