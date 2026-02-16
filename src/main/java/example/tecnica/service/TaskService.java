package example.tecnica.service;

import example.tecnica.dto.task.*;
import example.tecnica.entity.Task;
import example.tecnica.entity.TaskState;
import example.tecnica.entity.User;
import example.tecnica.repository.TaskRepository;
import example.tecnica.repository.specs.TaskSpecs;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskStateMachine taskStateFactory;

    /**
     * Crea una nueva tarea en el sistema.
     * Si se proporciona un ID de usuario asignado, valida su existencia y lo asigna a la tarea.
     *
     * @param taskRequestCreate DTO con los datos de la nueva tarea (título, descripción, prioridad, etc.)
     * @return TaskResponse con la tarea creada
     * @throws EntityNotFoundException si el usuario asignado no existe
     */
    public TaskResponse createTask(TaskRequestCreate taskRequestCreate) {

        User userAssigned = null;

        if (taskRequestCreate.getUserAssignedId() != null) {
            userAssigned = userService.getUserById(taskRequestCreate.getUserAssignedId());
        }

        Task task = taskRepository.save(new Task(taskRequestCreate, userAssigned));


        return new TaskResponse(task);
    }


    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("tarea no encontrada"));
    }

    public TaskResponse getTaskResponseById(Long id) {
        return new TaskResponse(this.getTaskById(id));
    }

    /**
     * Actualiza los datos de una tarea existente.
     * Permite modificar título, descripción, prioridad, deadline y usuario asignado.
     * Si se proporciona un nuevo usuario asignado, valida su existencia antes de asignarlo.
     *
     * @param id ID de la tarea a actualizar
     * @param taskRequest DTO con los nuevos datos de la tarea
     * @return TaskResponse con la tarea actualizada
     * @throws EntityNotFoundException si la tarea o el usuario asignado no existen
     */
    public TaskResponse updateTask(Long id, TaskRequestEdit taskRequest) {
        Task task = this.getTaskById(id);

        User userAssigned = null;

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        task.setDeadline(taskRequest.getDeadline());

        if (taskRequest.getUserAssignedId() != null) {
            userAssigned = userService.getUserById(taskRequest.getUserAssignedId());
        }

        task.setAssignedTo(userAssigned);

        task = taskRepository.save(task);

        return new TaskResponse(task);
    }

    /**
     * Realiza un soft delete de la tarea, marcándola como inactiva.
     * La tarea no se elimina físicamente de la base de datos.
     *
     * @param id ID de la tarea a eliminar
     * @return TaskResponse con la tarea marcada como inactiva
     * @throws EntityNotFoundException si la tarea no existe
     */
    public TaskResponse deleteTask(Long id) {
        Task task = this.getTaskById(id);
        task.setActive(false);
        return new TaskResponse(task);
    }

    /**
     * Calcula los indicadores clave de rendimiento (KPIs) de las tareas.
     * Incluye:
     * - Total de tareas
     * - Tareas por estado (TODO, DOING, DONE)
     * - Tareas vencidas (con deadline anterior a hoy y estado TODO o DOING)
     *
     * @return TaskResponseKPI con todos los indicadores calculados
     */
    public TaskResponseKPI getKpis() {

        TaskResponseKPI kpis = new TaskResponseKPI();

        BigInteger overdueTasks = BigInteger.ZERO;

        List<Task> tasks = taskRepository.findAll();

        Map<TaskState, BigInteger> maps = new HashMap<>();

        Arrays.stream(TaskState.values())
                .forEach(taskState -> maps.put(taskState, BigInteger.ZERO));

        for (Task task : tasks) {
            maps.put(task.getState(), maps.get(task.getState()).add(BigInteger.ONE));

            if ((task.getState() == TaskState.TODO || task.getState() == TaskState.DOING)
                    && task.getDeadline().isBefore(LocalDate.now())) {
                overdueTasks = overdueTasks.add(BigInteger.ONE);
            }
        }

        kpis.setTotalTasks(BigInteger.valueOf(tasks.size()));
        kpis.setPendingTasks(maps.get(TaskState.TODO));
        kpis.setCompletedTasks(maps.get(TaskState.DONE));
        kpis.setOverdueTasks(overdueTasks);
        return kpis;
    }

    /**
     * Avanza la tarea al siguiente estado según el flujo definido.
     * Utiliza TaskStateFactory para determinar el próximo estado válido.
     * Ejemplo: TODO → DOING → DONE
     *
     * @param id ID de la tarea
     * @return TaskResponse con la tarea en su nuevo estado
     * @throws EntityNotFoundException si la tarea no existe
     * @throws IllegalStateException si la tarea ya está en estado final
     */
    public TaskResponse nextState(Long id) {
        Task task = this.getTaskById(id);
        if (task.getState().equals(TaskState.DONE)) {
            throw new IllegalStateException("La tarea ya está en estado final");
        }
        TaskState newState = taskStateFactory.getNextState(task.getState());
        task.setState(newState);
        task = taskRepository.save(task);
        return new TaskResponse(task);
    }

    /**
     * Obtiene una lista paginada de tareas aplicando filtros dinámicos.
     * Los filtros disponibles incluyen: usuario asignado, estado, prioridad y rango de fechas.
     * Utiliza Specifications de JPA para construir la query de forma dinámica.
     *
     * @param filter Objeto con los criterios de filtrado
     * @param pageable Configuración de paginación y ordenamiento
     * @return Lista de TaskResponse que cumplen los criterios
     */
    public List<TaskResponse> getAllTasks(TaskFilter filter, Pageable pageable) {

        Specification<Task> spec = Specification
                .where(TaskSpecs.hasUserId(filter.getAssignedToId()))
                .and(TaskSpecs.hasState(filter.getState()))
                .and(TaskSpecs.hasPriority(filter.getPriority()))
                .and(TaskSpecs.createdBetween(filter.getDateFrom(), filter.getDateAt()));

        Page<Task> tasks = taskRepository.findAll(spec, pageable);

        return tasks.map(TaskResponse::new).getContent();
    }


    public List<TaskResponse> searchByTitle(String q) {
        Specification<Task> specification = Specification.where(TaskSpecs.titleLike(q));

        List<Task> tasks = taskRepository.findAll(specification);

        return tasks.stream().map(TaskResponse::new).toList();
    }

}
