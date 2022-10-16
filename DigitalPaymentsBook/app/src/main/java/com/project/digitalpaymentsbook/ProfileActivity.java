package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.collections.ArrayDeque;

public class ProfileActivity extends AppCompatActivity {

    private TextView custName;
    private TextView custEmail;
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
        setContentView(R.layout.activity_profile);

        fbProfile = findViewById(R.id.profile);

        custName = findViewById(R.id.customerName);
        custEmail = findViewById(R.id.customerMailId);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("UsersDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String customerName = snapshot.child(uId).child("userName").getValue(String.class);
                String customerEmail = snapshot.child(uId).child("emailId").getValue(String.class);
                custName.setText(customerName);
                custEmail.setText(customerEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        setting = findViewById(R.id.settings);
        setting.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity.this,SettingsActivity.class);
            startActivity(i);
        });

        account = findViewById(R.id.account);
        account.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity.this,AccountActivity.class);
            startActivity(i);
        });
        helpAndSupport = findViewById(R.id.helpAndSupport);
        helpAndSupport.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity.this,HelpAndSupport.class);
            startActivity(i);
        });
        about = findViewById(R.id.About);
        about.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity.this,AboutActivity.class);
            startActivity(i);
        });

        signOut = findViewById(R.id.SignOut);
        mAuth = FirebaseAuth.getInstance();
        signOut.setOnClickListener(view -> {
            mAuth.signOut();
            Intent i = new Intent(ProfileActivity.this,LoginActivity.class);
            startActivity(i);
        });
    }
}