package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    ListView userList;
    List<Object> arrayField = new ArrayList<>();
    String[] userLead;
    int i=0,j=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        userList = findViewById(R.id.LU);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Leaderboard list

        db.collection(Log_In.userName).document("Projects").collection(Project_View.PName).document("Project details").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("Memebers")) {
                            // Field exists
                            arrayField = (List<Object>) documentSnapshot.get("Memebers");
                            Toast.makeText(Leaderboard.this, "Got array: " + arrayField, Toast.LENGTH_SHORT).show();

                            // Convert the List to a String array
                            userLead = new String[arrayField.size()];
                            for (i = 0; i < arrayField.size(); i++) {
                                userLead[i] = String.valueOf(arrayField.get(i));
                            }

                            for (j = 0; j < arrayField.size(); j++) {
                                Toast.makeText(Leaderboard.this, userLead[j] + " checking", Toast.LENGTH_SHORT).show();

                                final int index = j; // Create a new variable to hold the current value of j

                                db.collection(Log_In.userName).document("Projects").collection(Project_View.PName).document("Project details").collection("LeaderBorad").document(userLead[index]).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    try {
                                                        if (documentSnapshot.contains(userLead[index])) {
                                                            String val = String.valueOf(documentSnapshot.get(userLead[index]));
                                                            userLead[index] = userLead[index] + " " + val;
                                                            System.out.println(val);
                                                            Toast.makeText(Leaderboard.this, "val: " + val, Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(Leaderboard.this, userLead[index] + " not there", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (ArrayIndexOutOfBoundsException ae) {
                                                        System.out.println("Error Out");
                                                    }
                                                } else {
                                                    System.out.println("Didn't get doc");
                                                }

                                                // Check if all the updates have been completed
                                                if (index == arrayField.size() - 1) {

                                                    // Set up the list view with the array
                                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                                            Leaderboard.this,
                                                            android.R.layout.simple_list_item_1,
                                                            userLead
                                                    );
                                                    userList.setAdapter(adapter);
                                                }
                                            }
                                        });
                            }



                        } else {
                            // Field does not exist
                            Toast.makeText(Leaderboard.this, "Array field does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Document does not exist
                        Toast.makeText(Leaderboard.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle exceptions or errors that occurred during the retrieval
                    Toast.makeText(Leaderboard.this, "Error retrieving document", Toast.LENGTH_SHORT).show();
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Handle the exception
                        Log.e(TAG, "Error retrieving document", exception);
                    }
                }

                // Set up the button click listener

            }
        });

        //Leaderboard list end
    }
}