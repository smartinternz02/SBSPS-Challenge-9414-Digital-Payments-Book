package com.project.digitalpaymentsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeUserNameActivity extends AppCompatActivity {

    TextView currentName;
    EditText updateName;
    Button updateBtn;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_name);

        currentName = findViewById(R.id.currentName);
        updateName = findViewById(R.id.UpdateUsername);
        updateBtn = findViewById(R.id.UpdateBtn);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("UsersDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String customerName = snapshot.child(uId).child("userName").getValue(String.class);
                currentName.setText(customerName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = updateName.getText().toString();
                if (updatedName!=null){
                    databaseReference.child(uId).child("userName").setValue(updatedName);
                    Toast.makeText(ChangeUserNameActivity.this, "Name changed Successfully", Toast.LENGTH_SHORT).show();
                    updateName.setText("");
                }

            }
        });




    }
}