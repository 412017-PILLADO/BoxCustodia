package example.tecnica.dto.task;

import lombok.*;

import java.math.BigInteger;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseKPI {

    private BigInteger totalTasks;

    private BigInteger completedTasks;

    private BigInteger pendingTasks;

    private BigInteger overdueTasks;

}
