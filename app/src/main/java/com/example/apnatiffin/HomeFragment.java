package com.example.apnatiffin;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    SearchView searchview;
    EditText searchEditText;
    RecyclerView recycle;
    ArrayList<Model> arrayList, searchlist;
    Messadapter messAdapter;

    String[] Messnamelist = {"Sarvadnya Mess", "Balaji Mess", "Maratha Mess","Anshika Mess","Yash Tiffin","Adesh Tiffin","Anapurna Mess","Anapurna Tiffin"};
    String[] Locatiolist = {"Tagore chowk", "Tilak chowk", "Pragati nagar","Gandhi chowk","Rangaripura","Shastri nagar","Guru nagar","Gadgebaba chowk"};
    int[] imglist = {R.drawable.mess1, R.drawable.mess2, R.drawable.mess3, R.drawable.mess4, R.drawable.mess5, R.drawable.img_7, R.drawable.img_8, R.drawable.img_12};

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchview = view.findViewById(R.id.searchview);
        searchEditText = searchview.findViewById(androidx.appcompat.R.id.search_src_text);
        recycle = view.findViewById(R.id.recycle);

        // Initialize Lists
        arrayList = new ArrayList<>();
        searchlist = new ArrayList<>();

        // Add Data to List
        for (int i = 0; i < Messnamelist.length; i++) {
            Model model = new Model();
            model.setMessname(Messnamelist[i]);
            model.setLocation(Locatiolist[i]);
            model.setImg(imglist[i]);
            arrayList.add(model);
        }

        // Apply SearchView Styling Based on Theme
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            searchEditText.setTextColor(Color.WHITE);
            searchEditText.setHintTextColor(Color.LTGRAY);
        } else {
            searchEditText.setTextColor(Color.BLACK);
            searchEditText.setHintTextColor(Color.GRAY);
        }

        // Set Up RecyclerView
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        messAdapter = new Messadapter(getContext(), arrayList, this::openDetailsFragment);
        recycle.setAdapter(messAdapter);

        // SearchView Functionality
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        return view;
    }

    private void filterList(String text) {
        searchlist.clear();

        if (!text.isEmpty()) {
            for (Model model : arrayList) {
                if (model.getMessname().toUpperCase().contains(text.toUpperCase()) ||
                        model.getLocation().toUpperCase().contains(text.toUpperCase())) {
                    searchlist.add(model);
                }
            }
        } else {
            searchlist.addAll(arrayList);
        }

        // Update Adapter with Filtered List
        messAdapter = new Messadapter(getContext(), searchlist, this::openDetailsFragment);
        recycle.setAdapter(messAdapter);
    }

    // Function to Open Fragment
    public void openDetailsFragment(int position) {
        Fragment fragment; // Initialize fragment

        switch (position) {
            case 0: // Sarvadnya mess will open
                fragment = new Sarvadnya();
                break;
            case 1: // Balaji mess will open
                fragment = new Balajimess();
                break;
            case 2: // Maratha mess will open
                fragment = new Marathamess();
                break;
            case 3: // Anshika mess will open
                fragment = new Anshikamess();
                break;
            case 4: // Yash mess will open
                fragment = new YashTiffin();
                break;
            case 5: // aadesh mess will open
                fragment = new Aadeshmess();
                break;
            case 6: // anapurna mess will open
                fragment = new Anapurnamessone();
                break;
            case 7: // anapurna mess will open
                fragment = new Anapurnamesstwo();
                break;
            default:
                Toast.makeText(getContext(), "No details available!", Toast.LENGTH_SHORT).show();
                return; // Exit if no fragment is found
        }

        if (getActivity() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}
