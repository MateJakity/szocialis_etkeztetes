package hu.szocialis_etkeztetes.szocialis_etkeztetes.model;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String productName;
    private double amount;
    private String amountType;
    private String type;
    private int price;
    private String orderStatus;
    private String warehouseStatus;

    public Product() {
    }

    public Product(String productName, double amount, String amountType, String type, int price, String orderStatus, String warehouseStatus) {
        this.productName = productName;
        this.amount = amount;
        this.amountType = amountType;
        this.type = type;
        this.price = price;
        this.orderStatus = orderStatus;
        this.warehouseStatus = warehouseStatus;
    }

    public long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public int setPrice(int price) {
        this.price = price;
        return price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getWarehouseStatus() {
        return warehouseStatus;
    }

    public void setWarehouseStatus(String warehouseStatus) {
        this.warehouseStatus = warehouseStatus;
    }
}
