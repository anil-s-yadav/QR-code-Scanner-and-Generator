package com.legendarysoftwares.qr_code_scan_and_ganerate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.android.material.navigation.NavigationBarView;
import com.google.zxing.Result;
import com.legendarysoftwares.qr_code_scan_and_ganerate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

   /* private CodeScanner codeScanner;
    private TextView resultView;
    private CodeScannerView scannerView;
    private Button resultButton1;
    private SeekBar zoom;*/

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadfrag(new GanerateQr(),0);
        loadfrag(new GanerateQr(),1);

        binding.navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());
                if (id == R.id.ganerate_tab){
                    loadfrag(new GanerateQr(),1);
                } else if (id==R.id.scan_tab) {
                    loadfrag(new QRScaner(),1);
                } else if (id==R.id.history_tab) {
                    loadfrag(new FragmentHistory(),1);
                }
                return true;
            }
        });

        }

/*
        resultView = findViewById(R.id.result_view);
        scannerView = findViewById(R.id.scanner_View);
        resultButton1 = findViewById(R.id.btn);
        zoom = findViewById(R.id.seekBar);

        resultButton1.setVisibility(View.GONE);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.CAMERA,
        };
        if (!hasPermissions(this, PERMISSIONS))
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
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

    }

    public void runCodeScanner() {
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFormats(codeScanner.ALL_FORMATS);
        codeScanner.setScanMode(ScanMode.SINGLE);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
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
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

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
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }


 */

    public void loadfrag(Fragment fragment, int flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag==0)
            ft.add(R.id.container,fragment);
        else
            ft.replace(R.id.container,fragment);
        ft.commit();
    }


}