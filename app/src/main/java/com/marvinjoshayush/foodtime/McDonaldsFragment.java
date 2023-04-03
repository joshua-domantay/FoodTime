package com.marvinjoshayush.foodtime;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    // Width and height layout parameters
    private LinearLayout.LayoutParams matchWrap = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );
    private LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );

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
                ArrayList<View> layout = createLayoutForSubMenuFragment(btn);
                ImageView logo = ViewMaker.createBasicImageView(getContext(), ViewMaker.WRAP_WRAP, "mcdonalds_menu_logo", Gravity.CENTER,
                        0, 10, 0, 10);
                Fragment frag = new SubMenuFragment(home, logo, layout);
                home.setFragment(frag);
            });
        }
    }

    private ArrayList<View> createLayoutForSubMenuFragment(int id) {
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

    private ArrayList<View> createLayoutFeatured() {
        ArrayList<View> contents = new ArrayList<>();
        ArrayList<String> toAdd = new ArrayList<>(Arrays.asList("big_mac", "chicken_mcnuggets", "french_fries",
                "quarter_pounder_with_cheese", "iced_coffee_regular", "egg_mcmuffin", "sausage_burrito"));
        for(Restaurant rest : home.getRestaurantManager().getRestaurants()) {
            if (rest.getNameForFile().equalsIgnoreCase("mcdonalds")) {
                for(MenuSection section : rest.getMenuSections()) {
                    for (MenuItem item : section.getMenu()) {
                        if(toAdd.contains(item.getNameForFile().toLowerCase())) {
                            String itemStr = rest.getNameForFile() + "_" + item.getNameForFile();
                            contents.add(ViewMaker.createBasicImageButton(getContext(), ViewMaker.WRAP_WRAP, itemStr, Gravity.CENTER, true));
                            toAdd.remove(item.getNameForFile().toLowerCase());
                        }
                    }
                }
            }
        }
        return contents;
    }

    private ArrayList<View> createLayoutDollarMenu() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutBurgers() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutBreakfast() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutBakery() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutCoffees() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutMcNuggets() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutChickenFish() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutFriesAndSides() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutHappyMeal() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutsweetsTreats() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutBeverages() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutFromFirebase(String sectionName) {
        ArrayList<View> contents = new ArrayList<>();
        for(Restaurant rest : home.getRestaurantManager().getRestaurants()) {
            if(rest.getNameForFile().equalsIgnoreCase("mcdonalds")) {
                for(MenuSection section : rest.getMenuSections()) {
                    if(section.getName().equalsIgnoreCase(sectionName)) {
                        for (MenuItem item : section.getMenu()) {
                            String itemStr = rest.getNameForFile() + "_" + item.getNameForFile();
                            contents.add(ViewMaker.createBasicImageButton(getContext(), ViewMaker.WRAP_WRAP, itemStr, Gravity.CENTER, true));
                        }
                    }
                }
                break;
            }
        }
        return contents;
    }
}
