package example.tecnica.service;

import example.tecnica.dto.auth.UserRequestRegister;
import example.tecnica.dto.task.TaskResponse;
import example.tecnica.dto.user.UserResponse;
import example.tecnica.entity.User;
import example.tecnica.repository.UserRepository;
import example.tecnica.repository.specs.UserSpecs;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public User register(UserRequestRegister userRequest, String passwordEncoded) {
        User user = new User(userRequest, passwordEncoded);
        return userRepository.save(user);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario"));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserResponse::new)
                .toList();
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario"));
    }

    public UserResponse deleteUser(Long id) {
        User user = this.getUserById(id);
        user.setActive(false);
        userRepository.save(user);
        return new UserResponse(user);
    }

    public List<UserResponse> searchByEmail(String q) {
        Specification<User> specification = Specification.where(UserSpecs.emailLike(q));

        List<User> users = userRepository.findAll(specification);

        return users.stream().map(UserResponse::new).toList();
    }

}
