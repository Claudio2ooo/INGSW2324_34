package it.unina.dietideals24.controller;

import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.service.interfaces.IDietiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class DietiUserController {

    @Qualifier("mainDietiUserService")
    private final IDietiUserService dietiUserService;

    @Autowired
    public DietiUserController(IDietiUserService dietiUserService){
        this.dietiUserService = dietiUserService;
    }

    @GetMapping
    public List<DietiUser> getUsers() {
        return dietiUserService.getUsers();
    }

    @DeleteMapping("{id}")
    public void deleteDietiUser(@PathVariable("id") Long id) {
        dietiUserService.deleteDietiUser(id);
    }
}
