package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class fragment2 extends Fragment {
    ListView l;
    String tutorials[]
            = { "Task 1", "Task 2"};

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        l = view.findViewById(R.id.list1);
        //ArrayAdapter<String> arr;
        ArrayAdapter<String> arr = new ArrayAdapter<String>(
                requireContext(), // Obtain the Context object from the Fragment
                android.R.layout.simple_list_item_1,
                tutorials);
        l.setAdapter(arr);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click event here
                String clickedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(requireContext(), "Clicked item: " + clickedItem, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}