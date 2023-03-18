package com.marvinjoshayush.foodtime;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

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
    }

    private ArrayList<View> createLayoutForSubMenuFragment(int id) {
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
                return createLayoutCubMeal();
            case R.id.pandaExpress_aLaCarte:
                return createLayoutALaCarte();
            case R.id.pandaExpress_appetizers:
                return createLayoutAppetizers();
            default:    // R.id.pandaExpress_drinks
                return createLayoutDrinks();
        }
    }

    /*
        <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="10dp"
        android:textSize="30sp"
        android:textAlignment="center"
        android:textColor="@color/black"
        android:textStyle="bold"
        android:text="Step 1"></TextView>

        // LinearLayout parent
        LinearLayout linearParent = new LinearLayout(getContext());
        linearParent.setLayoutParams(matchWrap);
        linearParent.setPadding(0, 0, 0, dpToPix(30));
        linearParent.setOrientation(LinearLayout.VERTICAL);
        */

    private ArrayList<View> createLayoutPlateBundle() {
        // Side, Entree, Appetizer, Drink
        ArrayList<View> contents = new ArrayList<>();
        contents.addAll(createLayoutPlate());
        contents.addAll(createLayoutAppetizers());
        contents.addAll(createLayoutDrinks());
        return contents;
    }

    private ArrayList<View> createLayoutBowl() {
        // Side, 1 Entree
        ArrayList<View> contents = new ArrayList<>();
        contents.addAll(createLayoutMain("Choose a Side, or Get Half and Half", "Choose an Entree"));
        return contents;
    }

    private ArrayList<View> createLayoutPlate() {
        // Side, 2 Entree
        ArrayList<View> contents = new ArrayList<>();
        contents.addAll(createLayoutMain("Choose a Side, or Get Half and Half", "Choose two Entrees"));
        return contents;
    }

    private ArrayList<View> createLayoutBiggerPlate() {
        // Side, 3 Entree
        ArrayList<View> contents = new ArrayList<>();
        contents.addAll(createLayoutMain("Choose a Side, or Get Half and Half", "Choose three Entrees"));
        return contents;
    }

    private ArrayList<View> createLayoutFamilyMeal() {
        // 2 Side, 3 Entree
        ArrayList<View> contents = new ArrayList<>();
        contents.addAll(createLayoutMain("Choose two Sides", "Choose three Entrees"));
        return contents;
    }

    private ArrayList<View> createLayoutCubMeal() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutALaCarte() {
        // Side + Entree
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutAppetizers() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutDrinks() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    // Bowl, Plate, BiggerPlate
    private ArrayList<View> createLayoutMain(String firstSub, String secondSub) {
        ArrayList<View> contents = new ArrayList<>();

        // Title
        contents.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 1", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        contents.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, firstSub,
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        // Sides
        //contents.addAll(createLayoutSides());
        // Title
        contents.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, "Step 2", R.color.black,
                30, TextView.TEXT_ALIGNMENT_CENTER, Typeface.BOLD, 0, 0, 0, 10));
        // Subtitle
        contents.add(ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP, secondSub,
                R.color.black, 24, TextView.TEXT_ALIGNMENT_CENTER));
        // Entrees
        //contents.addAll(createLayoutEntrees());

        return contents;
    }

    private ArrayList<View> createLayoutSides() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }

    private ArrayList<View> createLayoutEntrees() {
        ArrayList<View> contents = new ArrayList<>();
        return contents;
    }
}