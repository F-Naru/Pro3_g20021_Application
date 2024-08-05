package hcu.info.pro3_g20021_application;

import java.util.Date;

public class Product {
    private String janCode;
    private String name;
    private int price;
    private int stock;
    private Date purchaseDate;
    private Date expiryDate;

    public Product(String janCode, String name, int price, int stock, Date purchaseDate, Date expiryDate) {
        this.janCode = janCode;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
    }
    public String getJanCode() { return janCode; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public Date getPurchaseDate() { return purchaseDate; }
    public int getStock() { return stock; }
    public Date getExpiryDate() { return expiryDate; }

    public void setJanCode(String janCode) { this.janCode = janCode; }
    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }
    public void setStock(int stock) { this.stock = stock; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}
