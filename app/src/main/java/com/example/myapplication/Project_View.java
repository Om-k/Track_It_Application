package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Project_View extends AppCompatActivity {

    TextView PHeading;
    Button createTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        PHeading = findViewById(R.id.projName);
        createTask = findViewById(R.id.task);

        Intent intent = getIntent();
        String PName = intent.getStringExtra("Pname");
        PHeading.setText(PName);

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Project_View.this,Task_Create.class);
                intent.putExtra("Pname",PName);
                startActivity(intent);
            }
        });
    }
}