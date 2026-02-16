package example.tecnica.dto.task;

import example.tecnica.entity.TaskPriority;
import example.tecnica.entity.TaskState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TaskFilter {

    private TaskState state;

    private TaskPriority priority;

    private Long assignedToId;

    private LocalDate dateFrom;

    private LocalDate dateAt;

}
