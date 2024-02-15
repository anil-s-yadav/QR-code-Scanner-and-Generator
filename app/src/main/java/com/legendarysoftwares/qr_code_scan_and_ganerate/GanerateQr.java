package com.legendarysoftwares.qr_code_scan_and_ganerate;

import static android.content.ContentValues.TAG;
import static android.content.Context.WINDOW_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GanerateQr extends Fragment {
    public GanerateQr() {
        // Required empty public constructor
    }

    private EditText enterValue ;
    private Button ganerateBtn;
    private ImageView qrCode_result;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ganerate_qr, container, false);

        final Activity activity = getActivity();

        enterValue = view.findViewById(R.id.input);
        ganerateBtn = view.findViewById(R.id.ganerate);

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.qr_diloguebox);
        qrCode_result = dialog.findViewById(R.id.qrCode_result);


        ganerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String inputValue = enterValue.getText().toString().trim();
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) activity.getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = Math.min(width, height);
                    smallerDimension = smallerDimension * 3 / 4;

                    // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
                    QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension);
                    qrgEncoder.setColorBlack(Color.WHITE);
                    qrgEncoder.setColorWhite(Color.BLACK);
                    try {
                        // Getting QR-Code as Bitmap
                        bitmap = qrgEncoder.getBitmap();
                        // Setting Bitmap to ImageView
                        qrCode_result.setImageBitmap(bitmap);
                        dialog.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    enterValue.setError(getResources().getString(R.string.value_required));
                }

            }
        });















        return view;
    }


}