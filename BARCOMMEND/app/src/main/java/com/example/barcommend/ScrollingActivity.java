package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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


public class ScrollingActivity extends AppCompatActivity {
    private PopupWindow mPopupWindow;
    private String scanNum, userID;
    private String star, strcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Intent intent = getIntent();
        scanNum = intent.getStringExtra("Gtin");
        userID = intent.getStringExtra("userID");

        Log.i("ScrollingActivity", "Scrolling 들어옴");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());


        //리뷰 불러오기
        //String Gtin = scanNum;
        final String Gtin = "8801382131038"; //debug!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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


        //리뷰 추가 버튼 누를 시
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ScrollingActivity", "리뷰 추가 중");
                View popupView = getLayoutInflater().inflate(R.layout.activity_comment, null);
                mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                //리뷰 저장 버튼 누르면
                Button cancel = (Button) popupView.findViewById(R.id.btn_confirm);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //xml에서 별점, 코멘트 받아오기
                        star = "4.5"; //for debugggggggggggg
                        strcomment = "HAHA";

                        //
                        mPopupWindow.dismiss();

                        //입력받은 별점, 후기와 작성일, 작성자 디비에 넣기
                        //userID(String), Gtin(String), ReviewText(String), StarPoint(Double), ReviewDate(String)
                        //String userID, scanNum
                        final String ReviewText = strcomment;
                        final Double StarPoint = Double.valueOf(star);
                        final String ReviewDate = "2020-12-14";

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject( response );
                                    boolean success = jsonObject.getBoolean( "success" );
                                    //후기 추가 성공시
                                    if(success) {
                                        Log.i("리뷰 추가 성공", ReviewText + ", " + StarPoint + ", " + ReviewDate);
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
                        WriteReviewRequest writereviewRequest = new WriteReviewRequest( userID, Gtin, ReviewText, StarPoint, ReviewDate, responseListener);
                        RequestQueue queue = Volley.newRequestQueue( ScrollingActivity.this );
                        queue.add( writereviewRequest );

                    }
                });
            }
        });

    }
}
