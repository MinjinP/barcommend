package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

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
    private String Gtin, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Intent intent = getIntent();
        Gtin = intent.getStringExtra("Gtin");
        userID = intent.getStringExtra("userID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), CommentActivity.class), 101);
            }
        });

        //----------------------------------------------
        //리뷰 불러오기

        final ListView listView = (ListView)findViewById(R.id.listview);
        ViewCompat.setNestedScrollingEnabled(listView, true);

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
                    String[] getUserName = new String[list_cnt];
                    String[] getReviewText = new String[list_cnt];
                    String[] getStarPoint = new String[list_cnt];
                    String[] getReviewDate = new String[list_cnt];

                    //String[] strs = new String[]{"userName", "ReviewText", "StarPoint", "ReviewDate"};
                    // int[] ints = new int[] {R.id.rv_id, R.id.rv_text, R.id.ratingbarSmall, R.id.rv_date};
                    final CustomAdapter adapter;
                    adapter = new CustomAdapter();
                    listView.setAdapter(adapter);

                    for (int i = 0; i < list_cnt; i++) { //JSONArray 내 json 개수만큼 for문 동작
                        JSONObject jsonObject = jsonArray.getJSONObject(i); //i번째 Json데이터를 가져옴
                        getUserName[i] = jsonObject.getString("userName");
                        getReviewText[i] = jsonObject.getString("ReviewText");
                        getStarPoint[i] = jsonObject.getString("StarPoint");
                        getReviewDate[i] = jsonObject.getString("ReviewDate");
                        adapter.addItem(getUserName[i], getReviewText[i], getStarPoint[i], getReviewDate[i]);
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
                            } else {
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };

                WriteReviewRequest writereviewRequest = new WriteReviewRequest( userID, Gtin, comment, starPoint, ReviewDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue( ScrollingActivity.this );
                queue.add( writereviewRequest );
            }
        }
    }
}
