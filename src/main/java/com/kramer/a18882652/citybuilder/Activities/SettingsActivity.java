package com.kramer.a18882652.citybuilder.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kramer.a18882652.citybuilder.Adapters.SettingsAdapter;
import com.kramer.a18882652.citybuilder.Model.SettingsModel;
import com.kramer.a18882652.citybuilder.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RecyclerView recView = (RecyclerView) findViewById(R.id.settingsRecycler);


        //use  GridLayout manager
        recView.setLayoutManager(new LinearLayoutManager(SettingsActivity.this));

        recView.setAdapter(new SettingsAdapter(this, SettingsModel.getInstance().getSettings()));

    }


}
