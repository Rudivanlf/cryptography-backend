package com.project.cryptography.Service;

import com.project.cryptography.DTO.UserRequestDTO;
import com.project.cryptography.Model.User;
import com.project.cryptography.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void postUser (UserRequestDTO userRequest) {
        User user = new User();

        user.setNome(userRequest.getNome());
        user.setUsername(userRequest.getUsername());
        user.setSenha(userRequest.getSenha());

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
        if (user.getSenha().equals(senha))
            return user;
        else
            throw new RuntimeException("Senha inválida.");
    }
}
