package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project_Create extends AppCompatActivity {
    //String memebers[] = {};
    List<String> memebers = new ArrayList<>();
    ListView memeberList;
    Button createProj,addUse;
    EditText maembName,projName;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);
        createProj = findViewById(R.id.create);
        addUse = findViewById(R.id.addUser);
        maembName = findViewById(R.id.mamberName);
        projName = findViewById(R.id.pName);

        memeberList = findViewById(R.id.ListM);

        createProj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> user2 = new HashMap<>();
                user2.put("Memebers", memebers);


                CollectionReference collectionRef = db.collection(Log_In.userName);
                DocumentReference documentRef = collectionRef.document(projName.getText().toString());
                //Map<String, Object> documentData = new HashMap<>();
                //documentData.put("arrayField", memeberList);
                documentRef.set(user2)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Project_Create.this, "Added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Project_Create.this,HomePage.class);
                                    //intent.putExtra("key", memebers);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Project_Create.this, "No added", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        addUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection(maembName.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            QuerySnapshot document = task.getResult();
                            if (!document.isEmpty()) {
                                // Collection exists
                                //memebers = Arrays.copyOf(memebers, memebers.length + 1);
                                //memebers[memebers.length - 1] = maembName.getText().toString();
                                memebers.add(maembName.getText().toString());
                                for(String i:memebers)
                                {
                                    System.out.println(i);
                                }
                                maembName.setText("");

                                ArrayAdapter<String> arr = new ArrayAdapter<String>(
                                        Project_Create.this,
                                        android.R.layout.simple_list_item_1,
                                        memebers);
                                memeberList.setAdapter(arr);
                                // Perform your logic here
                            } else {
                                // Collection does not exist
                                Toast.makeText(Project_Create.this, "User does'nt exist.", Toast.LENGTH_SHORT).show();
                                // Perform your logic here
                            }


                        }
                        else
                        {
                            Toast.makeText(Project_Create.this, "Chotto error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });


    }
}