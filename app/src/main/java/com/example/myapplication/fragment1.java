package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class fragment1 extends Fragment {

    ListView l;
    Button newPro;
    String tutorials[]
            = { "Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies","CS Subjects","CS Subjects","CS Subjects","CS Subjects","CS Subjects"
            ,"CS Subjects","CS Subjects","CS Subjects","CS Subjects","CS Subjects"};


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        l = view.findViewById(R.id.list);
        newPro = view.findViewById(R.id.newProject);
        //ArrayAdapter<String> arr;
        ArrayAdapter<String> arr = new ArrayAdapter<String>(
                requireContext(), // Obtain the Context object from the Fragment
                android.R.layout.simple_list_item_1,
                tutorials);
        l.setAdapter(arr);
        Toast.makeText(requireContext(), Log_In.userName+" Yooo", Toast.LENGTH_SHORT).show();
        newPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),Project_Create.class);

                startActivity(intent);
            }
        });

        return view;
    }
}