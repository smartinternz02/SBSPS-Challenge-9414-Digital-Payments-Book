package com.project.digitalpaymentsbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RetailerHomeActivity extends AppCompatActivity {

    CircleImageView fbProfile;
    private ImageView customerPurchaseHistory;
    private ImageView enterPurchaseDetails;
    private ImageView pendingPayments;
    private ImageView bankTransfer;
    ListView li;
    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference;
    FirebaseUser user;
    List<String> itemList;
    String uId;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home);


        fbProfile = findViewById(R.id.profileImage);
        fbProfile.setOnClickListener(view -> {
            Intent intent = new Intent(RetailerHomeActivity.this,RetailerProfileActivity.class);
            startActivity(intent);
        });

        customerPurchaseHistory = findViewById(R.id.customerPurchaseHistory);
        enterPurchaseDetails = findViewById(R.id.enterPurchaseDetails);
        pendingPayments = findViewById(R.id.pendingPayments);
        bankTransfer = findViewById(R.id.Bank_transfer);

        customerPurchaseHistory.setOnClickListener(view -> {
            Intent intent1 = new Intent(RetailerHomeActivity.this,AllCustomersPurchaseHistoryActivity.class);
            startActivity(intent1);
        });

        enterPurchaseDetails.setOnClickListener(view -> {
            Intent intent1 = new Intent(RetailerHomeActivity.this, EnterCustomerDetailsActivity.class);
            startActivity(intent1);
        });

        pendingPayments.setOnClickListener(view -> {
            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
        });

        bankTransfer.setOnClickListener(view -> {
            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
        });

    }
}


