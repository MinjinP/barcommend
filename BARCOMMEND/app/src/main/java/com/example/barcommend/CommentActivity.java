package com.example.barcommend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class CommentActivity extends AppCompatActivity {
    RatingBar ratingBar;
    EditText inputComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        inputComment = (EditText)findViewById(R.id.inputComment);
        Button confirmBtn = (Button)findViewById(R.id.btn_confirm);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToReview();
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if(intent != null) {
            float rating = intent.getFloatExtra("rating", 0.0f);
            ratingBar.setRating(rating);
        }
    }

    public void returnToReview() {
        String comment = inputComment.getText().toString();
        float star = ratingBar.getRating();

        Intent intent = new Intent();
        intent.putExtra("comment", comment);
        intent.putExtra("star", star);

        setResult(RESULT_OK, intent);
        finish();
    }
}