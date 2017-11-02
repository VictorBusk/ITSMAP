package com.assignment.victor.group07_itsmap17_assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    private Button btnRefresh, btnAdd;
    private TextView txtCity;
    ArrayAdapter<String> adapter;
    public static final String PREFERENCES_TODO = "TODO_List_Shared_Preferences";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list_activity);

        //Instantiate listView with all the persisted data
//        final ArrayList<Demo> demoList = new ArrayList<Demo>();
//        for(int i = 0; i < 1000; i++){
//            demoList.add(new Demo("Demo #" + (i+1), "Demo #" + (i+1) + " is a great demo"));
//        }


        //Add button pressed: Persist city name, add to list and clear textview
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    persistCity();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Refresh button pressed: Reload layout and reload listView (fresh data)
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CityListActivity.this, CityDetailsActivity.class);
                startActivity(in);
            }
        });

    }

    protected void persistCity() throws IOException {
        txtCity = (TextView) findViewById(R.id.tvAddCity);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(txtCity);
        stringBuilder.append("!");
        for(String s : reloadPreferences()) {
            stringBuilder.append(s);
            stringBuilder.append("!");
        }

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("words", stringBuilder.toString());
        editor.apply();
    }

    protected List<String> reloadPreferences() {
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        String wordString = settings.getString("words", "");
        String[] itemWords = wordString.split("!");
        List<String> items = new ArrayList<String>();
        for (int i=0; i<itemWords.length; i++) {
            items.add(itemWords[i]);
        }
        return items;
    }
}
