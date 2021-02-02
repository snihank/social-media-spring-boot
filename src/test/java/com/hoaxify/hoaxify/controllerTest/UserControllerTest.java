package com.hoaxify.hoaxify.controllerTest;

import com.hoaxify.hoaxify.respository.UserRepository;
import com.hoaxify.hoaxify.service.UserService;
import com.hoaxify.hoaxify.user.GenericResponse;
import com.hoaxify.hoaxify.user.User;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    private static final String API_1_0_USERS = "/api/1.0/users";

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Autowired
    TestRestTemplate testRestTemplate;

    @Before
    public void cleanUp(){
        userRepository.deleteAll();
    }

    private User createValidUser(){
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("test-password");
        return user;
    }

    @Test
    public void postUser_whenUserIsValid_receiveOk(){
        User user = createValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity("/api/1.0/users", user, Object.class);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));

    }

    @Test
    public void postUser_whenUserIsValid_receiveSuccessMessage(){
        User user = createValidUser();
        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity("/api/1.0/users", user, GenericResponse.class);
        assertFalse(response.getBody().getMessage().isEmpty());

    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase(){
        User user = createValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);

        assertEquals(userRepository.count(), 1);
    }

    @Test
    public void postUser_whenUserIsValid_isPasswordHashedinDatabase(){
        User user = createValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        List<User> users = userRepository.findAll();
        User userInDB = users.get(0);
        assertTrue(userInDB.getPassword() != user.getPassword());
    }


}
