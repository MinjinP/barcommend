package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.barcommend.data.GetReviewRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChooseActivity extends AppCompatActivity {
    ImageView productImage;
    TextView tv;
    private String scanNum, userID, userName;
    public double point;
    String[] getStarPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Intent intent = getIntent();
        scanNum = intent.getStringExtra("Gtin");
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");

        productImage = (ImageView) findViewById(R.id.productImage2);
        productImage.setImageBitmap(ConfirmActivity.bitmap);


        //평점 계산 위한 리뷰 불러오기
        final String Gtin = scanNum;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("ChooseActivity", "평점 위한 리뷰 불러오는중");
                    JSONArray jsonArray = new JSONArray( response );
                    Log.i("jsonArray", String.valueOf(jsonArray));

                    //받아온 데이터 하나씩 읽기
                    int list_cnt = jsonArray.length(); //Json 배열 내 JSON 데이터 개수를 가져옴
                    getStarPoint = new String[list_cnt];

                    for (int i = 0; i < list_cnt; i++) { //JSONArray 내 json 개수만큼 for문 동작
                        JSONObject jsonObject = jsonArray.getJSONObject(i); //i번째 Json데이터를 가져옴
                        getStarPoint[i] = jsonObject.getString("StarPoint");
                        Log.i("JSON Object", jsonObject + "");
                        Log.i("JsonParsing",  getStarPoint[i]);
                    }

                    int cnt = getStarPoint.length;
                    point = 0;
                    for(int i=0; i < cnt; i++){
                        point += Double.parseDouble(getStarPoint[i]);
                    }
                    point = point / cnt;
                    point = Double.parseDouble(String.format("%.2f", point));
                    Log.i("평점 계산: ", String.valueOf(cnt) + ", " + String.valueOf(point));

                    tv = (TextView) findViewById(R.id.rating);
                    tv.setText("평점: " + point);

                } catch (JSONException e) {
                    Log.i("ChooseActivity", "평점 위한 리뷰 불러오기 실패");
                    e.printStackTrace();
                }
            }
        };
        GetReviewRequest getreviewRequest = new GetReviewRequest( Gtin, responseListener );
        RequestQueue queue = Volley.newRequestQueue( ChooseActivity.this );
        queue.add( getreviewRequest );


        Button readBtn = (Button) findViewById(R.id.toRead); // sign up button
        Button closeBtn = (Button) findViewById(R.id.close);
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