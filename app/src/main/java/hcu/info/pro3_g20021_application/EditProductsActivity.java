package hcu.info.pro3_g20021_application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditProductsActivity extends AppCompatActivity {

    private ProductManager productManager;
    private TextView textViewProductList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        productManager = ProductManager.getInstance();

        textViewProductList = findViewById(R.id.text_view_product_list);
        updateProductList();

        Button addProductButton = findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(v -> {
            Product product = new Product("1234567890123", 200, 20, new Date(), new Date());
            productManager.addProduct(product);
            updateProductList();
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
}
