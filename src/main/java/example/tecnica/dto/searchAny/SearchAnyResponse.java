package example.tecnica.dto.searchAny;

import example.tecnica.dto.task.TaskResponse;
import example.tecnica.dto.user.UserResponse;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchAnyResponse {

    private SearchResultType type;

    private TaskResponse taskResponse;

    private UserResponse userResponse;
}
