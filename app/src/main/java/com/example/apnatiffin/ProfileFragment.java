package com.example.apnatiffin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userId;
    TextView nameTextView, emailTextView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        // Initialize Firebase Auth & Firestore
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Get user ID (Handle null case)
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        } else {
            Toast.makeText(getContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
            return view; // Exit early if the user is not logged in
        }

        // Initialize UI elements
        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        TextView accountSettings = view.findViewById(R.id.accountSettings);
        TextView appearanceSettings = view.findViewById(R.id.appearanceSettings);
        TextView contact = view.findViewById(R.id.contact);
        TextView feedback = view.findViewById(R.id.feedback);
        TextView report = view.findViewById(R.id.report);
        TextView logout = view.findViewById(R.id.logout);

        // Fetch user data from Firestore
        loadUserData();

        // Navigate to Account Settings
        accountSettings.setOnClickListener(v -> navigateToFragment(new AccountFragment()));

        // Navigate to Appearance Settings
        appearanceSettings.setOnClickListener(v -> navigateToFragment(new AppearanceFragment()));

        // Open WhatsApp support chat
        contact.setOnClickListener(v -> openWhatsApp());

        // Open feedback form
        feedback.setOnClickListener(v -> openUrl("https://docs.google.com/forms/d/e/1FAIpQLSe4m1hPRMEVcpddIeMNuxGyh1nV90QWAC-A_ELfizjghrmKTg/viewform?usp=header"));

        // Open issue report form
        report.setOnClickListener(v -> openUrl("https://docs.google.com/forms/d/e/1FAIpQLSf_gSJ4ORqaarap4RXCjYe8TNc9nnhRUb2VqjTLg4xkHVszVw/viewform?usp=preview"));

        // Logout function
        logout.setOnClickListener(v -> logoutUser());

        return view;
    }

    private void loadUserData() {
        if (userId == null) return;

        DocumentReference docRef = firestore.collection("users").document(userId);

        // Use SnapshotListener to update profile instantly when data changes
        docRef.addSnapshotListener((document, error) -> {
            if (error != null) {
                Log.e("ProfileFragment", "Firestore Error: ", error);
                Toast.makeText(getContext(), "Error loading profile", Toast.LENGTH_SHORT).show();
                return;
            }

            if (document != null && document.exists()) {
                String name = document.getString("name");
                String email = document.getString("email");

                // Update UI instantly when data changes
                if (name != null) nameTextView.setText(name);
                if (email != null) emailTextView.setText(email);
            }
        });
    }



    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragment, fragment);
        transaction.commit();
    }

    private void openWhatsApp() {
        String phoneNumber = "+919422516746"; // Replace with your support number
        String message = "Hello, I need help with Apna Tiffin!";
        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void logoutUser() {
        // Clear user data from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Firebase sign-out
        FirebaseAuth.getInstance().signOut();

        // Redirect to Login screen
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);
        requireActivity().finish(); // Close the current activity
    }
}