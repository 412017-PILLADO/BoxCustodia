package example.tecnica.repository.specs;

import example.tecnica.entity.Task;
import example.tecnica.entity.TaskPriority;
import example.tecnica.entity.TaskState;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


public class TaskSpecs {

    public static Specification<Task> hasState(TaskState state) {
        return (root, query, cb) -> {
            if (state == null) return null;
            return cb.equal(root.get("state"), state);
        };
    }


    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, cb) -> {
            if (priority == null) return null;
            return cb.equal(root.get("priority"), priority);
        };
    }

    public static Specification<Task> createdBetween(LocalDate from, LocalDate to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;

            Path<LocalDate> date = root.get("creationDate");
            if (from != null && to != null) return cb.between(date, from, to);
            if (from != null) return cb.greaterThanOrEqualTo(date, from);
            return cb.lessThanOrEqualTo(date, to);
        };
    }

    public static Specification<Task> hasUserId(Long userId) {
        return (root, query, cb) -> {
            if (userId == null) return null;
            return cb.equal(root.get("assignedTo").get("id"), userId);
        };
    }

    public static Specification<Task> titleLike(String q) {
        return (root, query, cb) -> {
            if (q == null || q.isBlank()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + q.trim().toLowerCase() + "%");
        };
    }





}
