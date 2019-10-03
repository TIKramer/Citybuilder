package com.kramer.a18882652.citybiolder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RecyclerView recView = (RecyclerView) findViewById(R.id.settingsRecycler);


        recView.setHasFixedSize(true);
        //use  GridLayout manager
        recView.setLayoutManager(new LinearLayoutManager(SettingsActivity.this));

        recView.setAdapter(new SettingsAdapter(this, SettingsModel.getSettings()));

    }
}
