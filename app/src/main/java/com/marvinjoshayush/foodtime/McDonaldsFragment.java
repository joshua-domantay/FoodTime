package com.marvinjoshayush.foodtime;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class McDonaldsFragment extends Fragment {
    private HomeActivity home;
    private View view;

    public McDonaldsFragment(HomeActivity home) {
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mcdonalds, container, false);

        setOnClickListeners();

        return view;
    }

    private void setOnClickListeners() {
        int[] btns = new int[]{
                R.id.mcDonalds_featured,        R.id.mcDonalds_dollarMenu,      R.id.mcDonalds_burgers,         R.id.mcDonalds_breakfast,
                R.id.mcDonalds_bakery,          R.id.mcDonalds_coffees,         R.id.mcDonalds_mcNuggets,       R.id.mcDonalds_chickenFish,
                R.id.mcDonalds_friesAndSides,   R.id.mcDonalds_happyMeal,       R.id.mcDonalds_sweetsTreats,    R.id.mcDonalds_beverages
        };

        for(int btn : btns) {
            view.findViewById(btn).setOnClickListener(item -> {
                ViewsAndImageButtonInfos layout = createLayoutForSubMenuFragment(btn);
                ImageView logo = ViewMaker.createBasicImageView(getContext(), ViewMaker.WRAP_WRAP(), "mcdonalds_menu_logo", Gravity.CENTER,
                        0, 10, 0, 10);
                Fragment frag = new SubMenuFragment(home, "McDonalds", "mcdonalds", logo, new McDonaldsFragment(home), layout.views, layout.imageButtonInfos);
                home.setFragment(frag);
            });
        }
    }

    private ViewsAndImageButtonInfos createLayoutForSubMenuFragment(int id) {
        switch(id) {
            case R.id.mcDonalds_featured:
                return createLayoutFeatured();
            case R.id.mcDonalds_dollarMenu:
                return createLayoutDollarMenu();
            case R.id.mcDonalds_burgers:
                return createLayoutBurgers();
            case R.id.mcDonalds_breakfast:
                return createLayoutBreakfast();
            case R.id.mcDonalds_bakery:
                return createLayoutBakery();
            case R.id.mcDonalds_coffees:
                return createLayoutCoffees();
            case R.id.mcDonalds_mcNuggets:
                return createLayoutMcNuggets();
            case R.id.mcDonalds_chickenFish:
                return createLayoutChickenFish();
            case R.id.mcDonalds_friesAndSides:
                return createLayoutFriesAndSides();
            case R.id.mcDonalds_happyMeal:
                return createLayoutHappyMeal();
            case R.id.mcDonalds_sweetsTreats:
                return createLayoutsweetsTreats();
            default:    // R.id.mcDonalds_beverages:
                return createLayoutBeverages();
        }
    }

    private ViewsAndImageButtonInfos createLayoutFeatured() {
        ArrayList<String> toAdd = new ArrayList<>(Arrays.asList(
                "big_mac", "chicken_mcnuggets", "french_fries", "quarter_pounder_with_cheese",
                "iced_coffee_regular", "egg_mcmuffin", "sausage_burrito"
        ));
        return RestaurantManager.getSpecifiedMenu(home, getContext(), "mcdonalds", toAdd);
    }

    private ViewsAndImageButtonInfos createLayoutDollarMenu() {
        ArrayList<String> toAdd = new ArrayList<>(Arrays.asList(
                "sausage_biscuit_size", "sausage_mcmuffin", "sausage_burrito", "hash_browns",
                "mcdouble", "mcchicken", "chicken_nuggets", "french_fries"
        ));
        ViewsAndImageButtonInfos contents = RestaurantManager.getSpecifiedMenu(home, getContext(), "mcdonalds", toAdd);

        toAdd = new ArrayList<>(Arrays.asList(
                "coca_cola_classic", "sprite", "hi_c_orange_lavaburst", "diet_coke",
                "sweet_tea", "unsweetened_iced_tea"
        ));
        ViewsAndImageButtonInfos n = RestaurantManager.getSpecifiedMenu(home, getContext(), "mcdonalds", toAdd);
        contents.views.addAll(n.views);
        contents.imageButtonInfos.addAll(n.imageButtonInfos);
        return contents;
    }

    private ViewsAndImageButtonInfos createLayoutBurgers() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "burgers");
    }

    private ViewsAndImageButtonInfos createLayoutBreakfast() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "breakfast");
    }

    private ViewsAndImageButtonInfos createLayoutBakery() {
        ViewsAndImageButtonInfos contents = new ViewsAndImageButtonInfos();
        return contents;
    }

    private ViewsAndImageButtonInfos createLayoutCoffees() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "coffees");
    }

    private ViewsAndImageButtonInfos createLayoutMcNuggets() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "mcnuggets and meals");
    }

    private ViewsAndImageButtonInfos createLayoutChickenFish() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "chicken & fish sandwiches");
    }

    private ViewsAndImageButtonInfos createLayoutFriesAndSides() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "fries & sides");
    }

    private ViewsAndImageButtonInfos createLayoutHappyMeal() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "happy meal");
    }

    private ViewsAndImageButtonInfos createLayoutsweetsTreats() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "sweets & treats");
    }

    private ViewsAndImageButtonInfos createLayoutBeverages() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "mcdonalds", "beverages");
    }
}
