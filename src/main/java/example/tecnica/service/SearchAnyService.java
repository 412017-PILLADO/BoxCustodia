package example.tecnica.service;

import example.tecnica.dto.searchAny.SearchAnyResponse;
import example.tecnica.dto.searchAny.SearchResultType;
import example.tecnica.dto.task.TaskResponse;
import example.tecnica.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchAnyService {

    private final TaskService taskService;
    private final UserService userService;

    /**
     * Realiza una búsqueda global en tareas y usuarios.
     * Busca coincidencias en el título de las tareas y en el email de los usuarios.
     * Los resultados se agrupan en una lista unificada con tipo discriminador (TASK o USER).
     *
     * @param q Término de búsqueda (texto a buscar)
     * @return Lista de SearchAnyResponse con los resultados de tareas y usuarios
     */
    public List<SearchAnyResponse> searchAny(String q) {
        if (q == null || q.isBlank()) return List.of();
        String term = q.trim();

        List<SearchAnyResponse> results = new ArrayList<>();

        // Buscar tareas
        List<TaskResponse> taskResults = taskService.searchByTitle(term);
        for (TaskResponse task : taskResults) {
            results.add(new SearchAnyResponse(SearchResultType.TASK, task, null));
        }

        // Buscar usuarios
        List<UserResponse> userResults = userService.searchByEmail(term);
        for (UserResponse user : userResults) {
            results.add(new SearchAnyResponse(SearchResultType.USER, null, user));
        }

        return results;
    }


}
