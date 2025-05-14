package org.yasmani.io.apprestwithtoken.controller;

import org.yasmani.io.apprestwithtoken.dto.AuthenticationRequest;
import org.yasmani.io.apprestwithtoken.dto.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yasmani.io.apprestwithtoken.exception.AuthenticationException;
import org.yasmani.io.apprestwithtoken.utils.JwtToken;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final UserDetailsService userDetailsService;

    public LoginController(AuthenticationManager authenticationManager, JwtToken jwtToken, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
        this.userDetailsService = userDetailsService;
    }
    @PostMapping
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            logger.info("Invalid username {}",e.getMessage());
            throw new AuthenticationException("Authentication Invalid");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtToken.createToken(userDetails);
        logger.info("Created token {}", token);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
