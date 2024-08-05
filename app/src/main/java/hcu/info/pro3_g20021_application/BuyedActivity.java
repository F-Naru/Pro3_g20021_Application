package hcu.info.pro3_g20021_application;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BuyedActivity extends AppCompatActivity {

    private UserManager userManager;
    private ProductManager productManager;
    private TextView textView;
    private String janCode;
    private String IDm;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buyed);

        userManager = UserManager.getInstance(this);
        productManager = ProductManager.getInstance(this);
        textView = findViewById(R.id.textView);
        IDm = getIntent().getStringExtra("IDm");
        janCode = getIntent().getStringExtra("janCode");

        // 商品チェック
        Product product = productManager.getProduct(janCode);
        if (product == null) {
            Toast.makeText(this, "未登録商品です", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // ユーザーチェック
        User user = userManager.getUser(IDm);
        if (user == null) {
            Toast.makeText(this, "未登録ユーザです", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 購入処理
        product.setStock(product.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        // ログ情報を表示するテキストを作成
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("購入が完了しました\n\n");
        logBuilder.append("購入商品: \n");
        logBuilder.append("商品名: ").append(product.getName()).append("\n");
        logBuilder.append("価格: ").append(product.getPrice()).append("\n");
        logBuilder.append("残在庫数: ").append(product.getStock()).append("\n");
        logBuilder.append("仕入れ日: ").append(dateFormat.format(product.getPurchaseDate())).append("\n");
        logBuilder.append("賞味期限: ").append(dateFormat.format(product.getExpiryDate())).append("\n\n");

        logBuilder.append("購入ユーザ: \n");
        logBuilder.append("名前: ").append(user.getName()).append("\n");
        logBuilder.append("学籍番号: ").append(user.getStudentId()).append("\n");
        logBuilder.append("残高: ").append(user.getBalance()).append("\n");

        // テキストビューにログ情報を設定
        textView.setText(logBuilder.toString());

        // 購入情報をログファイルに記録
        logPurchaseInfo(janCode, IDm);
    }

    private void logPurchaseInfo(String janCode, String IDm) {
        String logFileName = "purchase_log.csv";
        File logFile = new File(getFilesDir(), logFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            String timestamp = logDateFormat.format(new Date());
            writer.write(String.format("%s,%s,%s\n", timestamp, janCode, IDm));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "ログファイルの記録に失敗しました", Toast.LENGTH_SHORT).show();
        }
    }
}
