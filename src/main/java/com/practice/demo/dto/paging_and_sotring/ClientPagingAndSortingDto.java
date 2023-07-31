package com.practice.demo.dto.paging_and_sotring;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Builder
public class ClientPagingAndSortingDto extends PagingAndSortingDto {

    @Override
    public void initializeDefaultValues() {

        DEFAULT_PAGE = 0;
        DEFAULT_SIZE = 3;
        DEFAULT_SORT_BY = "numberOfAccounts";
        DEFAULT_ORDER = "DESC";
    }
}
