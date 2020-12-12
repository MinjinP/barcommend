package com.example.barcommend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQR extends AppCompatActivity {
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        // QR-code Scanner
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됨
        qrScan.setPrompt("바코드를 스캔하세요");
        qrScan.initiateScan();
    }

    // 스캔한 QR-code 결과값 받아 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            //result.getContents() : QR코드 읽어서 가져옴.
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ConfirmActivity.class)); //실제 구동 시 삭제
                // todo
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                // todo

                Intent intent = new Intent( getApplicationContext(), ConfirmActivity.class );
                intent.putExtra( "scanNum", result.getContents() );
                startActivity( intent );
            }
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}