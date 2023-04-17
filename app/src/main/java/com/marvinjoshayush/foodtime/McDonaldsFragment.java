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
                ViewsAndImageButtonInfos layout = createLayoutForSubMenuFragment(btn);
                ImageView logo = ViewMaker.createBasicImageView(getContext(), ViewMaker.WRAP_WRAP, "mcdonalds_menu_logo", Gravity.CENTER,
                        0, 10, 0, 10);
                Fragment frag = new SubMenuFragment(home, logo, new McDonaldsFragment(home), layout.views, layout.imageButtonInfos);
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
        return getSpecifiedMenu(toAdd);
    }

    private ViewsAndImageButtonInfos createLayoutDollarMenu() {
        ArrayList<String> toAdd = new ArrayList<>(Arrays.asList(
                "sausage_biscuit_size", "sausage_mcmuffin", "sausage_burrito", "hash_browns",
                "mcdouble", "mcchicken", "chicken_nuggets", "french_fries"
        ));
        ViewsAndImageButtonInfos contents = getSpecifiedMenu(toAdd);

        toAdd = new ArrayList<>(Arrays.asList(
                "coca_cola_classic", "sprite", "hi_c_orange_lavaburst", "diet_coke",
                "sweet_tea", "unsweetened_iced_tea"
        ));
        ViewsAndImageButtonInfos n = getSpecifiedMenu(toAdd);
        contents.views.addAll(n.views);
        contents.imageButtonInfos.addAll(n.imageButtonInfos);
        return contents;
    }

    private ViewsAndImageButtonInfos createLayoutBurgers() {
        return createLayoutFromFirebase("burgers");
    }

    private ViewsAndImageButtonInfos createLayoutBreakfast() {
        return createLayoutFromFirebase("breakfast");
    }

    private ViewsAndImageButtonInfos createLayoutBakery() {
        ViewsAndImageButtonInfos contents = new ViewsAndImageButtonInfos();
        return contents;
    }

    private ViewsAndImageButtonInfos createLayoutCoffees() {
        return createLayoutFromFirebase("coffees");
    }

    private ViewsAndImageButtonInfos createLayoutMcNuggets() {
        return createLayoutFromFirebase("mcnuggets and meals");
    }

    private ViewsAndImageButtonInfos createLayoutChickenFish() {
        return createLayoutFromFirebase("chicken & fish sandwiches");
    }

    private ViewsAndImageButtonInfos createLayoutFriesAndSides() {
        return createLayoutFromFirebase("fries & sides");
    }

    private ViewsAndImageButtonInfos createLayoutHappyMeal() {
        return createLayoutFromFirebase("happy meal");
    }

    private ViewsAndImageButtonInfos createLayoutsweetsTreats() {
        return createLayoutFromFirebase("sweets & treats");
    }

    private ViewsAndImageButtonInfos createLayoutBeverages() {
        return createLayoutFromFirebase("beverages");
    }

    private ViewsAndImageButtonInfos createLayoutFromFirebase(String sectionName) {
        ViewsAndImageButtonInfos contents = new ViewsAndImageButtonInfos();
        ArrayList<String> added = new ArrayList<>();
        for(Restaurant rest : home.getRestaurantManager().getRestaurants()) {
            if(rest.getNameForFile().equalsIgnoreCase("mcdonalds")) {
                for(MenuSection section : rest.getMenuSections()) {
                    if(section.getName().equalsIgnoreCase(sectionName)) {
                        for (MenuItem item : section.getMenu()) {
                            if(!added.contains(item.getNameForFile())) {
                                String itemStr = rest.getNameForFile() + "_" + item.getNameForFile();
                                View x = ViewMaker.createBasicImageButton(getContext(), ViewMaker.WRAP_WRAP, itemStr, Gravity.CENTER, true);
                                if(x != null) {
                                    contents.views.add(x);
                                    contents.imageButtonInfos.add(new ImageButtonInfo((ImageButton) x, item.getName(), item.getNameForFile(), 0, item.getPrice()));
                                }
                                added.add(item.getNameForFile());
                            }
                        }
                    }
                }
                break;
            }
        }
        return contents;
    }

    private ViewsAndImageButtonInfos getSpecifiedMenu(ArrayList<String> toAdd) {
        ViewsAndImageButtonInfos contents = new ViewsAndImageButtonInfos();
        for(Restaurant rest : home.getRestaurantManager().getRestaurants()) {
            if (rest.getNameForFile().equalsIgnoreCase("mcdonalds")) {
                for(MenuSection section : rest.getMenuSections()) {
                    for (MenuItem item : section.getMenu()) {
                        if(toAdd.contains(item.getNameForFile().toLowerCase()) || toAdd.contains(item.getName().toLowerCase())) {
                            String itemStr = rest.getNameForFile() + "_" + item.getNameForFile();
                            View x = ViewMaker.createBasicImageButton(getContext(), ViewMaker.WRAP_WRAP, itemStr, Gravity.CENTER, true);
                            if(x != null) {
                                contents.views.add(x);
                                contents.imageButtonInfos.add(new ImageButtonInfo((ImageButton) x, item.getName(), item.getNameForFile(), 0, item.getPrice()));
                            }
                            toAdd.remove(item.getNameForFile().toLowerCase());
                        }
                    }
                }
            }
        }
        return contents;
    }
}
