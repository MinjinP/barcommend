package com.example.barcommend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();
        String scanNum = intent.getStringExtra("scanNum");

        TextView tv_scanNum = (TextView) findViewById(R.id.tv_scanNum);
        tv_scanNum.setText("번호: " + scanNum);

        Button yesButton = (Button) findViewById(R.id.yesButton); // sign in button
        Button noButton = (Button) findViewById(R.id.noButton); // sign up button

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}