package com.codesunny.app.crudapi;

import com.codesunny.app.crudapi.Models.User;
import com.codesunny.app.crudapi.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private final UserRepo userRepo;

    public ApiController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping(value = "/")
    public String getPage(){
        return "Welcome";
    }

    @GetMapping(value = "/users")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        return userRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        return userRepo.findById(id)
                .map(existing -> {
                    existing.setFirstName(user.getFirstName());
                    existing.setLastName(user.getLastName());
                    existing.setOccupation(user.getOccupation());
                    existing.setAge(user.getAge());
                    User updated = userRepo.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        if (!userRepo.existsById(id)) {
            return ResponseEntity.ok("No Id found");
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
