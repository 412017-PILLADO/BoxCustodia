package example.tecnica.controller;

import example.tecnica.dto.task.*;
import example.tecnica.error.ErrorApi;
import example.tecnica.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@AllArgsConstructor
@Tag(name = "Tareas", description = "Gestión completa de tareas: crear, editar, eliminar, consultar y cambiar estado")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(
        summary = "Crear tarea",
        description = "Crea una nueva tarea con título, descripción, prioridad y estado inicial."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente",
            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequestCreate taskRequest) {
        return new ResponseEntity<>(taskService.createTask(taskRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar tarea",
        description = "Actualiza los datos de una tarea existente (título, descripción, prioridad, estado, etc.)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<TaskResponse> updateTask(
            @Parameter(description = "ID de la tarea", example = "1") @PathVariable Long id,
            @Valid @RequestBody TaskRequestEdit taskRequest) {
        return new ResponseEntity<>(taskService.updateTask(id, taskRequest),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar tarea",
        description = "Elimina una tarea por su ID (soft delete o hard delete según implementación)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea eliminada exitosamente",
            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<TaskResponse> deleteTask(
            @Parameter(description = "ID de la tarea", example = "1") @PathVariable Long id) {
       return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }

    @GetMapping("/kpis")
    @Operation(
        summary = "Obtener KPIs de tareas",
        description = "Retorna indicadores clave de rendimiento: total de tareas, por estado, por prioridad, etc."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "KPIs obtenidos exitosamente",
            content = @Content(schema = @Schema(implementation = TaskResponseKPI.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<TaskResponseKPI> getKPI() {
        return new ResponseEntity<>(taskService.getKpis(), HttpStatus.OK);
    }

    @PatchMapping("/{id}/state")
    @Operation(
        summary = "Avanzar estado de tarea",
        description = "Cambia la tarea al siguiente estado en el flujo (ej: TODO → IN_PROGRESS → DONE)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "400", description = "No se puede avanzar el estado (ya está en estado final)",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<TaskResponse> nextState(
            @Parameter(description = "ID de la tarea", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(taskService.nextState(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener tarea por ID",
        description = "Retorna los detalles completos de una tarea específica."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea encontrada",
            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<TaskResponse> getTaskById(
            @Parameter(description = "ID de la tarea", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskResponseById(id), HttpStatus.OK);
    }

    @GetMapping()
    @Operation(
        summary = "Listar tareas",
        description = "Obtiene una lista paginada y ordenada de tareas. Soporta filtros por título, prioridad, estado, usuario, etc. Ejemplo de uso: ?page=0&size=10&sort=creationDate,desc&title=Compras&priority=HIGH"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = TaskResponse.class))),
        @ApiResponse(responseCode = "400", description = "Parámetros de filtro o paginación inválidos",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @Parameter(description = "Filtros: title, priority, state, userId, etc.") @ModelAttribute TaskFilter filter,
            @Parameter(description = "Paginación y ordenamiento. Ejemplo: page=0&size=20&sort=creationDate,desc")
            @PageableDefault(size = 20, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(taskService.getAllTasks(filter, pageable), HttpStatus.OK);
    }
}
