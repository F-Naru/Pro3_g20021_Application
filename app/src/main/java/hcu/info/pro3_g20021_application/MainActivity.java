package hcu.info.pro3_g20021_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SCAN_REQUEST_CODE = 1;
    private static final int READ_FELICA_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ProductManagerのインスタンスをcontextを渡して初期化
        ProductManager productManager = ProductManager.getInstance(this);

        Button startScanningButton = findViewById(R.id.buy_button);
        startScanningButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
            startActivityForResult(intent, SCAN_REQUEST_CODE);
        });

        Button readFelicaButton = findViewById(R.id.add_balance_button);
        readFelicaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FelicaReaderActivity.class);
            startActivityForResult(intent, READ_FELICA_REQUEST_CODE);
        });

        Button editProductsButton = findViewById(R.id.edit_products_button);
        editProductsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProductsActivity.class);
            startActivity(intent);
        });

        Button editUsersButton = findViewById(R.id.edit_users_button);
        editUsersButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditUsersActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            String janCode = data.getStringExtra("janCode");
            if (janCode != null) {
                Toast.makeText(this, "janCode: " + janCode, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_FELICA_REQUEST_CODE && resultCode == RESULT_OK) {
            String IDm = data.getStringExtra("IDm");
            if (IDm != null) {
                Toast.makeText(this, "IDm: " + IDm, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
