package com.marvinjoshayush.foodtime;

import android.graphics.Typeface;
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

class ViewsAndHashMap {
    public ArrayList<View> views;
    public HashMap<String, ImageButtonInfo> hMap;

    public ViewsAndHashMap() {
        views = new ArrayList<>();
        hMap = new HashMap<>();
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
        /*
        int[] btns = new int[]{
                R.id.pandaExpress_plateBundle,  R.id.pandaExpress_bowl,         R.id.pandaExpress_plate,
                R.id.pandaExpress_biggerPlate,  R.id.pandaExpress_familyMeal,   R.id.pandaExpress_cubMeal,
                R.id.pandaExpress_aLaCarte,     R.id.pandaExpress_appetizers,   R.id.pandaExpress_drinks
        };

        for(int btn : btns) {
            view.findViewById(btn).setOnClickListener(item -> {
                ArrayList<View> layout = createLayoutForSubMenuFragment(btn);
                Fragment frag = new PandaExpressSubMenuFragment(home, layout);
                home.setFragment(frag);
            });
        }
         */

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
                ViewsAndHashMap layout = createLayoutForSubMenuFragment(entry.getKey());
                int[] maxSelections = getMaxSelections(entry.getKey());
                if(maxSelections != null) {
                    frag = new PandaExpressSubMenuFragment(home, layout.views, layout.hMap, maxSelections, entry.getValue());
                } else {
                    frag = new PandaExpressSubMenuFragment(home, layout.views);
                }
                home.setFragment(frag);
            });
        }

        /*
        view.findViewById(R.id.pandaExpress_plateBundle).setOnClickListener(item -> {

        });

        view.findViewById(R.id.pandaExpress_bowl).setOnClickListener(item -> {

        });

        view.findViewById(R.id.pandaExpress_plate).setOnClickListener(item -> {

        });

        view.findViewById(R.id.pandaExpress_biggerPlate).setOnClickListener(item -> {

        });

        view.findViewById(R.id.pandaExpress_familyMeal).setOnClickListener(item -> {

        });

        view.findViewById(R.id.pandaExpress_cubMeal).setOnClickListener(item -> {

        });

        view.findViewById(R.id.pandaExpress_aLaCarte).setOnClickListener(item -> {

        });

        view.findViewById(R.id.pandaExpress_appetizers).setOnClickListener(item -> {

        });

        view.findViewById(R.id.pandaExpress_drinks).setOnClickListener(item -> {

        });
        */
    }

    private int[] getMaxSelections(int id) {
        switch(id) {
            case R.id.pandaExpress_plateBundle:
                return null;
            case R.id.pandaExpress_bowl:
                return null;
            case R.id.pandaExpress_plate:
                return null;
            case R.id.pandaExpress_biggerPlate:
                return null;
            case R.id.pandaExpress_familyMeal:
                return null;
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

    private ViewsAndHashMap createLayoutForSubMenuFragment(int id) {
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

    private ViewsAndHashMap createLayoutPlateBundle() {
        // Side, Entree, Appetizer, Drink
        ViewsAndHashMap vAndH = new ViewsAndHashMap();
        ViewsAndHashMap n = createLayoutPlate();
        vAndH.views.addAll(n.views);
        for(Map.Entry<String, ImageButtonInfo> entry : n.hMap.entrySet()) {
            vAndH.hMap.put(entry.getKey(), entry.getValue());
        }

        // Appetizers
        // Title
        vAndH.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 3", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndH.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Choose an Appetizer",
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        n = createLayoutAppetizers();
        vAndH.views.addAll(n.views);
        for(Map.Entry<String, ImageButtonInfo> entry : n.hMap.entrySet()) {
            vAndH.hMap.put(entry.getKey(), entry.getValue());
        }

        // Drinks
        // Title
        vAndH.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 4", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndH.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP,
                "Select flavor for included Medium Drink, or get a bottled drink",
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        n = createLayoutDrinks();
        vAndH.views.addAll(n.views);
        for(Map.Entry<String, ImageButtonInfo> entry : n.hMap.entrySet()) {
            vAndH.hMap.put(entry.getKey(), entry.getValue());
        }
        return vAndH;
    }

    private ViewsAndHashMap createLayoutBowl() {
        // Side, 1 Entree
        return createLayoutMain("Choose a Side, or Get Half and Half", "Choose an Entree");
    }

    private ViewsAndHashMap createLayoutPlate() {
        // Side, 2 Entree
        return createLayoutMain("Choose a Side, or Get Half and Half", "Choose two Entrees");
    }

    private ViewsAndHashMap createLayoutBiggerPlate() {
        // Side, 3 Entree
        return createLayoutMain("Choose a Side, or Get Half and Half", "Choose three Entrees");
    }

    private ViewsAndHashMap createLayoutFamilyMeal() {
        // 2 Side, 3 Entree
        return createLayoutMain("Choose two Sides", "Choose three Entrees");
    }

    private ViewsAndHashMap createLayoutCubMeals() {
        return createLayoutFromFirebase("cub meals");
    }

    private ViewsAndHashMap createLayoutALaCarte() {
        // Side + Entree
        ViewsAndHashMap vAndH = new ViewsAndHashMap();
        ViewsAndHashMap n = createLayoutFromFirebase("entrees");
        vAndH.views.addAll(n.views);
        for(Map.Entry<String, ImageButtonInfo> entry : n.hMap.entrySet()) {
            vAndH.hMap.put(entry.getKey(), entry.getValue());
        }
        n = createLayoutFromFirebase("sides");
        vAndH.views.addAll(n.views);
        for(Map.Entry<String, ImageButtonInfo> entry : n.hMap.entrySet()) {
            vAndH.hMap.put(entry.getKey(), entry.getValue());
        }
        return vAndH;
    }

    private ViewsAndHashMap createLayoutAppetizers() {
        return createLayoutFromFirebase("appetizers");
    }

    private ViewsAndHashMap createLayoutDrinks() {
        return createLayoutFromFirebase("drinks");
    }

    // Bowl, Plate, BiggerPlate
    private ViewsAndHashMap createLayoutMain(String firstSub, String secondSub) { return createLayoutMain(firstSub, secondSub, new ViewsAndHashMap()); }
    private ViewsAndHashMap createLayoutMain(String firstSub, String secondSub, ViewsAndHashMap vAndH) {
        // Title
        vAndH.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 1", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndH.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, firstSub,
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        // Sides
        ViewsAndHashMap n = createLayoutFromFirebase("sides");
        vAndH.views.addAll(n.views);
        for(Map.Entry<String, ImageButtonInfo> entry : n.hMap.entrySet()) {
            vAndH.hMap.put(entry.getKey(), entry.getValue());
        }

        // Title
        vAndH.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 2", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        vAndH.views.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, secondSub,
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        // Entrees
        n = createLayoutFromFirebase("entrees");
        vAndH.views.addAll(n.views);
        for(Map.Entry<String, ImageButtonInfo> entry : n.hMap.entrySet()) {
            vAndH.hMap.put(entry.getKey(), entry.getValue());
        }

        return vAndH;
    }

    private ViewsAndHashMap createLayoutFromFirebase(String sectionName) {
        ViewsAndHashMap contents = new ViewsAndHashMap();
        for(Restaurant rest : home.getRestaurantManager().getRestaurants()) {
            if(rest.getNameForFile().equalsIgnoreCase("panda_express")) {
                for(MenuSection section : rest.getMenuSections()) {
                    if(section.getName().equalsIgnoreCase(sectionName)) {
                        for (MenuItem item : section.getMenu()) {
                            String itemStr = rest.getNameForFile() + "_" + item.getNameForFile();
                            View x = ViewMaker.createBasicImageButton(getContext(), ViewMaker.WRAP_WRAP, itemStr, Gravity.CENTER, true);
                            if(x != null) {
                                contents.views.add(x);
                                contents.hMap.put(item.getNameForFile(), new ImageButtonInfo(item.getName(), item.getNameForFile(), item.getPrice()));
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
