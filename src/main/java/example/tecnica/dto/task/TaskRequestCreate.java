package example.tecnica.dto.task;

import example.tecnica.entity.TaskPriority;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestCreate {

    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 500, message = "La descripción debe tener menos de 500 caracteres")
    private String description;

    @NotBlank(message = "La prioridad no puede estar vacía")
    private TaskPriority priority;

    @NotBlank(message = "La fecha límite no puede estar vacía")
    @FutureOrPresent(message = "La fecha límite debe ser una fecha futura")
    private LocalDate deadline;

    private Long userAssignedId;


}
