package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.barcommend.data.GetReviewRequest;
import com.example.barcommend.data.WriteReviewRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


        //----------------------------------------------
        //리뷰 불러오기
        String Gtin = scanNum;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("ScrollingActivity", "리뷰 불러오는중");
                    JSONArray jsonArray = new JSONArray( response );
                    Log.i("jsonArray", String.valueOf(jsonArray));

                    //받아온 데이터 하나씩 읽기
                    String dataArray = null; //Json Array를 String으로 저장하기 위한 변수 선언

                    int list_cnt = jsonArray.length(); //Json 배열 내 JSON 데이터 개수를 가져옴
                    String[] getUserName = new String[list_cnt]; //userName 저장용
                    String[] getReviewText = new String[list_cnt];
                    String[] getStarPoint = new String[list_cnt];
                    String[] getReviewDate = new String[list_cnt];

                    for (int i = 0; i < list_cnt; i++) { //JSONArray 내 json 개수만큼 for문 동작
                        JSONObject jsonObject = jsonArray.getJSONObject(i); //i번째 Json데이터를 가져옴
                        getUserName[i] = jsonObject.getString("userName");  //userName 값을 배열에 저장
                        getReviewText[i] = jsonObject.getString("ReviewText");
                        getStarPoint[i] = jsonObject.getString("StarPoint");
                        getReviewDate[i] = jsonObject.getString("ReviewDate");
                        Log.i("JSON Object", jsonObject + "");
                        Log.i("JsonParsing", getUserName[i] + "," + getReviewText[i] + "," + getStarPoint[i] + "," + getReviewDate[i]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetReviewRequest getreviewRequest = new GetReviewRequest( Gtin, responseListener );
        RequestQueue queue = Volley.newRequestQueue( ScrollingActivity.this );
        queue.add( getreviewRequest );

        //리뷰 화면에 보여주기
        //todo
        //-------------------------------------------------------
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 101) {
            if(intent != null) {
                final String comment = intent.getStringExtra("comment");
                final float star = intent.getFloatExtra("star", 0.0f);
                Date currentTime = Calendar.getInstance().getTime();
                final String ReviewDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);
                Toast.makeText( getApplicationContext(), star+":"+comment+" "+ReviewDate, Toast.LENGTH_SHORT ).show();

                Log.i("ScrollingActivity", "리뷰 추가 중");
                //입력받은 별점, 후기와 작성일, 작성자 디비에 넣기
                //userID(String), Gtin(String), ReviewText(String), StarPoint(Double), ReviewDate(String)
                //String userID, scanNum, comment
                final double starPoint = star;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );
                            //후기 추가 성공시
                            if(success) {
                                Log.i("리뷰 추가 성공", comment + ", " + star + ", " + ReviewDate);
                                //후기 추가 실패시
                            } else {
                                //Toast.makeText( getApplicationContext(), "실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 Volley를 이용해서 요청
                WriteReviewRequest writereviewRequest = new WriteReviewRequest( userID, scanNum, comment, starPoint, ReviewDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue( ScrollingActivity.this );
                queue.add( writereviewRequest );

            }
        }
    }
}
