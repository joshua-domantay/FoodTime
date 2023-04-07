package com.marvinjoshayush.foodtime;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantManager {
    private static ArrayList<Restaurant> restaurants;
    private HomeActivity home;

    public RestaurantManager(HomeActivity home) {
        if(restaurants == null) {
            restaurants = new ArrayList<>();
            initializeRestaurants();
        }
        this.home = home;
    }

    private void initializeRestaurants() {
        // restaurants.add(getPandaExpress());
        // restaurantToFirebase();

        DatabaseReference restaurantsFirebase = FirebaseDatabase.getInstance().getReference("Restaurants");
        getRestaurantsFromFirebase(restaurantsFirebase);
    }

    private void getRestaurantsFromFirebase(DatabaseReference pAllRestaurantsRef) {
        pAllRestaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mRestaurant : snapshot.getChildren()) {
                    Restaurant newRestaurant = new Restaurant(mRestaurant.getKey(), getRestaurantBanner(mRestaurant.getKey()));
                    getMenuSectionsFromFirebase(newRestaurant, pAllRestaurantsRef.child(mRestaurant.getKey()));
                    restaurants.add(newRestaurant);
                }
                home.setRestaurants();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private int getRestaurantBanner(String pRestaurant) {
        switch(pRestaurant) {
            case "McDonalds":
                return R.drawable.mcdonalds;
            case "Panda Express":
                return R.drawable.panda_express;
            case "Subway":
                return R.drawable.subway_logo;
            default:
                return R.drawable.demo_chicky;
        }
    }

    private void getMenuSectionsFromFirebase(Restaurant restaurantObj, DatabaseReference pRestaurantRef) {
        pRestaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sectionNum = 0;
                for(DataSnapshot mMenuSection : snapshot.getChildren()) {
                    restaurantObj.addMenuSection(mMenuSection.getKey());
                    getMenusFromFirebase(restaurantObj, pRestaurantRef.child(mMenuSection.getKey()), sectionNum);
                    sectionNum++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void getMenusFromFirebase(Restaurant restaurantObj, DatabaseReference pRestaurantRef, int pSectionNum) {
        pRestaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mMenuItem : snapshot.getChildren()) {
                    String menuItemStr = mMenuItem.getKey();
                    getIngredientsFromFirebase(pRestaurantRef.child(mMenuItem.getKey()), restaurantObj, pSectionNum, menuItemStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void getIngredientsFromFirebase(DatabaseReference pMenuItemRef, Restaurant restaurantObj, int pSectionNum, String menuItemStr) {
        pMenuItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> ingredients = new ArrayList<>();
                for(DataSnapshot child : snapshot.getChildren()) {
                    ingredients.add(child.getValue().toString());
                }
                restaurantObj.addMenu(pSectionNum, menuItemStr, ingredients);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void restaurantToFirebaseH(DatabaseReference toAdd, String section, String menuItem, String[] ingredients) {
        for(int i = 0; i < ingredients.length; i++) {
            toAdd.child(section).child(menuItem).child(i + "").setValue(ingredients[i]);
        }
    }

    private void restaurantToFirebase() {
        DatabaseReference restRef = FirebaseDatabase.getInstance().getReference("Restaurants");
        putPandaExpress(restRef.child("Panda Express"));
        putSubway(restRef.child("Subway"));
        putMcdonalds(restRef.child("McDonalds"));

        /*
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Toast.makeText(SignUpActivity.this,"User has been registered Sucessfully!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this, WelcomeActivity1.class));
                } else {
                    Toast.makeText(SignUpActivity.this,"Cannot process registration. Try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
        */
    }

    private void putPandaExpress(DatabaseReference restRef) {
        String[] ingredients = new String[]{"cabbage", "enriched wheat flour", "water", "onion", "soybean oil",
                "celery", "rice", "wheat", "caramel color", "sesame oil", "wheat gluten", "canola oil", "cottonseed oil",
                "dextrose", "sugar", "malted barley flour", "monoglycerides", "datem", "l-cysteine hydrochloride",
                "ascorbic acid", "enzyme", "modified cornstarch", "xanthan gum", "potassium carbonate", "salt",
                "sodium carbonate", "yellow 5", "yellow 6", "potassium bicarbonate", "soy sauce", "spices", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Sides", "Chow Mein", ingredients);

        ingredients = new String[]{"white rice", "liquid eggs", "peas", "carrots", "soybean oil", "green onions",
                "salt", "sesame oil", "maltodextrin", "modified food starch", "sugar", "onion powder", "celery extract",
                "disodium inosinate", "disodium guanylate", "soybeans", "wheat", "water", "soy sauce", "spices", "eggs", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Sides", "Fried Rice", ingredients);

        ingredients = new String[]{"white rice"};
        restaurantToFirebaseH(restRef, "Sides", "Steamed Rice", ingredients);

        ingredients = new String[]{"brown rice", "soybean oil", "soy"};
        restaurantToFirebaseH(restRef, "Sides", "Steamed Brown Rice", ingredients);

        ingredients = new String[]{"cabbage", "broccoli", "kale", "water", "soybean oil", "garlic", "phosphoric acid", "natural flavor",
                "cornstarch", "potato starch", "modified food starch", "salt", "maltodextrin", "disodium inosinate",
                "disodium guanylate", "dehydrated soy sauce powder", "sugar", "onion powder", "celery extract", "soy", "wheat"};
        restaurantToFirebaseH(restRef, "Sides", "Super Greens", ingredients);

        ingredients = new String[]{"beef", "water", "sugar", "red bell pepper", "onions", "modified food starch", "soybean oil",
                "distilled vinegar", "invert syrup", "wheat", "soybeans", "salt", "garlic", "phosphoric acid", "potassium sorbate",
                "sodium benzoate", "guar gum", "tomato paste", "tapioca dextrin", "rice flour", "natural flavor", "vegetable juice color",
                "beta carotene color", "wheat gluten", "wheat flour", "hydrolyzed soy proteins", "hydrolyzed corn protein", "autolyzed yeast",
                "corn syrup solids", "palm oil", "maltodextrin", "sodium bicarbonate", "sodium phosphate", "carrageenan", "potassium chloride",
                "dextrose", "xanthan gum", "beef fat", "whey", "milk", "soy"};
        restaurantToFirebaseH(restRef, "Entrees", "Beijing Beef", ingredients);

        ingredients = new String[]{"broccoli", "water", "beef", "soybean oil", "garlic", "phosphoric acid", "corn starch", "potato starch",
                "modified food starch", "soy sauce", "salt", "rice", "caramel color", "sesame oil", "sea salt", "brown sugar", "sodium phosphate",
                "natural flavor", "yeast extract", "spices", "sugar", "soy", "wheat", "sesame"};
        restaurantToFirebaseH(restRef, "Entrees", "Broccoli Beef", ingredients);

        ingredients = new String[]{"angus steak", "baby broccoli", "mushrooms", "onions", "bell pepper", "water", "soybean oil", "tomato paste",
                "miso paste", "soy sauce powder", "modified corn starch", "black pepper powder", "dehydrated garlic", "chili pepper powder",
                "yeast extract", "lactic acid", "onion powder", "caramel color", "disodium inosinate", "disodium guanylate", "xanthan gum",
                "paprika oleoresin", "sodium benzoate", "soy", "wheat"};
        restaurantToFirebaseH(restRef, "Entrees", "Black Pepper Angus Beef", ingredients);

        ingredients = new String[]{"dark meat chicken", "water", "celery", "onions", "soybean oil", "garlic", "phosphoric acid", "corn starch",
                "guar gum", "potato starch", "modified food starch", "rice", "wheat", "sesame oil", "caramel color", "salt", "spices",
                "sodium phosphates", "sugar", "soy sauce", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Entrees", "Black Pepper Chicken", ingredients);

        ingredients = new String[]{"eggplant", "firm tofu", "soybean oil", "red bell peppers", "distilled vinegar", "sugar", "water", "garlic",
                "phosphoric acid", "guar gum", "rice", "wheat", "caramel color", "salt", "modified food starch", "potato starch", "corn starch",
                "sesame oil", "soy sauce", "spices", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Entrees", "Eggplant Tofu (Regional)", ingredients);

        ingredients = new String[]{"chicken thighs", "sugar", "soy sauce", "ginger puree", "garlic", "water", "soybean oil", "black pepper",
                "roasted sesame seed oil", "soy", "wheat", "sesame"};
        restaurantToFirebaseH(restRef, "Entrees", "Grilled Chicken", ingredients);

        ingredients = new String[]{"chicken breast strips", "green beans", "yellow bell pepper", "water", "distilled vinegar", "modified food starch",
                "corn starch", "wheat", "caramel color", "ginger", "garlic", "phosphoric acid", "salt", "sodium phosphate", "sesame oil",
                "sesame seeds", "wheat flour", "spices", "sugar", "organic", "honey"};
        restaurantToFirebaseH(restRef, "Entrees", "Honey Sesame Chicken Breast", ingredients);

        ingredients = new String[]{"shrimp", "modified food starch", "soybean oil", "rice flour", "glazed walnuts", "water", "sugar", "potato dextrin",
                "bleached wheat flour", "evaporated milk", "white corn flour", "salt", "distilled vinegar", "sodium tripolyphosphate",
                "sodium aluminum phosphate", "sodium bicarbonate", "guar gum", "honey", "salted egg yolks", "wheat gluten", "yeast", "malic acid",
                "xanthan gum", "potassium sorbate", "sodium benzoate", "natural flavors", "spices", "dried garlic", "paprika", "annatto extract",
                "oleoresin turmeric", "tree nuts", "shellfish", "eggs", "milk", "soy", "wheat"};
        restaurantToFirebaseH(restRef, "Entrees", "Honey Walnut Shrimp", ingredients);

        ingredients = new String[]{"dark meat chicken", "zucchini", "water", "red bell peppers", "roasted peanuts", "soybean oil", "green onions",
                "garlic", "corn starch", "phosphoric acid", "guar gum", "potato starch", "modified food starch", "wheat", "salt", "caramel color",
                "rice", "sesame oil", "sodium phosphate", "sugar", "soy sauce", "spices", "peanuts", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Entrees", "Kung Pao Chicken", ingredients);

        ingredients = new String[]{"dark meat chicken", "mushrooms", "zucchini", "water", "soybean oil", "dehydrated garlic", "phosphoric acid",
                "guar gum", "corn starch", "sugar", "soy sauce", "rice", "modified food starch", "sesame oil", "sodium phosphate", "sea salt",
                "salt", "brown sugar", "caramel flavor", "natural flavor", "yeast extract", "spices", "soy", "wheat", "sesame"};
        restaurantToFirebaseH(restRef, "Entrees", "Mushroom Chicken", ingredients);

        ingredients = new String[]{"dark meat chicken", "water", "sugar", "distilled vinegar", "modified food starch", "corn starch", "potato starch",
                "wheat", "orange extract", "caramel color", "salt", "garlic", "phosphoric acid", "rice", "sesame oil", "wheat flour", "eggs", "spices",
                "leavening", "soy sauce", "milk", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Entrees", "Original Orange Chicken", ingredients);


        ingredients = new String[]{"green beans", "chicken breast", "water", "onion", "soybean oil", "dehydrated garlic",
                "phosphoric acid", "guar gum", "corn starch", "sodium phosphate", "sugar", "soy sauce", "salt", "potato starch", "modified food starch",
                "rice", "wheat", "caramel color", "sesame oil", "sea salt", "brown sugar", "natural flavor", "yeast extract", "spices", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Entrees", "String Bean Chicken Breast", ingredients);

        ingredients = new String[]{"chicken breast bites", "pineapple chunks", "water", "sugar", "red bell peppers", "onions", "bleached wheat flour",
                "corn starch", "soybean oil", "red jalapenos", "modified food starch", "distilled vinegar", "carrot puree", "salt", "wheat flour",
                "natural flavor", "dehydrated garlic", "sodium phosphate", "dried onion", "spices", "sodium bicarbonate", "wheat"};
        restaurantToFirebaseH(restRef, "Entrees", "Sweet Fire Chicken Breast (Regional)", ingredients);

        ingredients = new String[]{"bleached enriched flour", "dark meat chicken", "water", "cabbage", "onion", "napa cabbage", "carrot", "green onion",
                "mung bean vermicelli", "durum flour", "clear vermicelli", "vegetable oil", "soy sauce", "wine", "salt", "natural flavor", "dehydrated chicken",
                "chicken fat", "sugar", "whey", "maltodextrin", "nonfat dry milk", "disodium inosinate", "disodium guanylate", "sauterne wine", "chicken",
                "concentrated juices", "yeast extract", "sesame oil", "garlic", "carrageenan", "locust bean gum", "dextrose", "cottonseed oil", "soybean oil",
                "guar gum", "egg", "corn starch", "palm oil", "wheat gluten", "sodium stearoyl lactylate", "citric acid", "modified corn starch", "milk",
                "soy", "wheat", "sesame"};
        restaurantToFirebaseH(restRef, "Appetizers", "Chicken Eggroll", ingredients);

        ingredients = new String[]{"chicken", "cabbage", "onions", "sugar", "salt", "granulated garlic", "sesame seed oil", "soy sauce", "soybean oil",
                "modified corn starch", "ginger", "yeast extract", "chives", "enriched bleached flour", "water", "vegetable oil", "corn starch",
                "soy", "wheat", "sesame"};
        restaurantToFirebaseH(restRef, "Appetizers", "Chicken Potsticker (Regional)", ingredients);

        ingredients = new String[]{"cream cheese", "green onions", "enriched flour", "water", "egg", "salt", "mono", "diglycerides", "enzyme", "annatto",
                "turmeric", "corn starch", "milk", "wheat"};
        restaurantToFirebaseH(restRef, "Appetizers", "Cream Cheese Rangoon", ingredients);

        ingredients = new String[]{"water", "firm tofu", "mushrooms", "liquid egg", "distilled vinegar", "modified food starch", "corn starch",
                "potato starch", "salt", "maltodextrin", "shortening powder", "disodium inosinate", "disodium guanylate",
                "dehydrated soy sauce powder", "sugar", "onion powder", "soybean oil", "celery extract", "sesame oil", "soy sauce", "spices",
                "egg", "soy", "wheat", "sesame"};
        restaurantToFirebaseH(restRef, "Appetizers", "Hot and Sour Soup", ingredients);

        ingredients = new String[]{"cabbage", "wheat flour", "celery", "carrots", "water", "mung bean vermicelli", "green onions", "modified corn starch",
                "ginger", "garlic oil", "sesame oil", "salt", "sugar", "dehydrated soy sauce", "onion powder", "natural stir-fry flavor", "spices",
                "disodium inosinate", "disodium guanylate", "soybean oil", "sodium polyphosphate", "sodium carbonate", "monoglycerides", "diglycerides",
                "polysorbitan esters of stearates", "lecithin", "citric acid", "soy", "wheat", "sesame"};
        restaurantToFirebaseH(restRef, "Appetizers", "Vegetable Spring Roll", ingredients);

        ingredients = new String[]{
                "white rice", "cabbage", "broccoli", "kale", "water", "soybean oil", "garlic", "phosphoric acid", "natural flavor", "cornstarch", "potato starch",
                "modified food starch", "salt", "maltodextrin", "disodium inosinate", "disodium guanylate", "dehydrated soy sauce powder", "sugar", "onion powder",
                "celery extract", "soy", "wheat", "dark meat chicken", "distilled vinegar", "corn starch", "orange extract", "caramel color", "rice", "sesame oil",
                "wheat flour", "eggs", "spices", "leavening", "soy sauce", "milk", "sesame"
        };
        restaurantToFirebaseH(restRef, "Cub Meals", "Orange Chicken Cub Meal", ingredients);

        ingredients = new String[]{
                "cabbage", "enriched wheat flour", "water", "onion", "soybean oil", "celery", "rice", "wheat", "caramel color", "sesame oil", "wheat gluten",
                "canola oil", "cottonseed oil", "dextrose", "sugar", "malted barley flour", "monoglycerides", "datem", "l-cysteine hydrochloride", "ascorbic acid",
                "enzyme", "modified cornstarch", "xanthan gum", "potassium carbonate", "salt", "sodium carbonate", "yellow 5", "yellow 6", "potassium bicarbonate",
                "soy sauce", "spices", "soy", "sesame", "broccoli", "kale", "garlic", "phosphoric acid", "natural flavor", "cornstarch", "potato starch",
                "modified food starch", "maltodextrin", "disodium inosinate", "disodium guanylate", "dehydrated soy sauce powder", "onion powder", "celery extract",
                "chicken thighs", "ginger puree", "black pepper", "roasted sesame seed oil"
        };
        restaurantToFirebaseH(restRef, "Cub Meals", "Grilled Teriyaki Cub Meal", ingredients);

        ingredients = new String[]{
                "white rice", "cabbage", "broccoli", "kale", "water", "soybean oil", "garlic", "phosphoric acid", "natural flavor", "cornstarch", "potato starch",
                "modified food starch", "salt", "maltodextrin", "disodium inosinate", "disodium guanylate", "dehydrated soy sauce powder", "sugar", "onion powder",
                "celery extract", "soy", "wheat", "beef", "corn starch", "soy sauce", "rice", "caramel color", "sesame oil", "sea salt", "brown sugar", "sodium phosphate",
                "yeast extract", "spices", "sesame"
        };
        restaurantToFirebaseH(restRef, "Cub Meals", "Broccoli Beef Cub Meal", ingredients);

        // DRINKS (Could be used for other restaurants)
        ingredients = new String[]{
                "carbonated water", "high fructose corn syrup", "caramel color", "phosphoric acid", "natural flavors", "caffeine"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Coca Cola", ingredients);

        ingredients = new String[]{
                "carbonated water", "caramel color", "aspartame", "phosphoric acid", "potassium benzoate", "natural flavors", "citric acid", "caffeine"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Diet Coke", ingredients);

        ingredients = new String[]{
                "carbonated water", "high fructose corn syrup", "caramel color", "sodium benzoate", "citric acid", "caffeine", "artificial and natural flavors", "acacia"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Barqs Root Beer", ingredients);

        ingredients = new String[]{
                "carbonated water", "high fructose corn syrup", "citric acid", "natural flavors", "sodium benzoate", "modified food starch", "glycerol ester of rosin", "yellow 6", "red 40"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Fanta Orange", ingredients);

        ingredients = new String[]{
                "pure filtered water", "lemon juice from concentrate", "high fructose corn syrup", "lemon pulp", "natural flavors", "pectin", "sucralose", "acesulfame potassium"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Minute Maid Lemonade", ingredients);

        ingredients = new String[]{
                "carbonated water", "citric acid", "tartaric acid", "acidity regulator", "sodium citrates", "aspartame", "acesulfame-k", "sucralose", "natural lemon", "lime flavourings"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Sprite", ingredients);

        ingredients = new String[]{
                "carbonated water", "high fructose corn syrup", "caramel color", "phosphoric acid", "natural flavors", "caffeine", "sodium"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Coca Cola Cherry", ingredients);

        ingredients = new String[]{
                "carbonated water", "high fructose corn syrup", "natural flavors", "citric acid", "sodium benzoate", "red 40"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Fanta Strawberry", ingredients);

        ingredients = new String[]{
                "purified water", "magnesium sulfate", "potassium chloride", "salt", "sodium", "minerals"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Dasani", ingredients);

        ingredients = new String[]{
                "water", "high fructose corn syrup", "citric acid", "electrolytes", "salt", "sodium citrate", "magnesium", "calcium chlorides", "mono-potassium phosphate",
                "vitamins b12", "vitamin c", "cyanocobalamin", "ascorbic acid", "natural flavors", "modified food starch", "calcium disodium edta", "medium chain triglycerides", "sucrose acetate isobutrate", "blue 1"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Powerade Berry Blast", ingredients);

        ingredients = new String[]{
                "pure filtered water", "concentrated apple juice", "vitamin c", "ascorbic acid"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Minute Maid Apple Juice", ingredients);

        ingredients = new String[]{
                "carbonated water", "citric acid", "tartaric acid", "acidity regulator", "sodium citrates", "aspartame", "acesulfame-k", "sucralose", "natural lemon", "lime flavourings"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Sprite Bottle", ingredients);

        ingredients = new String[]{
                "spring water", "electrolytes", "calcium chloride", "magnesium chloride", "potassium bicarbonate"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Smart Water", ingredients);

        ingredients = new String[]{
                "carbonated water", "lychee puree", "citric acid"
        };
        restaurantToFirebaseH(restRef, "Drinks", "Sanzo Lychee Sparkling Water", ingredients);
    }

    private void putSubway(DatabaseReference restRef) {
        String[] ingredients = new String[]{"enriched flour", "wheat flour", "malted barley flour", "niacin", "iron", "thiamine mononitrate", "riboflavin",
                "folic acid", "water", "yeast", "sugar", "wheat gluten", "wheat sourdough", "cultured wheat flour", "natural flavor", "salt", "palm oil",
                "soy lecithin", "enzymes", "ascorbic acid", "soybean oil", "wheat", "soy"};
        restaurantToFirebaseH(restRef, "Breads", "ARTISAN ITALIAN (WHITE) BREAD", ingredients);

        ingredients = new String[]{"enriched flour", "wheat flour", "niacin", "reduced iron", "thiamine mononitrate", "riboflavin", "folic acid", "water",
                "soybean oil", "yeast", "sugar", "salt", "cultured wheat flour", "vinegar", "oat bran", "oat fiber", "enzymes", "wheat", "milk"};
        restaurantToFirebaseH(restRef, "Breads", "FLATBREAD, ARTISAN", ingredients);

        ingredients = new String[]{"water", "whole wheat flour", "enriched wheat flour", "wheat flour", "niacin", "reduced iron", "thiamine mononitrate",
                "riboflavin", "folic acid", "grain blend", "seed blend", "sourdough", "fermented wheat flour", "fermented rye flour", "wheat grains", "rye grains",
                "oat grains", "flax seed", "millet seed", "teff seed", "salt", "sugar", "wheat gluten", "yeast", "natural flavors", "oat bran", "oat fiber",
                "cultured wheat flour", "enzymes", "milk", "wheat"};
        restaurantToFirebaseH(restRef, "Breads", "FLATBREAD, MULTIGRAIN", ingredients);

        ingredients = new String[]{"egg whites", "cornstarch", "modified cornstarch", "tapioca starch", "palm oil", "sugar", "distilled monoglycerides", "yeast",
                "rice bran", "germ", "salt", "natural flavor", "leavening", "sodium acid pyrophosphate", "baking soda", "monocalcium phosphate", "pectin", "xanthan gum",
                "carbohydrate gum", "guar gum", "enzyme", "eggs"};
        restaurantToFirebaseH(restRef, "Breads", "GLUTEN-FREE BREAD (as packaged)", ingredients);

        ingredients = new String[]{"enriched flour", "wheat flour", "malted barley flour", "niacin", "iron", "thiamine mononitrate", "riboflavin", "folic acid",
                "water", "whole wheat flour", "yeast", "wheat gluten", "sugar", "rye flour", "rye sourdough", "fermented rye flour", "salt", "soybean oil", "brown sugar",
                "whole oat groats", "sunflower seeds", "millet seed", "flax seed", "dried molasses", "wheat starch", "soy lecithin", "cracked wheat", "ascorbic acid",
                "enzymes", "wheat", "soy"};
        restaurantToFirebaseH(restRef, "Breads", "HEARTY MULTIGRAIN", ingredients);

        ingredients = new String[]{"subway artisan italian bread", "enriched flour", "wheat flour", "malted barley flour", "niacin", "iron", "thiamine mononitrate",
                "riboflavin", "folic acid", "water", "yeast", "sugar", "wheat gluten", "wheat sourdough", "cultured wheat flour", "natural flavor", "salt", "palm oil",
                "soy lecithin", "enzymes", "ascorbic acid", "soybean oil", "monterey jack cheese", "cultured pasteurized milk", "cheddar cheese", "annatto color",
                "potato starch", "powdered cellulose", "natamycin", "parmesan-oregano topping", "corn maltodextrin", "enriched wheat flour", "flour", "reduced iron",
                "toasted bread crumbs", "sea salt", "spices", "dehydrated garlic", "parmesan cheese", "part skim milk", "cheese cultures", "modified corn starch",
                "sunflower oil", "citric acid", "yeast extract", "lactic acid", "silicon dioxide", "milk", "soy", "wheat"};
        restaurantToFirebaseH(restRef, "Breads", "ITALIAN HERBS & CHEESE BREAD", ingredients);

        ingredients = new String[]{"subway artisan italian bread", "enriched flour", "wheat flour", "malted barley flour", "niacin", "iron", "thiamine mononitrate",
                "riboflavin", "folic acid", "water", "yeast", "sugar", "wheat gluten", "wheat sourdough", "cultured wheat flour", "natural flavor", "salt", "palm oil",
                "soy lecithin", "enzymes", "ascorbic acid", "soybean oil", "monterey jack cheese", "cultured pasteurized milk", "cheddar cheese", "annatto color",
                "potato starch powdered cellulose", "natamycin", "jalapenos", "jalapeno peppers", "distilled vinegar", "natural flavors", "sodium benzoate", "calcium chloride",
                "sodium metabisulfite", "milk", "wheat", "soy", "sulfites"};
        restaurantToFirebaseH(restRef, "Breads", "JALAPENO CHEDDAR BREAD", ingredients);

        ingredients = new String[]{"subway artisan italian bread", "enriched flour", "wheat flour", "malted barley flour", "niacin", "iron", "thiamine mononitrate",
                "riboflavin", "folic acid", "water", "yeast", "sugar", "wheat gluten", "wheat sourdough", "cultured wheat flour", "natural flavor", "salt", "palm oil",
                "soy lecithin", "enzymes", "ascorbic acid", "soybean oil", "monterey jack cheese", "cultured pasteurized milk", "cheddar cheese", "annatto color",
                "potato starch", "powdered cellulose", "natamycin", "soy", "milk", "wheat"};
        restaurantToFirebaseH(restRef, "Breads", "MONTEREY CHEDDAR BREAD", ingredients);

        ingredients = new String[]{"subway artisan italian bread", "enriched flour", "wheat flour", "malted barley flour", "niacin", "iron", "thiamine mononitrate",
                "riboflavin", "folic acid", "water", "yeast", "sugar", "wheat gluten", "wheat sourdough", "cultured wheat flour", "natural flavor", "salt", "palm oil",
                "soy lecithin", "enzymes", "ascorbic acid", "soybean oil", "parmesan-oregano topping", "corn maltodextrin", "enriched wheat flour", "flour", "reduced iron",
                "toasted bread crumbs", "sea salt", "spices", "dehydrated garlic", "parmesan cheese", "part skim milk", "cheese cultures", "modified corn starch",
                "sunflower oil", "citric acid", "yeast extract", "lactic acid", "silicon dioxide", "soy", "milk", "wheat"};
        restaurantToFirebaseH(restRef, "Breads", "PARMESAN OREGANO BREAD", ingredients);

        ingredients = new String[]{"enriched flour", "wheat flour", "niacin", "iron", "thiamine mononitrate", "riboflavin", "folic acid", "water", "yeast", "wheat gluten",
                "sugar", "fermented wheat flour", "salt", "soybean oil", "fumaric acid", "soy lecithin", "lactic acid", "enzymes", "ascorbic acid", "wheat", "soy"};
        restaurantToFirebaseH(restRef, "Breads", "SOURDOUGH", ingredients);

        ingredients = new String[]{"unbleached wheat flour", "wheat flour", "niacin", "iron", "thiamine mononitrate", "riboflavin", "folic acid", "water", "malted wheat flour",
                "wheat gluten", "cultured wheat flour", "palm oil", "sodium acid pyrophosphate", "sodium bicarbonate", "corn starch", "monocalcium phosphate", "yeast",
                "spinach powder", "dextrose", "sugar", "salt", "dehydrated parsley", "dehydrated garlic", "dehydrated onion", "soy lecithin", "citric acid", "enzymes",
                "wheat", "soy", "barley"};
        restaurantToFirebaseH(restRef, "Breads", "WRAP, SPINACH", ingredients);

        ingredients = new String[]{"unbleached wheat flour", "wheat flour", "niacin", "iron", "thiamine mononitrate", "riboflavin", "folic acid", "water", "malted wheat flour",
                "wheat gluten", "cultured wheat flour", "palm oil", "sodium acid pyrophosphate", "sodium bicarbonate", "corn starch", "monocalcium phosphate", "yeast",
                "tomato", "basil", "salt", "sugar", "dehydrated onion", "dehydrated garlic", "bell pepper", "paprika", "dextrose", "soy lecithin", "citric acid", "enzymes",
                "wheat", "soy", "barley"};
        restaurantToFirebaseH(restRef, "Breads", "WRAP, TOMATO BASIL", ingredients);

        ingredients = new String[]{"unbleached wheat flour", "wheat flour", "niacin", "reduced iron", "thiamine mononitrate", "riboflavin", "folic acid", "water",
                "non gmo canola oil", "cultured wheat flour", "malted wheat flour", "wheat gluten", "salt", "sugar", "sodium acid pyrophosphate", "sodium bicarbonate",
                "corn starch", "monocalcium phosphate", "guar gum", "inactive dry yeast", "soy lecithin", "citric acid", "enzymes", "wheat", "soy", "barley"};
        restaurantToFirebaseH(restRef, "Breads", "WRAP", ingredients);

        ingredients = new String[]{"pork", "water", "salt", "sugar", "sodium erythorbate", "sodium nitrite"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "BACON", ingredients);

        ingredients = new String[]{"pork cured with water", "dextrose", "salt", "spices", "paprika", "vinegar", "sodium phosphate", "pepper", "sodium erythorbate",
                "sodium nitrite"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "CAPICOLA", ingredients);

        ingredients = new String[]{"chicken breast with rib meat", "water", "chicken flavor", "sea salt", "sugar", "chicken stock", "salt", "flavors", "canola oil",
                "onion powder", "garlic powder", "spice", "chicken fat", "honey", "potato starch", "sodium phosphate", "dextrose", "carrageenan"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "CHICKEN PATTY, OVEN ROASTED", ingredients);

        ingredients = new String[]{"boneless chicken breast", "skinless chicken breast", "rib meat", "water", "soy protein concentrate", "modified potato starch",
                "sodium phosphate", "potassium chloride", "salt", "maltodextrin", "yeast extract", "flavors", "natural flavors", "dextrose", "caramelized sugar",
                "paprika", "vinegar solids", "paprika extract", "chicken broth", "soy"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "CHICKEN, GRILLED", ingredients);

        ingredients = new String[]{"subway grilled chicken", "boneless chicken breast", "skinless chicken breast", "rib meat", "water", "soy protein concentrate",
                "modified potato starch", "sodium phosphate", "potassium chloride", "salt", "maltodextrin", "yeast extract", "flavors", "natural flavors", "dextrose",
                "caramelized sugar", "paprika", "vinegar solids", "paprika extract", "chicken broth", "sweet onion teriyaki sauce", "sugar", "corn vinegar", "corn syrup",
                "soy sauce", "wheat", "soybean salt", "sodium benzoate", "food starch-modified", "rice vinegar", "dehydrated onion", "tomato paste", "spices", "mustard seed",
                "distilled vinegar", "garlic", "sesame oil", "poppyseed", "sesame seed", "potassium sorbate", "dehydrated green onion", "dehydrated red bell pepper",
                "dehydrated garlic", "autolyzed yeast extract", "citric acid", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "CHICKEN, GRILLED (Sweet Onion Teriyaki Glazed)", ingredients);

        ingredients = new String[]{"turkey bologna", "mechanically separated turkey", "water", "salt", "corn syrup solids", "potassium lactate", "dextrose",
                "sodium diacetate", "sodium erythorbate", "sodium nitrite", "flavorings cooked turkey salami", "smoke flavor added", "dark turkey", "sugar",
                "sodium tripolyphosphate", "spice", "flavorings", "smoke flavor", "sodium nitrite", "turkey ham", "cured turkey thigh meat", "brown sugar"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "COLD CUT COMBO", ingredients);

        ingredients = new String[]{"egg whites", "egg yolks", "soybean oil", "water", "unmodified corn starch", "liquid butter alternative", "liquid soybean oil",
                "hydrogenated soybean oil", "salt", "soy lecithin", "natural flavor", "tocopherols", "black pepper", "xanthan gum", "cellulose gum", "citric acid", "eggs", "soy"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "EGG OMELET PATTY (Regular)", ingredients);

        ingredients = new String[]{"egg whites", "water", "unmodified corn starch", "soybean oil", "butter flavored soybean oil", "natural flavor", "salt", "black pepper",
                "xanthan gum", "white pepper", "eggs"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "EGG WHITE OMELET PATTY", ingredients);

        ingredients = new String[]{"egg whites", "egg yolks", "soybean oil", "water", "unmodified corn starch", "liquid butter alternative", "liquid soybean oil",
                "hydrogenated soybean oil", "salt", "soy lecithin", "natural flavor", "tocopherols", "black pepper", "xanthan gum", "cellulose gum", "citric acid", "egg", "soy"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "EGGS, CAGE-FREE", ingredients);

        ingredients = new String[]{"egg whites", "water", "unmodified corn starch", "soybean oil", "butter flavored soybean oil", "natural flavor", "salt", "black pepper",
                "xanthan gum", "white pepper", "egg"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "EGG WHITES, CAGE-FREE", ingredients);

        ingredients = new String[]{"ham", "water", "dextrose", "modified food starch", "salt", "vinegar", "sodium phosphates", "natural smoke flavor", "sodium erythorbate",
                "sodium nitrite"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "HAM (Black Forest)", ingredients);

        ingredients = new String[]{"sliced genoa salami", "pork", "beef", "salt", "corn syrup", "dextrose", "sugar", "flavorings", "wine", "sodium erythorbate",
                "sodium nitrate", "spices", "garlic", "lactic acid starter culture", "sodium nitrite", "pepperoni", "water", "paprika", "oleoresin of paprika", "ham",
                "modified food starch", "vinegar", "sodium phosphates", "natural smoke flavor"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "ITALIAN BMT MEATS", ingredients);

        ingredients = new String[]{"beef", "water", "bread crumbs", "toasted wheat crumbs", "enriched wheat flour", "wheat flour", "niacin", "reduced iron",
                "thiamine mononitrate", "riboflavin", "folic acid", "sugar", "salt", "soybean oil", "yeast", "textured soy protein concentrate", "seasoning",
                "dehydrated onion", "garlic", "spice", "dehydrated parsley", "soy protein concentrate", "romano cheese", "pasteurized part-skim cows milk",
                "cheese cultures", "enzymes", "marinara sauce", "tomato puree", "tomato paste", "diced tomatoes", "tomatoes", "tomato juice", "citric acid",
                "calcium chloride", "seasoning blend", "modified corn starch", "onion powder", "herbs", "spices", "milk", "soy", "wheat"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "MEATBALLS & MARINARA", ingredients);

        ingredients = new String[]{"beef cured withwater", "salt", "sugar", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "flavorings", "oil", "onion",
                "garlic", "black pepper", "dextrose", "coriander", "allspice", "paprika"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "PASTRAMI", ingredients);

        ingredients = new String[]{"pork", "beef", "salt", "water", "spices", "dextrose", "corn syrup", "flavorings", "paprika", "oleoresin", "sodium erythorbate",
                "lactic acid starter culture", "sodium nitrite"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "PEPPERONI", ingredients);

        ingredients = new String[]{"roast beef", "water", "salt", "dextrose", "corn syrup solids", "sodium phosphates", "natural flavors", "potato maltodextrin",
                "cooked sugar", "sugar"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "ROAST BEEF", ingredients);

        ingredients = new String[]{"chicken", "water", "salt", "soybean oil", "dextrose", "seasoning", "flavoring", "acacia gum", "onion powder", "sodium phosphate",
                "spice extractives", "sunflower oil"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "ROTISSERIE-STYLE CHICKEN", ingredients);

        ingredients = new String[]{"beef", "water", "salt", "modified corn starch", "dextrose", "sodium phosphate", "dried tomato", "yeast extract", "caramelized sugar",
                "onion powder", "garlic powder", "natural beef type flavor", "natural flavor", "autolyzed yeast", "disodium inosinate", "disodium guanylate", "ascorbic acid",
                "beet juice red", "corn maltodextrin", "citric acid", "hydrolyzed corn protein", "flavoring", "potato maltodextrin", "red cabbage concentrate", "turmeric",
                "soybean oil"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "STEAK", ingredients);

        ingredients = new String[]{"flaked tuna", "brine", "tuna", "water", "salt", "mayonnaise", "soybean oil", "eggs", "distilled vinegar", "sugar", "spice",
                "lemon juice concentrate", "calcium disodium", "edta", "fish"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "TUNA SALAD", ingredients);

        ingredients = new String[]{"turkey breast", "turkey broth", "seasoning", "vinegar", "cultured dextrose", "xanthan gum", "salt", "dextrose", "sodium phosphates",
                "natural flavor", "soybean oil"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "TURKEY BREAST, OVEN ROASTED", ingredients);

        ingredients = new String[]{"carrots", "onions", "green beans", "oat bran", "expeller pressed canola oil", "zucchini", "soybeans", "peas", "broccoli",
                "corn", "soy flour", "spinach", "red bell peppers", "arrowroot powder", "garlic", "corn starch", "corn meal", "salt", "methyl cellulose", "parsley",
                "black pepper", "soy"};
        restaurantToFirebaseH(restRef, "Meat, Poultry, Seafood & Eggs", "VEGGIE PATTY", ingredients);

        ingredients = new String[]{"milk", "water", "cream", "sodium citrate", "salt", "cheese culture", "sorbic acid preservative", "citric acid", "enzymes",
                "soy lecithin", "soy"};
        restaurantToFirebaseH(restRef, "Cheese", "AMERICAN CHEESE", ingredients);

        ingredients = new String[]{"pasteurized milk", "vinegar", "enzymes", "salt", "milk"};
        restaurantToFirebaseH(restRef, "Cheese", "BelGioioso FRESH MOZZARELLA", ingredients);

        ingredients = new String[]{"monterey jack cheese", "cultured pasteurized milk", "salt", "enzymes", "cheddar cheese", "annatto color", "potato starch",
                "powdered cellulose", "natamycin", "milk"};
        restaurantToFirebaseH(restRef, "Cheese", "MONTEREY & CHEDDAR CHEESE BLEND (shredded)", ingredients);

        ingredients = new String[]{"low-moisture", "mozzarella", "cheese", "cultured pasteurized milk", "salt", "enzymes", "potato starch", "powdered cellulose",
                "natamycin", "milk"};
        restaurantToFirebaseH(restRef, "Cheese", "MOZZARELLA, SHREDDED", ingredients);

        ingredients = new String[]{"parmesan cheese", "cultured pasteurized part-skim milk", "salt", "enzymes", "powdered cellulose", "potassium sorbate", "milk"};
        restaurantToFirebaseH(restRef, "Cheese", "PARMESAN CHEESE", ingredients);

        ingredients = new String[]{"milk", "cream", "water", "sodium citrate", "salt", "cheese culture", "jalapeno peppers", "jalapeno pepper puree",
                "sorbic acid", "enzymes", "red bell peppers", "citric acid", "soy lecithin", "natural flavor", "soy"};
        restaurantToFirebaseH(restRef, "Cheese", "PEPPERJACK CHEESE", ingredients);

        ingredients = new String[]{"cultured pasteurized milk", "salt", "enzymes", "milk"};
        restaurantToFirebaseH(restRef, "Cheese", "PROVOLONE CHEESE", ingredients);

        ingredients = new String[]{"part-skim milk", "cheese culture", "salt", "enzymes", "milk"};
        restaurantToFirebaseH(restRef, "Cheese", "SWISS CHEESE (Sliced)", ingredients);

        ingredients = new String[]{"banana peppers", "water", "distilled vinegar", "salt", "calcium chloride", "sodium benzoate", "sodium metabisulfite",
                "polysorbate 80", "turmeric", "natural flavors", "sulfites"};
        restaurantToFirebaseH(restRef, "Vegetables", "BANANA PEPPERS", ingredients);

        ingredients = new String[]{"ripe olives", "water", "salt", "ferrous gluconate"};
        restaurantToFirebaseH(restRef, "Vegetables", "BLACK OLIVES", ingredients);

        ingredients = new String[]{"ripe olives", "water", "salt", "ferrous gluconate"};
        restaurantToFirebaseH(restRef, "Vegetables", "BLACK OLIVES", ingredients);

        ingredients = new String[]{"ripe olives", "water", "salt", "ferrous gluconate"};
        restaurantToFirebaseH(restRef, "Vegetables", "BLACK OLIVES", ingredients);

        ingredients = new String[]{"ripe olives", "water", "salt", "ferrous gluconate"};
        restaurantToFirebaseH(restRef, "Vegetables", "BLACK OLIVES", ingredients);

        ingredients = new String[]{"cucumber"};
        restaurantToFirebaseH(restRef, "Vegetables", "CUCUMBERS", ingredients);

        ingredients = new String[]{"green bell peppers"};
        restaurantToFirebaseH(restRef, "Vegetables", "GREEN PEPPERS", ingredients);

        ingredients = new String[]{"jalapeno peppers", "water", "distilled vinegar", "salt", "natural flavors", "sodium benzoate",
                "calcium chloride", "sodium metabisulfite", "sulfites"};
        restaurantToFirebaseH(restRef, "Vegetables", "JALAPENO PEPPER SLICES", ingredients);

        ingredients = new String[]{"lettuce"};
        restaurantToFirebaseH(restRef, "Vegetables", "LETTUCE", ingredients);

        ingredients = new String[]{"red onions"};
        restaurantToFirebaseH(restRef, "Vegetables", "ONIONS", ingredients);

        ingredients = new String[]{"cucumbers", "water", "distilled vinegar", "salt", "calcium chloride", "sodium benzoate",
                "alum", "natural flavors", "polysorbate 80", "turmeric"};
        restaurantToFirebaseH(restRef, "Vegetables", "PICKLES", ingredients);

        ingredients = new String[]{"cucumbers", "water", "distilled vinegar", "salt", "sodium benzoate", "alum", "natural flavors", "polysorbate 80", "turmeric"};
        restaurantToFirebaseH(restRef, "Vegetables", "PICKLES, CRINKLE", ingredients);

        ingredients = new String[]{"hass avocados"};
        restaurantToFirebaseH(restRef, "Vegetables", "SLICED AVOCADO", ingredients);

        ingredients = new String[]{"hass avocados", "sea salt"};
        restaurantToFirebaseH(restRef, "Vegetables", "SMASHED AVOCADO", ingredients);

        ingredients = new String[]{"fresh baby spinach"};
        restaurantToFirebaseH(restRef, "Vegetables", "SPINACH", ingredients);

        ingredients = new String[]{"red tomatoes"};
        restaurantToFirebaseH(restRef, "Vegetables", "TOMATOES", ingredients);

        ingredients = new String[]{"soybean oil", "water", "cultured low-fat buttermilk", "chipotle pepper sauce concentrate", "chipotle pepper pure", "chipotle pepper",
                "distilled vinegar", "sugar", "salt", "onion powder", "egg yolks", "guajillo pepper", "natural flavors", "paprika", "spices", "dehydrated garlic", "xanthan gum",
                "lime juice concentrate", "lactic acid", "dehydrated onion", "sodium benzoate", "potassium sorbate", "natural smoke flavor", "dehydrated parsley", "citric acid",
                "calcium disodium", "edta", "milk", "egg"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "BAJA CHIPOTLE SOUTHWEST SAUCE", ingredients);

        ingredients = new String[]{"sugar", "tomato paste", "distilled vinegar", "water", "corn syrup", "molasses", "salt", "pineapple juice concentrate", "mustard bran",
                "spices", "natural flavor", "including hickory smoke", "celery seed", "onion powder", "sodium benzoate", "garlic powder", "paprika oleoresin"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "BARBEQUE SAUCE", ingredients);

        ingredients = new String[]{"distilled vinegar", "cayenne red peppers", "salt", "water", "modified corn starch", "canola oil", "paprika", "xanthan gum",
                "carrot fiber", "garlic powder", "natural flavor"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "BUFFALO SAUCE", ingredients);

        ingredients = new String[]{"sriracha sauce", "chili puree", "red pepper", "acetic acid", "water", "sugar", "salt", "garlic", "vinegar", "xanthan gum",
                "natural flavor", "sodium benzoate", "citric acid", "soybean oil", "liquid egg yolks", "modified corn starch", "color", "polysorbate 60",
                "potassium sorbate", "spice", "calcium disodium", "edta", "egg", "mustard"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "CREAMY SRIRACHA SAUCE", ingredients);

        ingredients = new String[]{"creole mustard", "distilled vinegar", "water", "rd seed", "salt", "xanthan gum", "honey", "soybean oil", "sugar",
                "liquid egg yolks", "mustard seed", "natural flavour", "molasses", "sodium benzoate", "potassium sorbate", "natural smoke flavour",
                "calcium disodium", "edta", "egg", "mustard"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "HONEY MUSTARD SAUCE", ingredients);

        ingredients = new String[]{"water", "soybean oil", "modified food starch", "distilled vinegar", "egg yolks", "whole egg", "salt", "spice",
                "potassium sorbate", "phosphoric acid", "calcium disodium", "edta", "dl-alpha tocopherol acetate", "natural flavor", "eggs"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "MAYONNAISE, LIGHT", ingredients);

        ingredients = new String[]{"soybean oil", "eggs", "water", "distilled vinegar", "salt", "sugar", "spice", "lemon juice concentrate", "calcium disodium", "edta"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "MAYONNAISE, REGULAR", ingredients);

        ingredients = new String[]{"vinegar", "water", "mustard seed", "salt", "turmeric", "paprika", "spice", "natural flavors", "garlic powder", "mustard"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "MUSTARD (Yellow)", ingredients);

        ingredients = new String[]{"soybean oil", "water", "extra virgin olive oil", "cabernet sauvignon wine vinegar", "sugar", "red bell pepper puree",
                "distilled vinegar", "salt", "parmesan cheese", "pasteurized part skim milk", "cheese cultures", "enzymes", "spices", "dehydrated garlic",
                "natural flavors", "dehydrated onion", "xanthan gum", "sodium benzoate", "potassium sorbate", "calcium disodium", "edta", "milk"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "MVP Parmesan Vinaigrette", ingredients);

        ingredients = new String[]{"canola oil", "virgin olive oil"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "OIL BLEND", ingredients);

        ingredients = new String[]{"soybean oil", "water", "cultured low-fat buttermilk", "distilled vinegar", "egg yolks", "sugar", "salt", "buttermilk",
                "natural flavors", "spice", "lactic acid", "dehydrated garlic", "sodium benzoate", "potassium sorbate", "xanthan gum", "dehydrated parsley",
                "dehydrated onion", "calcium disodium", "edta", "milk", "egg"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "PEPPERCORN RANCH SAUCE", ingredients);

        ingredients = new String[]{"black pepper"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "PEPPER, BLACK", ingredients);

        ingredients = new String[]{"red wine vinegar", "water", "sulfite"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "RED WINE VINEGAR", ingredients);

        ingredients = new String[]{"soybean oil", "water", "distilled vinegar", "extra virgin olive oil", "egg yolks", "garlic", "garlic juice", "sugar", "salt",
                "natural flavors", "dehydrated garlic", "lemon juice concentrate", "sodium benzoate", "potassium sorbate", "garlic juice concentrate", "xanthan gum",
                "dehydrated parsley", "spice", "citric acid", "propylene glycol alginate", "calcium disodium", "edta", "egg"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "ROASTED GARLIC AIOLI", ingredients);

        ingredients = new String[]{"salt"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "SALT", ingredients);

        ingredients = new String[]{"sugar", "water", "corn vinegar", "corn syrup", "soy sauce", "wheat", "soybean salt", "sodium benzoate", "modified food starch",
                "rice vinegar", "salt", "dehydrated onion", "tomato paste", "spices", "mustard seed", "distilled vinegar", "garlic", "sesame oil", "poppyseed",
                "sesame seed", "potassium sorbate", "dehydrated green onion", "dehydrated red bell pepper", "natural flavors", "dehydrated garlic", "autolyzed yeast extract",
                "citric acid", "soy", "sesame"};
        restaurantToFirebaseH(restRef, "Condiments & Seasonings", "SWEET ONION TERIYAKI SAUCE", ingredients);

        ingredients = new String[]{"crust", "enriched flour", "wheat flour", "niacin", "reduced iron", "thiamine mononitrate", "riboflavin", "folic acid", "water",
                "bread crumbs", "sugar", "yeast", "salt", "soybean oil", "non-fat dry milk", "garlic", "mozzarella cheese", "cultured pasteurized milk", "enzymes",
                "sauce", "tomato puree", "tomatoes", "citric acid", "tomato paste", "seasoning", "spices", "garlic powder", "romano cheese", "pasteurized cows milk",
                "cheese culture", "parmesan", "romano", "cows milk", "cheese blend", "pasteurized milk", "part skim milk", "oregano", "basil", "milk", "wheat"};
        restaurantToFirebaseH(restRef, "Pizza (Round)", "CHEESE PIZZA", ingredients);

        ingredients = new String[]{"apple", "lemon juice concentrate"};
        restaurantToFirebaseH(restRef, "Cookies & Sides", "APPLESAUCE", ingredients);

        ingredients = new String[]{"enriched flour", "wheat flour", "niacin", "iron", "thiamine mononitrate", "riboflavin", "folic acid", "sugar",
                "semi-sweet chocolate chips", "unsweetened chocolate", "cocoa butter", "soy lecithin", "vanilla extract", "palm blend", "butter blend", "palm oil", "butter",
                "cream", "milk", "salt", "water", "natural flavo", "beta carotene", "eggs", "pre-gelatinized wheat starch", "molasses", "baking soda", "natural flavor",
                "egg", "soy", "wheat", "peanut"};
        restaurantToFirebaseH(restRef, "Cookies & Sides", "CHOCOLATE CHIP", ingredients);

        ingredients = new String[]{"enriched flour", "wheat flour", "niacin", "iron", "thiamine mononitrate", "riboflavin", "folic acid", "sugar", "raisins", "palm oil",
                "rolled oats", "eggs", "water", "molasses", "natural flavor", "whey", "milk", "baking soda", "salt", "spice", "egg", "wheat", "peanut"};
        restaurantToFirebaseH(restRef, "Cookies & Sides", "OATMEAL RAISIN", ingredients);

        ingredients = new String[]{"enriched flour", "wheat flour", "niacin", "iron", "thiamine mononitrate", "riboflavin", "folic acid", "sugar", "palm oil", "butter blend",
                "butter", "cream", "milk", "salt", "water", "natural flavor", "beta carotene", "palmitate", "white confectionery chips", "fractionated palm kernel",
                "nonfat milk", "soy lecithin", "macadamia nuts", "eggs", "pre-gelatinized wheat starch", "molasses", "baking soda", "soy", "wheat", "peanut"};
        restaurantToFirebaseH(restRef, "Cookies & Sides", "WHITE CHIP MACADAMIA NUT", ingredients);

        ingredients = new String[]{"ground beef", "water", "tomatoes", "diced tomatoes", "tomato juice", "citric acid", "calcium chloride", "dark red kidney beans",
                "kidney beans", "salt", "seasoning", "spices", "chili pepper", "brown sugar", "hydrolyzed soy", "corn", "wheat protein", "wheat flour", "dextrose",
                "sugar", "yeast extract", "garlic powder", "maltodextrin", "soy lecithin", "onion powder", "soybean oil", "silicon dioxide", "celery", "tomato paste",
                "green bell peppers", "modified corn starch", "dehydrated onion", "beef fat type flavor", "beef fat", "natural flavor", "soy", "wheat"};
        restaurantToFirebaseH(restRef, "Cookies & Sides", "BEEF CHILI WITH BEANS", ingredients);

        ingredients = new String[]{"water", "whole milk", "milk", "vitamin d3", "broccoli", "pasteurized process cheddar cheese food", "cheddar cheese", "cheese culture",
                "salt", "enzymes", "whey", "whey protein concentrate", "nonfat milk", "sodium citrate", "milkfat", "lactic acid", "oleoresin paprika", "annatto extract",
                "pasteurized process white cheddar cheese", "pasteurized milk", "cheese cultures", "sodium phosphate", "dairy blend", "cream", "onions", "seasoning",
                "whey powder", "sea salt", "yeast extract", "sugar", "spices", "turmeric", "carrots", "modified corn starch", "unsalted butter", "natural flavors",
                "wheat flour", "garlic juice", "wheat"};
        restaurantToFirebaseH(restRef, "Soups", "BROCCOLI CHEDDAR", ingredients);

        ingredients = new String[]{"water", "white chicken meat", "carrots", "noodles", "semolina", "wheat", "eggs", "niacin", "iron", "ferrous sulfatel", "thiamin mononitrate",
                "riboflavin", "folic acid", "celery", "chicken", "stock base", "chicken stock", "yeast extract", "natural flavors", "salt", "chicken fat", "dried potato",
                "xanthan gum", "seasoning", "sea salt", "natural flavor", "disodium inosinate", "disodium guanylate", "corn starch", "sugar", "spice", "guar gum", "turmeric",
                "natural flavoring", "parsley", "tomato concentrate", "egg"};
        restaurantToFirebaseH(restRef, "Soups", "CHICKEN NOODLE", ingredients);

        ingredients = new String[]{"water", "potatoes", "sodium acid pyrophosphate", "dairy blend", "milk", "cream", "cream cheese", "pasteurized milk", "cheese culture",
                "salt", "guar gum", "carob bean gum", "xanthan gum", "uncured bacon topping", "pork", "sea salt", "cane sugar", "cultured celery juice", "natural flavors",
                "onions", "parmesan cheese", "pasteurized part-skim milk", "cheese cultures", "enzymes", "powdered cellulose", "seasoning", "modified corn starch",
                "enriched wheat flour", "sugar", "yeast extract", "whey powder", "spice", "natural flavor", "red potatoes", "green onions", "unsalted butter",
                "chicken stock plus", "chicken stock", "cheese", "parmesan type", "granular cheesse", "lactic acid", "citric acid", "pepper sauce", "distilled vinegar",
                "red pepper", "wheat"};
        restaurantToFirebaseH(restRef, "Soups", "LOADED BAKED POTATO WITH BACON", ingredients);
    }

    private void putMcdonalds(DatabaseReference restRef) {
        String[] ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin",
                "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast",
                "soybean oil", "partially hydrogenated soybean oil", "calcium sulfate", "calcium carbonate", "wheat gluten", "ammonium sulfate",
                "ammonium chloride", "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides",
                "ethoxylated monoglycerides", "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin",
                "wheat", "soy", "tomato", "distilled vinegar", "corn syrup", "natural vegetable flavor", "mustard seed", "turmeric", "paprika", "spice extractive", "cucumbers",
                "calcium chloride", "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "onions"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Hamburger", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "calcium carbonate", "wheat gluten", "ammonium sulfate", "ammonium chloride",
                "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "wheat", "soy", "milk",
                "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid", "acetic acid", "tomato",
                "distilled vinegar", "corn syrup", "natural vegetable flavor", "mustard seed", "turmeric", "paprika", "spice extractive", "cucumbers", "calcium chloride",
                "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "onions"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Cheeseburger", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "calcium carbonate", "wheat gluten", "ammonium sulfate", "ammonium chloride",
                "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides",
                "ethoxylated monoglycerides", "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate",
                "soy lecithin", "wheat", "soy", "milk", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate",
                "artificial color", "lactic acid", "acetic acid", "tomato", "distilled vinegar", "corn syrup", "natural vegetable flavor", "mustard seed",
                "turmeric", "paprika", "spice extractive", "cucumbers", "calcium chloride", "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "onions"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Double Cheeseburger", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "calcium carbonate", "wheat gluten", "ammonium sulfate", "ammonium chloride",
                "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides",
                "ethoxylated monoglycerides", "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate",
                "soy lecithin", "wheat", "soy", "milk", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate", "artificial color",
                "lactic acid", "acetic acid", "tomato", "distilled vinegar", "corn syrup", "natural vegetable flavor", "cucumbers", "calcium chloride", "alum",
                "potassium sorbate", "natural plant flavor", "polysorbate 80", "turmeric", "onions", "mustard seed", "paprika", "spice extractive"
        };
        restaurantToFirebaseH(restRef, "Burgers", "McDouble", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "red onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "ammonium chloride", "calcium carbonate", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "sesame seed", "wheat",
                "soy", "milk", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid",
                "acetic acid", "tomato", "distilled vinegar", "corn syrup", "natural vegetable flavor", "mustard seed", "turmeric", "paprika", "spice extractive",
                "cucumbers", "calcium chloride", "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "slivered onions"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Quarter Pounder with Cheese", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "red onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "ammonium chloride", "calcium carbonate", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "sesame seed", "wheat",
                "soy", "milk", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid",
                "acetic acid", "tomato", "distilled vinegar", "corn syrup", "natural vegetable flavor", "mustard seed", "turmeric", "paprika", "spice extractive",
                "cucumbers", "calcium chloride", "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "slivered onions"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Double Quarter Pounder with Cheese", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "milk", "water", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid",
                "sodium phosphate", "artificial color", "lactic acid", "acetic acid", "enzymes", "soy lecithin", "lettuce", "cucumbers", "distilled vinegar",
                "calcium chloride", "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "turmeric", "onions", "enriched flour", "bleached wheat flour",
                "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "high fructose corn syrup", "sugar", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "calcium carbonate", "wheat gluten", "ammonium sulfate", "ammonium chloride", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "sesame seed", "wheat", "soy",
                "pickle relish", "diced pickles", "vinegar", "corn syrup", "xanthan gum", "spice extractives", "egg yolks", "onion powder", "mustard seed", "spices",
                "propylene glycol alginate", "sodium benzoate", "mustard bran", "garlic powder", "vegetable protein", "hydrolyzed corn", "caramel color", "paprika",
                "calcium disodium edta", "egg"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Big Mac", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "red onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "ammonium chloride", "calcium carbonate", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "sesame seed", "wheat",
                "soy", "leaf lettuce", "tomato slice", "distilled vinegar", "maltodextrin", "food starch-modified", "enzyme modified egg yolk", "xanthan gum", "mustard flour",
                "potassium sorbate", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene", "egg", "tomato",
                "corn syrup", "natural vegetable flavor", "slivered onions", "cucumbers", "calcium chloride", "alum", "natural plant flavor", "turmeric"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Big N Tasty", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "red onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "ammonium chloride", "calcium carbonate", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "sesame seed", "wheat",
                "soy", "leaf lettuce", "tomato slice", "distilled vinegar", "maltodextrin", "food starch-modified", "enzyme modified egg yolk", "xanthan gum", "mustard flour",
                "potassium sorbate", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene", "egg", "milk", "milkfat",
                "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid", "acetic acid", "tomato", "corn syrup",
                "natural vegetable flavor", "onions", "cucumbers", "calcium chloride", "alum", "natural plant flavor", "turmeric"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Big N Tasty with Cheese", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "red onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "ammonium chloride", "calcium carbonate", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "sesame seed", "wheat",
                "soy", "leaf lettuce", "tomato slice", "distilled vinegar", "maltodextrin", "food starch-modified", "enzyme modified egg yolk", "xanthan gum", "mustard flour",
                "potassium sorbate", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene", "egg", "milk", "milkfat",
                "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid", "acetic acid", "tomato", "corn syrup",
                "natural vegetable flavor", "onions", "cucumbers", "calcium chloride", "alum", "natural plant flavor", "turmeric"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Big N Tasty with Cheese", ingredients);

        ingredients = new String[]{
                "pork bellies", "water", "salt", "sugar", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "angus beef", "black pepper",
                "onion powder", "natural botanical flavor", "artificial flavors", "maltodextrin", "natural beef flavor", "beef broth", "yeast extract", "lactic acid",
                "beef fat", "citric acid", "spice", "dextrose", "garlic powder", "dried beef extract", "sunflower oil", "caramel color", "worcestershire sauce powder",
                "distilled vinegar", "molasses", "corn syrup", "spices", "tamarind", "natural fruit flavor", "spice extractives", "annatto", "turmeric", "calcium silicate",
                "soybean oil", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid",
                "high fructose corn syrup", "yeast", "barley malt extract", "wheat gluten", "hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate",
                "yellow corn flour", "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "distilled monoglycerides",
                "monocalcium phosphate", "enzymes", "calcium peroxide", "paprika extracts", "calcium propionate", "sesame seed", "wheat", "milk", "milkfat", "cheese culture",
                "sodium citrate", "sorbic acid", "artificial color", "acetic acid", "soy lecithin", "red onions", "tomato", "natural vegetable flavor", "mustard seed",
                "paprika", "spice extractive", "cucumbers", "calcium chloride", "sodium benzoate", "polysorbate 80"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Angus Bacon & Cheese", ingredients);

        ingredients = new String[]{
                "angus beef", "salt", "black pepper", "sugar", "onion powder", "natural botanical flavor", "artificial flavors", "maltodextrin", "natural beef flavor",
                "beef broth", "yeast extract", "lactic acid", "natural plant flavor", "beef fat", "citric acid", "spice", "dextrose", "garlic powder", "dried beef extract",
                "sunflower oil", "caramel color", "worcestershire sauce powder", "distilled vinegar", "molasses", "corn syrup", "spices", "tamarind", "natural fruit flavor",
                "spice extractives", "annatto", "turmeric", "calcium silicate", "soybean oil", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin",
                "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water", "high fructose corn syrup", "yeast", "barley malt extract", "wheat gluten",
                "hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "yellow corn flour", "dough conditioners", "sodium stearoyl lactylate", "datem",
                "ascorbic acid", "azodicarbonamide", "distilled monoglycerides", "monocalcium phosphate", "enzymes", "calcium peroxide", "paprika extracts",
                "calcium propionate", "sesame seed", "wheat", "milk", "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "sodium phosphate", "artificial color",
                "acetic acid", "soy lecithin", "tomato slice", "food starch-modified", "enzyme modified egg yolk", "xanthan gum", "mustard flour", "potassium sorbate",
                "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene", "egg", "red onions", "lettuce", "mustard seed",
                "paprika", "spice extractive", "cucumbers", "calcium chloride", "sodium benzoate"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Angus Deluxe", ingredients);

        ingredients = new String[]{
                "angus beef", "salt", "black pepper", "sugar", "onion powder", "natural botanical flavor", "artificial flavors", "maltodextrin", "natural beef flavor",
                "beef broth", "yeast extract", "lactic acid", "natural plant flavor", "beef fat", "citric acid", "spice", "dextrose", "garlic powder", "dried beef extract",
                "sunflower oil", "caramel color", "worcestershire sauce powder", "distilled vinegar", "molasses", "corn syrup", "spices", "tamarind", "natural fruit flavor",
                "spice extractives", "annatto", "turmeric", "calcium silicate", "soybean oil", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin",
                "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water", "high fructose corn syrup", "yeast", "barley malt extract", "wheat gluten",
                "hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "yellow corn flour", "dough conditioners", "sodium stearoyl lactylate", "datem",
                "ascorbic acid", "azodicarbonamide", "distilled monoglycerides", "monocalcium phosphate", "enzymes", "calcium peroxide", "paprika extracts",
                "calcium propionate", "sesame seed", "wheat", "swiss cheese", "pasteurized milk", "cheese culture", "powdered cellulose", "potato starch", "natamycin",
                "milk", "mushrooms", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "soy lecithin", "monoglycerides",
                "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "food starch-modified", "enzyme modified egg yolk",
                "xanthan gum", "mustard flour", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "egg"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Angus Mushroom & Swiss", ingredients);

        ingredients = new String[]{
                "fish filet", "hoki", "pollock", "water", "food starch-modified", "yellow corn flour", "bleached wheat flour", "salt", "whey", "dextrose", "dried yeast",
                "sugar", "sodium polyphosphate", "potassium polyphosphate", "cellulose gum", "paprika", "turmeric extract", "natural plant flavor", "vegetable oil",
                "canola oil", "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane", "fish", "wheat", "milk", "enriched flour",
                "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "high fructose corn syrup", "yeast",
                "partially hydrogenated soybean oil", "calcium sulfate", "calcium carbonate", "wheat gluten", "ammonium sulfate", "ammonium chloride", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "soy", "pickle relish",
                "diced pickles", "vinegar", "capers", "xanthan gum", "potassium sorbate", "calcium chloride", "spice extractives", "polysorbate 80", "egg yolks", "onions",
                "distilled vinegar", "spice", "parsley", "egg", "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "sodium phosphate", "artificial color",
                "lactic acid", "acetic acid"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "Filet-O-Fish", ingredients);

        ingredients = new String[]{
                "chicken", "water", "salt", "sodium phosphates", "bleached wheat flour", "wheat flour", "food starch-modified", "spices", "wheat gluten", "paprika",
                "dextrose", "yeast", "garlic powder", "partially hydrogenated soybean oil", "cottonseed oil", "monoglycerides", "diglycerides", "leavening",
                "sodium acid pyrophosphate", "baking soda", "ammonium bicarbonate", "monocalcium phosphate", "natural plant flavor", "vegetable oil", "canola oil", "corn oil",
                "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane", "wheat", "enriched flour", "malted barley flour", "niacin",
                "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "high fructose corn syrup", "sugar", "calcium sulfate", "calcium carbonate",
                "ammonium sulfate", "ammonium chloride", "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide",
                "ethoxylated monoglycerides", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "soy", "lettuce",
                "distilled vinegar", "maltodextrin", "enzyme modified egg yolk", "xanthan gum", "mustard flour", "potassium sorbate", "lemon juice concentrate",
                "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene", "egg"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "McChicken", ingredients);

        ingredients = new String[]{
                "water", "high fructose corn syrup", "tomato paste", "distilled vinegar", "molasses", "natural plant flavor", "food starch-modified", "salt", "sugar",
                "spices", "soybean oil", "xanthan gum", "onion powder", "garlic powder", "chili pepper", "sodium benzoate", "caramel color", "beet powder", "pork",
                "dextrose", "bha", "bht", "propyl gallate", "citric acid", "cucumbers", "calcium chloride", "alum", "potassium sorbate", "polysorbate 80", "turmeric",
                "slivered onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin",
                "folic acid", "yeast", "corn meal", "wheat gluten", "partially hydrogenated soybean", "cottonseed oils", "cultured wheat flour", "calcium sulfate",
                "ammonium sulfate", "soy flour", "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides",
                "diglycerides", "ethoxylated monoglycerides", "monocalcium phosphate", "enzymes", "guar gum", "calcium peroxide", "calcium propionate", "soy lecithin",
                "wheat", "soy"
        };
        restaurantToFirebaseH(restRef, "Burgers", "McRib", ingredients);

        ingredients = new String[]{
                "water", "soybean oil", "distilled vinegar", "maltodextrin", "food starch-modified", "enzyme modified egg yolk", "salt", "sugar", "xanthan gum",
                "mustard flour", "potassium sorbate", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene",
                "egg", "boneless skinless chicken breast fillets with rib meat", "seasoning", "rice starch", "yeast extract", "canola oil", "onion powder", "chicken skin",
                "paprika", "flavor", "sunflower oil", "chicken", "garlic powder", "chicken fat", "spices", "sodium phosphates", "liquid soybean oil", "hydrogenated cottonseed",
                "soybean oils", "partially hydrogenated soybean oil", "soy lecithin", "monoglycerides", "diglycerides", "sodium benzoate", "artificial flavor", "citric acid",
                "palmitate", "leaf lettuce", "tomato slice", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate",
                "riboflavin", "folic acid", "whole wheat flour", "high fructose corn syrup", "honey", "wheat gluten", "yeast", "dietary fiber", "calcium sulfate",
                "dough conditioners", "wheat flour", "datem", "ascorbic acid", "azodicarbonamide", "sodium stearoyl lactylate", "enzymes", "calcium peroxide",
                "ammonium sulfate", "natural botanical flavor", "calcium propionate", "vitamin d3", "rolled wheat", "wheat"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "Premium Grilled Chicken Classic Sandwich", ingredients);

        ingredients = new String[]{
                "water", "soybean oil", "distilled vinegar", "maltodextrin", "food starch-modified", "enzyme modified egg yolk", "salt", "sugar", "xanthan gum",
                "mustard flour", "potassium sorbate", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene",
                "egg", "chicken breasts", "chicken rib meat", "seasoning", "sodium phosphates", "modified tapioca starch", "spice", "autolyzed yeast extract", "carrageenan",
                "natural vegetable flavor", "natural botanical flavor", "artificial flavors", "sunflower lecithin", "gum arabic", "bleached wheat flour", "wheat flour",
                "yellow corn flour", "leavening", "sodium acid pyrophosphate", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "ammonium bicarbonate",
                "wheat gluten", "spices", "corn starch", "dextrose", "paprika", "wheat", "tomato slice", "leaf lettuce", "enriched flour", "malted barley flour", "niacin",
                "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "whole wheat flour", "high fructose corn syrup", "honey", "yeast", "dietary fiber",
                "calcium sulfate", "dough conditioners", "datem", "ascorbic acid", "azodicarbonamide", "sodium stearoyl lactylate", "monoglycerides", "diglycerides",
                "enzymes", "calcium peroxide", "ammonium sulfate", "calcium propionate", "vitamin d3", "rolled wheat"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "Premium Crispy Chicken Classic Sandwich", ingredients);

        ingredients = new String[]{
                "water", "soybean oil", "distilled vinegar", "maltodextrin", "food starch-modified", "enzyme modified egg yolk", "salt", "sugar", "xanthan gum",
                "mustard flour", "potassium sorbate", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene",
                "egg", "pork bellies", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "chicken breast",
                "rib meat", "seasoning", "rice starch", "yeast extract", "canola oil", "onion powder", "chicken skin", "paprika", "flavor", "sunflower oil", "chicken",
                "garlic powder", "chicken fat", "spices", "sodium phosphates", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils",
                "partially hydrogenated soybean oil", "soy lecithin", "monoglycerides", "diglycerides", "sodium benzoate", "artificial flavor", "citric acid", "palmitate",
                "tomato slice", "swiss cheese", "pasteurized milk", "cheese culture", "enzymes", "powdered cellulose", "potato starch", "natamycin", "milk", "leaf lettuce",
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid",
                "whole wheat flour", "high fructose corn syrup", "honey", "wheat gluten", "yeast", "dietary fiber", "calcium sulfate", "dough conditioners",
                "wheat flour", "datem", "ascorbic acid", "azodicarbonamide", "sodium stearoyl lactylate", "calcium peroxide", "ammonium sulfate", "natural botanical flavor",
                "calcium propionate", "vitamin d3", "rolled wheat", "wheat"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "Premium Grilled Chicken Club Sandwich", ingredients);

        ingredients = new String[]{
                "water", "soybean oil", "distilled vinegar", "maltodextrin", "food starch-modified", "enzyme modified egg yolk", "salt", "sugar", "xanthan gum",
                "mustard flour", "potassium sorbate", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene",
                "egg", "pork bellies", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "chicken breasts", "chicken rib meat",
                "seasoning", "sodium phosphates", "modified tapioca starch", "spice", "autolyzed yeast extract", "carrageenan", "natural vegetable flavor",
                "natural botanical flavor", "artificial flavors", "sunflower lecithin", "gum arabic", "bleached wheat flour", "wheat flour", "yellow corn flour",
                "leavening", "sodium acid pyrophosphate", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "ammonium bicarbonate",
                "wheat gluten", "spices", "corn starch", "dextrose", "paprika", "wheat", "tomato slice", "swiss cheese", "pasteurized milk", "cheese culture",
                "enzymes", "powdered cellulose", "potato starch", "natamycin", "milk", "leaf lettuce", "enriched flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "whole wheat flour", "high fructose corn syrup", "honey", "yeast", "dietary fiber", "calcium sulfate",
                "dough conditioners", "datem", "ascorbic acid", "azodicarbonamide", "sodium stearoyl lactylate", "monoglycerides", "diglycerides", "calcium peroxide",
                "ammonium sulfate", "calcium propionate", "vitamin d3", "rolled wheat"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "Premium Crispy Chicken Club Sandwich", ingredients);

        ingredients = new String[]{
                "pork bellies", "water", "salt", "sugar", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite",
                "chicken breast", "rib meat", "seasoning", "rice starch", "yeast extract", "canola oil", "onion powder", "maltodextrin", "chicken skin", "paprika", "flavor",
                "sunflower oil", "chicken", "garlic powder", "chicken fat", "spices", "sodium phosphates", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils",
                "partially hydrogenated soybean oil", "soy lecithin", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor",
                "citric acid", "palmitate", "beta carotene", "tomato slice", "leaf lettuce", "soybean oil", "corn syrup solids", "buttermilk", "distilled vinegar",
                "egg yolks", "food starch-modified", "chives", "modified tapioca starch", "natural dairy flavor", "natural vegetable flavor", "natural botanical flavor",
                "natural animal flavor", "lactic acid", "parsley", "xanthan gum", "lemon juice concentrate", "propylene glycol alginate", "milk", "egg", "enriched flour",
                "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "whole wheat flour",
                "high fructose corn syrup", "honey", "wheat gluten", "yeast", "dietary fiber", "calcium sulfate", "dough conditioners", "wheat flour", "datem",
                "ascorbic acid", "azodicarbonamide", "sodium stearoyl lactylate", "enzymes", "calcium peroxide", "ammonium sulfate", "calcium propionate", "vitamin d3",
                "rolled wheat", "wheat"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "Premium Grilled Chicken Ranch BLT Sandwich", ingredients);

        ingredients = new String[]{
                "pork bellies", "water", "salt", "sugar", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "chicken breasts",
                "chicken rib meat", "seasoning", "sodium phosphates", "modified tapioca starch", "spice", "autolyzed yeast extract", "carrageenan",
                "natural vegetable flavor", "natural botanical flavor", "artificial flavors", "maltodextrin", "sunflower lecithin", "gum arabic",
                "bleached wheat flour", "wheat flour", "food starch-modified", "yellow corn flour", "leavening", "sodium acid pyrophosphate", "baking soda",
                "sodium aluminum phosphate", "monocalcium phosphate", "ammonium bicarbonate", "wheat gluten", "spices", "corn starch", "dextrose", "xanthan gum",
                "paprika", "wheat", "tomato slice", "leaf lettuce", "soybean oil", "corn syrup solids", "buttermilk", "distilled vinegar", "egg yolks", "garlic powder",
                "onion powder", "chives", "natural dairy flavor", "natural animal flavor", "lactic acid", "parsley", "lemon juice concentrate", "sodium benzoate",
                "propylene glycol alginate", "milk", "egg", "enriched flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin",
                "folic acid", "whole wheat flour", "high fructose corn syrup", "honey", "yeast", "dietary fiber", "calcium sulfate", "dough conditioners", "datem",
                "ascorbic acid", "azodicarbonamide", "sodium stearoyl lactylate", "monoglycerides", "diglycerides", "enzymes", "calcium peroxide", "ammonium sulfate",
                "calcium propionate", "vitamin d3", "rolled wheat"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "Premium Crispy Chicken Ranch BLT Sandwich", ingredients);

        ingredients = new String[]{
                "chicken breast filets", "water", "sugar", "salt", "modified tapioca starch", "spice", "yeast extract", "sodium phosphates", "carrageenan", "maltodextrin",
                "natural plant flavor", "artificial flavors", "gum arabic", "sunflower lecithin", "wheat flour", "food starch-modified", "yellow corn flour", "leavening",
                "baking soda", "sodium aluminum phosphate", "sodium acid pyrophosphate", "monocalcium phosphate", "wheat gluten", "paprika", "vegetable oil", "canola oil",
                "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane", "antifoaming agent", "wheat", "enriched flour",
                "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "enzymes",
                "high fructose corn syrup", "yeast", "partially hydrogenated soybean oil", "calcium sulfate", "calcium carbonate", "ammonium sulfate", "ammonium chloride",
                "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides",
                "ethoxylated monoglycerides", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "soy", "cucumbers",
                "distilled vinegar", "calcium chloride", "alum", "potassium sorbate", "polysorbate 80", "turmeric", "liquid soybean oil", "hydrogenated cottonseed",
                "soybean oils", "sodium benzoate", "artificial flavor", "palmitate", "beta carotene"
        };
        restaurantToFirebaseH(restRef, "Chicken & Fish Sandwiches", "Southern Style Crispy Chicken Sandwich", ingredients);

        ingredients = new String[]{
                "chicken breast strips", "water", "seasoning", "food starch-modified", "salt", "autolyzed yeast extract", "maltodextrin", "chicken broth",
                "natural plant flavor", "natural animal flavor", "spice", "chicken fat", "sodium phosphates", "sunflower lecithin", "wheat flour", "food starch",
                "spices", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "garlic powder", "onion powder", "dextrose", "spice extractive",
                "paprika", "vegetable oil", "canola oil", "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane", "wheat",
                "cheddar cheese", "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "corn starch", "powdered cellulose",
                "natamycin", "milk", "corn syrup solids", "buttermilk", "distilled vinegar", "egg yolks", "sugar", "chives", "modified tapioca starch",
                "natural dairy flavor", "natural vegetable flavor", "natural botanical flavor", "lactic acid", "parsley", "xanthan gum", "lemon juice concentrate",
                "sodium benzoate", "propylene glycol alginate", "egg", "shredded lettuce", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin",
                "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "vegetable shortening", "partially hydrogenated soybean oil",
                "hydrogenated cottonseed oil", "monoglycerides", "diglycerides", "sodium aluminum sulfate", "calcium sulfate", "sodium phosphate", "wheat gluten",
                "dough conditioners", "sodium metabisulfite", "distilled monoglycerides"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Ranch Snack Wrap (Crispy)", ingredients);

        ingredients = new String[]{
                "chicken breast", "rib meat", "water", "seasoning", "salt", "sugar", "food starch-modified", "maltodextrin", "spices", "dextrose", "autolyzed yeast extract",
                "hydrolyzed corn gluten", "soy", "wheat gluten", "proteins", "garlic powder", "paprika", "chicken fat", "chicken broth", "natural plant flavor",
                "natural animal flavor", "caramel color", "polysorbate 80", "xanthan gum", "onion powder", "modified potato starch", "sodium phosphates", "wheat",
                "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "soy lecithin", "monoglycerides", "diglycerides",
                "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene", "cheddar cheese", "pasteurized milk",
                "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "corn starch", "powdered cellulose", "natamycin", "milk",
                "shredded lettuce", "soybean oil", "corn syrup solids", "buttermilk", "distilled vinegar", "egg yolks", "chives", "modified tapioca starch",
                "natural dairy flavor", "natural vegetable flavor", "natural botanical flavor", "lactic acid", "parsley", "lemon juice concentrate",
                "propylene glycol alginate", "egg", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate",
                "riboflavin", "folic acid", "vegetable shortening", "hydrogenated soybean oil", "hydrogenated cottonseed oil", "leavening", "sodium aluminum sulfate",
                "calcium sulfate", "sodium phosphate", "baking soda", "monocalcium phosphate", "dough conditioners", "sodium metabisulfite", "distilled monoglycerides"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Ranch Snack Wrap (Grilled)", ingredients);

        ingredients = new String[]{
                "chicken breast strips", "water", "seasoning", "food starch-modified", "salt", "autolyzed yeast extract", "maltodextrin", "chicken broth",
                "natural plant flavor", "natural animal flavor", "spice", "chicken fat", "sodium phosphates", "sunflower lecithin", "wheat flour", "food starch", "spices",
                "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "garlic powder", "onion powder", "dextrose", "spice extractive",
                "paprika", "vegetable oil", "canola oil", "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane", "wheat",
                "cheddar cheese", "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "corn starch", "powdered cellulose",
                "natamycin", "milk", "shredded lettuce", "sugar", "dijon mustard", "distilled vinegar", "mustard seed", "white wine", "corn syrup solids", "honey",
                "egg yolks", "turmeric", "xanthan gum", "titanium dioxide", "propylene glycol alginate", "sodium benzoate", "yellow 5", "yellow 6", "egg", "enriched flour",
                "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "vegetable shortening",
                "partially hydrogenated soybean oil", "hydrogenated cottonseed oil", "monoglycerides", "diglycerides", "sodium aluminum sulfate", "calcium sulfate",
                "sodium phosphate", "wheat gluten", "dough conditioners", "sodium metabisulfite", "distilled monoglycerides"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Honey Mustard Snack Wrap (Crispy)", ingredients);

        ingredients = new String[]{
                "chicken breast", "rib meat", "water", "seasoning", "salt", "sugar", "food starch-modified", "maltodextrin", "spices", "dextrose",
                "autolyzed yeast extract", "hydrolyzed corn gluten", "soy", "wheat gluten", "proteins", "garlic powder", "paprika", "chicken fat", "chicken broth",
                "natural plant flavor", "natural animal flavor", "caramel color", "polysorbate 80", "xanthan gum", "onion powder", "modified potato starch",
                "sodium phosphates", "wheat", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "soy lecithin",
                "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene", "cheddar cheese",
                "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "corn starch", "powdered cellulose", "natamycin",
                "milk", "shredded lettuce", "dijon mustard", "distilled vinegar", "mustard seed", "white wine", "corn syrup solids", "honey", "soybean oil",
                "food starch", "egg yolks", "turmeric", "titanium dioxide", "propylene glycol alginate", "yellow 5", "yellow 6", "egg", "enriched flour",
                "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "vegetable shortening",
                "hydrogenated soybean oil", "hydrogenated cottonseed oil", "leavening", "sodium aluminum sulfate", "calcium sulfate", "sodium phosphate", "baking soda",
                "monocalcium phosphate", "dough conditioners", "sodium metabisulfite", "distilled monoglycerides"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Honey Mustard Snack Wrap (Grilled)", ingredients);

        ingredients = new String[]{
                "chicken breast strips", "water", "seasoning", "food starch-modified", "salt", "autolyzed yeast extract", "maltodextrin", "chicken broth",
                "natural plant flavor", "natural animal flavor", "spice", "chicken fat", "sodium phosphates", "sunflower lecithin", "wheat flour", "food starch",
                "spices", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "garlic powder", "onion powder", "dextrose", "spice extractive",
                "paprika", "vegetable oil", "canola oil", "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane",
                "wheat", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid",
                "vegetable shortening", "partially hydrogenated soybean oil", "hydrogenated cottonseed oil", "monoglycerides", "diglycerides", "sugar",
                "sodium aluminum sulfate", "calcium sulfate", "sodium phosphate", "corn starch", "wheat gluten", "dough conditioners", "sodium metabisulfite",
                "distilled monoglycerides", "cheddar cheese", "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch",
                "powdered cellulose", "natamycin", "milk", "shredded lettuce", "high fructose corn syrup", "tomato paste", "honey", "distilled vinegar", "molasses",
                "artificial flavors", "dried chipotle peppers", "natural plant", "dried chili peppers", "sodium benzoate", "potassium sorbate", "caramel color",
                "propylene glycol alginate"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Chipotle BBQ Snack Wrap (Crispy)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water",
                "vegetable shortening", "hydrogenated soybean oil", "soybean oil", "partially hydrogenated soybean oil", "hydrogenated cottonseed oil", "monoglycerides",
                "diglycerides", "sugar", "leavening", "sodium aluminum sulfate", "calcium sulfate", "sodium phosphate", "baking soda", "corn starch", "monocalcium phosphate",
                "salt", "wheat gluten", "dough conditioners", "sodium metabisulfite", "distilled monoglycerides", "wheat", "chicken breast filets with rib meat",
                "seasoning", "food starch-modified", "maltodextrin", "spices", "dextrose", "autolyzed yeast extract", "hydrolyzed corn gluten", "soy", "proteins",
                "garlic powder", "paprika", "chicken fat", "chicken broth", "natural plant flavor", "natural animal flavor", "caramel color", "polysorbate 80",
                "xanthan gum", "onion powder", "modified potato starch", "sodium phosphates", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils",
                "soy lecithin", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene", "cheddar cheese",
                "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "powdered cellulose", "natamycin", "milk",
                "shredded lettuce", "high fructose corn syrup", "tomato paste", "honey", "distilled vinegar", "molasses", "artificial flavors", "dried chipotle peppers",
                "natural plant", "dried chili peppers", "propylene glycol alginate", "spice"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Chipotle BBQ Snack Wrap (Grilled)", ingredients);

        ingredients = new String[]{
                "pork bellies", "water", "salt", "sugar", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "onion powder",
                "natural botanical flavor", "artificial flavors", "maltodextrin", "natural beef flavor", "beef broth", "yeast extract", "lactic acid", "beef fat",
                "citric acid", "spice", "dextrose", "autolyzed yeast extract", "garlic powder", "dried beef extract", "sunflower oil", "caramel color",
                "worcestershire sauce powder", "distilled vinegar", "molasses", "corn syrup", "spices", "tamarind", "natural fruit flavor", "annatto", "turmeric",
                "calcium silicates", "soybean oil", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate",
                "riboflavin", "folic acid", "vegetable shortening", "hydrogenated soybean oil", "partially hydrogenated soybean oil", "hydrogenated cottonseed oil",
                "monoglycerides", "diglycerides", "leavening", "sodium aluminum sulfate", "calcium sulfate", "baking soda", "corn starch", "monocalcium phosphate",
                "wheat gluten", "dough conditioners", "sodium metabisulfite", "distilled monoglycerides", "wheat", "angus beef", "black pepper", "milk", "milkfat",
                "cheese culture", "sodium citrate", "sorbic acid", "artificial color", "acetic acid", "enzymes", "soy lecithin", "red onions", "tomato",
                "high fructose corn syrup", "natural vegetable flavor", "mustard seed", "paprika", "spice extractive", "cucumbers", "calcium chloride", "sodium benzoate",
                "polysorbate 80"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Angus Bacon & Cheese Snack Wrap", ingredients);

        ingredients = new String[]{
                "pork bellies", "water", "salt", "sugar", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "angus beef",
                "black pepper", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin",
                "folic acid", "high fructose corn syrup", "yeast", "barley malt extract", "soybean oil", "wheat gluten", "hydrogenated soybean oil", "calcium sulfate",
                "ammonium sulfate", "yellow corn flour", "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide",
                "distilled monoglycerides", "monocalcium phosphate", "enzymes", "calcium peroxide", "turmeric", "annatto", "paprika extracts", "artificial flavors",
                "caramel color", "calcium propionate", "sesame seed", "wheat", "milk", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid",
                "artificial color", "lactic acid", "acetic acid", "soy lecithin", "red onions", "cucumbers", "distilled vinegar", "calcium chloride", "sodium benzoate",
                "polysorbate 80", "tomato paste", "honey", "molasses", "food starch-modified", "dried chipotle peppers", "natural plant", "dried chili peppers",
                "potassium sorbate", "onion powder", "garlic powder", "propylene glycol alginate", "spice"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Angus Chipotle BBQ Bacon", ingredients);

        ingredients = new String[]{
                "salt", "sugar", "onion powder", "natural botanical flavor", "artificial flavors", "maltodextrin", "natural beef flavor", "beef broth", "yeast extract",
                "lactic acid", "natural plant flavor", "beef fat", "citric acid", "spice", "dextrose", "autolyzed yeast extract", "garlic powder", "dried beef extract",
                "sunflower oil", "caramel color", "worcestershire sauce powder", "distilled vinegar", "molasses", "corn syrup", "spices", "tamarind", "natural fruit flavor",
                "annatto", "turmeric", "calcium silicates", "soybean oil", "angus beef", "black pepper", "milk", "water", "milkfat", "cheese culture", "sodium citrate",
                "sorbic acid", "sodium phosphate", "artificial color", "acetic acid", "enzymes", "soy lecithin", "red onions", "enriched flour", "bleached wheat flour",
                "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "vegetable shortening", "hydrogenated soybean oil",
                "partially hydrogenated soybean oil", "hydrogenated cottonseed oil", "monoglycerides", "diglycerides", "leavening", "sodium aluminum sulfate",
                "calcium sulfate", "baking soda", "corn starch", "monocalcium phosphate", "wheat gluten", "dough conditioners", "sodium metabisulfite",
                "distilled monoglycerides", "wheat", "cucumbers", "calcium chloride", "sodium benzoate", "polysorbate 80", "pork bellies", "sodium erythorbate",
                "sodium nitrite", "high fructose corn syrup", "tomato paste", "honey", "food starch-modified", "dried chipotle peppers", "natural plant",
                "dried chili peppers", "potassium sorbate", "propylene glycol alginate"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Angus Chipotle BBQ Bacon Snack Wrap", ingredients);

        ingredients = new String[]{
                "salt", "sugar", "onion powder", "natural botanical flavor", "artificial flavors", "maltodextrin", "natural beef flavor", "beef broth", "yeast extract",
                "lactic acid", "natural plant flavor", "beef fat", "citric acid", "spice", "dextrose", "autolyzed yeast extract", "garlic powder", "dried beef extract",
                "sunflower oil", "caramel color", "worcestershire sauce powder", "distilled vinegar", "molasses", "corn syrup", "spices", "tamarind", "natural fruit flavor",
                "annatto", "turmeric", "calcium silicates", "soybean oil", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "water", "vegetable shortening", "hydrogenated soybean oil", "partially hydrogenated soybean oil",
                "hydrogenated cottonseed oil", "monoglycerides", "diglycerides", "leavening", "sodium aluminum sulfate", "calcium sulfate", "sodium phosphate",
                "baking soda", "corn starch", "monocalcium phosphate", "wheat gluten", "dough conditioners", "sodium metabisulfite", "distilled monoglycerides",
                "wheat", "angus beef", "black pepper", "milk", "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "artificial color", "acetic acid",
                "enzymes", "soy lecithin", "mustard seed", "paprika", "spice extractive", "food starch-modified", "enzyme modified egg yolk", "xanthan gum",
                "mustard flour", "potassium sorbate", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "beta carotene",
                "egg", "tomato slice", "red onions", "leaf lettuce", "cucumbers", "calcium chloride", "sodium benzoate"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Angus Deluxe Snack Wrap", ingredients);

        ingredients = new String[]{"salt", "sugar", "onion powder", "natural botanical flavor", "artificial flavors", "maltodextrin", "natural beef flavor",
                "beef broth", "yeast extract", "lactic acid", "natural plant flavor", "beef fat", "citric acid", "spice", "dextrose", "autolyzed yeast extract",
                "garlic powder", "dried beef extract", "sunflower oil", "caramel color", "worcestershire sauce powder", "distilled vinegar", "molasses", "corn syrup",
                "spices", "tamarind", "natural fruit flavor", "annatto", "turmeric", "calcium silicates", "soybean oil", "enriched flour", "bleached wheat flour",
                "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water", "vegetable shortening",
                "hydrogenated soybean oil", "partially hydrogenated soybean oil", "hydrogenated cottonseed oil", "monoglycerides", "diglycerides", "leavening",
                "sodium aluminum sulfate", "calcium sulfate", "sodium phosphate", "baking soda", "corn starch", "monocalcium phosphate", "wheat gluten", "dough conditioners",
                "sodium metabisulfite", "distilled monoglycerides", "wheat", "angus beef", "black pepper", "swiss cheese", "pasteurized milk", "cheese culture", "enzymes",
                "powdered cellulose", "potato starch", "natamycin", "milk", "mushrooms", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "soy lecithin",
                "sodium benzoate", "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "food starch-modified", "enzyme modified egg yolk",
                "xanthan gum", "mustard flour", "lemon juice concentrate", "polysorbate 80", "natural animal flavor", "calcium disodium edta", "egg"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Angus Mushroom & Swiss Snack Wrap", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "red onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin",
                "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water", "vegetable shortening", "hydrogenated soybean oil", "soybean oil",
                "partially hydrogenated soybean oil", "hydrogenated cottonseed oil", "monoglycerides", "diglycerides", "sugar", "leavening", "sodium aluminum sulfate",
                "calcium sulfate", "sodium phosphate", "baking soda", "corn starch", "monocalcium phosphate", "wheat gluten", "dough conditioners", "sodium metabisulfite",
                "distilled monoglycerides", "wheat", "shredded lettuce", "cucumbers", "distilled vinegar", "calcium chloride", "alum", "potassium sorbate",
                "natural plant flavor", "polysorbate 80", "turmeric", "milk", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid",
                "artificial color", "lactic acid", "acetic acid", "enzymes", "soy lecithin", "onions", "pickle relish", "diced pickles", "high fructose corn syrup",
                "vinegar", "corn syrup", "xanthan gum", "spice extractives", "egg yolks", "onion powder", "mustard seed", "spices", "propylene glycol alginate",
                "sodium benzoate", "mustard bran", "garlic powder", "vegetable protein", "hydrolyzed corn", "soy", "caramel color", "paprika", "calcium disodium edta", "egg"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Mac Snack Wrap", ingredients);

        ingredients = new String[]{
                "chicken breast", "rib meat", "water", "seasoning", "salt", "sugar", "food starch-modified", "maltodextrin", "spices", "dextrose",
                "autolyzed yeast extract", "hydrolyzed corn gluten", "soy", "wheat gluten", "proteins", "garlic powder", "paprika", "chicken fat", "chicken broth",
                "natural plant flavor", "natural animal flavor", "caramel color", "polysorbate 80", "xanthan gum", "onion powder", "modified potato starch",
                "sodium phosphates", "wheat", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "soy lecithin",
                "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene"
        };
        restaurantToFirebaseH(restRef, "Wraps", "Grilled Chicken Breast Filet", ingredients);

        ingredients = new String[]{
                "enriched flour", "wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water",
                "whole wheat flour", "dry honey blend", "honey", "high fructose corn syrup", "sugar", "corn syrup", "wheat starch", "yeast", "soybean oil",
                "canola oil", "salt", "wheat gluten", "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides",
                "diglycerides", "monocalcium phosphate", "enzymes", "calcium peroxide", "calcium propionate", "soy lecithin", "wheat"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Honey Wheat Roll", ingredients);

        ingredients = new String[]{
                "chicken", "water", "salt", "sodium phosphates", "bleached wheat flour", "wheat flour", "food starch-modified", "spices", "wheat gluten", "paprika",
                "dextrose", "yeast", "garlic powder", "partially hydrogenated soybean oil", "cottonseed oil", "monoglycerides", "diglycerides", "leavening",
                "sodium acid pyrophosphate", "baking soda", "ammonium bicarbonate", "monocalcium phosphate", "natural plant flavor", "vegetable oil", "canola oil",
                "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane", "wheat"
        };
        restaurantToFirebaseH(restRef, "Burgers", "McChicken Patty", ingredients);

        ingredients = new String[]{
                "potatoes", "vegetable oil", "canola oil", "hydrogenated soybean oil", "natural beef flavor", "wheat", "milk", "citric acid", "dextrose", "sodium acid",
                "pyrophosphate", "salt", "corn oil", "soybean oil", "tbhq", "dimethylpolysiloxane", "hydrolyzed wheat", "hydrolyzed milk"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "French Fries", ingredients);

        ingredients = new String[]{
                "tomatoes", "distilled vinegar", "high fructose corn syrup", "corn syrup", "water", "salt", "natural vegetable flavor"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Ketchup Packet", ingredients);

        ingredients = new String[]{"salt"};
        restaurantToFirebaseH(restRef, "Fries & Sides", "Salt Packet", ingredients);

        ingredients = new String[]{
                "white boneless chicken", "water", "food starch-modified", "salt", "seasoning", "autolyzed yeast extract", "wheat starch", "natural botanical flavor",
                "safflower oil", "dextrose", "citric acid", "sodium phosphates", "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate",
                "riboflavin", "folic acid", "yellow corn flour", "leavening", "baking soda", "sodium acid", "pyrophosphate", "sodium aluminum phosphate",
                "monocalcium phosphate", "calcium lactate", "spices", "corn starch", "wheat", "vegetable oil", "canola oil", "corn oil", "soybean oil",
                "hydrogenated soybean oil", "tbhq", "dimethylpolysiloxane"
        };
        restaurantToFirebaseH(restRef, "McNuggets and Meals", "Chicken McNuggets", ingredients);

        ingredients = new String[]{
                "white boneless chicken", "water", "food starch-modified", "salt", "seasoning", "autolyzed yeast extract", "wheat starch", "natural botanical flavor",
                "safflower oil", "dextrose", "citric acid", "sodium phosphates", "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate",
                "riboflavin", "folic acid", "yellow corn flour", "leavening", "baking soda", "sodium acid", "pyrophosphate", "sodium aluminum phosphate",
                "monocalcium phosphate", "calcium lactate", "spices", "corn starch", "wheat", "vegetable oil", "canola oil", "corn oil", "soybean oil",
                "hydrogenated soybean oil", "tbhq", "dimethylpolysiloxane"
        };
        restaurantToFirebaseH(restRef, "McNuggets and Meals", "Chicken McNuggets 10pc", ingredients);

        ingredients = new String[]{
                "high fructose corn syrup", "water", "tomato paste", "grape vinegar", "distilled vinegar", "salt", "soy sauce", "wheat", "soybeans", "food starch-modified",
                "spices", "dextrose", "soybean oil", "natural plant flavor", "xanthan gum", "caramel color", "garlic powder", "cellulose gum", "dried chili peppers",
                "malic acid", "natural fruit flavor", "natural vegetable flavor", "onion powder", "sodium benzoate", "succinic acid", "soy"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Barbeque Sauce", ingredients);

        ingredients = new String[]{
                "water", "high fructose corn syrup", "distilled vinegar", "soybean oil", "mustard seed", "salt", "spices", "egg yolks", "food starch", "turmeric",
                "xanthan gum", "annatto extract", "sodium benzoate", "caramel color", "calcium disodium edta", "spice extractives", "paprika", "egg"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Hot Mustard Sauce", ingredients);

        ingredients = new String[]{
                "high fructose corn syrup", "water", "apricot puree concentrate", "peach puree concentrate", "distilled vinegar", "soy sauce", "wheat", "soybeans", "salt",
                "food starch", "dextrose", "soybean oil", "xanthan gum", "spices", "sodium benzoate", "natural fruit flavor", "natural vegetable flavor", "soy",
                "natural plant flavor", "garlic powder", "cellulose gum", "dried chili peppers", "malic acid", "onion powder", "caramel color", "paprika", "succinic acid"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Sweet 'N Sour Sauce", ingredients);

        ingredients = new String[]{
                "distilled vinegar", "water", "red cayenne pepper", "soybean oil", "salt", "xanthan gum", "caramel color", "guar gum", "sodium benzoate",
                "propylene glycol alginate", "natural dairy flavor", "natural vegetable flavor", "artificial flavor", "ascorbic acid", "garlic powder", "red 40", "milk"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Spicy Buffalo Sauce", ingredients);

        ingredients = new String[]{
                "soybean oil", "water", "distilled vinegar", "sugar", "buttermilk", "salt", "egg yolks", "natural dairy flavor", "natural botanical flavor", "garlic",
                "lactic acid", "xanthan gum", "spice", "onion powder", "potassium sorbate", "monosodium glutamate", "propylene glycol alginate", "parsley", "milk", "egg"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Creamy Ranch Sauce", ingredients);

        ingredients = new String[]{
                "high fructose corn syrup", "water", "tomato paste", "honey", "distilled vinegar", "molasses", "food starch-modified", "salt", "dried chipotle peppers",
                "natural plant flavor", "dried chili peppers", "soybean oil", "onion powder", "sodium benzoate", "potassium sorbate", "garlic powder",
                "propylene glycol alginate", "caramel color", "spice"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Southwestern Chipotle Barbeque Sauce", ingredients);

        ingredients = new String[]{
                "chicken breast", "rib meat", "water", "seasoning", "salt", "sugar", "food starch-modified", "maltodextrin", "spices", "dextrose",
                "autolyzed yeast extract", "hydrolyzed corn gluten", "soy", "wheat gluten", "proteins", "garlic powder", "paprika", "chicken fat", "chicken broth",
                "natural plant flavor", "natural animal flavor", "caramel color", "polysorbate 80", "xanthan gum", "onion powder", "modified potato starch",
                "sodium phosphates", "wheat", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "soy lecithin",
                "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene", "iceberg lettuce",
                "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf", "baby red swiss chard",
                "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio", "carrots", "roasted corn",
                "black beans", "roasted tomato", "poblano pepper", "lime juice", "lime juice concentrate", "lime oil", "cilantro", "salad dressings", "cheddar cheese",
                "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "corn starch", "powdered cellulose", "natamycin",
                "milk", "corn syrup solids", "high fructose corn syrup", "distilled vinegar", "olive oil", "soybean oil", "freeze-dried orange juice concentrate",
                "freeze-dried lime juice concentrate", "propylene glycol alginate", "spice", "grape tomatoes", "corn", "vegetable oil", "corn oil", "sunflower oil",
                "dried tomato", "green bell pepper powder", "malic acid", "paprika extract", "disodium inosinate", "disodium guanylate", "natural vegetable flavor",
                "lemon extract", "spice extractive", "lime"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Southwest Salad with Grilled Chicken", ingredients);

        ingredients = new String[]{
                "chicken breast filets", "water", "sugar", "salt", "modified tapioca starch", "spice", "yeast extract", "sodium phosphates", "carrageenan",
                "maltodextrin", "natural plant flavor", "artificial flavors", "gum arabic", "sunflower lecithin", "wheat flour", "food starch-modified",
                "yellow corn flour", "leavening", "baking soda", "sodium aluminum phosphate", "sodium acid pyrophosphate", "monocalcium phosphate", "wheat gluten",
                "paprika", "vegetable oil", "canola oil", "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane",
                "antifoaming agent", "wheat", "iceberg lettuce", "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf",
                "baby green leaf", "baby red swiss chard", "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee",
                "radicchio", "carrots", "roasted corn", "black beans", "roasted tomato", "poblano pepper", "lime juice", "lime juice concentrate", "lime oil", "cilantro",
                "salad dressings", "corn syrup solids", "high fructose corn syrup", "distilled vinegar", "olive oil", "freeze-dried orange juice concentrate",
                "freeze-dried lime juice concentrate", "xanthan gum", "sodium benzoate", "potassium sorbate", "garlic powder", "propylene glycol alginate", "onion powder",
                "grape tomatoes", "cheddar cheese", "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "corn starch",
                "powdered cellulose", "dextrose", "natamycin", "milk", "corn", "sunflower oil", "dried tomato", "spices", "green bell pepper powder",
                "autolyzed yeast extract", "malic acid", "paprika extract", "disodium inosinate", "disodium guanylate", "natural vegetable flavor", "lemon extract",
                "spice extractive", "lime"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Southwest Salad with Crispy Chicken", ingredients);

        ingredients = new String[]{
                "iceberg lettuce", "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf",
                "baby red swiss chard", "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio",
                "carrots", "roasted corn", "black beans", "roasted tomato", "poblano pepper", "lime juice", "water", "lime juice concentrate", "lime oil", "cilantro",
                "salad dressings", "cheddar cheese", "pasteurized milk", "cheese culture", "salt", "enzymes", "annatto", "monterey jack cheese", "potato starch",
                "corn starch", "powdered cellulose", "dextrose", "natamycin", "milk", "corn", "vegetable oil", "corn oil", "soybean oil", "sunflower oil", "maltodextrin",
                "sugar", "dried tomato", "spices", "onion powder", "green bell pepper powder", "citric acid", "autolyzed yeast extract", "malic acid", "paprika extract",
                "disodium inosinate", "disodium guanylate", "natural vegetable flavor", "lemon extract", "spice extractive", "lime"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Southwest Salad (without chicken)", ingredients);

        ingredients = new String[]{"chicken breast filets with rib meat", "water", "seasoning", "salt", "sugar", "food starch-modified", "maltodextrin", "spices",
                "dextrose", "autolyzed yeast extract", "hydrolyzed corn gluten", "soy", "wheat gluten", "proteins", "garlic powder", "paprika", "chicken fat",
                "chicken broth", "natural plant flavor", "natural animal flavor", "caramel color", "polysorbate 80", "xanthan gum", "onion powder", "modified potato starch",
                "sodium phosphates", "wheat", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "soy lecithin",
                "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene", "iceberg lettuce",
                "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf", "baby red swiss chard",
                "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio", "carrots", "grape tomatoes",
                "cheddar cheese", "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "corn starch", "powdered cellulose",
                "natamycin", "milk", "pork bellies", "sodium phosphate", "sodium erythorbate", "sodium nitrite"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Bacon Ranch Salad with Grilled Chicken", ingredients);

        ingredients = new String[]{
                "chicken breast filets", "water", "sugar", "salt", "modified tapioca starch", "spice", "yeast extract", "sodium phosphates", "carrageenan", "maltodextrin",
                "natural plant flavor", "artificial flavors", "gum arabic", "sunflower lecithin", "wheat flour", "food starch-modified", "yellow corn flour", "leavening",
                "baking soda", "sodium aluminum phosphate", "sodium acid pyrophosphate", "monocalcium phosphate", "wheat gluten", "paprika", "vegetable oil", "canola oil",
                "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane", "antifoaming agent", "wheat", "iceberg lettuce",
                "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf", "baby red swiss chard",
                "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio", "carrots", "grape tomatoes",
                "cheddar cheese", "pasteurized milk", "cheese culture", "enzymes", "annatto", "monterey jack cheese", "potato starch", "corn starch", "powdered cellulose",
                "dextrose", "natamycin", "milk", "pork bellies", "sodium phosphate", "sodium erythorbate", "sodium nitrite"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Bacon Ranch Salad with Crispy Chicken", ingredients);

        ingredients = new String[]{
                "iceberg lettuce", "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf",
                "baby red swiss chard", "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio",
                "carrots", "grape tomatoes", "cheddar cheese", "pasteurized milk", "cheese culture", "salt", "enzymes", "annatto", "monterey jack cheese", "potato starch",
                "corn starch", "powdered cellulose", "dextrose", "natamycin", "milk", "pork bellies", "water", "sugar", "natural plant flavor", "sodium phosphate",
                "sodium erythorbate", "sodium nitrite"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Bacon Ranch Salad (without chicken)", ingredients);

        ingredients = new String[]{
                "chicken breast filets with rib meat", "water", "seasoning", "salt", "sugar", "food starch-modified", "maltodextrin", "spices", "dextrose",
                "autolyzed yeast extract", "hydrolyzed corn gluten", "soy", "wheat gluten", "proteins", "garlic powder", "paprika", "chicken fat", "chicken broth",
                "natural plant flavor", "natural animal flavor", "caramel color", "polysorbate 80", "xanthan gum", "onion powder", "modified potato starch",
                "sodium phosphates", "wheat", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "soy lecithin",
                "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene",
                "iceberg lettuce", "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf",
                "baby red swiss chard", "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio",
                "carrots", "grape tomatoes", "parmesan cheese", "pasteurized milk", "cheese culture", "enzymes", "powdered cellulose", "potato starch", "natamycin", "milk"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Caesar Salad with Grilled Chicken", ingredients);

        ingredients = new String[]{
                "chicken breast filets", "water", "sugar", "salt", "modified tapioca starch", "spice", "yeast extract", "sodium phosphates", "carrageenan", "maltodextrin",
                "natural plant flavor", "artificial flavors", "gum arabic", "sunflower lecithin", "wheat flour", "food starch-modified", "yellow corn flour", "leavening",
                "baking soda", "sodium aluminum phosphate", "sodium acid pyrophosphate", "monocalcium phosphate", "wheat gluten", "paprika", "vegetable oil", "canola oil",
                "corn oil", "soybean oil", "hydrogenated soybean oil", "tbhq", "citric acid", "dimethylpolysiloxane", "antifoaming agent", "wheat", "iceberg lettuce",
                "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf", "baby red swiss chard",
                "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio", "carrots", "grape tomatoes",
                "parmesan cheese", "pasteurized milk", "cheese culture", "enzymes", "powdered cellulose", "potato starch", "natamycin", "milk"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Caesar Salad with Crispy Chicken", ingredients);

        ingredients = new String[]{
                "iceberg lettuce", "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf",
                "baby red swiss chard", "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio",
                "carrots", "grape tomatoes", "parmesan cheese", "pasteurized milk", "salt", "cheese culture", "enzymes", "powdered cellulose", "potato starch",
                "natamycin", "milk"
        };
        restaurantToFirebaseH(restRef, "Salads", "Premium Caesar Salad (without chicken)", ingredients);

        ingredients = new String[]{
                "iceberg lettuce", "romaine lettuce", "baby lettuces", "lettuces", "baby red romaine", "baby green romaine", "baby red leaf", "baby green leaf",
                "baby red swiss chard", "baby red oak", "baby green oak", "parella", "lolla rosa", "tango", "totsoi", "arugula", "mizuna", "frisee", "radicchio",
                "carrots", "spring mix", "tatsoi", "grape tomatoes"
        };
        restaurantToFirebaseH(restRef, "Salads", "Side Salad", ingredients);

        ingredients = new String[]{
                "enriched flour", "wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid",
                "partially hydrogenated soybean oil", "yeast", "salt", "sugar", "high fructose corn syrup", "dough conditioners", "wheat gluten", "calcium sulfate",
                "ascorbic acid", "azodicarbonamide", "calcium peroxide", "whey", "maltodextrin", "garlic powder", "annatto", "butter oil", "natural dairy flavor",
                "natural botanical flavor", "natural vegetable flavor", "artificial flavors", "parsley", "disodium inosinate", "disodium guanylate", "natural plant flavor",
                "artificial smoke flavors", "beta carotene", "alpha-tocopherol", "soy lecithin", "calcium propionate", "wheat", "milk"
        };
        restaurantToFirebaseH(restRef, "Salads", "Butter Garlic Croutons", ingredients);

        ingredients = new String[]{
                "apple slices", "red grapes", "apples", "calcium ascorbate", "vanilla lowfat yogurt", "fat milk", "sugar", "food starch-modified", "fructose",
                "whey protein concentrate", "corn starch", "gelatin", "natural plant flavor", "artificial flavor", "potassium sorbate", "artificial color", "milk"
        };
        restaurantToFirebaseH(restRef, "Salads", "Snack Size Fruit & Walnut Salad", ingredients);

        ingredients = new String[]{
                "walnuts", "tbhq", "bht", "sugar", "peanut oil", "dry honey", "salt", "wheat starch", "maltodextrin", "xanthan gum", "soy lecithin", "natural plant flavor",
                "artificial flavor", "milk", "wheat", "tree nuts", "shell parts", "peanuts"
        };
        restaurantToFirebaseH(restRef, "Salads", "Candied Walnuts", ingredients);

        ingredients = new String[]{
                "water", "corn syrup solids", "soybean oil", "distilled vinegar", "egg yolks", "corn vinegar", "corn starch", "whey", "salt", "cilantro", "sugar",
                "spices", "onion powder", "garlic powder", "apple cider vinegar", "sour cream powder", "cultured sour cream", "skim milk solids", "citric acid",
                "lactic acid", "natural animal flavor", "natural vegetable flavor", "xanthan gum", "paprika", "parsley", "egg", "milk"
        };
        restaurantToFirebaseH(restRef, "Salads", "Newman's Own Creamy Southwest Dressing", ingredients);

        ingredients = new String[]{
                "water", "soybean oil", "distilled vinegar", "parmesan", "romano", "granular cheeses", "pasteurized milk", "cheese cultures", "salt", "enzymes",
                "fructose", "egg yolks", "corn starch", "anchovy powder", "dextrin", "anchovy extract", "olive oil", "spices", "lactic acid", "worcestershire sauce",
                "molasses", "corn syrup", "caramel color", "garlic powder", "sugar", "tamarind", "natural fruit flavor", "xanthan gum", "whey", "onion powder", "milk",
                "egg", "fish", "anchovy"
        };
        restaurantToFirebaseH(restRef, "Salads", "Newman's Own Creamy Caesar Dressing", ingredients);

        ingredients = new String[]{
                "water", "balsamic vinegar", "sulfites", "sugar", "soybean oil", "salt", "distilled vinegar", "olive oil", "garlic", "spices", "xanthan gum",
                "onion", "red bell pepper flakes", "dried green onion", "parsley", "oleoresin paprika"
        };
        restaurantToFirebaseH(restRef, "Salads", "Newman's Own Low Fat Balsamic Vinaigrette", ingredients);

        ingredients = new String[]{
                "water", "corn syrup solids", "distilled vinegar", "romano cheese", "part skim milk", "cheese cultures", "salt", "enzymes", "soybean oil", "sugar",
                "onion powder", "anchovies", "garlic powder", "spices", "xanthan gum", "paprika", "barley malt extract", "barley gluten", "citric acid",
                "hydrolyzed soy protein", "worcestershire sauce", "molasses", "corn syrup", "caramel color", "tamarind", "natural fruit flavor", "milk", "soybean",
                "fish", "anchovy"
        };
        restaurantToFirebaseH(restRef, "Salads", "Newman's Own Low Fat Family Recipe Italian Dressing", ingredients);

        ingredients = new String[]{
                "water", "soybean oil", "corn syrup solids", "buttermilk", "distilled vinegar", "egg yolks", "salt", "sugar", "corn starch", "chives", "garlic powder",
                "onion powder", "natural dairy flavor", "natural animal flavor", "lactic acid", "parsley", "xanthan gum", "lemon juice concentrate", "milk", "egg"
        };
        restaurantToFirebaseH(restRef, "Salads", "Newman's Own Ranch Dressing", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water",
                "yeast", "high fructose corn syrup", "sugar", "wheat gluten", "soybean oil", "canola oil", "salt", "calcium sulfate", "calcium carbonate", "citric acid",
                "calcium citrate", "yellow corn flour", "corn meal", "rice flour", "barley malt", "artificial flavors", "natural botanical flavor", "dough conditioners",
                "ascorbic acid", "azodicarbonamide", "datem", "tricalcium phosphate", "monocalcium phosphate", "enzymes", "calcium peroxide", "calcium propionate",
                "potassium sorbate", "soy lecithin", "wheat", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil",
                "monoglycerides", "diglycerides", "sodium benzoate", "artificial flavor", "palmitate", "beta carotene", "eggs", "hydrogenated soybean oil", "milk",
                "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid", "acetic acid", "pork", "sodium lactate",
                "natural vegetable flavor", "sodium diacetate", "sodium nitrite"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Egg McMuffin", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water",
                "yeast", "high fructose corn syrup", "sugar", "wheat gluten", "soybean oil", "canola oil", "salt", "calcium sulfate", "calcium carbonate", "citric acid",
                "calcium citrate", "yellow corn flour", "corn meal", "rice flour", "barley malt", "artificial flavors", "natural botanical flavor", "dough conditioners",
                "ascorbic acid", "azodicarbonamide", "datem", "tricalcium phosphate", "monocalcium phosphate", "enzymes", "calcium peroxide", "calcium propionate",
                "potassium sorbate", "soy lecithin", "wheat", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil",
                "monoglycerides", "diglycerides", "sodium benzoate", "artificial flavor", "palmitate", "beta carotene", "pork", "whey protein concentrate",
                "corn syrup solids", "spices", "dextrose", "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "milk", "milkfat", "cheese culture",
                "sodium citrate", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid", "acetic acid"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage McMuffin", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water",
                "yeast", "high fructose corn syrup", "sugar", "wheat gluten", "soybean oil", "canola oil", "salt", "calcium sulfate", "calcium carbonate", "citric acid",
                "calcium citrate", "yellow corn flour", "corn meal", "rice flour", "barley malt", "artificial flavors", "natural botanical flavor", "dough conditioners",
                "ascorbic acid", "azodicarbonamide", "datem", "tricalcium phosphate", "monocalcium phosphate", "enzymes", "calcium peroxide", "calcium propionate",
                "potassium sorbate", "soy lecithin", "wheat", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil",
                "monoglycerides", "diglycerides", "sodium benzoate", "artificial flavor", "palmitate", "beta carotene", "pork", "whey protein concentrate",
                "corn syrup solids", "spices", "dextrose", "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "milk", "eggs", "hydrogenated soybean oil",
                "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid", "acetic acid"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage McMuffin with Egg", ingredients);

        ingredients = new String[]{
                "pork bellies", "water", "salt", "sugar", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "enriched flour",
                "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk", "vegetable oil",
                "palm oil", "palm kernel", "oil", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "modified cellulose",
                "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils",
                "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid",
                "palmitate", "beta carotene", "pasteurized whole eggs", "food starch-modified", "soybean oil", "sodium acid pyrophosphate", "carrageenan", "flavor enhancer",
                "maltodextrin", "spices", "herb", "turmeric", "monosodium phosphate", "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "artificial color",
                "lactic acid", "acetic acid", "enzymes"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Bacon, Egg & Cheese Biscuit (Regular Size Biscuit)", ingredients);

        ingredients = new String[]{
                "pork bellies", "water", "salt", "sugar", "natural plant flavor", "sodium phosphate", "sodium erythorbate", "sodium nitrite", "enriched flour",
                "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk", "vegetable oil",
                "palm oil", "palm kernel", "oil", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "modified cellulose",
                "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils",
                "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid",
                "palmitate", "beta carotene", "pasteurized whole eggs", "food starch-modified", "soybean oil", "sodium acid pyrophosphate", "carrageenan", "flavor enhancer",
                "maltodextrin", "spices", "herb", "turmeric", "monosodium phosphate", "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "artificial color",
                "lactic acid", "acetic acid", "enzymes"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Bacon, Egg & Cheese Biscuit (Large Size Biscuit)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk",
                "vegetable oil", "palm oil", "palm kernel", "oil", "water", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "salt",
                "sugar", "modified cellulose", "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil",
                "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate",
                "artificial flavor", "citric acid", "palmitate", "beta carotene", "pork", "whey protein concentrate", "corn syrup solids", "spices", "dextrose",
                "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "pasteurized whole eggs", "food starch-modified", "soybean oil",
                "sodium acid pyrophosphate", "carrageenan", "flavor enhancer", "maltodextrin", "natural plant flavor", "herb", "turmeric", "monosodium phosphate"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage Biscuit with Egg (Regular Size Biscuit)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk",
                "vegetable oil", "palm oil", "palm kernel", "oil", "water", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "salt",
                "sugar", "modified cellulose", "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil",
                "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate",
                "artificial flavor", "citric acid", "palmitate", "beta carotene", "pork", "whey protein concentrate", "corn syrup solids", "spices", "dextrose",
                "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "pasteurized whole eggs", "food starch-modified", "soybean oil",
                "sodium acid pyrophosphate", "carrageenan", "flavor enhancer", "maltodextrin", "natural plant flavor", "herb", "turmeric", "monosodium phosphate"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage Biscuit with Egg (Large Size Biscuit)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk",
                "vegetable oil", "palm oil", "palm kernel", "oil", "water", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "salt",
                "sugar", "modified cellulose", "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil",
                "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate",
                "artificial flavor", "citric acid", "palmitate", "beta carotene", "pork", "whey protein concentrate", "corn syrup solids", "spices", "dextrose",
                "spice extractives", "caramel color", "bha", "propyl gallate", "bht"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage Biscuit (Regular Size Biscuit)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk",
                "vegetable oil", "palm oil", "palm kernel", "oil", "water", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "salt",
                "sugar", "modified cellulose", "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil",
                "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate",
                "artificial flavor", "citric acid", "palmitate", "beta carotene", "pork", "whey protein concentrate", "corn syrup solids", "spices", "dextrose",
                "spice extractives", "caramel color", "bha", "propyl gallate", "bht"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage Biscuit (Large Size Biscuit)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk",
                "vegetable oil", "palm oil", "palm kernel", "oil", "water", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "salt",
                "sugar", "modified cellulose", "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil",
                "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate",
                "artificial flavor", "citric acid", "palmitate", "beta carotene", "chicken breast filets", "modified tapioca starch", "spice", "yeast extract",
                "sodium phosphates", "carrageenan", "maltodextrin", "natural plant flavor", "artificial flavors", "gum arabic", "sunflower lecithin", "wheat flour",
                "food starch-modified", "yellow corn flour", "sodium acid pyrophosphate", "wheat gluten", "extractives", "paprika", "canola oil", "corn oil", "soybean oil",
                "hydrogenated soybean oil", "tbhq", "dimethylpolysiloxane", "antifoaming agent"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Southern Style Chicken Biscuit (Regular Size Biscuit)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk",
                "vegetable oil", "palm oil", "palm kernel", "oil", "water", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "salt",
                "sugar", "modified cellulose", "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil",
                "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate",
                "artificial flavor", "citric acid", "palmitate", "beta carotene", "chicken breast filets", "modified tapioca starch", "spice", "yeast extract",
                "sodium phosphates", "carrageenan", "maltodextrin", "natural plant flavor", "artificial flavors", "gum arabic", "sunflower lecithin", "wheat flour",
                "food starch-modified", "yellow corn flour", "sodium acid pyrophosphate", "wheat gluten", "extractives", "paprika", "canola oil", "corn oil", "soybean oil",
                "hydrogenated soybean oil", "tbhq", "dimethylpolysiloxane", "antifoaming agent"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Southern Style Chicken Biscuit (Large Size Biscuit)", ingredients);

        ingredients = new String[]{
                "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "water", "partially hydrogenated soybean oil", "salt", "soy lecithin", "monoglycerides",
                "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene", "beef", "sodium phosphate", "bha",
                "tbhq", "pasteurized whole eggs", "food starch-modified", "soybean oil", "natural botanical flavor", "sodium acid pyrophosphate", "carrageenan",
                "flavor enhancer", "maltodextrin", "natural plant flavor", "spices", "herb", "turmeric", "monosodium phosphate", "enriched flour", "bleached wheat flour",
                "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "brown sugar", "sugar", "yeast", "rice flour", "oat fiber",
                "propylene glycol", "inactive yeast", "monocalcium phosphate", "corn starch", "sodium alginate", "dough conditioners", "ascorbic acid", "enzymes", "calcium",
                "potassium iodate", "azodicarbonamide", "solubilized wheat gluten", "modified tapioca starch", "wheat gluten", "polysorbate 60", "calcium propionate",
                "sodium metabisulfite", "soy flour", "wheat", "milk", "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "artificial color", "lactic acid",
                "acetic acid", "slivered onions", "garlic", "natural vegetable flavor", "egg yolk", "whole egg", "egg white",
                "distilled vinegar", "corn syrup solids", "lemon juice concentrate", "natural dairy flavor", "artificial flavors", "propylene glycol alginate",
                "calcium disodium edta", "dehydrated swiss cheese", "cheese cultures", "buttermilk powder", "xanthan gum", "disodium guanylate", "disodium inosinate",
                "eggs", "hydrogenated soybean oil"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Steak, Egg & Cheese Bagel", ingredients);

        ingredients = new String[]{
                "water", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamine mononitrate", "riboflavin", "folic acid",
                "sugar", "dextrose", "palm oil", "soybean oil", "brown sugar", "leavening", "sodium acid pyrophosphate", "baking soda", "monocalcium phosphate",
                "natural dairy flavor", "natural botanical flavor", "natural plant flavor", "artificial flavors", "rice flour", "soy flour", "whey powder", "salt",
                "modified tapioca starch", "buttermilk", "caramel color", "soy lecithin", "carnauba wax", "corn oil", "propylene glycol", "tbhq", "citric acid",
                "wheat", "milk", "soy", "pasteurized whole eggs", "food starch-modified", "carrageenan", "flavor enhancer", "maltodextrin", "spices", "herb",
                "turmeric", "monosodium phosphate", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil",
                "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "milkfat",
                "cheese culture", "sodium citrate", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid", "acetic acid", "enzymes", "pork bellies",
                "sodium erythorbate", "sodium nitrite"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Bacon, Egg & Cheese McGriddles", ingredients);

        ingredients = new String[]{
                "water", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamine mononitrate", "riboflavin",
                "folic acid", "sugar", "dextrose", "palm oil", "soybean oil", "brown sugar", "leavening", "sodium acid pyrophosphate", "baking soda",
                "monocalcium phosphate", "natural dairy flavor", "natural botanical flavor", "natural plant flavor", "artificial flavors", "rice flour",
                "soy flour", "whey powder", "salt", "modified tapioca starch", "buttermilk", "caramel color", "soy lecithin", "carnauba wax", "corn oil",
                "propylene glycol", "tbhq", "citric acid", "wheat", "milk", "soy", "pork", "whey protein concentrate", "corn syrup solids", "spices",
                "spice extractives", "bha", "propyl gallate", "bht", "pasteurized whole eggs", "food starch-modified", "carrageenan", "flavor enhancer",
                "maltodextrin", "herb", "turmeric", "monosodium phosphate", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils",
                "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate", "artificial flavor", "palmitate",
                "beta carotene", "milkfat", "cheese culture", "sodium citrate", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid", "acetic acid", "enzymes"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage, Egg & Cheese McGriddles", ingredients);

        ingredients = new String[]{
                "water", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamine mononitrate", "riboflavin", "folic acid",
                "sugar", "dextrose", "palm oil", "soybean oil", "brown sugar", "leavening", "sodium acid pyrophosphate", "baking soda", "monocalcium phosphate",
                "natural dairy flavor", "natural botanical flavor", "natural plant flavor", "artificial flavors", "rice flour", "soy flour", "whey powder", "salt",
                "modified tapioca starch", "buttermilk", "caramel color", "soy lecithin", "carnauba wax", "corn oil", "propylene glycol", "tbhq", "citric acid",
                "wheat", "milk", "soy", "pork", "whey protein concentrate", "corn syrup solids", "spices", "spice extractives", "bha", "propyl gallate", "bht"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage McGriddles", ingredients);

        ingredients = new String[]{
                "pasteurized whole eggs", "sodium phosphate", "citric acid", "monosodium phosphate", "nisin preparation", "liquid soybean oil", "hydrogenated cottonseed",
                "soybean oils", "water", "partially hydrogenated soybean oil", "salt", "soy lecithin", "monoglycerides", "diglycerides", "sodium benzoate",
                "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "hydrogenated soybean oil", "egg", "pork", "whey protein concentrate",
                "corn syrup solids", "spices", "dextrose", "sugar", "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "milk", "enriched flour",
                "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk", "vegetable oil",
                "palm oil", "palm kernel", "oil", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "modified cellulose",
                "wheat protein isolate", "natural botanical flavor", "wheat", "potatoes", "canola oil", "natural beef flavor", "corn flour", "dehydrated potato",
                "sodium acid pyrophosphate", "black pepper", "corn oil", "soybean oil", "tbhq", "dimethylpolysiloxane", "hydrolyzed wheat", "hydrolyzed milk"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Big Breakfast (Regular Size Biscuit)", ingredients);

        ingredients = new String[]{
                "pasteurized whole eggs", "sodium phosphate", "citric acid", "monosodium phosphate", "nisin preparation", "liquid soybean oil", "hydrogenated cottonseed",
                "soybean oils", "water", "partially hydrogenated soybean oil", "salt", "soy lecithin", "monoglycerides", "diglycerides", "sodium benzoate",
                "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "hydrogenated soybean oil", "egg", "pork", "whey protein concentrate",
                "corn syrup solids", "spices", "dextrose", "sugar", "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "milk", "enriched flour",
                "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk", "vegetable oil",
                "palm oil", "palm kernel", "oil", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "modified cellulose",
                "wheat protein isolate", "natural botanical flavor", "wheat", "potatoes", "canola oil", "natural beef flavor", "corn flour", "dehydrated potato",
                "sodium acid pyrophosphate", "black pepper", "corn oil", "soybean oil", "tbhq", "dimethylpolysiloxane", "hydrolyzed wheat", "hydrolyzed milk"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Big Breakfast (Large Size Biscuit)", ingredients);

        ingredients = new String[]{
                "pasteurized whole eggs", "sodium phosphate", "citric acid", "monosodium phosphate", "nisin preparation", "liquid soybean oil", "hydrogenated cottonseed",
                "soybean oils", "water", "partially hydrogenated soybean oil", "salt", "soy lecithin", "monoglycerides", "diglycerides", "sodium benzoate",
                "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "hydrogenated soybean oil", "egg", "pork", "whey protein concentrate",
                "corn syrup solids", "spices", "dextrose", "sugar", "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "milk", "enriched flour",
                "wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "whey powder", "yellow corn flour", "soybean oil",
                "whole eggs", "high fructose corn syrup", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "egg whites",
                "glycerol-oleate", "emulsifier blend", "distilled monoglycerides", "distilled propylene glycol monoester", "sodium stearoyl lactylate", "xanthan gum",
                "tbhq", "wheat", "bleached wheat flour", "cultured nonfat buttermilk", "vegetable oil", "palm oil", "palm kernel", "oil", "modified cellulose",
                "wheat protein isolate", "natural botanical flavor", "potatoes", "canola oil", "natural beef flavor", "corn flour", "dehydrated potato",
                "sodium acid pyrophosphate", "black pepper", "corn oil", "dimethylpolysiloxane", "hydrolyzed wheat", "hydrolyzed milk"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Big Breakfast with Hotcakes (Regular Size Biscuit)", ingredients);

        ingredients = new String[]{
                "pasteurized whole eggs", "sodium phosphate", "citric acid", "monosodium phosphate", "nisin preparation", "liquid soybean oil", "hydrogenated cottonseed",
                "soybean oils", "water", "partially hydrogenated soybean oil", "salt", "soy lecithin", "monoglycerides", "diglycerides", "sodium benzoate",
                "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "hydrogenated soybean oil", "egg", "pork", "whey protein concentrate",
                "corn syrup solids", "spices", "dextrose", "sugar", "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "milk", "enriched flour",
                "wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "whey powder", "yellow corn flour", "soybean oil",
                "whole eggs", "high fructose corn syrup", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "egg whites",
                "glycerol-oleate", "emulsifier blend", "distilled monoglycerides", "distilled propylene glycol monoester", "sodium stearoyl lactylate", "xanthan gum",
                "tbhq", "wheat", "bleached wheat flour", "cultured nonfat buttermilk", "vegetable oil", "palm oil", "palm kernel", "oil", "modified cellulose",
                "wheat protein isolate", "natural botanical flavor", "potatoes", "canola oil", "natural beef flavor", "corn flour", "dehydrated potato",
                "sodium acid pyrophosphate", "black pepper", "corn oil", "dimethylpolysiloxane", "hydrolyzed wheat", "hydrolyzed milk"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Big Breakfast with Hotcakes (Large Size Biscuit)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water",
                "vegetable shortening", "hydrogenated soybean oil", "soybean oil", "partially hydrogenated soybean oil", "hydrogenated cottonseed oil",
                "monoglycerides", "diglycerides", "sugar", "leavening", "sodium aluminum sulfate", "calcium sulfate", "sodium phosphate", "baking soda",
                "corn starch", "monocalcium phosphate", "salt", "wheat gluten", "dough conditioners", "sodium metabisulfite", "distilled monoglycerides", "wheat",
                "eggs", "nonfat dry milk", "food starch-modified", "natural plant flavor", "black pepper", "xanthan gum", "citric acid", "natural dairy flavor",
                "natural botanical flavor", "artificial flavor", "annatto", "sausage", "pork", "dextrose", "spices", "corn syrup solids", "monosodium glutamate",
                "bha", "propyl gallate", "vegetable blend", "tomatoes", "green chilies", "onions", "calcium chloride", "egg", "milk", "milkfat", "cheese culture",
                "sodium citrate", "sorbic acid", "artificial color", "lactic acid", "acetic acid", "enzymes", "soy lecithin"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Sausage Burrito", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water",
                "vegetable shortening", "hydrogenated soybean oil", "soybean oil", "partially hydrogenated soybean oil", "hydrogenated cottonseed oil", "monoglycerides",
                "diglycerides", "sugar", "leavening", "sodium aluminum sulfate", "calcium sulfate", "sodium phosphate", "baking soda", "corn starch", "monocalcium phosphate",
                "salt", "wheat gluten", "dough conditioners", "sodium metabisulfite", "distilled monoglycerides", "wheat", "pasteurized whole eggs", "citric acid",
                "monosodium phosphate", "nisin preparation", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils", "soy lecithin", "sodium benzoate",
                "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "egg", "pork", "whey protein concentrate", "corn syrup solids", "spices",
                "dextrose", "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "milk", "potatoes", "onions", "green peppers", "red peppers",
                "partially hydrogenated vegetable oil", "soybean", "cottonseeed", "vegetable oil", "canola", "corn", "cottonseed", "palm", "sunflower oil",
                "dehydrated bell pepper", "disodium dihydrogen pyrophosphate", "disodium inosinate", "disodium guanylate", "garlic powder", "maltodextrin",
                "food starch-modified", "natural fruit flavor", "natural vegetable flavor", "onion powder", "paprika", "milkfat", "cheese culture", "sodium citrate",
                "sorbic acid", "artificial color", "lactic acid", "acetic acid", "enzymes", "cheddar cheese", "pasteurized milk", "annatto", "monterey jack cheese",
                "potato starch", "powdered cellulose", "natamycin", "tomatoes", "tomato juice", "calcium chloride", "fire roasted tomatoes", "green jalapeno pepper puree",
                "onion", "red anaheim", "pepper puree", "garlic", "distilled vinegar", "cilantro", "lime juice concentrate", "sodium acid sulfate", "xanthan gum"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "McSkillet Burrito with Sausage", ingredients);

        ingredients = new String[]{
                "water", "enriched flour", "wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "whey powder", "yellow corn flour",
                "soybean oil", "whole eggs", "sugar", "high fructose corn syrup", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate",
                "dextrose", "egg whites", "glycerol-oleate", "emulsifier blend", "distilled monoglycerides", "distilled propylene glycol monoester",
                "sodium stearoyl lactylate", "soy lecithin", "salt", "artificial flavor", "xanthan gum", "beta carotene", "tbhq", "wheat", "milk", "egg", "pork",
                "whey protein concentrate", "corn syrup solids", "spices", "spice extractives", "caramel color", "bha", "propyl gallate", "bht", "citric acid"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Hotcakes and Sausage", ingredients);

        ingredients = new String[]{
                "corn syrup", "sugar", "water", "artificial maple flavor", "potassium sorbate", "caramel color"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Hotcake Syrup", ingredients);

        ingredients = new String[]{
                "soybean oil", "palm oil", "palm kernel oil", "partially hydrogenated soybean oil", "whey", "salt", "hydrogenated cottonseed oil",
                "vegetable monoglycerides", "diglycerides", "soy lecithin", "sodium benzoate", "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "milk"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Whipped Margarine (1 pat)", ingredients);

        ingredients = new String[]{"concord grape puree", "corn syrup", "sugar", "grape juice concentrate", "fruit pectin", "malic acid", "sodium citrate"};
        restaurantToFirebaseH(restRef, "Breakfast", "Grape Jam", ingredients);

        ingredients = new String[]{"sugar", "strawberries", "strawberry puree concentrate", "fruit pectin", "citric acid", "sodium citrate"};
        restaurantToFirebaseH(restRef, "Breakfast", "Strawberry Preserves", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid",
                "water", "brown sugar", "sugar", "salt", "yeast", "rice flour", "oat fiber", "propylene glycol", "inactive yeast", "monocalcium phosphate",
                "food starch-modified", "corn starch", "sodium alginate", "dough conditioners", "ascorbic acid", "monoglycerides", "diglycerides", "enzymes",
                "calcium", "potassium iodate", "azodicarbonamide", "solubilized wheat gluten", "modified tapioca starch", "wheat gluten", "polysorbate 60", "soy lecithin",
                "calcium propionate", "sodium metabisulfite", "soy flour", "liquid soybean oil", "hydrogenated cottonseed", "soybean oils",
                "partially hydrogenated soybean oil", "sodium benzoate", "potassium sorbate", "artificial flavor", "citric acid", "palmitate", "beta carotene", "wheat",
                "pasteurized whole eggs", "soybean oil", "natural botanical flavor", "sodium acid pyrophosphate", "carrageenan", "flavor enhancer", "maltodextrin",
                "natural plant flavor", "spices", "herb", "turmeric", "monosodium phosphate", "milk", "milkfat", "cheese culture", "sodium citrate", "sorbic acid",
                "sodium phosphate", "artificial color", "lactic acid", "acetic acid", "pork bellies", "sodium erythorbate", "sodium nitrite", "egg yolk", "whole egg",
                "egg white", "distilled vinegar", "corn syrup solids", "lemon juice concentrate", "natural dairy flavor", "natural vegetable flavor", "artificial flavors",
                "propylene glycol alginate", "calcium disodium edta", "dehydrated swiss cheese", "cheese cultures", "buttermilk powder", "xanthan gum", "disodium guanylate",
                "disodium inosinate", "eggs", "hydrogenated soybean oil"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Bacon, Egg & Cheese Bagel", ingredients);

        ingredients = new String[]{
                "whole grain rolled oats", "brown sugar", "food starch-modified", "salt", "natural plant flavor", "barley malt extract", "caramel color", "apples",
                "calcium ascorbate", "milk", "cream", "sodium phosphate", "datem", "sodium stearoyl lactylate", "sodium citrate", "carrageenan", "cranberries", "sugar",
                "california raisins", "golden raisins", "sunflower oil", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Fruit & Maple Oatmeal", ingredients);

        ingredients = new String[]{
                "whole grain rolled oats", "food starch-modified", "salt", "natural plant flavor", "barley malt extract", "caramel color", "apples", "calcium ascorbate",
                "milk", "cream", "sodium phosphate", "datem", "sodium stearoyl lactylate", "sodium citrate", "carrageenan", "cranberries", "sugar", "california raisins",
                "golden raisins", "sunflower oil", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Fruit & Maple Oatmeal without Brown Sugar", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk",
                "vegetable oil", "palm oil", "palm kernel", "oil", "water", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "salt",
                "sugar", "modified cellulose", "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil",
                "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate",
                "artificial flavor", "citric acid", "palmitate", "beta carotene"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Biscuit (Large)", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "cultured nonfat buttermilk",
                "vegetable oil", "palm oil", "palm kernel", "oil", "water", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate", "salt",
                "sugar", "modified cellulose", "wheat protein isolate", "natural botanical flavor", "soy lecithin", "wheat", "milk", "liquid soybean oil",
                "hydrogenated cottonseed", "soybean oils", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "sodium benzoate", "potassium sorbate",
                "artificial flavor", "citric acid", "palmitate", "beta carotene"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Biscuit (Regular)", ingredients);

        ingredients = new String[]{
                "water", "enriched flour", "wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "whey powder", "yellow corn flour",
                "soybean oil", "whole eggs", "sugar", "high fructose corn syrup", "leavening", "baking soda", "sodium aluminum phosphate", "monocalcium phosphate",
                "dextrose", "egg whites", "glycerol-oleate", "emulsifier blend", "distilled monoglycerides", "distilled propylene glycol monoester",
                "sodium stearoyl lactylate", "soy lecithin", "salt", "artificial flavor", "xanthan gum", "beta carotene", "tbhq", "wheat", "milk", "egg"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Hotcakes (3)", ingredients);

        ingredients = new String[]{"milk", "cream", "sodium phosphate", "datem", "sodium stearoyl lactylate", "sodium citrate", "carrageenan"};
        restaurantToFirebaseH(restRef, "Breakfast", "Light Cream", ingredients);

        ingredients = new String[]{"whole grain rolled oats", "brown sugar", "food starch-modified", "salt", "natural plant flavor", "barley malt extract", "caramel color"};
        restaurantToFirebaseH(restRef, "Breakfast", "Oatmeal", ingredients);

        ingredients = new String[]{"whole grain rolled oats", "food starch-modified", "maltodextrin", "natural plant flavor", "barley malt extract", "caramel color"};
        restaurantToFirebaseH(restRef, "Breakfast", "Oatmeal without Brown Sugar", ingredients);

        ingredients = new String[]{
                "potatoes", "onions", "green peppers", "red peppers", "partially hydrogenated vegetable oil", "soybean", "cottonseeed", "vegetable oil", "canola",
                "corn", "cottonseed", "palm", "soybean oil", "sunflower oil", "corn syrup solids", "dehydrated bell pepper", "dextrose", "disodium dihydrogen pyrophosphate",
                "disodium inosinate", "disodium guanylate", "garlic powder", "maltodextrin", "food starch-modified", "natural fruit flavor", "natural vegetable flavor",
                "onion powder", "paprika", "salt", "spices"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Potato Vegetable Blend", ingredients);

        ingredients = new String[]{
                "tomatoes", "tomato juice", "citric acid", "calcium chloride", "water", "fire roasted tomatoes", "green jalapeno pepper puree", "onion", "red anaheim",
                "pepper puree", "garlic", "distilled vinegar", "cilantro", "salt", "lime juice concentrate", "food starch-modified", "paprika", "spices", "soybean oil",
                "sodium acid sulfate", "sodium benzoate", "potassium sorbate", "xanthan gum"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Salsa Roja", ingredients);

        ingredients = new String[]{
                "pasteurized whole eggs", "sodium phosphate", "citric acid", "monosodium phosphate", "nisin preparation", "liquid soybean oil", "hydrogenated cottonseed",
                "soybean oils", "water", "partially hydrogenated soybean oil", "salt", "soy lecithin", "monoglycerides", "diglycerides", "sodium benzoate",
                "potassium sorbate", "artificial flavor", "palmitate", "beta carotene", "hydrogenated soybean oil", "egg"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Scrambled Eggs", ingredients);

        ingredients = new String[]{
                "vanilla lowfat yogurt", "cultured pasteurized grade a reduced fat milk", "sugar", "food starch-modified", "fructose", "whey protein concentrate",
                "corn starch", "gelatin", "natural plant flavor", "artificial flavor", "potassium sorbate", "artificial color", "milk"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Fruit n Yogurt Parfait (7 oz)", ingredients);

        ingredients = new String[]{
                "whole grain rolled oats", "brown sugar", "crisp rice", "rice flour", "barley malt extract", "salt", "dried high maltose corn syrup", "honey",
                "sunflower oil", "baking", "soda", "sodium aluminum phosphate", "apple puree concentrate", "soy lecithin", "cinnamon", "crushed oranges",
                "natural vegetable flavor"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Low Fat Granola", ingredients);

        ingredients = new String[]{
                "corn syrup", "sweetened condensed milk", "milk", "sugar", "high fructose corn syrup", "water", "butter", "cream", "salt", "disodium phosphate",
                "artificial flavors", "vanillin", "ethyl vanillin", "caramel color", "pectin", "potassium sorbate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Low Fat Caramel Dip", ingredients);

        ingredients = new String[]{
                "enriched flour", "wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "tapioca starch", "sugar", "shortening",
                "soybean oil", "palm oil", "soy lecithin", "emulsifier", "leavening", "baking soda", "ammonium bicarbonate", "salt", "natural plant flavor", "annatto",
                "caramel color", "corn syrup", "wheat", "milk", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum",
                "dextrose", "sodium citrate", "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Vanilla Reduced Fat Ice Cream Cone", ingredients);

        ingredients = new String[]{
                "enriched flour", "wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "tapioca starch", "sugar", "shortening",
                "soybean oil", "palm oil", "soy lecithin", "emulsifier", "leavening", "baking soda", "ammonium bicarbonate", "salt", "natural plant flavor", "annatto",
                "caramel color", "corn syrup", "wheat", "milk", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum",
                "dextrose", "sodium citrate", "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Kiddie Cone", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Strawberry Sundae", ingredients);

        ingredients = new String[]{
                "strawberries", "sugar", "water", "high fructose corn syrup", "natural strawberry flavor", "natural fruit flavors", "citric acid", "pectin",
                "sodium benzoate", "carob bean gum", "red 40", "calcium chloride"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Strawberry Topping", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Hot Caramel Sundae", ingredients);

        ingredients = new String[]{
                "corn syrup", "sweetened condensed milk", "milk", "sugar", "high fructose corn syrup", "butter", "cream", "salt", "water", "disodium phosphate",
                "pectin", "potassium sorbate", "artificial flavors", "vanillin", "ethyl vanillin"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Hot Caramel Topping", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Hot Fudge Sundae", ingredients);

        ingredients = new String[]{
                "sugar", "water", "sweetened condensed skim milk", "milk", "partially hydrogenated palm kernel oil", "nonfat dry milk", "cocoa", "alkali", "corn syrup",
                "salt", "disodium phosphate", "potassium sorbate", "artificial flavor", "vanillin", "soy lecithin", "polyglycerol esters", "fatty acids"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Hot Fudge Topping", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "milk chocolate", "chocolate",
                "skim milk", "cocoa butter", "lactose", "milkfat", "soy lecithin", "salt", "artificial flavors", "corn starch", "corn syrup", "yellow 5 lake",
                "red 40 lake", "blue 1 lake", "yellow 6 lake", "blue 2 lake", "yellow 5", "red 40", "blue 1", "yellow 6", "blue 2", "dextrin", "peanuts"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "McFlurry with M&M'S Candies", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "enriched flour", "wheat flour",
                "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "palm", "high oleic canola", "soybean oil", "cocoa", "alkali",
                "high fructose corn syrup", "baking soda", "corn starch", "salt", "soy lecithin", "emulsifier", "vanillin", "artificial flavor", "chocolate", "wheat"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "McFlurry with OREO Cookies", ingredients);

        ingredients = new String[]{
                "apples", "citric acid", "ascorbic acid", "salt", "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin",
                "folic acid", "water", "high fructose corn syrup", "sugar", "shortening", "palm oil", "soy lecithin", "artificial flavor", "beta carotene", "food starch",
                "sorbitol", "palm kernel oil", "dextrose", "brown sugar", "apple powder", "dehydrated apples", "sodium alginate", "dicalcium phosphate", "sodium citrate",
                "spices", "yeast", "lcysteine", "natural plant flavors", "artificial flavors", "annatto", "turmeric", "caramel color", "wheat"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Baked Hot Apple Pie", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "water",
                "sugar", "margarine", "palm oil", "soybean oil", "salt", "whey", "partially hydrogenated soybean oil", "monoglycerides", "diglycerides", "soy lecithin",
                "sodium benzoate", "lactic acid", "artificial flavor", "beta carotene", "palmitate", "brown sugar", "shortening", "eggs", "cream cheese powder",
                "cream cheese", "pasteurized milk", "cream", "cheese cultures", "carob bean gum", "nonfat dry milk", "disodium phosphate", "natural vegetable flavor",
                "cinnamon", "yeast", "natural plant flavor", "natural dairy flavor", "artificial flavors", "sodium alginate", "sodium stearoyl lactylate", "polysorbate 60",
                "titanium dioxide", "potassium sorbate", "enzymes", "pectin", "wheat", "egg", "milk"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Cinnamon Melts", ingredients);

        ingredients = new String[]{
                "enriched flour", "wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "sugar", "vegetable oil", "soybean oil",
                "palm oil", "tbhq", "citric acid", "high fructose corn syrup", "salt", "leavening", "baking soda", "sodium acid pyrophosphate", "monocalcium phosphate",
                "soy lecithin", "natural fruit flavor", "wheat"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "McDonaldland Cookies", ingredients);

        ingredients = new String[]{
                "semi-sweet chocolate chips", "sugar", "chocolate liquor", "cocoa butter", "milkfat", "soy lecithin", "emulsifier", "artificial flavor", "vanilla",
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "iron", "thiamin mononitrate", "riboflavin", "folic acid", "margarine",
                "palm oil", "water", "soybean oil", "salt", "natural dairy flavor", "whey", "monoglycerides", "diglycerides", "sodium benzoate", "beta carotene",
                "palmitate", "brown sugar", "eggs", "vanillin", "ethyl vanillin", "caramel color", "leavening", "baking soda", "sodium acid pyrophosphate", "corn starch",
                "monocalcium phosphate", "wheat", "milk", "egg"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Chocolate Chip Cookie", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "iron", "thiamin mononitrate", "riboflavin", "folic acid", "sugar", "margarine",
                "palm oil", "soybean oil", "water", "salt", "natural flavor", "whey", "monoglycerides", "diglycerides", "sodium benzoate", "soy lecithin",
                "artificial flavor", "beta carotene", "palmitate", "rolled oats", "brown sugar", "raisins", "eggs", "corn flakes", "milled corn", "malt flavoring",
                "high fructose corn syrup", "reduced iron", "thiamin hydrochloride", "niacinamide", "pyridoxine hydrochloride", "calcium pantothenate", "sweetened coconut",
                "desiccated coconut", "propylene glycol", "sodium metabisulfite", "vanillin", "ethyl vanillin", "caramel color", "leavening", "baking soda",
                "sodium acid pyrophosphate", "corn starch", "monocalcium phosphate", "wheat", "milk", "egg", "tree nuts", "coconut"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Oatmeal Raisin Cookie", ingredients);

        ingredients = new String[]{
                "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "iron", "thiamin mononitrate", "riboflavin", "folic acid", "sugar",
                "margarine", "palm oil", "water", "soybean oil", "salt", "natural flavor", "whey", "monoglycerides", "diglycerides", "soy lecithin", "beta carotene",
                "palmitate", "eggs", "artificial flavor", "vanillin", "ethyl vanillin", "caramel color", "leavening", "baking soda", "sodium acid pyrophosphate",
                "calcium sulfate", "monocalcium phosphate", "fumaric acid", "corn starch", "wheat", "milk", "egg"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Sugar Cookie", ingredients);

        ingredients = new String[]{
                "apples", "calcium ascorbate", "corn syrup", "sweetened condensed milk", "milk", "sugar", "high fructose corn syrup", "water", "butter", "cream", "salt",
                "disodium phosphate", "artificial flavors", "vanillin", "ethyl vanillin", "caramel color", "pectin", "potassium sorbate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Apple Dippers with Low Fat Caramel Dip", ingredients);

        ingredients = new String[]{
                "high fructose corn syrup", "corn syrup", "water", "cocoa", "alkali", "natural botanical flavors", "artificial flavors", "caramel color", "salt",
                "potassium sorbate", "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose",
                "sodium citrate", "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "artificial flavor",
                "vanillin", "red 40", "egg", "nonfat milk", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "cherries", "malic acid", "citric acid", "natural plant flavors", "sodium benzoate",
                "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Chocolate McCafe Shake (12 fl oz cup)", ingredients);

        ingredients = new String[]{
                "high fructose corn syrup", "corn syrup", "water", "cocoa", "alkali", "natural botanical flavors", "artificial flavors", "caramel color", "salt",
                "potassium sorbate", "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose",
                "sodium citrate", "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "artificial flavor",
                "vanillin", "red 40", "egg", "nonfat milk", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "cherries", "malic acid", "citric acid", "natural plant flavors", "sodium benzoate",
                "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Chocolate McCafe Shake (16 fl oz cup)", ingredients);

        ingredients = new String[]{
                "high fructose corn syrup", "corn syrup", "water", "cocoa", "alkali", "natural botanical flavors", "artificial flavors", "caramel color", "salt",
                "potassium sorbate", "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose",
                "sodium citrate", "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "artificial flavor",
                "vanillin", "red 40", "egg", "nonfat milk", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "cherries", "malic acid", "citric acid", "natural plant flavors", "sodium benzoate",
                "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Chocolate McCafe Shake (22 fl oz cup)", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Chocolate Triple Thick Shake", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "nonfat milk", "water", "corn syrup",
                "high fructose corn syrup", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "strawberries", "natural plant flavors", "artificial flavors", "pectin", "citric acid",
                "xanthan gum", "potassium sorbate", "caramel color",
                "calcium chloride", "red 40", "egg", "cherries", "malic acid", "sodium benzoate", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Strawberry McCafe Shake (12 fl oz cup)", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "nonfat milk", "water", "corn syrup",
                "high fructose corn syrup", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "strawberries", "natural plant flavors", "artificial flavors", "pectin", "citric acid",
                "xanthan gum", "potassium sorbate", "caramel color",
                "calcium chloride", "red 40", "egg", "cherries", "malic acid", "sodium benzoate", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Strawberry McCafe Shake (16 fl oz cup)", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "nonfat milk", "water", "corn syrup",
                "high fructose corn syrup", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "strawberries", "natural plant flavors", "artificial flavors", "pectin", "citric acid",
                "xanthan gum", "potassium sorbate", "caramel color",
                "calcium chloride", "red 40", "egg", "cherries", "malic acid", "sodium benzoate", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Strawberry McCafe Shake (22 fl oz cup)", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Strawberry Triple Thick Shake", ingredients);

        ingredients = new String[]{
                "sugar", "water", "corn syrup", "strawberries", "high fructose corn syrup", "natural botanical flavor", "artificial flavors", "pectin", "citric acid",
                "xanthan gum", "potassium sorbate", "caramel color", "calcium chloride", "red 40", "egg"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Strawberry Syrup", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "corn syrup", "water",
                "vanilla extract", "caramel color", "citric acid", "pectin", "sodium benzoate", "yellow 5", "yellow 6", "egg", "nonfat milk", "high fructose corn syrup",
                "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor", "mixed tocopherols",
                "whipping propellant", "nitrous oxide", "cherries", "malic acid", "natural plant flavors", "artificial flavors", "potassium sorbate", "red 40", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Vanilla McCafe Shake (12 fl oz cup)", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "corn syrup", "water",
                "vanilla extract", "caramel color", "citric acid", "pectin", "sodium benzoate", "yellow 5", "yellow 6", "egg", "nonfat milk", "high fructose corn syrup",
                "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor", "mixed tocopherols",
                "whipping propellant", "nitrous oxide", "cherries", "malic acid", "natural plant flavors", "artificial flavors", "potassium sorbate", "red 40", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Vanilla McCafe Shake (16 fl oz cup)", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate", "corn syrup", "water",
                "vanilla extract", "caramel color", "citric acid", "pectin", "sodium benzoate", "yellow 5", "yellow 6", "egg", "nonfat milk", "high fructose corn syrup",
                "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor", "mixed tocopherols",
                "whipping propellant", "nitrous oxide", "cherries", "malic acid", "natural plant flavors", "artificial flavors", "potassium sorbate", "red 40", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Vanilla McCafe Shake (22 fl oz cup)", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Vanilla Triple Thick Shake", ingredients);

        ingredients = new String[]{"corn syrup", "water", "vanilla extract", "caramel color", "citric acid", "pectin", "sodium benzoate", "yellow 5", "yellow 6", "egg"};
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Triple Thick Vanilla Shake Syrup", ingredients);

        ingredients = new String[]{"apples", "calcium ascorbate"};
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Apple Dippers", ingredients);

        ingredients = new String[]{
                "high fructose corn syrup", "corn syrup", "water", "cocoa", "alkali", "natural botanical flavors", "artificial flavors", "caramel color", "salt",
                "potassium sorbate", "artificial flavor", "vanillin", "red 40", "egg"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Chocolate Shake Syrup", ingredients);

        ingredients = new String[]{
                "enriched flour", "wheat flour", "niacin", "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "tapioca starch", "sugar", "shortening",
                "soybean oil", "palm oil", "soy lecithin", "emulsifier", "leavening", "baking soda", "ammonium bicarbonate", "salt", "natural plant flavor", "annatto",
                "caramel color", "corn syrup", "wheat"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Ice Cream Cone", ingredients);

        ingredients = new String[]{
                "cherries", "water", "corn syrup", "high fructose corn syrup", "sugar", "malic acid", "citric acid", "natural plant flavors", "artificial flavors",
                "sodium benzoate", "potassium sorbate", "red 40", "sulfur dioxide"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Maraschino Cherry", ingredients);

        ingredients = new String[]{
                "sugar", "water", "corn syrup", "strawberries", "high fructose corn syrup", "natural plant flavors", "artificial flavors", "pectin", "citric acid", "xanthan gum", "potassium sorbate", "caramel color",
                "calcium chloride", "red 40", "egg"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Strawberry Shake Syrup", ingredients);

        ingredients = new String[]{
                "milk", "sugar", "cream", "nonfat milk solids", "corn syrup solids", "monoglycerides", "diglycerides", "guar gum", "dextrose", "sodium citrate",
                "artificial vanilla flavor", "sodium phosphate", "carrageenan", "disodium phosphate", "cellulose gum", "palmitate"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Vanilla Reduced Fat Ice Cream", ingredients);

        ingredients = new String[]{
                "corn syrup", "water", "vanilla extract", "caramel color", "citric acid", "pectin", "sodium benzoate", "yellow 5", "yellow 6", "egg"
        };
        restaurantToFirebaseH(restRef, "Sweets & Treats", "Vanilla Shake Syrup", ingredients);

        ingredients = new String[]{"artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk", "low fat milk", "palmitate"};
        restaurantToFirebaseH(restRef, "Beverages", "1% Low Fat Milk Jug", ingredients);

        ingredients = new String[]{"low fat milk", "high fructose corn syrup", "sugar", "cocoa", "alkali", "carrageenan", "salt", "artificial flavor", "palmitate"};
        restaurantToFirebaseH(restRef, "Beverages", "1% Low Fat Chocolate Milk Jug", ingredients);

        ingredients = new String[]{"filtered water", "concentrated apple juice", "calcium citrate", "ascorbic acid"};
        restaurantToFirebaseH(restRef, "Beverages", "Minute Maid Apple Juice Box", ingredients);

        ingredients = new String[]{"purified water", "magnesium sulfate", "potassium chloride", "salt", "sodium"};
        restaurantToFirebaseH(restRef, "Beverages", "Dasani Water", ingredients);

        ingredients = new String[]{"orange juice", "filtered water", "concentrated orange juice"};
        restaurantToFirebaseH(restRef, "Beverages", "Orange Juice", ingredients);

        ingredients = new String[]{"carbonated water", "high fructose corn syrup", "caramel color", "phosphoric acid", "natural vegetable flavors", "caffeine"};
        restaurantToFirebaseH(restRef, "Beverages", "Coca Cola Classic", ingredients);

        ingredients = new String[]{
                "carbonated water", "caramel color", "phosphoric acid", "sodium saccharin", "natural vegetable flavors", "citric acid", "potassium citrate",
                "potassium benzoate", "taste", "caffeine", "aspartame", "dimethylpolysiloxane", "antifoaming agent", "phenylketonurics", "phenylalanine"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Diet Coke", ingredients);

        ingredients = new String[]{
                "carbonated water", "high fructose corn syrup", "citric acid", "natural vegetable flavors", "sodium citrate", "sodium benzoate", "dimethylpolysiloxane",
                "antifoaming agent"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Sprite", ingredients);

        ingredients = new String[]{
                "water", "high fructose corn syrup", "citric acid", "ascorbic acid", "potassium benzoate", "modified food starch", "natural vegetable flavors",
                "glycerol ester of wood rosin", "yellow 6", "brominated vegetable oil", "red 40"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Hi-C Orange Lavaburst", ingredients);

        ingredients = new String[]{
                "water", "high fructose corn syrup", "citric acid", "natural vegetable flavors", "salt", "potassium citrate", "potassium benzoate", "potassium sorbate",
                "modified food starch", "coconut oil", "potassium phosphate", "sucrose acetate isobutyrate", "niacinamide", "blue 1", "pyridoxine hydrochloride",
                "cyanocobalamin"
        };
        restaurantToFirebaseH(restRef, "Beverages", "POWERade Mountain Blast", ingredients);

        ingredients = new String[]{"orange pekoe", "pekoe cut black tea"};
        restaurantToFirebaseH(restRef, "Beverages", "Iced Tea", ingredients);

        ingredients = new String[]{"brewed coffee"};
        restaurantToFirebaseH(restRef, "Coffees", "Coffee (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Coffee (Large)", ingredients);

        ingredients = new String[]{
                "premium roast coffee", "milk", "cream", "sodium phosphate", "datem", "sodium stearoyl lactylate", "sodium citrate", "carrageenan", "sugar", "water",
                "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color", "sulfites", "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Coffee: Caramel", ingredients);

        ingredients = new String[]{
                "premium roast coffee", "milk", "cream", "sodium phosphate", "datem", "sodium stearoyl lactylate", "sodium citrate", "carrageenan", "sugar", "water",
                "fructose", "natural plant flavor", "artificial flavors", "potassium sorbate", "citric acid", "caramel color", "sulfites", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Coffee: Hazelnut", ingredients);

        ingredients = new String[]{
                "premium roast coffee", "milk", "cream", "sodium phosphate", "datem", "sodium stearoyl lactylate", "sodium citrate", "carrageenan", "liquid sugar"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Coffee: Regular (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Coffee: Regular (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Coffee: Regular (Large)", ingredients);

        ingredients = new String[]{
                "premium roast coffee", "milk", "cream", "sodium phosphate", "datem", "sodium stearoyl lactylate", "sodium citrate", "carrageenan", "sugar", "water",
                "fructose", "natural plant flavor", "artificial flavor", "caramel color", "sulfites", "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Coffee: Vanilla", ingredients);

        ingredients = new String[]{
                "premium roast coffee", "milk", "cream", "sodium phosphate", "datem", "sodium stearoyl lactylate", "sodium citrate", "carrageenan", "water",
                "erythritol", "natural plant flavor", "artificial flavors", "cellulose gum", "sucralose", "salt", "malic acid", "potassium sorbate",
                "acesulfame potassium", "caramel color", "sulfites"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Coffee with Sugar Free Vanilla Syrup (Large)", ingredients);

        ingredients = new String[]{"water", "sugar", "orange pekoe", "pekoe cut black tea"};
        restaurantToFirebaseH(restRef, "Beverages", "Sweet Tea (Child)", ingredients);

        ingredients = new String[]{"water", "sugar", "orange pekoe", "pekoe cut black tea"};
        restaurantToFirebaseH(restRef, "Beverages", "Sweet Tea (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Sweet Tea (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Sweet Tea (Large)", ingredients);

        ingredients = new String[]{
                "sugar", "water", "fructose", "natural plant flavor", "artificial flavors", "potassium sorbate", "citric acid", "caramel color", "sulfites", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Hazelnut Syrup", ingredients);

        ingredients = new String[]{"sugar", "water", "potassium sorbate", "citric acid", "premium roast coffee"};
        restaurantToFirebaseH(restRef, "Coffees", "Liquid Sugar", ingredients);

        ingredients = new String[]{"nonfat milk", "espresso"};
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Cappuccino (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Cappuccino (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Cappuccino (Large)", ingredients);

        ingredients = new String[]{"nonfat milk", "espresso"};
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Latte (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color", "sulfites",
                "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Cappuccino (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Cappuccino (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Cappuccino (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color", "sulfites",
                "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Latte (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavors", "potassium sorbate", "citric acid",
                "caramel color", "sulfites", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Hazelnut Cappuccino (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Hazelnut Cappuccino (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Hazelnut Cappuccino (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavors", "potassium sorbate", "citric acid",
                "caramel color", "sulfites", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Hazelnut Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Hazelnut Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Hazelnut Latte (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "caramel color", "sulfites", "potassium sorbate",
                "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Vanilla Cappuccino (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Vanilla Cappuccino (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Vanilla Cappuccino (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "water", "erythritol", "natural plant flavor", "artificial flavors", "cellulose gum", "sucralose", "salt", "malic acid",
                "potassium sorbate", "acesulfame potassium", "caramel color", "sulfites"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Cappuccino with Sugar Free Vanilla Syrup (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Cappuccino with Sugar Free Vanilla Syrup (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Cappuccino with Sugar Free Vanilla Syrup (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "water", "erythritol", "natural plant flavor", "artificial flavors", "cellulose gum", "sucralose", "salt", "malic acid",
                "potassium sorbate", "acesulfame potassium", "caramel color", "sulfites"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Latte with Sugar Free Vanilla Syrup (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Latte with Sugar Free Vanilla Syrup (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Latte with Sugar Free Vanilla Syrup (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "natural botanical flavors", "artificial flavors", "chocolate liquor", "caramel color", "sulfites",
                "vanilla extract", "salt", "gellan gum", "citric acid", "potassium sorbate", "red 40", "cream", "corn syrup", "high fructose corn syrup", "monoglycerides",
                "diglycerides", "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source",
                "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk", "dextrose", "glycerin", "hydrogenated coconut oil",
                "cocoa", "alkali", "food starch", "natural plant flavor", "disodium phosphate", "soy lecithin", "vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Mocha with Nonfat Milk (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Mocha with Nonfat Milk (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Mocha with Nonfat Milk (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "sugar", "water", "natural botanical flavors", "artificial flavors", "chocolate liquor", "caramel color", "sulfites",
                "vanilla extract", "salt", "gellan gum", "citric acid", "potassium sorbate", "red 40", "cream", "corn syrup", "high fructose corn syrup",
                "monoglycerides", "diglycerides", "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor",
                "vegetable source", "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk", "dextrose", "glycerin",
                "hydrogenated coconut oil", "cocoa", "alkali", "food starch", "natural plant flavor", "disodium phosphate", "soy lecithin", "vanillin"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Hot Chocolate with Nonfat Milk (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Hot Chocolate with Nonfat Milk (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Hot Chocolate with Nonfat Milk (Large)", ingredients);

        ingredients = new String[]{"nonfat milk", "espresso"};
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Latte (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color", "sulfites",
                "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Caramel Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Caramel Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Caramel Latte (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavors", "potassium sorbate", "citric acid",
                "caramel color", "sulfites", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Hazelnut Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Hazelnut Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Hazelnut Latte (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "caramel color", "sulfites", "potassium sorbate",
                "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Vanilla Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Vanilla Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Vanilla Latte (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "water", "erythritol", "natural plant flavor", "artificial flavors", "cellulose gum", "sucralose", "salt",
                "malic acid", "potassium sorbate", "acesulfame potassium", "caramel color", "sulfites"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Latte with Sugar Free Vanilla Syrup (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Latte with Sugar Free Vanilla Syrup (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Latte with Sugar Free Vanilla Syrup (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "sugar", "water", "natural botanical flavors", "artificial flavors", "chocolate liquor", "caramel color", "sulfites",
                "vanilla extract", "salt", "gellan gum", "citric acid", "potassium sorbate", "red 40", "cream", "corn syrup", "high fructose corn syrup",
                "monoglycerides", "diglycerides", "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor",
                "vegetable source", "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk", "dextrose", "glycerin",
                "hydrogenated coconut oil", "cocoa", "alkali", "food starch", "natural plant flavor", "disodium phosphate", "soy lecithin", "vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Mocha with Nonfat Milk (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Mocha with Nonfat Milk (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Mocha with Nonfat Milk (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "chocolate sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color", "sulfites",
                "potassium sorbate", "citric acid", "malic acid", "cream", "corn syrup", "sugar", "high fructose corn syrup", "monoglycerides", "diglycerides",
                "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "mixed tocopherols",
                "whipping propellant", "nitrous oxide", "milk", "sweetened condensed milk", "butter", "pectin", "disodium phosphate", "vanillin", "ethyl vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Caramel Mocha (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Caramel Mocha (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Nonfat Caramel Mocha (Large)", ingredients);

        ingredients = new String[]{
                "nonfat milk", "espresso", "chocolate sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color",
                "sulfites", "potassium sorbate", "citric acid", "malic acid", "cream", "corn syrup", "sugar", "high fructose corn syrup", "monoglycerides",
                "diglycerides", "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk", "sweetened condensed milk", "butter", "pectin", "disodium phosphate",
                "vanillin", "ethyl vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Mocha (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Mocha (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Nonfat Caramel Mocha (Large)", ingredients);

        ingredients = new String[]{"whole milk", "espresso"};
        restaurantToFirebaseH(restRef, "Coffees", "Cappuccino (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Cappuccino (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Cappuccino (Large)", ingredients);

        ingredients = new String[]{"whole milk", "espresso"};
        restaurantToFirebaseH(restRef, "Coffees", "Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Latte (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color", "sulfites",
                "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Cappuccino (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Cappuccino (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Cappuccino (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color", "sulfites",
                "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Latte (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavors", "potassium sorbate", "citric acid",
                "caramel color", "sulfites", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Hazelnut Cappuccino (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Hazelnut Cappuccino (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Hazelnut Cappuccino (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavors", "potassium sorbate",
                "citric acid", "caramel color", "sulfites", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Hazelnut Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Hazelnut Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Hazelnut Latte (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "caramel color", "sulfites",
                "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Vanilla Cappuccino (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Vanilla Cappuccino (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Vanilla Cappuccino (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor",
                "caramel color", "sulfites", "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Vanilla Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Vanilla Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Vanilla Latte (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "water", "erythritol", "natural plant flavor", "artificial flavors", "cellulose gum", "sucralose",
                "salt", "malic acid", "potassium sorbate", "acesulfame potassium", "caramel color", "sulfites"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Cappuccino with Sugar Free Vanilla Syrup (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Cappuccino with Sugar Free Vanilla Syrup (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Cappuccino with Sugar Free Vanilla Syrup (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "water", "erythritol", "natural plant flavor", "artificial flavors", "cellulose gum", "sucralose",
                "salt", "malic acid", "potassium sorbate", "acesulfame potassium", "caramel color", "sulfites"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Latte with Sugar Free Vanilla Syrup (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Latte with Sugar Free Vanilla Syrup (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Latte with Sugar Free Vanilla Syrup (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "natural botanical flavors", "artificial flavors", "chocolate liquor", "caramel color",
                "sulfites", "vanilla extract", "salt", "gellan gum", "citric acid", "potassium sorbate", "red 40", "cream", "nonfat milk", "corn syrup",
                "high fructose corn syrup", "monoglycerides", "diglycerides", "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor",
                "natural vegetable flavor", "vegetable source", "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk",
                "dextrose", "glycerin", "hydrogenated coconut oil", "cocoa", "alkali", "food starch", "natural plant flavor", "disodium phosphate", "soy lecithin", "vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Mocha (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Mocha (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Mocha (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "sugar", "water", "natural botanical flavors", "artificial flavors", "chocolate liquor", "caramel color",
                "sulfites", "vanilla extract", "salt", "gellan gum", "citric acid", "potassium sorbate", "red 40", "cream", "nonfat milk",
                "corn syrup", "high fructose corn syrup", "monoglycerides", "diglycerides", "carrageenan", "polysorbate 80", "beta carotene",
                "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor", "mixed tocopherols", "whipping propellant",
                "nitrous oxide", "milk", "dextrose", "glycerin", "hydrogenated coconut oil", "cocoa", "alkali", "food starch", "natural plant flavor",
                "disodium phosphate", "soy lecithin", "vanillin"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Hot Chocolate (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Hot Chocolate (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Hot Chocolate (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavors", "potassium sorbate", "citric acid",
                "caramel color", "sulfites", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Hazelnut Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Hazelnut Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Hazelnut Latte (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "caramel color", "sulfites", "potassium sorbate",
                "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Vanilla Latte (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Vanilla Latte (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Vanilla Latte (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "water", "erythritol", "natural plant flavor", "artificial flavors", "cellulose gum", "sucralose", "salt",
                "malic acid", "potassium sorbate", "acesulfame potassium", "caramel color", "sulfites"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Latte with Sugar Free Vanilla Syrup (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Latte with Sugar Free Vanilla Syrup (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Latte with Sugar Free Vanilla Syrup (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "natural botanical flavors", "artificial flavors", "chocolate liquor", "caramel color",
                "sulfites", "vanilla extract", "salt", "gellan gum", "citric acid", "potassium sorbate", "red 40", "cream", "nonfat milk", "corn syrup",
                "high fructose corn syrup", "monoglycerides", "diglycerides", "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor",
                "natural vegetable flavor", "vegetable source", "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk",
                "dextrose", "glycerin", "hydrogenated coconut oil", "cocoa", "alkali", "food starch", "natural plant flavor", "disodium phosphate", "soy lecithin", "vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Mocha (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Mocha (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Mocha (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavors", "artificial flavors", "chocolate liquor", "caramel color",
                "sulfites", "salt", "sucralose", "gellan gum", "potassium sorbate", "citric acid", "malic acid", "red 40", "cream", "nonfat milk", "corn syrup",
                "high fructose corn syrup", "monoglycerides", "diglycerides", "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor",
                "natural vegetable flavor", "vegetable source", "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk",
                "sweetened condensed milk", "butter", "pectin", "disodium phosphate", "vanillin", "ethyl vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Mocha (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Mocha (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Mocha (Large)", ingredients);

        ingredients = new String[]{
                "whole milk", "espresso", "sugar", "water", "fructose", "natural plant flavors", "artificial flavors", "chocolate liquor", "caramel color",
                "sulfites", "salt", "sucralose", "gellan gum", "potassium sorbate", "citric acid", "malic acid", "red 40", "cream", "nonfat milk", "corn syrup",
                "high fructose corn syrup", "monoglycerides", "diglycerides", "carrageenan", "polysorbate 80", "beta carotene", "natural dairy flavor",
                "natural vegetable flavor", "vegetable source", "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk",
                "sweetened condensed milk", "butter", "pectin", "disodium phosphate", "vanillin", "ethyl vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Iced Caramel Mocha (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Caramel Mocha (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Iced Caramel Mocha (Large)", ingredients);

        ingredients = new String[]{
                "corn syrup", "sweetened condensed milk", "milk", "sugar", "high fructose corn syrup", "water", "butter", "salt", "pectin", "disodium phosphate",
                "artificial flavor", "vanillin", "ethyl vanillin", "caramel color", "sulfites", "potassium sorbate"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Drizzle", ingredients);

        ingredients = new String[]{
                "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "salt", "caramel color", "sulfites", "potassium sorbate",
                "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Caramel Syrup", ingredients);

        ingredients = new String[]{
                "corn syrup", "dextrose", "water", "sugar", "glycerin", "hydrogenated coconut oil", "cocoa", "alkali", "food starch", "nonfat milk",
                "natural plant flavor", "artificial flavor", "salt", "gellan gum", "disodium phosphate", "potassium sorbate", "soy lecithin", "vanillin", "milk"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Chocolate Drizzle", ingredients);

        ingredients = new String[]{
                "sugar", "water", "natural botanical flavors", "artificial flavors", "chocolate liquor", "caramel color", "sulfites", "vanilla extract", "salt",
                "gellan gum", "citric acid", "potassium sorbate", "red 40"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Chocolate Syrup", ingredients);

        ingredients = new String[]{
                "water", "erythritol", "natural plant flavor", "artificial flavors", "cellulose gum", "sucralose", "salt", "malic acid", "potassium sorbate",
                "acesulfame potassium", "caramel color", "sulfites"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Sugar Free Vanilla Syrup", ingredients);

        ingredients = new String[]{
                "sugar", "water", "fructose", "natural plant flavor", "artificial flavor", "caramel color", "sulfites", "potassium sorbate", "citric acid", "malic acid"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Vanilla Syrup", ingredients);

        ingredients = new String[]{
                "water", "cream", "sugar", "milk", "high fructose corn syrup", "coffee extract", "natural botanical flavors", "artificial flavors",
                "monoglycerides", "diglycerides", "guar gum", "potassium phosphate", "disodium phosphate", "carrageenan", "carob bean gum", "ice", "nonfat milk",
                "corn syrup", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "sweetened condensed milk", "butter", "salt", "pectin", "vanillin", "ethyl vanillin",
                "caramel color", "sulfites", "potassium sorbate"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Frappe Caramel (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Frappe Caramel (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Frappe Caramel (Large)", ingredients);

        ingredients = new String[]{
                "water", "cream", "sugar", "milk", "high fructose corn syrup", "coffee extract", "natural botanical flavors", "artificial flavors", "cocoa",
                "alkali", "monoglycerides", "diglycerides", "guar gum", "potassium phosphate", "disodium phosphate", "carrageenan", "carob bean gum", "ice",
                "nonfat milk", "corn syrup", "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source",
                "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "dextrose", "glycerin", "hydrogenated coconut oil", "food starch",
                "natural plant flavor", "salt", "gellan gum", "potassium sorbate", "soy lecithin", "vanillin"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Frappe Mocha (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Frappe Mocha (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Coffees", "Frappe Mocha (Large)", ingredients);

        ingredients = new String[]{
                "water", "cream", "sugar", "milk", "high fructose corn syrup", "coffee extract", "natural botanical flavors", "artificial flavors",
                "monoglycerides", "diglycerides", "guar gum", "potassium phosphate", "disodium phosphate", "carrageenan", "carob bean gum"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Caramel Coffee Frappe Base", ingredients);

        ingredients = new String[]{
                "water", "cream", "sugar", "milk", "high fructose corn syrup", "coffee extract", "natural botanical flavors", "artificial flavors", "cocoa",
                "alkali", "monoglycerides", "diglycerides", "guar gum", "potassium phosphate", "disodium phosphate", "carrageenan", "carob bean gum"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Mocha Coffee Frappe Base", ingredients);

        ingredients = new String[]{
                "cream", "nonfat milk", "water", "corn syrup", "sugar", "high fructose corn syrup", "monoglycerides", "diglycerides", "carrageenan",
                "polysorbate 80", "beta carotene", "natural dairy flavor", "natural vegetable flavor", "vegetable source", "artificial flavor",
                "mixed tocopherols", "whipping propellant", "nitrous oxide", "milk"
        };
        restaurantToFirebaseH(restRef, "Coffees", "Whipped Cream", ingredients);

        ingredients = new String[]{
                "strawberry banana fruit blend", "fat milk", "sugar", "whey protein concentrate", "fructose", "corn starch", "gelatin", "yogurt cultures", "milk", "ice"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Strawberry Banana Smoothie (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Strawberry Banana Smoothie (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Strawberry Banana Smoothie (Large)", ingredients);

        ingredients = new String[]{
                "strawberry puree", "water", "sugar", "blackberry puree", "blueberry puree", "concentrated pineapple juice", "concentrated apple juice",
                "cellulose powder", "xanthan gum", "fruit", "vegetable juice", "natural botanical flavors", "artificial flavors", "pectin", "citric acid",
                "milk", "yogurt", "fat milk", "whey protein concentrate", "fructose", "corn starch", "gelatin", "yogurt cultures", "ice"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Wild Berry Smoothie (Small)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Wild Berry Smoothie (Medium)", ingredients);
        restaurantToFirebaseH(restRef, "Beverages", "Wild Berry Smoothie (Large)", ingredients);

        ingredients = new String[]{"fat milk", "sugar", "whey protein concentrate", "fructose", "corn starch", "gelatin", "yogurt cultures", "milk"};
        restaurantToFirebaseH(restRef, "Coffees", "Low Fat Yogurt", ingredients);

        ingredients = new String[]{
                "strawberry puree", "banana puree", "water", "sugar", "concentrated apple juice", "cellulose powder", "natural botanical flavors",
                "artificial flavors", "xanthan gum", "citric acid", "fruit", "vegetable juice", "pectin", "ascorbic acid", "milk", "yogurt"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Strawberry Banana Fruit Blend", ingredients);

        ingredients = new String[]{
                "strawberry puree", "water", "sugar", "blackberry puree", "blueberry puree", "concentrated pineapple juice", "concentrated apple juice",
                "cellulose powder", "xanthan gum", "fruit", "vegetable juice", "natural botanical flavors", "artificial flavors", "pectin", "citric acid", "milk", "yogurt"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Wild Berry Fruit Blend", ingredients);

        ingredients = new String[]{
                "potatoes", "vegetable oil", "canola oil", "hydrogenated soybean oil", "natural beef flavor", "wheat", "milk", "citric acid", "salt",
                "corn flour", "dehydrated potato", "dextrose", "sodium acid pyrophosphate", "black pepper", "corn oil", "soybean oil", "tbhq", "dimethylpolysiloxane"
        };
        restaurantToFirebaseH(restRef, "Breakfast", "Hash Brown", ingredients);

        ingredients = new String[] { "apple" };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Apple Slices", ingredients);

        ingredients = new String[] {
                "water", "sugar", "dijon mustard", "distilled vinegar", "mustard seed", "salt", "white wine", "spices", "soybean oil", "honey",
                "corn syrup solids", "food starch", "egg yolks", "xanthan gum", "titanium dioxide", "sodium benzoate", "propylene glycol alginate",
                "turmeric", "yellow 5", "yellow 6"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Tangy Honey Mustard Sauce", ingredients);

        ingredients = new String[] { "honey" };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Honey", ingredients);

        ingredients = new String[] {
                "distilled vinegar", "water", "mustard seed", "salt", "turmeric", "paprika", "spice extractive"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Mustard Packet", ingredients);

        ingredients = new String[] {
                "soybean oil", "water", "egg yolks", "distilled vinegar", "salt", "sugar",
                "mustard flour", "lemon juice concentrate", "calcium disodium edta"
        };
        restaurantToFirebaseH(restRef, "Fries & Sides", "Mayonnaise Packet", ingredients);

        ingredients = new String[] {
                "filtered water", "organic apple juice", "natural flavor", "vitamin c", "organic natural flavor", "citric acid"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Honest Kids Appley Ever After Organic Juice Drink", ingredients);

        ingredients = new String[] {
                "water", "cane sugar", "lemon juice from concentrate", "lemon pulp", "natural flavor"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Lemonade", ingredients);

        ingredients = new String[]{
                "carbonated water", "high fructose corn syrup", "citric acid", "natural flavors", "sodium benzoate", "modified food starch", "glycerol ester of rosin", "yellow 6", "red 40"
        };
        restaurantToFirebaseH(restRef, "Beverages", "Fanta Orange", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "red onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin",
                "reduced iron", "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "ammonium chloride", "calcium carbonate", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "sesame seed",
                "wheat", "soy", "milk", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate", "artificial color",
                "lactic acid", "acetic acid", "tomato", "distilled vinegar", "corn syrup", "natural vegetable flavor", "mustard seed", "turmeric", "paprika",
                "spice extractive", "cucumbers", "calcium chloride", "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "slivered onions",
                "pork bellies", "natural smoke flavor", "sodium erythorbate", "sodium nitrite"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Quarter Pounder with Cheese Bacon", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "red onions", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "ammonium sulfate", "ammonium chloride", "calcium carbonate", "dough conditioners",
                "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides", "ethoxylated monoglycerides",
                "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate", "soy lecithin", "sesame seed", "wheat",
                "soy", "milk", "milkfat", "cheese culture", "sodium citrate", "citric acid", "sorbic acid", "sodium phosphate", "artificial color", "lactic acid",
                "acetic acid", "tomato", "distilled vinegar", "corn syrup", "natural vegetable flavor", "mustard seed", "turmeric", "paprika", "spice extractive",
                "cucumbers", "calcium chloride", "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "slivered onions", "tomato",
                "water", "egg yolks", "distilled vinegar", "mustard flour", "lemon juice concentrate", "calcium disodium edta", "lettuce"
        };
        restaurantToFirebaseH(restRef, "Burgers", "Quarter Pounder with Cheese Deluxe", ingredients);

        ingredients = new String[]{
                "beef", "grill seasoning", "salt", "black pepper", "enriched flour", "bleached wheat flour", "malted barley flour", "niacin", "reduced iron",
                "thiamin mononitrate", "riboflavin", "folic acid", "enzymes", "water", "high fructose corn syrup", "sugar", "yeast", "soybean oil",
                "partially hydrogenated soybean oil", "calcium sulfate", "calcium carbonate", "wheat gluten", "ammonium sulfate", "ammonium chloride",
                "dough conditioners", "sodium stearoyl lactylate", "datem", "ascorbic acid", "azodicarbonamide", "monoglycerides", "diglycerides",
                "ethoxylated monoglycerides", "monocalcium phosphate", "guar gum", "calcium peroxide", "soy flour", "calcium propionate", "sodium propionate",
                "soy lecithin", "wheat", "soy", "tomato", "distilled vinegar", "corn syrup", "natural vegetable flavor", "mustard seed", "turmeric", "paprika",
                "spice extractive", "cucumbers", "calcium chloride", "alum", "potassium sorbate", "natural plant flavor", "polysorbate 80", "onions", "potatoes",
                "vegetable oil", "canola oil", "hydrogenated soybean oil", "natural beef flavor", "milk", "citric acid", "dextrose", "sodium acid", "pyrophosphate",
                "corn oil", "tbhq", "dimethylpolysiloxane", "hydrolyzed wheat", "hydrolyzed milk", "artificial flavor", "mixed tocopherols", "whipping propellant",
                "nitrous oxide", "low fat milk", "palmitate", "apple"
        };
        restaurantToFirebaseH(restRef, "Happy Meal", "Happy Meal: Hamburger", ingredients);

        ingredients = new String[]{
                "white boneless chicken", "water", "food starch-modified", "salt", "seasoning", "autolyzed yeast extract", "wheat starch", "natural botanical flavor",
                "safflower oil", "dextrose", "citric acid", "sodium phosphates", "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate",
                "riboflavin", "folic acid", "yellow corn flour", "leavening", "baking soda", "sodium acid", "pyrophosphate", "sodium aluminum phosphate",
                "monocalcium phosphate", "calcium lactate", "spices", "corn starch", "wheat", "vegetable oil", "canola oil", "corn oil", "soybean oil",
                "hydrogenated soybean oil", "tbhq", "dimethylpolysiloxane", "potatoes", "natural beef flavor", "milk", "hydrolyzed wheat", "hydrolyzed milk",
                "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "low fat milk", "palmitate", "apple"
        };
        restaurantToFirebaseH(restRef, "Happy Meal", "Happy Meal: 4pc Chicken McNuggets", ingredients);

        ingredients = new String[]{
                "white boneless chicken", "water", "food starch-modified", "salt", "seasoning", "autolyzed yeast extract", "wheat starch", "natural botanical flavor",
                "safflower oil", "dextrose", "citric acid", "sodium phosphates", "enriched flour", "bleached wheat flour", "niacin", "reduced iron", "thiamin mononitrate",
                "riboflavin", "folic acid", "yellow corn flour", "leavening", "baking soda", "sodium acid", "pyrophosphate", "sodium aluminum phosphate",
                "monocalcium phosphate", "calcium lactate", "spices", "corn starch", "wheat", "vegetable oil", "canola oil", "corn oil", "soybean oil",
                "hydrogenated soybean oil", "tbhq", "dimethylpolysiloxane", "potatoes", "natural beef flavor", "milk", "hydrolyzed wheat", "hydrolyzed milk",
                "artificial flavor", "mixed tocopherols", "whipping propellant", "nitrous oxide", "low fat milk", "palmitate", "apple"
        };
        restaurantToFirebaseH(restRef, "Happy Meal", "Happy Meal: 6pc Chicken McNuggets", ingredients);
    }

    public ArrayList<Restaurant> getRestaurants() { return restaurants; }
}
