package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskViewUser extends AppCompatActivity {

    TextView Name,Desp,Deadline,ProjName;
    Button complete;
    String ProjLead=" ",ProjNameM=" ";
    Date date;
    ImageView high,med,low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view_user);

        Name = findViewById(R.id.TaskName);
        Desp = findViewById(R.id.descp);
        Deadline = findViewById(R.id.dead);
        complete = findViewById(R.id.Complete);
        ProjName = findViewById(R.id.ProjName);

        high = findViewById(R.id.high);
        med = findViewById(R.id.med);
        low = findViewById(R.id.low);

        high.setAlpha(0.0f);
        med.setAlpha(0.0f);
       // low.setAlpha(0.4f);

        Intent intent = getIntent();
        String taskName = intent.getStringExtra("TaskName");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentRef1 = db.collection(Log_In.userName).document("Tasks").collection(taskName).document("Tasks details");

        documentRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()) {
                    Name.setText(documentSnapshot.get("Name").toString());

                    if (documentSnapshot.contains("Deadline")) {
                        Timestamp timestamp = documentSnapshot.getTimestamp("Deadline");
                        date = timestamp.toDate();

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = dateFormat.format(date);
                        System.out.println("date=" + formattedDate);

                        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        String formattedTime = timeFormat.format(date);
                        System.out.println("time=" + formattedTime);

                        Deadline.setText("Date:" + formattedDate + "\nTime:" + formattedTime);
                    }

                    if (documentSnapshot.contains("Description")) {
                        Desp.setText(documentSnapshot.get("Description").toString());
                        System.out.println("desc = "+Desp.getText().toString());
                    }

                    if (documentSnapshot.contains("Project Name")) {
                        ProjName.setText(documentSnapshot.get("Project Name").toString());
                        ProjNameM = documentSnapshot.get("Project Name").toString();
                        System.out.println("PN = "+ProjNameM);
                    }

                    if (documentSnapshot.contains("Project lead")) {
                        ProjLead = documentSnapshot.get("Project lead").toString();

                        System.out.println("Pl = "+ProjLead);
                    }
                }
            }
        });


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Updating value in leaderboard
                DocumentReference documentReference=db.collection(ProjLead).document("Projects").collection(ProjNameM).document("Project details").collection("LeaderBorad").document(Log_In.userName);
                db.collection(ProjLead).document("Projects").collection(ProjNameM).document("Project details").collection("LeaderBorad").document(Log_In.userName).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    DocumentSnapshot documentSnapshot = task.getResult();

                                    Date currentDate = new Date(); // Get the current date and time

                                    if (currentDate.before(date)) {
                                        documentReference.update(Log_In.userName,documentSnapshot.getLong(Log_In.userName)+20);
                                    } else {
                                        documentReference.update(Log_In.userName,documentSnapshot.getLong(Log_In.userName)+10);
                                    }

                                }
                                else
                                {
                                    Toast.makeText(TaskViewUser.this, "Leadeboard not there", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                //Updating value in leaderboard done

                //Removing from creater
                DocumentReference documentRef1 = db.collection(ProjLead).document("Projects").collection(ProjNameM).document("Project details");

                documentRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                List<Object> arrayData = (List<Object>) document.get("Tasks");

                                // Remove the desired element by its value
                                Object elementToRemove = Name.getText().toString();
                                arrayData.remove(elementToRemove);

                                documentRef1.update("Tasks", arrayData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Array data updated successfully

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Error updating array data
                                            }
                                        });
                            } else {
                                // Document does not exist
                            }
                        } else {
                            // Error retrieving document
                        }
                    }
                });
                //Removing from creater done

                //Removing from user list
                DocumentReference documentRef = db.collection(Log_In.userName).document(Log_In.userName+" user Data");

                documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                List<Object> arrayData = (List<Object>) document.get("Tasks");

                                // Remove the desired element by its value
                                Object elementToRemove = Name.getText().toString();
                                arrayData.remove(elementToRemove);

                                documentRef.update("Tasks", arrayData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Array data updated successfully
                                                Intent intent = new Intent(TaskViewUser.this, HomePage.class);
                                                intent.putExtra("key", Log_In.userName);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Error updating array data
                                            }
                                        });
                            } else {
                                // Document does not exist
                            }
                        } else {
                            // Error retrieving document
                        }
                    }
                });
                //Removing from user list done

                //Removing the task collection
                //Removing the task collection from user
                db.collection(Log_In.userName).document("Tasks").collection(taskName).
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for (QueryDocumentSnapshot doc:task.getResult())
                                    {
                                        doc.getReference().delete();
                                    }
                                }
                            }
                        });
                //Removing the task collection from user done

                //Removing the task collection from Owner
                db.collection(ProjLead).document("Projects").collection(ProjNameM).document("Tasks").collection(taskName).
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for (QueryDocumentSnapshot doc:task.getResult())
                                    {
                                        doc.getReference().delete();
                                    }
                                }
                            }
                        });
                //Removing the task collection from Owner done

                //Removing the task collection done


            }


        });
    }
}