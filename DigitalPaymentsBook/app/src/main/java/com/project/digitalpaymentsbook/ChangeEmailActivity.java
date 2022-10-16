package com.project.digitalpaymentsbook;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeEmailActivity extends AppCompatActivity {

    TextView currentEmail;
    EditText updateEmail;
    Button updateBtn;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        currentEmail = findViewById(R.id.currentEmail);
        updateEmail = findViewById(R.id.UpdateEmail);
        updateBtn = findViewById(R.id.UpdateEmailBtn);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("UsersDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentEmailID = snapshot.child(uId).child("emailId").getValue(String.class);
                currentEmail.setText(currentEmailID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        updateBtn.setOnClickListener(v -> {
            String updatedEmail = updateEmail.getText().toString();
            if (updatedEmail!=null){
                databaseReference.child(uId).child("emailId").setValue(updatedEmail);
                Toast.makeText(ChangeEmailActivity.this, "Email changed Successfully", Toast.LENGTH_SHORT).show();
                updateEmail.setText("");
            }

            user.updateEmail(updatedEmail).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("update message", "User email address updated.");
                }
                else{
                    Toast.makeText(this, "An error occurs"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}