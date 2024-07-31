package hcu.info.pro3_g20021_application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductManager {

    private static ProductManager instance;
    private List<Product> products;

    private ProductManager() {
        products = new ArrayList<>();
        // デバッグ用にいくつかの商品を追加
        products.add(new Product("1234567890123", 200, 20, new Date(), new Date()));
        products.add(new Product("9876543210987", 300, 15, new Date(), new Date()));
    }

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
