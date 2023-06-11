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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;

public class Project_Create extends AppCompatActivity {
    String memebers[] = {};

    ListView memeberList;
    Button createProj,addUse;
    EditText maembName;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);
        createProj = findViewById(R.id.create);
        addUse = findViewById(R.id.addUser);
        maembName = findViewById(R.id.mamberName);
        memeberList = findViewById(R.id.ListM);

        createProj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Project_Create.this,fragment1.class);
                intent.putExtra("key", memebers);
                startActivity(intent);
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
                                memebers = Arrays.copyOf(memebers, memebers.length + 1);
                                memebers[memebers.length - 1] = maembName.getText().toString();
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