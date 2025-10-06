package com.project.cryptography.Service;

import com.project.cryptography.DTO.UserRequestDTO;
import com.project.cryptography.Model.User;
import com.project.cryptography.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void postUser (UserRequestDTO userRequest) {
        if (repository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username '" + userRequest.getUsername() + "' já está em uso.");
        }

        User newUser = new User();
        newUser.setName(userRequest.getName());
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(encoder.encode(userRequest.getPassword()));
        repository.save(newUser);
    }

    public User putUser(Long id, User user) {
        return repository.findById(id)
                .map(userExist -> {
                    userExist.setName(user.getName());
                    userExist.setUsername(user.getUsername());
                    userExist.setPassword(user.getPassword());
                    return repository.save(userExist);
                })
                .orElse(null);

    }

    public User getUser(Long id) {
        return repository.findById(id).orElse(null);

    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public User login(String username, String password) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        if (encoder.matches(password, user.getPassword()))
            return user;
        else
            throw new RuntimeException("Senha inválida.");
    }
}
