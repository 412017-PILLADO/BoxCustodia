package example.tecnica.repository.specs;

import example.tecnica.entity.Task;
import example.tecnica.entity.TaskState;
import example.tecnica.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {

    public static Specification<User> emailLike(String q) {
        return (root, query, cb) -> {
            if (q == null || q.isBlank()) return null;
            String like = "%" + q.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("email")), like);
        };
    }


}
