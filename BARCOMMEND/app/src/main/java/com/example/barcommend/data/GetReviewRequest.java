package com.example.barcommend.data;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetReviewRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://alswls97.dothome.co.kr/GetReview.php";
    private Map<String, String> map;

    public GetReviewRequest(String Gtin, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("Gtin", Gtin);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
