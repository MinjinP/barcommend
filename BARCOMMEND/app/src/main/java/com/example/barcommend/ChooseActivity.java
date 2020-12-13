package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseActivity extends AppCompatActivity {
    ImageView productImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Button readBtn = (Button) findViewById(R.id.toRead); // sign up button
        Button closeBtn = (Button) findViewById(R.id.close);

        productImage = (ImageView) findViewById(R.id.productImage2);
        productImage.setImageBitmap(ConfirmActivity.bitmap);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}