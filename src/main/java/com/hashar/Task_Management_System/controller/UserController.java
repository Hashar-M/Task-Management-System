package com.hashar.Task_Management_System.controller;

import com.hashar.Task_Management_System.dto.MemberDTO;
import com.hashar.Task_Management_System.dto.MemberLoginDTO;
import com.hashar.Task_Management_System.services.MemberService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> register(@Valid @RequestBody MemberDTO memberDto){
        // Check if the password is compromised using the injected checker
        if (compromisedPasswordChecker.check(memberDto.getPassword()).isCompromised()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The password you entered has been compromised. Please choose a different password.");
        }
        try{
            return new ResponseEntity<>(memberService.register(memberDto),HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody MemberLoginDTO memberLoginDTO) {
        return memberService.verify(memberLoginDTO);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        System.out.println("workinggg........");
        return new ResponseEntity<>("working....", HttpStatus.OK);
    }
}
