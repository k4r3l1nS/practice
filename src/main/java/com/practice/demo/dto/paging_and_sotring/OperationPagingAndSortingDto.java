package com.practice.demo.dto.paging_and_sotring;

import com.practice.demo.models.rates.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OperationPagingAndSortingDto extends PagingAndSortingDto {

    @Override
    public void initializeDefaultValues() {

        DEFAULT_PAGE = 0;
        DEFAULT_SIZE = 5;
        DEFAULT_SORT_BY = "operationDateTime";
        DEFAULT_ORDER = "DESC";
    }
}