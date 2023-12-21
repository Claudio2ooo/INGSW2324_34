package it.unina.dietideals24.security.controller;

import it.unina.dietideals24.security.dto.AuthResponseDTO;
import it.unina.dietideals24.security.dto.LoginDTO;
import it.unina.dietideals24.security.dto.RegisterDTO;
import it.unina.dietideals24.repository.IDietiUserRepository;
import it.unina.dietideals24.security.service.JwtService;
import it.unina.dietideals24.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final IDietiUserRepository dietiUserRepository;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthenticationService authenticationService,
                          AuthenticationManager authenticationManager,
                          IDietiUserRepository dietiUserRepository,
                          JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.dietiUserRepository = dietiUserRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        if(dietiUserRepository.existsByEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("email already registered", HttpStatus.BAD_REQUEST);
        }

        authenticationService.register(registerDTO);
        return new ResponseEntity<>("DietiUser registered!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

}
