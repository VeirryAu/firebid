package com.example.firebid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebid.adapter.ProductListAdapter;
import com.example.firebid.contract.ProductListContract;
import com.example.firebid.databinding.ActivityMainBinding;
import com.example.firebid.model.Product;
import com.example.firebid.presenter.ProductListPresenter;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListContract.View {
    private ActivityMainBinding binding;
    private static final int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProductListContract.Presenter presenter;
    private ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        presenter = new ProductListPresenter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getBaseContext());
        binding.viewProducts.setLayoutManager(layoutManager);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            SharedPreferences sharedPref = this.getPreferences(this.getApplicationContext().MODE_PRIVATE);
            presenter.loadProducts();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                presenter.loadProducts();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();
                // Close the app
                finish();
            }
        }
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