package example.tecnica.dto.auth;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseLogin {

    private String token;

}
