package example.tecnica.controller;

import example.tecnica.dto.auth.UserRequestLogin;
import example.tecnica.dto.auth.UserRequestRegister;
import example.tecnica.dto.auth.UserResponseLogin;
import example.tecnica.dto.auth.UserResponseRegister;
import example.tecnica.error.ErrorApi;
import example.tecnica.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro y autenticación de usuarios")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un nuevo usuario en el sistema. El username debe ser único."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponseRegister.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o username duplicado",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<UserResponseRegister> registerUser(@Valid @RequestBody UserRequestRegister userRequestRegister) {
        return new ResponseEntity<>(authService.registerUser(userRequestRegister), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica un usuario y retorna un token JWT para acceder a los recursos protegidos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa, retorna token JWT",
            content = @Content(schema = @Schema(implementation = UserResponseLogin.class))),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "401", description = "Usuario no autorizado",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<UserResponseLogin> loginUser(@Valid @RequestBody UserRequestLogin userRequestLogin) {
        return new ResponseEntity<>(authService.loginUser(userRequestLogin), HttpStatus.OK);
    }

}
