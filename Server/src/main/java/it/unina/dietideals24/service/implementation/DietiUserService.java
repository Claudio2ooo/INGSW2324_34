package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.repository.IDietiUserRepository;
import it.unina.dietideals24.service.interfaces.IDietiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mainDietiUserService")
public class DietiUserService implements IDietiUserService {

    @Autowired
    private IDietiUserRepository dietiUserRepository;

    @Override
    public List<DietiUser> getUsers() {
        return dietiUserRepository.findAll();
    }

    @Override
    public DietiUser getUserById(Long dietiUserId) {
        Optional<DietiUser> dietiUserOptional = dietiUserRepository.findById(dietiUserId);
        if(dietiUserOptional.isEmpty()){
            throw new IllegalStateException("Utente con id "+dietiUserId+" non esite");
        }
        return dietiUserOptional.get();
    }

    @Override
    public void addNewDietiUser(DietiUser dietiUser) {
        Optional<DietiUser> dietiUserOptional = dietiUserRepository.findDietiUserByEmail(dietiUser.getEmail());
        if(dietiUserOptional.isPresent()){
            throw new IllegalStateException("email già registrata");
        }
        dietiUserRepository.save(dietiUser);
    }

    @Override
    public void deleteDietiUser(Long dietiUserId) {
        boolean exists = dietiUserRepository.existsById(dietiUserId);
        if(!exists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        dietiUserRepository.deleteById(dietiUserId);
    }

    @Override
    public boolean existsById(Long id) {
        return dietiUserRepository.existsById(id);
    }

    @Override
    public void linkImage(String profilePicDirectory, Long id) {
        DietiUser dietiUser = dietiUserRepository.findById(id).get();
        dietiUser.setProfilePictureUrl(profilePicDirectory+"/"+id);
        dietiUserRepository.save(dietiUser);
    }
}
