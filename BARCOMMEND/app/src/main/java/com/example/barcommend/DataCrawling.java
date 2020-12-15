package com.example.barcommend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataCrawling {
    String urlFrame = "http://koreannet.or.kr/home/hpisSrchGtin.gs1?gtin=";
    Bitmap bitmap;
    String name, stmt = "";

    public DataCrawling(String gtin) {
        final String url = urlFrame + gtin;
        String text[] = new String [10];

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements contents = doc.select("div.detailImg");
                    URL url = new URL(contents.select("div").get(0).getElementsByTag("img").attr("src"));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                    contents = doc.select("div.productTit");
                    stmt = contents.select("div").get(0).getElementsByTag("div").text();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();

        try {
            mThread.join();
            name = stmt.substring(13);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
