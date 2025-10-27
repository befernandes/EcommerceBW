package com.ecombw.EcommerceBW.service;

import com.ecombw.EcommerceBW.dto.UserDTO;
import com.ecombw.EcommerceBW.model.User;
import com.ecombw.EcommerceBW.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO userDTO(User  user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    public UserDTO save(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());

        User savedUser = userRepository.save(user);
        return userDTO (savedUser);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::userDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(this::userDTO);
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDTO.getUsername());
                    existingUser.setEmail(userDTO.getEmail());
                    existingUser.setPassword(userDTO.getPassword());
                    existingUser.setRoles(userDTO.getRoles());

                    User savedUser = userRepository.save(existingUser);


                    return userDTO (savedUser);
            }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }
        else  {
            throw new RuntimeException("User not found with id " + id);
        }
    }
}
