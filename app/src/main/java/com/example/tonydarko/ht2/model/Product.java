package com.example.tonydarko.ht2.model;

public class Product {
    public String title;
    public int count;

    public Product(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
