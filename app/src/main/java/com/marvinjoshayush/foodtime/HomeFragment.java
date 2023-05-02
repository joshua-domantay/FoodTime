package com.marvinjoshayush.foodtime;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class HomeFragment extends Fragment {
    private String dietPreference;
    private String[] dietAllergies;

    private HomeActivity home;
    private View view;
    private LinearLayout scrollView;

    public HomeFragment(HomeActivity home) {
        this.home = home;
        // dietAllergies = new String[]{};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        scrollView = view.findViewById(R.id.homeSVLinear);

        setSortButtons();
        getChoices();
        setRestaurants();

        return view;
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

    private void getChoices() {
        /*
        // Testing
        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String theChoice = snapshot.child("Choices").getValue(String.class);
                String theAllergies = snapshot.child("Allergies").getValue(String.class);

                dietPreference = theChoice;
                if(dietPreference == null) { dietPreference = ""; }
                dietAllergies = theAllergies.split("&");
                // setRestaurants();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
         */
    }

    public void setRestaurants() {
        if(home.getRestaurantManager().getRestaurants().size() > 0) {
            for (int i = 0; i < home.getRestaurantManager().getRestaurants().size(); i++) {
                Restaurant rest = home.getRestaurantManager().getRestaurants().get(i);
                setRestaurantsLayout(rest);
            }
        }
    }

    // Future: Just one Restaurant class parameter
    // Add in ScrollView -> LinearLayout
    private void setRestaurantsLayout(Restaurant rest) {
        int restBanner = rest.getBanner();
        String restName = rest.getName();
        double restDist = rest.getDistance();
        long restRating = rest.getRating();
        long restReviews = rest.getReviews();

        // LinearLayout parent
        LinearLayout linearParent = new LinearLayout(getContext());
        linearParent.setLayoutParams(ViewMaker.MATCH_WRAP());
        linearParent.setPadding(0, 0, 0, ViewMaker.dpToPix(getResources(), 30));
        linearParent.setOrientation(LinearLayout.VERTICAL);

        // ImageView (banner)
        ImageView img = new ImageView(getContext());
        img.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewMaker.dpToPix(getResources(), 150)
        ));
        img.setImageResource(restBanner);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // img.setAdjustViewBounds(true);

        // TextView (name)
        TextView tvName = ViewMaker.createBasicTextView(getContext(), ViewMaker.MATCH_WRAP(), restName, R.color.black, 24);

        // LinearLayout (distance and services)
        LinearLayout linearChild = new LinearLayout(getContext());
        linearChild.setLayoutParams(ViewMaker.MATCH_WRAP());
        linearChild.setOrientation(LinearLayout.HORIZONTAL);
        linearChild.setWeightSum(2f);

        // TextView (distance)
        LinearLayout.LayoutParams tvDistParams = new LinearLayout.LayoutParams(
                ViewMaker.dpToPix(getResources(), 0),
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        );
        String distStr = "";
        if(restDist <= 0.1) {
            distStr = "On Campus";
        } else {
            distStr = Double.toString(restDist) + " mi";
        }
        TextView tvDist = ViewMaker.createBasicTextView(getContext(), tvDistParams, distStr,  R.color.black, 20);
        linearChild.addView(tvDist);        // Add to linearChild

        // TextView (services)
        LinearLayout.LayoutParams tvRatingReviewsParams = new LinearLayout.LayoutParams(
                ViewMaker.dpToPix(getResources(), 0),
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        );
        String tvRatingReviewsText = "Rating: " + restRating + "/10\n" + restReviews + " reviews";
        TextView tvRatingReviews = ViewMaker.createBasicTextView(getContext(), tvRatingReviewsParams, tvRatingReviewsText, R.color.black, 20, TextView.TEXT_ALIGNMENT_TEXT_END);
        linearChild.addView(tvRatingReviews);       // Add to linearChild

        /*
        // TextView (name)
        TextView tvMenu = new TextView(getContext());
        tvMenu.setLayoutParams(matchWrap);
        tvMenu.setText(restShortMenu);
        tvMenu.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        tvMenu.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvMenu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        */

        // Add everything to linearParent
        linearParent.addView(img);
        linearParent.addView(tvName);
        linearParent.addView(linearChild);
        // linearParent.addView(tvMenu);

        // Add to the LinearLayout from ScrollView
        scrollView.addView(linearParent);

        setRestaurantsOnClick(linearParent, restName);
    }

    private void setRestaurantsOnClick(LinearLayout restLayout, String restName) {
        restLayout.setOnClickListener(item -> {
            switch(restName) {
                case "McDonalds":
                    home.setFragment(new McDonaldsFragment(home));
                    break;
                case "Panda Express":
                    home.setFragment(new PandaExpressFragment(home));
                    break;
                case "Subway":
                    home.setFragment(new SubwayFragment(home));
                    break;
                default:
                    Log.d("TEST", "HEISL");
            }
        });
    }
}

