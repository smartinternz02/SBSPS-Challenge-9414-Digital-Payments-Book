package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayPurchaseDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<PurchaseDetails> myList;
    DatabaseReference databaseReference;
    MyAdaptor adaptor;
    PurchaseDetails purchaseDetails;

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(DisplayPurchaseDetailsActivity.this,HomeActivity.class));
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        recyclerView = findViewById(R.id.recycleView);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String registeredUserId = currentUser.getDisplayName();
        Log.d("username", registeredUserId);
        databaseReference = database.getReferenceFromUrl("https://digital-payments-book-default-rtdb.firebaseio.com/").child("Purchase History").child(registeredUserId).child("Purchase Details");

        myList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new MyAdaptor(this,myList);

        recyclerView.setAdapter(adaptor);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String productName = dataSnapshot.getKey();
                    Log.d("product name", productName);

                    purchaseDetails = snapshot.child(productName).getValue(PurchaseDetails.class);

                    myList.add(purchaseDetails);
                }
                adaptor.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayPurchaseDetailsActivity.this, "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}