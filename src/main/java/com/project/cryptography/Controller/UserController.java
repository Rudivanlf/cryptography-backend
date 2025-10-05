package com.project.cryptography.Controller;

import com.project.cryptography.DTO.LoginRequestDTO;
import com.project.cryptography.DTO.UserDetailResponseDTO;
import com.project.cryptography.DTO.UserRequestDTO;
import com.project.cryptography.Model.User;
import com.project.cryptography.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public void postUser(@RequestBody UserRequestDTO userRequest) {
        service.postUser(userRequest);
    }

    @PutMapping("/{id}")
    public User putUser(@PathVariable Long id, @RequestBody User user) {
        return service.putUser(id, user);
    }

    @GetMapping("/{id}")
    public UserDetailResponseDTO getUser(@PathVariable Long id) {
        User user = service.getUser(id);
        return new UserDetailResponseDTO(user);
    }

    @GetMapping("/all")
    public List<UserDetailResponseDTO> getAllUsers() {
        return service.getAllUsers().stream()
                .map(UserDetailResponseDTO::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            User user = service.login(loginRequest.getUsername(),
                    loginRequest.getSenha());
            return ResponseEntity.ok(new UserDetailResponseDTO(user));
        } catch (RuntimeException e) {
            // Retorna a mensagem no corpo da resposta
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }
}
