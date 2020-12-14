package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.barcommend.data.RegisterRequest;
import com.example.barcommend.data.WriteReviewRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class ScrollingActivity extends AppCompatActivity {
    private PopupWindow mPopupWindow;
    private String scanNum, userID, userName;
    private String star, strcomment;

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupView = getLayoutInflater().inflate(R.layout.activity_comment, null);
                mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                star = "3"; //for debugggggggggggg
                strcomment = "GOODGOOD";


                Button cancel = (Button) popupView.findViewById(R.id.btn_confirm);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Completed " + star + strcomment, Toast.LENGTH_LONG).show();
                        mPopupWindow.dismiss();


                        //입력받은 별점, 후기와 작성일, 작성자 디비에 넣기
                        //userID(String), Gtin(String), ReviewText(String), StarPoint(Double), ReviewDate(String)
                        //String userID, scanNum
                        String ReviewText = strcomment;
                        Double StarPoint = Double.valueOf(star);
                        String ReviewDate = "2020-12-14";
                        Toast.makeText( getApplicationContext(), "성공", Toast.LENGTH_SHORT ).show();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject( response );
                                    boolean success = jsonObject.getBoolean( "success" );

                                    //회원가입 성공시
                                    if(success) {

                                        Toast.makeText( getApplicationContext(), "성공", Toast.LENGTH_SHORT ).show();
                                        //Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
                                        //startActivity( intent );

                                        //회원가입 실패시
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
                        WriteReviewRequest writereviewRequest = new WriteReviewRequest( userID, scanNum, ReviewText, StarPoint, ReviewDate, responseListener);
                        RequestQueue queue = Volley.newRequestQueue( ScrollingActivity.this );
                        queue.add( writereviewRequest );

                    }
                });
            }
        });



    }
}
