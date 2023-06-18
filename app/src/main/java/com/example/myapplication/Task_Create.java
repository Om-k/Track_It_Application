package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.Timestamp;


public class Task_Create extends AppCompatActivity {

    TextView maembName,tUName,taskName,dline,descp;
    Button addUse,create,deadline;
    ListView memeberList;
    Timestamp timestamp;
    String[] priority= {"High","Med","Low"};
    Spinner autoCompleteTextView;
    SpinnerAdapter arrayAdapter;

    Calendar selectedDate;
    List<String> memebers = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);


        //Priority list

        autoCompleteTextView = findViewById(R.id.spinner);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,priority);
        autoCompleteTextView.setAdapter(arrayAdapter);


        //Priority list done

        FirebaseFirestore db = FirebaseFirestore.getInstance();
       // CollectionReference collectionRef = db.collection(Log_In.userName);
        //final DocumentReference[] documentRef1 = {collectionRef.document(Log_In.userName + " user Data")};


        Intent intent = getIntent();
        String PName = intent.getStringExtra("Pname");

        addUse = findViewById(R.id.button3);
        memeberList = findViewById(R.id.liss);
        maembName = findViewById(R.id.editTextText2);
        create = findViewById(R.id.button2);
        taskName = findViewById(R.id.editTextText);
        deadline = findViewById(R.id.button4);
        dline = findViewById(R.id.textView2);
        descp = findViewById(R.id.pName3);


       /* deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(Task_Create.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dline.setText(String.valueOf(year)+"."+String.valueOf(month)+"."+String.valueOf(day));
                        TimePickerDialog dialog1 = new TimePickerDialog(Task_Create.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hours, int min) {
                                dline.setText(dline.getText().toString()+" "+String.valueOf(hours)+":"+String.valueOf(min));
                            }
                        },15, 00,true);
                        dialog1.show();
                    }
                },2022,0,15);
                dialog.show();



            }
        });*/

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(Task_Create.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, day);


                        dline.setText("Date:"+String.valueOf(year)+"."+String.valueOf(month)+"."+String.valueOf(day));

                        TimePickerDialog dialog1 = new TimePickerDialog(Task_Create.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hours, int min) {
                                selectedDate.set(Calendar.HOUR_OF_DAY, hours);
                                selectedDate.set(Calendar.MINUTE, min);

                                dline.setText("Time:"+dline.getText().toString()+" "+String.valueOf(hours)+":"+String.valueOf(min));
                            }
                        }, 15, 00, true);
                        dialog1.show();
                    }
                }, 2022, 0, 15);
                dialog.show();
            }
        });



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the timestamp from the selected date and time
                timestamp = new Timestamp(selectedDate.getTime());

                Map<String, Object> task = new HashMap<>();
                task.put("Name", taskName.getText().toString());
                task.put("Users",memebers);
                task.put("Deadline",timestamp);
                task.put("Description",descp.getText().toString());
                task.put("Project Name",PName.toString());
                task.put("Project lead",Log_In.userName);

                Map<String, Object> taskUser = new HashMap<>();
                taskUser.put("Name", taskName.getText().toString());
                taskUser.put("Deadline",timestamp);
                taskUser.put("Description",descp.getText().toString());
                taskUser.put("Project Name",PName.toString());
                taskUser.put("Project lead",Log_In.userName);





                CollectionReference collectionRef = db.collection(Log_In.userName);
                //DocumentReference documentRef1 = collectionRef.document(Log_In.userName+" user Data");
                //final DocumentReference[] documentRef = {collectionRef.document("Projects")};
                //CollectionReference collectionRef1 = documentRef[0].collection(PName).document("Tasks").collection(taskName.getText().toString());

                ///adding task details to user
                for (String member:memebers)
                {

                    ///Adding tasks data to user
                    DocumentReference dc = db.collection(member).document("Tasks").collection(taskName.getText().toString()).document("Tasks details");

                    dc.set(taskUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Task_Create.this, "User task data added", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Task_Create.this, "User task data not added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    ///Adding tasks data to user done

                    //Adding to task array for user
                    //documentRef = collectionRef.document(Log_In.userName+" user Data");
                    DocumentReference documentRef2 = db.collection(member).document(member+" user Data");
                    documentRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    if (documentSnapshot.contains("Tasks")) {
                                        // Field exists
                                        Object fieldValue = documentSnapshot.get("Tasks");
                                        Toast.makeText(Task_Create.this, "List there"+ member, Toast.LENGTH_SHORT).show();
                                        //Log.d(TAG, "Field value: " + fieldValue);
                                        List<Object> existingArray = (List<Object>) documentSnapshot.get("Tasks");

                                        // Update the existing array field with new values
                                        existingArray.add(taskName.getText().toString());

                                        documentRef2.update("Tasks", existingArray);
                                    } else {
                                        // Field does not exist
                                        //Log.d(TAG, "Field does not exist");
                                        Toast.makeText(Task_Create.this, "List not there"+ member, Toast.LENGTH_SHORT).show();

                                        List<Object> newArray = new ArrayList<>();
                                        newArray.add(taskName.getText().toString());

                                        Map<String, Object> addProj = new HashMap<>();
                                        addProj.put("Tasks", newArray);


                                        documentRef2.update(addProj);

                                    }
                                } else {
                                    // Document does not exist
                                    //Log.d(TAG, "Document does not exist");
                                    Toast.makeText(Task_Create.this, "Document not there"+ member, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Handle exceptions or errors that occurred during the retrieval
                                Exception exception = task.getException();
                                if (exception != null) {
                                    // Handle the exception
                                    //Log.e(TAG, "Error retrieving document", exception);
                                }
                            }
                        }
                    });

                    //Adding to array done
                }
                ///adding task details to user end


                /*db.collection(Log_In.userName)
                        .get()
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                // Documents retrieved successfully
                                //QueryDocumentSnapshot document = task1.getResult();
                                for (QueryDocumentSnapshot document : task1.getResult()) {
                                    // Access individual document data
                                    String documentId = document.getId();
                                    Map<String, Object> data = document.getData();
                                    ArrayList projects = (ArrayList) data.get("Projects");
                                    System.out.println(projects);
                                    Toast.makeText(Task_Create.this, "Yes arr", Toast.LENGTH_SHORT).show();

                                    // Process document data as needed
                                    // ...
                                }
                            } else {
                                // Handle failure to retrieve documents
                                //Exception exception = task.getException();
                                // ...'
                                Toast.makeText(Task_Create.this, "No arr", Toast.LENGTH_SHORT).show();
                            }
                        });*/

                Intent intent = getIntent();
                DocumentReference documentRef2 = db.collection(Log_In.userName).document("Projects")
                        .collection(intent.getStringExtra("Pname")).document("Project details");

                //Adding to array
                //documentRef = collectionRef.document(Log_In.userName+" user Data");
                documentRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                if (documentSnapshot.contains("Tasks")) {
                                    // Field exists
                                    Object fieldValue = documentSnapshot.get("Tasks");
                                    Toast.makeText(Task_Create.this, "List there", Toast.LENGTH_SHORT).show();
                                    //Log.d(TAG, "Field value: " + fieldValue);
                                    List<Object> existingArray = (List<Object>) documentSnapshot.get("Tasks");

                                    // Update the existing array field with new values
                                    existingArray.add(taskName.getText().toString());

                                    documentRef2.update("Tasks", existingArray);
                                } else {
                                    // Field does not exist
                                    //Log.d(TAG, "Field does not exist");
                                    Toast.makeText(Task_Create.this, "List not there", Toast.LENGTH_SHORT).show();

                                    List<Object> newArray = new ArrayList<>();
                                    newArray.add(taskName.getText().toString());

                                    Map<String, Object> addProj = new HashMap<>();
                                    addProj.put("Tasks", newArray);


                                    documentRef2.update(addProj);

                                }
                            } else {
                                // Document does not exist
                                //Log.d(TAG, "Document does not exist");
                                Toast.makeText(Task_Create.this, "Document not there", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle exceptions or errors that occurred during the retrieval
                            Exception exception = task.getException();
                            if (exception != null) {
                                // Handle the exception
                                //Log.e(TAG, "Error retrieving document", exception);
                            }
                        }
                    }
                });

                //Adding to array done



                collectionRef.document("Projects").collection(PName).document("Tasks").collection(taskName.getText().toString()).document(taskName.getText().toString()+" details").set(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Task_Create.this, "Added", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(Task_Create.this,Project_View.class);
                            intent1.putExtra("Pname",PName);
                            startActivity(intent1);
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