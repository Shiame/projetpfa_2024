package com.example.autocar.Controllers;

import com.example.autocar.Repository.ClientRepo;
import com.example.autocar.Repository.UserRepo;
import com.example.autocar.models.Client;
import com.example.autocar.models.User;
import com.example.autocar.payload.request.SignInRequest;
import com.example.autocar.payload.request.SignUpRequest;
import com.example.autocar.payload.response.AuthenticationResponse;
import com.example.autocar.payload.response.MessageResponse;
import com.example.autocar.security.Jwt.JwtUtils;
import com.example.autocar.security.services.UserDetailsImpl;
import com.example.autocar.security.services.UserDetailsServiceImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepository;


    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtUtils.generateJwtToken(userDetails);


        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(
                token,
                userDetails.getId(),
                userDetails.getEmail(),
                roles
        ));
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setMotDePasse(encoder.encode(signUpRequest.getPassword()));
        user.setRole("CLIENT");
        Client client =new Client();
        userRepository.save(user);
        client.setUser(user);


        clientRepo.save(client);

        return ResponseEntity.ok(new MessageResponse("User Registered Successfully!"));
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've Been Signed Out!"));
    }


}
