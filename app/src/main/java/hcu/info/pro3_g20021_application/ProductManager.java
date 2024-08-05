package hcu.info.pro3_g20021_application;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductManager {

    private static ProductManager instance;
    private List<Product> products;
    private static final String FILE_NAME = "products.txt";
    private Context context;

    private ProductManager(Context context) {
        this.context = context;
        products = new ArrayList<>();
        loadProductsFromFile();
        // デバッグ用にいくつかの商品を追加
        if (products.isEmpty()) {
            products.add(new Product("1234567890123", 200, 20, new Date(), new Date()));
            products.add(new Product("9876543210987", 300, 15, new Date(), new Date()));
        }
    }

    public static ProductManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProductManager(context);
        }
        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
        saveProductsToFile();
    }

    private void loadProductsFromFile() {
        File file = new File(context.getFilesDir(), FILE_NAME);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String janCode = parts[0];
                    int price = Integer.parseInt(parts[1]);
                    int stock = Integer.parseInt(parts[2]);
                    Date purchaseDate = new Date(Long.parseLong(parts[3]));
                    Date expiryDate = new Date(Long.parseLong(parts[4]));
                    products.add(new Product(janCode, price, stock, purchaseDate, expiryDate));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading products from file: " + e.getMessage());
        }
    }

    private void saveProductsToFile() {
        File file = new File(context.getFilesDir(), FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Product product : products) {
                writer.write(String.format("%s,%d,%d,%d,%d\n",
                        product.getJanCode(),
                        product.getPrice(),
                        product.getStock(),
                        product.getPurchaseDate().getTime(),
                        product.getExpiryDate().getTime()));
            }
        } catch (IOException e) {
            System.err.println("Error saving products to file: " + e.getMessage());
        }
    }
}
