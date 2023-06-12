package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task_Create extends AppCompatActivity {

    TextView maembName,tUName,taskName;
    Button addUse,create;
    ListView memeberList;
    List<String> memebers = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(Log_In.userName);
        final DocumentReference[] documentRef1 = {collectionRef.document(Log_In.userName + " user Data")};


        Intent intent = getIntent();
        String PName = intent.getStringExtra("Pname");

        addUse = findViewById(R.id.button3);
        memeberList = findViewById(R.id.liss);
        maembName = findViewById(R.id.editTextText2);
        create = findViewById(R.id.button2);
        taskName = findViewById(R.id.editTextText);




        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> task = new HashMap<>();
                task.put("Name", taskName.getText().toString());
                task.put("Users",memebers);



                CollectionReference collectionRef = db.collection(Log_In.userName);
                //DocumentReference documentRef1 = collectionRef.document(Log_In.userName+" user Data");
                final DocumentReference[] documentRef = {collectionRef.document("Projects")};
                CollectionReference collectionRef1 = documentRef[0].collection(PName).document("Tasks").collection(taskName.getText().toString());

                collectionRef1.document(taskName.getText().toString()+" details").set(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Task_Create.this, "Added", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(Project_Create.this,HomePage.class);
                            //intent.putExtra("key", memebers);
                            //startActivity(intent);
                        } else {
                            Toast.makeText(Task_Create.this, "No added", Toast.LENGTH_SHORT).show();
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
                                        Task_Create.this,
                                        android.R.layout.simple_list_item_1,
                                        memebers);
                                memeberList.setAdapter(arr);
                                // Perform your logic here
                            } else {
                                // Collection does not exist
                                Toast.makeText(Task_Create.this, "User does'nt exist.", Toast.LENGTH_SHORT).show();
                                // Perform your logic here
                            }


                        }
                        else
                        {
                            Toast.makeText(Task_Create.this, "Chotto error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}