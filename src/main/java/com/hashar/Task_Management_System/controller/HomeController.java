package com.hashar.Task_Management_System.controller;

import com.hashar.Task_Management_System.Constants.Roles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){

        return new ResponseEntity<>("Hello welcome to Task Management System"+ Roles.USER.name()+"   "+Roles.USER, HttpStatus.OK);
    }
}
