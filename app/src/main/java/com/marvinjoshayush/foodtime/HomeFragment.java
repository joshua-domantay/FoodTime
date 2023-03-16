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

/* Future plan
    Add Restaurants to FireBase
    Make a Restaurant class
    Make Restaurant List
    Get all* restaurants from FireBase      -> This will only work in a specific area instead of anywhere in the world
    Only add restaurants to list if preferred by user according to preference and allergies
 */
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
                setRestaurantsLayout(rest.getBanner(), rest.getName(), 0f, "Demo only");
            }
        }
    }

    // Future: Just one Restaurant class parameter
    // Add in ScrollView -> LinearLayout
    private void setRestaurantsLayout(int restBanner, String restName, float restDist, String restService) {
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
                case "Panda Express":
                    home.setFragment(new PandaExpressFragment(home));
                    break;
                case "Subway":
                    Log.d("TEST", "subwaywaywyay");
                    break;
                default:
                    Log.d("TEST", "HEISL");
            }
        });
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
}

