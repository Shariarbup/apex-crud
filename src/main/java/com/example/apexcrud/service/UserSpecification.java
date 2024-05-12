package com.example.apexcrud.service;

import com.example.apexcrud.dto.UserFilterRequestDTO;
import com.example.apexcrud.model.User;
import com.example.apexcrud.utils.PaginationUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSpecification {
    public Specification<User> getUserSpecification(List<UserFilterRequestDTO> userFilterRequestDTOS) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            userFilterRequestDTOS.forEach(filter -> {
                if (filter != null) {
                    if (filter.getField().equals("userId")) {
                        String value = PaginationUtil.getFiledValue(filter);
                        predicates.add(criteriaBuilder.like(root.get("userId"), "%" + value + "%"));
                    } else if (filter.getField().equals("username")) {
                        String value = PaginationUtil.getFiledValue(filter);
                        predicates.add(criteriaBuilder.like(root.get("userName"), "%" + value + "%"));
                    }  else if (filter.getField().equals("email")) {
                        String value = PaginationUtil.getFiledValue(filter);
                        predicates.add(criteriaBuilder.like(root.get("email"), "%" + value + "%"));
                    } else if (filter.getField().equals("designation")) {
                        String value = PaginationUtil.getFiledValue(filter);
                        predicates.add(criteriaBuilder.like(root.get("designation"), "%" + value + "%"));
                    }
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }
}
