package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class RetailerProfileActivity extends AppCompatActivity {

    private TextView companyName;
    private TextView companyEmail;
    private TextView account;
    private TextView setting;
    private TextView helpAndSupport;
    private TextView about;
    private CircleImageView fbProfile;
    private Button signOut;

    DatabaseReference databaseReference;
    FirebaseUser user;
    String uId;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_profile);

        fbProfile = findViewById(R.id.profile);

        companyName = findViewById(R.id.companyName);
        companyEmail = findViewById(R.id.companyMailId);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("UsersDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String compName = snapshot.child(uId).child("companyName").getValue(String.class);
                String compEmail = snapshot.child(uId).child("email").getValue(String.class);
                Log.d("Company name", compName);
                companyName.setText(compName);
                companyEmail.setText(compEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        setting = findViewById(R.id.settings);
        setting.setOnClickListener(view -> {
            Intent i = new Intent(RetailerProfileActivity.this,SettingsActivity.class);
            startActivity(i);
        });

        account = findViewById(R.id.account);
        account.setOnClickListener(view -> {
            Intent i = new Intent(RetailerProfileActivity.this,AccountActivity.class);
            startActivity(i);
        });
        helpAndSupport = findViewById(R.id.helpAndSupport);
        helpAndSupport.setOnClickListener(view -> {
            Intent i = new Intent(RetailerProfileActivity.this,HelpAndSupport.class);
            startActivity(i);
        });
        about = findViewById(R.id.About);
        about.setOnClickListener(view -> {
            Intent i = new Intent(RetailerProfileActivity.this,AboutActivity.class);
            startActivity(i);
        });

        signOut = findViewById(R.id.SignOut);
        mAuth = FirebaseAuth.getInstance();
        signOut.setOnClickListener(view -> {
            mAuth.signOut();
            Intent i = new Intent(RetailerProfileActivity.this,LoginActivity.class);
            startActivity(i);
        });
    }
}