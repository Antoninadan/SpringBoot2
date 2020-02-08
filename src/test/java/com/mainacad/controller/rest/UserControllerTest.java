package com.mainacad.controller.rest;

import com.mainacad.AppRunner;
import com.mainacad.model.User;
import com.mainacad.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "rest"})
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void save() throws URISyntaxException {
        User user = new User();
        RequestEntity<User> request = new RequestEntity<>(user, HttpMethod.PUT, new URI("/user"));

        when(userService.save(any(User.class))).thenReturn(user);
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void update() {
        User user = new User();

        when(userService.update(any(User.class))).thenReturn(user);

        RequestEntity<User> request = new RequestEntity<>(user, HttpMethod.POST, URI.create("/user"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).update(any(User.class));
        System.out.println("request.getType() = " + request.getType());
    }
    @Test
    void updateNull() {
        User user = new User();
        user = null;

        when(userService.update(any(User.class))).thenReturn(null);

        RequestEntity request = new RequestEntity("{}", HttpMethod.POST, URI.create("/user"));

//        HttpHeaders headers = request.getHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("request.getBody() = " + request.getBody());
        System.out.println("request.getHeaders() = " + request.getHeaders());
        System.out.println("request.getType() = " + request.getType());

        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    void getByLoginAndPassword() {
        URI uri = URI.create("/user/auth");
        User user = new User("test_login", "test_password",
                "test_first_name", "test_last_name");

        when(userService.getByLoginAndPassword(anyString(), anyString())).thenReturn(user);

        RequestEntity request = new RequestEntity("{\"login\":\"test_login\",\"password\":\"test_password\"}", HttpMethod.POST, uri);
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getByLoginAndPassword(anyString(), anyString());
    }

    @Test
    void getByLoginAndPasswordNotFound() {
        URI uri = URI.create("/user/auth");

        when(userService.getByLoginAndPassword("ignatenko2207", "123456")).thenReturn(null);

        RequestEntity request = new RequestEntity("{\"login\":\"ignatenko2207\",\"password\":\"123456\"}", HttpMethod.POST, uri);
        ResponseEntity response = testRestTemplate.postForEntity(uri, request, Object.class);

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
        assertTrue(response.getBody() == null);

        verify(userService, times(1))
                .getByLoginAndPassword("ignatenko2207", "123456");
    }

    @Test
    void getUserOne() {
        User user = new User();

        when(userService.getById(anyInt())).thenReturn(user);

        RequestEntity request = new RequestEntity(HttpMethod.GET, URI.create("/user/1515"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getById(anyInt());
    }

    @Test
    void getUserOneNotFound() {
        User user = null;

        when(userService.getById(anyInt())).thenReturn(user);

        RequestEntity request = new RequestEntity(HttpMethod.GET, URI.create("/user/1515"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getById(anyInt());
    }

    @Test
    void getUserMany() {
        User user1 = new User("test_login", "test_password",
                "test_first_name", "test_last_name");
        User user2 = new User("test_login", "test_password",
                "test_first_name", "test_last_name");
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAll()).thenReturn(users);
        RequestEntity request = new RequestEntity(HttpMethod.GET, URI.create("/user"));
        ResponseEntity<List<User>> response = testRestTemplate.exchange(request,
                new ParameterizedTypeReference<List<User>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().get(0) instanceof User);
        verify(userService, times(1)).getAll();
    }

    @Test
    void delete() {
        User user = new User("test_login", "test_password",
                "test_first_name", "test_last_name");

        doNothing().when(userService).delete(any(User.class));

        RequestEntity request = new RequestEntity(user, HttpMethod.DELETE, URI.create("/user"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).delete(any(User.class));
    }

    @Test
    void deleteNotFount() {
        User user = new User("test_login", "test_password",
                "test_first_name", "test_last_name");

        doThrow(new RuntimeException()).when(userService).delete(any(User.class));

        RequestEntity request = new RequestEntity(user, HttpMethod.DELETE, URI.create("/user"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, times(1)).delete(any(User.class));
    }

    @Test
    void deleteById() {
        User user = new User(1, "test_login", "test_password",
                "test_first_name", "test_last_name");

        doNothing().when(userService).deleteById(anyInt());

        RequestEntity request = new RequestEntity(HttpMethod.DELETE, URI.create("/user/1"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteByIdNotFound() {
        User user = new User(1, "test_login", "test_password",
                "test_first_name", "test_last_name");

        doThrow(new RuntimeException()).when(userService).deleteById(anyInt());

        RequestEntity request = new RequestEntity(HttpMethod.DELETE, URI.create("/user/1"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, times(1)).deleteById(anyInt());
    }


}