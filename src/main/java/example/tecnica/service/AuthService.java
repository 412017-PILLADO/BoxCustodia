package example.tecnica.service;

import example.tecnica.config.jwt.JwtService;
import example.tecnica.dto.auth.UserRequestLogin;
import example.tecnica.dto.auth.UserRequestRegister;
import example.tecnica.dto.auth.UserResponseLogin;
import example.tecnica.dto.auth.UserResponseRegister;
import example.tecnica.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida que el email no esté duplicado, encripta la contraseña y crea el usuario.
     *
     * @param userRequest DTO con los datos del usuario a registrar (email, password, nombre, etc.)
     * @return UserResponseRegister con los datos del usuario creado
     * @throws ResponseStatusException con código 400 si el email ya está registrado
     */
    public UserResponseRegister registerUser(UserRequestRegister userRequest) {

        Boolean emailExists = userService.existsByEmail(userRequest.getEmail());
        if (emailExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"El correo electrónico ya está en uso");
        }

        String passwordEncoded = passwordEncoder.encode(userRequest.getPassword());

        User userToSave = userService.register(userRequest, passwordEncoded);

        return new UserResponseRegister(userToSave);
    }

    /**
     * Autentica un usuario y genera un token JWT.
     * Valida las credenciales comparando la contraseña encriptada.
     *
     * @param userRequest DTO con email y password del usuario
     * @return UserResponseLogin con el token JWT generado
     * @throws EntityNotFoundException si el usuario no existe
     * @throws ResponseStatusException con código 401 si las credenciales son incorrectas
     */
    public UserResponseLogin loginUser(UserRequestLogin userRequest) {

        User user = userService.getUserByEmail(userRequest.getEmail());

        if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            return new UserResponseLogin(jwtService.generateToken(user, null));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrecta");
        }
    }



}
