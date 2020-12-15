package com.example.barcommend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.barcommend.data.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText et_id, et_pass, et_name, et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = (EditText)findViewById( R.id.et_email );
        et_pass = (EditText)findViewById( R.id.et_password );
        et_name = (EditText)findViewById( R.id.et_name );
        et_phone = (EditText)findViewById( R.id.phone_num );

        Button btn_register = (Button)findViewById( R.id.btn_register );
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                String userPhone = et_phone.getText().toString();

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

                RegisterRequest registerRequest = new RegisterRequest( userID, userPass, userName, userPhone, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this );
                queue.add( registerRequest );
                finish();
            }

        });
    }
}