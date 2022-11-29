package com.marvinjoshayush.foodtime;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    private HomeActivity home;
    private View view;
    private LinearLayout scrollView;

    public HomeFragment(HomeActivity home) { this.home = home; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        scrollView = view.findViewById(R.id.homeSVLinear);

        setSortButtons();

        getRestaurants();
        setRestaurants();

        return view;
    }

    // Future use
    private void getRestaurants() { }

    // Change in the future (Just for demo)
    private void setRestaurants() {
        DemoRestaurants rest = new DemoRestaurants();

        // setRestaurantsH(rest.banners[0], rest.restaurants[0], rest.distances[0], rest.services[0], rest.services[0]);
        for(int i = 0; i < rest.restaurants.length; i++) {
            setRestaurantsH(rest.banners[i], rest.restaurants[i], rest.distances[i], rest.services[i], rest.menu[i]);
        }
    }

    // Add in ScrollView -> LinearLayout
    private void setRestaurantsH(int restBanner, String restName, float restDist, String restService, String restShortMenu) {
        LinearLayout.LayoutParams matchWrap = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // LinearLayout parent
        LinearLayout linearParent = new LinearLayout(getContext());
        linearParent.setLayoutParams(matchWrap);
        linearParent.setPadding(0, 0, 0, dpToPix(30));
        linearParent.setOrientation(LinearLayout.VERTICAL);

        // ImageView (banner)
        ImageView img = new ImageView(getContext());
        img.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPix(150)
        ));
        img.setImageResource(restBanner);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // img.setAdjustViewBounds(true);

        // TextView (name)
        TextView tvName = new TextView(getContext());
        tvName.setLayoutParams(matchWrap);
        tvName.setText(restName);
        tvName.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

        // LinearLayout (distance and services)
        LinearLayout linearChild = new LinearLayout(getContext());
        linearChild.setLayoutParams(matchWrap);
        linearChild.setOrientation(LinearLayout.HORIZONTAL);
        linearChild.setWeightSum(7f);

        // TextView (distance)
        TextView tvDist = new TextView(getContext());
        LinearLayout.LayoutParams tvDistParams = new LinearLayout.LayoutParams(
                dpToPix(0),
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
        );
        tvDist.setLayoutParams(tvDistParams);
        tvDist.setText(Float.toString(restDist) + " mi");
        tvDist.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        tvDist.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tvDist.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        linearChild.addView(tvDist);        // Add to linearChild

        // TextView (services)
        TextView tvService = new TextView(getContext());
        LinearLayout.LayoutParams tvServiceParams = new LinearLayout.LayoutParams(
                dpToPix(0),
                LinearLayout.LayoutParams.WRAP_CONTENT,
                5f
        );
        tvService.setLayoutParams(tvServiceParams);
        tvService.setText(restService);
        tvService.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        tvService.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tvService.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        linearChild.addView(tvService);     // Add to linearChild

        // TextView (name)
        TextView tvMenu = new TextView(getContext());
        tvMenu.setLayoutParams(matchWrap);
        tvMenu.setText(restShortMenu);
        tvMenu.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        tvMenu.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvMenu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        // Add everything to linearParent
        linearParent.addView(img);
        linearParent.addView(tvName);
        linearParent.addView(linearChild);
        linearParent.addView(tvMenu);

        // Add to the LinearLayout from ScrollView
        scrollView.addView(linearParent);
    }

    // For setRestaurantsH()
    private int dpToPix(int dp) {
        int pix = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
        return pix;
    }

    // Future use
    private void setSortButtons() {
        Button btn = view.findViewById(R.id.homeSortDelivery);
        btn.setOnClickListener(item -> {

        });

        btn = view.findViewById(R.id.homeSortPickUp);
        btn.setOnClickListener(item -> {

        });

        /*
        btn = view.findViewById(R.id.homeSortFastFood);
        btn.setOnClickListener(item -> {

        });

        btn = view.findViewById(R.id.homeSortRestaurant);
        btn.setOnClickListener(item -> {

        });
         */
    }
}

