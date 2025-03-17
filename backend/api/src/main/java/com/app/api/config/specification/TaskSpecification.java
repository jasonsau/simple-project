package com.app.api.config.specification;

import org.springframework.data.jpa.domain.Specification;

import com.app.entities.Task;
import com.app.entities.User;

public class TaskSpecification {

    public static Specification<Task> byUser(User user) {
        return ( root, query, criteriaBuilder ) -> {
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }


}
