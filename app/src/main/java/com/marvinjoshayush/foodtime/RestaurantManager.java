package com.marvinjoshayush.foodtime;

import java.util.ArrayList;

public class RestaurantManager {
    private ArrayList<Restaurant> restaurants;

    public RestaurantManager() {
        restaurants = new ArrayList<>();
        initializeRestaurants();
    }

    private void initializeRestaurants() {
        // Change when using Google Maps?
        restaurants.add(getPandaExpress());

        /*
        ingredients = new String[]{};
        rest.addMenu(1, "", ingredients);
         */
    }

    private Restaurant getPandaExpress() {
        Restaurant rest = new Restaurant("Panda Express", R.drawable.panda_express);

        rest.addMenuSection(new String[]{"Sides", "Entrees", "Appetizers"});
        String[] ingredients = new String[]{"cabbage", "enriched wheat flour", "water", "onion", "soybean oil",
                "celery", "rice", "wheat", "caramel color", "sesame oil", "wheat gluten", "canola oil", "cottonseed oil",
                "dextrose", "sugar", "malted barley flour", "monoglycerides", "datem", "l-cysteine hydrochloride",
                "ascorbic acid", "enzyme", "modified cornstarch", "xanthan gum", "potassium carbonate", "salt",
                "sodium carbonate", "yellow 5", "yellow 6", "potassium bicarbonate", "soy sauce", "spices", "soy", "sesame"};
        rest.addMenu(1, "Chow Mein", ingredients);
        ingredients = new String[]{"white rice", "liquid eggs", "peas", "carrots", "soybean oil", "green onions",
                "salt", "sesame oil", "maltodextrin", "modified food starch", "sugar", "onion powder", "celery extract",
                "disodium inosinate", "disodium guanylate", "soybeans", "wheat", "water", "soy sauce", "spices", "eggs", "soy", "sesame"};
        rest.addMenu(1, "Fried Rice", ingredients);
        ingredients = new String[]{"white rice"};
        rest.addMenu(1, "Steamed Rice", ingredients);
        ingredients = new String[]{"brown rice", "soybean oil", "soy"};
        rest.addMenu(1, "Steamed Brown Rice", ingredients);
        ingredients = new String[]{"cabbage", "broccoli", "kale", "water", "soybean oil", "garlic", "phosphoric acid", "natural flavor",
                "cornstarch", "potato starch", "modified food starch", "salt", "maltodextrin", "disodium inosinate",
                "disodium guanylate", "dehydrated soy sauce powder", "sugar", "onion powder", "celery extract", "soy", "wheat"};
        rest.addMenu(1, "Super Greens", ingredients);

        rest.addMenuSection("Entrees");
        ingredients = new String[]{"beef", "water", "sugar", "red bell pepper", "onions", "modified food starch", "soybean oil",
                "distilled vinegar", "invert syrup", "wheat", "soybeans", "salt", "garlic", "phosphoric acid", "potassium sorbate",
                "sodium benzoate", "guar gum", "tomato paste", "tapioca dextrin", "rice flour", "natural flavor", "vegetable juice color",
                "beta carotene color", "wheat gluten", "wheat flour", "hydrolyzed soy proteins", "hydrolyzed corn protein", "autolyzed yeast",
                "corn syrup solids", "palm oil", "maltodextrin", "sodium bicarbonate", "sodium phosphate", "carrageenan", "potassium chloride",
                "dextrose", "xanthan gum", "beef fat", "whey", "milk", "soy"};
        rest.addMenu(2, "Beijing Beef", ingredients);
        ingredients = new String[]{"broccoli", "water", "beef", "soybean oil", "garlic", "phosphoric acid", "corn starch", "potato starch",
                "modified food starch", "soy sauce", "salt", "rice", "caramel color", "sesame oil", "sea salt", "brown sugar", "sodium phosphate",
                "natural flavor", "yeast extract", "spices", "sugar", "soy", "wheat", "sesame"};
        rest.addMenu(2, "Brocolli Beef", ingredients);
        ingredients = new String[]{"angus steak", "baby broccoli", "mushrooms", "onions", "bell pepper", "water", "soybean oil", "tomato paste",
                "miso paste", "soy sauce powder", "modified corn starch", "black pepper powder", "dehydrated garlic", "chili pepper powder",
                "yeast extract", "lactic acid", "onion powder", "caramel color", "disodium inosinate", "disodium guanylate", "xanthan gum",
                "paprika oleoresin", "sodium benzoate", "soy", "wheat"};
        rest.addMenu(2, "Black Pepper Angus Steak", ingredients);
        ingredients = new String[]{"dark meat chicken", "water", "celery", "onions", "soybean oil", "garlic", "phosphoric acid", "corn starch",
                "guar gum", "potato starch", "modified food starch", "rice", "wheat", "sesame oil", "caramel color", "salt", "spices",
                "sodium phosphates", "sugar", "soy sauce", "soy", "sesame"};
        rest.addMenu(2, "Black Pepper Chicken", ingredients);
        ingredients = new String[]{"eggplant", "firm tofu", "soybean oil", "red bell peppers", "distilled vinegar", "sugar", "water", "garlic",
                "phosphoric acid", "guar gum", "rice", "wheat", "caramel color", "salt", "modified food starch", "potato starch", "corn starch",
                "sesame oil", "soy sauce", "spices", "soy", "sesame"};
        rest.addMenu(2, "Eggplant Tofu (Regional)", ingredients);
        ingredients = new String[]{"chicken thighs", "sugar", "soy sauce", "ginger puree", "garlic", "water", "soybean oil", "black pepper",
                "roasted sesame seed oil", "soy", "wheat", "sesame"};
        rest.addMenu(2, "Grilled Chicken", ingredients);
        ingredients = new String[]{"chicken breast strips", "green beans", "yellow bell pepper", "water", "distilled vinegar", "modified food starch",
                "corn starch", "wheat", "caramel color", "ginger", "garlic", "phosphoric acid", "salt", "sodium phosphate", "sesame oil",
                "sesame seeds", "wheat flour", "spices", "sugar", "organic", "honey"};
        rest.addMenu(2, "Honey Sesame Chicken Breast", ingredients);
        ingredients = new String[]{"shrimp", "modified food starch", "soybean oil", "rice flour", "glazed walnuts", "water", "sugar", "potato dextrin",
                "bleached wheat flour", "evaporated milk", "white corn flour", "salt", "distilled vinegar", "sodium tripolyphosphate",
                "sodium aluminum phosphate", "sodium bicarbonate", "guar gum", "honey", "salted egg yolks", "wheat gluten", "yeast", "malic acid",
                "xanthan gum", "potassium sorbate", "sodium benzoate", "natural flavors", "spices", "dried garlic", "paprika", "annatto extract",
                "oleoresin turmeric", "tree nuts", "shellfish", "eggs", "milk", "soy", "wheat"};
        rest.addMenu(2, "Honey Walnut Shrimp", ingredients);
        ingredients = new String[]{"dark meat chicken", "zucchini", "water", "red bell peppers", "roasted peanuts", "soybean oil", "green onions",
                "garlic", "corn starch", "phosphoric acid", "guar gum", "potato starch", "modified food starch", "wheat", "salt", "caramel color",
                "rice", "sesame oil", "sodium phosphate", "sugar", "soy sauce", "spices", "peanuts", "soy", "sesame"};
        rest.addMenu(2, "Kung Pao Chicken", ingredients);
        ingredients = new String[]{"dark meat chicken", "mushrooms", "zucchini", "water", "soybean oil", "dehydrated garlic", "phosphoric acid",
                "guar gum", "corn starch", "sugar", "soy sauce", "rice", "modified food starch", "sesame oil", "sodium phosphate", "sea salt",
                "salt", "brown sugar", "caramel flavor", "natural flavor", "yeast extract", "spices", "soy", "wheat", "sesame"};
        rest.addMenu(2, "Mushroom Chicken", ingredients);
        ingredients = new String[]{"dark meat chicken", "water", "sugar", "distilled vinegar", "modified food starch", "corn starch", "potato starch",
                "wheat", "orange extract", "caramel color", "salt", "garlic", "phosphoric acid", "rice", "sesame oil", "wheat flour", "eggs", "spices",
                "leavening", "soy sauce", "milk", "soy", "sesame"};
        rest.addMenu(2, "Original Orange Chicken", ingredients);
        ingredients = new String[]{"green beans", "raised without antibiotics sliced chicken breast", "water", "onion", "soybean oil", "dehydrated garlic",
                "phosphoric acid", "guar gum", "corn starch", "sodium phosphate", "sugar", "soy sauce", "salt", "potato starch", "modified food starch",
                "rice", "wheat", "caramel color", "sesame oil", "sea salt", "brown sugar", "natural flavor", "yeast extract", "spices", "soy", "sesame"};
        rest.addMenu(2, "String Bean Chicken Breast", ingredients);
        ingredients = new String[]{"chicken breast bites", "pineapple chunks", "water", "sugar", "red bell peppers", "onions", "bleached wheat flour",
                "corn starch", "soybean oil", "red jalapenos", "modified food starch", "distilled vinegar", "carrot puree", "salt", "wheat flour",
                "natural flavor", "dehydrated garlic", "sodium phosphate", "dried onion", "spices", "sodium bicarbonate", "wheat"};
        rest.addMenu(2, "Sweet Fire Chicken Breast (Regional)", ingredients);

        rest.addMenuSection("Appetizers");
        ingredients = new String[]{"bleached enriched flour", "dark meat chicken", "water", "cabbage", "onion", "napa cabbage", "carrot", "green onion",
                "mung bean vermicelli", "durum flour", "clear vermicelli", "vegetable oil", "soy sauce", "wine", "salt", "natural flavor", "dehydrated chicken",
                "chicken fat", "sugar", "whey", "maltodextrin", "nonfat dry milk", "disodium inosinate", "disodium guanylate", "sauterne wine", "chicken",
                "concentrated juices", "yeast extract", "sesame oil", "garlic", "carrageenan", "locust bean gum", "dextrose", "cottonseed oil", "soybean oil",
                "guar gum", "egg", "corn starch", "palm oil", "wheat gluten", "sodium stearoyl lactylate", "citric acid", "modified corn starch", "milk",
                "soy", "wheat", "sesame"};
        rest.addMenu(3, "Chicken Eggroll", ingredients);
        ingredients = new String[]{"chicken", "cabbage", "onions", "sugar", "salt", "granulated garlic", "sesame seed oil", "soy sauce", "soybean oil",
                "modified corn starch", "ginger", "yeast extract", "chives", "enriched bleached flour", "water", "vegetable oil", "corn starch",
                "soy", "wheat", "sesame"};
        rest.addMenu(3, "Chicken Potsticker (Regional)", ingredients);
        ingredients = new String[]{"cream cheese", "green onions", "enriched flour", "water", "egg", "salt", "mono", "diglycerides", "enzyme", "annatto",
                "turmeric", "dusted with corn starch", "milk", "wheat"};
        rest.addMenu(3, "Cream Cheese Rangoon", ingredients);
        ingredients = new String[]{"water", "firm tofu", "mushrooms", "liquid egg", "distilled vinegar", "modified food starch", "corn starch",
                "potato starch", "salt", "maltodextrin", "shortening powder", "disodium inosinate", "disodium guanylate",
                "dehydrated soy sauce powder", "sugar", "onion powder", "soybean oil", "celery extract", "sesame oil", "soy sauce", "spices",
                "egg", "soy", "wheat", "sesame"};
        rest.addMenu(3, "Hot and Sour Soup", ingredients);
        ingredients = new String[]{"cabbage", "wheat flour", "celery", "carrots", "water", "mung bean vermicelli", "green onions", "modified corn starch",
                "ginger", "garlic oil", "sesame oil", "salt", "sugar", "dehydrated soy sauce", "onion powder", "natural stir-fry flavor", "spices",
                "disodium inosinate", "disodium guanylate", "soybean oil", "sodium polyphosphate", "sodium carbonate", "monoglycerides", "diglycerides",
                "polysorbitan esters of stearates", "lecithin", "citric acid", "soy", "wheat", "sesame"};
        rest.addMenu(3, "Vegetable Spring Roll", ingredients);

        return rest;
    }

    public ArrayList<Restaurant> getRestaurants() { return restaurants; }
}
