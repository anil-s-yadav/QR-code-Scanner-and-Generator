package com.legendarysoftwares.qr_code_scan_and_ganerate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import org.jetbrains.annotations.Nullable;

public class QRScaner extends Fragment {
    public QRScaner() {
        // Required empty public constructor
    }

    private CodeScanner codeScanner;
    private TextView resultView;
    private CodeScannerView scannerView;
    private Button resultButton1;
    private SeekBar zoom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_r_scaner, container, false);
        final Activity activity = getActivity();
        resultView = view.findViewById(R.id.result_view);
        scannerView = view.findViewById(R.id.scanner_View);
        resultButton1 = view.findViewById(R.id.load_img);
        zoom = view.findViewById(R.id.seekBar);

        resultButton1.setVisibility(View.GONE);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.CAMERA,
        };
        if (!hasPermissions(activity, PERMISSIONS))
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        else
            runCodeScanner();

        zoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                codeScanner.setZoom(progress);
                codeScanner.setZoom(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // runCodeScanner();
        return view;
    }

    public void runCodeScanner() {
        final Activity activity = getActivity();
        codeScanner = new CodeScanner(activity, scannerView);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFormats(codeScanner.ALL_FORMATS);
        codeScanner.setScanMode(ScanMode.SINGLE);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String data = result.getText().toString();
                        resultView.setText(data);
                        vibrate();
                        resultButton1.setVisibility(View.VISIBLE);
                        if( URLUtil.isValidUrl(data)){
                            resultButton1.setText("Open Link");
                            openLink();
                        }
                        else{
                            resultButton1.setText("copy Text");
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.setAutoFocusEnabled(true);
                codeScanner.startPreview();
                resultButton1.setVisibility(View.GONE);

                resultView.setText("");
            }
        });

    }

    private void openLink(){
        resultButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String url = resultView.getText().toString();
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(urlIntent);
            }
        });
    }
    public void vibrate() {
        final Activity activity = getActivity();
        //final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        final VibrationEffect vibrationEffect1;

        // this is the only type of the vibration which requires system version Oreo (API 26)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // this effect creates the vibration of default amplitude for 1000ms(1 sec)
            vibrationEffect1 = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE);

            // it is safe to cancel other vibrations currently taking place
            vibrator.cancel();
            vibrator.vibrate(vibrationEffect1);

        }
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if (context != null && permissions != null){
            for(String permission : permissions){
                if (ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

}