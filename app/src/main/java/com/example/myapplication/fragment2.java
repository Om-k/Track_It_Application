package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        return view;
    }
}