package hcu.info.pro3_g20021_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SCAN_REQUEST_CODE = 1;
    private static final int READ_FELICA_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startScanningButton = findViewById(R.id.start_scanning_button);
        startScanningButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
            startActivityForResult(intent, SCAN_REQUEST_CODE);
        });

        Button readFelicaButton = findViewById(R.id.read_felica_button);
        readFelicaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FelicaReaderActivity.class);
            startActivityForResult(intent, READ_FELICA_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            String janCode = data.getStringExtra("janCode");
            if (janCode != null) {
                // スキャン結果を処理する
                Toast.makeText(this, "janCode: " + janCode, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "janCode: " + janCode);  // 追加
            }
        } else if (requestCode == READ_FELICA_REQUEST_CODE && resultCode == RESULT_OK) {
            String IDm = data.getStringExtra("IDm");
            if (IDm != null) {
                // 学生証の読み取り結果を処理する
                Toast.makeText(this, "IDm: " + IDm, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "IDm: " + IDm);  // 追加
            }
        }
    }
}
