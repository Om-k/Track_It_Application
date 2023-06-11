package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Log_In extends AppCompatActivity {
    Button b1;
    static String userName = "New";
    EditText name,password,uName;

    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.email);//"om1@gmail.com";
        password = findViewById(R.id.pass);//"Om@12345";
        uName = findViewById(R.id.userName);
        //uName= findViewById(R.id.UserName);
        b1 = findViewById(R.id.LogIn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ////////////////////////////////////////////////////////////////////
                //Fast Login section to be removed after testing
                Intent intent = new Intent(Log_In.this,HomePage.class);
                intent.putExtra("key", "New");
                startActivity(intent);
                //Fast Login section to be removed after testing
                ////////////////////////////////////////////////////////////////////


                String email = name.getText().toString();
                String passwordT = password.getText().toString();
                //String usName;
                Toast.makeText(Log_In.this, email+" "+passwordT, Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(Log_In.this,HomePage.class);
                mAuth.signInWithEmailAndPassword(email,passwordT).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String userId = mAuth.getUid();
                        if(task.isSuccessful())
                        {
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //String userId = user.getUid().toString();

                            Toast.makeText(Log_In.this, userId, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Log_In.this,HomePage.class);

                            String usName;
                            String usName1 =  uName.getText().toString();//doc.getString("name");
                            userName = usName1;
                            db.collection(usName1).document(usName1+" user Data").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Log_In.this, "Lesss gooo", Toast.LENGTH_SHORT).show();

                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                String emailGot = document.get("email").toString();
                                                                Toast.makeText(Log_In.this, emailGot+" "+email, Toast.LENGTH_SHORT).show();
                                                                // Do something with the field value
                                                                //System.out.println("Field Value: " + fieldValue);
                                                                if(emailGot.equals(email))
                                                                {
                                                                      intent.putExtra("key", usName1);
                                                                      startActivity(intent);
                                                                      Toast.makeText(Log_In.this, usName1, Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(Log_In.this, "Wrong username.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                System.out.println("Document does not exist");
                                                            }




                                        }
                                        else
                                        {
                                            Toast.makeText(Log_In.this, "Nopes.", Toast.LENGTH_SHORT).show();
                                            intent.putExtra("key", "usName1");
                                        }
                                    }
                              //  }
                            });

                           // startActivity(intent);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w("Failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Log_In.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUIOnLoad(null);
                        }
                    }
                });
              /*  mAuth.createUserWithEmailAndPassword(email,passwordT).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String userId = mAuth.getUid();

                        if(userId != null) {
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //String userId = user.getUid().toString();

                            Toast.makeText(Log_In.this, userId, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Log_In.this,HomePage.class);
                            //String usName = db.document(userId).getParent().getId().toString();
                            intent.putExtra("key", "usName");
                            startActivity(intent);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w("Failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Log_In.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUIOnLoad(null);
                        }
                        //}
                    }
                });*/
            }
        });
    }
}