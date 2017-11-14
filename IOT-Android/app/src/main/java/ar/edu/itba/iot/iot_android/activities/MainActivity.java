package ar.edu.itba.iot.iot_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.itba.iot.iot_android.R;

import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ar.edu.itba.iot.iot_android.controller.UserController;
import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.view.DrawerHeader;
import ar.edu.itba.iot.iot_android.view.DrawerMenuItem;
import ar.edu.itba.iot.iot_android.view.MyAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> devicesNames = null;
    private List<String> targetTemps = null;
    private List<String> currentTemps = null;
    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private UserController userController;
    private User user;



    private Observer userChange = new Observer() {

        @Override
        public void update(Observable o, Object arg) {
            if(((String) arg).equals("token")) userController.getFullUserData();
            else if(((String) arg).equals("id")){
                userController.getDevices();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User("hjulian", "hjulian", Long.valueOf(4));

        user.addObserver(userChange);

        userController = new UserController(this, user);

        userController.login();



        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        View v = this.findViewById(R.id.addDevice);
        v.setOnClickListener(this);
        mDrawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView)findViewById(R.id.drawerView);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setupDrawer();

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("token", user.getToken());
        intent.putExtra("userId", user.getId());
        startActivityForResult(intent, 1);
    }

    public void populateAdapter() {
        int n = user.getDevices().size();

        devicesNames = new ArrayList<>(n);
        targetTemps = new ArrayList<>(n);
        currentTemps = new ArrayList<>(n);

        int i = 0;

        devicesNames.clear();
        currentTemps.clear();
        targetTemps.clear();

        for(Device d: user.getDevices()){
            devicesNames.add(i, d.getNickname());
            currentTemps.add(i, String.format("%.1f", d.getTemperature()));
            targetTemps.add(i, String.format("%.1f", d.getTargetTemperature()));
            i++;
        }

        if(mAdapter != null) mAdapter.updateAll(devicesNames, targetTemps, currentTemps);
        else{
            mAdapter = new MyAdapter(devicesNames, currentTemps, targetTemps);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void setupDrawer(){

        if(userController.isLoggedIn()){
            mDrawerView
                    .addView(new DrawerHeader(userController))
                    .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE, userController))
                    .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS, userController))
                    .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_TERMS, userController))
                    .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS, userController))
                    .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT, userController));
        }else{
            mDrawerView
                    .addView(new DrawerHeader(userController))
                    .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_SIGNIN, userController))
                    .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_TERMS, userController))
                    .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS, userController));
        }

        ActionBarDrawerToggle  drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String[] a = data.getStringArrayExtra("codes");
                for(String code : a){
                    userController.registerDevice(code);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void updateDrawer() {
        mDrawerView.removeAllViews();
        mDrawerView.refresh();
        setupDrawer();
    }
}
