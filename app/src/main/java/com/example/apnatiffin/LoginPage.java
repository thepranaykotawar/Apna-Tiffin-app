package com.example.apnatiffin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    TextInputLayout TextInputLayout1, TextInputLayout2;
    TextInputEditText email, password;
    AppCompatButton login;
    TextView register,forget;
    FirebaseAuth fAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // If logged in, redirect to MainActivity
            startActivity(new Intent(LoginPage.this, MainActivity.class));
            finish(); // Prevent going back to login screen
            return; // Exit onCreate() early
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

        fAuth = FirebaseAuth.getInstance();
        TextInputLayout1 = findViewById(R.id.TextInputLayout1);
        TextInputLayout2 = findViewById(R.id.TextInputLayout2);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.reg);
        forget = findViewById(R.id.forget);

        // Register button action
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(in);
            }
        });

        // Login button action
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

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

                fAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Store login status
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();

                                    Toast.makeText(LoginPage.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginPage.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editemail = new EditText(view.getContext());
                AlertDialog.Builder passwordreset = new AlertDialog.Builder(view.getContext());
                passwordreset.setTitle("Reset Password?");
                passwordreset.setMessage("Enter your email to receive a reset link");
                passwordreset.setView(editemail);

                passwordreset.setPositiveButton("Send", null); // Set to null initially

                passwordreset.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); // Close the dialog
                    }
                });

                AlertDialog dialog = passwordreset.create();
                dialog.show();

                // Override positive button behavior after the dialog is shown
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String userEmail = editemail.getText().toString().trim();

                    if (TextUtils.isEmpty(userEmail)) {
                        editemail.setError("Email is required");
                        return;
                    }

                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                        editemail.setError("Please enter a valid email address");
                        return;
                    }

                    // Send password reset email
                    fAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginPage.this, "Reset link sent successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss(); // Dismiss the dialog only if email was sent successfully
                                } else {
                                    String errorMessage = (task.getException() != null) ? task.getException().getMessage() : "Unknown error";
                                    Toast.makeText(LoginPage.this, "Failed to send: " + errorMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                });
            }
        });

    }};