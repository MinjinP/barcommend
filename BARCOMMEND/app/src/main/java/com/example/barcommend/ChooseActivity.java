package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseActivity extends AppCompatActivity {
    ImageView productImage;
    private String scanNum, userID, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Intent intent = getIntent();
        scanNum = intent.getStringExtra("Gtin");
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");

        Button readBtn = (Button) findViewById(R.id.toRead); // sign up button
        Button closeBtn = (Button) findViewById(R.id.close);

        productImage = (ImageView) findViewById(R.id.productImage2);
        productImage.setImageBitmap(ConfirmActivity.bitmap);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);

                intent.putExtra( "Gtin", scanNum );
                intent.putExtra( "userID", userID );
                intent.putExtra( "userName", userName);

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