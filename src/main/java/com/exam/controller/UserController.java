package com.exam.controller;
import com.exam.Model.User;
import com.exam.Model.Role;
import com.exam.Model.UserRole;
import com.exam.helper.UserFoundException;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {

        user.setProfile("default.png");
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));


        Set<UserRole> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("Normal");

        UserRole userRole  = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        roles.add(userRole);

     return this.userService.createUser(user, roles);
    }
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username)
    {
        return this.userService.getUser(username);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId)
    {
        this.userService.deleteUser(userId);
    }


@ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionhandler(UserFoundException ex){
    String errorMessage = "User already exists: " + ex.getMessage();

    // You can choose an appropriate HTTP status code
    // For example, HttpStatus.BAD_REQUEST or HttpStatus.CONFLICT
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
}
}
