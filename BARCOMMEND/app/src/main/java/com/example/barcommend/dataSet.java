package com.example.barcommend;

public class dataSet {
    private String userName;
    private String ReviewText;
    private String StarPoint;
    private String ReviewDate;

    public dataSet(String userName, String ReviewText, String StarPoint, String ReviewDate) {
        this.userName = userName;
        this.ReviewText = ReviewText;
        this.StarPoint = StarPoint;
        this.ReviewDate = ReviewDate;
    }

    public String getUserName(){
        return userName;
    }

    public String getReviewText() {
        return ReviewText;
    }

    public float getStarPoint() {
        return Float.valueOf(StarPoint);
    }

    public String getReviewDate() {
        return ReviewDate;
    }
}
