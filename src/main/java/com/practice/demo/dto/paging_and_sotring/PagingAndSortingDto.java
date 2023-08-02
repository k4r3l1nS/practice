package com.practice.demo.dto.paging_and_sotring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public abstract class PagingAndSortingDto {

    public static int DEFAULT_PAGE;
    public static int DEFAULT_SIZE;
    public static String DEFAULT_SORT_BY;
    public static String DEFAULT_ORDER;

    Integer page;
    Integer size;
    String sortBy;
    String order;

    public abstract void initializeDefaultValues();

    public PageRequest toPageRequest() {

        Sort.Direction sortDirection = order.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;

        return PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
    }

    public void fillEmptyFields() {

        initializeDefaultValues();

        if (page == null)
            page = DEFAULT_PAGE;

        if (size == null)
            size = DEFAULT_SIZE;

        if (sortBy == null || sortBy.isEmpty())
            sortBy = DEFAULT_SORT_BY;

        if (order == null || order.isEmpty())
            order = DEFAULT_ORDER;
    }
}
