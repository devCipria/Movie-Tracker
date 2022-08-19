package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
   User findByUsername(String username);
   User findById(int Id);

    void deleteUserByUsername(String userName);

    @Query(
            nativeQuery = true,
            value = "select max(id) from users"
    )
    int findMaxId();
}