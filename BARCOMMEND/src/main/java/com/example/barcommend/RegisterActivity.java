package com.example.barcommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.barcommend.data.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_id, et_pass, et_name, et_phone;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //아이디값 찾아주기
        et_id = findViewById( R.id.et_email );
        et_pass = findViewById( R.id.et_password );
        et_name = findViewById( R.id.et_name );
        et_phone = findViewById( R.id.phone_num );


        btn_register = findViewById( R.id.btn_register );
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                int userPhone = Integer.parseInt( et_phone.getText().toString() );

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            //회원가입 성공시
                            if(success) {
                                Toast.makeText( getApplicationContext(), "가입 성공", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
                                startActivity( intent );
                                finish();

                                //회원가입 실패시
                            } else {
                                Toast.makeText( getApplicationContext(), "가입 실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청
                RegisterRequest registerRequest = new RegisterRequest( userID, userPass, userName, userPhone, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this );
                queue.add( registerRequest );
                //Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_LONG).show();
                //finish();
            }
        });
    }
}