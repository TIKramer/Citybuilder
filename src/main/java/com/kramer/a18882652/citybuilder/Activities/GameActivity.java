package com.kramer.a18882652.citybuilder.Activities;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.kramer.a18882652.citybuilder.Adapters.MapAdapter;
import com.kramer.a18882652.citybuilder.Database.GameDataSchema;
import com.kramer.a18882652.citybuilder.Fragments.MapFragment;
import com.kramer.a18882652.citybuilder.Fragments.SelectorFragment;
import com.kramer.a18882652.citybuilder.Fragments.UserStatusFragment;
import com.kramer.a18882652.citybuilder.Model.GameDataModel;
import com.kramer.a18882652.citybuilder.Model.SettingsModel;
import com.kramer.a18882652.citybuilder.R;

import java.util.Timer;
import java.util.TimerTask;

/* This is the main activity where all the game logic takes place */


public class GameActivity extends AppCompatActivity implements SelectorFragment.OnMapItemClickListener, MapAdapter.StructureUpdateListener, MapAdapter.PlayerCashCallBack, SelectorFragment.OnDetailsChangeListener, LifecycleObserver  {


    private int gameTime;
    private double employmentRate;
    private int population;
    private int cash;
   private int salary;
    private double taxRate;
    private int serviceCost;
   private int familySize;
    private int nResidential;
    private int nCommerical;
    private int nRoads;
    private int shopSize;
   private Timer timer;

    private UserStatusFragment userStatusFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        intialValues();

        GameDataModel model = GameDataModel.getGameData(this);
        cash = model.getUserMoney();
        gameTime = model.getGameTime();
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFrag = (MapFragment) fm.findFragmentById(R.id.map_container);
        SelectorFragment selectorFrag = (SelectorFragment) fm.findFragmentById(R.id.selector_container);
        userStatusFragment = (UserStatusFragment) fm.findFragmentById(R.id.userStatusContainer);


        if(mapFrag == null)
        {
            mapFrag = new MapFragment();
            fm.beginTransaction().add(R.id.map_container, mapFrag).commit();
        }
        if(selectorFrag ==null)
        {
            selectorFrag = new SelectorFragment();
            fm.beginTransaction().add(R.id.selector_container,selectorFrag).commit();
        }
        if(userStatusFragment ==null)
        {
            userStatusFragment = new UserStatusFragment();
            fm.beginTransaction().add(R.id.userStatusContainer,userStatusFragment).commit();
        }
        if(timer!=null) {
            timer.cancel();
            timer =null;
        }
        if(timer == null) {
            createTimer();
        }
    }
//This creates a timer that increments time every second
    //After every second it does the calculation for income and adds it to cash
    //It then updates the fields in the fragments to display
    private void createTimer()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                // Your code

                gameTime++;
                new Handler(Looper.getMainLooper().getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        userStatusFragment.updateTime(gameTime);
                        //     userStatusFragment.updateCash(nResidential);
                        population = familySize * nResidential;
                        if(population > 0)
                            employmentRate = Math.min(1, (double)(nCommerical * shopSize) / (double)population);
                        else
                            employmentRate = 0;
                        int income = 0;
                        income += population * (employmentRate * salary * taxRate - serviceCost);
                        cash += income;
                        userStatusFragment.updateCash(cash);
                        userStatusFragment.updateIncome(income);
                        userStatusFragment.updatePopulation(population);
                        userStatusFragment.updateEmploymentRate(employmentRate * 100);
                        if(cash < 0)
                        {
                            gameOver();
                        }

                    }
                });


            }
        }, 1000, 1000);

    }
    private void gameOver() {
        timer.cancel();
        new AlertDialog.Builder(this)
                .setTitle("GAME OVER")
                .setMessage("You can still view your world but no longer build")


                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })




                .show();
    }
    //When the class starts load these values in from the database.
    private  void intialValues()
    {
        SettingsModel settings = SettingsModel.getInstance();
        taxRate = Double.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.TAX_RATE));
        familySize = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.FAMILY_SIZE));
        salary = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SALARY));
        serviceCost = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SERVICE_COST));
        shopSize = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SHOP_SIZE));

    }


    //This just puts reference to the callbacks into the fragments
//Could later set call back to one set for each fragment

    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        if (fragment instanceof SelectorFragment) {
            SelectorFragment selectorFragment = (SelectorFragment) fragment;
            selectorFragment.setOnMapItemClickListener(this);
            selectorFragment.setOnDetailsChangeListener(this);
        }
        else if(fragment instanceof MapFragment)
        {
            MapFragment headlinesFragment = (MapFragment) fragment;
            headlinesFragment.setStructureUpdateListener(this);
            headlinesFragment.setCashCallBack(this);



        }
    }


//Listeners and call backs


    @Override
    public void demolishBuilding(boolean value) {
        {
            MapFragment articleFrag = (MapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map_container);

            if (articleFrag != null) {

                articleFrag.setDemolish(value);
            }
            }
        }


    @Override
    public void roadsUpdateListener(int x) {
        nRoads = x;
    }

    @Override
    public void commercialUpdateListener(int x) {
        nCommerical = x;
    }
    @Override
    public void residentialUpdateListener(int x) {
        nResidential = x;
    }

    @Override
    public Integer getCash() {
        return cash;
    }



    @Override
    public void detailsChangeListener(boolean change)
    {
        MapFragment articleFrag = (MapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_container);

        if (articleFrag != null) {

            articleFrag.setDetailsMode(change);
        }
    }

    /*Below is only saving and resuming parts of code.
//Everything here works in different ways of stopping the app besides a complete crash hitting the stop button
// some may be redundant
Once a timer is cancled it cant be restarted so also must set it to null
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        GameDataModel model = GameDataModel.getGameData(this);
        model.setUserMoney(cash);
        model.setGameTime(gameTime);
        if(timer!=null) {
            timer.cancel();
            timer =null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void saveData()
    {
        GameDataModel model = GameDataModel.getGameData(this);
        model.setUserMoney(cash);
        model.setGameTime(gameTime);

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void restoreData()
    {
        GameDataModel model = GameDataModel.getGameData(this);

        cash = model.getUserMoney();
        gameTime = model.getGameTime();
        if(timer == null) {
            createTimer();
        }


    }

    @Override
    protected void onResume() {
        // call the superclass method first
        super.onResume();
        if(timer == null) {
            createTimer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        GameDataModel model = GameDataModel.getGameData(this);
        model.setUserMoney(cash);
        model.setGameTime(gameTime);
        model.setStructureNumber(nResidential, nCommerical, nRoads);

        if(timer!=null) {
            timer.cancel();
            timer =null;
        }
        model.saveGameData();
        model.saveSettings();
    }
    @Override
    public void onPause() {
        super.onPause();
        GameDataModel model = GameDataModel.getGameData(this);
        model.setUserMoney(cash);
        model.setGameTime(gameTime);
        model.setStructureNumber(nResidential, nCommerical, nRoads);

        if(timer!=null) {
            timer.cancel();
            timer =null;
        }
        model.saveGameData();
        model.saveSettings();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("cash", cash);
        savedInstanceState.putInt("time", gameTime);


    }








}


