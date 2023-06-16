package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Project_View extends AppCompatActivity {

    TextView PHeading;
    Button createTask,Back,leaderB;
    ListView tskList;
    static String PName;

    List<Object> arrayField = new ArrayList<>();
    String[] projects;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        PHeading = findViewById(R.id.projName);
        createTask = findViewById(R.id.task);
        tskList = findViewById(R.id.tskList);

        Back = findViewById(R.id.Back1);
        leaderB = findViewById(R.id.lead);

        Intent intent = getIntent();
        PName = intent.getStringExtra("Pname");
        PHeading.setText(PName);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //DocumentReference documentRef = db.collection(Log_In.userName).document("Projects");
        //DocumentReference documentRef1 = documentRef.collection(PName).document("Project details");

        db.collection(Log_In.userName).document("Projects").collection(PName).document("Project details").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("Tasks")) {
                            // Field exists
                            arrayField = (List<Object>) documentSnapshot.get("Tasks");
                            Toast.makeText(Project_View.this, "Got array: " + arrayField, Toast.LENGTH_SHORT).show();

                            // Convert the List to a String array
                            projects = new String[arrayField.size()];
                            for (int i = 0; i < arrayField.size(); i++) {
                                projects[i] = String.valueOf(arrayField.get(i));
                            }

                            // Set up the list view with the array
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    Project_View.this,
                                    android.R.layout.simple_list_item_1,
                                    projects
                            );
                            tskList.setAdapter(adapter);
                        } else {
                            // Field does not exist
                            Toast.makeText(Project_View.this, "Array field does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Document does not exist
                        Toast.makeText(Project_View.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle exceptions or errors that occurred during the retrieval
                    Toast.makeText(Project_View.this, "Error retrieving document", Toast.LENGTH_SHORT).show();
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Handle the exception
                        Log.e(TAG, "Error retrieving document", exception);
                    }
                }

                // Set up the button click listener

            }
        });


        leaderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Project_View.this, Leaderboard.class);
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Project_View.this, HomePage.class);
                startActivity(intent);
            }
        });

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Project_View.this,Task_Create.class);
                intent.putExtra("Pname",PName);
                startActivity(intent);
            }
        });

        tskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click event here
                String clickedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(Project_View.this, "Clicked item: " + clickedItem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Project_View.this, TaskViewUser.class);
                intent.putExtra("TaskName",clickedItem);
                startActivity(intent);
            }
        });
    }
}