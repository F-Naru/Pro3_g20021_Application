package hcu.info.pro3_g20021_application;

import java.util.Date;

public class Product {
    private String janCode;
    private int price;
    private int stock;
    private Date purchaseDate;
    private Date expiryDate;

    public Product(String janCode, int price, int stock, Date purchaseDate, Date expiryDate) {
        this.janCode = janCode;
        this.price = price;
        this.stock = stock;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
    }
    public String getJanCode() { return janCode; }
    public void setJanCode(String janCode) { this.janCode = janCode; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public Date getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }
    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}
