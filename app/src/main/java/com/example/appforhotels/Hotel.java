package com.example.appforhotels;

public class Hotel
{
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
}
