package hcu.info.pro3_g20021_application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditProductsActivity extends AppCompatActivity {

    private ProductManager productManager;
    private TextView textViewProductList;
    private EditText editTextJanCode;
    private EditText editTextPrice;
    private EditText editTextStock;
    private EditText editTextPurchaseDate;
    private EditText editTextExpiryDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        productManager = ProductManager.getInstance(this);

        textViewProductList = findViewById(R.id.text_view_product_list);
        editTextJanCode = findViewById(R.id.edit_text_jan_code);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextStock = findViewById(R.id.edit_text_stock);
        editTextPurchaseDate = findViewById(R.id.edit_text_purchase_date);
        editTextExpiryDate = findViewById(R.id.edit_text_expiry_date);

        String janCode = getIntent().getStringExtra("janCode");
        editTextJanCode.setText("JANコード: " + janCode);
        editTextJanCode.setEnabled(false); // 編集不可

        updateProductList();

        Button addProductButton = findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(v -> {
            String price = editTextPrice.getText().toString();
            String stock = editTextStock.getText().toString();
            Date purchaseDate = parseDate(editTextPurchaseDate.getText().toString());
            Date expiryDate = parseDate(editTextExpiryDate.getText().toString());

            if (!price.isEmpty() && !stock.isEmpty() && purchaseDate != null && expiryDate != null) {
                Product product = new Product(janCode, Integer.parseInt(price), Integer.parseInt(stock), purchaseDate, expiryDate);
                if(productManager.addProduct(product) == -1) {
                    //IDm重複
                    Toast.makeText(this, "商品を編集しました", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                };
                updateProductList();
                Toast.makeText(this, "商品を登録しました", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "情報が不足しています", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProductList() {
        StringBuilder sb = new StringBuilder();
        for (Product product : productManager.getProducts()) {
            sb.append("JANコード: ").append(product.getJanCode()).append("\n");
            sb.append("価格: ").append(product.getPrice()).append("円\n");
            sb.append("在庫数: ").append(product.getStock()).append("\n");
            sb.append("購入日: ").append(dateFormat.format(product.getPurchaseDate())).append("\n");
            sb.append("賞味期限: ").append(dateFormat.format(product.getExpiryDate())).append("\n");
            sb.append("\n");
        }
        textViewProductList.setText(sb.toString());
    }

    private Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
