package com.hashar.Task_Management_System.controller;

import com.hashar.Task_Management_System.Constants.Roles;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Tag(name = "home", description = "Home page")
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){

        return new ResponseEntity<>("Hello welcome to Task Management System "+ Roles.USER.name()+"   "+Roles.USER, HttpStatus.OK);
    }
}
