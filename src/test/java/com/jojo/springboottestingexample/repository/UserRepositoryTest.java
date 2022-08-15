package com.jojo.springboottestingexample.repository;

import com.jojo.springboottestingexample.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("JPA - Test demos like native query")
    void should_test_like_native_query(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setValue("someValue 123");

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setValue("someValue 123");

        userRepository.saveAll(List.of(user, user2));

        List<User> userList = userRepository.findByValue("Value");

        assertEquals(2, userList.size());
        assertEquals("someValue 123",userList.get(0).getValue());
    }


}
