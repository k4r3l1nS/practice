package com.practice.demo.models.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
@Setter
public class Condition {

    private final String path;
    private final List<Comparable> rightOperand;
    private final OperationType operation;
    private LogicalOperatorType logicalOperator;

    @Getter
    public enum OperationType {

        GREATER(">"),
        LESS("<"),
        EQUALS("="),
        IN ("in"),
        BEGINS_WITH("begins with");

        private String name;

        private final static Map<String, OperationType> _map;

        static {

            _map = Stream.of(values()).collect(Collectors.toMap(OperationType::getName, Function.identity()));
        }

        OperationType(String name) {
            this.name = name;
        }

        public static OperationType resolveByName(String name) {
            return _map.getOrDefault(name, null);
        }
    }

    public enum LogicalOperatorType {

        AND, OR, END;
    }
}
