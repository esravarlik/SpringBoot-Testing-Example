package com.jojo.springboottestingexample.repository;

import com.jojo.springboottestingexample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value= "SELECT * FROM users WHERE value LIKE (%:value%)" ,nativeQuery = true)
    List<User> findByValue(@Param("value") String value);

    List<User> findByValueLikeIgnoreCase(String value);


}
