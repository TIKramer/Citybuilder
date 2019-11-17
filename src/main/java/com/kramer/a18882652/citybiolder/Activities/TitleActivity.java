package com.kramer.a18882652.citybiolder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kramer.a18882652.citybiolder.Model.GameDataModel;
import com.kramer.a18882652.citybiolder.R;


/*This is the first screen display here
Just details of the app - name - auther
Buttons to other activities
*/


public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Button settingsBtn = findViewById(R.id.setting_btn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        Button restartBtn = findViewById(R.id.restartBtn);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               GameDataModel.getGameData(getApplicationContext()).reset(getApplicationContext());
            }
        });

        Button mapBtn = findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, GameActivity.class);
                GameDataModel model = GameDataModel.getGameData(getApplicationContext());
                model.loadMap();
                startActivity(intent);
            }
        });
    }
}
