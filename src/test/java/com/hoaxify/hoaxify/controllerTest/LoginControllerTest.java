package com.hoaxify.hoaxify.controllerTest;

import com.hoaxify.hoaxify.respository.UserRepository;
import com.hoaxify.hoaxify.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginControllerTest {

    private static final String API_1_0_LOGIN = "/api/1.0/login";

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Autowired
    TestRestTemplate testRestTemplate;

    public <T> ResponseEntity<T> login(Class<T> responseType) {
        return testRestTemplate.postForEntity(API_1_0_LOGIN, null, responseType);
    }

    @Test
    public void postLogin_withIncorrectCredentials_receivedunauthorized() {
        authenticate();
        ResponseEntity<Object> responseEntity = login(Object.class);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
    }

    public void authenticate() {
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthenticationInterceptor("test-user", "password"));
    }

    @Test
    public void postLogin_withoutUserCredentials_receivedunauthorized() {
        ResponseEntity<Object> responseEntity = login(Object.class);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
    }



}
