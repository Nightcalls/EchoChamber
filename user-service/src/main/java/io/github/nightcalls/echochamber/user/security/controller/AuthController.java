package io.github.nightcalls.echochamber.user.security.controller;

import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.repository.UserRepository;
import io.github.nightcalls.echochamber.user.security.jwt.JwtUtils;
import io.github.nightcalls.echochamber.user.security.payload.request.LoginRequest;
import io.github.nightcalls.echochamber.user.security.payload.request.SignupRequest;
import io.github.nightcalls.echochamber.user.security.payload.response.JwtResponse;
import io.github.nightcalls.echochamber.user.security.services.UserDetailsImpl;
import io.github.nightcalls.echochamber.user.security.services.UserDetailsServiceImpl;
import io.github.nightcalls.echochamber.user.service.UserSearchService;
import io.github.nightcalls.echochamber.user.service.create.UserCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSearchService userSearchService;

    @Autowired
    UserCreateService userCreateService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UserDetailsImpl userDetails =
                (UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), null));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userSearchService.getUserByName(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        User user = new User.Builder().name(signUpRequest.getUsername()).lastLoginInfo("127.0.0.1").build();

        userCreateService.createUser(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
