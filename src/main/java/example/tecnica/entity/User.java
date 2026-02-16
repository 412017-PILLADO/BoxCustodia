package example.tecnica.entity;

import example.tecnica.dto.auth.UserRequestRegister;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private LocalDate createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedTo")
    private List<Task> tasks = new ArrayList<>();

    public User(UserRequestRegister userRequest, String encodedPassword) {
        this.active = true;
        this.name = userRequest.getName();
        this.lastName = userRequest.getLastName();
        this.email = userRequest.getEmail();
        this.password = encodedPassword;
        this.createdAt = LocalDate.now();
    }
}
