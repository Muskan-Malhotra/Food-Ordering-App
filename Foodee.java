package com.example.zaika;

public class Foodee {
    private String Description,Menuid, Name,Price;
     private int Quantity;

    public Foodee() {
    }

    public Foodee(String name, String description, String price, String menuid, int quantity) {
        Name = name;
        Quantity = quantity;
        Description = description;
        Price = price;
        Menuid = menuid;

    }

    public String getname() {
        return Name;
    }

    public void setname(String name) {
        Name = name;
    }

    public int getquantity() {
        return Quantity;
    }

    public void setquantity(int quantity) {
        Quantity = quantity;
    }

    public String getdescription() {
        return Description;
    }

    public void setdescription(String description) {
        Description = description;
    }

    public String getprice() {
        return Price;
    }

    public void setprice(String price) {
        Price = price;
    }

    public String getmenuid() {
        return Menuid;
    }

    public void setmenuid(String menuid) {
        Menuid = menuid;
    }
}

