package it.unina.dietideals24.service;

import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.repository.DietiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DietiUserService {

    private final DietiUserRepository dietiUserRepository;

    @Autowired
    public DietiUserService(DietiUserRepository dietiUserRepository){
        this.dietiUserRepository = dietiUserRepository;
    }

    public List<DietiUser> getUsers() {
        return dietiUserRepository.findAll();
    }

    public void addNewDietiUser(DietiUser dietiUser) {
        Optional<DietiUser> dietiUserOptional = dietiUserRepository.findDietiUserByEmail(dietiUser.getEmail());
        if(dietiUserOptional.isPresent()){
            throw new IllegalStateException("email già registrata");
        }
        dietiUserRepository.save(dietiUser);
    }

    public void deleteDietiUser(String email) {
        boolean exists = dietiUserRepository.existsById(email);
        if(!exists){
            throw new IllegalStateException("Utente con email "+email+" non esiste");
        }
        dietiUserRepository.deleteById(email);
    }
}
