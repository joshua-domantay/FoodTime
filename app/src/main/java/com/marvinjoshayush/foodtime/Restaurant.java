package com.marvinjoshayush.foodtime;

import android.view.Menu;

import java.util.ArrayList;

class MenuItem {
    private String name;
    private ArrayList<String> ingredients;

    public MenuItem(String name) {
        this.name = name;
        ingredients = new ArrayList<String>();
    }

    public void addIngredients(String[] ingredients) {
        for(String x : ingredients) {
            this.ingredients.add(x.toLowerCase());
        }
    }
}

class MenuSection {
    private String name;
    private ArrayList<MenuItem> menu;

    public MenuSection(String name) {
        this.name = name;
        menu = new ArrayList<MenuItem>();
    }

    public void addMenu(String menuName, String[] ingredients) {
        MenuItem newMenu = new MenuItem(menuName);
        newMenu.addIngredients(ingredients);
    }

    public String getName() { return name; }
}

public class Restaurant {
    private String name;
    private int banner;
    private ArrayList<MenuSection> menuSections;

    public Restaurant(String name, int banner) {
        this.name = name;
        this.banner = banner;
        menuSections = new ArrayList<MenuSection>();
    }

    public void addMenuSection(String name) {
        MenuSection newSection = new MenuSection(name);
        menuSections.add(newSection);
    }

    public void addMenuSection(String[] sections) {
        for(String section : sections) { addMenuSection(section); }
    }

    public void addMenu(int sectionNumber, String menuName, String[] ingredients) {
        menuSections.get(sectionNumber).addMenu(menuName, ingredients);
    }

    public String getName() { return name; }
    public int getBanner() { return banner; }
    public ArrayList<MenuSection> getMenuSections() { return menuSections; }
}
