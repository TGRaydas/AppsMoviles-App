package com.example.tgraydas.billsmanager;
import java.io.Serializable;
public class Product implements Serializable{
    public int id;
    public int price;
    public String name;
    public String detail;
    public Product (int id, int price, String name, String detail){
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
    }
}
