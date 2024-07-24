package hcu.info.pro3_g20021_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SCAN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startScanningButton = findViewById(R.id.start_scanning_button);
        startScanningButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
            startActivityForResult(intent, SCAN_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            String barcode = data.getStringExtra("barcode");
            if (barcode != null) {
                // スキャン結果を処理する
                // 例えば、Toastで表示する
                Toast.makeText(this, "スキャン結果: " + barcode, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
