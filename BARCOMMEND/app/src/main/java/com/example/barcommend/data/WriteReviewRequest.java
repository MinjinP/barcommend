package com.example.barcommend.data;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteReviewRequest extends StringRequest {
    final static private String URL = "http://alswls97.dothome.co.kr/WriteReview.php";
    private Map<String, String> map;

    public WriteReviewRequest(String userID, String Gtin, String ReviewText, Double StarPoint, String ReviewDate, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

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
