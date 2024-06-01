package com.example.carrentalsystem.model;

import java.util.List;

public class Item {
    public Item(String location, String carName, String price, List<Integer> images, int totalImages) {
        this.location = location;
        this.carName = carName;
        this.price = price;
        this.images = images;
        this.totalImages = totalImages;
    }

    public Item() {
    }

    public Item(String location, String carName, String price) {
        this.location = location;
        this.carName = carName;
        this.price = price;
    }

    private String location;
    private String carName;
    private String price;
    private List<Integer> images;
    private int totalImages;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public int getTotalImages() {
        return totalImages;
    }

    public void setTotalImages(int totalImages) {
        this.totalImages = totalImages;
    }
}
