package com.marvinjoshayush.foodtime;

import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PandaExpressFragment extends Fragment {
    private HomeActivity home;
    private View view;

    public PandaExpressFragment(HomeActivity home) {
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_panda_express, container, false);

        setOnClickListeners();

        return view;
    }

    private void setOnClickListeners() {
        HashMap<Integer, Float> map = new HashMap<>();
        map.put(R.id.pandaExpress_plateBundle, 13.30f);
        map.put(R.id.pandaExpress_bowl, 8.40f);
        map.put(R.id.pandaExpress_plate, 9.90f);
        map.put(R.id.pandaExpress_biggerPlate, 11.40f);
        map.put(R.id.pandaExpress_familyMeal, 35.00f);
        map.put(R.id.pandaExpress_cubMeal, 0f);
        map.put(R.id.pandaExpress_aLaCarte, 5.20f);
        map.put(R.id.pandaExpress_appetizers, 0f);
        map.put(R.id.pandaExpress_drinks, 0f);

        for(Map.Entry<Integer, Float> entry : map.entrySet()) {
            view.findViewById(entry.getKey()).setOnClickListener(item -> {
                Fragment frag;
                ViewsAndImageButtonInfos layout = createLayoutForSubMenuFragment(entry.getKey());
                int[] maxSelections = getMaxSelections(entry.getKey());
                if(maxSelections != null) {
                    String itemName = getItemName(entry.getKey());
                    String imageStr = getImageStr(entry.getKey());
                    frag = new PandaExpressSubMenuFragment(home, layout.views, layout.imageButtonInfos, entry.getValue(), maxSelections, itemName, imageStr);
                } else {
                    frag = new PandaExpressSubMenuFragment(home, layout.views, layout.imageButtonInfos, entry.getValue());
                }
                home.setFragment(frag);
            });
        }
    }

    private String getItemName(int id) {
        switch(id) {
            case R.id.pandaExpress_plateBundle:
                return "Plate Bundle";
            case R.id.pandaExpress_bowl:
                return "Bowl";
            case R.id.pandaExpress_plate:
                return "Plate";
            case R.id.pandaExpress_biggerPlate:
                return "Bigger Plate";
            case R.id.pandaExpress_familyMeal:
                return "Family Meal";
            default:    // R.id.pandaExpress_drinks
                return "";
        }
    }

    private String getImageStr(int id) {
        switch(id) {
            case R.id.pandaExpress_plateBundle:
                return "panda_express_plate";
            case R.id.pandaExpress_bowl:
                return "panda_express_bowl";
            case R.id.pandaExpress_plate:
                return "panda_express_plate2";
            case R.id.pandaExpress_biggerPlate:
                return "panda_express_bigger_plate";
            case R.id.pandaExpress_familyMeal:
                return "family_meal";
            default:    // R.id.pandaExpress_drinks
                return "";
        }
    }

    private int[] getMaxSelections(int id) {
        switch(id) {
            case R.id.pandaExpress_plateBundle:
                return new int[]{2, 2, 1, 1};
            case R.id.pandaExpress_bowl:
                return new int[]{2, 1};
            case R.id.pandaExpress_plate:
                return new int[]{2, 2};
            case R.id.pandaExpress_biggerPlate:
            case R.id.pandaExpress_familyMeal:
                return new int[]{2, 3};
            case R.id.pandaExpress_cubMeal:
                return null;
            case R.id.pandaExpress_aLaCarte:
                return null;
            case R.id.pandaExpress_appetizers:
                return null;
            default:    // R.id.pandaExpress_drinks
                return null;
        }
    }

    private ViewsAndImageButtonInfos createLayoutForSubMenuFragment(int id) {
        switch(id) {
            case R.id.pandaExpress_plateBundle:
                return createLayoutPlateBundle();
            case R.id.pandaExpress_bowl:
                return createLayoutBowl();
            case R.id.pandaExpress_plate:
                return createLayoutPlate();
            case R.id.pandaExpress_biggerPlate:
                return createLayoutBiggerPlate();
            case R.id.pandaExpress_familyMeal:
                return createLayoutFamilyMeal();
            case R.id.pandaExpress_cubMeal:
                return createLayoutCubMeals();
            case R.id.pandaExpress_aLaCarte:
                return createLayoutALaCarte();
            case R.id.pandaExpress_appetizers:
                return createLayoutAppetizers();
            default:    // R.id.pandaExpress_drinks
                return createLayoutDrinks();
        }
    }

    private ViewsAndImageButtonInfos createLayoutPlateBundle() {
        // Side, Entree, Appetizer, Drink
        ViewsAndImageButtonInfos vAndI = new ViewsAndImageButtonInfos();
        ViewsAndImageButtonInfos n = createLayoutPlate();
        vAndI.views.addAll(n.views);
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);

        // Appetizers
        // Title
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(), "Step 3", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(), "Choose an Appetizer",
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        n = createLayoutAppetizers();
        vAndI.views.addAll(n.views);
        for(ImageButtonInfo x : n.imageButtonInfos) {
            x.section = 2;
            x.setPrice(0);
        }
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);

        // Drinks
        // Title
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(), "Step 4", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(),
                "Select flavor for included Medium Drink, or get a bottled drink",
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        n = createLayoutDrinks();
        vAndI.views.addAll(n.views);
        for(ImageButtonInfo x : n.imageButtonInfos) {
            x.section = 3;
            x.setPrice(0);
        }
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);
        return vAndI;
    }

    private ViewsAndImageButtonInfos createLayoutBowl() {
        // Side, 1 Entree
        return createLayoutMain("Choose a Side, or Get Half and Half", "Choose an Entree");
    }

    private ViewsAndImageButtonInfos createLayoutPlate() {
        // Side, 2 Entree
        return createLayoutMain("Choose a Side, or Get Half and Half", "Choose two Entrees");
    }

    private ViewsAndImageButtonInfos createLayoutBiggerPlate() {
        // Side, 3 Entree
        return createLayoutMain("Choose a Side, or Get Half and Half", "Choose three Entrees");
    }

    private ViewsAndImageButtonInfos createLayoutFamilyMeal() {
        // 2 Side, 3 Entree
        return createLayoutMain("Choose two Sides", "Choose three Entrees");
    }

    private ViewsAndImageButtonInfos createLayoutCubMeals() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "panda_express", "cub meals");
    }

    private ViewsAndImageButtonInfos createLayoutALaCarte() {
        // Side + Entree
        ViewsAndImageButtonInfos vAndI = new ViewsAndImageButtonInfos();
        ViewsAndImageButtonInfos n = RestaurantManager.createLayoutFromFirebase(home, getContext(), "panda_express", "entrees");
        vAndI.views.addAll(n.views);
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);
        n = RestaurantManager.createLayoutFromFirebase(home, getContext(), "panda_express", "sides");
        vAndI.views.addAll(n.views);
        for(ImageButtonInfo x : n.imageButtonInfos) {
            x.section = 1;
        }
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);
        return vAndI;
    }

    private ViewsAndImageButtonInfos createLayoutAppetizers() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "panda_express", "appetizers");
    }

    private ViewsAndImageButtonInfos createLayoutDrinks() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "panda_express", "drinks");
    }

    // Bowl, Plate, BiggerPlate
    private ViewsAndImageButtonInfos createLayoutMain(String firstSub, String secondSub) { return createLayoutMain(firstSub, secondSub, new ViewsAndImageButtonInfos()); }
    private ViewsAndImageButtonInfos createLayoutMain(String firstSub, String secondSub, ViewsAndImageButtonInfos vAndI) {
        // Title
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(), "Step 1", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(), firstSub,
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        // Sides
        ViewsAndImageButtonInfos n = RestaurantManager.createLayoutFromFirebase(home, getContext(), "panda_express", "sides");
        vAndI.views.addAll(n.views);
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);

        // Title
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(), "Step 2", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(), secondSub,
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        // Entrees
        n = RestaurantManager.createLayoutFromFirebase(home, getContext(), "panda_express", "entrees");
        vAndI.views.addAll(n.views);
        for(ImageButtonInfo x : n.imageButtonInfos) {
            x.section = 1;
        }
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);

        return vAndI;
    }
}
