package com.example.appforhotels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Piccaso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HotelAdapter extends ArrayAdapter<Hotel>
{
    private ArrayList<Hotel> hotelsList;
    private Context context;

    public HotelAdapter(ArrayList<Hotel> data, Context context) {
        super(context, R.layout.hotel_post, data);
        this.hotelsList = data;
        this.context = context;
    }

     static class ViewHolder {
        private TextView hName, hAddr, hPrice;
        private ImageView hImg;

        public String getName()
        {
            return hName.toString();
        }

        public String getAddr()
        {
            return hAddr.toString();
        }

        public String getPrice()
        {
            return hPrice.toString();
        }

        public String getImg()
        {
            return ((String)(hImg.getTag()));
        }


    }

    @NonNull
    @Override
    public View getView(
            int position,
            @Nullable View convertView,
            @NonNull ViewGroup parent) {

        //Get the data item for this position
        Hotel currHotel = getItem(position);

        //Check if an existing view is being reused
        ViewHolder viewHolder;

        final View result;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.hotel_post, parent, false);

            viewHolder.hName = convertView.findViewById(R.id.hName);
            viewHolder.hPrice = convertView.findViewById(R.id.hPrice);
            viewHolder.hAddr = convertView.findViewById(R.id.hAddr);
            viewHolder.hImg = convertView.findViewById(R.id.hImg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //Get the data
        viewHolder.hName.setText(currHotel.getName());
        viewHolder.hPrice.setText(currHotel.getPrice());
        viewHolder.hAddr.setText(currHotel.getAddr());
        try {
            Picasso.get()
                    .load(currHotel.getImg())
                    .resize(100, 80)
                    .centerCrop()
                    .into(viewHolder.hImg);
        }
        catch (Exception err)
        {
        }

        return convertView;

    }


}