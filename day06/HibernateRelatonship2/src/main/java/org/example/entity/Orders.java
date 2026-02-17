package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private String productName;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)  // for bi-directional
    @JoinColumn(name = "customer_id")     // for adding person_id as foregin key
    private Customer customer;

    public Orders() {}

    public Orders(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
