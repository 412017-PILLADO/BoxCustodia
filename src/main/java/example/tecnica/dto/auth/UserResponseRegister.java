package example.tecnica.dto.auth;

import example.tecnica.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseRegister {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private LocalDate createdAt;

    public UserResponseRegister(User userSaved) {
        this.id = userSaved.getId();
        this.name = userSaved.getName();
        this.lastName = userSaved.getLastName();
        this.email = userSaved.getEmail();
        this.createdAt = userSaved.getCreatedAt();
    }
}
