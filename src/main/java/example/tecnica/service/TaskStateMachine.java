package example.tecnica.service;

import example.tecnica.entity.TaskState;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Factory para gestionar las transiciones de estado de las tareas.
 * Implementa el patrón Factory para definir el flujo de estados válido.
 * Flujo: TODO → DOING → DONE
 */
@Component
public class TaskStateMachine {

    private final Map<TaskState, TaskState> stateMap;

    /**
     * Constructor que inicializa el mapa de transiciones de estado.
     * Define el flujo: TODO → DOING → DONE
     */
    public TaskStateMachine() {
        this.stateMap = Map.of(
                TaskState.TODO, TaskState.DOING,
                TaskState.DOING, TaskState.DONE
        );
    }

    /**
     * Obtiene el siguiente estado válido para una tarea.
     *
     * @param currentState Estado actual de la tarea
     * @return El siguiente estado en el flujo, o null si ya está en estado final (DONE)
     */
    public TaskState getNextState(TaskState currentState) {
        return stateMap.get(currentState);
    }
}
