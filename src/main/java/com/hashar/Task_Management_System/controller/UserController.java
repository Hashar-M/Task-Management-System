package com.hashar.Task_Management_System.controller;

import com.hashar.Task_Management_System.model.Member;
import com.hashar.Task_Management_System.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CompromisedPasswordChecker compromisedPasswordChecker;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member member){
        // Check if the password is compromised using the injected checker
        if (compromisedPasswordChecker.check(member.getPassword()).isCompromised()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The password you entered has been compromised. Please choose a different password.");
        }
        System.out.println(member);
//        return memberService.register(member);
        return new ResponseEntity<>(memberService.register(member),HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody Member member) {
        return memberService.verify(member);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        System.out.println("workinggg........");
        return new ResponseEntity<>("working....", HttpStatus.OK);
    }
}
