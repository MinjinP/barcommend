package com.example.barcommend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmActivity extends AppCompatActivity {
    String url = "http://koreannet.or.kr/home/hpisSrchGtin.gs1?gtin=";
    ImageView productImage;
    public static Bitmap bitmap;
    private String gtin, userID, userName;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        productImage = (ImageView) findViewById(R.id.productImage);

        Intent intent = getIntent();
        gtin = intent.getStringExtra("scanNum");
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");

        gtin = "8801382131038"; //debug!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        TextView tv_scanNum = (TextView) findViewById(R.id.tv_scanNum);
        tv_scanNum.setText("번호: " + gtin);

        DataCrawling crawl = new DataCrawling(gtin);
        bitmap = crawl.bitmap;
        name = crawl.name;
        productImage.setImageBitmap(bitmap);

        Button yesButton = (Button) findViewById(R.id.yesButton);
        Button noButton = (Button) findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChooseActivity.class);

                intent.putExtra( "Gtin", gtin );
                intent.putExtra( "userID", userID );
                intent.putExtra( "userName", userName);
                intent.putExtra("itemName", name);

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