package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.barcommend.data.ReviewRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScrollingActivity extends AppCompatActivity {
    private String scanNum, userID, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Intent intent = getIntent();
        scanNum = intent.getStringExtra("Gtin");
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // 새 창 출력
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), CommentActivity.class), 101);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 101) {
            if(intent != null) {
                String comment = intent.getStringExtra("comment");
                float star = intent.getFloatExtra("star", 0.0f);
                Date currentTime = Calendar.getInstance().getTime();
                String ReviewDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);
                Toast.makeText( getApplicationContext(), star+":"+comment+" "+ReviewDate, Toast.LENGTH_SHORT ).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {
                                Toast.makeText( getApplicationContext(), "성공", Toast.LENGTH_SHORT ).show();
                            } else {
                                Toast.makeText( getApplicationContext(), "실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청
                ReviewRequest writereviewRequest = new ReviewRequest( userID, scanNum, comment, star, ReviewDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue( ScrollingActivity.this );
                queue.add(writereviewRequest);
            }
        }
    }
}
