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
        User user = new User();

        user.setNome(userRequest.getNome());
        user.setUsername(userRequest.getUsername());
        String encodedPassword = encoder.encode(userRequest.getSenha());
        user.setSenha(encodedPassword);

        repository.save(user);
    }

    public User putUser(Long id, User user) {
        return repository.findById(id)
                .map(userExist -> {
                    userExist.setNome(user.getNome());
                    userExist.setUsername(user.getUsername());
                    userExist.setSenha(user.getSenha());
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

    public User login(String username, String senha) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        if (encoder.matches(senha, user.getSenha()))
            return user;
        else
            throw new RuntimeException("Senha inválida.");
    }
}
