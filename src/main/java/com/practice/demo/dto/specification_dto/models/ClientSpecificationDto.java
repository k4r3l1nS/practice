package com.practice.demo.dto.specification_dto.models;

import com.practice.demo.dto.specification_dto.SpecificationDto;
import com.practice.demo.models.specification.Condition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
public class ClientSpecificationDto implements SpecificationDto {

    public final static String DEFAULT_FIRST_NAME_OPERATION_TYPE = "=";
    public final static String DEFAULT_LAST_NAME_OPERATION_TYPE = "=";
    public final static String DEFAULT_NUMBER_OF_ACCOUNTS_OPERATION_TYPE = "=";
    public final static String DEFAULT_REGISTRATION_DATE_OPERATION_TYPE = ">";
    public final static String DEFAULT_BIRTH_DATE_OPERATION_TYPE = ">";

    private String firstName;
    private String lastName;
    private Integer numberOfAccounts;
    private LocalDateTime registrationDate;
    private LocalDate birthDate;

    private String firstNameOT;
    private String lastNameOT;
    private String numberOfAccountsOT;
    private String registrationDateOT;
    private String birthDateOT;

    @Override
    public boolean isEmpty() {

        return (firstName == null || firstName.isEmpty()) && (lastName == null || lastName.isEmpty()) &&
                numberOfAccounts == null && registrationDate == null && birthDate == null;
    }

    @Override
    public List<Condition> toConditions() {

        List<Condition> conditions = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {

            conditions.add(new Condition("firstName", Arrays.asList(firstName),
                    Condition.OperationType.resolveByName(firstNameOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (lastName != null && !lastName.isEmpty()) {

            conditions.add(new Condition("lastName", Arrays.asList(lastName),
                    Condition.OperationType.resolveByName(lastNameOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (numberOfAccounts != null) {

            conditions.add(new Condition("numberOfAccounts", Arrays.asList(numberOfAccounts.toString()),
                    Condition.OperationType.resolveByName(numberOfAccountsOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (registrationDate != null) {

            conditions.add(new Condition("registrationDate", Arrays.asList(registrationDate.toString()),
                    Condition.OperationType.resolveByName(registrationDateOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (birthDate != null) {

            conditions.add(new Condition("birthDate", Arrays.asList(birthDate.toString()),
                    Condition.OperationType.resolveByName(birthDateOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (!conditions.isEmpty())
            conditions.get(conditions.size() - 1).setLogicalOperator(Condition.LogicalOperatorType.END);

        return conditions.isEmpty() ? null : conditions;
    }

    @Override
    public void fillEmptyFields() {

        if (firstNameOT == null)
            firstNameOT = DEFAULT_FIRST_NAME_OPERATION_TYPE;

        if (lastNameOT == null)
            lastNameOT = DEFAULT_LAST_NAME_OPERATION_TYPE;

        if (numberOfAccountsOT == null)
            numberOfAccountsOT = DEFAULT_NUMBER_OF_ACCOUNTS_OPERATION_TYPE;

        if (registrationDateOT == null)
            registrationDateOT = DEFAULT_REGISTRATION_DATE_OPERATION_TYPE;

        if (birthDateOT == null)
            birthDateOT = DEFAULT_BIRTH_DATE_OPERATION_TYPE;
    }
}
