package com.example.barcommend.data;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReviewRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://alswls97.dothome.co.kr/ReviewRequest.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public ReviewRequest(String userID, String Gtin, String ReviewText, Float StarPoint, String ReviewDate, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("Gtin", Gtin);
        map.put("ReviewText", ReviewText);
        map.put("StarPoint", StarPoint + "");
        map.put("ReviewDate", ReviewDate);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}