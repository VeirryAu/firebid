package com.example.firebid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.firebid.adapter.ProductListAdapter;
import com.example.firebid.contract.ProductListContract;
import com.example.firebid.databinding.ActivityRoomListBinding;
import com.example.firebid.model.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListContract.View {
    private ActivityMainBinding binding;
    private ProductListContract.Presenter presenter;
    private ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    @Override
    public void setProducts(List<Product> products) {
        adapter = new ProductListAdapter(MainActivity.this, products);
        binding.viewProducts.setAdapter(adapter);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}