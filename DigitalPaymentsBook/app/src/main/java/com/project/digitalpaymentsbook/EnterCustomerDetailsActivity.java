package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterCustomerDetailsActivity extends AppCompatActivity {

    private EditText customerName;
    private EditText phoneNumber;
    private Button next;
    CustomerDetails customerDetails;
    DatabaseReference reference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer_details);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        customerName = findViewById(R.id.EnterCustomerName);
        phoneNumber = findViewById(R.id.customerPhoneNo);
        next = findViewById(R.id.next);
        reference = FirebaseDatabase.getInstance().getReference().child("Purchase History");


        next.setOnClickListener(view -> {

            String enteredCustomerName = customerName.getText().toString().trim();
            long enteredPhoneNumber = Long.parseLong(phoneNumber.getText().toString().trim());

//            Log.d("customer", enteredCustomerName);

            CustomerDetails customerDetails1 = new CustomerDetails();
            customerDetails1.setCustomerName(enteredCustomerName);
            customerDetails1.setPhoneNumber(enteredPhoneNumber);
            Log.d("phone number", String.valueOf(enteredPhoneNumber));

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String currentUserId = currentUser.getUid();
            Log.d("User id",currentUserId);
            reference.child(currentUserId).child(enteredCustomerName).child("Purchase Details");


            Intent intent = new Intent(EnterCustomerDetailsActivity.this, EnterPurchaseDetailsActivity.class);
            intent.putExtra("Entered Customer Name",enteredCustomerName);
            Log.d("entered cust name", enteredCustomerName);
            startActivity(intent);

            customerName.setText("");
            customerName.requestFocus();

            phoneNumber.setText("");
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