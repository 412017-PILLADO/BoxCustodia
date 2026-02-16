package example.tecnica.entity;

import example.tecnica.dto.task.TaskRequestCreate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    @Column(name = "priority_weight", nullable = false)
    private Integer priorityWeight;

    @Enumerated(EnumType.STRING)
    private TaskState state;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
        this.priorityWeight = (priority == null) ? null : priority.getWeight();
    }

    public Task(TaskRequestCreate taskRequestCreate, User userAssigned) {
        this.title = taskRequestCreate.getTitle();
        this.description = taskRequestCreate.getDescription();
        this.setPriority(taskRequestCreate.getPriority());
        this.state = TaskState.TODO;
        this.creationDate = LocalDate.now();
        this.deadline = taskRequestCreate.getDeadline();
        this.assignedTo = userAssigned;
        this.active = true;
    }
}