package hcu.info.pro3_g20021_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private static final int BUY_MODE = 0;
    private static final int ADD_BALANCE_MODE = 1;
    private static final int EDIT_PRODUCTS_MODE = 2;
    private static final int EDIT_USERS_MODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProductManager productManager = ProductManager.getInstance(this);
        UserManager userManager = UserManager.getInstance(this);

        // 商品購入ボタン
        Button startScanningButton = findViewById(R.id.buy_button);
        startScanningButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
            startActivityForResult(intent, BUY_MODE);
        });

        // 残高追加ボタン
        Button readFelicaButton = findViewById(R.id.add_balance_button);
        readFelicaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FelicaReaderActivity.class);
            startActivityForResult(intent, ADD_BALANCE_MODE);
        });

        // 商品登録・編集ボタン
        Button editProductsButton = findViewById(R.id.edit_products_button);
        editProductsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
            startActivityForResult(intent, EDIT_PRODUCTS_MODE);
        });

        // ユーザ登録・編集ボタン
        Button editUsersButton = findViewById(R.id.edit_users_button);
        editUsersButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FelicaReaderActivity.class);
            startActivityForResult(intent, EDIT_USERS_MODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BUY_MODE && resultCode == RESULT_OK) {
            // 商品購入
            String janCode = data.getStringExtra("janCode");
            if (janCode != null) {
                Toast.makeText(this, "janCode: " + janCode, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ADD_BALANCE_MODE && resultCode == RESULT_OK) {
            // 残高追加
            String IDm = data.getStringExtra("IDm");
            Intent intent = new Intent(MainActivity.this, AddBalanceActivity.class);
            intent.putExtra("IDm", IDm);
            startActivity(intent);
        } else if (requestCode == EDIT_PRODUCTS_MODE && resultCode == RESULT_OK) {
            // 商品登録・編集
            String janCode = data.getStringExtra("janCode");
            Intent intent = new Intent(MainActivity.this, EditProductsActivity.class);
            intent.putExtra("janCode", janCode);
            startActivity(intent);
        } else if (requestCode == EDIT_USERS_MODE && resultCode == RESULT_OK) {
            // ユーザ登録・編集
            String IDm = data.getStringExtra("IDm");
            Intent intent = new Intent(MainActivity.this, EditUsersActivity.class);
            intent.putExtra("IDm", IDm);
            startActivity(intent);
        }
    }
}
