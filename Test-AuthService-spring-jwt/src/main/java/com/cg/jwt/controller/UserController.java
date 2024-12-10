package com.cg.jwt.controller;

import com.cg.dto.AuthRequest;

import com.cg.jwt.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @GetMapping("/welcome")
//    public String welcome() {
//        return "Welcome this endpoint is not secure";
//    }
//

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    
    @GetMapping(value = "/validate")
	public boolean getValidation(@RequestHeader("Authorization") String token){
		token = token.substring(7);
		com.cg.dto.AuthResponse auth = new com.cg.dto.AuthResponse();
	
		//log.info("Token validation for "+jwtUtil.extractUsername(token));
		
		if(jwtUtil.validateToken(token)) {
			
			System.out.println("Token validated");
			return true;
		}
		else {
			System.out.println("Token NOT validated");
			return false;

	}
    
    }
}
