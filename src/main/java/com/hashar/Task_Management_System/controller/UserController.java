package com.hashar.Task_Management_System.controller;

import com.hashar.Task_Management_System.dto.LoginResponseDTO;
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
        return new ResponseEntity<>(memberService.register(memberDto),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody MemberLoginDTO memberLoginDTO) {
        return  new ResponseEntity<>(memberService.verify(memberLoginDTO),HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        System.out.println("workinggg........");
        return new ResponseEntity<>("working....", HttpStatus.OK);
    }
}
