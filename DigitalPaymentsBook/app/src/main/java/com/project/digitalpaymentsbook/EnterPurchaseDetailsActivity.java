package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EnterPurchaseDetailsActivity extends AppCompatActivity {

    private EditText productName;
    private EditText price;
    private Button submit;
    DatabaseReference reference;
    FirebaseUser currentUser;
    PurchaseDetails purchaseDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);


        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);



        productName = findViewById(R.id.productName);
        price = findViewById(R.id.productPrice);
        submit = findViewById(R.id.submit);
        reference = FirebaseDatabase.getInstance().getReference().child("Purchase History");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());


                String enteredProductName = productName.getText().toString().trim();
                String enteredProductPrice = price.getText().toString().trim();
                String enteredPurchaseDate = date;

                purchaseDetails = new PurchaseDetails();
                purchaseDetails.setProductName(enteredProductName);
                purchaseDetails.setProductPrice(enteredProductPrice);
                purchaseDetails.setPurchaseDate(enteredPurchaseDate);

                Intent intent = getIntent();
                String enteredCustomerName = intent.getStringExtra("Entered Customer Name");

                Log.d("entered Customer name",enteredCustomerName);

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String UserId = currentUser.getUid();

                reference.child(enteredCustomerName).child("Purchase Details").child(enteredProductName).setValue(purchaseDetails);

                productName.setText("");
                productName.requestFocus();

                price.setText("");

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}