package com.scandit.batchmodescansample;
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.scandit.barcodepicker.*;
import com.scandit.recognition.Barcode;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Example for a full-screen barcode scanning activity using the Scandit
 * Barcode picker.
 *
 * The activity does the following:
 *
 *  - starting the picker full-screen mode
 *  - configuring the barcode picker with the settings defined in the
 *    SettingsActivity.
 *  - registering a listener to get notified whenever a barcode gets
 *    scanned. Upon a successful scan, the barcode scanner is paused and
 *    the recognized barcode is displayed in an overlay. When the user
 *    taps the screen, barcode recognition is resumed.
 *
 * For non-fullscreen barcode scanning take a look at the BatchModeScanSampleMainActivity
 * class.
 */
public class SampleScanAndConfirmBarcodeActivity
		extends Activity
		implements OnScanListener {

    // The main object for recognizing a displaying barcodes.
    private BarcodePicker mBarcodePicker;
    private View barcodeView = null;
    private UIHandler mHandler = null;
    private TextView barcodeText;
    private Button confirmButton;
    private Button cancelButton;
    private Runnable mRunnable;

    // Enter your Scandit SDK App key here.
    // Your Scandit SDK App key is available via your Scandit SDK web account.
    public static final String sScanditSdkAppKey = "--- ENTER YOUR SCANDIT APP KEY HERE ---";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_confirm);
        mHandler = new UIHandler(this);
        // We keep the screen on while the scanner is running.
        getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Initialize and start the bar code recognition.
        initializeAndStartBarcodeScanning();
    }

    @Override
    protected void onPause() {
		super.onPause();
        // When the activity is in the background immediately stop the
        // scanning to save resources and free the camera.
        mBarcodePicker.stopScanning();

    }

    @Override
    protected void onResume() {
		super.onResume();
        // Once the activity is in the foreground again, restart scanning.
        mBarcodePicker.startScanning();
    }

    /**
     * Initializes and starts the bar code scanning.
     */
    public void initializeAndStartBarcodeScanning() {
        ScanditLicense.setAppKey(sScanditSdkAppKey);
        barcodeView = findViewById(R.id.barcode_detected);
        barcodeText = (TextView) findViewById(R.id.barcode_text);

        cancelButton = (Button) findViewById(R.id.barcode_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBarcodePicker.removeView(barcodeView);
            }
        });
        confirmButton = (Button) findViewById(R.id.barcode_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                barcodeText.setText("Barcode added");
                mHandler.postDelayed(mRunnable, 3 * 1000);
            }
        });

        mRunnable = new Runnable() {
            @Override
            public void run() {
                mBarcodePicker.removeView(barcodeView);
            }
        };
        // Switch to full screen.
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
        
        SettingsActivity.setActivityOrientation(this);

		// Set all scan settings according to the settings activity. Typically
		// you will hard-code the settings for your app and do not need a settings
		// activity.
		ScanSettings settings = SettingsActivity.getSettings(this);

		// the following code caching and duplicate filter values are the
		// defaults, they are nevertheless listed here to introduce them.

		// keep codes forever
		settings.setCodeCachingDuration(-1);
		// classify codes as duplicates if the same data/symbology is scanned
		// within 500ms.
		settings.setCodeDuplicateFilter(500);

        settings.setRestrictedAreaScanningEnabled(true);
        settings.setScanningHotSpotHeight(0.05f);

        mBarcodePicker = new BarcodePicker(this, settings);

		// Add listeners for scan events and search bar events
        mBarcodePicker.setOnScanListener(this);


        // Add both views to activity, with the scan GUI on top.
        setContentView(mBarcodePicker);
        //! [Restrict Area]
        mBarcodePicker.getOverlayView().setViewfinderDimension(0.7f, 0.05f, 0.7f, 0.05f);
        //! [Restrict Area]
        mBarcodePicker.getOverlayView().setTorchEnabled(false);
        //! [Laser Added]
        mBarcodePicker.getOverlayView().setGuiStyle(ScanOverlay.GUI_STYLE_LASER);
        //! [Laser Added]
        //! [Start]
        mBarcodePicker.startScanning();
        //! [Start]
        // If you want to process the individual frames yourself, add a capture
        // listener.
        // mBarcodePicker.setCaptureListener(this);
    }

	@Override
	public void didScan(ScanSession session) {
        List<Barcode> newlyRecognized = session.getNewlyRecognizedCodes();

        // because the callback is invoked inside the thread running the barcode
        // recognition, any UI update must be posted to the UI thread.
        // In this example, we want to show the first decoded barcode in a
        // splash screen covering the full display.
        mHandler.removeCallbacks(mRunnable);
        Message msg = mHandler.obtainMessage(UIHandler.SHOW_BARCODES,
                newlyRecognized);
        mHandler.sendMessage(msg);
	}


    @Override
    public void onBackPressed() {
        mBarcodePicker.stopScanning();
        barcodeView = null;
        finish();
    }


    static private class UIHandler extends Handler {
        public static final int SHOW_BARCODES = 0;
        private WeakReference<SampleScanAndConfirmBarcodeActivity> mActivity;
        UIHandler(SampleScanAndConfirmBarcodeActivity activity) {
            mActivity = new WeakReference<SampleScanAndConfirmBarcodeActivity>(activity);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_BARCODES:
                    showSplash(createMessage((List<Barcode>)msg.obj));
                    break;
            }
        }
        private String createMessage(List<Barcode> codes) {
            String message = "";
            for (Barcode code : codes) {
                String data = code.getData();
                // cleanup the data somewhat by replacing control characters contained in
                // some of the barcodes with hash signs and truncating long barcodes
                // to reasonable lengths.
                String cleanData = "";

                for (int i = 0; i < data.length(); ++i) {
                    char c = data.charAt(i);
                    cleanData += Character.isISOControl(c) ? '#' : c;
                }
                if (cleanData.length() > 30) {
                    cleanData = cleanData.substring(0, 25)+"[...]";
                }
                message += code.getSymbologyName().toUpperCase() + " ";
                message += cleanData;

            }
            return message;
        }

        private void showSplash(String msg) {
            SampleScanAndConfirmBarcodeActivity activity = mActivity.get();
            RelativeLayout layout = activity.mBarcodePicker;
            if (activity.barcodeView != null)  {
                layout.removeView(activity.barcodeView);
                RelativeLayout.LayoutParams rParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                rParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layout.addView(activity.barcodeView, rParams);
                activity.confirmButton.setVisibility(View.VISIBLE);
                activity.cancelButton.setVisibility(View.VISIBLE);
                activity.barcodeText.setText(msg);
            }
        }
    }
}
