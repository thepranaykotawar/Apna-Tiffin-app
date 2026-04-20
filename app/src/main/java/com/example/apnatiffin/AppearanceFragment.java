package com.example.apnatiffin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AppearanceFragment extends Fragment {

    private RadioGroup modeRadioGroup;
    private RadioButton lightModeRadioButton, darkModeRadioButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean nightMode;
    ImageView back12;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get stored theme preference
        sharedPreferences = requireActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);

        // Apply saved theme
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_appearance, container, false);

        modeRadioGroup = view.findViewById(R.id.themeRadioGroup);
        lightModeRadioButton = view.findViewById(R.id.lightThemeRadioButton);
        darkModeRadioButton = view.findViewById(R.id.darkThemeRadioButton);

        // **Set radio button according to saved mode**
        if (nightMode) {
            darkModeRadioButton.setChecked(true);
        } else {
            lightModeRadioButton.setChecked(true);
        }

        // **Theme Change Listener**
        modeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            editor = sharedPreferences.edit();

            if (checkedId == R.id.darkThemeRadioButton) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("night", true); // Save mode
            } else if (checkedId == R.id.lightThemeRadioButton) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("night", false); // Save mode
            }

            editor.apply();

            // **Recreate activity to apply theme properly**
            requireActivity().recreate();
        });

        // **Back Button to Profile**
        back12 = view.findViewById(R.id.back12);
        back12.setOnClickListener(view1 -> {
            assert getFragmentManager() != null;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, new ProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
