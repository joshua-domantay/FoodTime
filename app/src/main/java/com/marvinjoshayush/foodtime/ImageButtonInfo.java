package com.marvinjoshayush.foodtime;

import android.widget.ImageButton;

public class ImageButtonInfo {
    private ImageButton button;
    private String name;
    private String nameForFile;
    private float price;
    private ImageButtonType type;
    public int section;
    public boolean selected;

    public ImageButtonInfo(ImageButton button, String name, String nameForFile) {
        this.button = button;
        this.name = name;
        this.nameForFile = nameForFile;
        this.price = -1.0f;
        this.type = ImageButtonType.SINGLE;
    }

    public ImageButtonInfo(ImageButton button, String name, String nameForFile, int section) {
        this(button, name, nameForFile);
        this.section = section;
    }

    public ImageButtonInfo(ImageButton button, String name, String nameForFile, int section, float price) {
        this(button, name, nameForFile, section);
        this.price = price;
    }

    public ImageButtonInfo(ImageButton button, String name, String nameForFile, int section, float price, ImageButtonType type) {
        this(button, name, nameForFile, section, price);
        this.type = type;
    }

    public ImageButton getButton() { return button; }
    public String getName() { return name; }
    public String getNameForFile() { return nameForFile; }
    public float getPrice() { return price; }
    public ImageButtonType getType() { return type; }

    public void setPrice(float x) { price = x; }
}
