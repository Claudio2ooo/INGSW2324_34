package it.unina.dietideals24.controller;

import it.unina.dietideals24.model.DietiUser;
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

    @PostMapping
    public void registerNewDietiUser(@RequestBody DietiUser dietiUser){
        dietiUserService.addNewDietiUser(dietiUser);
    }

    @DeleteMapping(path = "{dietiUserEmail}")
    public void deleteDietiUser(@PathVariable("dietiUserId") Long id){
        dietiUserService.deleteDietiUser(id);
    }
}
