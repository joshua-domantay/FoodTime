package com.marvinjoshayush.foodtime;

public class ImageButtonInfo {
    private String name;
    private String nameForFile;
    private float price;
    private ImageButtonType type;
    public boolean selected;

    public ImageButtonInfo(String name, String nameForFile) {
        this.name = name;
        this.nameForFile = nameForFile;
        this.price = -1.0f;
        this.type = ImageButtonType.SINGLE;
    }

    public ImageButtonInfo(String name, String nameForFile, float price) {
        this(name, nameForFile);
        this.price = price;
    }

    public ImageButtonInfo(String name, String nameForFile, float price, ImageButtonType type) {
        this(name, nameForFile, price);
        this.type = type;
    }

    public String getName() { return name; }
    public String getNameForFile() { return nameForFile; }
    public float getPrice() { return price; }
    public ImageButtonType getType() { return type; }
}
