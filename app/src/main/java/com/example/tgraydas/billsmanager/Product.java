package com.example.tgraydas.billsmanager;
import java.io.Serializable;
import java.net.URL;

public class Product implements Serializable{
    public int id;
    public int price;
    public String name;
    public String detail;
    public URL url;
    public Product (int id, int price, String name, String detail, URL url){
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.url = url;
    }
}
