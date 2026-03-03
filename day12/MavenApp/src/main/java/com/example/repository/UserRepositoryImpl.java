package com.example.repository;

import com.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private List<User> users = new ArrayList<>();

    public UserRepositoryImpl(){
        users.add(new User(1L, "Avinash Dhanuka", "john@example.com"));
        users.add(new User(2L, "Kshitiz Kumar", "kumar@example.com"));
    }

    @Override
    public List<User> findAll(){
        return this.users;
    }

    @Override
    public User findById(Long id){
        return this.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(User user){
        this.users.add(user);
    }
}
