package com.example.skr;

public class snack {
    private String name;
    private int imageId;
    public snack(String name, int imageId){
        this.name =name;
        this.imageId =imageId;
    }
    public String getName(){return name;}
    public int getImageId(){return imageId;}
}
