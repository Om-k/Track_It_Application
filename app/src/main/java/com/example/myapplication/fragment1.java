package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class fragment1 extends Fragment {

    ListView l;
    Button newPro;
    List<Object> arrayField = new ArrayList<>();
    String[] projects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        l = view.findViewById(R.id.list);
        newPro = view.findViewById(R.id.newProject);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //DocumentReference documentRef = db.collection(Log_In.userName).document("Projects");
        //DocumentReference documentRef1 = db.collection(Log_In.userName).document(Log_In.userName + " user Data");

        db.collection(Log_In.userName).document(Log_In.userName + " user Data").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("Projects")) {
                            // Field exists
                            arrayField = (List<Object>) documentSnapshot.get("Projects");
                            //Toast.makeText(requireContext(), "Got array: " + arrayField, Toast.LENGTH_SHORT).show();

                            // Convert the List to a String array
                            projects = new String[arrayField.size()];
                            for (int i = 0; i < arrayField.size(); i++) {
                                projects[i] = String.valueOf(arrayField.get(i));
                            }

                            // Set up the list view with the array
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    requireContext(),
                                    android.R.layout.simple_list_item_1,
                                    projects
                            );
                            l.setAdapter(adapter);
                        } else {
                            // Field does not exist
                            Toast.makeText(requireContext(), "Array field does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Document does not exist
                        Toast.makeText(requireContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle exceptions or errors that occurred during the retrieval
                    Toast.makeText(requireContext(), "Error retrieving document", Toast.LENGTH_SHORT).show();
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Handle the exception
                        Log.e(TAG, "Error retrieving document", exception);
                    }
                }

                // Set up the button click listener

            }
        });

        newPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), Project_Create.class);
                startActivity(intent);
            }
        });

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click event here
                String clickedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(requireContext(), "Clicked item: " + clickedItem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireContext(),Project_View.class);
                intent.putExtra("Pname",clickedItem);
                startActivity(intent);
            }
        });

        return view;
    }
}
