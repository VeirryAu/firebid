package com.example.firebid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebid.adapter.BidListAdapter;
import com.example.firebid.databinding.ActivityBidBinding;
import com.example.firebid.model.Bid;
import com.example.firebid.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BidActivity extends AppCompatActivity {

    private Product product;
    private String PRODUCT_ID;
    private ActivityBidBinding binding;
    private BidListAdapter bidListAdapter;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBidBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        PRODUCT_ID =  intent.getStringExtra("PRODUCT_ID");
//        PRODUCT_ID =  "12312";

        loadSingleProduct();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        binding.btnMakeABid.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                loadSingleProduct();
                Bid bid = new Bid();
                bid.setProductId(PRODUCT_ID);
                bid.setTime(Timestamp.now());
                bid.setUser(
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                if (binding.etPrice.getText() == null || binding.etPrice.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Insert Your Bid", Toast.LENGTH_SHORT).show();
                    return;
                }
                bid.setPrice(Integer.parseInt(binding.etPrice.getText().toString()));
                if (bid.getPrice() == 0) {
                    Toast.makeText(getApplicationContext(), "Please Insert Your Bid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (product != null && !product.getHighestBid().equals("") && Integer.parseInt(product.getHighestBid()) >= bid.getPrice()) {
                    int highestPrice = Integer.parseInt(product.getHighestBid()) + 1000;
                    binding.etPrice.setText(Integer.toString(highestPrice));
                    Toast.makeText(getApplicationContext(), "Bid should bigger than current highest bid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (product != null && product.getEndTime() != null) {
                    if (!product.getEndTime().equals("")) {
                        @SuppressLint("SimpleDateFormat")
                        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String string1 = product.getEndTime();
                        try {
                            long endTime = df1.parse(string1).getTime();
                            long currentTime = (new Date()).getTime();
                            long timeDiff = currentTime - endTime;
                            if (currentTime > endTime) {
                                Toast.makeText(getApplicationContext(), "Bid already finished", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (ParseException e) {

                        }
                    }
                }

                FirebaseFirestore.getInstance().collection("bid_team2")
                        .add(bid)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Your bid has been placed", Toast.LENGTH_SHORT).show();
                                binding.etPrice.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });
    }

    private void loadSingleProduct(){
        Task<DocumentSnapshot> query = FirebaseFirestore.getInstance()
                .collection("product-team2")
                .document(PRODUCT_ID)
                .get();
        query.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        loadHighestBid();
                        updateUI(document.toObject(Product.class));
                        product = document.toObject(Product.class);
                    }
                }
            }
        });
    }

    private void loadHighestBid(){
        Query query = FirebaseFirestore.getInstance()
                .collection("bid_team2")
                .whereEqualTo("productId", PRODUCT_ID)
                .orderBy("price", Query.Direction.DESCENDING)
                .limit(3);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    //...
                    return;
                }

                // Convert query snapshot to a list of chats
                List<Bid> bids = snapshot.toObjects(Bid.class);

                updateBidList(bids);
            }
        });
    }

    private void updateBidList(List<Bid> bids){
        bidListAdapter = new BidListAdapter(getApplicationContext(), (ArrayList<Bid>)bids);
        binding.rvHighestBid.setLayoutManager(linearLayoutManager);
        binding.rvHighestBid.setAdapter(bidListAdapter);
    }

    private void updateUI(Product product){
        binding.tvProductName.setText(product.getProductName());
        binding.tvProductDescription.setText(product.getDescription());
        loadImage(product.getImageUrl());
//        loadImage("https://firebasestorage.googleapis.com/v0/b/smu-firebase-80435.appspot.com/o/bid_team2%2F1613274665224-WhatsApp%20Image%202020-12-01%20at%209.00.25%20PM.jpeg?alt=media&token=e67223c4-dd3c-4c97-b765-d861d54935c1");
    }

    private void loadImage(String imageUrl){
        Glide.with(this).load(imageUrl).into(binding.ivProductImage);
    }
}
