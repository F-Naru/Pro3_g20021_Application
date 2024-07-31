package hcu.info.pro3_g20021_application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductManager {
    private List<Product> products;

    public ProductManager() {
        products = new ArrayList<>();
        // デバッグ用にいくつかの商品を追加
        addDebugProducts();
    }

    private void addDebugProducts() {
        products.add(new Product("1234567890123", 100, 10, new Date(), new Date()));
        products.add(new Product("9876543210987", 200, 5, new Date(), new Date()));
        products.add(new Product("5678901234567", 150, 20, new Date(), new Date()));
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product findProductByJanCode(String janCode) {
        for (Product product : products) {
            if (product.getJanCode().equals(janCode)) {
                return product;
            }
        }
        return null;
    }
}
