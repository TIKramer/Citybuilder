package com.kramer.a18882652.citybiolder.Activities;

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
import android.widget.Toast;

import com.kramer.a18882652.citybiolder.Database.GameDataSchema;
import com.kramer.a18882652.citybiolder.Adapters.MapAdapter;
import com.kramer.a18882652.citybiolder.Fragments.MapFragment;
import com.kramer.a18882652.citybiolder.Model.GameDataModel;
import com.kramer.a18882652.citybiolder.R;
import com.kramer.a18882652.citybiolder.Fragments.SelectorFragment;
import com.kramer.a18882652.citybiolder.Model.SettingsModel;
import com.kramer.a18882652.citybiolder.Fragments.UserStatusFragment;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements SelectorFragment.OnMapItemClickListener, MapAdapter.StructureUpdateListener, MapAdapter.PlayerCashCallBack, SelectorFragment.OnDetailsChangeListener, LifecycleObserver  {


    private int gameTime;
    private double employmentRate;
    private int population;
    private int cash;
    int salary;
    double taxRate;
    int serviceCost;
    int familySize;
    int nResidential;
    int nCommerical;
    int shopSize;
   private Timer timer;

    private UserStatusFragment userStatusFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        intialValues();
        GameDataModel model = GameDataModel.getGameData(this);
        if(model.getUserMoney() > 0) {
            cash = model.getUserMoney();
        }
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
        createTimer();

    }

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

                        if (gameTime % 1 == 0) {
                            population = familySize * nResidential;
                            if (population > 0) {
                                employmentRate = Math.min(1, nCommerical * shopSize / population);
                            } else
                                employmentRate = 0;
                            int income = 0;
                            income += (Integer) population * (employmentRate * salary * taxRate - serviceCost);
                            cash += income;
                            if (cash < 0) {
                                gameOver();
                            }
                            userStatusFragment.updateCash(cash);
                            userStatusFragment.updateIncome(income);

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
    private  void intialValues()
    {

        SettingsModel settings = SettingsModel.getInstance();
        if(cash == 0) {
            cash = Integer.valueOf("" + settings.getData(GameDataSchema.SettingsTable.Cols.INITIAL_MONEY));
        }

         taxRate = Double.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.TAX_RATE));
        familySize = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.FAMILY_SIZE));
        salary = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SALARY));
        serviceCost = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SERVICE_COST));
        shopSize = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SHOP_SIZE));

    }


    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        if (fragment instanceof SelectorFragment) {
            SelectorFragment headlinesFragment = (SelectorFragment) fragment;
            headlinesFragment.setOnMapItemClickListener(this);
            headlinesFragment.setOnDetailsChangeListener(this);
          //  headlinesFragment.set
        }
        else if(fragment instanceof MapFragment)
        {
            MapFragment headlinesFragment = (MapFragment) fragment;
            headlinesFragment.setStructureUpdateListener(this);
            headlinesFragment.setCashCallBack(this);



        }
    }





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
        nResidential = x;
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
    protected void onResume() {
        // call the superclass method first
        super.onResume();
        if(timer == null) {
            createTimer();
        }
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        timer.cancel();
        timer = null;
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("cash", cash);
        savedInstanceState.putInt("time", gameTime);
        //savedInstanceState.putInt("income", 1);
       // savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        GameDataModel model = GameDataModel.getGameData(this);
        model.setUserMoney(cash);
        model.setGameTime(gameTime);
        if(timer!=null) {
            timer.cancel();
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
    }



}


