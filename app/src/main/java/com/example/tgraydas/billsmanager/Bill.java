package com.example.tgraydas.billsmanager;

import java.util.List;

public class Bill {
    int id;
    Desk desk;
    List<Product> products;
    public Bill(int id, Desk desk, List<Product> products){
        this.id = id;
        this.desk = desk;
        this.products = products;
    }

    public void PayBill(){

    }
}
