package com.jojo.springboottestingexample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojo.springboottestingexample.model.User;
import com.jojo.springboottestingexample.request.UserRequest;
import com.jojo.springboottestingexample.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("IT - Should return OK and a demo json")
    public void get_user_by_id_should_return_OK_and_a_user_json() throws Exception{
        String id = "someId";
        String value = "someValue";

        User user = new User();
        user.setId(id);
        user.setValue(value);

        Mockito.when(userService.getUser(id)).thenReturn(user);

        this.mockMvc.perform(
                get("/user/" + id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.value").value(value));

    }

    @Test
    @DisplayName("IT - Should return CREATED and a demo json")
    public void create_user_should_return_CREATED_and_a_user_json() throws Exception{
        String id = "someId";
        String value = "someValue";

        UserRequest user = new UserRequest();
        user.setValue(value);

        User result = new User();
        result.setId(id);
        result.setValue(value);

        Mockito.when(userService.createUser(value)).thenReturn(result);

        this.mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.value").value(value));

        verify(userService).createUser(value);

    }

    @Test
    @DisplayName("IT - Should return OK and a list of demo json")
    public void get_user_by_id_should_return_OK_and_a_list_of_user_json() throws Exception{
        String valueQuery = "someValue";

        String id = "someId";
        String id2 = "someId 2";
        String value = "somValue 123";
        String value2 = "someValue 1234";

        User user = new User();
        user.setId(id);
        user.setValue(value);

        User user2 = new User();
        user2.setId(id2);
        user2.setValue(value2);

        Mockito.when(userService.getUsers(valueQuery)).thenReturn(List.of(user, user2));

        this.mockMvc.perform(
                        get("/user?value=" + valueQuery)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].value").value(value))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].value").value(value2));
    }

}
