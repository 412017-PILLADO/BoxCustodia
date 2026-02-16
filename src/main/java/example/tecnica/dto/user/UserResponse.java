package example.tecnica.dto.user;

import example.tecnica.entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private Boolean active;

    private LocalDate createdAt;

    public UserResponse(User r) {
        this.active = r.getActive();
        this.createdAt = r.getCreatedAt();
        this.email = r.getEmail();
        this.id = r.getId();
        this.lastName = r.getLastName();
        this.name = r.getName();
    }
}
