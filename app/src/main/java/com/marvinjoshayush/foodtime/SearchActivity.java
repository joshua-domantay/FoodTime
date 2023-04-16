package com.marvinjoshayush.foodtime;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ListView results_List;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set up search view listener
        SearchView searchView = (SearchView) findViewById(R.id.Searching);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_box, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });

        return true;
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("Apple");
        data.add("Banana");
        data.add("Cherry");
        data.add("Durian");
        data.add("Elderberry");
        data.add("Fig");
        data.add("Grape");
        data.add("Honeydew");
        data.add("Iced Tea");
        data.add("Jackfruit");
        data.add("Kiwi");
        data.add("Lemon");
        data.add("Mango");
        data.add("Nectarine");
        data.add("Orange");
        data.add("Papaya");
        data.add("Quince");
        data.add("Raspberry");
        data.add("Strawberry");
        data.add("Tangerine");
        data.add("Ugli Fruit");
        data.add("Vanilla");
        data.add("Watermelon");
        data.add("Xigua");
        data.add("Yellow Squash");
        data.add("Zucchini");

        return data;
    }



    private void filterData(String query) {
        List<String> filteredData = new ArrayList<>();
        for (String item : getData()) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                filteredData.add(item);
            }
        }
        adapter.clear();
        adapter.addAll(filteredData);
    }
}