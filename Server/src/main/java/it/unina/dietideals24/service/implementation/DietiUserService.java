package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.repository.IDietiUserRepository;
import it.unina.dietideals24.service.interfaces.IDietiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mainDietiUserService")
public class DietiUserService implements IDietiUserService {

    @Autowired
    private IDietiUserRepository IDietiUserRepository;

    @Override
    public List<DietiUser> getUsers() {
        return IDietiUserRepository.findAll();
    }

    @Override
    public DietiUser getUserById(Long dietiUserId) {
        Optional<DietiUser> dietiUserOptional = IDietiUserRepository.findById(dietiUserId);
        if(dietiUserOptional.isEmpty()){
            throw new IllegalStateException("Utente con id "+" non esite");
        }
        return dietiUserOptional.get();
    }

    @Override
    public void addNewDietiUser(DietiUser dietiUser) {
        Optional<DietiUser> dietiUserOptional = IDietiUserRepository.findDietiUserByEmail(dietiUser.getEmail());
        if(dietiUserOptional.isPresent()){
            throw new IllegalStateException("email già registrata");
        }
        IDietiUserRepository.save(dietiUser);
    }

    @Override
    public void deleteDietiUser(Long dietiUserId) {
        boolean exists = IDietiUserRepository.existsById(dietiUserId);
        if(!exists){
            throw new IllegalStateException("Utente con id "+dietiUserId+" non esiste");
        }
        IDietiUserRepository.deleteById(dietiUserId);
    }
}
