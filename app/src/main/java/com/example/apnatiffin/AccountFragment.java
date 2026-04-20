package com.example.apnatiffin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {

    EditText editName;
    AppCompatButton btnSave, btnEditPassword;
    FirebaseAuth auth;
     FirebaseFirestore firestore;
     FirebaseUser currentUser;
     ImageView backButton;  // ✅ Declare backButton

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        // Initialize UI elements
        editName = view.findViewById(R.id.editName);
        btnSave = view.findViewById(R.id.btnSave);
        btnEditPassword = view.findViewById(R.id.btnEditPassword);
        backButton = view.findViewById(R.id.back12);  // ✅ Find back button

        // Load user data
        loadUserData();

        // Save button click listener (updates only the name)
        btnSave.setOnClickListener(v -> updateUserData());

        // Edit Password button click listener (sends reset email)
        btnEditPassword.setOnClickListener(v -> sendPasswordResetEmail());

        // Back button click listener (navigates to ProfileFragment)
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, new ProfileFragment())  // ✅ Ensure correct container ID
                    .addToBackStack(null)  // Allows going back
                    .commit();
        });

        return view;
    }

    private void loadUserData() {
        if (currentUser != null) {
            firestore.collection("users").document(currentUser.getUid()).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            editName.setText(documentSnapshot.getString("name"));
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show());
        }
    }

    private void updateUserData() {
        String newName = editName.getText().toString().trim();

        if (TextUtils.isEmpty(newName)) {
            editName.setError("Name cannot be empty!");
            return;
        }

        if (currentUser != null) {
            // Update Firestore with new name
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", newName);

            firestore.collection("users").document(currentUser.getUid())
                    .update(updates)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Profile Updated!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Update Failed!", Toast.LENGTH_SHORT).show());
        }
    }

    private void sendPasswordResetEmail() {
        if (currentUser != null) {
            String userEmail = currentUser.getEmail(); // Get user's email

            if (userEmail != null) {
                auth.sendPasswordResetEmail(userEmail)
                        .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Password reset email sent! Check your email.", Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(getContext(), "No email found for this account!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
