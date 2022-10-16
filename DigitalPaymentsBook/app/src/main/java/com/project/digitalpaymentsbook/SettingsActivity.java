package com.project.digitalpaymentsbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView ChangeUserName;
    TextView ChangeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ChangeUserName = findViewById(R.id.ChangeUserName);
        ChangeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,ChangeUserNameActivity.class));
            }
        });

        ChangeEmail = findViewById(R.id.ChangeEmail);
        ChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,ChangeEmailActivity.class));
            }
        });


    }
}