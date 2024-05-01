package com.example.fit2081_labtask_week2;

public class Data {
    private String id;
    private String author;
    private String desc;
    private String title;
    private String isbn;
    private float price;
    private int count;

    public Data(String id, String author, String desc, String title, String isbn, float price, int count) {
        this.id = id;
        this.author = author;
        this.desc = desc;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
