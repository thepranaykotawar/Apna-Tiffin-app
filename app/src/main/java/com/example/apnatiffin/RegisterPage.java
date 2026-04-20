package com.example.apnatiffin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {
    TextInputLayout TextInputLayout1, TextInputLayout2;
    TextInputEditText email, password, username;
    AppCompatButton register;
    FirebaseAuth fAuth;
    TextView login;
    FirebaseFirestore fstore;

    private static final String TAG = "RegisterPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        username = findViewById(R.id.username);
        login = findViewById(R.id.login); // The TextView for Login
        fAuth = FirebaseAuth.getInstance();
        TextInputLayout1 = findViewById(R.id.TextInputLayout1);
        TextInputLayout2 = findViewById(R.id.TextInputLayout2);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        fstore = FirebaseFirestore.getInstance();

        // Register user on button click
        register.setOnClickListener(v -> {
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();
            String Username = username.getText().toString().trim();

            if (TextUtils.isEmpty(Email)) {
                email.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(Password)) {
                password.setError("Password is required");
                return;
            }
            if (Password.length() < 6) {
                password.setError("Password must be at least 6 characters");
                return;
            }
            if (TextUtils.isEmpty(Username)) {
                username.setError("Username is required");
                return;
            }

            fAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterPage.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "User registered successfully.");

                                // Navigate to main activity
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                                // Store user data in Firestore
                                String userId = fAuth.getCurrentUser() != null ? fAuth.getCurrentUser().getUid() : null;
                                if (userId != null) {
                                    DocumentReference documentReference = fstore.collection("users").document(userId);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", Username);
                                    user.put("email", Email);

                                    documentReference.set(user)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(RegisterPage.this, "User data saved", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "User data saved successfully.");
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.e(TAG, "Error saving user data: " + e.getMessage(), e);
                                                Toast.makeText(RegisterPage.this, "Error saving user data", Toast.LENGTH_SHORT).show();
                                            });
                                } else {
                                    Log.e(TAG, "User ID is null after registration.");
                                    Toast.makeText(RegisterPage.this, "Error: User ID is null", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                // Capture and log Firebase errors
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                                Log.e(TAG, "Firebase Registration Error: " + errorMessage, task.getException());
                                Toast.makeText(RegisterPage.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });

        // On clicking the login TextView, navigate to LoginPage
        login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(intent);
            finish();  // Finish the RegisterPage to prevent the user from returning to it
        });
    }
}