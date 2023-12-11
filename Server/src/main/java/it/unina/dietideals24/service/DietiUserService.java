package it.unina.dietideals24.service;

import it.unina.dietideals24.api.model.DietiUser;
import it.unina.dietideals24.api.repository.DietiUserRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        System.out.println(dietiUser);
    }
}
