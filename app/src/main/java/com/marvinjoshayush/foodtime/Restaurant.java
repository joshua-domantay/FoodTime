package com.marvinjoshayush.foodtime;

import android.view.Menu;

import java.util.ArrayList;

class MenuItem {
    private String name;
    private String nameForFile;
    private ArrayList<String> ingredients;

    public MenuItem(String name) {
        this.name = name;
        setNameForFile(name);
        ingredients = new ArrayList<>();
    }

    public void addIngredients(ArrayList<String> ingredients) {
        for(String x : ingredients) {
            this.ingredients.add(x.toLowerCase());
        }
    }

    private void setNameForFile(String x) {
        setNameForFileH(x.toLowerCase(), " ");
        setNameForFileH(nameForFile, "&");
        setNameForFileH(nameForFile, ",");
        setNameForFileH(nameForFile, "-");
        setNameForFileH(nameForFile, ":");
        setNameForFileH(nameForFile, "_");
    }

    private void setNameForFileH(String toSplit, String regex) {
        String[] x = toSplit.split(regex);
        nameForFile = "";
        for(String i : x) {
            if(!i.contains("(") && !i.contains(")") && (i.length() != 0)) {
                if(nameForFile.length() == 0) {
                    nameForFile = i;
                } else {
                    nameForFile += "_" + i;
                }
            }
        }
    }

    public String getName() { return name; }
    public String getNameForFile() { return nameForFile; }
    public ArrayList<String> getIngredients() { return ingredients; }
}

class MenuSection {
    private String name;
    private ArrayList<MenuItem> menu;

    public MenuSection(String name) {
        this.name = name;
        menu = new ArrayList<>();
    }

    public void addMenu(String menuName, ArrayList<String> ingredients) {
        MenuItem newMenu = new MenuItem(menuName);
        newMenu.addIngredients(ingredients);
        menu.add(newMenu);
    }

    public String getName() { return name; }
    public ArrayList<MenuItem> getMenu() { return menu; }
}

public class Restaurant {
    private String name;
    private String nameForFile;
    private int banner;
    private ArrayList<MenuSection> menuSections;

    public Restaurant(String name, int banner) {
        this.name = name;
        setNameForFile(name);
        this.banner = banner;
        menuSections = new ArrayList<>();
    }

    public void addMenuSection(String name) {
        MenuSection newSection = new MenuSection(name);
        menuSections.add(newSection);
    }

    public void addMenuSection(String[] sections) {
        for(String section : sections) { addMenuSection(section); }
    }

    public void addMenu(int sectionNumber, String menuName, ArrayList<String> ingredients) {
        menuSections.get(sectionNumber).addMenu(menuName, ingredients);
    }

    private void setNameForFile(String x) {
        setNameForFileH(x.toLowerCase(), " ");
        setNameForFileH(nameForFile, "&");
        setNameForFileH(nameForFile, ",");
        setNameForFileH(nameForFile, "-");
        setNameForFileH(nameForFile, ":");
        setNameForFileH(nameForFile, "_");
    }

    private void setNameForFileH(String toSplit, String regex) {
        String[] x = toSplit.split(regex);
        nameForFile = "";
        for(String i : x) {
            if(!i.contains("(") && !i.contains(")") && (i.length() != 0)) {
                if(nameForFile.length() == 0) {
                    nameForFile = i;
                } else {
                    nameForFile += "_" + i;
                }
            }
        }
    }

    public String getName() { return name; }
    public String getNameForFile() { return nameForFile; }
    public int getBanner() { return banner; }
    public ArrayList<MenuSection> getMenuSections() { return menuSections; }
}
