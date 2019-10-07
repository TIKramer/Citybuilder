package com.kramer.a18882652.citybiolder.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kramer.a18882652.citybiolder.Adapters.SettingsAdapter;
import com.kramer.a18882652.citybiolder.Model.GameDataModel;
import com.kramer.a18882652.citybiolder.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RecyclerView recView = (RecyclerView) findViewById(R.id.settingsRecycler);


        //use  GridLayout manager
        recView.setLayoutManager(new LinearLayoutManager(SettingsActivity.this));

        recView.setAdapter(new SettingsAdapter(this, GameDataModel.getGameData(this).getSettings()));

    }


}
