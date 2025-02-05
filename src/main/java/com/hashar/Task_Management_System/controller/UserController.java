package com.hashar.Task_Management_System.controller;

import com.hashar.Task_Management_System.dto.LoginResponseDTO;
import com.hashar.Task_Management_System.dto.MemberDTO;
import com.hashar.Task_Management_System.dto.MemberLoginDTO;
import com.hashar.Task_Management_System.dto.RolesDTO;
import com.hashar.Task_Management_System.model.Permissions;
import com.hashar.Task_Management_System.model.Roles;
import com.hashar.Task_Management_System.services.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Authentication APis", description = "APIs related to user authentication.")
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
        return new ResponseEntity<>(memberService.verify(memberLoginDTO),HttpStatus.OK);
    }
    @GetMapping("/details")
    public ResponseEntity<MemberDTO> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        MemberDTO memberDTO = memberService.getUserDetailsByUsername(username);
        if (memberDTO != null) {
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        System.out.println("working........");
        return new ResponseEntity<>("working....", HttpStatus.OK);
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly(){
        return new ResponseEntity<>("This is admin only page", HttpStatus.OK);
    }
    @PostMapping("/refresh_token")
    public ResponseEntity<LoginResponseDTO> getAccessTokenByRefreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return new ResponseEntity<>(memberService.loginByRefreshToken(request, response),HttpStatus.OK);
    }
    @PostMapping("/permissions")
    public ResponseEntity<Permissions> addPermission(@RequestBody Permissions permissions){
        return new ResponseEntity<>(memberService.addPermission(permissions),HttpStatus.OK);
//        return new ResponseEntity<>("Permission granted", HttpStatus.OK);
    }

    @GetMapping("/permissions")
    public ResponseEntity<Iterable<Permissions>> getPermissions(){
        return new ResponseEntity<>(memberService.getPermissions(),HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<Roles> addRole(@RequestBody RolesDTO rolesDTO){
        return new ResponseEntity<>(memberService.addRoleWithPermissions(rolesDTO),HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Iterable<Roles>> getRoles(){
        return new ResponseEntity<>(memberService.getRoles(),HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<String> view(){
        return new ResponseEntity<>("Viewing", HttpStatus.OK);
    }

    @GetMapping("/edit")
    public ResponseEntity<String> edit(){
        return new ResponseEntity<>("Editing", HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity<String> delete(){
        return new ResponseEntity<>("Deleting", HttpStatus.OK);
    }
}
