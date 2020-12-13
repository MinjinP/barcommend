package com.example.barcommend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConfirmActivity extends AppCompatActivity {
    String url = "http://koreannet.or.kr/home/hpisSrchGtin.gs1?gtin=";
    ImageView productImage;
    public static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        productImage = (ImageView) findViewById(R.id.productImage);

        Intent intent = getIntent();
        String gtin = intent.getStringExtra("scanNum");


        gtin = "8801382131038"; //debug!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        final String finalUrl = url + gtin;
        TextView tv_scanNum = (TextView) findViewById(R.id.tv_scanNum);
        tv_scanNum.setText("번호: " + gtin);

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(finalUrl).get();
                    Elements contents = doc.select("div.detailImg");
                    URL url = new URL(contents.select("div").get(0).getElementsByTag("img").attr("src"));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();

        try {
            mThread.join();
            productImage.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Button yesButton = (Button) findViewById(R.id.yesButton);
        Button noButton = (Button) findViewById(R.id.noButton);

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