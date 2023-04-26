package com.marvinjoshayush.foodtime;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientsManager {
    private HomeActivity home;
    private HashMap<String, String> ingredientCategory;

    public IngredientsManager(HomeActivity home) {
        this.home = home;
        ingredientCategory = new HashMap<>();
        // ingredientsToFirebase();
        getIngredientsFromFirebase();
    }

    private void getIngredientsFromFirebase() {
        DatabaseReference ingredientsRef = FirebaseDatabase.getInstance().getReference("Ingredients");
    }

    private void ingredientsToFirebase() {
        DatabaseReference ingredientsRef = FirebaseDatabase.getInstance().getReference("Ingredients");
        putBeef(ingredientsRef.child("preferences"));
        putDairy(ingredientsRef.child("preferences"));
        putEggs(ingredientsRef.child("preferences"));
        putGrains(ingredientsRef.child("preferences"));
        putMeatIngredients(ingredientsRef.child("preferences"));
        putPork(ingredientsRef.child("preferences"));
        putPoultry(ingredientsRef.child("preferences"));
        putSeafood(ingredientsRef.child("preferences"));

        putOnion(ingredientsRef.child("allergies"));
        putGarlic(ingredientsRef.child("allergies"));
        putPeanuts(ingredientsRef.child("allergies"));
        putTreeNuts(ingredientsRef.child("allergies"));
        putFish(ingredientsRef.child("allergies"));
        putMilk(ingredientsRef.child("allergies"));
        putShellfish(ingredientsRef.child("allergies"));
        putWheat(ingredientsRef.child("allergies"));
        putSoy(ingredientsRef.child("allergies"));
        putSesame(ingredientsRef.child("allergies"));
    }

    private void ingredientsToFirebaseH(DatabaseReference ingredientsRef, String[] ingredients, String section) {
        for(int i = 0; i < ingredients.length; i++) {
            ingredientsRef.child(section).child(i + "").setValue(ingredients[i]);
        }
    }

    private void putBeef(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"beef", "beef fat", "angus steak", "ground beef", "beef cured with water",
                "roast beef", "angus beef", "natural beef flavor", "beef broth", "dried beef extract"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "beef");
    }

    private void putDairy(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"whey", "nonfat dry milk", "milk", "evaporated milk", "monterey jack cheese",
                "cultured pasteurized milk", "cheddar cheese", "parmesan cheese", "part skim milk", "cheese cultures", "cream",
                "cheese culture", "pasteurized milk", "low-moisture", "mozzarella", "cheese", "cultured pasteurized part-skim milk",
                "part-skim milk", "cultured low-fat buttermilk", "pasteurized part skim milk", "butter blend", "butter",
                "nonfat milk", "romano cheese", "pasteurized part-skim cows milk", "non-fat dry milk", "mozzarella cheese",
                "pasteurized cows milk", "parmesan", "romano", "cows milk", "cheese blend", "whole milk",
                "pasteurized process cheddar cheese food", "whey protein concentrate", "milkfat",
                "pasteurized process white cheddar cheese", "dairy blend", "whey powder", "parmesan type",
                "pasteurized part-skim milk", "granular cheesse", "yogurt", "yogurt cultures", "low fat milk", "milk chocolate",
                "chocolate", "skim milk", "lactose", "sweetened condensed skim milk", "sweetened condensed milk", "skim milk solids",
                "sour cream powder", "cultured sour cream", "granular cheeses", "vanilla lowfat yogurt", "fat milk", "swiss cheese",
                "cultured pasteurized grade a reduced fat milk", "nonfat milk solids", "hydrolyzed milk", "cultured nonfat buttermilk",
                "buttermilk powder"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "dairy");
    }

    private void putEggs(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"egg", "liquid egg", "salted egg yolks", "eggs", "liquid eggs", "egg whites",
                "egg yolks", "liquid egg yolks", "whole egg", "enzyme modified egg yolk", "whole eggs", "egg yolk", "egg white",
                "pasteurized whole eggs"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "eggs");
    }

    private void putGrains(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"mung bean vermicelli", "durum flour", "clear vermicelli", "bleached enriched flour",
                "wheat", "sesame", "enriched bleached flour", "enriched flour", "wheat flour", "rice", "sesame seeds",
                "bleached wheat flour", "white corn flour", "enriched wheat flour", "malted barley flour", "white rice", "brown rice",
                "wheat sourdough", "cultured wheat flour", "oat bran", "oat fiber", "whole wheat flour", "grain blend",
                "seed blend", "sourdough", "fermented wheat flour", "fermented rye flour", "wheat grains", "rye grains",
                "oat grains", "flax seed", "millet seed", "teff seed", "rice bran", "germ", "rye flour", "rye sourdough",
                "whole oat groats", "wheat starch", "cracked wheat", "subway artisan italian bread", "flour", "toasted bread crumbs",
                "unbleached wheat flour", "malted wheat flour", "barley", "wheat protein", "rolled oats", "white confectionery chips",
                "bread crumbs", "toasted wheat crumbs", "crust", "mustard flour", "barley malt extract", "yellow corn flour",
                "rice starch", "whole grain rolled oats"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "grains");
    }

    private void putMeatIngredients(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"natural animal flavor", "natural beef flavor"};
        ingredientsToFirebaseH(ingredientsRef, ingredients, "meatIngredients");
    }

    private void putPork(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"pork", "pork cured with water", "ham", "sliced genoa salami", "pepperoni",
                "pork bellies", "uncured bacon topping", "genoa salami"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "pork");
    }

    private void putPoultry(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"dark meat chicken", "dehydrated chicken", "chicken fat", "chicken", "chicken thighs",
                "chicken breast", "chicken breast bites", "chicken breast with rib meat", "chicken stock", "boneless chicken breast",
                "skinless chicken breast", "rib meat", "subway grilled chicken", "turkey bologna", "mechanically separated turkey",
                "flavorings cooked turkey salami", "dark turkey", "turkey ham", "cured turkey thigh meat",
                "turkey breast", "turkey broth", "white chicken meat", "chicken stock plus", "chicken skin", "chicken breasts",
                "chicken rib meat", "boneless skinless chicken breast fillets with rib meat", "chicken breast filets"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "poultry");
    }

    private void putSeafood(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"shrimp", "flaked tuna", "tuna", "fish", "fish filet"};
        ingredientsToFirebaseH(ingredientsRef, ingredients, "seafood");
    }

    private void putOnion(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"onion", "green onion", "onions", "green onions", "onion powder", "dried onion",
                "dehydrated onion", "dehydrated green onion", "sweet onion teriyaki sauce", "dehydrated onions", "fried onions",
                "caramelized onions", "shallots", "scallions", "leeks", "chives", "red onions", "slivered onions", "dried green onion"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "onion");
    }

    private void putGarlic(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"garlic", "granulated garlic", "garlic oil", "dehydrated garlic", "dried garlic",
                "garlic powder", "garlic juice", "garlic juice concentrate"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "garlic");
    }

    private void putPeanuts(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"peanut", "roasted peanuts", "peanuts", "peanut oil"};
        ingredientsToFirebaseH(ingredientsRef, ingredients, "peanuts");
    }

    private void putTreeNuts(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"tree nuts", "macadamia nuts", "glazed walnuts", "walnuts", "sweetened coconut",
                "desiccated coconut", "coconut oil", "hydrogenated coconut oil", "coconut"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "treeNuts");
    }

    private void putFish(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"flaked tuna", "tuna", "fish", "fish filet"};
        ingredientsToFirebaseH(ingredientsRef, ingredients, "fish");
    }

    private void putMilk(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"whey", "nonfat dry milk", "milk", "evaporated milk", "monterey jack cheese",
                "cultured pasteurized milk", "cheddar cheese", "parmesan cheese", "part skim milk", "cheese cultures", "cream",
                "cheese culture", "pasteurized milk", "low-moisture", "mozzarella", "cheese", "cultured pasteurized part-skim milk",
                "part-skim milk", "cultured low-fat buttermilk", "pasteurized part skim milk", "butter blend", "butter",
                "nonfat milk", "romano cheese", "pasteurized part-skim cows milk", "non-fat dry milk", "mozzarella cheese",
                "pasteurized cows milk", "parmesan", "romano", "cows milk", "cheese blend", "whole milk",
                "pasteurized process cheddar cheese food", "whey protein concentrate", "milkfat",
                "pasteurized process white cheddar cheese", "dairy blend", "whey powder", "parmesan type",
                "pasteurized part-skim milk", "granular cheesse", "yogurt", "yogurt cultures", "low fat milk",
                "milk chocolate", "chocolate", "skim milk", "lactose", "sweetened condensed skim milk", "sweetened condensed milk",
                "skim milk solids", "sour cream powder", "cultured sour cream", "granular cheeses", "vanilla lowfat yogurt",
                "fat milk", "swiss cheese", "cultured pasteurized grade a reduced fat milk", "nonfat milk solids", "hydrolyzed milk",
                "cultured nonfat buttermilk", "buttermilk powder"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "milk");
    }

    private void putShellfish(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"shrimp", "shell parts"};
        ingredientsToFirebaseH(ingredientsRef, ingredients, "shellfish");
    }

    private void putWheat(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"durum flour", "bleached enriched flour", "wheat", "enriched bleached flour",
                "enriched flour", "wheat flour", "bleached wheat flour", "enriched wheat flour", "malted barley flour",
                "wheat sourdough", "cultured wheat flour", "whole wheat flour", "fermented wheat flour", "fermented rye flour",
                "wheat grains", "rye grains", "oat grains", "wheat starch", "subway artisan italian bread", "flour",
                "unbleached wheat flour", "malted wheat flour", "barley", "wheat protein", "rolled oats", "white confectionery chips",
                "bread crumbs", "toasted wheat crumbs", "crust", "barley malt extract"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "wheat");
    }

    private void putSoy(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"soy sauce", "soybean oil", "soy", "dehydrated soy sauce powder",
                "dehydrated soy sauce", "soybeans", "hydrolyzed soy proteins", "soy sauce powder", "soy lecithin",
                "soybean salt", "hydrolyzed soy", "soy protein concentrate", "liquid soybean oil", "hydrogenated soybean oil",
                "butter flavored soybean oil", "textured soy protein concentrate", "soy flour"
        };
        ingredientsToFirebaseH(ingredientsRef, ingredients, "soy");
    }

    private void putSesame(DatabaseReference ingredientsRef) {
        String[] ingredients = new String[]{"sesame oil", "sesame seed oil", "roasted sesame seed oil", "sesame seed"};
        ingredientsToFirebaseH(ingredientsRef, ingredients, "sesame");
    }

    public String getIngredientCategory(String ingredient) {
        return ingredient.toLowerCase();
    }
}
