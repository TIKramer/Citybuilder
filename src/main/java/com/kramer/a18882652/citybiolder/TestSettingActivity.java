package com.kramer.a18882652.citybiolder;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class TestSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_setting);
        TextView test = findViewById(R.id.testText);
        GameDataModel model = GameDataModel.getGameData(this);
        Map<String,Setting> settings = model.getSettings();
        if(settings !=null) {
            for (String name: settings.keySet()){
                 String value = settings.get(name).getData().toString();
                test.append(name + " " + value + "'n");
            }
            }
        }


    }



