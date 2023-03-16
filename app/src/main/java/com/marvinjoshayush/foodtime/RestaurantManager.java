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
                    ArrayList<String> ingredients = getIngredientsFromFirebase(pRestaurantRef.child(mMenuItem.getKey()));
                    restaurantObj.addMenu(pSectionNum, menuItemStr, ingredients);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private ArrayList<String> getIngredientsFromFirebase(DatabaseReference pMenuItemRef) {
        ArrayList<String> ingredients = new ArrayList<>();
        pMenuItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()) {
                    ingredients.add(child.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        return ingredients;
    }

    private void restaurantToFirebaseH(DatabaseReference toAdd, String section, String menuItem, String[] ingredients) {
        for(int i = 0; i < ingredients.length; i++) {
            toAdd.child(section).child(menuItem).child(i + "").setValue(ingredients[i]);
        }
    }

    private void restaurantToFirebase() {
        DatabaseReference restRef = FirebaseDatabase.getInstance().getReference("Restaurants");
        getPandaExpress(restRef.child("Panda Express"));
        getSubway(restRef.child("Subway"));


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

    private void getPandaExpress(DatabaseReference restRef) {
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
        restaurantToFirebaseH(restRef, "Entrees", "Brocolli Beef", ingredients);

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
    }

    private void getSubway(DatabaseReference restRef) {
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

    public ArrayList<Restaurant> getRestaurants() { return restaurants; }
}
