package com.adria.dbencryption.controller;

import com.adria.dbencryption.dto.WSUserDetails;
import com.adria.dbencryption.model.UserDetails;
import com.adria.dbencryption.repository.UserDetailsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserDetailsRepository userDetailsRepository;

    public UserController(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDetails> saveUserDetails(@RequestBody WSUserDetails request) {
        UserDetails userDetails = new UserDetails();
        userDetails.setAddress(request.getAddress());
        userDetails.setCity(request.getCity());
        userDetails.setEmailId(request.getEmailId());
        userDetails.setFirstName(request.getFirstName());
        userDetails.setLastName(request.getLastName());
        userDetails.setMobileNumber(request.getMobileNumber());
        return ResponseEntity.ok(userDetailsRepository.save(userDetails));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable(name = "id") String id) {
        Optional<UserDetails> optionalUserDetails = userDetailsRepository.findById(id);
        return ResponseEntity.ok(optionalUserDetails.isPresent() ? optionalUserDetails.get() : null);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {
        userDetailsRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDetails> update(@PathVariable(name = "id") String id) {
        var user = userDetailsRepository.findById(id).get();
        user.setCity("agadir");
        return ResponseEntity.ok(userDetailsRepository.save(user));
    }
}
