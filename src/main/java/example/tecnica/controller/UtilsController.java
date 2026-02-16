package example.tecnica.controller;

import example.tecnica.error.ErrorApi;
import example.tecnica.service.UtilsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/utils")
@AllArgsConstructor
@Tag(name = "Utilidades", description = "Endpoints auxiliares para obtener catálogos y valores permitidos")
public class UtilsController {

    private final UtilsService utilsService;

    @GetMapping("/tasks/priorities")
    @Operation(
        summary = "Obtener prioridades disponibles",
        description = "Retorna la lista de prioridades válidas para las tareas (ej: LOW, MEDIUM, HIGH)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de prioridades obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<List<String>> getPriorityTasks() {
        return new ResponseEntity<>(utilsService.getPriorityTasks(), HttpStatus.OK);
    }

    @GetMapping("/tasks/states")
    @Operation(
        summary = "Obtener estados disponibles",
        description = "Retorna la lista de estados válidos para las tareas (ej: TODO, IN_PROGRESS, DONE)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de estados obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<List<String>> getStateTasks() {
        return new ResponseEntity<>(utilsService.getStateTasks(), HttpStatus.OK);
    }



}
