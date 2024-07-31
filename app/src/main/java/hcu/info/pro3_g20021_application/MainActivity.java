package hcu.info.pro3_g20021_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int SCAN_REQUEST_CODE = 1;
    private static final int READ_FELICA_REQUEST_CODE = 2;

    private ProductManager productManager;
    private UserManager userManager;
    private TextView textViewProducts;
    private TextView textViewUsers;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productManager = new ProductManager();
        userManager = new UserManager();

        textViewProducts = findViewById(R.id.text_view_products);
        textViewUsers = findViewById(R.id.text_view_users);
        updateProductList();
        updateUserList();

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
                Log.d("MainActivity", "janCode: " + janCode);

                // 例としてデフォルトの商品情報を追加
                Product product = new Product(janCode, 100, 10, new Date(), new Date());
                productManager.addProduct(product);
                updateProductList();
            }
        } else if (requestCode == READ_FELICA_REQUEST_CODE && resultCode == RESULT_OK) {
            String IDm = data.getStringExtra("IDm");
            if (IDm != null) {
                // 学生証の読み取り結果を処理する
                Toast.makeText(this, "IDm: " + IDm, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "IDm: " + IDm);

                // IDmでユーザーを検索して表示
                User user = userManager.findUserByIdm(IDm);
                if (user != null) {
                    Toast.makeText(this, "ユーザー名: " + user.getName() + ", 残高: " + user.getPrepaidBalance() + "円", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "ユーザーが見つかりませんでした", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void updateProductList() {
        StringBuilder sb = new StringBuilder();
        sb.append("商品\n");
        for (Product product : productManager.getProducts()) {
            sb.append("JANコード: ").append(product.getJanCode()).append("\n");
            sb.append("価格: ").append(product.getPrice()).append("円\n");
            sb.append("在庫数: ").append(product.getStock()).append("\n");
            sb.append("購入日: ").append(dateFormat.format(product.getPurchaseDate())).append("\n");
            sb.append("賞味期限: ").append(dateFormat.format(product.getExpiryDate())).append("\n");
            sb.append("\n");
        }
        textViewProducts.setText(sb.toString());
    }

    private void updateUserList() {
        StringBuilder sb = new StringBuilder();
        sb.append("ユーザー\n");
        for (User user : userManager.getUsers()) {
            sb.append("名前: ").append(user.getName()).append("\n");
            sb.append("学籍番号: ").append(user.getStudentId()).append("\n");
            sb.append("IDm: ").append(user.getIdm()).append("\n");
            sb.append("プリペイド残高: ").append(user.getPrepaidBalance()).append("円\n");
            sb.append("\n");
        }
        textViewUsers.setText(sb.toString());
    }
}
