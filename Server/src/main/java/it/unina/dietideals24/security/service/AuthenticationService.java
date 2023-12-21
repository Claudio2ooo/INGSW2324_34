package it.unina.dietideals24.security.service;

import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.repository.IDietiUserRepository;
import it.unina.dietideals24.security.dto.LoginDTO;
import it.unina.dietideals24.security.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private IDietiUserRepository dietiUserRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(IDietiUserRepository dietiUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.dietiUserRepository = dietiUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public DietiUser register(RegisterDTO registerDTO){
        DietiUser dietiUser = new DietiUser();
        dietiUser.setEmail(registerDTO.getEmail());
        dietiUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        dietiUser.setName(registerDTO.getName());
        dietiUser.setSurname(registerDTO.getSurname());

        return dietiUserRepository.save(dietiUser);
    }

    public DietiUser login(LoginDTO loginDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        return dietiUserRepository.findDietiUserByEmail(loginDTO.getEmail()).orElseThrow();
    }
}
