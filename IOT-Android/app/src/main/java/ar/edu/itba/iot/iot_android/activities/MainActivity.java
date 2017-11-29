package ar.edu.itba.iot.iot_android.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;

import ar.edu.itba.iot.iot_android.R;

import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ar.edu.itba.iot.iot_android.controller.Controller;
import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.tasks.GetDevicesAsyncTask;
import ar.edu.itba.iot.iot_android.view.DrawerHeader;
import ar.edu.itba.iot.iot_android.view.DrawerMenuItem;
import ar.edu.itba.iot.iot_android.view.MyAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private SharedPreferences sp;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> devicesNames = null;
    private List<String> targetTemps = null;
    private List<String> currentTemps = null;
    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private Controller controller;
    private User user;
    private int notCounter = 0;



    private Observer userChange = new Observer() {

        @Override
        public void update(Observable o, Object arg) {
            if(((String) arg).equals("id")){
                controller.getDevices();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = this.findViewById(R.id.addDevice);
        v.setVisibility(View.INVISIBLE);

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);

        user = new User(getIntent().getStringExtra("username"), getIntent().getStringExtra("password"));
        user.setToken(getIntent().getStringExtra("token"));
        user.setLogoutURL(getIntent().getStringExtra("logoutURL"));

        user.addObserver(userChange);

        controller = new Controller(this, user);

        controller.getFullUserData();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        v.setOnClickListener(this);
        mDrawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView)findViewById(R.id.drawerView);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setupDrawer();

        new GetDevicesAsyncTask(this).execute();

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
            if(d.getTemperature()>= d.getTargetTemperature()) notification(d.getNickname());
            i++;
        }
        notCounter++;
        if(mAdapter != null && notCounter%3 == 0) mAdapter.updateAll(devicesNames, currentTemps, targetTemps);
        else{
            mAdapter = new MyAdapter(this, devicesNames, currentTemps, targetTemps);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void setupDrawer(){

        mDrawerView
                .addView(new DrawerHeader(controller))
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE, controller))
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS, controller))
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_TERMS, controller))
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS, controller))
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT, controller));

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
                    controller.registerDevice(code);
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

    public void changeDeviceName(final int deviceNumber){

        final Device device = user.getDevices().get(deviceNumber);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle("Change Nickname");
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.edit_name, null, false);
        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.edit_name_text);

        //Set current name to edit and start with all text selected
        input.setText(device.getNickname());
        input.selectAll();

        //open keyboard
        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                dialog.dismiss();
                device.setNickname(input.getText().toString());
                populateAdapter();
                controller.changeDeviceNickname(user, device.getId(), input.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);

                dialog.cancel();
            }
        });
        builder.show();
    }

    public void changeDeviceTargetTemperature(final int deviceNumber) {
        final Device device = user.getDevices().get(deviceNumber);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle("Change Target Temperature");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.edit_temp, null, false);
        // Set up the picker
        final NumberPicker np = (NumberPicker) viewInflated.findViewById(R.id.np);
        np.setMaxValue(90);
        np.setMinValue(30);
        np.setWrapSelectorWheel(false);

        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close keyboard
                dialog.dismiss();
                device.setTargetTemperature(np.getValue());
                populateAdapter();
                controller.changeTargetTemperature(user, device.getId(), np.getValue());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close keyboard
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void logout() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "");
        editor.commit();

        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    public void updateDevices(){
        controller.getDevices();
    }

    private void notification(String name){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Carne")
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                        .setContentText("Your " + name +" is ready!");

        Intent resultIntent = new Intent(this, LogInActivity.class);
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 3761;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
