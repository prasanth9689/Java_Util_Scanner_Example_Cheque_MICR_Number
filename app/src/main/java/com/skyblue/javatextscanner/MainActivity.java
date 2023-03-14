package com.skyblue.javatextscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skyblue.javatextscanner.databinding.ActivityMainBinding;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /*
          Step 1: Capture cheque using OpenCV Lib
          Step 2: apply canny and gassuian blur
          Step 3: Find largest contour
          Step 4: Crop cheque image only
          Step 5: Apply prespective transform
          Step 6: Save image to local storage
          Step 7: Get saved cheque image as a bitmap
          Step 8: init tessaract OCR Lib
          Step 9: Crop bottom cheque number only ( Easily to read OCR quick and fast)
          Step 10: Apply Scanner
         */

        /*
           correct format of cheque number
           scanner next function get cheque number ' cheque no <space> routing no <space> etc
         */
        scanText("1a1aa 11a1aa1 a1a1a2a 2a2a23d3 e4dd5f5ed 44d3d3d3s3d3ff4f5");

        /*
           wrong format cheque number
         */
        // scanText("1a1aa 11a1aa1 a1a1a2a 2a2a23d3 e4dd5f5ed 44d3d3d3s3d3ff4f5");
    }

    private void scanText(String text)
    {
        /*
         *  if cheque number empty scanner function not access to stop unwanted crash
         */
        if (text.equals("")){
            Toast.makeText(context, "Unable to read Cheque Number", Toast.LENGTH_SHORT).show();
            Log.d("ocr", "Empty cheque number");
            return;
        }

        /*
         * remove all small letters
         */
        String strNew = text.replaceAll("([a-z])", "");

        /*
           The java.util.Scanner class is a simple text scanner which can parse primitive types and strings
           using regular expressions.Following are the important points about Scanner − A Scanner breaks its
           input into tokens using a delimiter pattern, which by default matches whitespace.
         */
        Scanner scanner = new Scanner(strNew);

        /*
           first did not make app crash
         */
        String firstStrChequeNo = scanner.next();

        Toast.makeText(context, firstStrChequeNo, Toast.LENGTH_SHORT).show();
        Log.d("scanner_", "Cheque no " + firstStrChequeNo);

        /*
           check space available
           space available scanner work continues
           not available scanner will be stop
         */
        if (firstStrChequeNo.length() <= 7){
            Log.d("scanner_", "true");
        }else {
            Log.d("scanner_", "false");
            Toast.makeText(context, "Unable to read Cheque Number", Toast.LENGTH_SHORT).show();
            return;
        }

        String secondStrRoutingNo = scanner.next();
        Log.d("scanner_", "Routing no " + secondStrRoutingNo);

        String secondStrRoutingNoRemLastTwoNo = secondStrRoutingNo.substring(0, secondStrRoutingNo.length()-2);

        if (secondStrRoutingNoRemLastTwoNo.length() <= 9){
            Log.d("scanner_", "true");
        }else {
            Log.d("scanner_", "false");
            Toast.makeText(context, "Unable to read Cheque Number", Toast.LENGTH_SHORT).show();
            return;
        }

        String thirdStrShortAcNo = scanner.next();

        if (thirdStrShortAcNo.length() <= 6){
            Log.d("scanner_", "true");
        }else {
            Log.d("scanner_", "false");
            Toast.makeText(context, "Unable to read Cheque Number", Toast.LENGTH_SHORT).show();
            return;
        }
            String fourthStrTCNo = scanner.next();

            binding.edTextChequeNo.setText(firstStrChequeNo);
            binding.edTextRouting.setText(secondStrRoutingNoRemLastTwoNo);
            binding.edTextShortAcNo.setText(thirdStrShortAcNo);
            binding.edTexttC.setText(fourthStrTCNo);
    }
}