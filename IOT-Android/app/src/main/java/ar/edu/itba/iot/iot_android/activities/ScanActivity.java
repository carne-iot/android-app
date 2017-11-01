package ar.edu.itba.iot.iot_android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.scandit.barcodepicker.BarcodePicker;
import com.scandit.barcodepicker.OnScanListener;
import com.scandit.barcodepicker.ScanSession;
import com.scandit.barcodepicker.ScanSettings;
import com.scandit.barcodepicker.ScanditLicense;
import com.scandit.recognition.Barcode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.iot.iot_android.R;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.UserService;

public class ScanActivity extends AppCompatActivity implements OnScanListener {

    private BarcodePicker picker;
    private String token;
    private Long userId;
    private SharedPreferences sp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //token = (String) getIntent().getSerializableExtra("token");
        //userId = (Long) getIntent().getSerializableExtra("userId");
//        setContentView(R.layout.activity_register_device);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ScanditLicense.setAppKey("/NVB2WWhIOpaM1Otog2hYq4CbgOoKmX/gdq6z+tQwXE");
        ScanSettings settings = ScanSettings.create();
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODE39, true);
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCA, true);
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_QR, true);
        settings.setCodeDuplicateFilter(2000);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        // Instantiate the barcode picker by using the settings defined above.
        picker = new BarcodePicker(this, settings);
        // Set the on scan listener to receive barcode scan events.
        picker.setOnScanListener(this);
        setContentView(picker);


    }
        @Override
        public void didScan(ScanSession scanSession) {
            ArrayList<Barcode> codes = new ArrayList<>(scanSession.getNewlyRecognizedCodes());
            Set<String> codesValues = new HashSet<>();
            for(Barcode code: codes){
                codesValues.add(code.getData());
                Log.d("scanned", code.getData());
            }
            if(sp != null){
                SharedPreferences.Editor e = sp.edit();
                e.putStringSet("codes", codesValues);

            }
            this.finish();
        }


    @Override
    protected void onResume() {
        picker.startScanning();
        super.onResume();
    }
    @Override
    protected void onPause() {
        picker.stopScanning();
        super.onPause();
    }
}
