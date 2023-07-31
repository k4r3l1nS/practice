package com.practice.demo.models.specification;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
public class SpecificationImpl<T> implements Specification<T> {

    private final Condition condition;
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (condition.getRightOperand() == null)
            return root.get(condition.getPath()).isNull();

        switch (condition.getOperation()) {

            case EQUALS: {

                return criteriaBuilder.equal(root.get(condition.getPath()).as(condition.getRightOperand().get(0).getClass()),
                        condition.getRightOperand().get(0));
            }

            case LESS:

                return criteriaBuilder.lessThan(root.get(condition.getPath()).as(condition.getRightOperand().get(0).getClass()),
                        condition.getRightOperand().get(0));

            case GREATER:
                return criteriaBuilder.greaterThan(root.get(condition.getPath()).as(condition.getRightOperand().get(0).getClass()),
                        condition.getRightOperand().get(0));

            case IN:{
//                System.out.println(root.get(condition.getPath()).toString()); condition.getRightOperand().stream().forEach(x -> System.out.println(x));
                Predicate predicate = root.get(condition.getPath()).as(condition.getRightOperand().get(0).getClass()).in(condition.getRightOperand());

                if (condition.getRightOperand().contains(null))
                    return criteriaBuilder.or(predicate, root.get(condition.getPath()).isNull());

                return predicate;
            }

            default:
                return null;
        }
    }
}
