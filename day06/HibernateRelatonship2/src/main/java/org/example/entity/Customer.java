package org.example.entity;

import jakarta.persistence.*;
import java.util.List;
import org.example.entity.Orders;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    private String name;
    private String email;
    private int age;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer_id") // This creates FK inside orders table
    private List<Orders> orders;

    public Customer() {}

    public Customer(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }


    // ---------- GETTERS ----------

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    // ---------- SETTERS ----------

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    // Helper Methods (only for Bi-directional)

    public void addOrder(Orders order) {
        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Orders order) {
        orders.remove(order);
        order.setCustomer(null);
    }
}
