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

class ViewsAndImageButtonInfos {
    public ArrayList<View> views;
    public ArrayList<ImageButtonInfo> imageButtonInfos;

    public ViewsAndImageButtonInfos() {
        views = new ArrayList<>();
        imageButtonInfos = new ArrayList<>();
    }
}

public class PandaExpressFragment extends Fragment {
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
        map.put(R.id.pandaExpress_plateBundle, 0f);
        map.put(R.id.pandaExpress_bowl, 0f);
        map.put(R.id.pandaExpress_plate, 0f);
        map.put(R.id.pandaExpress_biggerPlate, 0f);
        map.put(R.id.pandaExpress_familyMeal, 0f);
        map.put(R.id.pandaExpress_cubMeal, 0f);
        map.put(R.id.pandaExpress_aLaCarte, 0f);
        map.put(R.id.pandaExpress_appetizers, 0f);
        map.put(R.id.pandaExpress_drinks, 0f);

        for(Map.Entry<Integer, Float> entry : map.entrySet()) {
            view.findViewById(entry.getKey()).setOnClickListener(item -> {
                Fragment frag;
                ViewsAndImageButtonInfos layout = createLayoutForSubMenuFragment(entry.getKey());
                int[] maxSelections = getMaxSelections(entry.getKey());
                if(maxSelections != null) {
                    frag = new PandaExpressSubMenuFragment(home, layout.views, layout.imageButtonInfos, maxSelections, entry.getValue());
                } else {
                    frag = new PandaExpressSubMenuFragment(home, layout.views);
                }
                home.setFragment(frag);
            });
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
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 3", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Choose an Appetizer",
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        n = createLayoutAppetizers();
        vAndI.views.addAll(n.views);
        for(ImageButtonInfo x : n.imageButtonInfos) {
            x.section = 2;
        }
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);

        // Drinks
        // Title
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 4", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP,
                "Select flavor for included Medium Drink, or get a bottled drink",
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        n = createLayoutDrinks();
        vAndI.views.addAll(n.views);
        for(ImageButtonInfo x : n.imageButtonInfos) {
            x.section = 3;
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
        return createLayoutFromFirebase("cub meals");
    }

    private ViewsAndImageButtonInfos createLayoutALaCarte() {
        // Side + Entree
        ViewsAndImageButtonInfos vAndI = new ViewsAndImageButtonInfos();
        ViewsAndImageButtonInfos n = createLayoutFromFirebase("entrees");
        vAndI.views.addAll(n.views);
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);
        n = createLayoutFromFirebase("sides");
        vAndI.views.addAll(n.views);
        for(ImageButtonInfo x : n.imageButtonInfos) {
            x.section = 1;
        }
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);
        return vAndI;
    }

    private ViewsAndImageButtonInfos createLayoutAppetizers() {
        return createLayoutFromFirebase("appetizers");
    }

    private ViewsAndImageButtonInfos createLayoutDrinks() {
        return createLayoutFromFirebase("drinks");
    }

    // Bowl, Plate, BiggerPlate
    private ViewsAndImageButtonInfos createLayoutMain(String firstSub, String secondSub) { return createLayoutMain(firstSub, secondSub, new ViewsAndImageButtonInfos()); }
    private ViewsAndImageButtonInfos createLayoutMain(String firstSub, String secondSub, ViewsAndImageButtonInfos vAndI) {
        // Title
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 1", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, firstSub,
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        // Sides
        ViewsAndImageButtonInfos n = createLayoutFromFirebase("sides");
        vAndI.views.addAll(n.views);
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);

        // Title
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 2", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndI.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, secondSub,
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        // Entrees
        n = createLayoutFromFirebase("entrees");
        vAndI.views.addAll(n.views);
        for(ImageButtonInfo x : n.imageButtonInfos) {
            x.section = 1;
        }
        vAndI.imageButtonInfos.addAll(n.imageButtonInfos);

        return vAndI;
    }

    private ViewsAndImageButtonInfos createLayoutFromFirebase(String sectionName) {
        ViewsAndImageButtonInfos contents = new ViewsAndImageButtonInfos();
        for(Restaurant rest : home.getRestaurantManager().getRestaurants()) {
            if(rest.getNameForFile().equalsIgnoreCase("panda_express")) {
                for(MenuSection section : rest.getMenuSections()) {
                    if(section.getName().equalsIgnoreCase(sectionName)) {
                        for (MenuItem item : section.getMenu()) {
                            String itemStr = rest.getNameForFile() + "_" + item.getNameForFile();
                            View x = ViewMaker.createBasicImageButton(getContext(), ViewMaker.WRAP_WRAP, itemStr, Gravity.CENTER, true);
                            if(x != null) {
                                contents.views.add(x);
                                contents.imageButtonInfos.add(new ImageButtonInfo((ImageButton) x, item.getName(), item.getNameForFile(), 0, item.getPrice()));
                            }
                        }
                    }
                }
                break;
            }
        }
        return contents;
    }
}
