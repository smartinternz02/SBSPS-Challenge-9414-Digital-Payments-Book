package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterRetailerActivity extends AppCompatActivity {

    private EditText companyName;
    private EditText retailerEmail;
    private EditText retailerPassword;
    private EditText retailerPhoneNumber;
    private EditText retailerAddress;
    private EditText retailerCity;
    private EditText retailerRegion;
    private EditText retailerPostalCode;
    private Button registerBtn;
    String emailpattern = "[a-zAA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    DatabaseReference reff;
    RetailerDetails retailerDetails;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_retailer);

        companyName = findViewById(R.id.companyName);
        retailerEmail = findViewById(R.id.retaierEmail);
        retailerPassword = findViewById(R.id.RetailerPassword);
        retailerPhoneNumber = findViewById(R.id.RetailerPhoneNumber);
        retailerAddress = findViewById(R.id.RetailerAddress);
        retailerCity = findViewById(R.id.RetailerCity);
        retailerRegion = findViewById(R.id.retailerRegion);
        retailerPostalCode = findViewById(R.id.RetailerZipCode);
        registerBtn = findViewById(R.id.retailerRegisterBtn);
        retailerDetails = new RetailerDetails();
        reff = FirebaseDatabase.getInstance().getReference().child("UsersDetails");
        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(view -> {
            String retailerCompany = companyName.getText().toString().trim();
            String retailerEmailid = retailerEmail.getText().toString().trim();
            String Password = retailerPassword.getText().toString().trim();
            long PhoneNumber = Long.parseLong(retailerPhoneNumber.getText().toString().trim());
            String address = retailerAddress.getText().toString().trim();
            String city = retailerCity.getText().toString().trim();
            String region = retailerRegion.getText().toString().trim();
            int postalCode = Integer.parseInt(retailerPostalCode.getText().toString().trim());


            retailerDetails.setUserType("Retailer");
            retailerDetails.setPostalCode(postalCode);
            retailerDetails.setRetailerRegion(region);
            retailerDetails.setRetailerCity(city);
            retailerDetails.setRetailerAddress(address);
            retailerDetails.setPhoneNumber(PhoneNumber);
            retailerDetails.setPassword(Password);
            retailerDetails.setEmail(retailerEmailid);
            retailerDetails.setCompanyName(retailerCompany);


            mAuth.createUserWithEmailAndPassword(retailerEmailid,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String registeredUserId = currentUser.getUid();
                        Log.d("register user id", registeredUserId);

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(retailerCompany).build();

                        currentUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    reff.child(registeredUserId).setValue(retailerDetails);
                                    startActivity(new Intent(RegisterRetailerActivity.this,RetailerHomeActivity.class));
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RegisterRetailerActivity.this, "Error occurs: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

    }
}