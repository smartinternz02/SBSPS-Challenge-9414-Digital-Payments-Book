package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibm.db2.jcc.DB2Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginButton faceBook_login_button;
    private EditText LoginEmail;
    private EditText LoginPassword;
    private ImageView showPass;
    private Button LoginButton;
    CallbackManager callbackManager;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);

        LoginEmail = findViewById(R.id.LoginEmail);
        LoginPassword = findViewById(R.id.LoginPassword);
        LoginButton = findViewById(R.id.LoginButton);
        mAuth = FirebaseAuth.getInstance();

        showPass = findViewById(R.id.showPass);

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPassword.setTransformationMethod(null);
                showPass.setImageResource(R.drawable.icons_hide);
                Handler handler =new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoginPassword.setTransformationMethod(new PasswordTransformationMethod());
                        showPass.setImageResource(R.drawable.ic_show_password);
                    }
                },2000);

            }
        });





        LoginButton.setOnClickListener(view -> {

                    String loginId = LoginEmail.getText().toString().trim();
                    String loginPassword = LoginPassword.getText().toString().trim();
                    Log.d("Login mail id", loginId);

                    if (TextUtils.isEmpty(loginId)) {
                        LoginEmail.setError("enter your email");
                        LoginEmail.requestFocus();
                    } else if (TextUtils.isEmpty(loginPassword)) {
                        LoginPassword.setError("enter your name");
                        LoginPassword.requestFocus();
                    } else {
                        mAuth.signInWithEmailAndPassword(loginId, loginPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    String registeredUserId = currentUser.getUid();
                                    Log.d("register user id", registeredUserId);
                                    DatabaseReference loginDatabase = FirebaseDatabase.getInstance().getReference().child("UsersDetails");
                                    Log.d("hello", "hello");
                                    loginDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String userType = snapshot.child(registeredUserId).child("userType").getValue().toString();
                                            Log.d("UserType",userType);

                                            if(userType.equals("Customer")){
                                                Intent intentCustomer = new Intent(LoginActivity.this, HomeActivity.class);
                                                intentCustomer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                loadingDialog.startLoadingDialog();
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        loadingDialog.dismissDialog();
                                                    }
                                                },3000);
                                                startActivity(intentCustomer);
                                                finish();
                                            }else if(userType.equals("Retailer")){
                                                Intent intentRetailer = new Intent(LoginActivity.this, RetailerHomeActivity.class);
                                                intentRetailer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intentRetailer);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        });
                    }
                });

        SpannableString ss = new SpannableString("Don 't have an account? Sign Up");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(LoginActivity.this, RegisterCustomerActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 24, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.rgb(255,191,155)),24,31,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView textView = (TextView) findViewById(R.id.sign_Up);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setTextColor(Color.BLACK);

    }

}
