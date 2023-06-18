package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProjViewFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //TextView PHeading;
    Button createTask;//Back, leaderB;
    ListView tskList;
    static String PName;

    List<Object> arrayField = new ArrayList<>();
    String[] projects;

    public ProjViewFrag() {
        // Required empty public constructor
    }

    public static ProjViewFrag newInstance(String param1, String param2) {
        ProjViewFrag fragment = new ProjViewFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proj_view, container, false);

        //PHeading = view.findViewById(R.id.projName);
        createTask = view.findViewById(R.id.button6);
        tskList = view.findViewById(R.id.txtL);
        //Back = view.findViewById(R.id.Back1);
        //leaderB = view.findViewById(R.id.lead);

        Intent intent = getActivity().getIntent();
        PName = intent.getStringExtra("Pname");
        //PHeading.setText(PName);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Log_In.userName).document("Projects").collection(PName).document("Project details").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("Tasks")) {
                            // Field exists
                            arrayField = (List<Object>) documentSnapshot.get("Tasks");
                            Toast.makeText(getActivity(), "Got array: " + arrayField, Toast.LENGTH_SHORT).show();

                            // Convert the List to a String array
                            projects = new String[arrayField.size()];
                            for (int i = 0; i < arrayField.size(); i++) {
                                projects[i] = String.valueOf(arrayField.get(i));
                            }

                            // Set up the list view with the array
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    getActivity(),
                                    android.R.layout.simple_list_item_1,
                                    projects
                            );
                            tskList.setAdapter(adapter);
                        } else {
                            // Field does not exist
                            Toast.makeText(getActivity(), "Array field does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Document does not exist
                        Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle exceptions or errors that occurred during the retrieval
                    Toast.makeText(getActivity(), "Error retrieving document", Toast.LENGTH_SHORT).show();
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Handle the exception
                        Log.e("ProjViewFrag", "Error retrieving document", exception);
                    }
                }
            }
        });

       /* leaderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Leaderboard.class);
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomePage.class);
                startActivity(intent);
            }
        });*/

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Task_Create.class);
                intent.putExtra("Pname", PName);
                startActivity(intent);
            }
        });

        tskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click event here
                String clickedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), "Clicked item: " + clickedItem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TaskViewUser.class);
                intent.putExtra("TaskName", clickedItem);
                startActivity(intent);
            }
        });

        return view;
    }
}
