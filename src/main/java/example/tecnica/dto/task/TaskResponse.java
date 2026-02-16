package example.tecnica.dto.task;

import example.tecnica.dto.user.UserResponse;
import example.tecnica.entity.Task;
import example.tecnica.entity.TaskPriority;
import example.tecnica.entity.TaskState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private TaskPriority priority;

    private TaskState state;

    private LocalDate creationDate;

    private LocalDate deadline;

    private Boolean active;

    private UserResponse userAssigned;

    public TaskResponse(Task task) {
        this.creationDate = task.getCreationDate();
        this.deadline = task.getDeadline();
        this.description = task.getDescription();
        this.id = task.getId();
        this.priority = task.getPriority();
        this.state = task.getState();
        this.title = task.getTitle();
        this.active = task.getActive();
        this.userAssigned = task.getAssignedTo() != null ? new UserResponse(task.getAssignedTo()) : null;
    }
}
