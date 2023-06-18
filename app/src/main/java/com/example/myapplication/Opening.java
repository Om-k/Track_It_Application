package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Opening extends AppCompatActivity {
    Button log,sign,glow;
    boolean isButtonClicked = false;
    private ConstraintLayout parentLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        log = findViewById(R.id.LoginM);
        sign = findViewById(R.id.SignUpM);
        //glow = findViewById(R.id.glow);
        parentLayout = findViewById(R.id.CL);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Opening.this,Log_In.class);
                startActivity(intent);
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Opening.this, MainActivity.class);
                startActivity(intent);
            }
        });

       /* glow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isButtonClicked = !isButtonClicked;
                updateButtonBorder();
            }
        });*/
    }

    private void updateButtonBorder() {
        if (isButtonClicked) {
            glow.setBackgroundResource(R.drawable.button_border_glow);
        } else {
            glow.setBackgroundResource(R.drawable.button_border);
        }
    }
}