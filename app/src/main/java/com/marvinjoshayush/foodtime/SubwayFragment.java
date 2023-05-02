package com.marvinjoshayush.foodtime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class SubwayFragment extends Fragment {
    private HomeActivity home;
    private View view;

    public SubwayFragment(HomeActivity home) {
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subway, container, false);
        setOnClickListeners();
        return view;
    }

    private void setOnClickListeners() {
        int[] btns = new int[]{
                R.id.subway_subwaySeries,       R.id.subway_classicSandwiches,  R.id.subway_freshMelts,
                R.id.subway_noBready,           R.id.subway_onlineExclusive ,   R.id.subway_wraps,
                R.id.subway_breakfast,          R.id.subway_salads,             R.id.subway_freshFit,
                R.id.subway_drinks
                // R.id.subway_personalPizza,      R.id.subway_sides,              R.id.subway_deserts,
        };

        for(int btn : btns) {
            view.findViewById(btn).setOnClickListener(item -> {
                ViewsAndImageButtonInfos layout = createLayoutForSubMenuFragment(btn);
                ImageView logo = ViewMaker.createBasicImageView(getContext(), ViewMaker.MATCH_WRAP(), "subway_menu_logo", Gravity.CENTER);
                Fragment frag = new SubMenuFragment(home, "Subway", "subway", logo, new SubwayFragment(home), layout.views, layout.imageButtonInfos);
                home.setFragment(frag);
            });
        }
    }

    private ViewsAndImageButtonInfos createLayoutForSubMenuFragment(int id) {
        switch(id) {
            case R.id.subway_subwaySeries:
                return createLayoutSubwaySeries();
            case R.id.subway_classicSandwiches:
                return createLayoutClassicSandwiches();
            case R.id.subway_freshMelts:
                return createLayoutFreshMelts();
            case R.id.subway_noBready:
                return createLayoutNoBready();
            case R.id.subway_onlineExclusive:
                return createLayoutOnlineExclusive();
            case R.id.subway_wraps:
                return createLayoutWraps();
            case R.id.subway_breakfast:
                return createLayoutBreakfast();
            case R.id.subway_salads:
                return createLayoutSalads();
            case R.id.subway_freshFit:
                return createLayoutFreshFit();
            /*
            case R.id.subway_personalPizza:
                return createLayoutPersonalPizza();
            case R.id.subway_sides:
                return createLayoutSides();
            case R.id.subway_deserts:
                return createLayoutDeserts();
             */
            default:    // R.id.subway_drinks
                return createLayoutDrinks();
        }
    }

    private ViewsAndImageButtonInfos createLayoutSubwaySeries() {
        ViewsAndImageButtonInfos rearrange = RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "subwaySeries");

        // Split to 2 arrays and sort by ascending number
        View[] arrangedViews = new View[12];
        ImageButtonInfo[] arrangedIbf = new ImageButtonInfo[12];
        for(int i = 0; i < rearrange.views.size(); i++) {
            int index = Integer.parseInt(rearrange.imageButtonInfos.get(i).getName().split(" ")[0]) - 1;
            arrangedViews[index] = rearrange.views.get(i);
            arrangedIbf[index] = rearrange.imageButtonInfos.get(i);
        }

        // Create new ViewsAndImageButtonInfos
        ViewsAndImageButtonInfos contents = new ViewsAndImageButtonInfos();
        for(int i = 0; i < arrangedViews.length; i++) {
            if(arrangedViews[i] != null) {
                contents.views.add(arrangedViews[i]);
                contents.imageButtonInfos.add(arrangedIbf[i]);
            }
        }

        return contents;
    }

    private ViewsAndImageButtonInfos createLayoutClassicSandwiches() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "classicSandwiches");
    }

    private ViewsAndImageButtonInfos createLayoutFreshMelts() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "freshMelts");
    }

    private ViewsAndImageButtonInfos createLayoutNoBready() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "noBreadyBowls");
    }

    private ViewsAndImageButtonInfos createLayoutOnlineExclusive() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "onlineExclusive");
    }

    private ViewsAndImageButtonInfos createLayoutWraps() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "wraps");
    }

    private ViewsAndImageButtonInfos createLayoutBreakfast() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "breakfast");
    }

    private ViewsAndImageButtonInfos createLayoutSalads() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "salad");
    }

    private ViewsAndImageButtonInfos createLayoutFreshFit() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "freshFitForKids");
    }

    private ViewsAndImageButtonInfos createLayoutPersonalPizza() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "personalPizza");
    }

    private ViewsAndImageButtonInfos createLayoutSides() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "sides");
    }

    private ViewsAndImageButtonInfos createLayoutDeserts() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "deserts");
    }

    private ViewsAndImageButtonInfos createLayoutDrinks() {
        return RestaurantManager.createLayoutFromFirebase(home, getContext(), "subway", "drinks");
    }
}
