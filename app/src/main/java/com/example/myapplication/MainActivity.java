package com.example.myapplication;

//import static androidx.core.app.NotificationCompatJellybean.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.FirebaseFirestore;

//import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button b1;
    EditText name,password,uName;
    public void loadUser(String name)
    {
        //Toast.makeText(MainActivity.this, "We Innnn.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,HomePage.class);
        intent.putExtra("key", name);
        startActivity(intent);
    }

    public  void updateUIOnLoad(FirebaseUser user)
    {

    }
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.Name);//"om1@gmail.com";
        password = findViewById(R.id.Password);//"Om@12345";
        uName= findViewById(R.id.UserName);
        b1 = findViewById(R.id.button);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = name.getText().toString();
                String passwordT = password.getText().toString();
                String usName = uName.getText().toString();
                Toast.makeText(MainActivity.this, email+" "+passwordT, Toast.LENGTH_SHORT).show();
                mAuth.createUserWithEmailAndPassword(email,passwordT).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      /*  FirebaseUser currentUser = mAuth.getCurrentUser();

                        if(currentUser != null) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid().toString();

                            Toast.makeText(MainActivity.this, userId, Toast.LENGTH_SHORT).show();



                            loadUser(usName);
                        }
                        else {*/
                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Success", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid().toString();

                                Toast.makeText(MainActivity.this, userId, Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Success.", Toast.LENGTH_SHORT).show();

                                Map<String, Object> user2 = new HashMap<>();
                                user2.put("name", usName);
                                user2.put("email", email);
                                user2.put("password", passwordT);

                                db.collection(usName).document(usName+" user Data")
                                        .set(user2)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Firestore", "Document added with ID: " + userId);
                                            }
                                        });
                                updateUIOnLoad(user);
                               loadUser(usName);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Failed", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                updateUIOnLoad(null);
                            }
                        //}
                    }
                });
            }
        });


        /*mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Success.", Toast.LENGTH_SHORT).show();
                            updateUIOnLoad(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUIOnLoad(null);
                        }
                    }
                });*/
    }



   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            loadUser();
        }


    }*/
}