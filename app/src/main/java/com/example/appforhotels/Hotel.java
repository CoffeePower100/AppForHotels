package com.example.appforhotels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Hotel implements Parcelable {
    private String name;
    private String addr;
    private String img;
    private String price; // in dollars, for one person, per a night.

    public Hotel(String name, String addr, String img, String price) {
        this.name = name;
        this.addr = addr;
        this.img = img;
        this.price = price;
    }

    public Hotel()
    {
        this.name = "";
        this.addr = "";
        this.img = "";
        this.price = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Hotel(Parcel source) {
        name = source.readString();
        addr = source.readString();
        price = source.readString();
        img = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(addr);
        dest.writeString(price);
        dest.writeString(img);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Hotel createFromParcel(Parcel source) {
            return new Hotel(source);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };
}
