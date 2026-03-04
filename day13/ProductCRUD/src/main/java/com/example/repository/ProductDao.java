package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {
    private List<Product> productList = new ArrayList<>();
    private Long idCounter = 1L;

    public List<Product> findAll(){
        return productList;
    }

    public  void save(Product product){
        if(product.getId() == null){
            product.setId(idCounter++);
            productList.add(product);
        }
        else{
            update(product);
        }
    }

    public Optional<Product> findBy(Long id){
        return productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Long id){
        productList.removeIf(p -> p.getId().equals(id));
    }

    public void update(Product updatedProduct){
        for(int i = 0 ; i < productList.size(); i++){
            if(productList.get(i).getId().equals(updatedProduct.getId())){
                productList.set(i, updatedProduct);
                break;
            }
        }
    }
//
//    public List<Product> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Product> productList) {
//        this.productList = productList;
//    }
}
