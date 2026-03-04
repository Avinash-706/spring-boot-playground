package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public void saveProduct(Product product){
        productDao.save(product);
        System.out.println("Product Saved !!");
    }

    public Optional<Product> findById(Long id){
        return productDao.findBy(id);
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public void delete(Long id){
        productDao.deleteById(id);
        System.out.println("Product Deleted with ID: " + id);
    }

}
