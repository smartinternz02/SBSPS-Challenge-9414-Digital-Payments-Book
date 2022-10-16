package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    CircleImageView fbProfile;
    private ImageView purchaseHistory;
    private ImageView expenses;
    private ImageView payBills;
    private ImageView bankTransfer;
    private TextView purchasePrice;
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
        setContentView(R.layout.activity_home);

        Intent i = new Intent();
        String customerName = i.getStringExtra("Customer name");
        String customerEmail = i.getStringExtra("Customer Email");
        Log.d("cust name", "onCreate: "+customerName);

        fbProfile = findViewById(R.id.profilePic);
        fbProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
            startActivity(intent);
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        uId = user.getDisplayName();

        purchasePrice = findViewById(R.id.purchasePrice);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Purchase History").child(uId).child("Purchase Details");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> price =new ArrayList<String>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String productName = dataSnapshot.getKey();
//                    Log.d("product name", productName);

                    price.add(String.valueOf(snapshot.child(productName).child("productPrice").getValue()));

//                    String productPrice = snapshot.child(productName).child("productPrice").getValue(String.class);
                }
                int over_all_price = 0;
                for (int j = 0; j < price.size(); j++) {
                    Log.d("j price", price.get(j));
                    over_all_price = over_all_price+ Integer.parseInt(price.get(j));
                }
                Log.d("over all price", String.valueOf(over_all_price));
                Log.d("price", String.valueOf(price));
                purchasePrice.setText(String.valueOf(over_all_price));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        purchasePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DisplayPurchaseDetailsActivity.class));
            }
        });

        purchaseHistory = findViewById(R.id.purchase_history);
        expenses = findViewById(R.id.Expenses);
        payBills = findViewById(R.id.pay_bills);
        bankTransfer = findViewById(R.id.Bank_transfer);

        purchaseHistory.setOnClickListener(view -> {
            Intent intent1 = new Intent(HomeActivity.this, DisplayPurchaseDetailsActivity.class);
            startActivity(intent1);
        });

        expenses.setOnClickListener(view -> {
            Intent intent1 = new Intent(HomeActivity.this,ExpensesActivity.class);
            startActivity(intent1);
        });

        payBills.setOnClickListener(view -> {
            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
        });

        bankTransfer.setOnClickListener(view -> {
            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
        });

    }
}


