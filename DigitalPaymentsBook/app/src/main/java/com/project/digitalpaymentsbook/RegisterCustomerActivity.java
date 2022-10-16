package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.CharacterPickerDialog;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.Arrays;

public class RegisterCustomerActivity extends AppCompatActivity {

    private LoginButton login;
    private EditText userName;
    private EditText emailId;
    private EditText password;
    private EditText phoneNumber;
    private EditText custAddress;
    private EditText custState;
    private EditText custDistrict;
    private TextView registerRetailer;
    private Button registerBtn;
    String emailpattern = "[a-zAA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    CallbackManager callbackManager;
    DatabaseReference reff;
    UserDetails userDetails;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String uId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.Fullname);
        emailId = findViewById(R.id.EmailId);
        password = findViewById(R.id.Password);
        phoneNumber = findViewById(R.id.PhoneNumber);
        custAddress = findViewById(R.id.Address);
        custState = findViewById(R.id.State);
        custDistrict = findViewById(R.id.district);
        registerBtn = findViewById(R.id.registerBtn);
        registerRetailer = findViewById(R.id.registerAsRetailer);
        userDetails = new UserDetails();
        reff = FirebaseDatabase.getInstance().getReference().child("UsersDetails");
        mAuth = FirebaseAuth.getInstance();


        registerBtn.setOnClickListener(view -> {

            String registeredUserName = userName.getText().toString().trim();
            String registeredEmailId = emailId.getText().toString().trim();
            String registeredPassword = password.getText().toString().trim();
            long registeredPhoneNumber = Long.parseLong((phoneNumber.getText().toString()).trim());
            String registeredCustAddress = custAddress.getText().toString().trim();
            String registeredCustState = custState.getText().toString().trim();
            String registeredCustDistrict = custDistrict.getText().toString().trim();


            if(TextUtils.isEmpty(registeredUserName)){
                userName.setError("enter your name");
                userName.requestFocus();
            }
            else if(TextUtils.isEmpty(registeredEmailId)){
                emailId.setError("enter your email");
                emailId.requestFocus();
            }
            else if(TextUtils.isEmpty(registeredPassword)){
                password.setError("enter password");
                password.requestFocus();
            }
            else if(String.valueOf(registeredPhoneNumber).length()<=9){
                phoneNumber.setError("Enter a valid 10 digit phone number");
            }
            else if(TextUtils.isEmpty(registeredCustAddress)){
                custAddress.setError("Enter your Address");
                custAddress.requestFocus();
            }
            else if(TextUtils.isEmpty(registeredCustState)){
                custState.setError("Enter your State");
                custState.requestFocus();
            }
            else if(TextUtils.isEmpty(registeredCustDistrict)){
                custState.setError("Enter your District");
                custState.requestFocus();
            }


            userDetails.setUserName(registeredUserName);
            userDetails.setPassword(registeredPassword);
            userDetails.setEmailId(registeredEmailId);
            userDetails.setPhoneNumber(registeredPhoneNumber);
            userDetails.setCustAddress(registeredCustAddress);
            userDetails.setCustState(registeredCustState);
            userDetails.setCustDistrict(registeredCustDistrict);
            userDetails.setUserType("Customer");


            mAuth.createUserWithEmailAndPassword(registeredEmailId,registeredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String registeredUserId = currentUser.getUid();
                        Log.d("register user id", registeredUserId);

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(registeredUserName).build();

                        currentUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            reff.child(registeredUserId).setValue(userDetails);
                                            startActivity(new Intent(RegisterCustomerActivity.this,HomeActivity.class));
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(RegisterCustomerActivity.this, "Error occurs: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


        registerRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterCustomerActivity.this,RegisterRetailerActivity.class));
            }
        });



        SpannableString ss = new SpannableString("Already have an account Login In");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(RegisterCustomerActivity.this, LoginActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 24, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.rgb(255,191,155)),24,32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView textView = (TextView) findViewById(R.id.login);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setTextColor(Color.BLACK);



//        login = findViewById(R.id.login_button);
//        callbackManager = CallbackManager.Factory.create();
//        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Intent i = new Intent(RegisterCustomerActivity.this,HomeActivity.class);
//                startActivity(i);
//            }
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(@NonNull FacebookException e) {
//
//            }
//        });
    }


//    public void Login(View v){
//        LoginManager.getInstance().logInWithReadPermissions(
//                this, Arrays.asList("email","public_profile","user_birthday")
//        );
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

}