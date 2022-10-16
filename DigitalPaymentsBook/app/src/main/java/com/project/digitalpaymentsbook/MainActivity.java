package com.project.digitalpaymentsbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button registerRetailer;
    private Button registerCustomer;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();


        registerRetailer = findViewById(R.id.registerAsRetailer);
        registerRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterRetailerActivity.class);
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                },3000);
                startActivity(i);
            }
        });

        registerCustomer = findViewById(R.id.registerAsCustometer);
        registerCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterCustomerActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected  void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null){
        }else{
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
    }
}