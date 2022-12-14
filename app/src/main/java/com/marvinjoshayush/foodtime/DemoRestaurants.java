package com.marvinjoshayush.foodtime;

public class DemoRestaurants {
    public int[] banners = new int[] {R.drawable.demo_salad_eggs, R.drawable.demo_milk_fruit,
            R.drawable.demo_fruit_salad, R.drawable.demo_seafoodie,
            R.drawable.demo_egg_cakes, R.drawable.demo_the_farm,
            R.drawable.demo_frutas, R.drawable.demo_tasty,
            R.drawable.demo_chicky};

    public String[] restaurants = new String[] {"Salad & Eggs", "Milk+Fruit", "FruitSalad",
            "Seafoodie", "EggCakes", "TheFarm", "Frutas", "Tasty", "Chicky"};

    public float[] distances = new float[] {5.2f, 3.1f, 0.8f, 7.8f, 12.1f, 6.3f, 5.3f, 2.2f, 9.7f};

    public String[] services = new String[] {"Delivery only", "Delivery & Pick Up", "Pick Up only",
            "Pick Up only", "Delivery & Pick Up", "Delivery & Pick Up", "Pick Up only",
            "Delivery & Pick Up", "Delivery & Pick Up"};

    public String[] menu = new String[] {"Salad\nWater\nEgg", "Fruits\nMilk", "Salad\nFruits", "Shrimps\nFish",
            "Fruits\nMilk\nEggs", "Tomato\nCorn", "Watermelon\nBanana\nApple", "Burger\nSteak", "Chicken\nSoda"};

    public String[][] contains = new String[][] {{"Eggs"}, {"Dairy Products", "Milk"}, {}, {"Seafood"},
            {"Dairy Products", "Milk", "Eggs"}, {}, {}, {"Meat"}, {"Poultry"}};
}
