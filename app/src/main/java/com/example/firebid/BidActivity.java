package com.example.firebid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class BidActivity extends AppCompatActivity {

    private String PRODUCT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);

        Intent intent = new Intent();
        PRODUCT_ID =  intent.getStringExtra("PRODUCT_ID");
    }
}
