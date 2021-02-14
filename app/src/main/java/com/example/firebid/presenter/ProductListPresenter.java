package com.example.firebid.presenter;

import androidx.annotation.Nullable;

import com.example.firebid.contract.ProductListContract;
import com.example.firebid.model.Product;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductListPresenter implements ProductListContract.Presenter {

    private ProductListContract.View view;
    private List<Product> rooms;

    public ProductListPresenter(ProductListContract.View view){
        this.view = view;
    }

    @Override
    public void loadProducts() {

        Query query = FirebaseFirestore.getInstance()
                .collection("product-main2");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    //...
                    return;
                }


                List<Product> rooms = new ArrayList<>();
                for(DocumentSnapshot ds: snapshot){
                    Product product = new Product();
                    product.setProductId(ds.getId());
                    product.setProductName(ds.getString("productId"));
                    product.setDescription(ds.getString("description"));
                    product.setImageUrl(ds.getString("imageUrl"));
                    product.setWinner(ds.getString("winner"));
                    product.setHighestBid(ds.getString("highestBid"));
                    product.setEndTime(ds.getString("endTime"));
                    rooms.add(product);
                }

                // Update UI
                view.setProducts(rooms);
            }
        });

    }
}
