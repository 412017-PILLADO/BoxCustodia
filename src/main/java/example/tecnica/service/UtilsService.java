package example.tecnica.service;

import example.tecnica.entity.TaskPriority;
import example.tecnica.entity.TaskState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UtilsService {

    //devuelve las prioridades de las tareas en formato String
    public List<String> getPriorityTasks() {
        return new ArrayList<>(Stream.of(TaskPriority.values()).map(TaskPriority::name).toList());
    }

    //devuelve los estados de las tareas en formato String
    public List<String> getStateTasks() {
        return new ArrayList<>(Stream.of(TaskState.values()).map(TaskState::name).toList());
    }

}
