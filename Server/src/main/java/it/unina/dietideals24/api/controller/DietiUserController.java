package it.unina.dietideals24.api.controller;

import it.unina.dietideals24.api.model.DietiUser;
import it.unina.dietideals24.service.DietiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class DietiUserController {

    private final DietiUserService dietiUserService;

    @Autowired
    public DietiUserController(DietiUserService dietiUserService){
        this.dietiUserService = dietiUserService;
    }

    @GetMapping
    public List<DietiUser> getUsers(){
        return dietiUserService.getUsers();
    }

    @PostMapping(value="/createDietiUser", consumes = "application/json")
    public void registerNewDietiUser(@RequestBody DietiUser dietiUser){
        dietiUserService.addNewDietiUser(dietiUser);
    }
}
